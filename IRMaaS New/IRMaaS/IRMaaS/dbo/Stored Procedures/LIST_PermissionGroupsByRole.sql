﻿CREATE PROCEDURE [dbo].[LIST_PermissionGroupsByRole]
(
	@RoleID VARCHAR(500)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	DISTINCT A.PERMSN_GRP_KEY,A.ROW_STS_KEY,A.PERMSN_GRP_NM,A.PERMSN_GRP_DESC,B.APPL_ROLE_KEY,D.APPL_ROLE_NM
FROM	PERMSN_GRP				A 
JOIN	APPL_ROLE_PERMSN_GRP	B
ON		A.PERMSN_GRP_KEY	  = B.PERMSN_GRP_KEY
JOIN	FnSplit(@RoleID,',')	C
ON		B.APPL_ROLE_KEY		  = C.Items
JOIN	APPL_ROLE				D
ON		B.APPL_ROLE_KEY		  = D.APPL_ROLE_KEY
WHERE	B.ROW_STS_KEY		  <> 3
AND		A.ROW_STS_KEY		  <> 3

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

