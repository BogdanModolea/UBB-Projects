use Esports

go
create or alter procedure addToTable (@tableName varchar(50)) as
	begin
		if @tableName in (select [Name] from [Tables])
		begin
			print 'Table already present in Tables'
			return
		end

		if @tableName not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES)
		begin
			print 'Table not present in the database'
			return
		end

		insert into [Tables] ([Name])
		values (@tableName)

	end


go
create or alter procedure addToView (@viewName varchar(50)) as
	begin
		if @viewName in (select [Name] from [Views])
		begin
			print 'View already present in Views'
			return
		end

		if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS)
		begin
			print 'View not present in the database'
			return
		end

		insert into [Views] ([Name])
		values (@viewName)
	end

go
create or alter procedure addToTests(@testName varchar(50)) as
	begin
		if @testName in (select [Name] from [Tests])
		begin
			print 'Test already present in Tests'
			return
		end

		insert into [Tests] ([Name])
		values
			(@testName)

	end

go
create or alter procedure connectTableToTest (@tableName varchar(50), @testName varchar(50), @rows int, @pos int) as
	begin
		if @tableName not in (select [Name] from [Tables])
			begin
				print 'Table not present in Tables'
				return
			end

		if @testName not in (select [Name] from [Tests])
			begin
				print 'Test not present in Test'
				return
			end

		if exists(
			select *
			from TestTables T1 join Tests T2 on T1.TestID = T2.TestID
			where T2.[Name] = @testName and Position = @pos
		)
			begin
				print 'Position provided conflicts with previous position'
				return
			end

		insert into [TestTables] (TestID, TableID, NoOfRows, Position)
		values (
			(select [Tests].TestID from [Tests] where [Name] = @testName),
			(select [Tables].TableID from [Tables] where [Name] = @tableName),
			@rows,
			@pos
		)
	end

go
create or alter procedure connectViewToTest (@viewName varchar(50), @testName varchar(50)) as
	begin
		if @viewName not in (select [Name] from [Views])
			begin
				print 'View not present in Views'
				return
			end

		if @testName not in (select [Name] from [Tests])
			begin
				print 'Test not present in Tests'
				return
			end

		insert into [TestViews] (TestID, ViewID)
		values(
			(select [Tests].TestID from [Tests] where [Name] = @testName),
			(select [Views].ViewID from [Views] where [Name] = @viewName)
		)
	end

go
create or alter procedure deleteFromTable (@tableID int) as
	begin
		if @tableID not in (select [TableID] from [Tables])
			begin
				print 'Table not in Tables'
				return
			end

			declare @tableName nvarchar(50) = (select [Name] from [Tables] where TableID = @tableID)
			print 'Delete data from table ' + @tableName
			declare @query nvarchar(100) = N'DELETE FROM ' + @tableName
			print @query
			exec sp_executesql @query
	end

go
create or alter procedure deleteDataFromAllTables (@testID int) as
	begin 
		if @testID not in (select [TestID] from [Tests])
			begin
				print 'Test not present in Tests'
				return
			end
			print 'Delete data from all tables for the test ' + convert(varchar, @testID)
			declare @tableID int
			declare @pos int

			declare allTableCursorDesc cursor local for
				select T1.TableID, T1.Position
				from TestTables T1
				inner join Tests T2 on T2.TestID = T1.TestID
				where T2.TestID = @testID
				order by T1.Position desc

			open allTableCursorDesc
			fetch allTableCursorDesc into @tableID, @pos
			while @@FETCH_STATUS = 0
				begin
					exec deleteFromTable @tableID
					fetch next from allTableCursorDesc into @tableID, @pos
				end
			close allTableCursorDesc
			deallocate allTableCursorDesc
	end

go
create or alter procedure insertDataIntoTable (@testRunID int, @testID int, @tableID int) as
	begin
		if @testID not in (select [TestID] from [Tests])
		begin
			print 'Test not present in Tests'
			return
		end

		if @testRunID not in (select [TestRunID] from [TestRuns])
			begin
				print 'Test run not present in TestRuns'
				return
			end

		if @tableID not in (select [TableID] from [Tables])
			begin
				print 'Table not present in Tables'
				return
			end

		declare @startTime datetime = sysdatetime()
		declare @tableName varchar(50) = (
			select [Name] from [Tables] where TableID = @tableID
		)
		print 'Insert data into table ' + @tableName
		declare @numberOfRows int = (
			select [NoOfRows] from [TestTables] where TestID = @testID and TableID = @tableID
		)




		
		declare @query varchar(100)
		set @query = 'populateTable' + @tableName
		print @query
		exec @query @numberOfRows
		
		
		
		
		
		declare @endTime datetime = sysdatetime()
		insert into TestRunTables(TestRunID, TableID, StartAt, EndAt)
		values (@testRunID, @tableID, @startTime, @endTime)
	end

go
create or alter procedure insertDataIntoAllTables(@testRunID int, @testID int) as
	begin
		if @testID not in (select [TestID] from [Tests])
			begin
				print 'Test not present in Tests'
				return
			end

		if @testRunID not in (select [TestRunID] from [TestRuns])
			begin
				print 'Test run not present in TestRuns'
				return
			end

		print 'Insert data in all the tables for the test ' + convert(varchar, @testID)
		
		declare @tableID int
		declare @pos int
		
		declare allTableCursorAsc cursor local for
			select T1.TableID, T1.Position
			from TestTables T1
			inner join Tests T2 on T2.TestID = T1.TestID
			where T1.TestID = @testID
			order by T1.Position asc

		open allTableCursorAsc
		fetch allTableCursorAsc into @tableID, @pos
		while @@FETCH_STATUS = 0
			begin
				exec insertDataIntoTable @testRunID, @testID, @tableID
				fetch next from allTableCursorAsc into @tableID, @pos
			end

		close allTableCursorAsc
		deallocate allTableCursorAsc


	end

go
create or alter procedure selectDataFromView (@viewID int, @testRunID int) as
	begin
		if @viewID not in (select [ViewID] from [Views])
			begin 
				print 'View not present in Views'
				return
			end

		if @testRunID not in (select [TestRunID] from [TestRuns])
			begin
				print 'Test run not present in TestRuns'
				return
			end

		declare @startTime datetime = sysdatetime()
		declare @viewName varchar(100) = (
			select [Name] from [Views] where ViewID = @viewID
		)
		print 'Selecting data from the view ' + @viewName

		declare @query nvarchar(150) = N'SELECT * FROM ' + @viewName
		exec sp_executesql @query

		declare @endTime datetime = sysdatetime()
		insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values
		(@testRunID, @viewID, @startTime, @endTime)

	end

go
create or alter procedure selectAllViews (@testRunID int, @testID int) as
	begin
		if @testID not in (select [TestID] from [Tests])
			BEGIN
				print 'Test not present in Tests'
				return
			end

		if @testRunID not in (select [TestRunID] from [TestRuns])
			begin
				print 'Test run not present in TestRuns'
				return
			end


		print 'Selecting data from all the views from the test ' + convert(varchar, @testID)
		declare @viewID int

		declare selectAllViewsCursor cursor local for
			select T1.ViewID 
			from TestViews T1
			inner join Tests T2 on T2.TestID = T1.TestID
			where T1.TestID = @testID

		open selectAllViewsCursor
		fetch selectAllViewsCursor into @viewID
		while @@FETCH_STATUS = 0 
			begin
				exec selectDataFromView @viewID, @testRunID
				fetch next from selectAllViewsCursor into @viewID
			end
		close selectAllViewsCursor
		deallocate selectAllViewsCursor

	end

go
create or alter procedure runTest(@testID int, @description varchar(max)) as
	begin
		if @testID not in (select [TestID] from [Tests])
			begin
				print 'Test not present in Tests'
				return
			end

		print 'Running test with id ' + convert(varchar, @testID)
		insert into TestRuns([Description]) values (@description)
		
		declare @testRunID int = (select max(TestRunID) from TestRuns)

		declare @startTime datetime = sysdatetime()
		exec insertDataIntoAllTables @testRunID, @testID
		exec selectAllViews @testRunID, @testID
		declare @endTime datetime = sysdatetime()
		exec deleteDataFromAllTables @testID

		update [TestRuns] set StartAt = @startTime, EndAt = @endTime
		declare @timeDifference int = datediff(second, @startTime, @endTime)
		print 'The test with id ' + convert(varchar, @testID) + ' took ' + convert(varchar, @timeDifference) + ' seconds'

	end


go
create or alter procedure runAllTests as
	begin
		declare @testName varchar(50)
		declare @testID int
		declare @description varchar(2000)
		
		declare allTestsCursors cursor local for
			select *
			from Tests

		open allTestsCursors
		fetch allTestsCursors into @testID, @testName
		while @@FETCH_STATUS = 0
			begin
				print 'Running ' + @testName
				set @description = 'Test result for the thest with ID ' + cast(@testID as varchar(2))
				exec runTest @testID, @description
				fetch next from allTestsCursors into @testID, @testName
			end

		close allTestsCursors
		deallocate allTestsCursors

	end


-- a table with a single-column primary key and no foreign keys;
-- a view with a SELECT statement operating on one table;
go
create or alter view viewFans as
	select *
	from Fan


exec addToTable 'Fan'
exec addToView viewFans
exec addToTests 'Test1'
exec connectTableToTest 'Fan', 'Test1', 1000, 1
exec connectViewToTest 'viewFans', 'Test1'

create or alter procedure populateTableFan (@rows int) as
	while @rows > 0
		begin
			insert into Fan(fanID, fanName, dob)
			values (@rows, 'Name' + cast(@rows as varchar(max)), '2020-01-01')
			set @rows = @rows - 1
		end



-- a table with a single-column primary key and at least one foreign key;
-- a view with a SELECT statement that operates on at least 2 different tables and contains at least one JOIN operator;

go
create or alter view viewSponsors as
	select S.companyName, T.teamID
	from Sponsors S
	inner join Teams T on S.teamID = T.teamID

	
exec addToView viewSponsors
exec addToTests 'Test2'

exec addToTable 'Sponsors'
exec connectTableToTest 'Sponsors', 'Test2', 500, 2

exec addToTable 'Teams'
exec connectTableToTest 'Teams', 'Test2', 500, 1

exec connectViewToTest 'viewSponsors', 'Test2'



create or alter procedure populateTableSponsors (@rows int) as
	while @rows > 0
		begin
			insert into Sponsors(sponsorID, companyName, sponsorYear, teamID)
			values (@rows, 
			'Name' + cast(@rows as varchar(max)), 
			'2020-01-01', 
			(select top 1 teamID from Teams order by newid())
			)
			set @rows = @rows - 1
		end

create or alter procedure populateTableTeams (@rows int) as
	while @rows > 0
		begin
			insert into Teams(teamID, teamLocation, teamOwner, maxAttendance)
			values (
			'Team' + cast(@rows as varchar(max)), 
			'Country' + cast(@rows as varchar(max)), 
			'Name' + cast(@rows as varchar(max)),
			floor(1000*rand())
			)
			set @rows = @rows - 1
		end


-- a table with a multicolumn primary key, (Players -> (ign, teamID))
-- a view with a SELECT statement that has a GROUP BY clause, operates on at least 2 different tables and contains at least one JOIN operator.

ALTER TABLE Players ALTER COLUMN teamID varchar(50) NOT NULL;
alter table Players drop constraint PK__Players__DC5031E6CB2FD372
alter table Players add constraint PlayerPK primary key(ign, teamID)


go
create or alter view viewPlayers as
	select P.teamID, count(*) as Fans
	from Players P
	inner join FanOf FO on P.teamID = FO.teamID
	group by P.teamID

exec addToView viewPlayers
exec addToTests 'Test3'

exec addToTable 'Teams'
exec connectTableToTest 'Teams', 'Test3', 500, 1

exec addToTable 'Players'
exec connectTableToTest 'Players', 'Test3', 500, 2

exec addToTable 'Fan'
exec connectTableToTest 'Fan', 'Test3', 500, 3

exec addToTable 'FanOf'
exec connectTableToTest 'FanOf', 'Test3', 500, 4


exec connectViewToTest 'viewPlayers', 'Test3'


create or alter procedure populateTableFanOf (@rows int) as
	while @rows > 0
			begin
				insert into FanOf(fanID, teamID)
				values (
				(select top 1 fanID from Fan order by newid()),
				(select top 1 teamID from Teams order by newid())
				)
				set @rows = @rows - 1
			end

create or alter procedure populateTablePlayers (@rows int) as
	while @rows > 0
			begin
				insert into Players(ign, playerName, dob, teamID)
				values (
				'Player' + cast(@rows as varchar(max)), 
				'Name' + cast(@rows as varchar(max)), 
				'2020-01-01',
				(select top 1 teamID from Teams order by newid())
				) 
				set @rows = @rows - 1
			end











execute runAllTests
execute runTest 8, 'Something'























SELECT *
FROM [Views]

SELECT *
FROM [Tables]

SELECT *
FROM [Tests]

SELECT *
FROM [TestTables]

SELECT *
FROM [TestViews]

SELECT *
FROM [TestRuns]

SELECT *
FROM [TestRunTables]

SELECT *
FROM [TestRunViews]

delete from [Views]
delete from [Tables]
delete from [Tests]
delete from [TestTables]
delete from [TestViews]
delete from [TestRuns]
delete from [TestRunTables]
delete from [TestRunViews]

delete from Teams
delete from CasterDetails
delete from Casters
delete from CurrentSeason
delete from FanOf
delete from Fan
delete from Leagues
delete from PastWinners
delete from Players
delete from Sponsors




select C.COLUMN_NAME FROM  
INFORMATION_SCHEMA.TABLE_CONSTRAINTS T  
JOIN INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE C  
ON C.CONSTRAINT_NAME=T.CONSTRAINT_NAME  
WHERE  
C.TABLE_NAME='Players'  
and T.CONSTRAINT_TYPE='PRIMARY KEY' 


SELECT TABLE_NAME,
       CONSTRAINT_TYPE,CONSTRAINT_NAME
FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
WHERE TABLE_NAME='Players';