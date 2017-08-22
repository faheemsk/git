CREATE PROCEDURE [dbo].[CreateSchema]
(
 @SchemaName VARCHAR(50),
 @ORG_KEY	 INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

DECLARE @Query VARCHAR(max)
DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)
DECLARE @Query3 VARCHAR(max)
--declare @DatabaseName	sysname set @DatabaseName	= 'IRMaaSQCRole'
--declare @SchemaName		sysname set @SchemaName		= 'Test'
--declare @UserName		sysname set @UserName		= 'dbo'
--declare @myrole			sysname set @myrole			= 'myrole'
--declare @TableName		sysname set @TableName		= 'Test'

/*
set @Query1='CREATE '+ @myrole  + ' AUTHORIZATION '+ @UserName + ';'
PRINT(@query1)
-- EXEC (@Query1) 
-- EXEC sp_addrolemember @myrole, @UserName;


set @Query2='GRANT ALTER, CONTROL, DELETE, EXECUTE, INSERT, REFERENCES, SELECT, UPDATE, VIEW 
DEFINITION ON SCHEMA::' + @SchemaName + 'TO' + @myrole + ';'
EXEC (@Query2)

GRANT CREATE TABLE, CREATE PROCEDURE, CREATE FUNCTION, CREATE VIEW TO myrole;
*/
set @Query='
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = N'+QuoteName(@SchemaName,'''')+')
exec (''CREATE SCHEMA '+QuoteName(@SchemaName)+''')'
EXECUTE(@Query)
-- PRINT @Query
 UPDATE ORG SET ORG_SCHM = @SchemaName WHERE ORG_KEY = @ORG_KEY

 SELECT @@ROWCOUNT RetVal;

 -- PRINT  (@query2)
-- SELECT * FROM   sys.schemas
-- DROP SCHEMA test 
-- DROP TABLE test.test1
END TRY

BEGIN CATCH

    DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();
    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();

    PRINT 'Actual error number: ' + CAST(@ErrorNumber AS VARCHAR(10));
    PRINT 'Actual line number: ' + CAST(@ErrorLine AS VARCHAR(10));

    RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
  END CATCH
-- COMMIT TRANSACTION
END;


