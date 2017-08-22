CREATE PROCEDURE [dbo].[TEST_CVSSScoreExample]
(
	@ConfImpact_Score	DECIMAL(10,2),
	@Confidentiality_Requirement_score DECIMAL(10,2),
	@IntegImpact_Score	DECIMAL(10,2),
	@Integrity_Requirement_score DECIMAL(10,2),
	@AvailImpact_Score DECIMAL(10,2),
	@Availability_Requirement_score DECIMAL(10,2),
	@Exploitability_Score DECIMAL(10,2),
	@Exp_Score DECIMAL(10,2),
	@Remediation_Level_score DECIMAL(10,2),
	@Report_Confidence_score DECIMAL(10,2),
	@Collateral_Damage_Potential_score DECIMAL(10,2),
	@Target_Distribution_score DECIMAL(10,2)

)
AS
BEGIN 


DECLARE  @Adjusted_Impact_score DECIMAL (10,2)
DECLARE  @Fn_Impact_Score DECIMAL (10,2)
DECLARE  @Adjusted_Base_Score DECIMAL (10,2)
DECLARE  @Adjusted_Temporal_Score DECIMAL (10,2)
DECLARE  @Environmental_Score DECIMAL (10,2)

BEGIN TRY
SET NOCOUNT ON

SET @Adjusted_Impact_score = CAST(10.41*(1-(1- @ConfImpact_Score * @Confidentiality_Requirement_score )*( 1 - @IntegImpact_Score * @Integrity_Requirement_score ) *(1- @AvailImpact_Score * @Availability_Requirement_score )) AS DECIMAL(10,1))

IF @Adjusted_Impact_score > 10 
BEGIN 
      SET @Adjusted_Impact_score = 10 
END

SET @Fn_Impact_Score = CASE @Adjusted_Impact_score
      WHEN  0.0 THEN 0.0
      ELSE 1.176
END

SET @Adjusted_Base_Score = cast((((0.6 * @Adjusted_Impact_score) + (0.4 * @Exploitability_Score) - 1.5 ) * @Fn_Impact_Score) as decimal(10,1)) 
SET @Adjusted_Temporal_Score =  cast(( @Adjusted_Base_Score * @Exp_Score * @Remediation_Level_score * @Report_Confidence_score ) as decimal(10,1)) 

SET @Environmental_Score = cast ((( @Adjusted_Temporal_Score + (10 -  @Adjusted_Temporal_Score ) * @Collateral_Damage_Potential_score) * @Target_Distribution_score) as decimal(10,1) ) 

SELECT  @Adjusted_Base_Score AS [Base Socre], @Adjusted_Temporal_Score AS [Temporal Score], @Environmental_Score AS [Environmental Score]

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
