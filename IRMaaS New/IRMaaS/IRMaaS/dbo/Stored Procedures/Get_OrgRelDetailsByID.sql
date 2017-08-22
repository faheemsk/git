--DROP PROCEDURE Get_OrgRelDetailsByID  
CREATE PROCEDURE [dbo].[Get_OrgRelDetailsByID]  
(  
 @OrgID INTEGER  
)  
AS  
BEGIN  

BEGIN TRY  
SET NOCOUNT ON  



  
SELECT  A.ORG_KEY,A.ROW_STS_KEY,CASE WHEN A.ROW_STS_KEY=1 THEN 'Active' WHEN A.ROW_STS_KEY=2 THEN 'Inactive' END OrgStatus,  
  A.PAR_ORG_KEY,A.ORG_TYP_KEY,A.ORG_INDUS_KEY,A.ORG_NM,A.STR_ADR_1,A.STR_ADR_2,A.CTY_NM,A.ST_NM,CNTRY_NM,  
  A.PST_CD,A.ORG_DESC,C.ORG_REL_ID_KEY,C.SRC_KEY,B.LKP_ENTY_NM SourceName,C.SRC_CLNT_ID
FROM    ORG      A  
JOIN ORG_REL_ID    C  
ON  A.ORG_KEY  = C.ORG_KEY  
JOIN MSTR_LKP    B  
ON  C.SRC_KEY    = B.MSTR_LKP_KEY  
WHERE   A.ORG_KEY  = @OrgID  

  
  
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







