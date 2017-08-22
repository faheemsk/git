
/*************************************
	** Desc: To load SECUR_CTL_MAP table
	** Auth: Prakash Selvaraj
	** Date: 06/28/2016
	**************************
	** Change History
	**************************
	**  Date	        Author                  Description	
	**  06/28/2016     Prakash Selvaraj        InitIal Version
	**  07/11/2016     Prakash Selvaraj        Added Try-Catch to capture the errors
**************************************/

/*

EXEC MERGE_SECUR_CTL_MAP ? OUTPUT , ? OUTPUT

*/




CREATE PROCEDURE [dbo].[MERGE_SECUR_CTL_MAP]
  @ins_row_cnt int output,
  @upd_row_cnt int output
AS

BEGIN

declare  @mergeResultsTable table (MergeAction VARCHAR(20));


BEGIN TRY

	SET NOCOUNT ON


		MERGE DBO.SECUR_CTL_MAP AS T

		USING 
		(
				SELECT
					PRI_REG_CMPLN_CD,
					PRI_REG_CMPLN_VER,
					PRI_SECUR_CTL_CD,
					SEC_REG_CMPLN_CD,
					SEC_REG_CMPLN_VER,
					SEC_SECUR_CTL_CD
				FROM
				STG.SECUR_CTL_MAP_STG stg
				JOIN dbo.secur_ctl pri
					on 		stg.PRI_REG_CMPLN_CD = pri.REG_CMPLN_CD
						and stg.PRI_REG_CMPLN_VER = pri.REG_CMPLN_VER
						and stg.PRI_SECUR_CTL_CD = pri.SECUR_CTL_CD
				join dbo.secur_ctl sec
					on  	stg.SEC_REG_CMPLN_CD = sec.REG_CMPLN_CD
						and stg.SEC_REG_CMPLN_VER = sec.REG_CMPLN_VER
						and stg.SEC_SECUR_CTL_CD = sec.SECUR_CTL_CD
		)

		AS S

		ON  S.PRI_REG_CMPLN_CD  = T.PRI_REG_CMPLN_CD
		 AND S.PRI_REG_CMPLN_VER = T.PRI_REG_CMPLN_VER
		 AND S.PRI_SECUR_CTL_CD  = T.PRI_SECUR_CTL_CD
		 AND S.SEC_REG_CMPLN_CD  = T.SEC_REG_CMPLN_CD
		 AND S.SEC_REG_CMPLN_VER = T.SEC_REG_CMPLN_VER
		 AND S.SEC_SECUR_CTL_CD  = T.SEC_SECUR_CTL_CD

		WHEN NOT MATCHED BY TARGET 

			THEN INSERT 

					( PRI_REG_CMPLN_CD, 
					  PRI_REG_CMPLN_VER,
					  PRI_SECUR_CTL_CD, 
					  SEC_REG_CMPLN_CD, 
					  SEC_REG_CMPLN_VER,
					  SEC_SECUR_CTL_CD, 
					  CREAT_DT, 
					  CREAT_USER_ID
					) 
				  VALUES
					( S.PRI_REG_CMPLN_CD, 
					  S.PRI_REG_CMPLN_VER,
					  S.PRI_SECUR_CTL_CD, 
					  S.SEC_REG_CMPLN_CD, 
					  S.SEC_REG_CMPLN_VER,
					  S.SEC_SECUR_CTL_CD, 
					  GETDATE(),
					  2
					)
		WHEN MATCHED 
    
			THEN UPDATE 
		  
				  SET T.UPDT_DT = GETDATE(), 
				  UPDT_USER_ID = 2

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


