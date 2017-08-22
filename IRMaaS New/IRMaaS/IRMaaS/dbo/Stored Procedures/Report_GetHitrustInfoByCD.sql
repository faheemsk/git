
CREATE PROCEDURE [dbo].Report_GetHitrustInfoByCD
(
       @SECUR_CTL_CD    VARCHAR(500)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
              
              SELECT	  DISTINCT A.SECUR_CTL_CD,A.SECUR_CTL_NM,A.SECUR_CTL_FAM_CD,A.SECUR_CTL_FAM_NM,A.SECUR_OBJ_CD,
						  A.SECUR_OBJ_NM,A.SECUR_CTL_DESC
              FROM		  SECUR_CTL							A
              CROSS APPLY FnSplit(@SECUR_CTL_CD,',')		B
              WHERE		  A.SECUR_CTL_CD			    =	B.items
 


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

