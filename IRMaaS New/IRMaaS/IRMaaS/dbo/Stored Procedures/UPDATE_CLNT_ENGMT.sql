CREATE PROCEDURE [dbo].[UPDATE_CLNT_ENGMT](
	   @CLNT_ENGMT_CD       VARCHAR(30),	
       @ENGMT_STS_KEY       INTEGER,
       @ROW_STS_KEY			INTEGER,		 
       @CLNT_ENGMT_NM       VARCHAR(150), 
       @ENGMT_STRT_DT       DATETIME, 
       @ENGMT_EST_END_DT    DATETIME, 
       @CLNT_ENGMT_DESC     VARCHAR(1000), 
       @ENGMT_COMMT         TEXT,
       @UPDT_USER_ID        INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

					UPDATE  CLNT_ENGMT
					SET	--	ENGMT_STS_KEY     =  @ENGMT_STS_KEY,
							CLNT_ENGMT_CD     =  @CLNT_ENGMT_CD,
							ROW_STS_KEY		  =  @ROW_STS_KEY,
							CLNT_ENGMT_NM     =  @CLNT_ENGMT_NM,
							ENGMT_STRT_DT	  =  @ENGMT_STRT_DT,
							ENGMT_EST_END_DT  =  @ENGMT_EST_END_DT,
							CLNT_ENGMT_DESC   =  @CLNT_ENGMT_DESC,
							ENGMT_COMMT		  =  @ENGMT_COMMT,
							UPDT_DT			  =  GETDATE(),
							UPDT_USER_ID	  =  @UPDT_USER_ID
					WHERE   CLNT_ENGMT_CD	  =  @CLNT_ENGMT_CD

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
