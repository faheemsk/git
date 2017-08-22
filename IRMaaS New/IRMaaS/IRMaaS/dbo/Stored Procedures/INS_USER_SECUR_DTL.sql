/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_USER_SECUR_DTL
	** Desc: This procedure insert data into USER_SECUR_DTL table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_USER_SECUR_DTL](
	@ROW_STS_KEY        INTEGER,
	@USER_ID			INTEGER,
	@SECUR_QUES_KEY     INTEGER,
	@ANS_TXT            VARCHAR(1000),
	@SEQ_ORDR_NBR       INTEGER,
	@CREAT_USER_ID      INTEGER
	)

	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

			INSERT USER_SECUR_DTL(ROW_STS_KEY,USER_ID,SECUR_QUES_KEY,ANS_TXT,SEQ_ORDR_NBR,CREAT_DT,CREAT_USER_ID) VALUES
			(@ROW_STS_KEY,@USER_ID,@SECUR_QUES_KEY,@ANS_TXT,@SEQ_ORDR_NBR,GETDATE(),@CREAT_USER_ID)

			SELECT SCOPE_IDENTITY() AS RETVAL

		END TRY

		BEGIN CATCH
			IF @@TRANCOUNT > 0
			
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







