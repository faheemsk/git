
CREATE PROCEDURE [dbo].[GetEmailIDbyUserID]
(
       @USER_ID      INTEGER               
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT EMAIL_ID,FST_NM [First Name],MIDL_NM [Middle Name],LST_NM [Last Name] 
FROM   USER_PRFL
WHERE   USER_ID = @USER_ID




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



