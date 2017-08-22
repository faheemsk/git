
CREATE PROCEDURE  [dbo].[Report_UpdateFileStatus]
(
       @RPT_FL_UPLOAD_LOG_KEY	INTEGER,
	   @RPT_STS_KEY				INTEGER
	      
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON
              
			  UPDATE	RPT_FL_UPLOAD_LOG
			  SET		RPT_STS_KEY		=	@RPT_STS_KEY,
						UPDT_DT			=	GETDATE()			  
			  WHERE		RPT_FL_UPLOAD_LOG_KEY	=	@RPT_FL_UPLOAD_LOG_KEY                   
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


