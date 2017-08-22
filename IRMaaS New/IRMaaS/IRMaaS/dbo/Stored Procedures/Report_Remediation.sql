CREATE PROCEDURE [dbo].[Report_Remediation]
(
       @CLNT_ENGMT_CD       VARCHAR(100),
       @SECUR_SRVC_CD       VARCHAR(10),
	   @schema				VARCHAR(50)  
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)
DECLARE @LiCOUNT INTEGER
	   SET		@Query ='       
       SELECT   CLNT_ENGMT_CD,SECUR_SRVC_CD,COUNT(CLNT_VULN_INSTC_KEY) ''Total'',
                     COUNT(CASE WHEN VULN_SEV_CD=''C'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Critical'',
                     COUNT(CASE WHEN VULN_SEV_CD=''H'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''High'',
                     COUNT(CASE WHEN VULN_SEV_CD=''M'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Medium'',
                     COUNT(CASE WHEN VULN_SEV_CD=''L'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Low'',
                     COUNT(CASE WHEN VULN_SEV_CD=''C'' AND VULN_INSTC_STS_CD=''O'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END) CriticalOpenCount,
                     COUNT(CASE WHEN VULN_SEV_CD=''H'' AND VULN_INSTC_STS_CD=''O'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END) HighOpenCount,
                     COUNT(CASE WHEN VULN_SEV_CD=''M'' AND VULN_INSTC_STS_CD=''O'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END) MediumOpenCount,
                     COUNT(CASE WHEN VULN_SEV_CD=''L'' AND VULN_INSTC_STS_CD=''O'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END) LowOpenCount,
                     dbo.fnGetServiceNameBycode(A.SECUR_SRVC_CD) ServiceName
	   FROM			 '+ @schema+'.CLNT_VULN_INSTC             A
       WHERE		 CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD + '''
       AND           VULN_INSTC_STS_CD  NOT IN(''D'',''FP'')
       AND           VULN_SEV_CD         NOT IN(''I'')
       AND           SECUR_SRVC_CD                    =      CASE WHEN '''+ @SECUR_SRVC_CD +''' = '''' THEN SECUR_SRVC_CD ELSE '''+ @SECUR_SRVC_CD +''' END
       AND           A.ROW_STS_KEY                    =      1
       GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD '
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



