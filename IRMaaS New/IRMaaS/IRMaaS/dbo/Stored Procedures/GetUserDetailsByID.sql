CREATE PROCEDURE [dbo].[GetUserDetailsByID]  
(  
       @UserID  INTEGER 
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  

  
SELECT A.[USER_ID] ID,USER_TYP_KEY,dbo.fnGetMasterLkpNameByID(USER_TYP_KEY) [User Type],B.ORG_NM [Organization Name],
	   A.ORG_KEY,FST_NM [First Name],MIDL_NM [Middle Name],LST_NM [Last Name],JOB_TITL_NM [Job Title],EMAIL_ID [EMAIL ID],
	   TEL_NBR [Phone Number],dbo.fnGetMasterLkpNameByID(A.ROW_STS_KEY)[Status], A.STS_COMMT_TXT,A.ROW_STS_KEY	
	     
FROM   USER_PRFL			 A
JOIN   ORG					 B
ON	   A.ORG_KEY		   = B.ORG_KEY
WHERE  A.[USER_ID]         = @UserID 
ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'')='' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC  
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







