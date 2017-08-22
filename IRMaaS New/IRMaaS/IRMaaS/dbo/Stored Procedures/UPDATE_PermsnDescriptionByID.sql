CREATE PROCEDURE [dbo].[UPDATE_PermsnDescriptionByID]
(	
	@PERMSN_KEY				INTEGER,
	@PERMSN_DESC			VARCHAR(1000),
	@UPDT_USER_ID			INTEGER  
			  
)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

BEGIN 
      UPDATE dbo.PERMSN
      SET    PERMSN_DESC		=	@PERMSN_DESC,
			 UPDT_DT			=	GETDATE(),
	         UPDT_USER_ID		=   @UPDT_USER_ID
      WHERE  PERMSN_KEY			=   @PERMSN_KEY
      
      SELECT @@ROWCOUNT AS RETVAL

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







