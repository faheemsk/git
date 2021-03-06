
GO
ALTER PROCEDURE [dbo].[CreateSchemaView]
(
 @SchemaName VARCHAR(50),
 @ORG_KEY	 INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

DECLARE @Query3 VARCHAR(max)
  
  SET @Query3= '
CREATE VIEW '+ @SchemaName+ '.Findings AS
		SELECT  DISTINCT A.CLNT_VULN_INSTC_KEY,A.CLNT_ENGMT_CD,A.VULN_NM,A.VULN_SRC_KEY,B.LKP_ENTY_NM VULN_SRC,A.RMDTN_CST_EFFRT_CD,F.RMDTN_CST_EFFRT_NM,
					A.DOM_NM,A.VULN_SEV_CD,A.IPADR,D.VULN_INSTC_STS_CD,D.VULN_INSTC_STS_NM,O.SECUR_SRVC_NM,A.VULN_OVALL_SCOR,VULN_VLD_DT,VULN_CLOS_DT,convert(varchar(max),CLOS_RSN_TXT) CLOS_RSN_TXT,
					A.SRC_VULN_SCAN_ID,CONVERT(VARCHAR,A.SRC_VULN_SCAN_STRT_DT,101)STRT_DT,A.CREAT_DT,G.LST_MOD_DT,G.CWE_ID,
					CONVERT(VARCHAR,A.SRC_VULN_SCAN_END_DT,101)END_DT,A.SFTW_NM,A.HST_NM,A.VULN_VCTR_TXT,
					ISNULL(A.APPL_URL,'''')APPL_URL,E.OS_NM,A.NETBIOS_NM,H.VULN_IMP_CD,-- K.SECUR_CTL_FAM_NM,K.SECUR_OBJ_NM,K.SECUR_CTL_NM,
					A.MAC_ADR_NM,A.PORT_NBR,CONVERT(VARCHAR(MAX),A.VULN_DESC)VULN_DESC,CONVERT(VARCHAR(MAX),A.VULN_TECH_COMMT_TXT)VULN_TECH_COMMT_TXT,
					CONVERT(VARCHAR(MAX),A.VULN_IMP_COMMT_TXT)VULN_IMP_COMMT_TXT,CONVERT(VARCHAR(MAX),A.RECOM_COMMT_TXT)RECOM_COMMT_TXT,
					CONVERT(VARCHAR(MAX),A.ROOT_CAUS_COMMT_TXT)ROOT_CAUS_COMMT_TXT,G.CVE_ID,G.CVE_DESC,A.VULN_BAS_SCOR,A.VULN_TMPRL_SCOR,A.VULN_ENV_SCOR,C.VULN_SEV_NM,H.VULN_IMP_NM,
					A.RISK_PRBL_CD,I.RISK_PRBL_NM,A.VULN_CATGY_CD,L.VULN_CATGY_NM,A.OWASP_TOP_10_KEY,
					M.OWASP_NM,A.SECUR_SRVC_CD,M.OWASP_CD,A.ROW_STS_KEY,A.ORG_KEY,VULN_IMP_SUB_SCOR,VULN_EXPLT_SUB_SCOR,VULN_CREAT_DT,SRC_ADVS_TXT,NTWK_NM,PRTCL_NM,
					SRC_VULN_SCAN_STRT_DT,SRC_VULN_SCAN_END_DT,B.LKP_ENTY_NM [Source Name],A.CREAT_USER_ID,E.OS_KEY,G.UPDT_DT,A.SRC_VULN_SEV_NM,A.SRC_BAS_SCOR,A.SRC_TMPRL_SCOR,A.SRC_VCTR_TXT,A.SCAN_TL_KEY,N.LKP_ENTY_NM SCAN_TL_NM,
					HITRUST = ISNULL(STUFF((
          SELECT DISTINCT '','' + D.SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL D
		  JOIN	 SECUR_CTL			 E
		  ON	 D.SECUR_CTL_CD	   = E.SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = D.CLNT_VULN_INSTC_KEY
		  AND	 D.REG_CMPLN_CD	   = ''HITRUST''
		  AND	 D.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  FedRAMP = ISNULL(STUFF((
          SELECT DISTINCT'','' + L.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 E
		  JOIN	 SECUR_CTL_MAP			 L
		  ON	 E.SECUR_CTL_CD		   = L.PRI_SECUR_CTL_CD
		  WHERE  A.CLNT_VULN_INSTC_KEY = E.CLNT_VULN_INSTC_KEY
		  AND	 L.SEC_REG_CMPLN_CD	   = ''FedRAMP''
		  AND	 E.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  NIST  = ISNULL(STUFF((
          SELECT DISTINCT'','' + K.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 F
		  JOIN	 SECUR_CTL_MAP			 K
		  ON	 F.SECUR_CTL_CD		   = K.PRI_SECUR_CTL_CD
		  WHERE  A.CLNT_VULN_INSTC_KEY = F.CLNT_VULN_INSTC_KEY
		  AND	 K.SEC_REG_CMPLN_CD	   = ''NIST SP 800-53''
		  AND	 F.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  CSACCM  = ISNULL(STUFF((
          SELECT DISTINCT'','' + J.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL G
		  JOIN	 SECUR_CTL_MAP			 J
		  ON	 G.SECUR_CTL_CD		   = J.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = G.CLNT_VULN_INSTC_KEY
		  AND	 J.SEC_REG_CMPLN_CD	   = ''CSA CCM''
		  AND	 G.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  FISMA  = ISNULL(STUFF((
          SELECT DISTINCT'','' + I.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 H
		  JOIN	 SECUR_CTL_MAP			 I
		  ON	 H.SECUR_CTL_CD		   = I.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = H.CLNT_VULN_INSTC_KEY
		  AND	 I.SEC_REG_CMPLN_CD	   = ''FISMA''
		  AND	 H.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  MARSE  = ISNULL(STUFF((
          SELECT DISTINCT'','' + N.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 M
		  JOIN	 SECUR_CTL_MAP			 N
		  ON	 M.SECUR_CTL_CD		   = N.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = M.CLNT_VULN_INSTC_KEY
		  AND	 N.SEC_REG_CMPLN_CD	   = ''MARS-E''
		  AND	 M.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  SOC2  = ISNULL(STUFF((
          SELECT DISTINCT'','' + P.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 O
		  JOIN	 SECUR_CTL_MAP			 P
		  ON	 O.SECUR_CTL_CD		   = P.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = O.CLNT_VULN_INSTC_KEY
		  AND	 P.SEC_REG_CMPLN_CD	   = ''SOC 2''
		  AND	 O.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  PCIDSS  = ISNULL(STUFF((
          SELECT DISTINCT'','' + R.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 Q
		  JOIN	 SECUR_CTL_MAP			 R
		  ON	 Q.SECUR_CTL_CD		   = R.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = Q.CLNT_VULN_INSTC_KEY
		  AND	 R.SEC_REG_CMPLN_CD	   = ''PCI DSS''
		  AND	 Q.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  HIPAA  = ISNULL(STUFF((
          SELECT DISTINCT'','' + T.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 S
		  JOIN	 SECUR_CTL_MAP			 T
		  ON	 S.SECUR_CTL_CD		   = T.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = S.CLNT_VULN_INSTC_KEY
		  AND	 T.SEC_REG_CMPLN_CD	   = ''HIPAA''
		  AND	 S.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  ISO  = ISNULL(STUFF((
          SELECT DISTINCT'','' + V.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 U
		  JOIN	 SECUR_CTL_MAP			 V
		  ON	 U.SECUR_CTL_CD		   = V.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = U.CLNT_VULN_INSTC_KEY
		  AND	 V.SEC_REG_CMPLN_CD	   = ''ISO/IEC 27001''
		  AND	 U.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  IRS   = ISNULL(STUFF((
          SELECT DISTINCT'','' + X.SEC_SECUR_CTL_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	 W
		  JOIN	 SECUR_CTL_MAP			 X
		  ON	 W.SECUR_CTL_CD		   = X.PRI_SECUR_CTL_CD
          WHERE  A.CLNT_VULN_INSTC_KEY = W.CLNT_VULN_INSTC_KEY
		  AND	 X.SEC_REG_CMPLN_CD	   = ''IRS Pub 1075 ''
		  AND	 W.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),''''),
		  SECUR_OBJ_CD=ISNULL(STUFF((
          SELECT DISTINCT'','' + Z.SECUR_OBJ_CD
          FROM   '+ @SchemaName+ '.CLNT_VULN_SECUR_CTL	Y
		  JOIN	 SECUR_CTL   			 Z
		  ON	 Y.SECUR_CTL_CD		   = Z.SECUR_CTL_CD
          WHERE  Y.CLNT_VULN_INSTC_KEY = A.CLNT_VULN_INSTC_KEY
		  AND	 Y.ROW_STS_KEY		   = 1
          FOR XML PATH(''''), TYPE).value(''.'', ''NVARCHAR(MAX)''), 1, 1, ''''),'''')
			FROM	'+ @SchemaName+ '.CLNT_VULN_INSTC				A
			JOIN	MSTR_LKP					B
			ON		A.VULN_SRC_KEY			=	B.MSTR_LKP_KEY
			LEFT JOIN	MSTR_LKP					N
			ON		A.SCAN_TL_KEY			=	N.MSTR_LKP_KEY
			LEFT JOIN	VULN_SEV					C
			ON		C.VULN_SEV_CD			=   A.VULN_SEV_CD
			LEFT JOIN	VULN_INSTC_STS				D
			ON		D.VULN_INSTC_STS_CD		=   A.VULN_INSTC_STS_CD
			LEFT JOIN	RMDTN_CST_EFFRT				F
			ON		A.RMDTN_CST_EFFRT_CD	=	F.RMDTN_CST_EFFRT_CD
			LEFT JOIN	VULN_IMP					H
			ON		A.VULN_IMP_CD			=   H.VULN_IMP_CD
			LEFT JOIN	RISK_PRBL					I
			ON		A.RISK_PRBL_CD			=	I.RISK_PRBL_CD
			LEFT JOIN	CVE							G
			ON		A.CVE_ID				=	G.CVE_ID
			JOIN	SECUR_SRVC					O
			ON		A.SECUR_SRVC_CD			=	O.SECUR_SRVC_CD
			LEFT JOIN	OS							E
			ON		A.OS_KEY		=			E.OS_KEY
			LEFT JOIN	CLNT_VULN_SECUR_CTL			J
			ON		A.CLNT_VULN_INSTC_KEY	=	J.CLNT_VULN_INSTC_KEY
			LEFT JOIN	SECUR_CTL					K
			ON		J.REG_CMPLN_CD			=	K.REG_CMPLN_CD
			AND	    J.REG_CMPLN_VER			=	K.REG_CMPLN_VER
			AND	    J.SECUR_CTL_CD			=	K.SECUR_CTL_CDno
			LEFT JOIN   VULN_CATGY						L
			ON		L.VULN_CATGY_CD	=  A.VULN_CATGY_CD
			LEFT JOIN OWASP_TOP_10				M
			ON		A.OWASP_TOP_10_KEY		=   M.OWASP_TOP_10_KEY
			WHERE	A.ROW_STS_KEY			=   1
		--	AND		A.VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
		--	AND		A.VULN_SEV_CD			NOT IN(''I'')
			AND		A.ROW_STS_KEY			=   1'
 -- PRINT (@Query3)
 EXECUTE(@Query3)
 SELECT 1 Retval
 -- PRINT  (@query2)
-- SELECT * FROM   sys.schemas
-- DROP SCHEMA test 
-- DROP TABLE test.test1
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
END;



GO
ALTER PROCEDURE [dbo].[Analyst_ListVulnerabilityDetails]
(
	@CLNT_VULN_INSTC_KEY INTEGER,
	@schema				 VARCHAR(50) 
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)

		    SET	@Query = 'SELECT    CLNT_VULN_INSTC_KEY, CLNT_ENGMT_CD,VULN_NM,VULN_SRC_KEY,VULN_SRC,RMDTN_CST_EFFRT_CD,
						  RMDTN_CST_EFFRT_NM,DOM_NM,VULN_SEV_CD,IPADR,VULN_INSTC_STS_CD,VULN_INSTC_STS_NM,
						  SECUR_SRVC_NM,VULN_OVALL_SCOR,VULN_VLD_DT,VULN_CLOS_DT,CLOS_RSN_TXT,SRC_VULN_SCAN_ID,
						  STRT_DT,CREAT_DT,LST_MOD_DT,CWE_ID,END_DT,SFTW_NM,HST_NM,VULN_VCTR_TXT,
						  APPL_URL,OS_NM,NETBIOS_NM,VULN_IMP_CD MAC_ADR_NM,PORT_NBR,VULN_DESC,
						  VULN_TECH_COMMT_TXT,VULN_IMP_COMMT_TXT,RECOM_COMMT_TXT,ROOT_CAUS_COMMT_TXT,
						  CVE_ID,CVE_DESC,VULN_BAS_SCOR,VULN_TMPRL_SCOR,VULN_ENV_SCOR,VULN_SEV_NM,
						  VULN_IMP_NM,RISK_PRBL_CD,RISK_PRBL_NM,VULN_CATGY_CD,VULN_CATGY_NM,OWASP_TOP_10_KEY,OWASP_NM,
						  SECUR_SRVC_CD,OWASP_CD,ROW_STS_KEY,ORG_KEY,VULN_IMP_SUB_SCOR,VULN_EXPLT_SUB_SCOR,VULN_CREAT_DT,SRC_ADVS_TXT,
						  NTWK_NM,PRTCL_NM,SRC_VULN_SCAN_STRT_DT,SRC_VULN_SCAN_END_DT,[Source Name],
						  CREAT_USER_ID,OS_KEY,UPDT_DT,HITRUST,FedRAMP,NIST,CSACCM,FISMA,MARSE,SOC2,
						  PCIDSS,HIPAA,ISO,IRS,SECUR_OBJ_CD,SRC_VULN_SEV_NM,SRC_BAS_SCOR,SRC_TMPRL_SCOR,SRC_VCTR_TXT,SCAN_TL_KEY,SCAN_TL_NM
			FROM	  '+ @schema+ '.Findings								 
    		WHERE CLNT_VULN_INSTC_KEY = '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY)
		-- PRINT @query
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


GO
CREATE PROCEDURE [dbo].[UPDATE_LoginCount]

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


	UPDATE USER_PRFL 
      SET    LOGIN_ATMPT_CNT	 =	 0


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
END;


GO
ALTER PROCEDURE [dbo].[Analyst_ListVulnerability]
(
       
       @CLNT_ENGMT_CD             VARCHAR(30),
       @PageNo                    INTEGER,
       @RowspPage                 INTEGER,
       @CLNT_VULN_INSTC_KEY       INTEGER,
       @VULN_NM                   VARCHAR(255),
       @SECUR_SRVC_CD             VARCHAR(10),
       @VULN_SRC                  INTEGER,
       @IPADR                     VARCHAR(39),
       @VULN_SEV_CD               VARCHAR(3),
       @STSKEY                    VARCHAR(3),
       @SFTW_NM                   VARCHAR(150),
       @schema                    VARCHAR(50),
       @VULN_CATGY_CD             VARCHAR(10),
	   @SCAN_TL_KEY				  VARCHAR(10)
)
AS
BEGIN

DECLARE @Count INTEGER

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)
DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)

                BEGIN TRAN

                     SET   @Query = 'SELECT              COUNT(A.CLNT_ENGMT_CD) TotalCount
                     FROM            '+ @schema+'.CLNT_VULN_INSTC   A             
                     WHERE           A.ROW_STS_KEY              =   1
                     AND           A.CLNT_ENGMT_CD                   =   ''' + CONVERT(VARCHAR,@CLNT_ENGMT_CD) +'''
                     AND           CLNT_VULN_INSTC_KEY =                   CASE WHEN ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''= 0  THEN       CLNT_VULN_INSTC_KEY ELSE  ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''  END
                     AND           ISNULL(A.VULN_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@VULN_NM) +'''= ''''               THEN       ISNULL(A.VULN_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@VULN_NM) +'%' +''' END      
                     AND           ISNULL(A.SECUR_SRVC_CD,'''') =          CASE WHEN ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +''' = ''''    THEN       ISNULL(A.SECUR_SRVC_CD,'''') ELSE ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +'''  END   
                     AND           ISNULL(A.VULN_SRC_KEY,0)   =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SRC) +'''= 0                 THEN       ISNULL(A.VULN_SRC_KEY,0) ELSE ''' + CONVERT(VARCHAR,@VULN_SRC) +''' END     
                     AND           ISNULL(A.IPADR,'''') LIKE               CASE WHEN ''' + CONVERT(VARCHAR,@IPADR) +''' = ''''                THEN       ISNULL(A.IPADR,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@IPADR) +'%' +''' END
                     AND           ISNULL(A.VULN_SEV_CD,'''') =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +''' = ''''          THEN       ISNULL(A.VULN_SEV_CD,'''') ELSE ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +'''  END
                     AND           ISNULL(A.VULN_INSTC_STS_CD,'''') = CASE WHEN ''' + CONVERT(VARCHAR,@STSKEY) +'''= ''''                THEN       ISNULL(A.VULN_INSTC_STS_CD,'''') ELSE ''' + CONVERT(VARCHAR,@STSKEY) +'''  END
                     AND           ISNULL(A.SFTW_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@SFTW_NM) +'''= ''''               THEN       ISNULL(A.SFTW_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@SFTW_NM) +'%' +''' END
					 AND           ISNULL(A.VULN_CATGY_CD,'''') =          CASE WHEN ''' + CONVERT(VARCHAR,@VULN_CATGY_CD) +''' = ''''    THEN       ISNULL(A.VULN_CATGY_CD,'''') ELSE ''' + CONVERT(VARCHAR,@VULN_CATGY_CD) +'''  END   
					 AND           ISNULL(SCAN_TL_KEY,0) =                   CASE WHEN ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''= 0  THEN       ISNULL(SCAN_TL_KEY,0) ELSE  ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''  END

                     GROUP BY A.CLNT_ENGMT_CD'
                           
                                  -- print @Query
                                    EXECUTE (@Query)

                                  SET   @Query2 = 'SELECT              COUNT(A.CLNT_VULN_INSTC_KEY) OpenCount
                     FROM            '+ @schema+'.CLNT_VULN_INSTC   A             
                     WHERE           A.ROW_STS_KEY              =   1
                     AND           A.CLNT_ENGMT_CD                   =   ''' + CONVERT(VARCHAR,@CLNT_ENGMT_CD) +'''
                     AND           CLNT_VULN_INSTC_KEY =                   CASE WHEN ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''= 0  THEN       CLNT_VULN_INSTC_KEY ELSE  ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''  END
                     AND           ISNULL(A.VULN_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@VULN_NM) +'''= ''''               THEN       ISNULL(A.VULN_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@VULN_NM) +'%' +''' END      
                     AND           ISNULL(A.SECUR_SRVC_CD,'''') =          CASE WHEN ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +''' = ''''    THEN       ISNULL(A.SECUR_SRVC_CD,'''') ELSE ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +'''  END   
                     AND           ISNULL(A.VULN_SRC_KEY,0)   =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SRC) +'''= 0                 THEN       ISNULL(A.VULN_SRC_KEY,0) ELSE ''' + CONVERT(VARCHAR,@VULN_SRC) +''' END     
                     AND           ISNULL(A.IPADR,'''') LIKE               CASE WHEN ''' + CONVERT(VARCHAR,@IPADR) +''' = ''''                THEN       ISNULL(A.IPADR,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@IPADR) +'%' +''' END
                     AND           ISNULL(A.VULN_SEV_CD,'''') =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +''' = ''''          THEN       ISNULL(A.VULN_SEV_CD,'''') ELSE ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +'''  END
                     AND           ISNULL(A.VULN_INSTC_STS_CD,'''') = CASE WHEN ''' + CONVERT(VARCHAR,@STSKEY) +'''= ''''                THEN       ISNULL(A.VULN_INSTC_STS_CD,'''') ELSE ''' + CONVERT(VARCHAR,@STSKEY) +'''  END
                     AND           ISNULL(A.SFTW_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@SFTW_NM) +'''= ''''               THEN       ISNULL(A.SFTW_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@SFTW_NM) +'%' +''' END
                     AND           A.VULN_INSTC_STS_CD =''O''
					 AND           ISNULL(SCAN_TL_KEY,0) =                   CASE WHEN ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''= 0  THEN       ISNULL(SCAN_TL_KEY,0) ELSE  ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''  END
                     GROUP BY A.CLNT_ENGMT_CD'
                           
                                  -- print @Query
                                    EXECUTE (@Query2)

                     SET   @Query1 = 'SELECT             CLNT_VULN_INSTC_KEY,A.VULN_NM,A.VULN_SRC_KEY,B.LKP_ENTY_NM VULN_SRC,A.VULN_SEV_CD,G.VULN_SEV_NM,A.IPADR,
                                   C.VULN_INSTC_STS_CD,C.VULN_INSTC_STS_NM,D.SECUR_SRVC_NM,A.SECUR_SRVC_CD,F.LKP_ENTY_NM [Service Status],
                                   A.CREAT_DT,A.UPDT_DT,A.SFTW_NM,A.SCAN_TL_KEY,H.LKP_ENTY_NM SCAN_TL_NM
                     FROM          '+ @schema+'.CLNT_VULN_INSTC             A
                     JOIN          MSTR_LKP                                 B
                     ON            A.VULN_SRC_KEY                    =      B.MSTR_LKP_KEY
                     JOIN          VULN_INSTC_STS                           C
                     ON            C.VULN_INSTC_STS_CD        =   A.VULN_INSTC_STS_CD
                     JOIN          SECUR_SRVC                               D
                     ON            D.SECUR_SRVC_CD                   =   A.SECUR_SRVC_CD
                     JOIN          CLNT_SECUR_SRVC_ENGMT             E
                     ON            E.SECUR_SRVC_CD                   =      A.SECUR_SRVC_CD      
                     JOIN          MSTR_LKP                                 F
                     ON            E.SRVC_ENGMT_STS_KEY =   F.MSTR_LKP_KEY
                     AND           A.CLNT_ENGMT_CD                   =   E.CLNT_ENGMT_CD
                     LEFT JOIN     VULN_SEV                          G
                     ON            G.VULN_SEV_CD              =   A.VULN_SEV_CD   
					 LEFT JOIN	   MSTR_LKP                                 H
                     ON            A.SCAN_TL_KEY                    =      H.MSTR_LKP_KEY    
                     WHERE         A.ROW_STS_KEY              =   1
                     AND           A.CLNT_ENGMT_CD                   =   ''' + CONVERT(VARCHAR,@CLNT_ENGMT_CD) +'''
                     AND           CLNT_VULN_INSTC_KEY =                   CASE WHEN ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''= 0  THEN       CLNT_VULN_INSTC_KEY ELSE  ''' + CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'''  END
                     AND           ISNULL(A.VULN_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@VULN_NM) +'''= ''''               THEN       ISNULL(A.VULN_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@VULN_NM) +'%' +''' END      
                     AND           ISNULL(A.SECUR_SRVC_CD,'''') =          CASE WHEN ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +''' = ''''    THEN       ISNULL(A.SECUR_SRVC_CD,'''') ELSE ''' + CONVERT(VARCHAR,@SECUR_SRVC_CD) +'''  END   
                     AND           ISNULL(A.VULN_SRC_KEY,0)   =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SRC) +'''= 0                 THEN       ISNULL(A.VULN_SRC_KEY,0) ELSE ''' + CONVERT(VARCHAR,@VULN_SRC) +''' END     
                     AND           ISNULL(A.IPADR,'''') LIKE               CASE WHEN ''' + CONVERT(VARCHAR,@IPADR) +''' = ''''                THEN       ISNULL(A.IPADR,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@IPADR) +'%' +''' END
                     AND           ISNULL(A.VULN_SEV_CD,'''') =            CASE WHEN ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +''' = ''''          THEN       ISNULL(A.VULN_SEV_CD,'''') ELSE ''' + CONVERT(VARCHAR,@VULN_SEV_CD) +'''  END
                     AND           ISNULL(A.VULN_INSTC_STS_CD,'''') = CASE WHEN ''' + CONVERT(VARCHAR,@STSKEY) +'''= ''''                THEN       ISNULL(A.VULN_INSTC_STS_CD,'''') ELSE ''' + CONVERT(VARCHAR,@STSKEY) +'''  END
                     AND           ISNULL(A.SFTW_NM,'''') LIKE             CASE WHEN ''' + CONVERT(VARCHAR,@SFTW_NM) +'''= ''''               THEN       ISNULL(A.SFTW_NM,'''') ELSE ''' + '%' + CONVERT(VARCHAR,@SFTW_NM) +'%' +''' END
                     AND           ISNULL(A.VULN_CATGY_CD,'''') =          CASE WHEN ''' + CONVERT(VARCHAR,@VULN_CATGY_CD) +''' = ''''    THEN       ISNULL(A.VULN_CATGY_CD,'''') ELSE ''' + CONVERT(VARCHAR,@VULN_CATGY_CD) +'''  END   
					 AND           ISNULL(SCAN_TL_KEY,0) =                   CASE WHEN ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''= 0  THEN       ISNULL(SCAN_TL_KEY,0) ELSE  ''' + CONVERT(VARCHAR,@SCAN_TL_KEY) +'''  END
                     ORDER BY	   CLNT_VULN_INSTC_KEY ASC
                     OFFSET ((' +CONVERT(VARCHAR,@PageNo) +' - 1) * ' + CONVERT(VARCHAR,@RowspPage)+') ROWS
                     FETCH NEXT '+CONVERT(VARCHAR,@RowspPage)+' ROWS ONLY;'
               EXECUTE (@Query1)       
              
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
COMMIT TRANSACTION
END