CREATE PROCEDURE [dbo].[Report_UpdateVlunarabilityStatus]
(
       
	   @VULN_INSTC_KEY		 VARCHAR(30),
	   @VULN_INSTC_STS_CD	 VARCHAR(2),
	   @schema				 VARCHAR(50)
	   			

)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON

DECLARE @Query VARCHAR(max)

		SET		@Query ='UPDATE	'+ @schema+'.CLNT_VULN_INSTC
		SET		VULN_INSTC_STS_CD	=	@VULN_INSTC_STS_CD,
				UPDT_DT				=	GETDATE()
		WHERE	CLNT_VULN_INSTC_KEY	=   '+@VULN_INSTC_KEY
		EXECUTE (@Query)
	   
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
-- COMMIT TRANSACTION
END
