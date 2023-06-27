begin transaction
update Teams set teamLocation = 'Deadlock 2'
where teamID = 'G2'

update Fan set fanName = 'Deadlock 2'
where fanID = 1

commit transaction