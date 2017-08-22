/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: INS_MSTR_LKP
       ** Desc: This procedure insert data into MSTR_LKP table
       ** Auth: Prasad varma
       ** Date: 19/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    00/00/1999      xxxxx          
       *******************************/

CREATE PROCEDURE [dbo].[INS_MSTR_LKP]
(
       @LKP_ENTY_TYP_NM     VARCHAR(500),
       @LKP_ENTY_NM         VARCHAR(500),
       @LKP_ENTY_DESC             VARCHAR(100),
       @CREAT_USER_ID             INTEGER


       )
       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

              

                     INSERT MSTR_LKP(LKP_ENTY_TYP_NM,LKP_ENTY_NM,LKP_ENTY_DESC,CREAT_DT,CREAT_USER_ID) VALUES
                     (@LKP_ENTY_TYP_NM,@LKP_ENTY_NM,@LKP_ENTY_DESC,GETDATE(),@CREAT_USER_ID)

                     SELECT SCOPE_IDENTITY() AS RETVAL

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
              
       END;







