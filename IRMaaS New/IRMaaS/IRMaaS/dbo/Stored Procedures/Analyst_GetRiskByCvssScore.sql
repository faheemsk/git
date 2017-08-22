﻿


CREATE PROCEDURE [dbo].[Analyst_GetRiskByCvssScore]
(
	
	@BaseScore				DECIMAL(10,2),	
	@ExpltScore				DECIMAL(10,2),
	@ImpScore				DECIMAL(10,2)	
	
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
		
		
		SELECT  TOP 1 VULN_SEV_CD,VULN_SEV_NM 
		FROM VULN_SEV 
		WHERE @BaseScore >= OVALL_SCOR_MIN 
		ORDER BY OVALL_SCOR_MIN DESC
		
		

		SELECT TOP 1 RISK_PRBL_CD,RISK_PRBL_NM 
		FROM   RISK_PRBL
		WHERE  @ExpltScore >= CVSS_EXPLT_SCOR_MIN 
		ORDER BY CVSS_EXPLT_SCOR_MIN DESC
		

		SELECT TOP 1 VULN_IMP_CD,VULN_IMP_NM 
		FROM   VULN_IMP
		WHERE  @ImpScore >= CVSS_IMP_SCOR_MIN 
		ORDER BY CVSS_IMP_SCOR_MIN DESC
			        
				   

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





