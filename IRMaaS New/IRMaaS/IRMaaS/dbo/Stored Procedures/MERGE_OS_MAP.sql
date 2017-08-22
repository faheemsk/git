

/*
--CREATE TYPE OS_NMs AS TABLE (OS_NM NVARCHAR(150));

DECLARE @OS_NM AS OS_NMs

INSERT @OS_NM(OS_NM)
SELECT 'Cisco Aironet 1200 Series AP  '
UNION ALL
SELECT 'mac os  '
UNION ALL
SELECT 'TEST vmware TEST  '
UNION ALL
SELECT '  TEST mac os TEST'

--SELECT * FROM @OS_NM

EXEC [dbo].[MERGE_OS_MAP] @OS_NM
*/


CREATE PROCEDURE [dbo].[MERGE_OS_MAP]
	@OS_NM AS OS_NMs READONLY,
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
		--,@SRC_COUNTS INT = 0
		--,@INSERT_COUNTS INT = 0
		--,@UPDATE_COUNTS INT = 0
		--,@DELETE_COUNTS INT = 0  
		,@COUNT INT = 1;
	DECLARE @SummaryOfChanges table (Change VARCHAR(20));

	
	IF OBJECT_ID('tempdb..#OS_CATGY_MAP') IS NOT NULL
	DROP TABLE #OS_CATGY_MAP;

	CREATE TABLE #OS_CATGY_MAP(
		[OS_CATGY_KEY] [int] NOT NULL,
		[OS_CATGY_NM] [varchar](150) NULL,
		[OS_NM_QUALFR] [varchar](150) NULL,
		[ROW_STS_KEY] [int] NOT NULL,
	) 

	IF OBJECT_ID('tempdb..#OS_NM_QUAL') IS NOT NULL
	DROP TABLE #OS_NM_QUAL;

	CREATE TABLE #OS_NM_QUAL(
		[OS_NM] [varchar](150) NULL,
		[OS_CATGY_KEY] [int] NOT NULL
	) 

	IF OBJECT_ID('tempdb..#OS_NM_INSERT') IS NOT NULL
	DROP TABLE #OS_NM_INSERT;

	CREATE TABLE #OS_NM_INSERT(
		[OS_NM] [varchar](150) NULL,
		[OS_CATGY_KEY] [int] NOT NULL
	) 
	
	/*MAPPING TABLE BASED ON BUSINESS RULES*/	 
	INSERT #OS_CATGY_MAP
	SELECT '1','Windows','windows','1' UNION ALL
	SELECT '2','Linux','linux','1' UNION ALL
	SELECT '2','Linux','Red Hat','1' UNION ALL
	SELECT '3','Unix','unix','1' UNION ALL
	SELECT '3','Unix','aix','1' UNION ALL
	SELECT '3','Unix','microsystems','1' UNION ALL
	SELECT '3','Unix','hp-ux','1' UNION ALL
	SELECT '3','Unix','sco opendesktop','1' UNION ALL
	SELECT '3','Unix','sco openserver','1' UNION ALL
	SELECT '3','Unix','sco release','1' UNION ALL
	SELECT '3','Unix','sco unix','1' UNION ALL
	SELECT '3','Unix','SCO','2' UNION ALL
	SELECT '4','Mac','mac os','1' UNION ALL
	SELECT '4','Mac','iOS','1' UNION ALL
	SELECT '5','VMWare','vmware','1' UNION ALL
	SELECT '6','MainFrame','ibm os','1'  UNION ALL
	SELECT '7','Other',NULL,'1' 
	

	INSERT #OS_NM_QUAL ([OS_NM], [OS_CATGY_KEY])
	SELECT DISTINCT 
		LTRIM(RTRIM(A.OS_NM)) AS OS_NM
		,B.OS_CATGY_KEY
	FROM @OS_NM A, #OS_CATGY_MAP B
	WHERE A.OS_NM LIKE '%' + B.OS_NM_QUALFR + '%'
		AND B.ROW_STS_KEY = 1

	INSERT #OS_NM_INSERT ([OS_NM], [OS_CATGY_KEY])
	SELECT 
		OS_OTH.OS_NM
		,ISNULL(OS_QUAL.OS_CATGY_KEY, (SELECT OS_CATGY_KEY FROM #OS_CATGY_MAP WHERE OS_CATGY_NM = 'Other')) AS OS_CATGY_KEY
	FROM (SELECT LTRIM(RTRIM(OS_NM)) AS OS_NM FROM @OS_NM) OS_OTH
	 LEFT JOIN #OS_NM_QUAL OS_QUAL
		ON OS_OTH.OS_NM = OS_QUAL.OS_NM 

	SELECT @SRC_COUNTS = COUNT(*) FROM #OS_NM_INSERT
	PRINT N'Message: TOTAL ' + CAST(@SRC_COUNTS AS VARCHAR) + N' RECORDS FROM SOURCE.'

	-- SET XACT_ABORT ON will cause the transaction to be uncommittable when the constraint violation occurs. 
	SET XACT_ABORT ON;

	BEGIN TRY
	
		BEGIN TRANSACTION OS_MAP WITH MARK N'OS NAME INSERT DATA'

		;WITH _target AS 
		(

			SELECT *
			FROM [dbo].[OS]
		)
		, _source AS 
		(
			SELECT *
			FROM #OS_NM_INSERT
		)

		MERGE INTO _target
		USING _source ON (
			_target.OS_NM=_source.OS_NM
			--AND _target.OS_CATGY_KEY=_source.OS_CATGY_KEY
			)
		WHEN NOT MATCHED THEN INSERT (
			[OS_NM]
			,[OS_CATGY_KEY]
			,[CREAT_DT]
			,[CREAT_USER_ID]
		)			
		VALUES (
			_source.OS_NM
			,_source.OS_CATGY_KEY
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
			COMMIT TRANSACTION OS_MAP;
		END;

	END TRY

        
	BEGIN CATCH

		IF (XACT_STATE()) = -1
		BEGIN
			PRINT N'The transaction is in an uncommittable state. Rolling back transaction.'
			ROLLBACK TRANSACTION OS_MAP
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



