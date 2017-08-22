
CREATE PROCEDURE [dbo].[Report_ListObjective]
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
					   SELECT DISTINCT LTRIM(SECUR_OBJ_CD) SECUR_OBJ_CD,SECUR_OBJ_CD + '-' + SECUR_OBJ_NM ObjectiveName
                       FROM SECUR_CTL
                       WHERE REG_CMPLN_CD = 'HITRUST'
                       ORDER BY LTRIM(SECUR_OBJ_CD) ASC
                     
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
