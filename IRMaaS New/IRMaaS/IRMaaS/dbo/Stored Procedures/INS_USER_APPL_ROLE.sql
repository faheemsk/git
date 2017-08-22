/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_USER_APPL_ROLE
	** Desc: This procedure insert data into USER_APPL_ROLE table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_USER_APPL_ROLE](
	@FLAG				  CHAR(1),
	@ROW_STS_KEY          INTEGER,
	@APPL_ROLE_KEY        INTEGER,
	@USER_ID              INTEGER,
	@CREAT_USER_ID        INTEGER  

	)
	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

		IF @FLAG = 'I'
		BEGIN
			INSERT USER_APPL_ROLE(ROW_STS_KEY,APPL_ROLE_KEY,USER_ID,CREAT_DT,CREAT_USER_ID) VALUES
			(@ROW_STS_KEY,@APPL_ROLE_KEY,@USER_ID,GETDATE(),@CREAT_USER_ID)

			SELECT SCOPE_IDENTITY() AS RETVAL
		END
		
		IF @FLAG = 'D'
		BEGIN 
		 
			DELETE FROM USER_APPL_ROLE WHERE USER_ID = @USER_ID

			SELECT @@ROWCOUNT AS RETVAL
			
		END

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
		
	END







