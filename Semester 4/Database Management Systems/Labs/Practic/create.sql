create table SemesterProject(
	id int primary key,
	name varchar(101)
);

create table ProjectTeam(
	id int primary key,
	name varchar(101),
	nr int,
	spid int foreign key references SemesterProject(id)
);

create table StudentMember(
	id int primary key,
	name varchar(101),
	role varchar(101),
	ptid int foreign key references ProjectTeam(id)
);


create table Task(
	id int primary key,
	descr varchar(101),
	smid int foreign key references StudentMember(id)
);


create table SoftwareTool(
	id int primary key,
	name varchar(101),
	descr varchar(101)
);

create table License(
	pjid int foreign key references ProjectTeam(id),
	stid int foreign key references SoftwareTool(id),
	avlb int
);

