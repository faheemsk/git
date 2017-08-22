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
CREATE PROCEDURE [dbo].[GetEngserveicePastDuedateEmailDeails]
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	A.USER_CLNT_SRVC_ASGN_KEY,A.USER_ID,(B.FST_NM+' '+B.LST_NM) UserName,B.EMAIL_ID,C.SECUR_SRVC_NM,A.SECUR_SRVC_CD,
		A.CLNT_ENGMT_CD
FROM	USER_CLNT_SRVC_ASGN		A
JOIN	USER_PRFL				B
ON		A.USER_ID		=	B.USER_ID
JOIN	SECUR_SRVC				C
ON		A.SECUR_SRVC_CD	= C.SECUR_SRVC_CD	 
WHERE	CONVERT(VARCHAR(10),A.USER_END_DT,101) < CONVERT(VARCHAR(10),GETDATE(),101)
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


