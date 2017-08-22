/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: INS_USER_EVNT_LOG
       ** Desc: This procedure INSERT  into USER_EVNT_LOG table
       ** Auth: Prasad varma
       ** Date: 22/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    00/00/1999      xxxxx          
       *******************************/
CREATE PROCEDURE [dbo].[INS_USER_EVNT_LOG]
(
	@USER_ID	        INTEGER,
	@ACT_NM  	        VARCHAR(100),
	@MDUL_NM	        VARCHAR(100),
	@EVNT_DT            DATETIME,
	@EVNT_DESC			VARCHAR(1000),
	@CLNT_ENGMT_CD		VARCHAR(30),
	@CLNT_SRVC_ENGMT_CD VARCHAR(150),
	@IPADR			    VARCHAR(20),
	@SFTW_INFO_TXT      VARCHAR(150),
	@FILL_TXT           TEXT
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

INSERT USER_EVNT_LOG(USER_ID,ACT_NM ,MDUL_NM,EVNT_DT ,EVNT_DESC,CLNT_ENGMT_CD,CLNT_SRVC_ENGMT_CD,IPADR,SFTW_INFO_TXT,FILL_TXT) VALUES
(@USER_ID,@ACT_NM ,@MDUL_NM,@EVNT_DT ,@EVNT_DESC,@CLNT_ENGMT_CD,@CLNT_SRVC_ENGMT_CD,@IPADR,@SFTW_INFO_TXT,@FILL_TXT)



SELECT SCOPE_IDENTITY() AS RETVAL

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







