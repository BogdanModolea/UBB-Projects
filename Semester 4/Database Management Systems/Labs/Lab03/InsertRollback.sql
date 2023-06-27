--Song, Playlist, Song_in_playlist
--Fan, Team, FanOf
CREATE OR ALTER PROCEDURE addRowFan @fanName VARCHAR(50), @dob DATE AS
BEGIN
	SET NOCOUNT ON
	DECLARE @maxId INT
	SET @maxId = 1
	SELECT TOP 1 @maxId = fanID + 1 FROM Fan ORDER BY fanID DESC
	DECLARE @error VARCHAR(max)
	SET @error = ''
	IF(@fanName IS NULL)
	BEGIN
		SET @error = 'Fan name must be non null'
		RAISERROR('Fan name must be non null', 16, 1);
	END
	IF(@dob IS NULL)
	BEGIN
		SET @error = 'Fan date of birth must not be null'
		RAISERROR('Fan date of birth must not be null', 16, 1);
	END
	INSERT INTO Fan(fanID, fanName, dob)
	VALUES (@maxId, @fanName, @dob)
	EXEC sp_log_changes @fanName, 'Add row to fan', @error
END


GO
CREATE OR ALTER PROCEDURE addRowTeam @teamName VARCHAR(101), @teamLocation VARCHAR(101), @teamOwner VARCHAR(101) AS
BEGIN
	SET NOCOUNT ON
	DECLARE @error VARCHAR(max)
	SET @error = ''
	IF(@teamName IS NULL)
	BEGIN
		SET @error = 'Team name must be non null'
		RAISERROR('Team name must be non null', 16, 1);
	END
	IF(@teamLocation IS NULL)
	BEGIN
		SET @error = 'Team location must be non null'
		RAISERROR('Team location must be non null', 16, 1);
	END
	IF(@teamOwner IS NULL)
	BEGIN
		SET @error = 'Team owner must be non null'
		RAISERROR('Team owner must be non null', 16, 1);
	END
	INSERT INTO Teams(teamID, teamLocation, teamOwner) 
	VALUES (@teamName, @teamLocation, @teamOwner)
	EXEC sp_log_changes  @teamName, 'Add row to teams', @error
END

GO
CREATE OR ALTER PROCEDURE addRowFanToTeam @fanName VARCHAR(101), @teamName VARCHAR(101) AS
BEGIN
	SET NOCOUNT ON
	DECLARE @error VARCHAR(max)
	SET @error = ''
	IF(@fanName IS NULL)
	BEGIN
		SET @error = 'Fan name must be non null'
		RAISERROR(@error, 16, 1);
	END
	IF(@teamName IS NULL)
	BEGIN
		SET @error = 'Team name must be non null'
		RAISERROR(@error, 16, 1);
	END
	DECLARE @fanID INT
	SET @fanID = (SELECT fanID FROM Fan WHERE fanName = @fanName)
	IF(@fanID IS NULL)
	BEGIN
		SET @error = 'No fan with the given name'
		RAISERROR(@error, 16, 1);
	END
	INSERT INTO FanOf VALUES (@fanID, @teamName)
	DECLARE @newData VARCHAR(350)
	SET @newData = @fanName + ' ' + @teamName
	EXEC sp_log_changes @newData, 'Connect fan to team', @error
END

EXEC addRowFan 'Bob', '2003-05-05'
EXEC addRowTeam 'TH', 'Spain', 'Ibai'
EXEC addRowFanToTeam 'Bob', 'TH'

GO
CREATE OR ALTER PROCEDURE successfulAddRollback AS
BEGIN
	BEGIN TRAN
	BEGIN TRY 
		EXEC addRowFan 'Bob', '2003-05-05'
		EXEC addRowTeam 'TH', 'Spain', 'Ibai'
		EXEC addRowFanToTeam 'Bob', 'TH'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		EXEC sp_log_changes '', 'rolledback all data', ''
		RETURN
	END CATCH
	COMMIT TRAN
END

GO 
CREATE OR ALTER PROCEDURE failAddRollback AS
BEGIN
	BEGIN TRAN
	BEGIN TRY
		EXEC addRowFan 'Bob', '2003-05-05'
		EXEC addRowTeam 'TH', 'Spain', 'Ibai'
		EXEC addRowFanToTeam 'Bo', 'TH'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		EXEC sp_log_changes '', 'rolledback all data', ''
		RETURN
	END CATCH
	COMMIT TRAN
END

EXEC successfulAddRollback
EXEC failAddRollback

SELECT * FROM Fan
SELECT * FROM Teams
SELECT * FROM FanOf
SELECT * FROM LogChanges
DELETE FROM FanOf
DELETE FROM Teams
DELETE FROM Fan
DELETE FROM LogChanges