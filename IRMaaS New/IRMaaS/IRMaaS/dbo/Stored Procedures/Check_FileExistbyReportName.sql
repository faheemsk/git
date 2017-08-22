CREATE PROCEDURE [dbo].[Check_FileExistbyReportName]
(
       @FL_NM               VARCHAR(150),
       @RPT_NM_KEY          INTEGER,
       @CLNT_ENGMT_CD       VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	COUNT(FL_NM) [COUNT] 
FROM    RPT_FL_UPLOAD_LOG A
JOIN	RPT_NM            B
ON      A.RPT_NM_KEY	= B.RPT_NM_KEY
WHERE   FL_NM			= @FL_NM
AND     B.RPT_NM_KEY	= @RPT_NM_KEY 
AND     A.CLNT_ENGMT_CD = @CLNT_ENGMT_CD
AND		ROW_STS_KEY         = 1

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