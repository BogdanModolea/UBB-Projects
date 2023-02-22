use A5VS

drop table Tc
drop table Tb
drop table Ta

create table Ta(
	aid int primary key,
	a2 int unique,
	a3 int
)

create table Tb(
	bid int primary key,
	b2 int
)

create table Tc(
	cid int primary key,
	aid int foreign key references Ta(aid),
	bid int foreign key references Tb(bid)
)

create or alter procedure insertIntoTa(@rows int) as
    declare @max int
    set @max = @rows*2 + 100
    while @rows > 0 begin
        insert into Ta values (@rows, @max, @rows%120)
        set @rows = @rows-1
        set @max = @max-2
    end

create or alter procedure insertIntoTb(@rows int) as
    while @rows > 0 begin
        insert into Tb values (@rows, @rows%870)
        set @rows = @rows-1
    end

create or alter procedure insertIntoTc(@rows int) as
    declare @aid int
    declare @bid int
    while @rows > 0 begin
        set @aid = (select top 1 aid from Ta order by NEWID())
        set @bid = (select top 1 bid from Tb order by NEWID())
        insert into Tc values (@rows, @aid, @bid)
        set @rows = @rows-1
    end

exec insertIntoTa 10000
exec insertIntoTb 12000
exec insertIntoTc 4000



--- Additional questions: 
--- a) Key lookup: How can we avoid key lookup, but keep the same info selected? (Add an index on a3)
--- Can we have 2 clustered indexes in the same table? 
--- What is the difference between clustered and nonclustered?



--- a. Write queries on Ta such that their execution plans contain the following operators:

--- clustered index scan;
--- reads all the rows in the table, in the order of the clustered index (aid is primary key and by default clustered) ---
--- scan the entire table ---
--- Cost: 0.03 ---

SELECT *
FROM Ta
order by aid



--- clustered index seek;
--- return a specific subset of rows from a clustered index
--- Cost: 0.003 ---

select *
from Ta
where aid < 100



--- nonclustered index scan;
--- scan the entire nonclustered index 
--- Cost: 0.02

create nonclustered index index1 on Ta(a2) --- not necessary, because a2 is unique and is by default nonclustered
drop index index1 on Ta

select a2
from Ta
order by a2



--- nonclustered index seek;
--- return a specific subset of rows from a nonclustered index
--- Cost: 0.003

select a2
from Ta
where a2 > 100 and a2 < 150



--- key lookup.
--- the data is found in a nonclustered index, but additional data is needed
--- Cost: 0.006


select a2, a3
from Ta
where a2 = 200


--- b. Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan. Create a nonclustered index that can speed up the query. Examine the execution plan again.

select *
from Tb
where b2 = 100


drop index index2 on Tb
create nonclustered index index2 on Tb(b2)

--- Before nonclustered index: clustered index scan; Cost: 0.03
--- After nonclustered index: nonclustered index seek; Cost: 0.003




--- c. Create a view that joins at least 2 tables. Check whether existing indexes are helpful; if not, reassess existing indexes / examine the cardinality of the tables.

create or alter view something as
	select A.aid, B.bid, C.cid
	from Tc C inner join Ta A on A.aid = C.aid
	inner join Tb B on B.bid = C.bid
	where B.b2 > 100 and A.a3 < 200

select *
from something


drop index index3 on Ta
create nonclustered index index3 on Ta(a3)

drop index index4 on Tc
create nonclustered index index4 on Tc(aid, bid)

--- Cost without indexes: 0.41
--- Cost with indexes: 0.39