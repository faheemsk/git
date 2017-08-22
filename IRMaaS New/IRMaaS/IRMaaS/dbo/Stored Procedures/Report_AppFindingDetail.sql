CREATE PROCEDURE [dbo].[Report_AppFindingDetail]
(
	@Flag				 VARCHAR(2),-- 'AL' all,'AP' Application , 'SS' SSID
	@CLNT_ENGMT_CD		 VARCHAR(100),
	@SECUR_SRVC_CD		 VARCHAR(100),
	@VULN_SEV_CD		 VARCHAR(50),
	@SECUR_OBJ_CD		 VARCHAR(500),
	@APPNETCD			 VARCHAR(500),
	@SchemaName			 VARCHAR(50)
)
AS
BEGIN
	
BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(MAX)

IF @Flag = 'AL'
BEGIN
SET @Query='
 SELECT    A.*
 FROM	  '+ @SchemaName+ '.Findings								  A
 WHERE    A.CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
 AND	  A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
 AND	  A.VULN_SEV_CD				NOT IN(''I'')
 --AND	  A.ROW_STS_KEY				  = 1
 AND	  1= CASE WHEN '''+@SECUR_SRVC_CD+''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+@SECUR_SRVC_CD+''','','')) THEN 1 END
 AND	  1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
 AND	  1= CASE WHEN '''+@SECUR_OBJ_CD+''' = ''''  THEN 1	WHEN LTRIM(RTRIM(A.SECUR_OBJ_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@SECUR_OBJ_CD+''')),'',''))	THEN 1 END'
 EXECUTE(@Query)
 END

 IF @Flag = 'AP'
BEGIN
SET @Query='
SELECT    A.*
FROM	  '+ @SchemaName+ '.Findings								  A
JOIN	  dbo.FnSplit('''+@APPNETCD +''','','')	  E
 ON		  A.SFTW_NM					= E.items
 WHERE    A.CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
 AND	  A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
 AND	  A.VULN_SEV_CD				NOT IN(''I'')
 --AND	  A.ROW_STS_KEY				  = 1
 AND	  1= CASE WHEN '''+@SECUR_SRVC_CD+''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+@SECUR_SRVC_CD+''','','')) THEN 1 END
 AND	  1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
 AND	  1= CASE WHEN '''+@SECUR_OBJ_CD+''' = ''''  THEN 1	WHEN LTRIM(RTRIM(A.SECUR_OBJ_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@SECUR_OBJ_CD+''')),'',''))	THEN 1 END'
 EXECUTE(@Query)
 END

 IF @Flag = 'SS'
BEGIN

SET @Query='
 SELECT    A.*
 FROM	  '+ @SchemaName+ '.Findings								  A
 JOIN	  dbo.FnSplit('''+@APPNETCD +''','','')	  E
 ON		 A.NETBIOS_NM				= E.items
 WHERE    A.CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
 AND	  A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
 AND	  A.VULN_SEV_CD				NOT IN(''I'')
 --AND	  A.ROW_STS_KEY				  = 1
 AND	  1= CASE WHEN '''+@SECUR_SRVC_CD+''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+@SECUR_SRVC_CD+''','','')) THEN 1 END
 AND	  1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
 AND	  1= CASE WHEN '''+@SECUR_OBJ_CD+''' = ''''  THEN 1	WHEN LTRIM(RTRIM(A.SECUR_OBJ_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@SECUR_OBJ_CD+''')),'',''))	THEN 1 END'
 EXECUTE(@Query)
 END
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

