﻿CREATE PROCEDURE [dbo].[REPORT_PRIORITYMATRIX]
(
       @CLNT_ENGMT_CD             VARCHAR(30),  
       @SECUR_SRVC_CD             VARCHAR(150),
	   @schema					  VARCHAR(50)  
  
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)

	SET		 @Query ='
    SELECT   DISTINCT CLNT_ENGMT_CD,CLNT_VULN_INSTC_KEY,SECUR_OBJ_CD,SECUR_CTL_CD,SECUR_CTL_NM,Quadrant
	FROM (
    SELECT   A.CLNT_ENGMT_CD,A.CLNT_VULN_INSTC_KEY,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM,''Quadrant1'' Quadrant
    FROM     '+ @schema+'.CLNT_VULN_INSTC		  A
	JOIN	 VULN_IMP				  B
	ON		 A.VULN_IMP_CD			= B.VULN_IMP_CD
	JOIN	 RMDTN_CST_EFFRT		  C
	ON		 A.RMDTN_CST_EFFRT_CD	= C.RMDTN_CST_EFFRT_CD
	LEFT JOIN '+ @schema+'.CLNT_VULN_SECUR_CTL	  D
	ON		 A.CLNT_VULN_INSTC_KEY	= D.CLNT_VULN_INSTC_KEY
	AND		 D.ROW_STS_KEY			= 1
	LEFT JOIN SECUR_CTL				  E
	ON		 D.REG_CMPLN_CD			= E.REG_CMPLN_CD
	AND		 D.SECUR_CTL_CD			= E.SECUR_CTL_CD
	AND		 D.REG_CMPLN_VER		= E.REG_CMPLN_VER
	AND		 E.REG_CMPLN_CD			= ''HITRUST''
	WHERE    CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
--	AND		 SECUR_SRVC_CD			= CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
	AND		 VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
	AND		 VULN_SEV_CD			NOT IN(''I'')
	AND		 B.VULN_IMP_CD			IN(''C'',''MJ'') 
	AND		 C.RMDTN_CST_EFFRT_CD = ''L''
	AND		 A.ROW_STS_KEY		  = 1
--	GROUP BY A.CLNT_ENGMT_CD,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM
	UNION
	SELECT   A.CLNT_ENGMT_CD,A.CLNT_VULN_INSTC_KEY, E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM,''Quadrant2'' Quadrant
    FROM     '+ @schema+'.CLNT_VULN_INSTC		  A
	JOIN	 VULN_IMP				  B
	ON		 A.VULN_IMP_CD			= B.VULN_IMP_CD
	JOIN	 RMDTN_CST_EFFRT		  C
	ON		 A.RMDTN_CST_EFFRT_CD	= C.RMDTN_CST_EFFRT_CD
	LEFT JOIN '+ @schema+'.CLNT_VULN_SECUR_CTL	  D
	ON		 A.CLNT_VULN_INSTC_KEY	= D.CLNT_VULN_INSTC_KEY
	AND		 D.ROW_STS_KEY			= 1
	LEFT JOIN SECUR_CTL				  E
	ON		 D.REG_CMPLN_CD			= E.REG_CMPLN_CD
	AND		 D.SECUR_CTL_CD			= E.SECUR_CTL_CD
	AND		 D.REG_CMPLN_VER		= E.REG_CMPLN_VER
	AND		 E.REG_CMPLN_CD			= ''HITRUST''
	WHERE    CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
--	AND		 SECUR_SRVC_CD			= CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
	AND		 VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
	AND		 VULN_SEV_CD			NOT IN(''I'')
	AND		 B.VULN_IMP_CD			IN(''C'',''MJ'') 
	AND		 C.RMDTN_CST_EFFRT_CD IN(''H'',''M'')
	AND		 A.ROW_STS_KEY		  = 1
--	GROUP BY A.CLNT_ENGMT_CD,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM
	UNION
	SELECT   A.CLNT_ENGMT_CD,A.CLNT_VULN_INSTC_KEY,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM,''Quadrant3'' Quadrant
    FROM     '+ @schema+'.CLNT_VULN_INSTC		  A
	JOIN	 VULN_IMP				  B
	ON		 A.VULN_IMP_CD			= B.VULN_IMP_CD
	JOIN	 RMDTN_CST_EFFRT		  C
	ON		 A.RMDTN_CST_EFFRT_CD	= C.RMDTN_CST_EFFRT_CD
	LEFT JOIN '+ @schema+'.CLNT_VULN_SECUR_CTL	  D
	ON		 A.CLNT_VULN_INSTC_KEY	= D.CLNT_VULN_INSTC_KEY
	AND		 D.ROW_STS_KEY			= 1
	LEFT JOIN SECUR_CTL				  E
	ON		 D.REG_CMPLN_CD			= E.REG_CMPLN_CD
	AND		 D.SECUR_CTL_CD			= E.SECUR_CTL_CD
	AND		 D.REG_CMPLN_VER		= E.REG_CMPLN_VER
	AND		 E.REG_CMPLN_CD			= ''HITRUST''
	WHERE    CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
--	AND		 SECUR_SRVC_CD			= CASE WHEN '''+@SECUR_SRVC_CD+'''='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
	AND		 VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
	AND		 VULN_SEV_CD			NOT IN(''I'')
	AND		 B.VULN_IMP_CD			IN(''I'',''MI'',''IF'',''MO'') 
	AND		 C.RMDTN_CST_EFFRT_CD	= ''L''
	AND		 A.ROW_STS_KEY		  = 1
--	GROUP BY A.CLNT_ENGMT_CD,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM
	UNION
	SELECT   A.CLNT_ENGMT_CD,A.CLNT_VULN_INSTC_KEY,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM,''Quadrant4'' Quadrant
    FROM     '+ @schema+'.CLNT_VULN_INSTC		  A
	JOIN	 VULN_IMP				  B
	ON		 A.VULN_IMP_CD			= B.VULN_IMP_CD
	JOIN	 RMDTN_CST_EFFRT		  C
	ON		 A.RMDTN_CST_EFFRT_CD	= C.RMDTN_CST_EFFRT_CD
	LEFT JOIN '+ @schema+'.CLNT_VULN_SECUR_CTL	  D
	ON		 A.CLNT_VULN_INSTC_KEY	= D.CLNT_VULN_INSTC_KEY
	AND		 D.ROW_STS_KEY			= 1
	LEFT JOIN SECUR_CTL				  E
	ON		 D.REG_CMPLN_CD			= E.REG_CMPLN_CD
	AND		 D.SECUR_CTL_CD			= E.SECUR_CTL_CD
	AND		 D.REG_CMPLN_VER		= E.REG_CMPLN_VER
	AND		 E.REG_CMPLN_CD			= ''HITRUST''
	WHERE    CLNT_ENGMT_CD			= '''+@CLNT_ENGMT_CD+'''
--	AND		 SECUR_SRVC_CD			= CASE WHEN '''+@SECUR_SRVC_CD+ '='''' THEN SECUR_SRVC_CD ELSE '''+@SECUR_SRVC_CD+''' END
	AND		 VULN_INSTC_STS_CD		NOT IN(''D'',''FP'')
	AND		 VULN_SEV_CD			NOT IN(''I'')
	AND		 B.VULN_IMP_CD IN(''I'',''MI'',''IF'',''MO'') 
	AND		 C.RMDTN_CST_EFFRT_CD IN(''H'',''M'')
	AND		 A.ROW_STS_KEY		  = 1
--	GROUP BY A.CLNT_ENGMT_CD,E.SECUR_OBJ_CD,E.SECUR_CTL_CD,E.SECUR_CTL_NM
)Z
	ORDER BY Quadrant '
EXECUTE (@Query)
--PRINT @Query

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
