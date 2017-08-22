CREATE PROCEDURE [dbo].[Analyst_ListOS]

AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

			SELECT   [OS_KEY],[OS_NM]
			FROM	 OS		
			WHERE	 ISNULL(OS_NM,'')<>''
			ORDER BY [OS_KEY]
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
