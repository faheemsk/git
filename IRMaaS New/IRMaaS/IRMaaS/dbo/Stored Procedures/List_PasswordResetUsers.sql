CREATE PROCEDURE [dbo].[List_PasswordResetUsers]
(
   @Days         INTEGER
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
					 SELECT USER_ID, USER_TYP_KEY, FST_NM + '' + LST_NM UserName ,EMAIL_ID
                     FROM	USER_PRFL     
                     WHERE  DATEDIFF(DAY,PSWD_RSET_DT,GETDATE()) >  @Days    
                     AND    ROW_STS_KEY = 1
                     
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

