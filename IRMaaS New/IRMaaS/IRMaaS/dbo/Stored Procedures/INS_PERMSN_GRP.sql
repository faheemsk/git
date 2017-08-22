/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_PERMSN_GRP
	** Desc: This procedure insert data into PERMSN_GRP table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_PERMSN_GRP](
	@ROW_STS_KEY          INTEGER,
	@PERMSN_GRP_NM        VARCHAR(100),
	@PERMSN_GRP_DESC      VARCHAR(1000),
	@CREAT_USER_ID        INTEGER,
	@STS_COMMT_TXT		  TEXT 

	)
	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

		
			INSERT PERMSN_GRP(ROW_STS_KEY,PERMSN_GRP_NM,PERMSN_GRP_DESC,CREAT_DT,CREAT_USER_ID,STS_COMMT_TXT) VALUES
			(@ROW_STS_KEY,@PERMSN_GRP_NM,@PERMSN_GRP_DESC,GETDATE(),@CREAT_USER_ID,@STS_COMMT_TXT)

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







