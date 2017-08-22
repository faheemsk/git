﻿
CREATE PROCEDURE [dbo].[Report_Severity]
(
	
	@CLNT_ENGMT_CD	VARCHAR(100),
	@SECUR_SRVC_CD	VARCHAR(100),	
	@Flag			VARCHAR(2),-- AP - Application,SS-SSID,AL- ALL
	@APPNETCD		VARCHAR(500),
    @schema			 VARCHAR(50)	
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
 DECLARE @Query VARCHAR(max)
 CREATE TABLE #TempTable(
 CRITICAL INTEGER,
 HIGH	  INTEGER,
 MEDIUM	  INTEGER,
 LOW	  INTEGER
 )

 IF @Flag = 'AL'
 BEGIN	 
 SET		  @Query ='	 
 INSERT INTO #TempTable
 SELECT   COUNT(CASE WHEN VULN_SEV_CD=''C'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Critical'',
		  COUNT(CASE WHEN VULN_SEV_CD=''H'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''High'',
		  COUNT(CASE WHEN VULN_SEV_CD=''M'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Medium'',
		  COUNT(CASE WHEN VULN_SEV_CD=''L'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Low''
 FROM	  '+ @schema+'.CLNT_VULN_INSTC A
 JOIN	  SECUR_SRVC	B
 ON		  A.SECUR_SRVC_CD = B.SECUR_SRVC_CD
 WHERE    CLNT_ENGMT_CD = '''+@CLNT_ENGMT_CD + '''
 AND	  A.SECUR_SRVC_CD = CASE WHEN '''+ @SECUR_SRVC_CD + ''' = '''' THEN A.SECUR_SRVC_CD ELSE '''+ @SECUR_SRVC_CD + ''' END
 AND	  VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
 AND	  VULN_SEV_CD		NOT IN(''I'')
 AND	  A.ROW_STS_KEY				=  1
 GROUP BY CLNT_ENGMT_CD,A.SECUR_SRVC_CD,B.SECUR_SRVC_NM '
 EXECUTE (@Query)

 SELECT Header,SeverityCount FROM(
 SELECT 'Critical'Header,CRITICAL SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'High'Header,HIGH SeverityCount
 FROM	#TempTable 
 UNION
  SELECT 'Medium'Header,MEDIUM SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'Low'Header,LOW SeverityCount
 FROM	#TempTable )A
 ORDER BY CASE Header  WHEN 'Critical' THEN 1 WHEN 'High' THEN 2 WHEN 'Medium' THEN 3 WHEN 'Low' THEN 4 END
 END

  IF @Flag = 'AP'
 BEGIN
 SET		  @Query ='	 	 
 INSERT INTO #TempTable
 SELECT   COUNT(CASE WHEN VULN_SEV_CD=''C'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Critical'',
		  COUNT(CASE WHEN VULN_SEV_CD=''H'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''High'',
		  COUNT(CASE WHEN VULN_SEV_CD=''M'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Medium'',
		  COUNT(CASE WHEN VULN_SEV_CD=''L'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Low''
 FROM	  '+ @schema+'.CLNT_VULN_INSTC A
 JOIN	  SECUR_SRVC	B
 ON		  A.SECUR_SRVC_CD = B.SECUR_SRVC_CD
 JOIN	  dbo.FnSplit('+@APPNETCD+','','')  C
 ON		  A.SFTW_NM		= C.items
 WHERE    CLNT_ENGMT_CD = '''+@CLNT_ENGMT_CD + '''
 AND	  A.SECUR_SRVC_CD = CASE WHEN '''+ @SECUR_SRVC_CD + ''' = '''' THEN A.SECUR_SRVC_CD ELSE '''+ @SECUR_SRVC_CD + ''' END
 AND	  VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
 AND	  VULN_SEV_CD		NOT IN(''I'')
 AND	  A.ROW_STS_KEY				=  1
 GROUP BY CLNT_ENGMT_CD,A.SECUR_SRVC_CD,B.SECUR_SRVC_NM '
 EXECUTE (@Query)

 SELECT Header,SeverityCount FROM(
 SELECT 'Critical'Header,CRITICAL SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'High'Header,HIGH SeverityCount
 FROM	#TempTable 
 UNION
  SELECT 'Medium'Header,MEDIUM SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'Low'Header,LOW SeverityCount
 FROM	#TempTable )A
 ORDER BY CASE Header  WHEN 'Critical' THEN 1 WHEN 'High' THEN 2 WHEN 'Medium' THEN 3 WHEN 'Low' THEN 4 END
 END

  IF @Flag = 'SS'
 BEGIN	 
 SET		  @Query ='	 
 INSERT INTO #TempTable
 SELECT   COUNT(CASE WHEN VULN_SEV_CD=''C'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Critical'',
		  COUNT(CASE WHEN VULN_SEV_CD=''H'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''High'',
		  COUNT(CASE WHEN VULN_SEV_CD=''M'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Medium'',
		  COUNT(CASE WHEN VULN_SEV_CD=''L'' THEN CLNT_VULN_INSTC_KEY ELSE NULL END)''Low''
 FROM	  '+ @schema+'.CLNT_VULN_INSTC A
 JOIN	  SECUR_SRVC	B
 ON		  A.SECUR_SRVC_CD = B.SECUR_SRVC_CD
 JOIN	  dbo.FnSplit('''+@APPNETCD+''','''','''')  C
 ON		  A.NETBIOS_NM				= C.items
 WHERE    CLNT_ENGMT_CD = '''+@CLNT_ENGMT_CD + '''
 AND	  A.SECUR_SRVC_CD = CASE WHEN '''+ @SECUR_SRVC_CD + ''' = '''' THEN A.SECUR_SRVC_CD ELSE '''+ @SECUR_SRVC_CD + ''' END
 AND	  VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
 AND	  VULN_SEV_CD		NOT IN(''I'')
 AND	  A.ROW_STS_KEY				=  1
 GROUP BY CLNT_ENGMT_CD,A.SECUR_SRVC_CD,B.SECUR_SRVC_NM '
 EXECUTE (@Query)

 SELECT Header,SeverityCount FROM(
 SELECT 'Critical'Header,CRITICAL SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'High'Header,HIGH SeverityCount
 FROM	#TempTable 
 UNION
  SELECT 'Medium'Header,MEDIUM SeverityCount
 FROM	#TempTable 
 UNION
 SELECT 'Low'Header,LOW SeverityCount
 FROM	#TempTable )A
 ORDER BY CASE Header  WHEN 'Critical' THEN 1 WHEN 'High' THEN 2 WHEN 'Medium' THEN 3 WHEN 'Low' THEN 4 END
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


