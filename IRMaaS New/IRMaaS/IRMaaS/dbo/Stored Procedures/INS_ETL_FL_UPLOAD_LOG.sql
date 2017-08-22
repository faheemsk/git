﻿
  
 CREATE PROCEDURE [dbo].[INS_ETL_FL_UPLOAD_LOG](      
 @ORG_KEY				 INTEGER,
 @CLNT_ENGMT_CD          VARCHAR(30),  
 @SECUR_SRVC_CD          VARCHAR(10),
 @SRC_KEY				 INTEGER,  
 @FL_STS_KEY             INTEGER,
 @UPLOAD_USER_ID         INTEGER,
 @FL_NM					 VARCHAR(150),
 @FL_FLDR_PTH			 VARCHAR(500),  
 @FL_STS_COMMT			 VARCHAR(1000) 
 
   
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
      
   INSERT ETL_FL_UPLOAD_LOG(ORG_KEY,CLNT_ENGMT_CD,SECUR_SRVC_CD,
   SRC_KEY,FL_STS_KEY,UPLOAD_USER_ID,FL_NM,FL_FLDR_PTH,FL_STS_COMMT,FL_UPLOAD_DT) VALUES  
   (@ORG_KEY,@CLNT_ENGMT_CD,@SECUR_SRVC_CD,
   @SRC_KEY,@FL_STS_KEY,@UPLOAD_USER_ID,@FL_NM,@FL_FLDR_PTH,@FL_STS_COMMT,GETDATE())  
  
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




