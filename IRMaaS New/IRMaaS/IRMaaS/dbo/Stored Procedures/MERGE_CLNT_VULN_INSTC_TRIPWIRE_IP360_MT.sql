
/******************************
	** Desc: 
	** Auth: GM
	** Date: 09/16/2016
	**************************
	** Change History
	**************************
	**  Date	        Author                  Description	
	**  --------        --					------------------------------------
	**  10/19/2016		GM					Added OWASP_TOP_10_KEY
	**  11/29/2016		GM					Multi-tenancy changes
	**  01/23/2017		GM					Vuln Category changes
	**************************************/

/*
	DECLARE @SRC_COUNTS INT = 0 
			,@INSERT_COUNTS INT = 0
			,@UPDATE_COUNTS INT = 0
			,@DELETE_COUNTS INT = 0;
	EXEC [dbo].[MERGE_CLNT_VULN_INSTC_TRIPWIRE_IP360_MT] 
		'JOH'
		,'[JOH].[TRIPWIRE_IP360_STG]'
		,'[JOH].[CLNT_VULN_INSTC]'
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

CREATE PROCEDURE [dbo].[MERGE_CLNT_VULN_INSTC_TRIPWIRE_IP360_MT]
	@SCHEMA_NM VARCHAR(1000),
	@STG_TBL_NAME VARCHAR(1000),
	@MASTER_TBL_NAME VARCHAR(1000),
	@SRC_COUNTS  INT OUTPUT,
	@INSERT_COUNTS INT OUTPUT,
	@UPDATE_COUNTS INT OUTPUT,
	@DELETE_COUNTS INT OUTPUT
AS
BEGIN
	IF OBJECT_ID('tempdb..#Vulnerability_NV') IS NOT NULL
	DROP TABLE #Vulnerability_NV;

	CREATE TABLE #Vulnerability_NV(
		[ROW_STS_KEY] [int] NOT NULL,
		[ORG_KEY] [int] NOT NULL,
		[CLNT_ENGMT_CD] [varchar](30) NOT NULL,
		[SECUR_SRVC_CD] [varchar](10) NOT NULL,
		[VULN_SRC_KEY] [int] NOT NULL,
		[VULN_INSTC_STS_CD] [varchar](3) NOT NULL,
		[VULN_SEV_CD] [varchar](3) NULL,
		[VULN_IMP_CD] [varchar](3) NULL,
		[RISK_PRBL_CD] [varchar](3) NULL,
		[RMDTN_CST_EFFRT_CD] [varchar](3) NULL,
		[VULN_CATGY_CD] [varchar](10) NULL,
		[OWASP_TOP_10_KEY] [int] NULL,
		[CVE_ID] [varchar](15) NULL,
		[OS_KEY] [int] NULL,
		[SRC_VULN_SCAN_ID] [varchar](150) NULL,
		[SRC_VULN_SCAN_STRT_DT] [datetime] NULL,
		[SRC_VULN_SCAN_END_DT] [datetime] NULL,
		[SRC_VULN_INSTC_ID] [varchar](150) NULL,
		[SRC_VULN_ID] [varchar](150) NULL,
		[VULN_NM] [varchar](255) NOT NULL,
		[VULN_DESC] [text] NULL,
		[VULN_CREAT_DT] [datetime] NOT NULL,
		[IPADR] [varchar](39) NULL,
		[PORT_NBR] [int] NULL,
		[SRC_ADVS_TXT] [varchar](1024) NULL,
		[SRC_VULN_BAS_SCOR] [decimal](10, 2) NULL,
		[VULN_BAS_SCOR] [decimal](10, 2) NULL,
		[VULN_IMP_SUB_SCOR] [decimal](10, 2) NULL,
		[VULN_EXPLT_SUB_SCOR] [decimal](10, 2) NULL,
		[VULN_TMPRL_SCOR] [decimal](10, 2) NULL,
		[VULN_ENV_SCOR] [decimal](10, 2) NULL,
		[VULN_OVALL_SCOR] [decimal](10, 2) NULL,
		[VULN_VCTR_TXT] [varchar](100) NULL,
		[NTWK_NM] [varchar](150) NULL,
		[PRTCL_NM] [varchar](255) NULL,
		[HST_NM] [varchar](150) NULL,
		[DOM_NM] [varchar](150) NULL,
		[SFTW_NM] [varchar](150) NULL,
		[APPL_URL] [nvarchar](2000) NULL,
		[NETBIOS_NM] [varchar](150) NULL,
		[MAC_ADR_NM] [varchar](150) NULL,
		[VULN_TECH_COMMT_TXT] [varchar](MAX) NULL,
		[VULN_IMP_COMMT_TXT] [varchar](MAX) NULL,
		[RECOM_COMMT_TXT] [varchar](MAX) NULL,
		[ROOT_CAUS_COMMT_TXT] [varchar](MAX) NULL,
		[APPL_FL_UPLOAD_LOG_KEY] [int] NULL
	) 
	 
	IF OBJECT_ID('tempdb..#OP_SummaryOfChanges') IS NOT NULL
	DROP TABLE #OP_SummaryOfChanges;

	CREATE TABLE #OP_SummaryOfChanges(
		[INSERT_COUNTS] [int] NULL,
		[UPDATE_COUNTS] [int] NULL,
		[DELETE_COUNTS] [int] NULL,
	);

	DECLARE @SQL_OS_NM VARCHAR(MAX);

	SET @SQL_OS_NM = '
		DECLARE @ETL_AUD_LOG_KEY INT
			, @OS_NM AS OS_NMs
			, @SRC_COUNTS_OS_NM INT = 0
			, @INSERT_COUNTS_OS_NM INT = 0;

		EXEC [dbo].[INS_ETL_AUD_LOG]   	
			@JOB_NM         = '''+ @SCHEMA_NM +':MERGE_CLNT_VULN_INSTC_TRIPWIRE_IP360:OS_NAME''
			,@JOB_STRT_DT         = NULL
			,@JOB_END_DT         = NULL
			,@JOB_STS       = ''IN PROGRESS''
			,@SRC_FL_NM       = NULL
			,@SRC_FL_FLDR_PTH  = NULL
			,@TGT_TBL_NM       = ''[dbo].[OS]''
			,@SRC_ROW_CNT     =  @SRC_COUNTS_OS_NM
			,@TGT_INSRT_ROW_CNT = @INSERT_COUNTS_OS_NM
			,@TGT_UPDT_ROW_CNT = 0 
			,@TGT_REJ_ROW_CNT  = 0
    		,@RETVAL = @ETL_AUD_LOG_KEY OUTPUT;

		INSERT @OS_NM(OS_NM)
		SELECT DISTINCT
			LTRIM(RTRIM(nv_stg.OS_NM)) AS OS_NM
		FROM ' + @STG_TBL_NAME + ' nv_stg 
		WHERE ISNULL(LTRIM(RTRIM(nv_stg.OS_NM)),'''') <> '''';
	
		EXEC [dbo].[MERGE_OS_MAP] @OS_NM, @SRC_COUNTS_OS_NM OUTPUT,  @INSERT_COUNTS_OS_NM OUTPUT;

		EXEC [dbo].[UPDATE_ETL_AUD_LOG]   	
			@ETL_AUD_LOG_KEY         = @ETL_AUD_LOG_KEY
			,@JOB_END_DT         = NULL
			,@JOB_STS       = ''COMPLETED''
			,@SRC_ROW_CNT     =  @SRC_COUNTS_OS_NM
			,@TGT_INSRT_ROW_CNT = @INSERT_COUNTS_OS_NM
			,@TGT_UPDT_ROW_CNT = 0 
			,@TGT_REJ_ROW_CNT  = 0;

		';
	EXEC (@SQL_OS_NM);
	/* END */

	/* BEGIN: INSERT VULN_NM DATA IF NOT AVAILABLE	*/
	DECLARE @SQL_VULN_NM VARCHAR(MAX);
	SET @SQL_VULN_NM = '
		DECLARE @ETL_AUD_LOG_KEY INT
			, @VULN_NM AS VULN_NMs
			, @SRC_COUNTS_VULN_NM INT = 0
			, @INSERT_COUNTS_VULN_NM INT = 0

		EXEC [dbo].[INS_ETL_AUD_LOG]   	
			@JOB_NM         = '''+ @SCHEMA_NM +':MERGE_TRIPWIRE_IP360:VULN_NM''
			,@JOB_STRT_DT         = NULL
			,@JOB_END_DT         = NULL
			,@JOB_STS       = ''IN PROGRESS''
			,@SRC_FL_NM       = NULL
			,@SRC_FL_FLDR_PTH  = NULL
			,@TGT_TBL_NM       = ''[dbo].[VULN]''
			,@SRC_ROW_CNT     =  @SRC_COUNTS_VULN_NM
			,@TGT_INSRT_ROW_CNT = @INSERT_COUNTS_VULN_NM
			,@TGT_UPDT_ROW_CNT = 0 
			,@TGT_REJ_ROW_CNT  = 0
    		,@RETVAL = @ETL_AUD_LOG_KEY OUTPUT;

		INSERT @VULN_NM(VULN_NM)
		SELECT DISTINCT
			LTRIM(RTRIM(nv_stg.VULN_NM)) AS VULN_NM
		FROM ' + @STG_TBL_NAME + ' nv_stg 
		WHERE ISNULL(LTRIM(RTRIM(nv_stg.VULN_NM)),'''') <> '''';

		EXEC [dbo].[MERGE_VULN_CATGY_MAP] @VULN_NM, @SRC_COUNTS_VULN_NM OUTPUT,  @INSERT_COUNTS_VULN_NM OUTPUT;

		EXEC [dbo].[UPDATE_ETL_AUD_LOG]   	
			@ETL_AUD_LOG_KEY         = @ETL_AUD_LOG_KEY
			,@JOB_END_DT         = NULL
			,@JOB_STS       = ''COMPLETED''
			,@SRC_ROW_CNT     =  @SRC_COUNTS_VULN_NM
			,@TGT_INSRT_ROW_CNT = @INSERT_COUNTS_VULN_NM
			,@TGT_UPDT_ROW_CNT = 0 
			,@TGT_REJ_ROW_CNT  = 0;
	';
	EXEC (@SQL_VULN_NM);
	/* END */


	-- SET XACT_ABORT ON will cause the transaction to be uncommittable when the constraint violation occurs. 
	SET XACT_ABORT ON;
	
	BEGIN TRY
		SET NOCOUNT ON

		DECLARE @SQL_TEMP VARCHAR(MAX), @SQL_MERGE VARCHAR(MAX);
		BEGIN TRANSACTION MERGE_TRIPWIRE_IP360 WITH MARK N'MERGE_TRIPWIRE_IP360 to process the Vulnerability data.'
		
		SET @SQL_TEMP = '
			INSERT #Vulnerability_NV
			SELECT 
				client_srvc_engmt.ROW_STS_KEY
				,client_engmt.CLNT_ORG_KEY
				,client_engmt.CLNT_ENGMT_CD
				,client_srvc_engmt.SECUR_SRVC_CD 
				,(SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = ''Source'' and LKP_ENTY_NM = ''Tripwire IP360'') AS VULN_SRC_KEY
				,(SELECT VULN_INSTC_STS_CD FROM dbo.VULN_INSTC_STS WHERE VULN_INSTC_STS_NM = ''Open'') AS VULN_INSTC_STS_CD --##
				,NULL AS VULN_SEV_CD
				,NULL AS VULN_IMP_CD
				,NULL AS RISK_PRBL_CD
				,NULL AS RMDTN_CST_EFFRT_CD
				,VULN_.VULN_CATGY_CD AS VULN_CATGY_CD
				,NULL AS OWASP_TOP_10_KEY
				,RTRIM(CASE 
						WHEN (charindex(''CVE-'', nv_stg.ADVS) > 0 AND charindex('','', nv_stg.ADVS, charindex(''CVE-'', nv_stg.ADVS)) = 0 ) 
							THEN substring(nv_stg.ADVS, charindex(''CVE-'', nv_stg.ADVS), charindex('','', nv_stg.ADVS) - charindex(''CVE-'', nv_stg.ADVS) + 20)  
						WHEN (charindex(''CVE-'', nv_stg.ADVS) > 0 AND charindex('','', nv_stg.ADVS, charindex(''CVE-'', nv_stg.ADVS)) > 0 ) 
							THEN substring(nv_stg.ADVS, charindex(''CVE-'', nv_stg.ADVS), charindex('','', nv_stg.ADVS, charindex(''CVE-'', nv_stg.ADVS)) - charindex(''CVE-'', nv_stg.ADVS)) 
					ELSE NULL 
					END)  AS CVE_ID
				,OS_.OS_KEY AS OS_KEY --##
				,nv_stg.AUD_ID AS SRC_VULN_SCAN_ID
				,nv_stg.STRT_DT AS SRC_VULN_SCAN_STRT_DT
				,nv_stg.END_DT AS SRC_VULN_SCAN_END_DT
				,nv_stg.[GUID] AS SRC_VULN_INSTC_ID
				,nv_stg.VULN_ID AS SRC_VULN_ID
				,nv_stg.VULN_NM AS VULN_NM
				,nv_stg.[DESC] AS VULN_DESC
				,ISNULL(nv_stg.PUBL_DT,nv_stg.CREAT_DT) AS VULN_CREAT_DT
				,nv_stg.IP AS IPADR
				,nv_stg.PORT AS PORT_NBR
				,nv_stg.ADVS AS SRC_ADVS_TXT
				,nv_stg.MAX_CVSS_BAS_SCOR AS SRC_VULN_BAS_SCOR
				,NULL AS VULN_BAS_SCOR
				,NULL AS VULN_IMP_SUB_SCOR
				,NULL AS VULN_EXPLT_SUB_SCOR
				,NULL AS VULN_TMPRL_SCOR
				,NULL AS VULN_ENV_SCOR
				,NULL AS VULN_OVALL_SCOR
				,NULL AS VULN_VCTR_TXT
				,nv_stg.NTWK_NM AS NTWK_NM
				,nv_stg.PRTCL AS PRTCL_NM
				,nv_stg.HST_NM AS HST_NM
				,nv_stg.DOM_NM AS DOM_NM
				,nv_stg.APPL_NM AS SFTW_NM
				,NULL AS APPL_URL
				,nv_stg.NETBIOS_NM AS NETBIOS_NM
				,nv_stg.MAC_ADR AS MAC_ADR_NM
				,NULL AS VULN_TECH_COMMT_TXT
				,CAST(,nv_stg.STRG AS VARCHAR(MAX)) AS VULN_IMP_COMMT_TXT
				,NULL AS RECOM_COMMT_TXT
				,CAST(nv_stg.RISK AS VARCHAR(MAX)) AS ROOT_CAUS_COMMT_TXT
				,NULL AS APPL_FL_UPLOAD_LOG_KEY
			FROM ' + @STG_TBL_NAME + ' nv_stg
			INNER JOIN dbo.CLNT_ENGMT client_engmt
				ON nv_stg.NTWK_GRP_NM = client_engmt.CLNT_ENGMT_CD
			INNER JOIN dbo.CLNT_SECUR_SRVC_ENGMT client_srvc_engmt
				ON client_srvc_engmt.CLNT_ENGMT_CD = client_engmt.CLNT_ENGMT_CD
				AND client_srvc_engmt.SECUR_SRVC_CD = ''NV''
			LEFT JOIN dbo.OS OS_
				ON OS_.OS_NM = LTRIM(RTRIM(nv_stg.OS_NM))
			LEFT JOIN dbo.VULN VULN_
				ON VULN_.VULN_NM = LTRIM(RTRIM(nv_stg.VULN_NM))
			WHERE client_srvc_engmt.ROW_STS_KEY = (SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = ''Row Status'' and LKP_ENTY_NM = ''Active'')';
		EXEC (@SQL_TEMP);

		UPDATE #Vulnerability_NV SET CVE_ID = NULL WHERE ISNULL(CVE_ID,'') = ''

		UPDATE vlun_master SET vlun_master.VULN_VCTR_TXT = cve.VCTR_TXT
			,vlun_master.VULN_BAS_SCOR = cve.BAS_SCOR
			,vlun_master.VULN_IMP_SUB_SCOR = cve.IMP_SUB_SCOR
			,vlun_master.VULN_EXPLT_SUB_SCOR = cve.EXPLT_SUB_SCOR 
			--,vlun_master.VULN_OVALL_SCOR = cve.BAS_SCOR
		FROM #Vulnerability_NV vlun_master
		JOIN dbo.CVE cve
			ON vlun_master.CVE_ID = cve.CVE_ID

		--Overall Score Logic --CVSS v2
		UPDATE vlun_master SET vlun_master.VULN_OVALL_SCOR = (CASE WHEN ((vlun_master.[VULN_BAS_SCOR] IS NOT NULL) AND (vlun_master.[VULN_ENV_SCOR] IS NOT NULL)) THEN vlun_master.[VULN_ENV_SCOR] WHEN ((vlun_master.[VULN_BAS_SCOR] IS NOT NULL) AND (vlun_master.[VULN_ENV_SCOR] IS NULL) AND (vlun_master.[VULN_TMPRL_SCOR] IS NOT NULL)) THEN vlun_master.[VULN_TMPRL_SCOR] WHEN ((vlun_master.[VULN_BAS_SCOR] IS NOT NULL) AND (vlun_master.[VULN_ENV_SCOR] IS NULL) AND (vlun_master.[VULN_TMPRL_SCOR] IS NULL)) THEN vlun_master.[VULN_BAS_SCOR] ELSE vlun_master.[VULN_BAS_SCOR] END)
		FROM #Vulnerability_NV vlun_master

		-- Update CVE_ID as NULL, if not in the reference table
		UPDATE vlun_master SET CVE_ID = NULL
		FROM  #Vulnerability_NV vlun_master
		LEFT OUTER JOIN dbo.CVE cve
			ON vlun_master.CVE_ID = cve.CVE_ID
		WHERE cve.CVE_ID IS NULL

		SELECT @SRC_COUNTS = COUNT(*) FROM #Vulnerability_NV
		PRINT N'Message: TOTAL ' + CAST(@SRC_COUNTS AS VARCHAR) + N' RECORDS FROM SOURCE.'	

		SET @SQL_MERGE = '
			DECLARE @SummaryOfChanges table (Change VARCHAR(20));

			;WITH _target AS 
			(

				SELECT *
				FROM ' + @MASTER_TBL_NAME + '
				WHERE SECUR_SRVC_CD IN(''NV'') 
			)
			, _source AS 
			(
				SELECT ROW_STS_KEY
				,ORG_KEY
				,CLNT_ENGMT_CD
				,SECUR_SRVC_CD
				,VULN_SRC_KEY
				,VULN_INSTC_STS_CD
				,VULN_SEV_CD
				,VULN_IMP_CD
				,RISK_PRBL_CD
				,RMDTN_CST_EFFRT_CD
				,VULN_CATGY_CD
				,OWASP_TOP_10_KEY
				,CVE_ID
				,OS_KEY
				,SRC_VULN_SCAN_ID
				,SRC_VULN_SCAN_STRT_DT
				,SRC_VULN_SCAN_END_DT
				,SRC_VULN_INSTC_ID
				,SRC_VULN_ID
				,VULN_NM
				,VULN_DESC
				,VULN_CREAT_DT
				,IPADR
				,PORT_NBR
				,SRC_ADVS_TXT
				,SRC_VULN_BAS_SCOR
				,VULN_BAS_SCOR
				,VULN_IMP_SUB_SCOR
				,VULN_EXPLT_SUB_SCOR
				,VULN_TMPRL_SCOR
				,VULN_ENV_SCOR
				,VULN_OVALL_SCOR
				,VULN_VCTR_TXT
				,NTWK_NM
				,PRTCL_NM
				,HST_NM
				,DOM_NM
				,SFTW_NM
				,APPL_URL
				,NETBIOS_NM
				,MAC_ADR_NM
				,CAST(VULN_TECH_COMMT_TXT AS TEXT) AS VULN_TECH_COMMT_TXT
				,CAST(VULN_IMP_COMMT_TXT AS TEXT) AS VULN_IMP_COMMT_TXT
				,CAST(RECOM_COMMT_TXT AS TEXT) AS RECOM_COMMT_TXT
				,CAST(ROOT_CAUS_COMMT_TXT AS TEXT) AS ROOT_CAUS_COMMT_TXT
				,APPL_FL_UPLOAD_LOG_KEY
				FROM #Vulnerability_NV
			)


			MERGE INTO _target
			USING _source ON (
				_target.CLNT_ENGMT_CD=_source.CLNT_ENGMT_CD
				AND _target.SECUR_SRVC_CD=_source.SECUR_SRVC_CD
				AND _target.ROW_STS_KEY=_source.ROW_STS_KEY
				AND _target.ORG_KEY=_source.ORG_KEY
				AND _target.VULN_SRC_KEY=_source.VULN_SRC_KEY
				AND _target.VULN_INSTC_STS_CD=_source.VULN_INSTC_STS_CD

				AND ISNULL(_target.SRC_VULN_SCAN_ID,0)=ISNULL(_source.SRC_VULN_SCAN_ID,0)
				AND _target.SRC_VULN_ID=_source.SRC_VULN_ID
				AND ISNULL(_target.APPL_FL_UPLOAD_LOG_KEY,0)=ISNULL(_source.APPL_FL_UPLOAD_LOG_KEY,0)
				)
			WHEN NOT MATCHED THEN INSERT (
				ROW_STS_KEY
				, ORG_KEY
				, CLNT_ENGMT_CD
				, SECUR_SRVC_CD
				, VULN_SRC_KEY
				, VULN_INSTC_STS_CD
				, VULN_SEV_CD
				, VULN_IMP_CD
				, RISK_PRBL_CD
				, RMDTN_CST_EFFRT_CD
				, VULN_CATGY_CD
				, OWASP_TOP_10_KEY
				, CVE_ID
				, OS_KEY
				, SRC_VULN_SCAN_ID
				, SRC_VULN_SCAN_STRT_DT
				, SRC_VULN_SCAN_END_DT
				, SRC_VULN_INSTC_ID
				, SRC_VULN_ID
				, VULN_NM
				, VULN_DESC
				, VULN_CREAT_DT
				, IPADR
				, PORT_NBR
				, SRC_ADVS_TXT
				, SRC_VULN_BAS_SCOR
				, VULN_BAS_SCOR
				, VULN_IMP_SUB_SCOR
				, VULN_EXPLT_SUB_SCOR
				, VULN_TMPRL_SCOR
				, VULN_ENV_SCOR
				, VULN_OVALL_SCOR
				, VULN_VCTR_TXT
				, NTWK_NM
				, PRTCL_NM
				, HST_NM
				, DOM_NM
				, SFTW_NM
				, APPL_URL
				, NETBIOS_NM
				, MAC_ADR_NM
				, VULN_TECH_COMMT_TXT
				, VULN_IMP_COMMT_TXT
				, RECOM_COMMT_TXT
				, ROOT_CAUS_COMMT_TXT
				, APPL_FL_UPLOAD_LOG_KEY
				, CREAT_DT
				, CREAT_USER_ID
			)			
			VALUES (
				_source.ROW_STS_KEY
				,_source.ORG_KEY
				,_source.CLNT_ENGMT_CD
				,_source.SECUR_SRVC_CD
				,_source.VULN_SRC_KEY
				,_source.VULN_INSTC_STS_CD
				,_source.VULN_SEV_CD
				,_source.VULN_IMP_CD
				,_source.RISK_PRBL_CD
				,_source.RMDTN_CST_EFFRT_CD
				,_source.VULN_CATGY_CD
				,_source.OWASP_TOP_10_KEY
				,_source.CVE_ID
				,_source.OS_KEY
				,_source.SRC_VULN_SCAN_ID
				,_source.SRC_VULN_SCAN_STRT_DT
				,_source.SRC_VULN_SCAN_END_DT
				,_source.SRC_VULN_INSTC_ID
				,_source.SRC_VULN_ID
				,_source.VULN_NM
				,_source.VULN_DESC
				,_source.VULN_CREAT_DT
				,_source.IPADR
				,_source.PORT_NBR
				,_source.SRC_ADVS_TXT
				,_source.SRC_VULN_BAS_SCOR
				,_source.VULN_BAS_SCOR
				,_source.VULN_IMP_SUB_SCOR
				,_source.VULN_EXPLT_SUB_SCOR
				,_source.VULN_TMPRL_SCOR
				,_source.VULN_ENV_SCOR
				,_source.VULN_OVALL_SCOR
				,_source.VULN_VCTR_TXT
				,_source.NTWK_NM
				,_source.PRTCL_NM
				,_source.HST_NM
				,_source.DOM_NM
				,_source.SFTW_NM
				,_source.APPL_URL
				,_source.NETBIOS_NM
				,_source.MAC_ADR_NM
				,_source.VULN_TECH_COMMT_TXT
				,_source.VULN_IMP_COMMT_TXT
				,_source.RECOM_COMMT_TXT
				,_source.ROOT_CAUS_COMMT_TXT
				,_source.APPL_FL_UPLOAD_LOG_KEY
				,GETDATE() 
				,''2''
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
			COMMIT TRANSACTION MERGE_TRIPWIRE_IP360;
		END;

	END TRY

        
	BEGIN CATCH

		IF (XACT_STATE()) = -1
		BEGIN
			PRINT N'The transaction is in an uncommittable state. Rolling back transaction.'
			ROLLBACK TRANSACTION MERGE_TRIPWIRE_IP360
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