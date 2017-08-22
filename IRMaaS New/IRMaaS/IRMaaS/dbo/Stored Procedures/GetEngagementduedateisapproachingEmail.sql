/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: GetEngserveicePastDuedateEmailDeails
	** Desc: This procedure is USED TO FEATCH ENGAGEMENT ASSIGN SERVICE PASTDATE USERS LIST.
	** Auth: Prasad varma
	** Date: 19/05/2016 
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    19/05/2016      xxxxx          
	*******************************/ 

CREATE PROCEDURE [dbo].[GetEngagementduedateisapproachingEmail]
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	DISTINCT B.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,B.ENGMT_EST_END_DT,(C.FST_NM+' '+C.LST_NM) [Engagement Lead],C.EMAIL_ID[Engagement Lead Email],
		(D.FST_NM+' '+D.LST_NM) [Analyst],D.EMAIL_ID[AnalystEmail],[dbo].[fnGetMasterLkpNameByID] (C.USER_TYP_KEY) [User Type]
FROM	CLNT_ENGMT_USER_ASGN		A
JOIN	CLNT_ENGMT					B
ON		B.CLNT_ENGMT_CD = A.CLNT_ENGMT_CD
JOIN	CLNT_ENGMT_USER_ASGN		E
ON		A.CLNT_ENGMT_CD		=	E.CLNT_ENGMT_CD	
JOIN	USER_PRFL					C
ON		C.USER_ID		= A.USER_ID
JOIN	USER_PRFL					D
ON		D.USER_ID		= A.USER_ID

WHERE  DATEDIFF(DAY,CONVERT(VARCHAR(10),GETDATE(),101),CONVERT(VARCHAR(10),B.ENGMT_EST_END_DT,101)) = 15


END TRY

BEGIN CATCH

    DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();

    PRINT 'Actual error number: ' + CAST(@ErrorNumber AS VARCHAR(10));
    PRINT 'Actual line number: ' + CAST(@ErrorLine AS VARCHAR(10));

    RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState);
  END CATCH
-- COMMIT TRANSACTION
END






