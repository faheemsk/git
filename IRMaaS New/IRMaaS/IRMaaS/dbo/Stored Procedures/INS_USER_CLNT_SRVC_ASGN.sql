
/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_CLNT_SECUR_SRVC_ENGMTR
	** Desc: This procedure insert data into CLNT_SECUR_SRVC_ENGMTR table
	** Auth: Prasad varma
	** Date: 20/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/
	

	CREATE PROCEDURE [dbo].[INS_USER_CLNT_SRVC_ASGN](
	@ROW_STS_KEY				 INTEGER,
	@USER_ID					 INTEGER,
	@USER_STRT_DT				 DATETIME,
	@USER_END_DT				 DATETIME,
	@CREAT_USER_ID				 INTEGER,
	@SECUR_SRVC_CD				 VARCHAR(10),
	@CLNT_ENGMT_CD				 VARCHAR(30)
	
	)

	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

		    INSERT USER_CLNT_SRVC_ASGN(ROW_STS_KEY	,USER_ID,USER_STRT_DT,USER_END_DT,CREAT_DT,CREAT_USER_ID,SECUR_SRVC_CD,CLNT_ENGMT_CD) VALUES
			(@ROW_STS_KEY,@USER_ID,@USER_STRT_DT,@USER_END_DT,GETDATE(),@CREAT_USER_ID,@SECUR_SRVC_CD,@CLNT_ENGMT_CD)

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





