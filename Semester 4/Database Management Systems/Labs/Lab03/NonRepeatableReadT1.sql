begin transaction
select dob from Fan
where fanID = 1

waitfor delay '00:00:10'

select dob from Fan
where fanID = 1
commit transaction