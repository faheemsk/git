
--DROP PROCEDURE ListMasterlookByEntityType
CREATE PROCEDURE [dbo].[LIST_MasterLookupByEntityType]
(

@EntityType VARCHAR(200)

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT MSTR_LKP_KEY,LKP_ENTY_TYP_NM , LKP_ENTY_NM , LKP_ENTY_DESC 
FROM   MSTR_LKP
WHERE  LKP_ENTY_TYP_NM =   @EntityType
ORDER BY LKP_ENTY_NM ASC

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








