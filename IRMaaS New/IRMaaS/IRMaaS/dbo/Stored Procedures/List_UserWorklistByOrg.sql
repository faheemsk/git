CREATE PROCEDURE [dbo].[List_UserWorklistByOrg]
(
      @OrgID INT
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

      SELECT  A.USER_ID,A.ORG_KEY,B.ORG_NM,[dbo].[fnGetMasterLkpNameByID](USER_TYP_KEY) USER_TYP_KEY,
                       [dbo].[fnGetMasterLkpNameByID](A.ROW_STS_KEY)ROW_STS_KEY,
              FST_NM [First Name],LST_NM,MIDL_NM,(FST_NM+' '+LST_NM) [User Name],JOB_TITL_NM,EMAIL_ID,TEL_NBR,
              USER_VERF_IND, LCK_IND,
              LOGIN_ATMPT_CNT,LST_LOGIN_DT,PSWD_RSET_DT,A.CREAT_DT,A.CREAT_USER_ID
      FROM    USER_PRFL           A
      JOIN    ORG           B
      ON      A.ORG_KEY   = B.ORG_KEY
      WHERE   A.ORG_KEY   = CASE WHEN @OrgID = 0 THEN A.ORG_KEY ELSE @OrgID END
       -- AND     B.ROW_STS_KEY = 1
         AND     A.USER_ID > 3
         ORDER BY A.USER_ID DESC 

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
