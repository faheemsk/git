CREATE PROCEDURE [dbo].[Get_ClntRelEngmtIndenByID]
(
      @CLNT_ENGMT_CD	VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON



	SELECT      CLNT_REL_ENGMT_ID_KEY, CLNT_ENGMT_CD,SRC_KEY,[dbo].[fnGetMasterLkpNameByID](SRC_KEY) ServiceName, 
				SRC_REL_ENGMT_ID, CREAT_DT, CREAT_USER_ID, 
				UPDT_DT, UPDT_USER_ID,ROW_STS_KEY

	FROM		CLNT_REL_ENGMT_ID
	WHERE		CLNT_ENGMT_CD                    =  @CLNT_ENGMT_CD


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




