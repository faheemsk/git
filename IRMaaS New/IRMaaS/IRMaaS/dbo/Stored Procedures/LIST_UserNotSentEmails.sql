﻿CREATE PROCEDURE [dbo].[LIST_UserNotSentEmails]

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
      SELECT  USER_EMAIL_LOG,NTF_MSG_KEY,USER_ID,FROM_EMAIL_ID,TO_EMAIL_ID,
			  RESND_CNT,EMAIL_MSG_SBJ_TXT,EMAIL_MSG_CNTN_TXT
      FROM    USER_EMAIL_LOG   
      WHERE   SND_SUC_IND = 0   
      AND	  RESND_CNT <= 3
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







