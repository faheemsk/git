CREATE PROCEDURE [dbo].[Check_OrgNameByName]
(
	@OrgName VARCHAR(150)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON



SELECT COUNT(ORG_NM) [COUNT] FROM    ORG
WHERE   ORG_NM	= @OrgName
AND ROW_STS_KEY <> 3



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











