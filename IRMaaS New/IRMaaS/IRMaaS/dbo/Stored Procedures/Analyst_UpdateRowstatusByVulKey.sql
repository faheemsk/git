CREATE PROCEDURE [dbo].[Analyst_UpdateRowstatusByVulKey]
(
       @CLNT_VULN_INSTC_KEY INTEGER,
       @USER_ID                          INTEGER,
       @schema                           VARCHAR(50)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)

              SET    @Query = 'DELETE FROM      '+ @schema+'.CLNT_VULN_SECUR_CTL
              WHERE  CLNT_VULN_INSTC_KEY  =      '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY)

              EXECUTE (@Query)

              SET    @Query = 'UPDATE     '+ @schema+'.CLNT_VULN_INSTC
              SET           ROW_STS_KEY          =   2, 
                           UPDT_USER_ID    =   '+convert(varchar(50),@USER_ID)+ ',
                UPDT_DT         =   '+'GETDATE()'+'
              WHERE  CLNT_VULN_INSTC_KEY  =      '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY)

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS

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

