CREATE PROCEDURE [dbo].[Get_ScanFailedFiles]
(
         @PvcFlag          VARCHAR(1),
         @PiHours       INTEGER,
              @Flag               VARCHAR(1)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


IF @Flag = 'D'
BEGIN
SELECT      A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,A.SECUR_SRVC_CD,
                     A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
                     [dbo].[fnGetUserEmailByID](A.UPLOAD_USER_ID) UploadedUserEmail,
                     FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,
                     A.FL_SZ

FROM    APPL_FL_UPLOAD_LOG        A
JOIN   ORG                                      B
ON      A.ORG_KEY            =     B.ORG_KEY
JOIN   MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN   MSTR_LKP                          D
ON            D.MSTR_LKP_KEY         =           A.FL_STS_KEY
--JOIN MSTR_LKP                          E
--ON          E.MSTR_LKP_KEY         =           A.DOC_TYP_KEY
WHERE   C.LKP_ENTY_NM        =           'Active'
AND           D.LKP_ENTY_NM   =           'Scan in Progress'
--AND         E.LKP_ENTY_NM   =           'Data' 
AND           CASE WHEN @PvcFlag = 'M' THEN DATEDIFF(MINUTE, FL_UPLOAD_DT, GETDATE())
                     WHEN @PvcFlag = 'H' THEN DATEDIFF(HOUR, FL_UPLOAD_DT, GETDATE()) END >= @PiHours 
ORDER BY A.FL_UPLOAD_DT DESC
END

IF @Flag = 'R'
BEGIN
SELECT      A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,
                     A.CREAT_USER_ID,[dbo].[fnGetUserNameByID](A.CREAT_USER_ID) UploadedUser,
                     [dbo].[fnGetUserEmailByID](A.CREAT_USER_ID) UploadedUserEmail,
                     FL_NM,FL_FLDR_PTH,A.CREAT_DT,A.RPT_FL_UPLOAD_LOG_KEY,A.RPT_STS_KEY,
                                  A.FL_SZ,A.RPT_PUBL_DT

FROM    RPT_FL_UPLOAD_LOG        A
JOIN   ORG                                      B
ON      A.ORG_KEY            =     B.ORG_KEY
JOIN   MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN   MSTR_LKP                          D
ON            D.MSTR_LKP_KEY         =           A.RPT_STS_KEY
--JOIN MSTR_LKP                          E
--ON          E.MSTR_LKP_KEY         =           A.DOC_TYP_KEY
WHERE   C.LKP_ENTY_NM        =           'Active'
AND           D.LKP_ENTY_NM   =           'Scan in Progress'
--AND         E.LKP_ENTY_NM   =           'Data' 
AND           CASE WHEN @PvcFlag = 'M' THEN DATEDIFF(MINUTE, A.CREAT_DT, GETDATE())
                     WHEN @PvcFlag = 'H' THEN DATEDIFF(HOUR, A.CREAT_DT, GETDATE()) END >= @PiHours 
ORDER BY A.CREAT_DT DESC
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


