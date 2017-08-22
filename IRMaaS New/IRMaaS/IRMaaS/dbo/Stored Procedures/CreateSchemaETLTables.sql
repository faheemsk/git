CREATE PROCEDURE [dbo].[CreateSchemaETLTables]
(
 @SchemaName VARCHAR(50),
 @ORG_KEY	 INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)
DECLARE @Query3 VARCHAR(max)
DECLARE @Query4 VARCHAR(max)

SET @Query1= '

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CMN_FMT_V1_STG'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.CMN_FMT_V1_STG 
(
	[SRC_VULN_SCAN_ID]       VARCHAR (150)   NULL,
    [SRC_VULN_SCAN_STRT_DT]  DATETIME        NULL,
    [SRC_VULN_SCAN_END_DT]   DATETIME        NULL,
    [SRC_VULN_INSTC_ID]      VARCHAR (150)   NULL,
    [SRC_VULN_ID]            VARCHAR (150)   NULL,
    [VULN_NM]                VARCHAR (255)   NOT NULL,
    [VULN_DESC]              TEXT            NULL,
    [VULN_CREAT_DT]          DATETIME        NOT NULL,
    [VULN_SEV_CD]            VARCHAR (3)     NULL,
    [VULN_SEV_NM]            VARCHAR (150)   NULL,
    [VULN_IMP_CD]            VARCHAR (3)     NULL,
    [VULN_IMP_NM]            VARCHAR (150)   NULL,
    [RISK_PRBL_CD]           VARCHAR (3)     NULL,
    [RISK_PRBL_NM]           VARCHAR (150)   NULL,
    [RMDTN_CST_EFFRT_CD]     VARCHAR (3)     NULL,
    [RMDTN_CST_EFFRT_NM]     VARCHAR (150)   NULL,
    [ROOT_CAUS_ANLYS_CD]     VARCHAR (10)    NULL,
    [OWASP_CD]               VARCHAR (10)    NULL,
    [IPADR]                  VARCHAR (39)    NULL,
    [PORT_NBR]               INT             NULL,
    [HST_NM]                 VARCHAR (150)   NULL,
    [NTWK_NM]                VARCHAR (150)   NULL,
    [PRTCL_NM]               VARCHAR (255)   NULL,
    [NETBIOS_NM]             VARCHAR (150)   NULL,
    [MAC_ADR_NM]             VARCHAR (150)   NULL,
    [APPL_URL]               NVARCHAR (2000) NULL,
    [SFTW_NM]                VARCHAR (150)   NULL,
    [CVE_ID]                 VARCHAR (25)    NULL,
    [SRC_ADVS_TXT]           VARCHAR (1024)  NULL,
    [DOM_NM]                 VARCHAR (150)   NULL,
    [OS_NM]                  VARCHAR (150)   NULL,
    [VULN_BAS_SCOR]          DECIMAL (10, 2) NULL,
    [VULN_EXPLT_SUB_SCOR]    DECIMAL (10, 2) NULL,
    [VULN_IMP_SUB_SCOR]      DECIMAL (10, 2) NULL,
    [VULN_TMPRL_SCOR]        DECIMAL (10, 2) NULL,
    [VULN_ENV_SCOR]          DECIMAL (10, 2) NULL,
    [VULN_VCTR_TXT]          VARCHAR (100)   NULL,
    [RECOM_COMMT_TXT]        TEXT            NULL,
    [VULN_IMP_COMMT_TXT]     TEXT            NULL,
    [ROOT_CAUS_COMMT_TXT]    TEXT            NULL,
    [VULN_TECH_COMMT_TXT]    TEXT            NULL,
    [APPL_FL_UPLOAD_LOG_KEY] INT             NOT NULL,
    [CREAT_DT]               DATETIME        NOT NULL,
    [CREAT_USER_ID]          INT             NOT NULL
	)  
	
END
ELSE
BEGIN
SELECT -1 Retval
END 
'

 
 -- PRINT  (@query1)
 EXECUTE (@Query1)

 SET @Query2= '

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CISCO_PEN_TST_STG'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.CISCO_PEN_TST_STG 
(
	[ID]                     VARCHAR (50)    NULL,
    [SEV]                    INT             NULL,
    [SEV_LBL]                VARCHAR (150)   NULL,
    [PRBL]                   INT             NULL,
    [PRBL_LBL]               VARCHAR (150)   NULL,
    [URL]                    VARCHAR (2000)  NULL,
    [DESC]                   TEXT            NULL,
    [CREAT_AT]               DATETIME        NULL,
    [STS]                    VARCHAR (150)   NULL,
    [IP]                     VARCHAR (255)   NULL,
    [IP_AS_INTG]             BIGINT          NULL,
    [PORT]                   INT             NULL,
    [PRTCL]                  VARCHAR (255)   NULL,
    [CVSS_BAS]               DECIMAL (10, 2) NULL,
    [CVSS_TMPRL]             DECIMAL (10, 2) NULL,
    [HST]                    VARCHAR (255)   NULL,
    [OS]                     VARCHAR (255)   NULL,
    [SCOR]                   INT             NULL,
    [SCOR_LBL]               VARCHAR (150)   NULL,
    [RMDTN_EFFRT]            INT             NULL,
    [RMDTN_EFFRT_LBL]        VARCHAR (150)   NULL,
    [PROJ_ID]                VARCHAR (150)   NULL,
    [PROJ_STRT_DT]           DATETIME        NULL,
    [PROJ_END_DT]            DATETIME        NULL,
    [VULN_CLSS]              VARCHAR (255)   NULL,
    [CVE]                    VARCHAR (2000)  NULL,
    [CATGY]                  VARCHAR (255)   NULL,
    [DTL]                    TEXT            NULL,
    [SECUR_IMP]              TEXT            NULL,
    [MITG_FCT]               TEXT            NULL,
    [SOLN]                   TEXT            NULL,
    [REPRD_STEP]             TEXT            NULL,
    [REPRD_NOTE]             TEXT            NULL,
    [CSTM_FLD_1]             VARCHAR (1000)  NULL,
    [CSTM_FLD_2]             VARCHAR (1000)  NULL,
    [CSTM_FLD_3]             VARCHAR (1000)  NULL,
    [CSTM_FLD_4]             VARCHAR (1000)  NULL,
    [CSTM_COL_TYP]           VARCHAR (1000)  NULL,
    [CSTM_COL_LBL]           VARCHAR (1000)  NULL,
    [APPL_FL_UPLOAD_LOG_KEY] INT             NOT NULL,
    [CREAT_DT]               DATETIME        NOT NULL,
    [CREAT_USER_ID]          INT             NOT NULL
	)  
	
END
ELSE
BEGIN
SELECT -1 Retval
END 
'

 
 -- PRINT  (@query2)
 EXECUTE (@Query2)

  SET @Query3= '

IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CISCO_SECUR_RISK_ASES_STG'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.CISCO_SECUR_RISK_ASES_STG 
(
	 [RISK_NBR]               INT            NULL,
    [RISK_NM]                VARCHAR (255)  NULL,
    [CTL_EFF]                VARCHAR (150)  NULL,
    [RISK_LVL_IMP]           VARCHAR (150)  NULL,
    [RISK_LVL_PRBL]          VARCHAR (150)  NULL,
    [RISK_LVL_OVALL]         VARCHAR (150)  NULL,
    [RISK_DESC]              TEXT           NULL,
    [IMP_COMMT]              TEXT           NULL,
    [CTL_DEFICIENCIES]       TEXT           NULL,
    [RMDTN_RECOM]            TEXT           NULL,
    [MITG_CTL_AREA]          VARCHAR (2000) NULL,
    [RLVN_STRG_TRND]         VARCHAR (2000) NULL,
    [APPL_FL_UPLOAD_LOG_KEY] INT            NOT NULL,
    [CREAT_DT]               DATETIME       NOT NULL,
    [CREAT_USER_ID]          INT            NOT NULL
	)  
	
END
ELSE
BEGIN
SELECT -1 Retval
END 
'

 
 -- PRINT  (@query3)
 EXECUTE (@Query3)
 
SET @Query4= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''TRIPWIRE_IP360_STG'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.TRIPWIRE_IP360_STG(
	[AUD_ID]            INT            NOT NULL,
    [NTWK_ID]           INT            NOT NULL,
    [NTWK_NM]           VARCHAR (256)  NOT NULL,
    [NTWK_GRP_NM]       VARCHAR (128)  NULL,
    [SCANPROFILE_ID]    INT            NOT NULL,
    [STRT_DT]           DATETIME       NULL,
    [END_DT]            DATETIME       NULL,
    [VULN_ID]           INT            NOT NULL,
    [VULN_NM]           VARCHAR (512)  NULL,
    [DESC]              TEXT           NULL,
    [ADVS]              VARCHAR (1024) NULL,
    [RISK]              VARCHAR (32)   NULL,
    [SKL]               VARCHAR (32)   NULL,
    [STRG]              VARCHAR (32)   NULL,
    [PUBL_DT]           DATETIME       NULL,
    [GUID]              INT            NULL,
    [APPL_NM]           VARCHAR (256)  NULL,
    [PORT]              INT            NULL,
    [PRTCL]             VARCHAR (64)   NULL,
    [MAX_CVSS_BAS_SCOR] DECIMAL (3, 1) NULL,
    [VNE_ID]            INT            NOT NULL,
    [IP]                VARCHAR (39)   NULL,
    [HST_NM]            VARCHAR (128)  NULL,
    [NETBIOS_NM]        VARCHAR (128)  NULL,
    [DOM_NM]            VARCHAR (128)  NULL,
    [OS_NM]             VARCHAR (128)  NULL,
    [MAC_ADR]           VARCHAR (32)   NULL,
    [IP_NBR]            BIGINT         NULL,
    [CREAT_DT]          DATETIME       NOT NULL,
    [CREAT_USER_ID]     INT            NOT NULL
) 
SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
-- PRINT (@Query4)
 EXECUTE (@Query4)
 
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

