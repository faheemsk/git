/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: GetLeadDeatilsByEngandSerID
	** Desc: This procedure is Engagement Lead details.
	** Auth: Prasad varma
	** Date: 19/05/2016 
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    25/05/2016      xxxxx          
	*******************************/
CREATE PROCEDURE [dbo].[GetLeadDeatilsByEngandSerID]
@CLNT_ENGMT_CD	VARCHAR(30)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	C.USER_ID [Engagment LeadID],(C.FST_NM + ' ' + C.LST_NM) [Engagement Lead Name],dbo.fnGetMasterLkpNameByID(C.USER_TYP_KEY) UserType,C.EMAIL_ID [Engagement Lead email]
		
FROM	CLNT_ENGMT_USER_ASGN		A
JOIN	USER_PRFL					C
ON		C.USER_ID		=	    A.USER_ID	
WHERE	A.CLNT_ENGMT_CD	=	@CLNT_ENGMT_CD


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




