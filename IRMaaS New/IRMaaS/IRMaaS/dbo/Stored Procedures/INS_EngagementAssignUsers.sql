
  
/******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: INS_EngagementAssignUsers  
 ** Desc: This procedure insert data into CLNT_ENGMT_USER_ASGN table  
 ** Auth: Prasad varma  
 ** Date: 27/4/2016  
 **************************  
 ** Change History  
 **************************  
 ** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  
  
 CREATE PROCEDURE [dbo].[INS_EngagementAssignUsers](  
 @CLNT_ENGMT_CD          VARCHAR(30),  
 @USER_ID                INTEGER,  
 @APPL_ROLE_KEY          INTEGER,  
 @CREAT_USER_ID          INTEGER
   
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
    
  
   INSERT CLNT_ENGMT_USER_ASGN(CLNT_ENGMT_CD,USER_ID,CREAT_DT,CREAT_USER_ID) VALUES  
   (@CLNT_ENGMT_CD,@USER_ID,GETDATE(),@CREAT_USER_ID)  
  
    SELECT @@ROWCOUNT AS RETVAL 
  
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
    
 END
 





