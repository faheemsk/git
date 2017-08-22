/********************************************************************
	** Desc: To load CVE table
	** Auth: Prakash Selvaraj
	** Date: 06/20/2016
	**************************
	** Change History
	**************************
	**  Date					Author					Description	
	**  06/20/2016        Prakash Selvaraj            Inital Version
*********************************************************************/

/*
EXEC [dbo].[MERGE_CVE]

*/



CREATE PROCEDURE [dbo].[MERGE_CVE]
  @ins_row_cnt int output,
  @upd_row_cnt int output
AS
BEGIN

declare  @mergeResultsTable table (MergeAction VARCHAR(20));


BEGIN TRY
	SET NOCOUNT ON

		MERGE DBO.CVE AS T
USING ( 
 SELECT 
	   CVE_ID,
	   PUBL_DT,
	   LST_MOD_DT,
	   CVE_DESC,  
	   metric.BAS_SCOR,
	   metric.ACS_VCTR,
	   metric.ACS_CMPLX, 
	   metric.AUTH, 
	   metric.CONFDTY_IMP, 
	   metric.INGTY_IMP, 
	   metric.AVL_IMP,
	   ltrim(CWE.CWE_LIST_ID) AS CWE_ID, 
			'(' + Acc_Vec.MTRC_VCTR_CD	 + ':' +  Acc_Vec.MTRC_VAL_VCTR_CD   + '/' 
				+ Acc_cmp.MTRC_VCTR_CD	 + ':' +  Acc_cmp.MTRC_VAL_VCTR_CD   + '/'
				+ Authen.MTRC_VCTR_CD	 + ':' +  Authen.MTRC_VAL_VCTR_CD    + '/'
				+ Conf_Imp.MTRC_VCTR_CD	 + ':' +  Conf_Imp.MTRC_VAL_VCTR_CD  + '/'
				+ Intg_Impc.MTRC_VCTR_CD + ':' +  Intg_Impc.MTRC_VAL_VCTR_CD + '/'
				+ Avail_Imp.MTRC_VCTR_CD + ':' +  Avail_Imp.MTRC_VAL_VCTR_CD + ')' VCTR_TXT,
	   cast ( cast(10.41*(1-(1-Conf_Imp.MTRC_VAL_SCOR)*(1-Intg_Impc.MTRC_VAL_SCOR)*(1-Avail_Imp.MTRC_VAL_SCOR)) as decimal(10,1)) as float ) AS IMP_SUB_SCOR,
	   cast( cast(20 * Acc_Vec.MTRC_VAL_SCOR * Acc_cmp.MTRC_VAL_SCOR * Authen.MTRC_VAL_SCOR as decimal(10,1)) as float ) as EXPLT_SUB_SCOR
 FROM 
   stg.CVE_ENTRY_STG ENT 
   LEFT OUTER JOIN   
		   ( SELECT ENTRY_ID
					,STUFF((SELECT ', ' + CAST(CWE_ID AS VARCHAR(10)) [text()]
			 FROM stg.CVE_CWE_STG
			 WHERE ENTRY_ID = t.ENTRY_ID
				FOR XML PATH(''), TYPE).value('.','VARCHAR(50)'),1,2,' ') CWE_LIST_ID
			FROM stg.CVE_CWE_STG t
			GROUP BY ENTRY_ID 
			) CWE
		ON ENT.ENTRY_ID = CWE.ENTRY_ID 
   LEFT OUTER JOIN  stg.CVE_CVSS_STG cvss
		on ENT.ENTRY_ID = cvss.ENTRY_ID 
   LEFT OUTER JOIN stg.CVE_BAS_METR_STG metric
		on cvss.cvss_id = metric.cvss_id 
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Access Vector' ) Acc_Vec
		  ON metric.ACS_VCTR = Acc_Vec.MTRC_VAL_TXT
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Access Complexity' ) Acc_cmp
		  ON metric.ACS_CMPLX = Acc_cmp.MTRC_VAL_TXT
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Authentication' ) Authen
		  ON metric.AUTH = Authen.MTRC_VAL_TXT
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Confidentiality Impact' ) Conf_Imp
		  ON metric.CONFDTY_IMP = Conf_Imp.MTRC_VAL_TXT
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Integrity Impact' ) Intg_Impc
		  ON metric.INGTY_IMP = Intg_Impc.MTRC_VAL_TXT
   LEFT OUTER JOIN (
		  SELECT MTRC_VCTR_CD, MTRC_VAL_VCTR_CD, MTRC_VAL_TXT, MTRC_VAL_SCOR
		  FROM dbo.CVSS_SCOR_MTRC where mtrc_nm = 'Availability Impact' ) Avail_Imp
		  ON metric.AVL_IMP = Avail_Imp.MTRC_VAL_TXT
  
 ) AS S
ON  S.CVE_ID  = T.CVE_ID
WHEN NOT MATCHED BY TARGET 
    THEN INSERT(
    CVE_ID, 
    CVE_DESC,
    PUBL_DT, 
    LST_MOD_DT, 
    CWE_ID,
    BAS_SCOR, 
    ACS_VCTR,
    ACS_CMPLX,
    AUTH,
    CONFDTY_IMP,
    INGTY_IMP,
    AVL_IMP,
	VCTR_TXT,
	IMP_SUB_SCOR,
	EXPLT_SUB_SCOR,
    CREAT_DT, 
    CREAT_USER_ID) 
  VALUES(
    S.CVE_ID, 
    S.CVE_DESC,
    S.PUBL_DT, 
    S.LST_MOD_DT, 
    S.CWE_ID,
    S.BAS_SCOR, 
    S.ACS_VCTR,
    S.ACS_CMPLX,
    S.AUTH,
    S.CONFDTY_IMP,
    S.INGTY_IMP,
    S.AVL_IMP, 
	S.VCTR_TXT,
	S.IMP_SUB_SCOR,
	S.EXPLT_SUB_SCOR,
    GETDATE(),
    2)
WHEN MATCHED 
    THEN UPDATE 
  SET 
   T.CVE_DESC      = S.CVE_DESC,
   T.PUBL_DT  = S.PUBL_DT,
   T.LST_MOD_DT = S.LST_MOD_DT,
   T.CWE_ID  = S.CWE_ID,
   T.BAS_SCOR  = S.BAS_SCOR,
   T.ACS_VCTR  = S.ACS_VCTR,
   T.ACS_CMPLX  = S.ACS_CMPLX,
   T.AUTH   = S.AUTH,
   T.CONFDTY_IMP = S.CONFDTY_IMP,
   T.INGTY_IMP  = S.INGTY_IMP,
   T.AVL_IMP  = S.AVL_IMP,  
   T.VCTR_TXT = S.VCTR_TXT,
   T.IMP_SUB_SCOR = S.IMP_SUB_SCOR,
   T.EXPLT_SUB_SCOR = S.EXPLT_SUB_SCOR,
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

