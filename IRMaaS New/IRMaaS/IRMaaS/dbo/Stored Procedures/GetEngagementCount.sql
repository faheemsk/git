CREATE PROCEDURE [dbo].[GetEngagementCount]  
(  
 @CLNT_ORG_KEY           INTEGER,  
 @SECUR_PKG_CD           VARCHAR(10), 
 @AGR_DT                 DATETIME 
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  


SELECT COUNT(1) [COUNT] FROM   CLNT_ENGMT
WHERE   SECUR_PKG_CD = @SECUR_PKG_CD
AND     REPLACE(CONVERT(VARCHAR(10),AGR_DT,112),'-','') = @AGR_DT 


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

-- Upload procedure


