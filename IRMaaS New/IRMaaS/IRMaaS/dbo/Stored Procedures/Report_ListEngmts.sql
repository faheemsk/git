CREATE PROCEDURE [dbo].[Report_ListEngmts]
(
       @PvcFlag             VARCHAR(1), -- 'R' Remediation,'U' for User specific data
       @CLNT_ORG_KEY INTEGER,
       @UserTypeID          INTEGER,
       @UserID                    INTEGER       
       
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON

              IF @PvcFlag ='U'
              BEGIN
              SELECT        A.CLNT_ENGMT_CD,A.CLNT_ENGMT_NM,A.SECUR_PKG_CD,B.SECUR_PKG_NM,D.ORG_SCHM
              FROM			CLNT_ENGMT              A
              JOIN			SECUR_PKG               B
              ON            A.SECUR_PKG_CD       =  B.SECUR_PKG_CD
              JOIN			CLNT_ENGMT_USER_ASGN    C
              ON            A.CLNT_ENGMT_CD      =  C.CLNT_ENGMT_CD
			  JOIN			ORG						D
			  ON			A.CLNT_ORG_KEY		 =	D.ORG_KEY
              WHERE			CLNT_ORG_KEY		 = CASE WHEN @UserTypeID = 17 THEN @CLNT_ORG_KEY ELSE CLNT_ORG_KEY END
              AND           A.ENGMT_STS_KEY		 = CASE WHEN @UserTypeID = 17 THEN 68 ELSE A.ENGMT_STS_KEY END
              AND           A.CLNT_ORG_KEY <> 1
              AND           A.ROW_STS_KEY		 = 1
              AND           C.USER_ID            =      @UserID
              
              UNION

              SELECT        A.CLNT_ENGMT_CD,A.CLNT_ENGMT_NM,A.SECUR_PKG_CD,B.SECUR_PKG_NM,D.ORG_SCHM
              FROM			CLNT_ENGMT             A
              JOIN			SECUR_PKG              B
              ON            A.SECUR_PKG_CD       = B.SECUR_PKG_CD
              JOIN			USER_CLNT_SRVC_ASGN	   C
              ON            A.CLNT_ENGMT_CD      = C.CLNT_ENGMT_CD
			  JOIN			ORG					   D
			  ON			A.CLNT_ORG_KEY		 = D.ORG_KEY
              WHERE  CLNT_ORG_KEY  = CASE WHEN @UserTypeID = 17 THEN @CLNT_ORG_KEY ELSE CLNT_ORG_KEY END
              AND           A.ENGMT_STS_KEY = CASE WHEN @UserTypeID = 17 THEN 68 ELSE A.ENGMT_STS_KEY END
              AND           A.CLNT_ORG_KEY <> 1
              AND           A.ROW_STS_KEY = 1
              AND           C.USER_ID            =      @UserID
              END
          
           IF @PvcFlag ='R'
              BEGIN
              SELECT        A.CLNT_ENGMT_CD,A.CLNT_ENGMT_NM,A.SECUR_PKG_CD,B.SECUR_PKG_NM,D.ORG_SCHM
              FROM			CLNT_ENGMT             A
              JOIN			SECUR_PKG              B
              ON            A.SECUR_PKG_CD       = B.SECUR_PKG_CD
			  JOIN			ORG						D
			  ON			A.CLNT_ORG_KEY		 =	D.ORG_KEY
              WHERE			A.CLNT_ORG_KEY <> 1
              AND           A.ROW_STS_KEY = 1
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

