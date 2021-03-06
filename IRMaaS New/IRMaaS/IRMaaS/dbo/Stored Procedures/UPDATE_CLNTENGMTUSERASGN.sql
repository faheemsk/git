﻿
CREATE PROCEDURE [dbo].[UPDATE_CLNTENGMTUSERASGN](
	   
	   @CLNT_ENGMT_USER_ASGN_KEY INTEGER,
	   @ROW_STS_KEY				 INTEGER,
	   @CLNT_ENGMT_CD			 VARCHAR(30),
	   @USER_ID					 INTEGER,
	   @SRV_LST_CD				 VARCHAR(150),
	   @UPDT_DT					 DATETIME,
	   @UPDT_USER_ID			 INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

					UPDATE  CLNT_ENGMT_USER_ASGN
					SET		CLNT_ENGMT_CD			 =  @CLNT_ENGMT_CD,
							ROW_STS_KEY				 =  @ROW_STS_KEY,
							[USER_ID]				 =  @USER_ID,
							SECUR_SRVC_LIST_CD   	 =  @SRV_LST_CD,
							UPDT_DT					 =  GETDATE(),
							UPDT_USER_ID			 =  @UPDT_USER_ID

					WHERE   CLNT_ENGMT_USER_ASGN_KEY =	@CLNT_ENGMT_USER_ASGN_KEY

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





