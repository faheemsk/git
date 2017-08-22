CREATE PROCEDURE [dbo].[Report_GetRemediationPMWorkList]
(
       
       @ORG_KEY                   INTEGER,
       @OrgName                   VARCHAR(150),
       @CLNT_ENGMT_CD             VARCHAR(30),  
       @CLNT_ENGMT_NM             VARCHAR(150)  

)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON

              SELECT A.CLNT_ENGMT_CD,A.CLNT_ORG_KEY,B.ORG_NM,A.SECUR_PKG_CD,A.CLNT_ENGMT_NM,A.UPDT_DT,A.CREAT_DT 
              FROM   CLNT_ENGMT           A
              JOIN   ORG                  B
              ON     B.ORG_KEY     =      A.CLNT_ORG_KEY
              WHERE  A.ROW_STS_KEY =      1
              AND    A.CLNT_ORG_KEY = CASE WHEN @ORG_KEY= 0 THEN A.CLNT_ORG_KEY ELSE @ORG_KEY   END
              AND    ISNULL(B.ORG_NM,'') LIKE CASE WHEN @OrgName = '' THEN  ISNULL(B.ORG_NM,'') ELSE '%' + @OrgName + '%' END
			  AND    ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN   A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END 
			  AND    ISNULL(A.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN   A.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%'   END   
			  ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'') ='' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC

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