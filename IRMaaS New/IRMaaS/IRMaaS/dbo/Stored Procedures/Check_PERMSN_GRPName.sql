/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: Check_PERMSN_GRPName
	** Desc: This procedure check permission name exist into PERMSN_GRP table
	** Auth: Prasad varma
	** Date: 22/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/
CREATE PROCEDURE [dbo].[Check_PERMSN_GRPName]
(
	@PERMSN_GRP_NM VARCHAR(100)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT COUNT(PERMSN_GRP_NM) FROM    PERMSN_GRP
WHERE   PERMSN_GRP_NM	= @PERMSN_GRP_NM
AND ROW_STS_KEY <> 3

SELECT @@ROWCOUNT RETVAL

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







