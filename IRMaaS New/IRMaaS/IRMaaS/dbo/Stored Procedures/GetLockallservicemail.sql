/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: GetLockallservicemail
       ** Desc: This procedure is The file for all services of engagement Uplodaed and services locked.
       ** Auth: Prasad varma
       ** Date: 27/05/2016 
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    xx/xx/xxxx      xxxxx          
       *******************************/
CREATE PROCEDURE [dbo].[GetLockallservicemail]
@CLNT_ENGMT_CD       VARCHAR(30)

AS
BEGIN
BEGIN TRY 
SET NOCOUNT ON

DECLARE @SRVCOUNT INTEGER = 0
DECLARE @LOCKCOUNT INTEGER = 0


SELECT @SRVCOUNT =COUNT(CLNT_ENGMT_CD) FROM [dbo].[CLNT_SECUR_SRVC_ENGMT] WHERE CLNT_ENGMT_CD=@CLNT_ENGMT_CD

SELECT @LOCKCOUNT =COUNT(FL_LCK_IND) FROM CLNT_SECUR_SRVC_ENGMT WHERE CLNT_ENGMT_CD=@CLNT_ENGMT_CD AND FL_LCK_IND=1

IF @SRVCOUNT = @LOCKCOUNT
BEGIN

SELECT 1 AS RETVAL

--SELECT C.USER_ID [Engagment LeadID],(C.FST_NM + ' ' + C.LST_NM) [Engagement Lead Name],dbo.fnGetMasterLkpNameByID(C.USER_TYP_KEY),C.EMAIL_ID [[Engagement Lead email]
              
--FROM   CLNT_SECUR_SRVC_ENGMT      A
--JOIN   CLNT_ENGMT_USER_ASGN       B
--ON            A.CLNT_ENGMT_CD      =                    B.CLNT_ENGMT_CD
--JOIN   USER_PRFL                   C
--ON            C.USER_ID            =                    B.USER_ID    
--WHERE  A.CLNT_ENGMT_CD      =                    @CLNT_ENGMT_CD

END

IF @SRVCOUNT <> @LOCKCOUNT
BEGIN

SELECT 0 AS RETVAL



END
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

