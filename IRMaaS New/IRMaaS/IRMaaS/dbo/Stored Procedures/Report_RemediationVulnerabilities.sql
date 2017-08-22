CREATE PROCEDURE [dbo].[Report_RemediationVulnerabilities]
(

       @CLNT_ENGMT_CD       VARCHAR(100),
       @SECUR_SRVC_CD		VARCHAR(10),
	   @schema				VARCHAR(50)   
       
)
AS
BEGIN
DECLARE @Query VARCHAR(max)
BEGIN TRY
SET NOCOUNT ON

	   SET			 @Query ='	       
	   SELECT		 A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.VULN_NM,B.VULN_SEV_CD,B.VULN_SEV_NM,C.VULN_INSTC_STS_NM
	   FROM			 '+ @schema+'.CLNT_VULN_INSTC       A
       JOIN			 VULN_SEV                           B
       ON            A.VULN_SEV_CD                    = B.VULN_SEV_CD
       JOIN			 VULN_INSTC_STS                     C
       ON            C.VULN_INSTC_STS_CD = A.VULN_INSTC_STS_CD
       WHERE		 CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD + '''
--     AND           A.VULN_INSTC_STS_CD       IN(''O'',''C'')
       AND           A.VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
       AND           A.VULN_SEV_CD                    NOT IN(''I'')
       AND           A.SECUR_SRVC_CD           =      CASE WHEN '''+ @SECUR_SRVC_CD + ''' = '''' THEN A.SECUR_SRVC_CD ELSE '''+ @SECUR_SRVC_CD + ''' END
       AND           A.ROW_STS_KEY                    =      1 '
	   EXECUTE (@Query)


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

