
       /******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: INS_CLNT_SECUR_SRVC_ENGMT  
 ** Desc: This procedure insert data into CLNT_SECUR_SRVC_ENGMT table  
 ** Auth: Prasad varma  
 ** Date: 27/4/2016  
 **************************  
 ** Change History  
 **************************  
 ** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  
  
 CREATE PROCEDURE [dbo].[INS_CLNT_SECUR_SRVC_ENGMT](  
 @CLNT_ENGMT_CD         VARCHAR(30),  
 @SECUR_SRVC_CD         VARCHAR(10), 
 @SRVC_ENGMT_STS_KEY    INTEGER,  
 @ROW_STS_KEY           INTEGER,   
 @SRVC_EST_STRT_DT      DATETIME,
 @SRVC_EST_END_DT       DATETIME,
 @FL_LCK_IND			INTEGER,
 @CREAT_USER_ID         INTEGER
   
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
    
  
   INSERT CLNT_SECUR_SRVC_ENGMT(CLNT_ENGMT_CD,SECUR_SRVC_CD,SRVC_ENGMT_STS_KEY,ROW_STS_KEY,
    SRVC_EST_STRT_DT,SRVC_EST_END_DT,CREAT_DT,FL_LCK_IND,CREAT_USER_ID) VALUES  
   (@CLNT_ENGMT_CD,@SECUR_SRVC_CD,@SRVC_ENGMT_STS_KEY,@ROW_STS_KEY,
    @SRVC_EST_STRT_DT,@SRVC_EST_END_DT,GETDATE(),@FL_LCK_IND,@CREAT_USER_ID ) 
  
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






