


/******************************
	** Desc: 
	** Auth: GM
	** Date: 06/20/2016
	**************************
	** Change History
	**************************
	**  Date	        Author			Description	
	**  --------        --		        ------------------------------------
	**  11/29/2016		GM				Multi-tenancy changes
	**************************************/

/*

DECLARE @SRC_COUNTS INT = 0 
		,@INSERT_COUNTS INT = 0
		,@UPDATE_COUNTS INT = 0
		,@DELETE_COUNTS INT = 0;
EXEC [dbo].[MERGE_CLNT_VULN_SECUR_CTL_MT] 
	'JOH'
	,'[JOH].[CLNT_VULN_INSTC]'
	,'[JOH].[CLNT_VULN_SECUR_CTL]'
	, @SRC_COUNTS OUTPUT
	, @INSERT_COUNTS OUTPUT 
	, @UPDATE_COUNTS OUTPUT 
	, @DELETE_COUNTS OUTPUT

SELECT @SRC_COUNTS  
SELECT @INSERT_COUNTS  
SELECT @UPDATE_COUNTS  
SELECT @DELETE_COUNTS 

--## - PLEASE VERIFY
*/


CREATE PROCEDURE [dbo].[MERGE_CLNT_VULN_SECUR_CTL_MT]
	@SCHEMA_NM VARCHAR(1000),
	@MASTER_TBL_NAME VARCHAR(1000),
	@MAPPING_TBL_NAME VARCHAR(1000),
	@SRC_COUNTS  INT OUTPUT,
	@INSERT_COUNTS INT OUTPUT,
	@UPDATE_COUNTS INT OUTPUT,
	@DELETE_COUNTS INT OUTPUT
AS

BEGIN

	IF OBJECT_ID('tempdb..#CLNT_VULN_SECUR_CTL') IS NOT NULL
	DROP TABLE #CLNT_VULN_SECUR_CTL;

	CREATE TABLE #CLNT_VULN_SECUR_CTL(
		[CLNT_VULN_INSTC_KEY] [int] NOT NULL,
		[REG_CMPLN_CD] [varchar](20) NOT NULL,
		[REG_CMPLN_VER] [varchar](20) NOT NULL,
		[SECUR_CTL_CD] [varchar](20) NOT NULL
		)

	IF OBJECT_ID('tempdb..#SecurCtlData') IS NOT NULL
	DROP TABLE #SecurCtlData;

	CREATE TABLE #SecurCtlData(
		[REG_CMPLN_CD] [varchar](20) NOT NULL,
		[REG_CMPLN_VER] [varchar](20) NOT NULL,
		[SECUR_CTL_CD] [varchar](20) NOT NULL,
		[PRI_REG_CMPLN_CD] [varchar](20) NOT NULL,
		[PRI_REG_CMPLN_VER] [varchar](20) NOT NULL,
		[PRI_SECUR_CTL_CD] [varchar](20) NOT NULL
		)
	
	IF OBJECT_ID('tempdb..#OP_SummaryOfChanges') IS NOT NULL
	DROP TABLE #OP_SummaryOfChanges;

	CREATE TABLE #OP_SummaryOfChanges(
		[INSERT_COUNTS] [int] NULL,
		[UPDATE_COUNTS] [int] NULL,
		[DELETE_COUNTS] [int] NULL,
	);
	


	-- #Populate Primary and Secondary control mapping
	SELECT DISTINCT PRI_SECUR_CTL_CD, PRI_REG_CMPLN_CD, PRI_REG_CMPLN_VER
	INTO #secur_ctl_CTE
	FROM [dbo].SECUR_CTL_MAP

	INSERT #SecurCtlData([REG_CMPLN_CD], [REG_CMPLN_VER], [SECUR_CTL_CD], [PRI_REG_CMPLN_CD], [PRI_REG_CMPLN_VER], [PRI_SECUR_CTL_CD] )
	SELECT [REG_CMPLN_CD], [REG_CMPLN_VER], [SECUR_CTL_CD], [PRI_REG_CMPLN_CD], [PRI_REG_CMPLN_VER], [PRI_SECUR_CTL_CD]
	FROM
	(
		SELECT DISTINCT PRI_REG_CMPLN_CD AS REG_CMPLN_CD, PRI_REG_CMPLN_VER AS REG_CMPLN_VER, PRI_SECUR_CTL_CD AS SECUR_CTL_CD
		, [PRI_REG_CMPLN_CD], [PRI_REG_CMPLN_VER], [PRI_SECUR_CTL_CD]
		FROM #secur_ctl_CTE
		UNION 
		SELECT SEC_REG_CMPLN_CD AS REG_CMPLN_CD, SEC_REG_CMPLN_VER AS REG_CMPLN_VER, SEC_SECUR_CTL_CD AS SECUR_CTL_CD
		, secur_ctl_map.PRI_REG_CMPLN_CD, secur_ctl_map.PRI_REG_CMPLN_VER, secur_ctl_map.PRI_SECUR_CTL_CD
		FROM [dbo].SECUR_CTL_MAP secur_ctl_map
		JOIN #secur_ctl_CTE 
			ON secur_ctl_map.PRI_REG_CMPLN_CD = #secur_ctl_CTE.PRI_REG_CMPLN_CD
				AND secur_ctl_map.PRI_REG_CMPLN_VER = #secur_ctl_CTE.PRI_REG_CMPLN_VER
				AND secur_ctl_map.PRI_SECUR_CTL_CD = #secur_ctl_CTE.PRI_SECUR_CTL_CD
	) SecurCtlTemp;

	 
	-- SET XACT_ABORT ON will cause the transaction to be uncommittable when the constraint violation occurs. 
	SET XACT_ABORT ON;
	
	BEGIN TRY
		SET NOCOUNT ON

		--DECLARE @SCHEMA_NM VARCHAR(1000), @STG_TBL_NAME VARCHAR(1000), @MASTER_TBL_NAME VARCHAR(1000), @MAPPING_TBL_NAME VARCHAR(1000);;
		--SET @SCHEMA_NM = 'JOH';
		--SET @MASTER_TBL_NAME = '[JOH].[CLNT_VULN_INSTC]';
		--SET @MAPPING_TBL_NAME = '[JOH].[CLNT_VULN_SECUR_CTL]';

		DECLARE @SQL_TEMP VARCHAR(MAX), @SQL_MERGE VARCHAR(MAX);

		--BEGIN TRANSACTION MERGE_CLNT_VULN_SECUR_CTL WITH MARK N'MERGE_CLNT_VULN_SECUR_CTL to process the Vulnerability data.'
			
		SET @SQL_TEMP = '
						
		INSERT #CLNT_VULN_SECUR_CTL
		SELECT DISTINCT 
			_clientvuln.CLNT_VULN_INSTC_KEY
			,SecurCtldata.[REG_CMPLN_CD]
			,SecurCtldata.[REG_CMPLN_VER]
			,SecurCtldata.[SECUR_CTL_CD]
		FROM ' + @MASTER_TBL_NAME + ' _clientvuln WITH(NOLOCK)
		INNER JOIN [dbo].[CVE] _cve  WITH(NOLOCK)
			ON _clientvuln.[CVE_ID] = _cve.[CVE_ID]
		INNER JOIN [dbo].[CVE_SECUR_CTL_MAP] _cvescrtymap  WITH(NOLOCK)
			ON _cvescrtymap.[CVE_ID] = _cve.[CVE_ID]
			AND _cvescrtymap.ROW_STS_KEY IN (SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = ''Row Status'' and LKP_ENTY_NM = ''Active'')
		INNER JOIN #SecurCtldata SecurCtldata
			on _cvescrtymap.REG_CMPLN_CD = SecurCtldata.PRI_REG_CMPLN_CD
				and _cvescrtymap.REG_CMPLN_VER = SecurCtldata.PRI_REG_CMPLN_VER
				and _cvescrtymap.SECUR_CTL_CD = SecurCtldata.PRI_SECUR_CTL_CD
		WHERE NOT EXISTS
		(
			SELECT TOP 1 _clientvulnsecurctl.CLNT_VULN_INSTC_KEY
			FROM ' + @MAPPING_TBL_NAME + ' _clientvulnsecurctl WITH(NOLOCK)
			WHERE _clientvuln.CLNT_VULN_INSTC_KEY = _clientvulnsecurctl.CLNT_VULN_INSTC_KEY
		);'

		PRINT @SQL_TEMP;
		EXEC (@SQL_TEMP);



		SELECT @SRC_COUNTS = COUNT(*) FROM #CLNT_VULN_SECUR_CTL
		PRINT N'Message: TOTAL ' + CAST(@SRC_COUNTS AS VARCHAR) + N' RECORDS FROM SOURCE.'	

		SET @SQL_MERGE = '

		DECLARE @SummaryOfChanges table (Change VARCHAR(20));

		;WITH _target AS 
		(
			SELECT * FROM ' + @MAPPING_TBL_NAME + '
		)
		, _source AS 
		(
			SELECT * FROM #CLNT_VULN_SECUR_CTL
		)

		MERGE INTO _target
		USING _source ON (
			_target.CLNT_VULN_INSTC_KEY=_source.CLNT_VULN_INSTC_KEY
			AND _target.REG_CMPLN_CD=_source.REG_CMPLN_CD
			 AND _target.REG_CMPLN_VER=_source.REG_CMPLN_VER
			 AND _target.SECUR_CTL_CD=_source.SECUR_CTL_CD
			)
		WHEN NOT MATCHED THEN INSERT (
			[CLNT_VULN_INSTC_KEY]
           ,[REG_CMPLN_CD]
           ,[REG_CMPLN_VER]
           ,[SECUR_CTL_CD]
           ,[ROW_STS_KEY]
           ,[CREAT_DT]
           ,[CREAT_USER_ID]
           ,[UPDT_DT]
           ,[UPDT_USER_ID]
		)			
		VALUES (
			_source.CLNT_VULN_INSTC_KEY
			,_source.REG_CMPLN_CD
			,_source.REG_CMPLN_VER
			,_source.SECUR_CTL_CD
			,(SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = ''Row Status'' and LKP_ENTY_NM = ''Active'')
			,GETDATE() 
			,''2'' 
			,NULL
			,NULL
		)

		OUTPUT $ACTION INTO @SummaryOfChanges
		OPTION(RECOMPILE);

		INSERT INTO #OP_SummaryOfChanges
		VALUES(
			(SELECT COUNT(*) AS INSERT_COUNTS FROM @SummaryOfChanges WHERE Change = ''INSERT'')
			,(SELECT COUNT(*) AS UPDATE_COUNTS FROM @SummaryOfChanges WHERE Change = ''UPDATE'')
			,(SELECT COUNT(*) AS DELETE_COUNTS FROM @SummaryOfChanges WHERE Change = ''DELETE'')
		);
		
		'

		--PRINT @SQL_MERGE;
		EXEC (@SQL_MERGE);

		SELECT @INSERT_COUNTS = INSERT_COUNTS FROM #OP_SummaryOfChanges
		PRINT N'Message: Total ' + CAST(@INSERT_COUNTS AS VARCHAR) + N' VULN INSERTED.'	

		SELECT @UPDATE_COUNTS = UPDATE_COUNTS FROM #OP_SummaryOfChanges
		PRINT N'Message: TOTAL ' + CAST(@UPDATE_COUNTS AS VARCHAR) + N' VULN UPDATED.'				

		SELECT @DELETE_COUNTS = DELETE_COUNTS FROM #OP_SummaryOfChanges
		PRINT N'Message: Total ' + CAST(@DELETE_COUNTS AS VARCHAR) + N' VULN INSERTED.'	

		IF (XACT_STATE()) = 1
		BEGIN
			PRINT N'The transaction is committable. Committing transaction.'
			COMMIT TRANSACTION MERGE_CLNT_VULN_SECUR_CTL;
		END;

	END TRY

        
	BEGIN CATCH

		IF (XACT_STATE()) = -1
		BEGIN
			PRINT N'The transaction is in an uncommittable state. Rolling back transaction.'
			ROLLBACK TRANSACTION MERGE_CLNT_VULN_SECUR_CTL
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

