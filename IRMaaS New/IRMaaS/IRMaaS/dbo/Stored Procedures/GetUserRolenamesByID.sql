CREATE PROCEDURE [dbo].[GetUserRolenamesByID]  
(  
       @UserID  INTEGER
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  


SELECT D.APPL_ROLE_KEY,D.[APPL_ROLE_NM]
FROM   USER_APPL_ROLE	     C
JOIN   [APPL_ROLE]			 D
ON	   C.APPL_ROLE_KEY     = D.APPL_ROLE_KEY
WHERE  C.[USER_ID]	       = @UserID
  
  
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







