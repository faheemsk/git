
GO
/****** Object:  StoredProcedure [dbo].[RMDTN_GetPlanListByEngmt]    Script Date: 4/10/2017 4:01:28 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



ALTER PROCEDURE [dbo].[RMDTN_GetPlanListByEngmt]
(
	@CLNT_ENGMT_CD			VARCHAR(50),
	@schema					VARCHAR(50),
	@Userid					INTEGER,
	@Flag					VARCHAR(3)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
	DECLARE @Query VARCHAR(max)

	IF @Flag	= 'AL'
	BEGIN

	SET		@Query = 'SELECT  A.CLNT_RMDTN_PLN_KEY,A.CLNT_ENGMT_CD,A.RMDTN_OWNR_USER_ID,CONCAT(C.FST_NM,'''',C.LST_NM)Owner,
					  A.RMDTN_PLN_STS_CD,B.RMDTN_PLN_STS_NM,
					  A.RMDTN_PLN_NM,A.RMDTN_PLN_DESC,A.RMDTN_PLN_CREAT_DT,A.RMDTN_PLN_NTF_DT,A.RMDTN_PLN_STRT_DT,
					  A.RMDTN_PLN_DUE_DT,A.RMDTN_PLN_CLOS_DT,A.ASSOC_VULN_TOT_CNT,A.ASSOC_VULN_CMPL_CNT,A.ORG_KEY,
					  DATEDIFF(DAY,A.RMDTN_PLN_NTF_DT,GETDATE()) DAYSOPEN,
					  CASE WHEN A.ASSOC_VULN_TOT_CNT <> 0 THEN CONVERT(INTEGER,(convert(numeric(18,2),A.ASSOC_VULN_CMPL_CNT)/convert(numeric(18,2),A.ASSOC_VULN_TOT_CNT))*100) ELSE 0 END Completion


	FROM	'+ @schema+'.CLNT_RMDTN_PLN		A
	JOIN	RMDTN_PLN_STS					B
	ON		A.RMDTN_PLN_STS_CD		=		B.RMDTN_PLN_STS_CD
	JOIN	USER_PRFL						C
	ON		A.RMDTN_OWNR_USER_ID	=		C.USER_ID
	WHERE	A.CLNT_ENGMT_CD			=		'''+ @CLNT_ENGMT_CD+'''
	AND		A.ROW_STS_KEY			=		1 
	ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'''')='''' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC'
 
 	--PRINT    @Query
	 EXECUTE (@Query)
	END

	IF @Flag	= 'RO'
	BEGIN

	SET		@Query = 'SELECT  A.CLNT_RMDTN_PLN_KEY,A.CLNT_ENGMT_CD,A.RMDTN_OWNR_USER_ID,CONCAT(C.FST_NM,'''',C.LST_NM)Owner,
					  A.RMDTN_PLN_STS_CD,B.RMDTN_PLN_STS_NM,
					  A.RMDTN_PLN_NM,A.RMDTN_PLN_DESC,A.RMDTN_PLN_CREAT_DT,A.RMDTN_PLN_NTF_DT,A.RMDTN_PLN_STRT_DT,
					  A.RMDTN_PLN_DUE_DT,A.RMDTN_PLN_CLOS_DT,A.ASSOC_VULN_TOT_CNT,A.ASSOC_VULN_CMPL_CNT,A.ORG_KEY,
					  DATEDIFF(DAY,A.RMDTN_PLN_NTF_DT,GETDATE()) DAYSOPEN,
					  CASE WHEN A.ASSOC_VULN_TOT_CNT <> 0 THEN CONVERT(INTEGER,(convert(numeric(18,2),A.ASSOC_VULN_CMPL_CNT)/convert(numeric(18,2),A.ASSOC_VULN_TOT_CNT))*100) ELSE 0 END Completion


	FROM	'+ @schema+'.CLNT_RMDTN_PLN		A
	JOIN	RMDTN_PLN_STS					B
	ON		A.RMDTN_PLN_STS_CD		=		B.RMDTN_PLN_STS_CD
	JOIN	USER_PRFL						C
	ON		A.RMDTN_OWNR_USER_ID	=		C.USER_ID
	WHERE	A.CLNT_ENGMT_CD			=		'''+ @CLNT_ENGMT_CD+'''
	AND		A.ROW_STS_KEY			=		1
	AND		A.RMDTN_OWNR_USER_ID	=		'+ isnull('''' + convert(varchar(10),@Userid) + '''','null') + '
	ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'''')='''' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC'
 
 --	PRINT    @Query
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
/****** Object:  StoredProcedure [dbo].[RMDTN_GetRiskRegistrylist]    Script Date: 4/10/2017 4:04:20 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[RMDTN_GetRiskRegistrylist]
(
	@CLNT_ENGMT_CD			VARCHAR(30),
	@Userid					INTEGER,
	@schema					VARCHAR(50),
	@UserFlag				VARCHAR(2)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON	
	DECLARE @Query VARCHAR(max)

	IF	@UserFlag	= 'RO' --Registry Owner
	BEGIN
	SET		@Query = 'SELECT  A.CLNT_RISK_RGST_KEY,A.ADJ_SEV_CD,A.CLNT_RMDTN_PLN_KEY,B.RMDTN_PLN_NM,A.RISK_RGST_ITM_TOT_CNT,
					  A.RISK_RSPN_CD,A.ACPT_DT,A.ACPT_USER_ID,C.VULN_SEV_NM,D.RISK_RSPN_NM,F.FST_NM	,F.LST_NM,A.ACPT_USER_SGN_TXT																																																																																																																																																													
			
	FROM	'+ @schema+'.CLNT_RISK_RGST				A
	JOIN	'+ @schema+'.CLNT_RMDTN_PLN				B
	ON		A.CLNT_RMDTN_PLN_KEY			=		B.CLNT_RMDTN_PLN_KEY
	JOIN	VULN_SEV								C
	ON		C.VULN_SEV_CD					=		A.ADJ_SEV_CD
    LEFT JOIN    RISK_RSPN								D
    ON      A.RISK_RSPN_CD						=   D.RISK_RSPN_CD
--	JOIN	CLNT_RISK_RGST_ITM						E
--	ON		E.CLNT_RISK_RGST_KEY			=		A.CLNT_RISK_RGST_KEY
	JOIN	USER_PRFL								F
	ON		F.USER_ID						=		A.ENT_USER_ID
	WHERE	B.CLNT_ENGMT_CD					=		'''+CONVERT(VARCHAR,@CLNT_ENGMT_CD)+'''
	AND		A.ENT_USER_ID					=		'+CONVERT(VARCHAR,@Userid)+'
	ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'''')='''' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
 '
--	PRINT    @Query
	EXECUTE (@Query)
	END

	IF	@UserFlag	= 'RA' --Remediation Cordinator or Remediation Analyst Owner
	BEGIN
	SET		@Query = 'SELECT  A.CLNT_RISK_RGST_KEY,A.ADJ_SEV_CD,A.CLNT_RMDTN_PLN_KEY,B.RMDTN_PLN_NM,A.RISK_RGST_ITM_TOT_CNT,
					  A.RISK_RSPN_CD,A.ACPT_DT,A.ACPT_USER_ID,C.VULN_SEV_NM,D.RISK_RSPN_NM,F.FST_NM	,F.LST_NM,A.ACPT_USER_SGN_TXT																																																																																																																																																													
			
	FROM	'+ @schema+'.CLNT_RISK_RGST				A
	JOIN	'+ @schema+'.CLNT_RMDTN_PLN				B
	ON		A.CLNT_RMDTN_PLN_KEY			=		B.CLNT_RMDTN_PLN_KEY
	JOIN	VULN_SEV								C
	ON		C.VULN_SEV_CD					=		A.ADJ_SEV_CD
    LEFT JOIN    RISK_RSPN								D
    ON      A.RISK_RSPN_CD						=   D.RISK_RSPN_CD
--	JOIN	CLNT_RISK_RGST_ITM						E
--	ON		E.CLNT_RISK_RGST_KEY			=		A.CLNT_RISK_RGST_KEY
	JOIN	USER_PRFL								F
	ON		F.USER_ID						=		'+CONVERT(VARCHAR,@Userid)+'
	WHERE	B.CLNT_ENGMT_CD					=		'''+CONVERT(VARCHAR,@CLNT_ENGMT_CD)+'''
--	AND		A.ENT_USER_ID					=		'+CONVERT(VARCHAR,@Userid)+'
	ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'''')=''''  THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
 '
--	PRINT    @Query
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
/****** Object:  StoredProcedure [dbo].[RMDTN_GetCOMMTByPlanKey]    Script Date: 4/10/2017 4:06:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


ALTER PROCEDURE [dbo].[RMDTN_GetCOMMTByPlanKey]
(
	@CLNT_RMDTN_PLN_KEY		INTEGER,
	@schema					VARCHAR(50)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
	DECLARE @Query VARCHAR(max)

	SET		@Query = 'SELECT  B.CLNT_RMDTN_PLN_COMMT_KEY,A.CLNT_RMDTN_PLN_KEY,B.ENT_DT,B.COMMT_TXT,A.CREAT_DT,
	A.CREAT_USER_ID,C.FST_NM+'' ''+C.LST_NM Username


	FROM	'+ @schema+'.CLNT_RMDTN_PLN		A
	JOIN	'+ @schema+'.CLNT_RMDTN_PLN_COMMT			B
	ON		A.CLNT_RMDTN_PLN_KEY	=		B.CLNT_RMDTN_PLN_KEY
	JOIN	USER_PRFL						C
	ON		B.CREAT_USER_ID			=		C.USER_ID
	WHERE	A.CLNT_RMDTN_PLN_KEY	=		'+CONVERT(VARCHAR(50),@CLNT_RMDTN_PLN_KEY)+'
	ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'''')='''' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
 '
--	PRINT    @Query
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

