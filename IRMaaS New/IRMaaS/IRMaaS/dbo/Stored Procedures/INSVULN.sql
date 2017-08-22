CREATE PROCEDURE [dbo].[INSVULN](  
       @PVCFLAG                   VARCHAR(1),
       @VULN_NM                   VARCHAR(255),
       @VULN_CATGY_CD             VARCHAR(10),
       @CREAT_USER_ID             INT,
       @UPDT_USER_ID        INT
  
  
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
    IF @PVCFLAG ='I' 
       BEGIN
  
       INSERT VULN(VULN_NM,VULN_CATGY_CD,CREAT_DT,CREAT_USER_ID,UPDT_USER_ID) VALUES  
   (@VULN_NM,@VULN_CATGY_CD,GETDATE(),@CREAT_USER_ID,@UPDT_USER_ID)  
  
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
    
 END

 GO