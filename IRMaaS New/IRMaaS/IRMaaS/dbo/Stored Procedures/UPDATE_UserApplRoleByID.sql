CREATE PROCEDURE [dbo].[UPDATE_UserApplRoleByID]
(
	@USER_APPL_ROLE_KEY		INTEGER,
	@ROW_STS_KEY            INTEGER,
	@APPL_ROLE_KEY          INTEGER,
	@USER_ID				INTEGER,
	@UPDT_USER_ID			INTEGER  
				  

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
      UPDATE dbo.USER_APPL_ROLE 
      SET    ROW_STS_KEY		 =   @ROW_STS_KEY,
			 APPL_ROLE_KEY       =   @APPL_ROLE_KEY,
	         USER_ID			 =   @USER_ID,
	         UPDT_DT			 =	 GETDATE(),
	         UPDT_USER_ID		 =   @UPDT_USER_ID
      WHERE  USER_APPL_ROLE_KEY  =   @USER_APPL_ROLE_KEY
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







