﻿CREATE PROCEDURE [dbo].[UPDATE_LockUnlockService]
(
      @CLNT_ENGMT_CD	VARCHAR(30),
      @SECUR_SRVC_CD	VARCHAR(10),
	  @FL_LCK_IND		INTEGER
 
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

		UPDATE CLNT_SECUR_SRVC_ENGMT
		SET    FL_LCK_IND = @FL_LCK_IND
		WHERE	CLNT_ENGMT_CD = @CLNT_ENGMT_CD
		AND		SECUR_SRVC_CD = @SECUR_SRVC_CD   

		SELECT	@FL_LCK_IND AS RETVAL
		

	IF @FL_LCK_IND = 1
	BEGIN
		UPDATE A		
		SET		A.FL_STS_KEY = dbo.fnGetMasterLkpID('File Status','To Be Processed')
		FROM	APPL_FL_UPLOAD_LOG		A
		JOIN	MSTR_LKP				B 
		ON		A.FL_STS_KEY			= B.MSTR_LKP_KEY
		AND		B.LKP_ENTY_NM			='New'
		AND		B.LKP_ENTY_TYP_NM		='File Status'
		WHERE	A.CLNT_ENGMT_CD			= @CLNT_ENGMT_CD
		AND		A.SECUR_SRVC_CD			= @SECUR_SRVC_CD 
		AND		A.ROW_STS_KEY			= 1 
		  
		
    END

	IF @FL_LCK_IND = 0
	BEGIN
		UPDATE A		
		SET		A.FL_STS_KEY = dbo.fnGetMasterLkpID('File Status','New')
		FROM	APPL_FL_UPLOAD_LOG		A
		JOIN	MSTR_LKP				B 
		ON		A.FL_STS_KEY			= B.MSTR_LKP_KEY
		AND		B.LKP_ENTY_NM			='To Be Processed'
		AND		B.LKP_ENTY_TYP_NM		='File Status'
		WHERE	A.CLNT_ENGMT_CD			= @CLNT_ENGMT_CD
		AND		A.SECUR_SRVC_CD			= @SECUR_SRVC_CD 
		AND		A.ROW_STS_KEY			= 1 
	
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
END;



