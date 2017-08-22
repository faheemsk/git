
CREATE PROCEDURE [dbo].[DEL_CLNTENGMTUSERASGN](
	   
	   @CLNT_ENGMT_CD	VARCHAR(30)
      
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON


			  DELETE FROM CLNT_ENGMT_USER_ASGN  
			  WHERE  CLNT_ENGMT_CD	=	@CLNT_ENGMT_CD 
			
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





