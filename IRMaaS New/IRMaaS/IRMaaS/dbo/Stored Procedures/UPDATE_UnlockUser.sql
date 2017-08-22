CREATE PROCEDURE [dbo].[UPDATE_UnlockUser]
(
      @UserID			INT,
      @LockIndicator	INT,
      @RowStatusKey		INT
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

      UPDATE USER_PRFL
      SET    LCK_IND         	= @LockIndicator,
			 LOGIN_ATMPT_CNT 	= 0,
             ROW_STS_KEY	 	= @RowStatusKey,
			 USER_VERF_IND		= 0
      WHERE  [USER_ID]		 	= @UserID
      
      SELECT @@ROWCOUNT Retval
      
 
      
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






