
CREATE PROCEDURE [dbo].[Check_AuthoritativeSourceName]
(
	@LKP_ENTY_NM VARCHAR(150)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT COUNT(LKP_ENTY_NM) [COUNT] FROM    MSTR_LKP 
WHERE   LKP_ENTY_TYP_NM= 'Authoritative Source'	
AND     LKP_ENTY_NM  =  @LKP_ENTY_NM 
AND     ACTV_IND = 1  
  


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







