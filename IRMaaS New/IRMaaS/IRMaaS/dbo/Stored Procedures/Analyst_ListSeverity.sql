﻿CREATE PROCEDURE [dbo].[Analyst_ListSeverity]
(
	@CLNT_ENGMT_CD	VARCHAR(30),
	@SchemaName		VARCHAR(50)
)

AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(MAX)

			SET @Query='
			IF ''' +@CLNT_ENGMT_CD+ ''' = ''''
			BEGIN

			SELECT  DISTINCT VULN_SEV_CD,VULN_SEV_NM,VULN_SEV_ORDR_NBR
			FROM	VULN_SEV
			WHERE	VULN_SEV_CD NOT IN(''I'')	
			ORDER BY VULN_SEV_ORDR_NBR DESC
			END	
			ELSE
			BEGIN
			SELECT  DISTINCT A.VULN_SEV_CD,A.VULN_SEV_NM,VULN_SEV_ORDR_NBR
			FROM	VULN_SEV A
			JOIN	'+ @SchemaName+ '.CLNT_VULN_INSTC B
			ON		A.VULN_SEV_CD	= B.VULN_SEV_CD
			WHERE	A.VULN_SEV_CD NOT IN(''I'')
			AND 	B.CLNT_ENGMT_CD	= '''+@CLNT_ENGMT_CD+'''
			AND     B.VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
            AND     B.ROW_STS_KEY   =   1
			ORDER BY A.VULN_SEV_ORDR_NBR DESC	
			END '
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


