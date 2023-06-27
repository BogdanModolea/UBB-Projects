set transaction isolation level SNAPSHOT

begin tran
select Name
from Project
where ProjectID = 7


waitfor delay '00:00:05'


select Name
From Project
where ProjectID = 7

UPDATE Project
SET Name = 'DM'
Where ProjectID = 7

COMMIT TRAN

--update Project Set Name = 'SA' where ProjectID = 7