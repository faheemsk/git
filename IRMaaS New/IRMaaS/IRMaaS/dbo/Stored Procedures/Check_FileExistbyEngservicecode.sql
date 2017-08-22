CREATE PROCEDURE [dbo].[Check_FileExistbyEngservicecode]
(
	@FL_NM			VARCHAR(150),
	@CLNT_ENGMT_CD	VARCHAR(30),
	@SECUR_SRVC_CD  VARCHAR(10)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON



SELECT  COUNT(FL_NM) [COUNT]
FROM    APPL_FL_UPLOAD_LOG			A
JOIN    MSTR_LKP                    B                    
ON      B.MSTR_LKP_KEY			=   A.FL_STS_KEY
WHERE   A.FL_NM                 =   @FL_NM
AND		A.CLNT_ENGMT_CD         =   @CLNT_ENGMT_CD       
AND     A.SECUR_SRVC_CD         =   @SECUR_SRVC_CD 
AND		B.LKP_ENTY_NM          <>  'Scan Failure'
AND		ROW_STS_KEY             =   1 



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











