--set transaction isolation level read uncommitted --solution
set transaction isolation level snapshot

begin tran
update SemesterProject set Name='name3' where id=1
select * from SemesterProject
commit tran