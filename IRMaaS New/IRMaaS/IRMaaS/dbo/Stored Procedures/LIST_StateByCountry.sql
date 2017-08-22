--DROP PROCEDURE LIST_StateByCountry
CREATE PROCEDURE [dbo].[LIST_StateByCountry]
(
	@CountryName VARCHAR(150)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


SELECT A.ST_CD,A.CNTRY_CD,A.ST_NM
FROM   ST_CD A
JOIN   CNTRY_CD B
ON     A.CNTRY_CD = B.CNTRY_CD
WHERE  B.CNTRY_NM  = @CountryName

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
END;







