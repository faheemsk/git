CREATE PROCEDURE [dbo].[UPDATE_UserPasswordDetailsbyUID](
       @USER_ID            INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

                  UPDATE	USER_PRFL
                  SET		PSWD_RSET_DT	=   GETDATE(),
							UPDT_DT			=	GETDATE(),
							UPDT_USER_ID	=	@USER_ID,
							LST_LOGIN_DT    =   GETDATE(),
							USER_VERF_IND	=   1
							
				  WHERE		USER_ID			=   @USER_ID

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







