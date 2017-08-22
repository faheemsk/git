CREATE PROCEDURE [dbo].[GetAppRoleDetailsByID]  
(  
       @APPLROLEKEY  INTEGER
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  


SELECT APPL_ROLE_KEY, ROW_STS_KEY, APPL_ROLE_NM, APPL_ROLE_DESC,STS_COMMT_TXT
FROM   APPL_ROLE
WHERE  APPL_ROLE_KEY = @APPLROLEKEY

 
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


