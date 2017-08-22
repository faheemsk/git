﻿CREATE PROCEDURE [dbo].[Report_ListServiceswithFindings]
(
	@ENGMT_CD VARCHAR(30),
	@SchemaName		VARCHAR(50)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(MAX)

SET @Query='
			SELECT  DISTINCT B.SECUR_SRVC_CD,SECUR_SRVC_NM,D.LKP_ENTY_NM ReviewStatus
			FROM	CLNT_SECUR_SRVC_ENGMT		A
			JOIN	SECUR_SRVC					B
			ON		A.SECUR_SRVC_CD			=   B.SECUR_SRVC_CD
			JOIN	USER_PRFL					C
			ON		A.CREAT_USER_ID			=   C.USER_ID
			JOIN	MSTR_LKP					D
			ON		A.SRVC_ENGMT_STS_KEY	=	D.MSTR_LKP_KEY
			JOIN	'+ @SchemaName+ '.CLNT_VULN_INSTC				E
			ON		A.SECUR_SRVC_CD			=	E.SECUR_SRVC_CD
			AND		A.CLNT_ENGMT_CD			=   E.CLNT_ENGMT_CD
			WHERE	A.CLNT_ENGMT_CD			=	'''+@ENGMT_CD+'''
			AND		A.ROW_STS_KEY			=   1
			AND		E.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
			AND		E.VULN_SEV_CD			NOT IN(''I'')
			AND		E.ROW_STS_KEY			=   1'
			 -- PRINT (@query)
  EXECUTE(@Query)
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