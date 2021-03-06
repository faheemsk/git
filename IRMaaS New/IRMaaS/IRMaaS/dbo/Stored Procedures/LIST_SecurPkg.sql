﻿
CREATE PROCEDURE [dbo].[LIST_SecurPkg]

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	SECUR_PKG_CD,SECUR_PKG_NM,SECUR_PKG_DESC,CREAT_DT,CREAT_USER_ID,UPDT_DT,
		UPDT_USER_ID,ROW_STS_KEY
FROM	SECUR_PKG
WHERE	ROW_STS_KEY = 1
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






