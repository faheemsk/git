﻿CREATE PROCEDURE [dbo].[Report_TopOSCategories]
(

	@CLNT_ENGMT_CD	VARCHAR(100),
	@SECUR_SRVC_CD	VARCHAR(100),
	@schema			VARCHAR(50)		
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
 DECLARE @Query VARCHAR(max)
 DECLARE @LiCOUNT INTEGER
 SET		@Query ='		 
 SELECT   A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,C.OS_CATGY_KEY,C.OS_CATGY_NM,COUNT(A.CLNT_VULN_INSTC_KEY)VULN_COUNT
 FROM	  '+ @schema+'.CLNT_VULN_INSTC			  A
 JOIN	  OS						  B
 ON		  A.OS_KEY					= B.OS_KEY
 JOIN	  OS_CATGY					  C
 ON		  B.OS_CATGY_KEY			= C.OS_CATGY_KEY
 WHERE    A.CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD + '''
 AND	  A.SECUR_SRVC_CD			= '''+ @SECUR_SRVC_CD + '''
 AND	  A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
 AND	  A.VULN_SEV_CD				NOT IN(''I'')
 AND	  A.ROW_STS_KEY				=  1
 GROUP BY A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,C.OS_CATGY_KEY,OS_CATGY_NM'
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