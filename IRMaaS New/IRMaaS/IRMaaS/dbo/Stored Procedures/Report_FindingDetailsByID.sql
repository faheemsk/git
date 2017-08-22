CREATE PROCEDURE  [dbo].[Report_FindingDetailsByID]
(
	
	@CLNT_ENGMT_CD		 VARCHAR(100),
	@CLNT_VULN_INSTC_KEY VARCHAR(1000),
	@Flag				 VARCHAR(2), --  Summary 'S',Matrix 'M'
	@SchemaName			 VARCHAR(50)
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(MAX)


IF @Flag = 'S' 
BEGIN
SET @Query='
SELECT    DISTINCT A.*
FROM	  '+ @SchemaName+ '.Findings								  A
--JOIN	  dbo.FnSplit('+@CLNT_VULN_INSTC_KEY + ','','')	  E
--ON		  A.CLNT_VULN_INSTC_KEY					= E.items
WHERE	      A.VULN_INSTC_STS_CD					NOT IN(''D'',''FP'')
AND			A.VULN_SEV_CD							NOT IN(''I'')
AND			A.CLNT_ENGMT_CD							= '''+@CLNT_ENGMT_CD + '''
AND			A.VULN_CATGY_CD							= '+ @CLNT_VULN_INSTC_KEY 
EXECUTE(@Query)
END

IF @Flag = 'M' 
BEGIN
SET @Query='
SELECT    *
FROM	  '+ @SchemaName+ '.Findings								 
WHERE	  CLNT_ENGMT_CD							=  '''+@CLNT_ENGMT_CD+'''
AND		  VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
AND		  VULN_SEV_CD			NOT IN(''I'')
AND		  VULN_IMP_CD			IN(''C'',''MJ'',''I'',''MI'',''IF'',''MO'') 
AND		  RMDTN_CST_EFFRT_CD IN(''H'',''M'',''L'')
AND		  1= CASE WHEN '''+@CLNT_VULN_INSTC_KEY+''' = ''''  THEN 1	WHEN LTRIM(RTRIM(CLNT_VULN_INSTC_KEY)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@CLNT_VULN_INSTC_KEY +''')),'',''))	THEN 1 END
'
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
