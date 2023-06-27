Begin Transaction
Select * from Fan
where fanID between 1 and 3

waitfor delay '00:00:10'

Select * from Fan
where fanID between 1 and 3
commit transaction