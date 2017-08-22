﻿
CREATE PROCEDURE [dbo].[LIST_UserClntSrvcAssn]
(
	@USERTYPEID 	INTEGER,
	@CLNT_ENGMT_CD  VARCHAR(30),
	@USER_ID		INTEGER,
	@USER_TYP_KEY	INTEGER,
	@USERNAME		VARCHAR(300),
	@USER_STRT_DT	VARCHAR(10),
	@USER_END_DT	VARCHAR(10)


		
)
AS
BEGIN

DECLARE @LISERVICE VARCHAR(100) = ''
BEGIN TRY
SET NOCOUNT ON   

IF @USERTYPEID = 16
BEGIN
SELECT		C.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.SECUR_SRVC_NM,B.SRVC_EST_STRT_DT ,B.SRVC_EST_END_DT,
			[dbo].[fnGetMasterLkpNameByID](Z.USER_TYP_KEY) UserType,Z.ORG_TYP_KEY,Z.ORG_NM,Z.ORG_KEY,
			ISNULL(Z.USER_CLNT_SRVC_ASGN_KEY,0)USER_CLNT_SRVC_ASGN_KEY,ISNULL(Z.FST_NM,'') USERNAME,
			ISNULL(Z.USER_ID,0) USER_ID,ISNULL(Z.ROW_STS_KEY,0) ROW_STS_KEY,
			Z.USER_STRT_DT,Z.USER_END_DT,Z.USER_TYP_KEY
FROM		SECUR_SRVC				  A
JOIN		CLNT_SECUR_SRVC_ENGMT	  B
ON			A.SECUR_SRVC_CD			= B.SECUR_SRVC_CD
JOIN		CLNT_ENGMT				  C
ON			B.CLNT_ENGMT_CD			= C.CLNT_ENGMT_CD
JOIN		CLNT_ENGMT_USER_ASGN	  F
ON			C.CLNT_ENGMT_CD			= F.CLNT_ENGMT_CD
LEFT JOIN	
(
SELECT      E.CLNT_ENGMT_CD,E.SECUR_SRVC_CD,G.USER_TYP_KEY,E.USER_STRT_DT,E.USER_END_DT,E.USER_CLNT_SRVC_ASGN_KEY,
			ISNULL(G.FST_NM+' '+G.LST_NM,'') FST_NM,D.ORG_NM,D.ORG_KEY,D.ORG_TYP_KEY,G.USER_ID,E.ROW_STS_KEY
FROM		USER_CLNT_SRVC_ASGN		  E
JOIN		USER_PRFL				  G
ON			E.USER_ID				= G.USER_ID
JOIN		ORG						  D
ON			G.ORG_KEY				= D.ORG_KEY
AND			CONVERT(VARCHAR(20),E.USER_STRT_DT,101)  = CASE WHEN @USER_STRT_DT = '' THEN CONVERT(VARCHAR(20),E.USER_STRT_DT,101) ELSE @USER_STRT_DT END
AND			CONVERT(VARCHAR(20),E.USER_END_DT,101)	 = CASE WHEN @USER_END_DT  = '' THEN CONVERT(VARCHAR(20),E.USER_END_DT,101)  ELSE @USER_END_DT END
AND         G.USER_TYP_KEY			= CASE WHEN @USER_TYP_KEY = 0 THEN G.USER_TYP_KEY ELSE @USER_TYP_KEY END
AND			ISNULL(G.FST_NM+' '+G.LST_NM,'')  LIKE CASE WHEN ISNULL(@USERNAME,'') = '' THEN ISNULL(G.FST_NM+' '+G.LST_NM,'') ELSE '%' + @USERNAME + '%' END)Z
ON			C.CLNT_ENGMT_CD = Z.CLNT_ENGMT_CD
AND			A.SECUR_SRVC_CD	= Z.SECUR_SRVC_CD
WHERE		C.CLNT_ENGMT_CD			= @CLNT_ENGMT_CD
AND			F.USER_ID				= @USER_ID

END

IF @USERTYPEID = 18
BEGIN

SELECT		@LISERVICE = SECUR_SRVC_LIST_CD 
FROM		dbo.CLNT_ENGMT_USER_ASGN  A
JOIN		dbo.USER_PRFL			  B 
ON			A.USER_ID				= B.USER_ID
WHERE		CLNT_ENGMT_CD			= @CLNT_ENGMT_CD 
AND			B.USER_TYP_KEY			= 18
AND			A.USER_ID				= @USER_ID

SELECT		C.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.SECUR_SRVC_NM,B.SRVC_EST_STRT_DT ,B.SRVC_EST_END_DT,
			[dbo].[fnGetMasterLkpNameByID](Z.USER_TYP_KEY) UserType,Z.ORG_TYP_KEY,Z.ORG_NM,Z.ORG_KEY,
			ISNULL(Z.USER_CLNT_SRVC_ASGN_KEY,0)USER_CLNT_SRVC_ASGN_KEY,ISNULL(Z.FST_NM,'') USERNAME,
			ISNULL(Z.USER_ID,0) USER_ID,ISNULL(Z.ROW_STS_KEY,0) ROW_STS_KEY,
			Z.USER_STRT_DT,Z.USER_END_DT,Z.USER_TYP_KEY
FROM		SECUR_SRVC				  A
JOIN		CLNT_SECUR_SRVC_ENGMT	  B
ON			A.SECUR_SRVC_CD			= B.SECUR_SRVC_CD
JOIN		CLNT_ENGMT				  C
ON			B.CLNT_ENGMT_CD			= C.CLNT_ENGMT_CD
JOIN		CLNT_ENGMT_USER_ASGN	  F
ON			C.CLNT_ENGMT_CD			= F.CLNT_ENGMT_CD
JOIN	    dbo.FnSplit(@LISERVICE,',')		  H
ON		    A.SECUR_SRVC_CD			= H.items
LEFT JOIN	
(
SELECT      E.CLNT_ENGMT_CD,E.SECUR_SRVC_CD,G.USER_TYP_KEY,E.USER_STRT_DT,E.USER_END_DT,E.USER_CLNT_SRVC_ASGN_KEY,
			ISNULL(G.FST_NM+' '+G.LST_NM,'') FST_NM,D.ORG_NM,D.ORG_KEY,D.ORG_TYP_KEY,G.USER_ID,E.ROW_STS_KEY
FROM		USER_CLNT_SRVC_ASGN		  E
JOIN		USER_PRFL				  G
ON			E.USER_ID				= G.USER_ID
AND			G.USER_TYP_KEY			= 18
JOIN		ORG						  D
ON			G.ORG_KEY				= D.ORG_KEY
AND			CONVERT(VARCHAR(20),E.USER_STRT_DT,101)  = CASE WHEN @USER_STRT_DT = '' THEN CONVERT(VARCHAR(20),E.USER_STRT_DT,101) ELSE @USER_STRT_DT END
AND			CONVERT(VARCHAR(20),E.USER_END_DT,101)	 = CASE WHEN @USER_END_DT  = '' THEN CONVERT(VARCHAR(20),E.USER_END_DT,101)  ELSE @USER_END_DT END
AND         G.USER_TYP_KEY			= CASE WHEN @USER_TYP_KEY = 0 THEN G.USER_TYP_KEY ELSE @USER_TYP_KEY END
AND			ISNULL(G.FST_NM+' '+G.LST_NM,'')  LIKE CASE WHEN ISNULL(@USERNAME,'') = '' THEN ISNULL(G.FST_NM+' '+G.LST_NM,'') ELSE '%' + @USERNAME + '%' END)Z
ON			C.CLNT_ENGMT_CD = Z.CLNT_ENGMT_CD
AND			A.SECUR_SRVC_CD	= Z.SECUR_SRVC_CD
WHERE		C.CLNT_ENGMT_CD			= @CLNT_ENGMT_CD
AND			F.USER_ID				= @USER_ID


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




/****** Object:  StoredProcedure [dbo].[LIST_UserClntSrvcAssn]    Script Date: 5/18/2016 1:21:23 PM ******/
SET ANSI_NULLS ON







