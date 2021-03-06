﻿/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_APPL_ROLE
	** Desc: This procedure insert data into APPL_ROLE table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          
	*******************************/

	CREATE PROCEDURE [dbo].[INS_APPL_ROLE](
	@ROW_STS_KEY          INTEGER,
	@APPL_ROLE_NM         VARCHAR(100),
	@APPL_ROLE_DESC       VARCHAR(1000),
	@CREAT_USER_ID        INTEGER,
	@STS_COMMT_TXT		   TEXT  


	)
	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

			INSERT APPL_ROLE(ROW_STS_KEY,APPL_ROLE_NM, APPL_ROLE_DESC, CREAT_DT,CREAT_USER_ID, UPDT_DT, UPDT_USER_ID,STS_COMMT_TXT ) VALUES
			(@ROW_STS_KEY,@APPL_ROLE_NM,@APPL_ROLE_DESC,GETDATE() ,@CREAT_USER_ID,GETDATE() ,@CREAT_USER_ID,@STS_COMMT_TXT)

			SELECT SCOPE_IDENTITY() AS RETVAL

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







