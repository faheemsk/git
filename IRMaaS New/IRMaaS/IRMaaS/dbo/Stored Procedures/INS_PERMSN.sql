/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_PERMSN
	** Desc: This procedure insert data into PERMSN table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_PERMSN](
	@PAR_PERMSN_KEY          INTEGER,
	@PERMSN_TYP_KEY          INTEGER,
	@ROW_STS_KEY             INTEGER,
	@PERMSN_NM               VARCHAR(100),
	@PERMSN_DESC             VARCHAR(1000),
	@DSPL_TXT                VARCHAR(100),
	@CHLD_XST_IND            INTEGER,
	@SEQ_ORDR                INTEGER,
	@CREAT_USER_ID           INTEGER  

	)
	AS
	BEGIN
	    BEGIN TRY

		SET NOCOUNT ON

		
			INSERT PERMSN(PAR_PERMSN_KEY,PERMSN_TYP_KEY,ROW_STS_KEY,PERMSN_NM,PERMSN_DESC,DSPL_TXT,CHLD_XST_IND,
			SEQ_ORDR,CREAT_DT,CREAT_USER_ID) VALUES
			(@PAR_PERMSN_KEY,@PERMSN_TYP_KEY,@ROW_STS_KEY,@PERMSN_NM,@PERMSN_DESC,@DSPL_TXT,@CHLD_XST_IND,
			@SEQ_ORDR,GETDATE(),@CREAT_USER_ID)

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







