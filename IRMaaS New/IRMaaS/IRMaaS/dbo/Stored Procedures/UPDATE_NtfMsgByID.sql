CREATE PROCEDURE [dbo].[UPDATE_NtfMsgByID]
(
	@NTF_MSG_KEY				INTEGER,
	@ROW_STS_KEY				INTEGER,
	@NTF_TYP_KEY				VARCHAR(255),
	@MSG_SBJ_TXT				VARCHAR(255),
	@MSG_CNTN_TXT				TEXT,
	@UPDT_USER_ID				INTEGER  
				  

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
      UPDATE dbo.NTF_MSG
      SET    ROW_STS_KEY				=   @ROW_STS_KEY,
			 NTF_TYP_NM				    =   @NTF_TYP_KEY,
			 MSG_SBJ_TXT				=	@MSG_SBJ_TXT,
			 MSG_CNTN_TXT				=   @MSG_CNTN_TXT,
			 UPDT_DT					=	GETDATE(),
	         UPDT_USER_ID				=   @UPDT_USER_ID
      WHERE  NTF_MSG_KEY				=   @NTF_MSG_KEY
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







