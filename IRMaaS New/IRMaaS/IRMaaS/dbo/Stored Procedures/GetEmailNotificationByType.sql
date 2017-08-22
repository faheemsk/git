/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: GetEmailNotificationByType
	** Desc: This procedure is USED TO FETCH EMAIL NOTIFICATION DETAils by  notification type.
	** Auth: Prasad varma
	** Date: 13/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          
	*******************************/
CREATE PROCEDURE [dbo].[GetEmailNotificationByType](
@NTF_TYP_NM  varchar(255)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

SELECT	A.NTF_MSG_KEY,A.MSG_SBJ_TXT,A.MSG_CNTN_TXT
FROM	NTF_MSG		  A
WHERE	A.NTF_TYP_NM = @NTF_TYP_NM AND A.ROW_STS_KEY	= 1

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







