/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_USER_PRFL
	** Desc: This procedure insert data into USER_PRFL table
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/

	CREATE PROCEDURE [dbo].[INS_USER_PRFL](
	@ORG_KEY            INTEGER,
	@USER_TYP_KEY       INTEGER,
	@ROW_STS_KEY        INTEGER, 
	@FST_NM             VARCHAR(150), 
	@LST_NM             VARCHAR(150), 
	@MIDL_NM            VARCHAR(150), 
	@JOB_TITL_NM        VARCHAR(150), 
	@EMAIL              VARCHAR(150), 
	@TEL_NBR            VARCHAR(20),
	@USER_VERF_IND		INTEGER, -- DEFAULT 0
	@LCK_IND            INTEGER, -- DEFAULT 0
	@LOGIN_ATMPT_CNT    INTEGER,
	@CREAT_USER_ID      INTEGER,
	@MAC_ADR_NM			VARCHAR(50),
	@STS_COMMT_TXT		TEXT 
	)

	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

		    INSERT USER_PRFL(ORG_KEY,USER_TYP_KEY,ROW_STS_KEY,FST_NM,LST_NM,MIDL_NM,JOB_TITL_NM,EMAIL_ID,TEL_NBR,USER_VERF_IND,LCK_IND,LOGIN_ATMPT_CNT,
			CREAT_DT,CREAT_USER_ID,MAC_ADR_NM,STS_COMMT_TXT) VALUES
			(@ORG_KEY,@USER_TYP_KEY,@ROW_STS_KEY,@FST_NM,@LST_NM,@MIDL_NM,@JOB_TITL_NM,@EMAIL,@TEL_NBR,@USER_VERF_IND,@LCK_IND,@LOGIN_ATMPT_CNT,
			GETDATE(),@CREAT_USER_ID,@MAC_ADR_NM,@STS_COMMT_TXT)

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







