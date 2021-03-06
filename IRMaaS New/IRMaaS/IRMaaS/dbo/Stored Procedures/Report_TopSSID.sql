﻿CREATE PROCEDURE [dbo].[Report_TopSSID]
(

	@CLNT_ENGMT_CD	VARCHAR(100),
	@SECUR_SRVC_CD	VARCHAR(100),
	@Flag			VARCHAR(2),-- AP - Application,SS-SSID,AL- ALL
	@APPNETCD		VARCHAR(500),
	@schema			VARCHAR(50)			
	
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
 DECLARE @Query VARCHAR(max)
 DECLARE @LiCOUNT INTEGER
	IF @Flag = 'AL'
	BEGIN	  
	SET		@Query ='
    SELECT   CLNT_ENGMT_CD,SECUR_SRVC_CD,NETBIOS_NM,COUNT(CLNT_VULN_INSTC_KEY)VULCOUNT
    FROM     '+ @schema+'.CLNT_VULN_INSTC
	WHERE    CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD + '''
	AND		 SECUR_SRVC_CD			= '''+@SECUR_SRVC_CD + '''
	AND		 VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
	AND		 VULN_SEV_CD			NOT IN(''I'')
	AND		 ROW_STS_KEY			= 1
    GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,NETBIOS_NM
    ORDER BY COUNT(SFTW_NM) DESC'
	EXECUTE (@Query)
	END

	IF @Flag = 'SS'
	BEGIN	  
	SET		@Query ='
    SELECT   A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.NETBIOS_NM,COUNT(A.CLNT_VULN_INSTC_KEY)VULCOUNT
    FROM     '+ @schema+'.CLNT_VULN_INSTC			 A
	JOIN	 dbo.FnSplit('''+@APPNETCD+''','','')  B
	ON		 A.NETBIOS_NM			= B.items
	WHERE    A.CLNT_ENGMT_CD		= '''+@CLNT_ENGMT_CD + '''
	AND		 A.SECUR_SRVC_CD		= '''+@SECUR_SRVC_CD + '''
	AND		 A.VULN_INSTC_STS_CD	NOT IN(''D'',''FP'')
	AND		 A.VULN_SEV_CD			NOT IN(''I'')
	AND		 A.ROW_STS_KEY		= 1
    GROUP BY A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.NETBIOS_NM
    ORDER BY COUNT(A.NETBIOS_NM) DESC'
	EXECUTE (@Query)
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
-- COMMIT TRANSACTION
END



