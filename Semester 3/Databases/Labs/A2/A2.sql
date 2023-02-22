insert into CasterDetails values('Caedrel', 'Marc', '1996-02-19')
insert into CasterDetails values('Vedius', 'Andrew', '1993-01-04')
insert into CasterDetails values('Medic', 'Aaron', '1991-06-17')
insert into CasterDetails values('Sjokz', 'Eefje', '1987-06-16')
insert into CasterDetails values('LS', 'Nick', '1993-09-15')
insert into CasterDetails values('Wolf', 'Wolf', '1990-06-22')
insert into CasterDetails values('Kobe', 'Sam', '1983-06-29')

insert into Teams values('G2', 'Spain', 'ocelot')
insert into Teams values('FNC', 'UK', 'Sam')
insert into Teams values('RGE', 'USA', 'Ibai')
insert into Teams values('KC', 'France', 'Kameto')
insert into Teams values('T1', 'Korea', 'Joe')
insert into Teams values('MCD', 'World', 'Roland')
insert into Teams values('ALL', 'Sweden', 'Alex')
insert into Teams values('XL', 'UK', 'Wouter')

insert into Players values('BrokenBlade', 'Sergen Celik', '2000-01-19', 'G2')
insert into Players values('Jankos', 'Marcin Jankovski', '1995-07-23', 'G2')
insert into Players values('caPs', 'Rasmus Winther', '1999-11-17', 'G2')
insert into Players values('Flakked', 'Victor Tortosa', '2001-04-25', 'G2')
insert into Players values('Targamas', 'Raphael Crabbe', '2000-06-30', 'G2')

insert into Players values('Wunder', 'Martin Hansen', '1998-11-09', 'FNC')
insert into Players values('Razork', 'Ivan Diaz', '2000-10-07', 'FNC')
insert into Players values('Humanoid', 'Marek Brazda', '2000-03-14', 'FNC')
insert into Players values('Upset', 'Elias Lipp', '1999-12-16', 'FNC')
insert into Players values('Hylissang', 'Zdravets Galabov', '1995-04-30', 'FNC')

insert into Players values('Odoamne', 'Andrei Pascu', '1995-01-18', 'RGE')
insert into Players values('Marlang', 'Kim Geun-seong', '2000-02-09', 'RGE')
insert into Players values('Larssen', 'Emil Larsson', '2000-03-30', 'RGE')
insert into Players values('Comp', 'Markos Stamkopoulos', '2001-12-20', 'RGE')
insert into Players values('Trymbi', 'Adrian Trybus', '2000-10-20', 'RGE')

insert into Players values('Cabochard', 'Lucas Meslet', '1997-04-15', 'KC')
insert into Players values('113', 'Dogukan Balci', '2004-08-12', 'KC')
insert into Players values('Saken', 'Lucas Fayard', '1998-11-05', 'KC')
insert into Players values('Rekkles', 'Martin Larsson', '1996-09-20', 'KC')
insert into Players values('Hantera', 'Jules Bourgeois', '1999-02-12', 'KC')

insert into Players values('Zeus', 'Choi Woo-je', '2004-01-31', 'T1')
insert into Players values('Oner', 'Mun Hyeon-jun', '2002-12-24', 'T1')
insert into Players values('Faker', 'Lee Sang hyeok', '1996-05-07', 'T1')
insert into Players values('Gumayusi', 'Lee Min-hyeong', '2002-02-06', 'T1')
insert into Players values('Keria', 'Ryu Min-seok', '2002-10-14', 'T1')
insert into Players values('Asper', 'Kim Tae-gi', '2000-01-12', 'T1')

insert into Players values('Finn', 'Finn Wiestal', '1999-06-03', 'XL')
insert into Players values('Markoon', 'Mark van Woensel', '2002-06-28', 'XL')
insert into Players values('nukeduck', 'Erlend Vatevik Holm', '1996-05-21', 'XL')
insert into Players values('Patrik', 'Patrik Jiru', '2000-04-07', 'XL')
insert into Players values('Mikyx', 'Mihael Mehle', '1998-11-02', 'XL')
insert into Players values('Caedrel', 'Marc Robert Lamont', '1996-03-19', 'XL')


insert into Players values('Nemesis', 'Tim Lipovsek', '1999-07-26', 'GENG')
insert into Players values('Nemesis', 'Tim Lipovsek', '1999-07-26', 'FNC')
--update
update Players
set teamID = 'MCD'
where ign = 'Nemesis'

insert into Players values('brTT', 'Felipe Goncalves', '1991-02-19', 'MCD')

insert into Fan values(1, 'Bogdan', '2002-05-30')
insert into Fan values(2, 'Fabian', '2002-06-29')
insert into Fan values(3, 'Ionut', '2003-01-10')
insert into Fan values(4, 'Edy', '2002-04-13')
insert into Fan values(5, 'Dorel', '2000-09-12')
insert into Fan values(6, 'Stefan', '2002-03-25')
insert into Fan values(7, 'Robert', '2002-03-25')
insert into Fan values(8, 'Leo', '2002-04-14')


insert into FanOf values(1, 'RGE')
insert into FanOf values(2, 'G2')
insert into FanOf values(3, 'MCD')
insert into FanOf values(4, 'FNC')
insert into FanOf values(5, 'ALL')
insert into FanOf values(6, 'T1')
insert into FanOf values(8, 'G2')
insert into FanOf values(6, 'FNC')


insert into Leagues values('LEC', 'Europe', 'Germany')
insert into Leagues values('LFL', 'Europe', 'France')
insert into Leagues values('LCK', 'Asia', 'Korea')
insert into Leagues values('EUM', 'Europe', 'Spain')
insert into Leagues values('MSI', 'Asia', 'Korea')

insert into Leagues values('LCS', 'America', 'USA')
--delete
delete from Leagues
where leagueID = 'LCS'


insert into Sponsors values(1, 'BMW', '1916-03-07', 'G2')
insert into Sponsors values(2, 'Kia', '1944-06-09', 'RGE')
insert into Sponsors values(3, 'AMD', '1969-05-01', 'FNC')
insert into Sponsors values(4, 'Chupa Chups', '1958-01-01', 'KC')
insert into Sponsors values(5, 'SK Telecom', '1984-04-20', 'T1')


insert into CurrentSeason values('LEC', 'G2')
insert into CurrentSeason values('LEC', 'FNC')
insert into CurrentSeason values('LEC', 'RGE')
insert into CurrentSeason values('LFL', 'KC')
insert into CurrentSeason values('LCK', 'T1')
insert into CurrentSeason values('EUM', 'KC')
insert into CurrentSeason values('MSI', 'G2')
insert into CurrentSeason values('MSI', 'T1')

insert into CurrentSeason values('LEC', 'KC')
--delete
delete from CurrentSeason
where teamID = 'KC' and leagueID = 'LEC'


insert into Casters values('Caedrel', 'LEC')
insert into Casters values('Vedius', 'LEC')
insert into Casters values('Medic', 'EUM')
insert into Casters values('LS', 'LCK')

insert into Casters values('Sjokz', 'LCK')
--update
update Casters
set leagueID = 'LEC'
where ign = 'Sjokz'



insert into PastWinners values('LEC', 2022, 'RGE')
insert into PastWinners values('LEC', 2020, 'G2')
insert into PastWinners values('LEC', 2019, 'G2')
insert into PastWinners values('LCK', 2022, 'T1')
insert into PastWinners values('LEC', 2013, 'ALL')

insert into PastWinners values('LEC', 2018, 'G2')

--update
update PastWinners
set teamID = 'FNC'
where leagueID = 'LEC' and leagueYear = 2018 and teamID	= 'G2'


--- a ---

select fanName
from Fan
union
select casterName
from CasterDetails

select ign
from Players
where teamID = 'G2' or teamID = 'T1'



--- b ---

select teamID
from CurrentSeason
intersect
select teamID
from PastWinners

select ign
from Players
where teamID in (select teamID
				 from Sponsors)



--- c ---

select teamID
from Teams
except
select TeamID
from CurrentSeason
where leagueID = 'LEC'

select teamID
from Teams
where teamID not in (select teamID
					 from Sponsors)



--- d ---

select distinct F.fanName
from Fan F
inner join FanOf FO on FO.fanID = F.fanID
inner join CurrentSeason CS on CS.teamID = FO.teamID
inner join PastWinners PW on PW.leagueID = CS.leagueID

select distinct T.teamID, PW.leagueYear
from Teams T left join PastWinners PW on T.teamID = PW.teamID
where PW.leagueID = 'LEC'
order by PW.leagueYear

select S.companyName, T.teamID
from Sponsors S right join Teams T on S.teamID = T.teamID
order by S.companyName

select distinct F.fanName, T.teamID, T.teamOwner
from Fan F 
full join FanOf FO on F.fanID = FO.fanID
full join Teams T on T.teamID = FO.teamID



--- e ---

select S.companyName
from Sponsors S
where S.teamID in ( 
	select CS.teamID
	from CurrentSeason CS
	where CS.teamID in (
		select PW.teamID
		from PastWinners PW
	)
)

select CD.casterName
from CasterDetails CD
where CD.ign in (
	select C.ign
	from Casters C
	where C.leagueID in (
		select CS.leagueID
		from CurrentSeason CS
	)
)



--- f ---

select T.teamID, T.teamLocation
from Teams T
where exists(
	select *
	from FanOf FO
	where FO.teamID = T.teamID
)

select T.teamID, T.teamOwner
from Teams T
where exists(
	select *
	from Players P
	where P.teamID = T.teamID
)



--- g ---

select t.teamID
from (
	select P.teamID, count(P.teamID) as NoPlayers
	from Players P 
	group by P.teamID
)t
where t.NoPlayers > 5


select t.ign
from (
	select C.ign, C.leagueID, CD.dob
	from Casters C inner join CasterDetails CD on CD.ign = C.ign
	where C.leagueID = 'LEC'
)t
where year(t.dob) >= 1990



--- h ---

select year(dob), count(*) as NoPlayers
from Players
group by year(dob)

select P.teamID, count(P.teamID) as NoPlayers
from Players P 
group by P.teamID
having count(P.teamID) >= 5

select T.teamID, count(*) as MaxFans
from Teams T inner join FanOf FO on T.teamID = FO.teamID
group by T.teamID
having count(*) = (
	select max(t.NoFans)
	from (
		select count(*) as NoFans
		from Teams T inner join FanOf FO on T.teamID = FO.teamID
		group by T.teamID
	)t
)


select T.teamID, count(S.teamID) as MinSponsors
from Teams T left join Sponsors S on S.teamID = T.teamID
group by T.teamID
having T.teamID in (
	select PW.teamID
	from PastWinners PW
) 
and count(S.teamID) = (
	select min(m.NoSponsors)
	from (
		select count(S.teamID) as NoSponsors
		from Teams T left join Sponsors S on S.teamID = T.teamID
		group by T.teamID
		having T.teamID in (
			select PW.teamID
			from PastWinners PW
		)
	)m
)



--- i ---

select CD.ign
from CasterDetails CD
where CD.dob < any(
	select S.sponsorYear
	from Sponsors S
)

select CD.ign
from CasterDetails CD
where CD.dob < (
	select max(S.sponsorYear)
	from Sponsors S
)

select F.fanName
from Fan F
where F.dob > all(
	select P.dob
	from Players P
	where P.teamID = 'RGE'
)

select F.fanName
from Fan F
where F.dob > (
	select max(P.dob)
	from Players P
	where P.teamID = 'RGE'
)

select T.teamID, T.teamLocation
from Teams T
where T.teamLocation <> all(
	select L.leagueLocation
	from Leagues L
	where L.leagueID in (
		select CS.leagueID
		from CurrentSeason CS
		where CS.teamID = T.teamID
	)
)
and T.teamID in(
	select CS.teamID
	from CurrentSeason CS
)

select T.teamID, T.teamLocation
from Teams T
where T.teamLocation not in(
	select L.leagueLocation
	from Leagues L inner join CurrentSeason CS on L.leagueID = CS.leagueID 
	where CS.teamID = T.teamID
)
and T.teamID in(
	select CS.teamID
	from CurrentSeason CS
)

select P.ign
from Players P
where P.ign = any(
	select CD.ign
	from CasterDetails CD
)

select P.ign
from Players P
where P.ign in(
	select CD.ign
	from CasterDetails CD
)



--- Must use ---

select top 5 *
from Players
order by dob desc

select top 3 P.ign, datediff(year, P.dob, cast(getdate() as date)) as age
from Players P
where datediff(year, P.dob, cast(getdate() as date)) + 5 < all(
	select datediff(year, CD.dob, cast(getdate() as date))
	from CasterDetails CD
)
order by age, P.ign


select CD.ign, datediff(year, CD.dob, cast(getdate() as date)) as age
from CasterDetails CD
where datediff(year, CD.dob, cast(getdate() as date)) * 3.5 < any(
	select cast(year(getdate()) as int) - cast(year(S.sponsorYear) as int)
	from Sponsors S
)

select P.ign, datediff(year, P.dob, cast(getdate() as date)) as age
from Players P
where datediff(year, P.dob, cast(getdate() as date)) / 1.5 > any(
	select datediff(year, PP.dob, cast(getdate() as date))
	from Players PP
)

select *
from CasterDetails

select *
from Casters

select *
from CurrentSeason

select *
from Fan

select *
from FanOf

select *
from Leagues

select *
from PastWinners

select *
from Players

select *
from Sponsors

select *
from Teams

delete from CasterDetails
delete from Casters
delete from CurrentSeason
delete from Fan
delete from FanOf
delete from Leagues
delete from PastWinners
delete from Players
delete from Sponsors
delete from Teams