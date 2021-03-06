-- EXEC [RMDTN_GetRiskRegstWorklist] 17,'APO3','RO','','',''
GO
/****** Object:  StoredProcedure [dbo].[RMDTN_GetRiskRegstWorklist]    Script Date: 6/5/2017 3:21:44 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[RMDTN_GetRiskRegstWorklist]
(
	@Userid					INTEGER,
	@schema					VARCHAR(50),
	@UserFlag				VARCHAR(2),
	@ORG_NM					VARCHAR(100),
	@CLNT_ENGMT_CD			VARCHAR(50),
	@CLNT_ENGMT_NM			VARCHAR(100)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON	
	DECLARE @Query VARCHAR(max)

	IF	@UserFlag	= 'RO' --Registry Owner
	BEGIN
	SET		@Query = 'SELECT  C.CLNT_ENGMT_CD,C.CLNT_ORG_KEY,D.ORG_NM,C.CLNT_ENGMT_NM
			
	FROM	'+ @schema+'.CLNT_RISK_RGST				A
	JOIN	'+ @schema+'.CLNT_RMDTN_PLN				B
	ON		A.CLNT_RMDTN_PLN_KEY			=		B.CLNT_RMDTN_PLN_KEY
	JOIN	CLNT_ENGMT								C
	ON		B.CLNT_ENGMT_CD					=		C.CLNT_ENGMT_CD
    JOIN    ORG										D
    ON      D.ORG_KEY						=      C.CLNT_ORG_KEY
	WHERE	A.ACPT_USER_ID					=		'+CONVERT(VARCHAR,@Userid)+'
	AND     ISNULL(D.ORG_NM,'''')		 LIKE CASE WHEN '''+@ORG_NM+''' = '''' THEN  ISNULL(D.ORG_NM,'''') ELSE ''%'' + '''+@ORG_NM+''' + ''%'' END
	AND     ISNULL(C.CLNT_ENGMT_CD,'''') LIKE CASE WHEN '''+@CLNT_ENGMT_CD+''' = '''' THEN   C.CLNT_ENGMT_CD ELSE ''%'' + '''+@CLNT_ENGMT_CD+''' +''%'' END 
	AND     ISNULL(C.CLNT_ENGMT_NM,'''') LIKE CASE WHEN '''+@CLNT_ENGMT_NM+''' = '''' THEN   C.CLNT_ENGMT_NM ELSE ''%'' + '''+@CLNT_ENGMT_NM+''' + ''%''   END   
	ORDER BY CASE WHEN B.UPDT_DT ='''' THEN B.CREAT_DT ELSE B.UPDT_DT END DESC
 '
--	PRINT    @Query
	EXECUTE (@Query)
	END

	IF	@UserFlag	= 'RA' --Remediation Cordinator or Remediation Analyst
	BEGIN
	SET		@Query = 'SELECT  C.CLNT_ENGMT_CD,C.CLNT_ORG_KEY,D.ORG_NM,C.CLNT_ENGMT_NM
			
	FROM	CLNT_ENGMT								C
    JOIN    ORG										D
    ON      D.ORG_KEY						=		C.CLNT_ORG_KEY
	JOIN    CLNT_ENGMT_USER_ASGN					E
	ON	    C.CLNT_ENGMT_CD					=		E.CLNT_ENGMT_CD
	WHERE   ISNULL(D.ORG_NM,'''')		 LIKE CASE WHEN '''+@ORG_NM+''' = '''' THEN  ISNULL(D.ORG_NM,'''') ELSE ''%'' + '''+@ORG_NM+''' + ''%'' END
	AND     ISNULL(C.CLNT_ENGMT_CD,'''') LIKE CASE WHEN '''+@CLNT_ENGMT_CD+''' = '''' THEN   C.CLNT_ENGMT_CD ELSE ''%'' + '''+@CLNT_ENGMT_CD+''' +''%'' END 
	AND     ISNULL(C.CLNT_ENGMT_NM,'''') LIKE CASE WHEN '''+@CLNT_ENGMT_NM+''' = '''' THEN   C.CLNT_ENGMT_NM ELSE ''%'' + '''+@CLNT_ENGMT_NM+''' + ''%''   END   
	AND 	E.USER_ID					=		'+CONVERT(VARCHAR,@Userid)+'
	ORDER BY CASE WHEN C.UPDT_DT ='''' THEN C.CREAT_DT ELSE C.UPDT_DT END DESC
 '
	--PRINT    @Query
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






GO
/****** Object:  StoredProcedure [dbo].[Report_GetRemediationPMWorkList]    Script Date: 6/5/2017 3:42:11 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[Report_GetRemediationPMWorkList]
(
       
       @ORG_KEY                   INTEGER,
       @OrgName                   VARCHAR(150),
       @CLNT_ENGMT_CD             VARCHAR(30),  
       @CLNT_ENGMT_NM             VARCHAR(150),
	   @USER_ID					  INTEGER  

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

              SELECT A.CLNT_ENGMT_CD,A.CLNT_ORG_KEY,B.ORG_NM,A.SECUR_PKG_CD,A.CLNT_ENGMT_NM,A.UPDT_DT,A.CREAT_DT 
              FROM   CLNT_ENGMT           A
              JOIN   ORG                  B
              ON     B.ORG_KEY     =      A.CLNT_ORG_KEY
			  JOIN	 CLNT_ENGMT_USER_ASGN C
			  ON	 A.CLNT_ENGMT_CD	= C.CLNT_ENGMT_CD
			  JOIN	 APPL_ROLE			  D
			  ON	 D.APPL_ROLE_KEY	= C.APPL_ROLE_KEY
              WHERE  A.ROW_STS_KEY =      1
			  AND	 C.USER_ID			= @USER_ID
              AND    A.CLNT_ORG_KEY = CASE WHEN @ORG_KEY= 0 THEN A.CLNT_ORG_KEY ELSE @ORG_KEY   END
              AND    ISNULL(B.ORG_NM,'') LIKE CASE WHEN @OrgName = '' THEN  ISNULL(B.ORG_NM,'') ELSE '%' + @OrgName + '%' END
			  AND    ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN   A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END 
			  AND    ISNULL(A.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN   A.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%'   END 
			 -- AND	 D.APPL_ROLE_NM IN('Remediation Coordinator','Remediation Analyst')  
			  ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'') ='' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC

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
ALTER PROCEDURE [dbo].[RMDTN_GetRmdPlanitmByInstcKey]
(
	@CLNT_VULN_INSTC_KEY		INTEGER,
	@schema					VARCHAR(50)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
	DECLARE @Query VARCHAR(max)

	SET		@Query = 'SELECT  A.CLNT_RMDTN_PLN_KEY,A.VULN_RMDTN_STS_CD,A.CLOS_DT,A.CLOS_RSN_TXT,C.ADJ_RMDTN_PLN_SEV_CD,D.VULN_SEV_NM
			
	FROM	'+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM	A
	JOIN	'+ @schema+'.CLNT_VULN_INSTC			B
	ON		A.CLNT_VULN_INSTC_KEY		=			B.CLNT_VULN_INSTC_KEY
	JOIN	'+ @schema+'.CLNT_RMDTN_PLN  		C
	ON		C.CLNT_RMDTN_PLN_KEY		=			A.CLNT_RMDTN_PLN_KEY
	JOIN	VULN_SEV								D
	ON		C.ADJ_RMDTN_PLN_SEV_CD		=			D.VULN_SEV_CD
	WHERE	B.CLNT_VULN_INSTC_KEY			=		'+CONVERT(VARCHAR(50),@CLNT_VULN_INSTC_KEY)+'
 '
	-- PRINT    @Query
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

