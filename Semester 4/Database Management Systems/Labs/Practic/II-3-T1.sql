begin tran
update SemesterProject set Name='name2' where id=1

waitfor delay '00:00:10'

select * from SemesterProject
commit tran