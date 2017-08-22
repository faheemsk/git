/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: GetUserSessLogByID
       ** Desc: This procedure fetch data from USER_SESS_LOG table
       ** Auth: Prasad varma
       ** Date: 22/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    00/00/1999      xxxxx          
       *******************************/


CREATE PROCEDURE [dbo].[GetUserSessLogByID]  
(  
       @EMAIL_ID  VARCHAR(150) 
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  

  
	SELECT USER_SESS_INFO_KEY,USER_ID,EMAIL_ID,SESS_ID,LST_ACT_DT          
	FROM   dbo.USER_SESS_LOG
	WHERE  EMAIL_ID = @EMAIL_ID  


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







