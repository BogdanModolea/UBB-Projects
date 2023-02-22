create table Teams(
	teamID varchar(50) primary key,
	teamLocation varchar(50),
	teamOwner varchar(50),
	unique(teamID)
)

create table Players(		/* One to many, because one team has more players */
	ign varchar(50) primary key,
	playerName varchar(50),
	dob date,
	teamID varchar(50) foreign key references Teams(teamID) default 'MCD',
	unique(ign)
)

create table Fan(
	fanID int primary key,
	fanName varchar(50),
	dob date
)

create table FanOf(	/* Many to many, because more fans has more favourite teams */
	fanID int references Fan(fanID),
	teamID varchar(50) foreign key references Teams(teamID)
)

create table Sponsors( /* One to many, because one team has more sponsors */
	sponsorID int primary key,
	companyName varchar(50),
	sponsorYear date,
	teamID varchar(50) foreign key references Teams(teamID)
)

create table Leagues(
	leagueID varchar(50) primary key,
	continent varchar(50) not null,
	leagueLocation varchar(50),
	unique(leagueID)
)

create table CasterDetails(
	ign varchar(50) primary key,
	casterName varchar(50),
	dob date
	--leagueID varchar(50) foreign key references Leagues(leagueID)
)


create table Casters(	/* many to many, because more leagues has more casters */
	ign varchar(50) foreign key references CasterDetails(ign),
	leagueID varchar(50) foreign key references Leagues(leagueID)
)


create table CurrentSeason(
	leagueID varchar(50) foreign key references Leagues(leagueID),
	teamID varchar(50) foreign key references Teams(teamID)
)

create table PastWinners(
	leagueID varchar(50) foreign key references Leagues(leagueID),
	leagueYear int,
	teamID varchar(50) foreign key references Teams(teamID)
)

