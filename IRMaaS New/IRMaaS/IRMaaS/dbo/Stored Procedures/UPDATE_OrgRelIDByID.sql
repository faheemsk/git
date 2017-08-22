CREATE PROCEDURE [dbo].[UPDATE_OrgRelIDByID]
(	
	@ORG_REL_ID_KEY				INTEGER,
	@ORG_KEY					INTEGER,
	@SRC_KEY					INTEGER,
	@SRC_CLNT_ID				VARCHAR(150),
	@UPDT_USER_ID				INTEGER,  
	@ROW_STS_KEY				INTEGER		  
)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

BEGIN 
      UPDATE dbo.ORG_REL_ID
      SET    ORG_KEY				=   @ORG_KEY,
			 SRC_KEY				=   @SRC_KEY,
			 SRC_CLNT_ID			=	@SRC_CLNT_ID,
			 UPDT_DT				=	GETDATE(),
	         UPDT_USER_ID			=   @UPDT_USER_ID,
			 ROW_STS_KEY			=   @ROW_STS_KEY
	         
      WHERE  ORG_REL_ID_KEY			=   @ORG_REL_ID_KEY
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







