CREATE PROCEDURE [dbo].[Analyst_ListRootCause]
(
       @CLNT_ENGMT_CD VARCHAR(30)

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

                     IF @CLNT_ENGMT_CD = ''
                     BEGIN
                     SELECT  DISTINCT ROOT_CAUS_ANLYS_CD,ROOT_CAUS_ANLYS_NM,ROOT_CAUS_ANLYS_DESC
                     FROM   ROOT_CAUS_ANLYS      
                     ORDER  BY     ROOT_CAUS_ANLYS_NM 
                     END    
                     ELSE
                     BEGIN
                     SELECT  DISTINCT A.ROOT_CAUS_ANLYS_CD,A.ROOT_CAUS_ANLYS_NM,A.ROOT_CAUS_ANLYS_DESC
                     FROM   ROOT_CAUS_ANLYS      A
                     JOIN   CLNT_VULN_INSTC B
                     ON     A.ROOT_CAUS_ANLYS_CD = B.ROOT_CAUS_ANLYS_CD
                     WHERE   B.CLNT_ENGMT_CD     = @CLNT_ENGMT_CD
                     AND     B.VULN_SEV_CD NOT IN('I')  
                     AND     B.VULN_INSTC_STS_CD NOT IN('D','FP')
					 AND     B.ROW_STS_KEY   =   1
                     ORDER  BY     A.ROOT_CAUS_ANLYS_NM 


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