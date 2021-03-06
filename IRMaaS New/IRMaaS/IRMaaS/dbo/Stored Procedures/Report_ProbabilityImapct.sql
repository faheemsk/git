﻿CREATE PROCEDURE [dbo].[Report_ProbabilityImapct]
(

	@CLNT_ENGMT_CD			VARCHAR(100),
	@SECUR_SRVC_CD			VARCHAR(200),
	@VULN_SEV_CD			VARCHAR(200),
	@VULN_CATGY_CD			VARCHAR(500),
	@schema					VARCHAR(50)  
		
	
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query  VARCHAR(max)
DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)
DECLARE @Query3 VARCHAR(max)

-- BEGIN TRANSACTION
		IF OBJECT_ID('dbo.#1', 'U') IS NOT NULL 
		 DROP TABLE dbo.#1; 
		 IF OBJECT_ID('dbo.#2', 'U') IS NOT NULL 
		 DROP TABLE dbo.#2; 
		 IF OBJECT_ID('dbo.#3', 'U') IS NOT NULL 
		 DROP TABLE dbo.#3; 
SET @Query='
IF OBJECT_ID(''dbo.#1'', ''U'') IS NOT NULL 
  DROP TABLE dbo.#1; 

SELECT	A.VULN_CATGY_CD,A.CLNT_ENGMT_CD,COUNT(DISTINCT A.CLNT_VULN_INSTC_KEY)FindingCount,
SUM(VULN_IMP_SUB_SCOR)/COUNT(DISTINCT A.CLNT_VULN_INSTC_KEY)ImpactAvg,B.VULN_CATGY_NM,
SUM(VULN_EXPLT_SUB_SCOR)/COUNT(DISTINCT A.CLNT_VULN_INSTC_KEY)RiskAvg INTO dbo.#1
FROM		'+ @schema+'.CLNT_VULN_INSTC		  A
JOIN		VULN_CATGY		  B
ON			A.VULN_CATGY_CD	= B.VULN_CATGY_CD
WHERE		A.CLNT_ENGMT_CD			=	'''+@CLNT_ENGMT_CD +'''
AND			A.ROW_STS_KEY			=   1
AND			1= CASE WHEN '''+ @SECUR_SRVC_CD +''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+ @SECUR_SRVC_CD+''','','')) THEN 1 END
AND			1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
 AND		1= CASE WHEN '''+@VULN_CATGY_CD+'''  = ''''  THEN 1	WHEN LTRIM(RTRIM(A.VULN_CATGY_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@VULN_CATGY_CD+''')),'',''))	THEN 1 END
AND			A.RISK_PRBL_CD			IN	(''A'',''L'',''P'',''U'',''R'')
AND			A.VULN_IMP_CD			IN	(''MI'',''I'',''MO'',''C'',''MJ'',''IF'')
AND			A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
AND			A.VULN_SEV_CD			NOT IN(''I'')
AND			ISNULL(A.VULN_IMP_SUB_SCOR,0)<> 0
AND			ISNULL(VULN_EXPLT_SUB_SCOR,0)<>0
GROUP BY	A.VULN_CATGY_CD,A.CLNT_ENGMT_CD,B.VULN_CATGY_NM
--PRINT (@Query)



--SET @Query1=
IF OBJECT_ID(''dbo.#2'', ''U'') IS NOT NULL 
  DROP TABLE dbo.#2; 

SELECT		VULN_CATGY_CD,SUM(Impactsqrt)ImpactSum,SUM(RiskSqrt)RiskSum,VULN_CATGY_NM INTO dbo.#2 FROM(
SELECT		A.VULN_CATGY_CD ,B.VULN_CATGY_NM,
			(CONVERT(NUMERIC,ISNULL(B.ImpactAvg,0)) -CONVERT(NUMERIC,ISNULL(VULN_IMP_SUB_SCOR,0)))*
			(CONVERT(NUMERIC,ISNULL(B.ImpactAvg,0)) -CONVERT(NUMERIC,ISNULL(VULN_IMP_SUB_SCOR,0)))Impactsqrt,
			(ISNULL(B.RiskAvg,0)-ISNULL(VULN_EXPLT_SUB_SCOR,0))*(ISNULL(B.RiskAvg,0)-ISNULL(VULN_EXPLT_SUB_SCOR,0))RiskSqrt 
FROM		'+ @schema+'.CLNT_VULN_INSTC A
JOIN		dbo.#1			 B
ON			A.VULN_CATGY_CD = B.VULN_CATGY_CD
WHERE		A.CLNT_ENGMT_CD			=	'''+@CLNT_ENGMT_CD +'''
AND			A.ROW_STS_KEY			=   1
AND			1= CASE WHEN '''+ @SECUR_SRVC_CD +''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+ @SECUR_SRVC_CD+''','','')) THEN 1 END
AND			1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
 AND		1= CASE WHEN '''+@VULN_CATGY_CD+'''  = ''''  THEN 1	WHEN LTRIM(RTRIM(A.VULN_CATGY_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@VULN_CATGY_CD+''')),'',''))	THEN 1 END
AND			A.RISK_PRBL_CD			IN	(''A'',''L'',''P'',''U'',''R'')
AND			A.VULN_IMP_CD			IN	(''MI'',''I'',''MO'',''C'',''MJ'',''IF'')
AND			A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
AND			A.VULN_SEV_CD			NOT IN(''I'')
AND			ISNULL(A.VULN_IMP_SUB_SCOR,0)<> 0
AND			ISNULL(VULN_EXPLT_SUB_SCOR,0)<>0
)Z
GROUP BY VULN_CATGY_CD,VULN_CATGY_NM

--EXECUTE(@Query1)
-- PRINT (@Query1)

IF OBJECT_ID(''dbo.#3'', ''U'') IS NOT NULL 
		 DROP TABLE dbo.#3; 

SELECT		A.VULN_CATGY_CD,A.CLNT_ENGMT_CD,
			MAX(VULN_IMP_SUB_SCOR)ImpactMax,
			MAX(VULN_EXPLT_SUB_SCOR)RiskMax INTO dbo.#3
FROM		'+ @schema+'.CLNT_VULN_INSTC			  A
JOIN		VULN_CATGY				  B
ON			A.VULN_CATGY_CD			= B.VULN_CATGY_CD
WHERE		A.CLNT_ENGMT_CD			='''+@CLNT_ENGMT_CD +'''
AND			A.ROW_STS_KEY			= 1
AND			1= CASE WHEN '''+ @SECUR_SRVC_CD +''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+ @SECUR_SRVC_CD+''','','')) THEN 1 END
AND			1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
AND			1= CASE WHEN '''+@VULN_CATGY_CD+'''  = ''''  THEN 1	WHEN LTRIM(RTRIM(A.VULN_CATGY_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@VULN_CATGY_CD+''')),'',''))	THEN 1 END
AND			A.RISK_PRBL_CD			IN	(''A'',''L'',''P'',''U'',''R'')
AND			A.VULN_IMP_CD			IN	(''MI'',''I'',''MO'',''C'',''MJ'',''IF'')
AND			A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
AND			A.VULN_SEV_CD			NOT IN(''I'')
GROUP BY A.VULN_CATGY_CD,A.CLNT_ENGMT_CD
--EXECUTE(@Query2)
--PRINT(@Query2)

--SET @Query3=
SELECT		A.VULN_CATGY_CD,COUNT(A.CLNT_VULN_INSTC_KEY)FindingsCount,B.VULN_CATGY_NM,
			C.ImpactMax -(SQRT(B.ImpactSum/COUNT(DISTINCT A.CLNT_VULN_INSTC_KEY)))Impact,
			(C.RiskMax -(SQRT(B.RiskSum/COUNT(DISTINCT A.CLNT_VULN_INSTC_KEY)))) Risk
FROM		'+ @schema+'.CLNT_VULN_INSTC A
JOIN		#2 B
ON			A.VULN_CATGY_CD	= B.VULN_CATGY_CD
JOIN		#3 C
ON			A.VULN_CATGY_CD	= C.VULN_CATGY_CD
AND			C.VULN_CATGY_CD	= B.VULN_CATGY_CD
WHERE		A.CLNT_ENGMT_CD			='''+@CLNT_ENGMT_CD +'''
AND			A.ROW_STS_KEY			= 1
AND			1= CASE WHEN '''+ @SECUR_SRVC_CD +''' = '''' THEN 1	WHEN A.SECUR_SRVC_CD IN (SELECT Items FROM Dbo.FnSplit('''+ @SECUR_SRVC_CD+''','','')) THEN 1 END
AND			1= CASE WHEN '''+@VULN_SEV_CD+''' = ''''   THEN 1	WHEN A.VULN_SEV_CD IN (SELECT Items FROM Dbo.FnSplit('''+@VULN_SEV_CD+''','',''))		THEN 1 END
AND			1= CASE WHEN '''+@VULN_CATGY_CD+'''  = ''''  THEN 1	WHEN LTRIM(RTRIM(A.VULN_CATGY_CD)) IN (SELECT Items FROM Dbo.FnSplit(LTRIM(RTRIM('''+@VULN_CATGY_CD+''')),'',''))	THEN 1 END
AND			A.RISK_PRBL_CD			IN	(''A'',''L'',''P'',''U'',''R'')
AND			A.VULN_IMP_CD			IN	(''MI'',''I'',''MO'',''C'',''MJ'',''IF'')
AND			A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
AND			A.VULN_SEV_CD			NOT IN(''I'')
AND			ISNULL(A.VULN_IMP_SUB_SCOR,0)<> 0
AND			ISNULL(A.VULN_EXPLT_SUB_SCOR,0)<>0
GROUP BY A.VULN_CATGY_CD,B.ImpactSum,B.RiskSum,C.ImpactMax,C.RiskMax,B.VULN_CATGY_NM
ORDER BY A.VULN_CATGY_CD'
EXECUTE(@Query)
--PRINT(@Query)


-- COMMIT TRANSACTION
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
-- COMMIT TRANSACTION
END
