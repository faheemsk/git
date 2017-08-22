--DROP PROCEDURE GetUserSecurityDetailsByName
CREATE PROCEDURE [dbo].[GetUserSecurityDetailsByName]
(
       @UserID  INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT USER_SECUR_DTL_KEY,[dbo].[fnGetMasterLkpNameByID](SECUR_QUES_KEY) SECUR_QUES,
	   SECUR_QUES_KEY,ANS_TXT,SEQ_ORDR_NBR
FROM   USER_SECUR_DTL
WHERE  USER_ID                           = @UserID

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







