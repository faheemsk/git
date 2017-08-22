/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: LIST_PermissionGroupsByPermGropID
       ** Desc: This procedure Fetch data into PERMSN_GRP table
       ** Auth: Prasad varma
       ** Date: 22/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    02/05/2016      xxxxx          
       *******************************/
CREATE PROCEDURE [dbo].[LIST_PermissionGroupsByPermGropID]
(
@PERMSN_GRP_KEY		INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	PERMSN_GRP_KEY,ROW_STS_KEY,PERMSN_GRP_NM,PERMSN_GRP_DESC,STS_COMMT_TXT,dbo.fnGetMasterLkpNameByID(ROW_STS_KEY) ROWSTATUS	
FROM	PERMSN_GRP		
WHERE   PERMSN_GRP_KEY = @PERMSN_GRP_KEY  


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







