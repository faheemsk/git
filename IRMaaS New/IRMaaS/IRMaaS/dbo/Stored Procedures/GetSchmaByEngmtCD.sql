CREATE PROCEDURE [dbo].[GetSchmaByEngmtCD]
(
       @CLNT_ENGMT_CD            VARCHAR(20)
       
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON


              SELECT        A.CLNT_ENGMT_CD,D.ORG_KEY,D.ORG_SCHM
              FROM			CLNT_ENGMT              A
			  JOIN			ORG						D
			  ON			A.CLNT_ORG_KEY		 =	D.ORG_KEY
              WHERE			A.CLNT_ENGMT_CD		 =  @CLNT_ENGMT_CD
              AND           A.ROW_STS_KEY		 =  1
			  AND			D.ROW_STS_KEY		 =  1
             

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
-- COMMIT TRANSACTION
END

