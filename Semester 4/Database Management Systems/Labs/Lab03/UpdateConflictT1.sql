BEGIN TRAN
UPDATE Fan
SET fanName = 'Updated by Transaction 1'
WHERE fanID = 11;
WAITFOR DELAY '00:00:05';


--SELECT * FROM Fan;