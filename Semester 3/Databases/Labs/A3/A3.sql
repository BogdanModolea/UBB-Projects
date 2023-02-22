use Esports


--- a ---

create procedure setMaxAttendanceFromTeamsTinyint as
	alter table Teams alter column maxAttendance tinyint

create procedure setMaxAttendanceFromTeamsInt as
	alter table Teams alter column maxAttendance int



--- b ---

create procedure addCountryToFan as
	alter table Fan add countryBirth varchar(50)

create procedure removeCountryFromFan as
	alter table Fan drop column countryBirth


--- c ---

create procedure addDefaultToTeamLocationFromTeams as
	alter table Teams add constraint DEFAULTLOC default('Germany') for teamLocation

create procedure removeDefaultFromTeamLocationFromTeams as
	alter table Teams drop constraint DEFAULTLOC


--- d ---

create procedure addPrimaryKeyToEmployee as
	alter table Employee drop constraint EMPLOYEEPK
	alter table Employee add constraint EMPLOYEEPK primary key(eid, role)

create procedure removePrimaryKeyToEmployee as
	alter table Employee drop constraint EMPLOYEEPK
	alter table Employee add constraint EMPLOYEEPK primary key(eid)



--- e ---

create procedure newCandidateKeyFromPlayers as
	alter table Players add constraint PLAYERSCK unique(playerName, dob)

create procedure dropCandidateKeyFromPlayers as
	alter table Players drop constraint PLAYERSCK



--- f ---

create procedure newForeignKeyFromEmployee as
	alter table Employee add constraint EMPLOYEEFK foreign key(teamID) references Teams(teamID)

create procedure dropForeignKeyFromEmployee as
	alter table Employee drop constraint EMPLOYEEFK



--- g ---

create procedure addEmployee as
	create table Employee(
		eid int not null,
		salary int,
		role varchar(50) not null,
		teamID varchar(50) not null,
		constraint EMPLOYEEPK primary key(eid)
	)

create procedure dropEmployee as
	drop table Employee



create table versionTable (
    version int
)
insert into versionTable values (1) 

create table proceduresTable (
    fromVersion int,
    toVersion int,
    primary key (fromVersion, toVersion),
    nameProcedure varchar(max)
)

insert into proceduresTable values(1, 2, 'setMaxAttendanceFromTeamsTinyint')
insert into proceduresTable values(2, 1, 'setMaxAttendanceFromTeamsInt')
insert into proceduresTable values(2, 3, 'addCountryToFan')
insert into proceduresTable values(3, 2, 'removeCountryFromFan')
insert into proceduresTable values(3, 4, 'addDefaultToTeamLocationFromTeams')
insert into proceduresTable values(4, 3, 'removeDefaultFromTeamLocationFromTeams')
insert into proceduresTable values(4, 5, 'addEmployee')
insert into proceduresTable values(5, 4, 'dropEmployee')
insert into proceduresTable values(5, 6, 'addPrimaryKeyToEmployee')
insert into proceduresTable values(6, 5, 'removePrimaryKeyToEmployee')
insert into proceduresTable values(6, 7, 'newCandidateKeyFromPlayers')
insert into proceduresTable values(7, 6, 'dropCandidateKeyFromPlayers')
insert into proceduresTable values(7, 8, 'newForeignKeyFromEmployee')
insert into proceduresTable values(8, 7, 'dropForeignKeyFromEmployee')


create procedure changeVersion(@newVersion int) as
	declare @current int
	declare @proc varchar(max)
	select @current = version from versionTable

	if @newVersion > (select max(toVersion) from proceduresTable)
		raiserror('Bad version', 10, 1)

	while @current > @newVersion begin
		select @proc = nameProcedure from proceduresTable where fromVersion = @current and toVersion = @current - 1
		exec(@proc)
		set @current = @current - 1
	end

	while @current < @newVersion begin
		select @proc = nameProcedure from proceduresTable where fromVersion = @current and toVersion = @current + 1
		exec(@proc)
		set @current = @current + 1
	end

	update versionTable set version = @newVersion


execute changeVersion 1

select *
from versionTable










--------------------------------



update versionTable set version = 1

select *
from Fan

select ROUTINE_DEFINITION
from INFORMATION_SCHEMA.ROUTINES
where ROUTINE_TYPE = 'PROCEDURE'

drop procedure [addEmployee]
alter table Teams drop constraint DEFAULTLOC

--update Teams
--set maxAttendance = 0

select *
from Teams