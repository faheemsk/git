
/******************************
	** Desc: To load REG_CMPLN table
	** Auth: Prakash Selvaraj
	** Date: 06/20/2016
	**************************
	** Change History
	**************************
	**  Date	        Author                  Description	
	**  06/20/2016        PS                  Inital Version
	**  07/11/2016        PS				Added Try-Catch to capture the errors
	**************************************/

/*
EXEC [dbo].[MERGE_REG_CMPLN]

*/




CREATE PROCEDURE [dbo].[MERGE_REG_CMPLN]
  @ins_row_cnt int output,
  @upd_row_cnt int output
AS
BEGIN


declare  @mergeResultsTable table (MergeAction VARCHAR(20));


	
BEGIN TRY

	SET NOCOUNT ON


		MERGE DBO.REG_CMPLN AS T
		USING STG.REG_CMPLN_STG AS S
		ON		S.REG_CMPLN_CD = T.REG_CMPLN_CD
			AND S.REG_CMPLN_VER = T.REG_CMPLN_VER
		WHEN NOT MATCHED BY TARGET 
    
			THEN INSERT
			(REG_CMPLN_CD, 
			REG_CMPLN_VER,
			REG_CMPLN_NM, 
			REG_CMPLN_DESC, 
			REG_CMPLN_PUBL_DT, 
			CREAT_DT, 
			CREAT_USER_ID) 
	
			VALUES
	
			(S.REG_CMPLN_CD, 
			S.REG_CMPLN_VER,
			S.REG_CMPLN_NM, 
			S.REG_CMPLN_DESC, 
			S.REG_CMPLN_PUBL_DT, 
			GETDATE(), 
			2)

		WHEN MATCHED 
    
			THEN UPDATE 
			SET T.REG_CMPLN_NM = S.REG_CMPLN_NM, 
			T.REG_CMPLN_DESC = S.REG_CMPLN_DESC, 
			T.REG_CMPLN_PUBL_DT = S.REG_CMPLN_PUBL_DT, 
			T.UPDT_DT = GETDATE(), 
			UPDT_USER_ID = 2

		output $action into @mergeResultsTable; 

			select @ins_row_cnt = [INSERT],
				   @upd_row_cnt = [UPDATE]
			  from (select 'NOOP' MergeAction -- row for null merge into null
					 union all
					select * from @mergeResultsTable) mergeResultsPlusEmptyRow     
			 pivot (count(MergeAction) 
			   for MergeAction in ([INSERT],[UPDATE],[DELETE])) 
				as mergeResultsPivot;

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



