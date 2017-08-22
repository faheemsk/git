

CREATE PROCEDURE [dbo].[UPDATE_EngagementServicedatebyEngservcode](
	   @CLNT_ENGMT_CD       VARCHAR(30),
	   @SECUR_SRVC_CD		VARCHAR(10),
	   @SRVC_EST_STRT_DT	DATETIME,
	   @SRVC_EST_END_DT		DATETIME
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

					UPDATE  CLNT_SECUR_SRVC_ENGMT
					SET		SRVC_EST_STRT_DT	= @SRVC_EST_STRT_DT,
							SRVC_EST_END_DT 	= @SRVC_EST_END_DT
					WHERE   CLNT_ENGMT_CD	  =  @CLNT_ENGMT_CD
					AND	    SECUR_SRVC_CD	  =  @SECUR_SRVC_CD
				

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






