CREATE PROCEDURE [dbo].[UPDOrgSchmaInd]  
( 
	@ORG_KEY				INTEGER,  
	@UPDT_USER_ID			INTEGER,
	@CREAT_ORG_SCHM_IND		CHAR  
)  
  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  
  
BEGIN   
      UPDATE dbo.ORG  
      SET    CREAT_ORG_SCHM_IND  =  @CREAT_ORG_SCHM_IND,
			 UPDT_DT			 =  GETDATE(),  
			 UPDT_USER_ID		 =  @UPDT_USER_ID
      WHERE  ORG_KEY			 =  @ORG_KEY  
        
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
END


