CREATE PROCEDURE [dbo].[LIST_OrglistByUserType]
(
      @UserType INT
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON
DECLARE @PiOrgType INTEGER = 0
       SELECT @PiOrgType = CASE WHEN @UserType = 16 THEN 8
                                                WHEN @UserType = 17 THEN 9
                                                WHEN @UserType = 18 THEN 10 END 
                                                 
      SELECT  ORG_KEY,ORG_NM
      FROM    ORG
      WHERE   ORG_TYP_KEY   = @PiOrgType
         AND     ROW_STS_KEY    = 1
         AND    CREAT_ORG_SCHM_IND = CASE WHEN @PiOrgType = 9 THEN  'Y' ELSE CREAT_ORG_SCHM_IND  END

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





