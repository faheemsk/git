
CREATE PROCEDURE [dbo].[UpdateFileStatusETLFailure]
(
      @Hours			VARCHAR(10)
	       
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON
	
	
	SELECT CLNT_ENGMT_CD,FL_NM  INTO #Upload FROM APPL_FL_UPLOAD_LOG
	WHERE   DATEDIFF(hour,FL_UPLOAD_DT, GETDATE()) >= @Hours
	AND		FL_STS_KEY   	=	38
	AND		ROW_STS_KEY		=	1
	
	
	UPDATE	APPL_FL_UPLOAD_LOG
	SET		FL_STS_KEY		=	40	
	WHERE   DATEDIFF(hour,FL_UPLOAD_DT, GETDATE()) >= @Hours
	AND		FL_STS_KEY   	=	38
	AND		ROW_STS_KEY		=	1

	SELECT * FROM #Upload

	DROP TABLE #Upload

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
