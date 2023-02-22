create table Eventss(
	event_id int primary key identity(1, 1),
	namee varchar(101) unique,
	datee date,
	descriptionn varchar(101),
	country_name varchar(101) foreign key references Countries(namee)
)

drop table Eventss

create table Countries(
	country_id int primary key identity(1, 1),
	namee varchar(101) unique,
	continent_id int foreign key references Continents(continent_id)
)

create table Continents(
	continent_id int primary key identity(1, 1),
	namee varchar(101) unique
)

create table Participants(
	participant_id int primary key identity(1, 1),
	namee varchar(101),
	dob date,
	nationality varchar(101)
)

create table Tickets(
	ticket_id int primary key identity(1, 1)
)

create table ParticipantAtEvent(
	participant_id int foreign key references Participants(participant_id),
	ticket_id int foreign key references Tickets(ticket_id)
)


create procedure createEvent(@date date, @name varchar(101), @desc varchar(101), @country varchar(101) = 'Romania')
as
BEGIN
	if exists (SELECT 1 FROM Eventss WHERE namee = @name)
	BEGIN
        RAISERROR ('An event already exists', 16, 1)
        RETURN
    END
	insert into Eventss (datee, namee, descriptionn, country_name)
	values (@date, @name, @desc, @country)
END


CREATE VIEW sortEvents AS
	select top 100 percent namee, abs(cast(datediff(day, GETDATE(), datee) as int)) as daysBetween
	from Eventss
	where year(datee) = 2023
	order by abs(cast(datediff(day, '2023/05/30', datee) as int)) asc



CREATE FUNCTION eventsCountry (@N int)
RETURNS TABLE
AS
RETURN
	SELECT C.namee, COUNT(E.event_id) AS CParticip
	from Countries C
	inner join Eventss E
	on C.namee = E.country_name
	where E.datee >= '2020/01/01'
	group by C.namee
	having COUNT(E.event_id) >= @N



