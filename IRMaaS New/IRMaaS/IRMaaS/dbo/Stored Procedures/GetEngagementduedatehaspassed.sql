CREATE PROCEDURE [dbo].[GetEngagementduedatehaspassed]
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT DISTINCT A.CLNT_ENGMT_CD,D.USER_TYP_KEY,(D.FST_NM + ' ' + D.LST_NM) [Engagement Lead],D.EMAIL_ID [Engagement Lead email],
              D.USER_ID [Engagement userid],[dbo].[fnGetMasterLkpNameByID] (D.USER_TYP_KEY) [User Type]

FROM   CLNT_ENGMT                               A
JOIN   CLNT_ENGMT_USER_ASGN       B
ON            B.CLNT_ENGMT_CD                          =      A.CLNT_ENGMT_CD      
JOIN   USER_PRFL                                D
ON            D.USER_ID                                =   B.USER_ID
WHERE  DATEDIFF(DAY,CONVERT(VARCHAR(10),A.ENGMT_EST_END_DT,101),CONVERT(VARCHAR(10),GETDATE(),101)) = 1


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

