CREATE PROCEDURE [dbo].[UPDATE_USERCLNTSRVCASGN](
	   
	   @USER_CLNT_SRVC_ASGN_KEY		INTEGER,
	   @USER_ID						INTEGER,
	   @USER_STRT_DT				DATETIME,
	   @USER_END_DT					DATETIME,
	   @SECUR_SRVC_CD				VARCHAR(30),
	   @CLNT_ENGMT_CD				VARCHAR(30),
	   @ROW_STS_KEY					INTEGER,
	   @UPDT_USER_ID				INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

					UPDATE  USER_CLNT_SRVC_ASGN
					SET		[USER_ID]				  =  @USER_ID,
							USER_STRT_DT			  =  @USER_STRT_DT,
							USER_END_DT				  =  @USER_END_DT,
							SECUR_SRVC_CD             =  @SECUR_SRVC_CD,
							CLNT_ENGMT_CD			  =  @CLNT_ENGMT_CD,
							ROW_STS_KEY				  =  @ROW_STS_KEY,
							UPDT_DT					  =  GETDATE(),
							UPDT_USER_ID			  =  @UPDT_USER_ID

					WHERE   USER_CLNT_SRVC_ASGN_KEY   =	 @USER_CLNT_SRVC_ASGN_KEY

                    SELECT @@ROWCOUNT AS RETVAL

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
              
       END




