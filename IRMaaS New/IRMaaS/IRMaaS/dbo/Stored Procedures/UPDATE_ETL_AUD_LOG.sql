
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
EXEC [dbo].[UPDATE_ETL_AUD_LOG]    
@ETL_AUD_LOG_KEY = '1359'
 ,@JOB_END_DT         = NULL
 ,@JOB_STS       = 'IN PROGRESS'
 ,@SRC_ROW_CNT     =  4
 ,@TGT_INSRT_ROW_CNT = 4
 ,@TGT_UPDT_ROW_CNT = 1 
 ,@TGT_REJ_ROW_CNT  = 2

*/
	
CREATE PROCEDURE [dbo].[UPDATE_ETL_AUD_LOG]
(
	@ETL_AUD_LOG_KEY         INT
	,@JOB_END_DT         DATETIME
	,@JOB_STS       VARCHAR(100)
	,@SRC_ROW_CNT     INT
	,@TGT_INSRT_ROW_CNT     INT
	,@TGT_UPDT_ROW_CNT    INT
	,@TGT_REJ_ROW_CNT    INT
)
AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRY
		
		IF (@JOB_END_DT IS NULL) SELECT @JOB_END_DT = GETDATE();

		UPDATE [dbo].[ETL_AUD_LOG]
			   SET [JOB_END_DT] = @JOB_END_DT
			   ,[JOB_STS] = @JOB_STS
			   ,[SRC_ROW_CNT] = @SRC_ROW_CNT
			   ,[TGT_INSRT_ROW_CNT] = @TGT_INSRT_ROW_CNT
			   ,[TGT_UPDT_ROW_CNT] = @TGT_UPDT_ROW_CNT
			   ,[TGT_REJ_ROW_CNT] = @TGT_REJ_ROW_CNT
		WHERE ETL_AUD_LOG_KEY = @ETL_AUD_LOG_KEY

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
