﻿CREATE PROCEDURE [dbo].[UPDATE_UserEmailLogByKey]
(
	@SND_SUC_IND				INTEGER,
	@RESND_CNT				    INTEGER,
	@USER_EMAIL_LOG				INTEGER,
	@EMAIL_SND_DT				DATETIME			  

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
      UPDATE dbo.USER_EMAIL_LOG
      SET    SND_SUC_IND	=   @SND_SUC_IND,
			 RESND_CNT		=   @RESND_CNT,
			 EMAIL_SND_DT	=   @EMAIL_SND_DT
      WHERE  USER_EMAIL_LOG =   @USER_EMAIL_LOG
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







