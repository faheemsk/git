CREATE PROCEDURE [dbo].[Report_RouteCauseAnalysis]
(

       @CLNT_ENGMT_CD       VARCHAR(100),
       @SECUR_SRVC_CD       VARCHAR(100),
       @Flag                VARCHAR(2),-- AP - Application,SS-SSID,AL- ALL
       @APPNETCD            VARCHAR(500),
	   @schema				VARCHAR(50)  
       
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)
DECLARE @LiCOUNT INTEGER

IF @Flag = 'AL'
BEGIN 
SET		  @Query ='	 
SELECT   B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD,
        COUNT(A.CLNT_VULN_INSTC_KEY) Percentage
FROM    '+ @schema+'.CLNT_VULN_INSTC                   A
JOIN    VULN_CATGY                   B
ON      A.VULN_CATGY_CD            = B.VULN_CATGY_CD
WHERE   CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD +'''
AND     SECUR_SRVC_CD                   = CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
AND     A.VULN_INSTC_STS_CD             NOT IN(''D'',''FP'')
AND     A.VULN_SEV_CD                   NOT IN(''I'')
AND     A.ROW_STS_KEY                    =  1
-- AND     B.ROOT_CAUS_ANLYS_CD            <> ''OTH''
-- AND   B.ROOT_CAUS_ANLYS_CD            LIKE ''OWSAP%''
GROUP BY B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD
ORDER BY Percentage DESC'
EXECUTE (@Query)
END

  IF @Flag = 'AP'
BEGIN 
 SET	@Query ='	
 SELECT B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD,
        COUNT(A.CLNT_VULN_INSTC_KEY) Percentage
FROM    '+ @schema+'.CLNT_VULN_INSTC                   A
JOIN    VULN_CATGY                   B
ON      A.VULN_CATGY_CD            = B.VULN_CATGY_CD
JOIN    dbo.FnSplit('''+@APPNETCD+''','','')		  C
ON      A.SFTW_NM                       = C.items
WHERE   CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD+'''
AND     SECUR_SRVC_CD                   = CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
AND     A.VULN_INSTC_STS_CD             NOT IN(''D'',''FP'')
AND     A.VULN_SEV_CD                   NOT IN(''I'')
AND     A.ROW_STS_KEY                   =  1
-- AND   B.ROOT_CAUS_ANLYS_CD            LIKE ''OWSAP%''
GROUP BY B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD
ORDER BY Percentage DESC '
EXECUTE (@Query)
END

  IF @Flag = 'SS'
BEGIN 
 SET	@Query ='
SELECT  B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD,
        COUNT(A.CLNT_VULN_INSTC_KEY) Percentage
FROM    '+ @schema+'.CLNT_VULN_INSTC                   A
JOIN    VULN_CATGY                   B
ON      A.VULN_CATGY_CD            = B.VULN_CATGY_CD
JOIN    dbo.FnSplit('''+@APPNETCD+''','','')		  C
ON      A.NETBIOS_NM                    = C.items
WHERE   CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD+'''
AND     SECUR_SRVC_CD                   = CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
AND     A.VULN_INSTC_STS_CD             NOT IN(''D'',''FP'')
AND     A.VULN_SEV_CD                   NOT IN(''I'')
AND     A.ROW_STS_KEY                   =  1
-- AND     B.ROOT_CAUS_ANLYS_CD            <> ''OTH''
-- AND   B.ROOT_CAUS_ANLYS_CD            LIKE ''OWSAP%''
GROUP BY B.VULN_CATGY_CD,B.VULN_CATGY_NM,A.CLNT_ENGMT_CD,A.SECUR_SRVC_CD
ORDER BY Percentage DESC '
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


