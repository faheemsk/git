

CREATE PROCEDURE [dbo].[Analyst_INS_CLNT_VULN_INSTC]
(
       @ROW_STS_KEY               INTEGER,
       @ORG_KEY                          INTEGER,
       @CLNT_ENGMT_CD                    VARCHAR(30),  
       @SECUR_SRVC_CD                    VARCHAR(10),
       @VULN_SRC_KEY              INTEGER,
       @VULN_INSTC_STS_CD         VARCHAR(3),
       @VULN_SEV_CD               VARCHAR(3),
       @VULN_IMP_CD               VARCHAR(3),
       @RISK_PRBL_CD              VARCHAR(3),
       @RMDTN_CST_EFFRT_CD        VARCHAR(3) = NULL,
       @VULN_CATGY_CD		      VARCHAR(10),
       @CVE_ID                                  VARCHAR(25),
       @SRC_VULN_INSTC_ID         VARCHAR(150),
       @VULN_NM                          VARCHAR(255),
       @VULN_DESC                        TEXT,
       @IPADR                            VARCHAR(39),
       @PORT_NBR                         INTEGER,
       @SRC_ADVS_TXT              VARCHAR(1024),
       @VULN_BAS_SCOR                    DECIMAL(10,2),
       @VULN_IMP_SUB_SCOR         DECIMAL(10,2),
       @VULN_EXPLT_SUB_SCOR DECIMAL(10,2),
       @VULN_TMPRL_SCOR           DECIMAL(10,2),
       @VULN_ENV_SCOR                    DECIMAL(10,2),
       @VULN_VCTR_TXT                    VARCHAR(100),
       @NTWK_NM                          VARCHAR(150),
       @PRTCL_NM                         VARCHAR(255),
       @HST_NM                                  VARCHAR(150),
       @DOM_NM                                  VARCHAR(150),
       @SFTW_NM                          VARCHAR(150),
       @OS_KEY                                  INTEGER,
       @APPL_URL                         NVARCHAR(2000),
       @NETBIOS_NM                       VARCHAR(150),
       @MAC_ADR_NM                       VARCHAR(150),
       @VULN_TECH_COMMT_TXT TEXT,
       @VULN_IMP_COMMT_TXT        TEXT,
       @RECOM_COMMT_TXT           TEXT,
       @ROOT_CAUS_COMMT_TXT    TEXT,
       @USER_ID                          INTEGER,
       @VULN_OVALL_SCOR           DECIMAL(10,2),
       @OWASP_TOP_10_KEY          INTEGER,
       @schema                                  VARCHAR(50)

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query nVARCHAR(max)
DECLARE @id INTEGER


SET          @Query = 'INSERT '+ @schema+'.CLNT_VULN_INSTC (ROW_STS_KEY,ORG_KEY, CLNT_ENGMT_CD, SECUR_SRVC_CD,VULN_SRC_KEY,VULN_INSTC_STS_CD,VULN_SEV_CD,VULN_IMP_CD,
RISK_PRBL_CD,RMDTN_CST_EFFRT_CD, VULN_CATGY_CD,CVE_ID,SRC_VULN_INSTC_ID,
VULN_NM,VULN_DESC,VULN_CREAT_DT,IPADR,PORT_NBR,SRC_ADVS_TXT,VULN_BAS_SCOR,VULN_IMP_SUB_SCOR,VULN_EXPLT_SUB_SCOR,
VULN_TMPRL_SCOR,VULN_ENV_SCOR,VULN_VCTR_TXT,NTWK_NM,PRTCL_NM,HST_NM,DOM_NM,SFTW_NM,OS_KEY,APPL_URL,NETBIOS_NM,
MAC_ADR_NM,VULN_TECH_COMMT_TXT,VULN_IMP_COMMT_TXT,RECOM_COMMT_TXT,ROOT_CAUS_COMMT_TXT,
CREAT_DT,CREAT_USER_ID,VULN_OVALL_SCOR,OWASP_TOP_10_KEY) VALUES

('+CONVERT(VARCHAR, @ROW_STS_KEY)+',
'+CONVERT(VARCHAR,@ORG_KEY)+',
'''+CONVERT(VARCHAR,@CLNT_ENGMT_CD)+''',
''' +CONVERT(VARCHAR,@SECUR_SRVC_CD)+''',
'+CONVERT(VARCHAR,@VULN_SRC_KEY)+',
''' +CONVERT(VARCHAR,@VULN_INSTC_STS_CD)+''',
  '+ isnull('''' + convert(varchar(3),@VULN_SEV_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@VULN_IMP_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@RISK_PRBL_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@RMDTN_CST_EFFRT_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(10),@VULN_CATGY_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(25),@CVE_ID) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@SRC_VULN_INSTC_ID) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(255),@VULN_NM),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_DESC),'''','''''') + '''','null') + ',
'+'GETDATE()'+',
  '+ isnull('''' + convert(varchar(39),@IPADR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@PORT_NBR) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(1024),@SRC_ADVS_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_BAS_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_IMP_SUB_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_EXPLT_SUB_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_TMPRL_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_ENV_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar(100),@VULN_VCTR_TXT) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@NTWK_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(255),@PRTCL_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@HST_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@DOM_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@SFTW_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@OS_KEY) + '''','null') + ',
  '+ isnull('''' + convert(varchar(2000),@APPL_URL) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@NETBIOS_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@MAC_ADR_NM) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_TECH_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_IMP_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@RECOM_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@ROOT_CAUS_COMMT_TXT),'''','''''') + '''','null') + ',
' + 'GETDATE()'+',
'+CONVERT(VARCHAR,@USER_ID)+',
  '+ isnull('''' + convert(varchar,@VULN_OVALL_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@OWASP_TOP_10_KEY) + '''','null') + ') ; SELECT @@IDENTITY' 

--  PRINT @Query
--  PRINT @id
--  EXECUTE sp_executesql @Query, N'@Id INTEGER OUTPUT', @NewId OUTPUT
  -- SELECT @Result = @id
  DECLARE @Result AS Table (RetValue int)
  INSERT INTO @Result EXECUTE (@Query)
  SELECT RetValue FROM @Result
--  SELECT @id AS RETVAL
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
END
