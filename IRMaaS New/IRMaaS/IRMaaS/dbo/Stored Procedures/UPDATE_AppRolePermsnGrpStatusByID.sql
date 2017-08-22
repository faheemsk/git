CREATE PROCEDURE [dbo].[UPDATE_AppRolePermsnGrpStatusByID]

(   
	@APPL_ROLE_KEY               INTEGER
)

AS  
BEGIN
BEGIN TRY
SET NOCOUNT ON
BEGIN 

            UPDATE dbo.APPL_ROLE
            SET    ROW_STS_KEY                =   3
	        WHERE  APPL_ROLE_KEY              =   @APPL_ROLE_KEY

            UPDATE dbo.APPL_ROLE_PERMSN_GRP
            SET    ROW_STS_KEY                =   3
	        WHERE  APPL_ROLE_KEY              =   @APPL_ROLE_KEY

			UPDATE dbo.USER_APPL_ROLE
            SET    ROW_STS_KEY                =   3
	        WHERE  APPL_ROLE_KEY              =   @APPL_ROLE_KEY
     

			SELECT @@ROWCOUNT AS RETVAL

END 

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








