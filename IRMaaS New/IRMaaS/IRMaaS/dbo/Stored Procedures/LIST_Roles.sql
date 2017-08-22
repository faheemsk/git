CREATE PROCEDURE [dbo].[LIST_Roles]
(
	@USER_TYP_KEY	INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT      DISTINCT A.APPL_ROLE_KEY,A.APPL_ROLE_NM,A.APPL_ROLE_DESC
FROM		APPL_ROLE                       A
JOIN		APPL_ROLE_PERMSN_GRP			B
ON          A.APPL_ROLE_KEY               = B.APPL_ROLE_KEY
WHERE		A.ROW_STS_KEY                 = 1
AND         B.ROW_STS_KEY                 = 1
AND			A.USER_TYP_KEY				  = @USER_TYP_KEY
ORDER BY	A.APPL_ROLE_NM
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



