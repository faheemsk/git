/******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: INS_CLNT_ENGMT  
 ** Desc: This procedure insert data into CLNT_ENGMT table  
 ** Auth: Prasad varma  
 ** Date: 27/4/2016  
 **************************  
 ** Change History  
 **************************  
 -- ALTER SECUR_PKG_KEY integer name and data type changed
   as SECUR_PKG_CD varchar(10)

** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  
  
 CREATE PROCEDURE [dbo].[INS_CLNT_ENGMT](      
 @CLNT_ORG_KEY           INTEGER,  
 @SECUR_PKG_CD           VARCHAR(10),  
 @ENGMT_STS_KEY          INTEGER,  
 @ROW_STS_KEY            INTEGER,  
 @CLNT_ENGMT_CD          VARCHAR(30),  
 @CLNT_ENGMT_NM          VARCHAR(150),  
 @AGR_DT                 DATETIME,  
 @ENGMT_STRT_DT          DATETIME,     
 @ENGMT_EST_END_DT       DATETIME,    
 @CLNT_ENGMT_DESC        VARCHAR(1000), 
 @ENGMT_COMMT             TEXT,
 @CREAT_USER_ID          INTEGER
   
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
    
  
   INSERT CLNT_ENGMT(CLNT_ORG_KEY,SECUR_PKG_CD,ENGMT_STS_KEY,ROW_STS_KEY,CLNT_ENGMT_CD,CLNT_ENGMT_NM,AGR_DT,
   ENGMT_STRT_DT,ENGMT_EST_END_DT,CLNT_ENGMT_DESC,ENGMT_COMMT,CREAT_DT,CREAT_USER_ID) VALUES  
   (@CLNT_ORG_KEY,@SECUR_PKG_CD,@ENGMT_STS_KEY,@ROW_STS_KEY,@CLNT_ENGMT_CD,@CLNT_ENGMT_NM,@AGR_DT,
   @ENGMT_STRT_DT,@ENGMT_EST_END_DT,@CLNT_ENGMT_DESC,@ENGMT_COMMT,GETDATE(),@CREAT_USER_ID)  
  
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


