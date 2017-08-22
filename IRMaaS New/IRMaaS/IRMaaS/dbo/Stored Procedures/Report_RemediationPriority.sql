CREATE PROCEDURE [dbo].[Report_RemediationPriority]
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
DECLARE @Query1 VARCHAR(max)
DECLARE @LiCOUNT VARCHAR(20)
DECLARE @CountResults TABLE (CountReturned INT)


       IF @Flag = 'AL'
       BEGIN     
	   SET		@Query ='
	   SELECT   COUNT(CLNT_VULN_INSTC_KEY) 
	   FROM     '+ @schema+'.CLNT_VULN_INSTC
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		ROW_STS_KEY				  = 1'
	   INSERT @CountResults
	   EXECUTE (@Query)
	   SET @LiCOUNT = (SELECT CountReturned FROM @CountResults)

	   SET		@Query1 ='
       SELECT   TOP 20 CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM,COUNT(CLNT_VULN_INSTC_KEY)VULCOUNT,'+@LiCOUNT+' FindingsCount
	   FROM     '+ @schema+'.CLNT_VULN_INSTC
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		ROW_STS_KEY				  = 1
	   GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM
       ORDER BY COUNT(VULN_NM) DESC'
	   EXECUTE (@Query1)
       END

       IF @Flag = 'AP'
       BEGIN     

	   SET		@Query ='
	   SELECT   COUNT(CLNT_VULN_INSTC_KEY)
       FROM     '+ @schema+'.CLNT_VULN_INSTC             A
       JOIN     dbo.FnSplit('''+@APPNETCD+''','','')  B
       ON       A.SFTW_NM                 = B.items
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD + '''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD + '''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		A.ROW_STS_KEY				  = 1'
	   INSERT @CountResults
	   EXECUTE (@Query)
	   SET @LiCOUNT = (SELECT CountReturned FROM @CountResults)


	   SET      @Query1='
       SELECT   TOP 20 CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM,COUNT(CLNT_VULN_INSTC_KEY)VULCOUNT,'+@LiCOUNT+' FindingsCount
       FROM     '+ @schema+'.CLNT_VULN_INSTC                    A
       JOIN     dbo.FnSplit('''+@APPNETCD+''','','')  B
       ON       A.SFTW_NM                 = B.items
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		A.ROW_STS_KEY				  = 1
       GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM
       ORDER BY COUNT(VULN_NM) DESC'
	 --  PRINT (@Query1)
	   EXECUTE (@Query1)
       END

       IF @Flag = 'SS'
       BEGIN     
	   SET		@Query ='
	   SELECT   COUNT(CLNT_VULN_INSTC_KEY) 
       FROM     '+ @schema+'.CLNT_VULN_INSTC             A
       JOIN     dbo.FnSplit('''+@APPNETCD+''','','')  B
       ON       A.NETBIOS_NM              = B.items
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		A.ROW_STS_KEY				  = 1'
	   INSERT @CountResults
	   EXECUTE (@Query)
	   SET @LiCOUNT = (SELECT CountReturned FROM @CountResults)

	   SET		@Query1 ='
       SELECT   TOP 20 CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM,COUNT(CLNT_VULN_INSTC_KEY)VULCOUNT,'+@LiCOUNT+' FindingsCount
       FROM     '+ @schema+'.CLNT_VULN_INSTC             A
       JOIN     dbo.FnSplit('''+@APPNETCD+''','','')  B
       ON       A.NETBIOS_NM              = B.items
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		A.ROW_STS_KEY				  = 1
       GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM
       ORDER BY COUNT(VULN_NM) DESC'
	   EXECUTE (@Query1)
       END

       IF @Flag = 'NW'
       BEGIN     

	   SET		@Query ='
	   SELECT   SUM(VULCOUNT) 
	   FROM(
       SELECT   CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM,COUNT(DISTINCT IPADR)VULCOUNT
       FROM     '+ @schema+'.CLNT_VULN_INSTC
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		ROW_STS_KEY				  = 1
	   AND		ISNULL(IPADR,'''')<>''''
       GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM)Z'
	--   Print (@Query)
	   INSERT @CountResults
	   EXECUTE (@Query)
	   SET @LiCOUNT = ISNULL((SELECT CountReturned FROM @CountResults),0)

	   SET		@Query1 ='
       SELECT   TOP 20 CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM,COUNT(DISTINCT IPADR)VULCOUNT,'+@LiCOUNT+' FindingsCount
       FROM     '+ @schema+'.CLNT_VULN_INSTC
       WHERE    CLNT_ENGMT_CD             = '''+@CLNT_ENGMT_CD +'''
       AND      SECUR_SRVC_CD             = '''+ @SECUR_SRVC_CD +'''
       AND      VULN_INSTC_STS_CD         NOT IN(''D'',''FP'')
       AND      VULN_SEV_CD               IN(''C'',''H'')
	   AND		ROW_STS_KEY				  = 1
	   AND		ISNULL(IPADR,'''')<>''''
       GROUP BY CLNT_ENGMT_CD,SECUR_SRVC_CD,VULN_NM
       ORDER BY COUNT(VULN_NM) DESC'
	   EXECUTE (@Query1)
	-- PRINT (@Query1)
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
