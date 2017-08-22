
CREATE PROCEDURE [dbo].[Get_ClntSecurSrvcEngmtByID]
(
      @CLNT_ENGMT_CD	VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

       SELECT		 A.CLNT_ENGMT_CD, A.SECUR_SRVC_CD, SRVC_ENGMT_STS_KEY, B.SECUR_SRVC_NM, A.ROW_STS_KEY, 
                     SRVC_EST_STRT_DT, SRVC_EST_END_DT, A.CREAT_DT, A.CREAT_USER_ID, A.UPDT_DT, A.UPDT_USER_ID,
                     COUNT(C.APPL_FL_UPLOAD_LOG_KEY) FileCount


       FROM          CLNT_SECUR_SRVC_ENGMT      A
       JOIN          SECUR_SRVC                 B
       ON            A.SECUR_SRVC_CD         =  B.SECUR_SRVC_CD
       LEFT JOIN     APPL_FL_UPLOAD_LOG         C
       ON            A.SECUR_SRVC_CD         =  C.SECUR_SRVC_CD
       AND           C.CLNT_ENGMT_CD         =  @CLNT_ENGMT_CD
       WHERE         A.CLNT_ENGMT_CD         =  @CLNT_ENGMT_CD
       GROUP BY      A.CLNT_ENGMT_CD, A.SECUR_SRVC_CD, SRVC_ENGMT_STS_KEY, B.SECUR_SRVC_NM, A.ROW_STS_KEY, 
                     SRVC_EST_STRT_DT, SRVC_EST_END_DT, A.CREAT_DT, A.CREAT_USER_ID, A.UPDT_DT, A.UPDT_USER_ID



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

