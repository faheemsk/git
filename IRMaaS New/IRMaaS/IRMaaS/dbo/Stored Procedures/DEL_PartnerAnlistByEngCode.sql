
CREATE PROCEDURE [dbo].[DEL_PartnerAnlistByEngCode](
	   
	   @CLNT_ENGMT_CD	VARCHAR(30),
	   @Userlist		VARCHAR(100)

       )

       AS
       BEGIN
	   DECLARE @LiCOUNT INTEGER = 0;
              BEGIN TRY
			  
              SET NOCOUNT ON

					DELETE  A 
					FROM	USER_CLNT_SRVC_ASGN	 A
					JOIN	USER_PRFL			 B
					ON		A.USER_ID		   = B.USER_ID
					WHERE	A.CLNT_ENGMT_CD	   = @CLNT_ENGMT_CD
					AND		A.CREAT_USER_ID NOT IN (SELECT items FROM dbo.FnSplit(@Userlist,','))
					AND		B.USER_TYP_KEY	   = 18
				
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

/****** Object:  StoredProcedure [dbo].[Get_ClntEngmtUserAsgnByID]    Script Date: 5/25/2016 11:03:58 AM ******/
SET ANSI_NULLS ON






