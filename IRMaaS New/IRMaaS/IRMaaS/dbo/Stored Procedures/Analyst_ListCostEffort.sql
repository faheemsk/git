﻿
CREATE PROCEDURE [dbo].[Analyst_ListCostEffort]

AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

			SELECT   RMDTN_CST_EFFRT_CD,RMDTN_CST_EFFRT_NM
			FROM	 RMDTN_CST_EFFRT		
			ORDER BY RMDTN_CST_EFFRT_ORDR_NBR
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


