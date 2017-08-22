
CREATE PROCEDURE [dbo].[Report_ListReportNames]
(
       @CLNT_ENGMT_CD             VARCHAR(30)   
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
              SELECT  RPT_NM_KEY,RPT_NM
              FROM   RPT_NM        
              WHERE  RPT_NM                            =   'Executive Summary'    
              UNION
              SELECT  A.RPT_NM_KEY,A.RPT_NM
              FROM   RPT_NM                                   A
              JOIN   CLNT_SECUR_SRVC_ENGMT             B
              ON            A.SECUR_SRVC_CD                   =   B.SECUR_SRVC_CD
              WHERE   B.CLNT_ENGMT_CD                  =   @CLNT_ENGMT_CD

                     
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
