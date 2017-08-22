
/******************************
	** Desc: 
	** Auth: GM
	** Date: 06/07/2014
	**************************
	** Change History
	**************************
	**  Date	        Author                  Description	
	**  --------        GM                ------------------------------------
	**************************************/

/*

 EXEC [dbo].[INS_ETL_ERR_LOG]    
	@ETL_AUD_LOG_KEY         = 1367
	,@ERR_LOG_DT = NULL
	,@ERR_CD         =  '100'
	,@ERR_MSG_TXT         = 'TEST ERR MSG TXT'
	,@ERR_REC_TXT       = 'TEST ERR REC TXT'


*/
	
CREATE PROCEDURE [dbo].[INS_ETL_ERR_LOG]
(
	@ETL_AUD_LOG_KEY         INT
	,@ERR_LOG_DT         DATETIME
	,@ERR_CD       VARCHAR(150)
	,@ERR_MSG_TXT       VARCHAR(1000)
	,@ERR_REC_TXT       VARCHAR(2000)
)
AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRY

		IF (ISNULL(@ERR_LOG_DT,'') = '') SELECT @ERR_LOG_DT = GETDATE();

		INSERT INTO [dbo].[ETL_ERR_LOG]
           ([ETL_AUD_LOG_KEY]
           ,[ERR_LOG_DT]
           ,[ERR_CD]
           ,[ERR_MSG_TXT]
           ,[ERR_REC_TXT])
		 VALUES
			(
			@ETL_AUD_LOG_KEY
			,@ERR_LOG_DT
			,@ERR_CD
			,@ERR_MSG_TXT
			,@ERR_REC_TXT
			)

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

END

