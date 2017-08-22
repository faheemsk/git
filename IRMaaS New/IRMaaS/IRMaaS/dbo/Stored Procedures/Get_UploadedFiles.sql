
CREATE PROCEDURE [dbo].[Get_UploadedFiles]
(
      @CLNT_ENGMT_CD VARCHAR(30),
      @SECUR_SRVC_CD VARCHAR(10),
      @UPLOAD_USER_ID      INTEGER,
      @Flag                           VARCHAR(2)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

IF @Flag = 'EL'
BEGIN
SELECT      A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,A.SECUR_SRVC_CD,
                     A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
                     A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
                     A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
                     FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,
                     A.ETL_PROC_END_DT,A.FL_SZ

FROM		  APPL_FL_UPLOAD_LOG        A
JOIN		  ORG                                      B
ON			  A.ORG_KEY            =     B.ORG_KEY
JOIN		  MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN		  MSTR_LKP                          D
ON            D.MSTR_LKP_KEY         =           A.FL_STS_KEY
WHERE		  CLNT_ENGMT_CD     =       @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
--AND         A.UPLOAD_USER_ID  =        @UPLOAD_USER_ID
AND           C.LKP_ENTY_NM   =           'Active'
AND           D.LKP_ENTY_NM   <>   'ETL Failure' 
AND           D.LKP_ENTY_NM   <>   'Scan Failure'
ORDER BY A.FL_UPLOAD_DT DESC
END

IF @Flag = 'EA'
BEGIN

SELECT        A.ORG_KEY,ORG_NM,CLNT_ENGMT_CD,SECUR_SRVC_CD,
              A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
              A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
              A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
              FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,
              A.FL_STS_KEY,A.ETL_PROC_END_DT,A.FL_SZ

FROM		  APPL_FL_UPLOAD_LOG        A
JOIN		  ORG                                      B
ON			  A.ORG_KEY            =     B.ORG_KEY
JOIN		  MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN		  MSTR_LKP                          D
ON            D.MSTR_LKP_KEY         =           A.FL_STS_KEY
WHERE		  CLNT_ENGMT_CD     =       @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
--AND         A.UPLOAD_USER_ID  =        @UPLOAD_USER_ID
AND           C.LKP_ENTY_NM   =           'Active'
AND           D.LKP_ENTY_NM   <>   'ETL Failure'
AND           D.LKP_ENTY_NM   <>   'Scan Failure'
ORDER BY A.FL_UPLOAD_DT DESC


END

IF @Flag = 'PL'
BEGIN
SELECT		  DISTINCT A.ORG_KEY,ORG_NM,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,
              A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
              A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
              A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
              FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,
              A.ETL_PROC_END_DT,A.FL_SZ

FROM		  APPL_FL_UPLOAD_LOG        A
JOIN		  ORG                                      B
ON			  A.ORG_KEY            =     B.ORG_KEY
JOIN		  MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN		  CLNT_ENGMT_USER_ASGN D
ON            A.CLNT_ENGMT_CD        =     D.CLNT_ENGMT_CD
JOIN		  USER_PRFL                         F
ON            a.UPLOAD_USER_ID           =            F.USER_ID
CROSS APPLY	  dbo.FnSplit(D.SECUR_SRVC_LIST_CD,',') AS K
JOIN		  USER_CLNT_SRVC_ASGN        E
ON            A.SECUR_SRVC_CD                   =      K.items
JOIN		  MSTR_LKP                          G
ON            G.MSTR_LKP_KEY         =           A.FL_STS_KEY
WHERE		  A.CLNT_ENGMT_CD     =     @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
AND           F.USER_TYP_KEY             =      18
AND           A.SECUR_SRVC_CD        =     K.items      
AND           C.LKP_ENTY_NM   =           'Active'
AND           G.LKP_ENTY_NM   <>   'ETL Failure'
AND           G.LKP_ENTY_NM   <>   'Scan Failure'
ORDER BY	  A.FL_UPLOAD_DT DESC
END

IF @Flag = 'PU'
BEGIN

SELECT       DISTINCT A.ORG_KEY,ORG_NM,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,
             A.SRC_KEY,[dbo].[fnGetMasterLkpNameByID](A.SRC_KEY)SourceName,
             A.DOC_TYP_KEY,[dbo].[fnGetMasterLkpNameByID](A.DOC_TYP_KEY) DocumentType,
             A.UPLOAD_USER_ID,[dbo].[fnGetUserNameByID](A.UPLOAD_USER_ID) UploadedUser,
             FL_NM,FL_FLDR_PTH,FL_COMMT,FL_UPLOAD_DT,A.APPL_FL_UPLOAD_LOG_KEY,A.FL_STS_KEY,
             A.ETL_PROC_END_DT,A.FL_SZ

FROM		  APPL_FL_UPLOAD_LOG        A
JOIN		  ORG                                      B
ON			  A.ORG_KEY            =     B.ORG_KEY
JOIN		  MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN		  CLNT_ENGMT_USER_ASGN D
ON            A.CLNT_ENGMT_CD        =     D.CLNT_ENGMT_CD
JOIN		  USER_PRFL                         F
ON            a.UPLOAD_USER_ID           =            F.USER_ID
CROSS APPLY	  dbo.FnSplit(D.SECUR_SRVC_LIST_CD,',') AS K
JOIN		  USER_CLNT_SRVC_ASGN        E
ON            A.SECUR_SRVC_CD                   =      K.items
JOIN		  MSTR_LKP                          G
ON            G.MSTR_LKP_KEY         =           A.FL_STS_KEY
WHERE		  A.CLNT_ENGMT_CD     =     @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
AND           F.USER_TYP_KEY             =      18
AND           A.SECUR_SRVC_CD        =     K.items      
AND           C.LKP_ENTY_NM   =           'Active'
AND           G.LKP_ENTY_NM   <>   'ETL Failure'
AND           G.LKP_ENTY_NM   <>   'Scan Failure'
ORDER BY	  A.FL_UPLOAD_DT DESC

END

END TRY

BEGIN CATCH

    DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();
    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();

    PRINT 'Actual line number: ' + CAST(@ErrorLine AS VARCHAR(10));

    RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
  END CATCH
-- COMMIT TRANSACTION
END;
