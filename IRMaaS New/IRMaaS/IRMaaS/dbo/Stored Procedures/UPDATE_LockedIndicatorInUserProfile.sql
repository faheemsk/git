CREATE PROCEDURE [dbo].[UPDATE_LockedIndicatorInUserProfile]
(

@UserID  INTEGER,
@LockIndicator	INTEGER -- 0 for unlock /1 for lock

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
      UPDATE USER_PRFL 
      SET    LCK_IND             =   @LockIndicator
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







