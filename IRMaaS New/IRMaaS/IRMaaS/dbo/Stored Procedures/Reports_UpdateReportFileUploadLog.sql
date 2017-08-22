	CREATE PROCEDURE  [dbo].[Reports_UpdateReportFileUploadLog](
	@RPT_FL_UPLOAD_LOG_KEY INTEGER,
	@ROW_STS_KEY          INTEGER,
	@RPT_STS_KEY		  INTEGER,  
	@USER_ID			  INTEGER,
	@Flag				  VARCHAR(1)


	)
	AS
	BEGIN
		BEGIN TRY
			
		SET NOCOUNT ON

			IF @Flag = 'U'
			BEGIN
		
			UPDATE	RPT_FL_UPLOAD_LOG
			SET		ROW_STS_KEY		=		@ROW_STS_KEY,
					RPT_STS_KEY		=		@RPT_STS_KEY,
					UPDT_USER_ID	=		@USER_ID,
					UPDT_DT			=		GETDATE(),
					RPT_PUBL_DT		=		GETDATE()
			WHERE	RPT_FL_UPLOAD_LOG_KEY	=	@RPT_FL_UPLOAD_LOG_KEY

			SELECT @@ROWCOUNT AS RETVAL
			
			END
			IF @Flag = 'D'
			BEGIN
			DELETE	FROM RPT_FL_UPLOAD_LOG
			WHERE	RPT_FL_UPLOAD_LOG_KEY	=	@RPT_FL_UPLOAD_LOG_KEY

			END

			SELECT @@ROWCOUNT AS RETVAL

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
		
	END;







