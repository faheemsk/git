CREATE PROCEDURE [dbo].[Analyst_ListSource]

AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

			SELECT  MSTR_LKP_KEY,LKP_ENTY_NM
			FROM	MSTR_LKP	
			WHERE	LKP_ENTY_TYP_NM = 'Source'
			ORDER BY MSTR_LKP_KEY ASC	
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
