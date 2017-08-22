
CREATE PROCEDURE [dbo].[UPDATE_CLNTRELENGMTINDENTIFIER](
	   
	   @CLNT_REL_ENGMT_ID_KEY	INTEGER,
	   @ROW_STS_KEY				INTEGER,
	   @CLNT_ENGMT_CD			VARCHAR(30),
	   @SRC_KEY					INTEGER,
	   @SRC_REL_ENGMT_ID		VARCHAR(150),
	   @UPDT_USER_ID			INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

					UPDATE  CLNT_REL_ENGMT_ID
					SET		CLNT_ENGMT_CD			=  @CLNT_ENGMT_CD,
							ROW_STS_KEY				=  @ROW_STS_KEY,
							SRC_KEY					=  @SRC_KEY,
							SRC_REL_ENGMT_ID		=  @SRC_REL_ENGMT_ID,
							UPDT_DT					=  GETDATE(),
							UPDT_USER_ID			=  @UPDT_USER_ID

					WHERE   CLNT_REL_ENGMT_ID_KEY	=  @CLNT_REL_ENGMT_ID_KEY

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






