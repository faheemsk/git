CREATE PROCEDURE [dbo].[INSCLNT_SCHM_LOG]
(
	@PvcFlag			VARCHAR(1),
	@ORG_KEY			INTEGER,
	@SCHM_STS_DESC		VARCHAR(40),
	@ERR_DESC			TEXT

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON


       IF @PvcFlag = 'I'
       BEGIN
              INSERT CLNT_SCHM_LOG(ORG_KEY,CREAT_DT,SCHM_STS_DESC,ERR_DESC) 
               VALUES (@ORG_KEY,GETDATE(),@SCHM_STS_DESC,@ERR_DESC)

              SELECT SCOPE_IDENTITY() AS RETVAL
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
END

