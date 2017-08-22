CREATE PROCEDURE [dbo].[GetEngagementServiceduedatehaspassedEmials]
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT DISTINCT A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,C.SECUR_SRVC_NM,B.USER_TYP_KEY [Analyst UserType] ,
              (b.FST_NM + ' ' + b.LST_NM) [Analyst],A.SRVC_EST_STRT_DT,A.SRVC_EST_END_DT,B.EMAIL_ID [Analyst Email],
              F.USER_TYP_KEY [Lead UserType],(F.FST_NM + ' ' + F.LST_NM) [Lead Name],G.ORG_NM
              

FROM   CLNT_SECUR_SRVC_ENGMT   A
JOIN   SECUR_SRVC                        C
ON            A.SECUR_SRVC_CD      = C.SECUR_SRVC_CD    
JOIN   dbo.CLNT_ENGMT_USER_ASGN   E
ON            A.CLNT_ENGMT_CD = E.CLNT_ENGMT_CD 
JOIN   USER_PRFL                         F
ON            E.USER_ID            =          F.USER_ID
JOIN   USER_CLNT_SRVC_ASGN        D
ON            A.SECUR_SRVC_CD      =             D.SECUR_SRVC_CD
AND           A.CLNT_ENGMT_CD            =   D.CLNT_ENGMT_CD
JOIN   USER_PRFL                         B
ON            D.USER_ID            =          B.USER_ID
JOIN   ORG                                                    G
ON            F.ORG_KEY                                =   G.ORG_KEY
WHERE  DATEDIFF(DAY,CONVERT(VARCHAR(10),A.SRVC_EST_END_DT,101),CONVERT(VARCHAR(10),GETDATE(),101)) = 1
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
