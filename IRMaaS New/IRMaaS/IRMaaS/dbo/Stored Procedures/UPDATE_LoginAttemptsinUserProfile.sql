CREATE PROCEDURE [dbo].[UPDATE_LoginAttemptsinUserProfile]
(

@UserID  INTEGER

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

DECLARE @COUNT INT
      SELECT @COUNT = LOGIN_ATMPT_CNT 
      FROM   USER_PRFL
      WHERE  [USER_ID]           =   @UserID 

IF @COUNT < 2 
BEGIN
	UPDATE USER_PRFL 
	SET    LOGIN_ATMPT_CNT     =   LOGIN_ATMPT_CNT + 1
	WHERE  [USER_ID]           =   @UserID
END
	
ELSE
BEGIN
	UPDATE USER_PRFL 
      SET    LCK_IND             =   1,
			 LOGIN_ATMPT_CNT	 =	 0
      WHERE  [USER_ID]           =   @UserID
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
END;



