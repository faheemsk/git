﻿

/*
CREATE TYPE VULN_NMs AS TABLE (VULN_NM NVARCHAR(255));

DECLARE @VULN_NM AS VULN_NMs

INSERT @VULN_NM(VULN_NM)
SELECT DISTINCT VULN_NM FROM [ABC5].[CLNT_VULN_INSTC]
UNION ALL
SELECT DISTINCT VULN_NM FROM [BAY4].[CLNT_VULN_INSTC]
UNION ALL
SELECT DISTINCT VULN_NM FROM MAX2.[CLNT_VULN_INSTC]

SELECT * FROM @VULN_NM

EXEC [dbo].[MERGE_VULN_CATGY_MAP] @VULN_NM, 0, 0
*/


CREATE PROCEDURE [dbo].[MERGE_VULN_CATGY_MAP]
	@VULN_NM AS VULN_NMs READONLY,
	@SRC_COUNTS  INT OUTPUT,
	@INSERT_COUNTS INT OUTPUT
AS
BEGIN

	SET NOCOUNT ON;

	DECLARE
		@ROWS INT = 0
		,@TARGETROWS INT = 0			
		,@MESSAGE NVARCHAR(4000)
		,@DEBUG INT = 0
		,@COUNT INT = 1;
	DECLARE @SummaryOfChanges table (Change VARCHAR(20));

	
	IF OBJECT_ID('tempdb..#VULN_CATGY_MAP') IS NOT NULL
	DROP TABLE #VULN_CATGY_MAP;

	CREATE TABLE #VULN_CATGY_MAP(
		[VULN_NM] [varchar](150) NULL,
		[VULN_CATGY_CD] [varchar](10) NULL
	)

	
	/*MAPPING TABLE BASED ON BUSINESS RULES*/	 
	INSERT #VULN_CATGY_MAP
	SELECT DISTINCT LTRIM(RTRIM(VULN_NM)) AS [VULN_NM], NULL AS [VULN_CATGY_CD]
	FROM @VULN_NM
	
	SELECT @SRC_COUNTS = COUNT(*) FROM #VULN_CATGY_MAP
	PRINT N'Message: TOTAL ' + CAST(@SRC_COUNTS AS VARCHAR) + N' RECORDS FROM SOURCE.'

	-- SET XACT_ABORT ON will cause the transaction to be uncommittable when the constraint violation occurs. 
	SET XACT_ABORT ON;

	BEGIN TRY
	
		BEGIN TRANSACTION VULN_MAP WITH MARK N'OS NAME INSERT DATA'

		;WITH _target AS 
		(

			SELECT [VULN_NM]
			  ,[VULN_CATGY_CD]
			  ,[CREAT_DT]
			  ,[CREAT_USER_ID]
			FROM [dbo].[VULN]
		)
		, _source AS 
		(
			SELECT [VULN_NM], [VULN_CATGY_CD]
			FROM #VULN_CATGY_MAP
		)

		MERGE INTO _target
		USING _source ON (
			_target.VULN_NM=_source.VULN_NM
			--AND _target.VULN_CATGY_CD=_source.VULN_CATGY_CD
			)
		WHEN NOT MATCHED THEN INSERT (
			[VULN_NM]
			,[VULN_CATGY_CD]
			,[CREAT_DT]
			,[CREAT_USER_ID]
		)			
		VALUES (
			_source.[VULN_NM]
			,_source.[VULN_CATGY_CD]
			,GETDATE() 
			,'2' 
		)

		OUTPUT $ACTION INTO @SummaryOfChanges
		OPTION(RECOMPILE);

		SELECT @INSERT_COUNTS = @@ROWCOUNT
		PRINT N'Message: Total ' + CAST(@INSERT_COUNTS AS VARCHAR) + N' VULN INSERTED.'	

		IF (XACT_STATE()) = 1
		BEGIN
			PRINT N'The transaction is committable. Committing transaction.'
			COMMIT TRANSACTION VULN_MAP;
		END;

	END TRY

        
	BEGIN CATCH

		IF (XACT_STATE()) = -1
		BEGIN
			PRINT N'The transaction is in an uncommittable state. Rolling back transaction.'
			ROLLBACK TRANSACTION VULN_MAP
		END;
			
		DECLARE @ErrorNumber INT = ERROR_NUMBER();
		DECLARE @ErrorLine INT = ERROR_LINE();
		DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
		DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
		DECLARE @ErrorState INT = ERROR_STATE();

		PRINT 'Actual error number: ' + CAST(@ErrorNumber AS VARCHAR(10));
		PRINT 'Actual line number: ' + CAST(@ErrorLine AS VARCHAR(10));

		RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
	END CATCH

END