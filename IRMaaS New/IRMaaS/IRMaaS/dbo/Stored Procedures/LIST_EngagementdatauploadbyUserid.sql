
CREATE PROCEDURE [dbo].[LIST_EngagementdatauploadbyUserid]
(
       @UserID       INTEGER       
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON


       SELECT dbo.fnGetParentOrgNameID(B.CLNT_ORG_KEY) [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
       A.FL_LCK_IND,G.USER_STRT_DT,G.USER_END_DT,G.USER_ID
       FROM   CLNT_SECUR_SRVC_ENGMT   A
       JOIN   CLNT_ENGMT                        B
       ON            A.CLNT_ENGMT_CD                   =      B.CLNT_ENGMT_CD
       JOIN   SECUR_SRVC                        D
       ON            D.SECUR_SRVC_CD                   =      A.SECUR_SRVC_CD
       JOIN   USER_CLNT_SRVC_ASGN        G
       ON            B.CLNT_ENGMT_CD                   =      G.CLNT_ENGMT_CD
       WHERE  G.USER_ID                         =   @UserID  


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

