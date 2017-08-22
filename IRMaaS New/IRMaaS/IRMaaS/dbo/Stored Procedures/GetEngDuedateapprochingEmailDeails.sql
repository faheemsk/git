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
CREATE PROCEDURE [dbo].[GetEngDuedateapprochingEmailDeails]
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	B.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,B.ENGMT_EST_END_DT,C.USER_ID,(C.FST_NM+' '+C.LST_NM) UserName,C.EMAIL_ID
FROM	CLNT_ENGMT_USER_ASGN		A
JOIN	CLNT_ENGMT					B
ON		B.CLNT_ENGMT_CD = A.CLNT_ENGMT_CD
JOIN	USER_PRFL					C
ON		C.USER_ID		= A.USER_ID
WHERE   CONVERT(VARCHAR(10),B.ENGMT_EST_END_DT,101) < DATEADD(DAY, -15,CONVERT(VARCHAR(10),GETDATE(),101))

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

