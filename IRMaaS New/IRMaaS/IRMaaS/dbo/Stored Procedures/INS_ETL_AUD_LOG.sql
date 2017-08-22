
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
EXEC [dbo].[INS_ETL_AUD_LOG]    
 @JOB_NM         = 'Load_CISCO_PEN_TST_STG'
 ,@JOB_STRT_DT         = NULL
 ,@JOB_END_DT         = NULL
 ,@JOB_STS       = 'IN PROGRESS'
 ,@SRC_FL_NM       = 'optum_example_csv_output.csv'
 ,@SRC_FL_FLDR_PTH  = '\\10.3.0.122\IRMaaS_nfs_Data\Max Care Clinics\MAX-HC-20160616\NV\optum_example_csv_output.csv'
 ,@TGT_TBL_NM       = 'CISCO_PEN_TST_STG'
 ,@SRC_ROW_CNT     =  0
 ,@TGT_INSRT_ROW_CNT = 0
 ,@TGT_UPDT_ROW_CNT = 0 
 ,@TGT_REJ_ROW_CNT  = 0

*/
	
CREATE PROCEDURE [dbo].[INS_ETL_AUD_LOG]
(
	@JOB_NM         VARCHAR(150)
	,@JOB_STRT_DT         DATETIME
	,@JOB_END_DT         DATETIME
	,@JOB_STS       VARCHAR(100)
	,@SRC_FL_NM       VARCHAR(150)
	,@SRC_FL_FLDR_PTH       VARCHAR(500)
	,@TGT_TBL_NM       VARCHAR(50)
	,@SRC_ROW_CNT     INT
	,@TGT_INSRT_ROW_CNT     INT
	,@TGT_UPDT_ROW_CNT    INT
	,@TGT_REJ_ROW_CNT    INT
	,@RETVAL INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRY

		IF (ISNULL(@JOB_STRT_DT,'') = '') SELECT @JOB_STRT_DT = GETDATE();

		INSERT INTO [dbo].[ETL_AUD_LOG]
			   ([JOB_NM]
			   ,[JOB_STRT_DT]
			   ,[JOB_END_DT]
			   ,[JOB_STS]
			   ,[SRC_FL_NM]
			   ,[SRC_FL_FLDR_PTH]
			   ,[TGT_TBL_NM]
			   ,[SRC_ROW_CNT]
			   ,[TGT_INSRT_ROW_CNT]
			   ,[TGT_UPDT_ROW_CNT]
			   ,[TGT_REJ_ROW_CNT])
		 VALUES
			   (
			   @JOB_NM
			   ,@JOB_STRT_DT
			   ,@JOB_END_DT
			   ,@JOB_STS
			   ,@SRC_FL_NM
			   ,@SRC_FL_FLDR_PTH
			   ,@TGT_TBL_NM
			   ,@SRC_ROW_CNT
			   ,@TGT_INSRT_ROW_CNT
			   ,@TGT_UPDT_ROW_CNT
			   ,@TGT_REJ_ROW_CNT)

		 SELECT @RETVAL = SCOPE_IDENTITY()
		 RETURN;

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

