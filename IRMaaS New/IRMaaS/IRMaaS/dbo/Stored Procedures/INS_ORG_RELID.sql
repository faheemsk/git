﻿/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_ORG_RELID
	** Desc: This procedure insert data into ORG_REL_ID table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_ORG_RELID](
	@FLAG			  CHAR(1),
	@ORG_KEY          INTEGER,
	@SRC_KEY          INTEGER,
	@SRC_CLNT_ID      VARCHAR(150),
	@CREAT_USER_ID    INTEGER  

	)
	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

			IF @FLAG = 'I'
		BEGIN
			INSERT ORG_REL_ID(ORG_KEY,SRC_KEY,SRC_CLNT_ID,CREAT_DT,CREAT_USER_ID,ROW_STS_KEY ) VALUES
			(@ORG_KEY,@SRC_KEY,@SRC_CLNT_ID,GETDATE(),@CREAT_USER_ID,1)

			SELECT SCOPE_IDENTITY() AS RETVAL
		END
		
		IF @FLAG = 'D'
		BEGIN 
		 
			DELETE FROM ORG_REL_ID WHERE ORG_KEY = @ORG_KEY

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
		
	END;







