/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: UPDATE_USER_SESS_LOG
       ** Desc: This procedure UPDATE data into USER_SESS_LOG table
       ** Auth: Prasad varma
       ** Date: 22/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    00/00/1999      xxxxx          
       *******************************/

CREATE PROCEDURE [dbo].[UPDATE_USER_SESS_LOG](
	   @FLAG			   VARCHAR(1),	
       @USER_ID            INTEGER,
       @LST_ACT_DT         DATETIME,
	   @SESS_ID            VARCHAR(150)
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

		IF @FLAG = 'U'

		BEGIN
			     UPDATE  USER_SESS_LOG
                  SET     LST_ACT_DT =     GETDATE()
                  WHERE   USER_ID    =   @USER_ID

                 
		END
		
		IF @FLAG = 'D'

		BEGIN 
		 
			DELETE FROM USER_SESS_LOG WHERE [USER_ID] = @USER_ID 
		    -- AND SESS_ID =@SESS_ID

			
			
		END

		IF @FLAG = 'L'

		BEGIN 
		 
			DELETE FROM USER_SESS_LOG WHERE [USER_ID] = @USER_ID 
		    AND SESS_ID =@SESS_ID

			
			
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
              
       END







