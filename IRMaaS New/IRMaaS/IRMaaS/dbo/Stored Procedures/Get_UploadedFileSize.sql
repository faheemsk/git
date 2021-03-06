﻿


CREATE PROCEDURE [dbo].[Get_UploadedFileSize]
(
      @FL_NM					VARCHAR(150),
	  @APPL_FL_UPLOAD_LOG_KEY	INTEGER
     
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


SELECT  A.FL_SZ,A.FL_STS_KEY,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.FL_FLDR_PTH,B.ORG_NM
FROM    APPL_FL_UPLOAD_LOG				A
JOIN	ORG								B
ON		A.ORG_KEY				=		B.ORG_KEY
WHERE 	APPL_FL_UPLOAD_LOG_KEY	=		@APPL_FL_UPLOAD_LOG_KEY




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
