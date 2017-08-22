CREATE PROCEDURE [dbo].[DEL_CLNTSECURSRVCENGMT](
	   
	   @CLNT_ENGMT_CD	VARCHAR(30),
	   @SECUR_SRVC_CD	VARCHAR(30),
	   @USER_ID			INTEGER,
	   @Flag			VARCHAR(1)
	  
       )

       AS
       BEGIN
	   DECLARE @LiCOUNT INTEGER = 0;
              BEGIN TRY
			  
              SET NOCOUNT ON

			IF  @Flag = 'I'
			BEGIN
					DELETE FROM USER_CLNT_SRVC_ASGN WHERE CLNT_ENGMT_CD = @CLNT_ENGMT_CD AND SECUR_SRVC_CD = @SECUR_SRVC_CD

					DELETE FROM CLNT_SECUR_SRVC_ENGMT WHERE CLNT_ENGMT_CD = @CLNT_ENGMT_CD AND SECUR_SRVC_CD = @SECUR_SRVC_CD 

                    SELECT @@ROWCOUNT AS RETVAL
			END

			IF  @Flag = 'P'
			BEGIN
					DELETE A FROM USER_CLNT_SRVC_ASGN A
					JOIN	USER_PRFL				  B
					ON		A.USER_ID	=	B.USER_ID
					 WHERE A.CLNT_ENGMT_CD = @CLNT_ENGMT_CD 
					 AND SECUR_SRVC_CD = @SECUR_SRVC_CD
					 AND B.USER_TYP_KEY	= 18
					 

			

                    SELECT @@ROWCOUNT AS RETVAL
			END

			IF  @Flag = 'U'
			BEGIN
					 DELETE  FROM USER_CLNT_SRVC_ASGN  
					 WHERE	 CLNT_ENGMT_CD =  @CLNT_ENGMT_CD
					 AND     SECUR_SRVC_CD = @SECUR_SRVC_CD
					 AND	 USER_ID	  =  @USER_ID			 

                    SELECT @@ROWCOUNT AS RETVAL
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

	   