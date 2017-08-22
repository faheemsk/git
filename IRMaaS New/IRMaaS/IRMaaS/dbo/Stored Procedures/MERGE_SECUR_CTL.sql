/******************************
	** Desc: To load SECUR_CTL table
	** Auth: Prakash Selvaraj
	** Date: 06/28/2016
	**************************
	** Change History
	**************************
	**  Date	        Author                  Description	
	**  06/20/2016     Prakash Selvaraj        Inital Version
	**  07/11/2016     Prakash Selvaraj        Added Try-Catch to capture the errors
	**************************************/

/*

EXEC MERGE_SECUR_CTL ? OUTPUT , ? OUTPUT

*/




CREATE PROCEDURE [dbo].[MERGE_SECUR_CTL]
  @ins_row_cnt int output,
  @upd_row_cnt int output
AS
BEGIN


declare  @mergeResultsTable table (MergeAction VARCHAR(20));

BEGIN TRY
	SET NOCOUNT ON

		MERGE DBO.SECUR_CTL AS T
		USING 
		(
		SELECT
		  STG.REG_CMPLN_CD, 
		  STG.REG_CMPLN_VER,
		  STG.SECUR_CTL_CD,
		  STG.SECUR_CTL_FAM_CD,
		  STG.SECUR_CTL_FAM_NM,
		  STG.SECUR_CTL_FAM_DESC,
		  STG.SECUR_OBJ_CD,
		  STG.SECUR_OBJ_NM,
		  STG.SECUR_OBJ_DESC,
		  STG.SECUR_CTL_NM,
		  STG.SECUR_CTL_DESC,
		  STG.CTL_TYP_NM,
		  STG.TPC_TXT,
		  STG.SECUR_CTL_IMPL,
		  STG.RQR_FOR_CERT_IND
		 FROM 
		 STG.SECUR_CTL_STG STG
		 JOIN dbo.REG_CMPLN REG
		 ON STG.REG_CMPLN_CD = REG.REG_CMPLN_CD
		 AND STG.REG_CMPLN_VER = REG.REG_CMPLN_VER
		  )
		   AS S
		ON S.REG_CMPLN_CD = T.REG_CMPLN_CD
		 AND S.REG_CMPLN_VER = T.REG_CMPLN_VER
		 AND S.SECUR_CTL_CD = T.SECUR_CTL_CD
		WHEN NOT MATCHED BY TARGET 
			THEN 
		 INSERT
		  (
		  REG_CMPLN_CD, 
		  REG_CMPLN_VER,
		  SECUR_CTL_CD,
		  SECUR_CTL_FAM_CD,
		  SECUR_CTL_FAM_NM,
		  SECUR_CTL_FAM_DESC,
		  SECUR_OBJ_CD,
		  SECUR_OBJ_NM,
		  SECUR_OBJ_DESC,
		  SECUR_CTL_NM,
		  SECUR_CTL_DESC,
		  CTL_TYP_NM,
		  TPC_TXT,
		  SECUR_CTL_IMPL,
		  RQR_FOR_CERT_IND,
		  CREAT_DT,
		  CREAT_USER_ID
		  ) 
		 VALUES
		  (
		  S.REG_CMPLN_CD, 
		  S.REG_CMPLN_VER,
		  S.SECUR_CTL_CD,
		  S.SECUR_CTL_FAM_CD,
		  S.SECUR_CTL_FAM_NM,
		  S.SECUR_CTL_FAM_DESC,
		  S.SECUR_OBJ_CD,
		  S.SECUR_OBJ_NM,
		  S.SECUR_OBJ_DESC,
		  S.SECUR_CTL_NM,
		  S.SECUR_CTL_DESC,
		  S.CTL_TYP_NM,
		  S.TPC_TXT, 
		  S.SECUR_CTL_IMPL,
		  S.RQR_FOR_CERT_IND,
		  GETDATE(),
		  2
		  )
		WHEN MATCHED 
			THEN UPDATE  
		  SET T.SECUR_CTL_FAM_CD = S.SECUR_CTL_FAM_CD, 
		   T.SECUR_CTL_FAM_NM = S.SECUR_CTL_FAM_NM, 
		   T.SECUR_CTL_FAM_DESC = S.SECUR_CTL_FAM_DESC,
		   T.SECUR_OBJ_CD = S.SECUR_OBJ_CD,
		   T.SECUR_OBJ_NM = S.SECUR_OBJ_NM,
		   T.SECUR_OBJ_DESC = S.SECUR_OBJ_DESC,
		   T.SECUR_CTL_NM = S.SECUR_CTL_NM,
		   T.SECUR_CTL_DESC = S.SECUR_CTL_DESC,
		   T.CTL_TYP_NM = S.CTL_TYP_NM,
		   T.TPC_TXT = S.TPC_TXT,
		   T.SECUR_CTL_IMPL = S.SECUR_CTL_IMPL,
		   T.RQR_FOR_CERT_IND = S.RQR_FOR_CERT_IND,
		   T.UPDT_DT = GETDATE(),
		   T.UPDT_USER_ID = 2


		output $action into @mergeResultsTable; 


		SELECT  
			@ins_row_cnt = [INSERT],
			@upd_row_cnt = [UPDATE]
		FROM 
			(
			SELECT 'NOOP' MergeAction -- row for null merge into null
			UNION ALL
			SELECT * FROM @mergeResultsTable
			) mergeResultsPlusEmptyRow     

			pivot ( count(MergeAction) for MergeAction in ([INSERT],[UPDATE],[DELETE])
				  ) as mergeResultsPivot;

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


