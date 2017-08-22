CREATE PROCEDURE [dbo].[UPDATE_USER_PRFL](
       @USER_ID            INTEGER,
       @ORG_KEY            INTEGER,
       @USER_TYP_KEY       INTEGER,
       @ROW_STS_KEY        INTEGER, 
       @FST_NM             VARCHAR(150), 
       @LST_NM             VARCHAR(150), 
       @MIDL_NM            VARCHAR(150), 
       @JOB_TITL_NM        VARCHAR(150), 
       @TEL_NBR            VARCHAR(20),
       @UPDAT_USER_ID      INTEGER,
	   @STS_COMMT_TXT		TEXT 
       
       )

       AS
       BEGIN



              BEGIN TRY
			  DECLARE @liRowStatus INTEGER = 0
			  SET NOCOUNT ON

			      SELECT    @liRowStatus = ROW_STS_KEY FROM USER_PRFL WHERE  USER_ID =   @USER_ID

                  UPDATE	USER_PRFL
                  SET		ORG_KEY			=	@ORG_KEY,
							USER_TYP_KEY	=	@USER_TYP_KEY,
							ROW_STS_KEY		=	@ROW_STS_KEY,
							FST_NM			=	@FST_NM,
							LST_NM			=	@LST_NM,
							MIDL_NM			=	@MIDL_NM,
							JOB_TITL_NM		=	@JOB_TITL_NM,
							TEL_NBR			=	@TEL_NBR,
							UPDT_DT			=	GETDATE(),
							UPDT_USER_ID	=	@UPDAT_USER_ID,
							STS_COMMT_TXT   =   @STS_COMMT_TXT,
							USER_VERF_IND	=   CASE WHEN @liRowStatus = 2 AND @ROW_STS_KEY = 1 THEN 0 ELSE USER_VERF_IND END
							
					 WHERE  USER_ID			=   @USER_ID

                     SELECT 1 AS RETVAL

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




/****** Object:  StoredProcedure [dbo].[UPDATE_UnlockUser]    Script Date: 5/11/2016 7:10:52 PM ******/
SET ANSI_NULLS ON



