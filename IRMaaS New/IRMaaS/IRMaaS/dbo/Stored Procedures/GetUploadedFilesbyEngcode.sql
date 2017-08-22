CREATE PROCEDURE [dbo].[GetUploadedFilesbyEngcode]
(
@CLNT_ENGMT_CD VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT      A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,A.SECUR_SRVC_CD,
                     A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
                     A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
                     A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
                     FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,
                     A.ETL_PROC_END_DT,A.FL_SZ,E.EMAIL_ID

FROM		  APPL_FL_UPLOAD_LOG   A
JOIN		  ORG                  B
ON			  A.ORG_KEY       =    B.ORG_KEY
JOIN		  MSTR_LKP             C
ON            C.MSTR_LKP_KEY  =        A.ROW_STS_KEY
JOIN		  MSTR_LKP             D
ON            D.MSTR_LKP_KEY  =    A.FL_STS_KEY
JOIN		  USER_PRFL			   E
ON			  A.UPLOAD_USER_ID	= E.UPDT_DT
WHERE	      CLNT_ENGMT_CD   =    @CLNT_ENGMT_CD
AND			  C.LKP_ENTY_NM   =    'Active'
AND           D.LKP_ENTY_NM   <>   'ETL Failure' 
AND           D.LKP_ENTY_NM   <>   'Scan Failure'
ORDER BY A.FL_UPLOAD_DT DESC
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



