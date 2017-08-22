CREATE PROCEDURE  [dbo].[Get_UploadedFileCountByStatus]
(
      @CLNT_ENGMT_CD	VARCHAR(30),
      @SECUR_SRVC_CD	VARCHAR(10),
      @UPLOAD_USER_ID   INTEGER,
      @Status           INTEGER,
     @Flag              VARCHAR(2)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

IF @Flag = 'EL'
BEGIN
SELECT      A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,COUNT(A.APPL_FL_UPLOAD_LOG_KEY) DocCount
FROM		APPL_FL_UPLOAD_LOG        A
JOIN		MSTR_LKP                  C
ON          C.MSTR_LKP_KEY    =       A.ROW_STS_KEY
WHERE		CLNT_ENGMT_CD     =       @CLNT_ENGMT_CD
AND         A.SECUR_SRVC_CD   =       @SECUR_SRVC_CD
AND         C.LKP_ENTY_NM     =       'Active'
AND         A.FL_STS_KEY      =        @Status
GROUP BY	A.FL_STS_KEY,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD 

END

IF @Flag = 'EA'
BEGIN

SELECT      A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,COUNT(A.APPL_FL_UPLOAD_LOG_KEY) DocCount
FROM		APPL_FL_UPLOAD_LOG        A
JOIN   ORG                                      B
ON      A.ORG_KEY            =     B.ORG_KEY
JOIN   MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
WHERE   CLNT_ENGMT_CD     =       @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
AND           C.LKP_ENTY_NM   =           'Active'
AND           A.FL_STS_KEY    =           @Status

GROUP BY A.FL_STS_KEY,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD 


END

IF @Flag = 'PL'
BEGIN
SELECT     A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,COUNT(A.APPL_FL_UPLOAD_LOG_KEY) DocCount

FROM    APPL_FL_UPLOAD_LOG        A

JOIN   MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN   CLNT_ENGMT_USER_ASGN D
ON            A.CLNT_ENGMT_CD        =     D.CLNT_ENGMT_CD
JOIN   USER_PRFL                         F
ON            a.UPLOAD_USER_ID           =            F.USER_ID
CROSS APPLY dbo.FnSplit(D.SECUR_SRVC_LIST_CD,',') AS K
JOIN   USER_CLNT_SRVC_ASGN        E
ON            A.SECUR_SRVC_CD                   =      K.items
WHERE   A.CLNT_ENGMT_CD     =     @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
AND           F.USER_TYP_KEY             =      18
AND           A.SECUR_SRVC_CD        =     K.items      
AND           C.LKP_ENTY_NM   =           'Active'
AND           A.FL_STS_KEY    =           @Status
GROUP BY A.FL_STS_KEY,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD 
END

IF @Flag = 'PU'
BEGIN

SELECT    A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,COUNT(A.APPL_FL_UPLOAD_LOG_KEY) DocCount

FROM    APPL_FL_UPLOAD_LOG        A

JOIN   MSTR_LKP                          C
ON            C.MSTR_LKP_KEY    =        A.ROW_STS_KEY
JOIN   CLNT_ENGMT_USER_ASGN D
ON            A.CLNT_ENGMT_CD        =     D.CLNT_ENGMT_CD
JOIN   USER_PRFL                         F
ON            a.UPLOAD_USER_ID           =            F.USER_ID
CROSS APPLY dbo.FnSplit(D.SECUR_SRVC_LIST_CD,',') AS K
JOIN   USER_CLNT_SRVC_ASGN        E
ON            A.SECUR_SRVC_CD                   =      K.items
WHERE   A.CLNT_ENGMT_CD     =     @CLNT_ENGMT_CD
AND           A.SECUR_SRVC_CD        =           @SECUR_SRVC_CD
AND           F.USER_TYP_KEY             =      18
AND           A.SECUR_SRVC_CD        =     K.items      
AND           C.LKP_ENTY_NM   =           'Active'
AND           A.FL_STS_KEY    =           @Status
GROUP BY A.FL_STS_KEY,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD 

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
