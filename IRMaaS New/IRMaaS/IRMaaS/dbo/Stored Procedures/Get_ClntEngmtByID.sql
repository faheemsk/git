
CREATE PROCEDURE [dbo].[Get_ClntEngmtByID]
(
      @CLNT_ENGMT_CD	VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


SELECT      A.CLNT_ENGMT_CD, A.CLNT_ORG_KEY, A.SECUR_PKG_CD, B.SECUR_PKG_NM, dbo.fnGetParentOrgNameID(A.CLNT_ORG_KEY) ClientName,A.ENGMT_STS_KEY,
			 dbo.fnGetMasterLkpNameByID(A.ROW_STS_KEY) ROW_STS_VAL, A.ROW_STS_KEY, CLNT_ENGMT_CD, A.CLNT_ENGMT_NM,  
            A.AGR_DT, A.ENGMT_STRT_DT, A.ENGMT_EST_END_DT, A.CLNT_ENGMT_DESC, A.ENGMT_COMMT, A.CREAT_DT, A.CREAT_USER_ID, A.UPDT_DT, 
			A.UPDT_USER_ID,dbo.fnGetParentOrgNameID(C.PAR_ORG_KEY) [Parent Client Name],
			dbo.fnGetMasterLkpNameByID(C.ORG_TYP_KEY) OrgType,A.CLNT_PUBL_DT

FROM    	CLNT_ENGMT			 A
JOIN    	SECUR_PKG			 B
ON			A.SECUR_PKG_CD	   = B.SECUR_PKG_CD
JOIN        ORG                  C
ON          A.CLNT_ORG_KEY    =  C.ORG_KEY
WHERE       CLNT_ENGMT_CD     = @CLNT_ENGMT_CD


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
END;


