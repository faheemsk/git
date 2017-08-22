CREATE PROCEDURE [dbo].[Report_TopHitrust]
(
       @CLNT_ENGMT_CD    VARCHAR(50),
	   @SECUR_SRVC_CD	 VARCHAR(50),	
	   @schema			 VARCHAR(50)		
)
AS
BEGIN

DECLARE @Query VARCHAR(max)
BEGIN TRY
SET NOCOUNT ON
			  SET		  @Query ='	              
              SELECT	  A.SECUR_CTL_CD SECUR_OBJ_CD,A.SECUR_CTL_NM SECUR_OBJ_NM,COUNT(C.CLNT_VULN_INSTC_KEY)FindingCount
              FROM		  SECUR_CTL							A
              JOIN		  '+ @schema+'.CLNT_VULN_SECUR_CTL				B
			  ON		  A.SECUR_CTL_CD				=	B.SECUR_CTL_CD
			  JOIN		  '+ @schema+'.CLNT_VULN_INSTC					C
			  ON		  B.CLNT_VULN_INSTC_KEY			=	C.CLNT_VULN_INSTC_KEY
              WHERE		  C.CLNT_ENGMT_CD			    =	'''+@CLNT_ENGMT_CD + '''
			  AND		  C.SECUR_SRVC_CD				=   '''+ @SECUR_SRVC_CD + '''
			  AND		  C.VULN_INSTC_STS_CD			NOT IN(''D'',''FP'')
			  AND		  C.VULN_SEV_CD					NOT IN(''I'')
			  AND		  A.REG_CMPLN_CD				=   ''HITRUST''
			  AND		  C.ROW_STS_KEY					= 1
			  GROUP BY	  A.SECUR_CTL_CD,A.SECUR_CTL_NM
			  ORDER BY	  COUNT(C.CLNT_VULN_INSTC_KEY) DESC'
			  EXECUTE (@Query)
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
