/********************************************************************
	** Desc: To load CVE_SECUR_CTL_MAP table
	** Auth: Prakash Selvaraj
	** Date: 06/20/2016
	**************************
	** Change History
	**************************
	**  Date					Author					Description	
	**  06/20/2016        Prakash Selvaraj            Inital Version
	**  07/11/2016        Prakash Selvaraj            Added Try-Catch to capture the errors
*********************************************************************/

/*
EXEC [dbo].[MERGE_CVE_SECUR_CTL_MAP]

*/



CREATE PROCEDURE [dbo].[MERGE_CVE_SECUR_CTL_MAP]
  @ins_row_cnt int output,
  @upd_row_cnt int output
AS
BEGIN

declare  @mergeResultsTable table (MergeAction VARCHAR(20));


BEGIN TRY
	SET NOCOUNT ON

		MERGE DBO.CVE_SECUR_CTL_MAP AS T
		USING 
		(

				SELECT 
					a.CVE_ID, 
					a.REG_CMPLN_CD, 
					a.REG_CMPLN_VER, 
					LTRIM(Split.a.value('.', 'VARCHAR(100)')) AS SECUR_CTL_CD_SPLIT 
				FROM 
					(
					SELECT 
						CVE_ID, 
						REG_CMPLN_CD, 
						REG_CMPLN_VER, 
						CAST ('<M>' + REPLACE(SECUR_CTL_CD, ',', '</M><M>') + '</M>' AS XML) AS String 
					 FROM 
						stg.CVE_SECUR_CTL_MAP_STG 
		
					) AS a CROSS APPLY String.nodes ('/M') AS Split(a)

				 join dbo.cve cve
					 on a.cve_id = cve.cve_id
				 join dbo.secur_ctl sec
					 on a.REG_CMPLN_CD = sec.REG_CMPLN_CD 
					 and a.REG_CMPLN_VER = sec.REG_CMPLN_VER 
					 and LTRIM(Split.a.value('.', 'VARCHAR(100)')) = sec.SECUR_CTL_CD

		)
		AS S
		ON 
			S.CVE_ID				= T.CVE_ID
		AND S.REG_CMPLN_CD			= T.REG_CMPLN_CD
		AND S.REG_CMPLN_VER			= T.REG_CMPLN_VER
		AND S.SECUR_CTL_CD_SPLIT    = T.SECUR_CTL_CD

		WHEN NOT MATCHED BY TARGET 
 
					THEN INSERT
					(
					CVE_ID, 
					REG_CMPLN_CD,
					REG_CMPLN_VER, 
					SECUR_CTL_CD, 
					ROW_STS_KEY,
					CREAT_DT, 
					CREAT_USER_ID) VALUES

					(
					S.CVE_ID, 
					S.REG_CMPLN_CD,
					S.REG_CMPLN_VER,
					S.SECUR_CTL_CD_SPLIT, 
					1,
					GETDATE(), 
					2)

		WHEN MATCHED 
			THEN UPDATE 
					SET 
					T.CVE_ID = S.CVE_ID, 
					T.REG_CMPLN_CD = S.REG_CMPLN_CD, 
					T.REG_CMPLN_VER = S.REG_CMPLN_VER, 
					T.SECUR_CTL_CD = S.SECUR_CTL_CD_SPLIT,
					T.ROW_STS_KEY = 1,
					T.UPDT_DT = GETDATE(), 
					T.UPDT_USER_ID = 2


		output $action into @mergeResultsTable; 

			select @ins_row_cnt = [INSERT],
				   @upd_row_cnt = [UPDATE]
			  from (select 'NOOP' MergeAction -- row for null merge into null
					 union all
					select * from @mergeResultsTable) mergeResultsPlusEmptyRow     
			 pivot (count(MergeAction) 
			   for MergeAction in ([INSERT],[UPDATE],[DELETE])) 
				as mergeResultsPivot;


		-- Marking Inactive records

		UPDATE TGT
		SET 
		ROW_STS_KEY = 2, 
		UPDT_DT = GETDATE(), 
		UPDT_USER_ID = 2
		FROM dbo.CVE_SECUR_CTL_MAP TGT
		JOIN ( SELECT DISTINCT CVE_ID FROM STG.CVE_SECUR_CTL_MAP_STG ) STG
		ON TGT.CVE_ID = STG.CVE_ID
		LEFT OUTER JOIN 
		( SELECT 
			a.CVE_ID, 
			a.REG_CMPLN_CD, 
			a.REG_CMPLN_VER, 
			LTRIM(Split.a.value('.', 'VARCHAR(100)')) AS SECUR_CTL_CD_SPLIT 
		FROM 
			(SELECT 
			CVE_ID, 
			REG_CMPLN_CD, 
			REG_CMPLN_VER,
			CAST ('<M>' + REPLACE(SECUR_CTL_CD, ',', '</M><M>') + '</M>' AS XML) AS String 
			FROM 
			stg.CVE_SECUR_CTL_MAP_STG ) AS A 
		CROSS APPLY String.nodes ('/M') AS Split(a) ) STG1

		ON TGT.CVE_ID = STG1.CVE_ID
		AND TGT.REG_CMPLN_CD  = STG1.REG_CMPLN_CD
		AND TGT.REG_CMPLN_VER = STG1.REG_CMPLN_VER
		AND TGT.SECUR_CTL_CD  = STG1.SECUR_CTL_CD_SPLIT
		WHERE STG1.CVE_ID IS NULL 
		AND STG1.REG_CMPLN_CD IS NULL
		AND STG1.REG_CMPLN_VER IS NULL
		AND STG1.SECUR_CTL_CD_SPLIT IS NULL;


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



