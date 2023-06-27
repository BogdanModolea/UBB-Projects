begin transaction
update Fan set fanName = 'Deadlock 1'
where fanID = 1

update Teams set teamLocation = 'Deadlock 1'
where teamID = 'G2'

commit transaction

select * from Fan;
select * from Teams;

insert into Fan values(1, 'Bobo', '2002-05-05')
insert into Teams values('G2', 'Spain', 'Carlos')

--update Teams set teamLocation = 'Spain'
--where teamID = 'G2'

--update Fan set fanName = 'Bobo'
--where fanID = 1