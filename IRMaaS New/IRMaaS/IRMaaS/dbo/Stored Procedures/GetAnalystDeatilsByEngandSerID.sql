/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: GetAnalystDeatilsByEngandSerID
	** Desc: This procedure is Engagement Analyst details.
	** Auth: Prasad varma
	** Date: 19/05/2016 
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    25/05/2016      xxxxx          
	*******************************/
CREATE PROCEDURE [dbo].[GetAnalystDeatilsByEngandSerID]
@CLNT_ENGMT_CD	VARCHAR(30),
@SECUR_SRVC_CD	VARCHAR(10)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	DISTINCT B.USER_ID AnalystID,(B.FST_NM + ' ' + B.LST_NM) [Analyst Name],
		B.EMAIL_ID [Analyst email]
		
FROM	USER_CLNT_SRVC_ASGN			A
JOIN	USER_PRFL					B
ON		B.USER_ID		=	    A.USER_ID	
WHERE	A.CLNT_ENGMT_CD	=	@CLNT_ENGMT_CD
AND		A.SECUR_SRVC_CD	=   @SECUR_SRVC_CD
AND		B.USER_TYP_KEY	= 16


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




