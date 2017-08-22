SELECT VULN_OVALL_SCOR, 
		 CASE WHEN (VULN_OVALL_SCOR >= 9 AND VULN_OVALL_SCOR <= 10) THEN 'C'
			  WHEN (VULN_OVALL_SCOR >= 7 AND VULN_OVALL_SCOR <= 8.9) THEN 'H'
			  WHEN (VULN_OVALL_SCOR >= 4 AND VULN_OVALL_SCOR <= 6.9) THEN 'M'
			  WHEN (VULN_OVALL_SCOR >= 0.1 AND VULN_OVALL_SCOR <= 3.9) THEN 'L'
			  WHEN (VULN_OVALL_SCOR = 0) THEN 'I' END  VULN_SEV_CD,
		 VULN_IMP_SUB_SCOR, 
		 CASE WHEN (VULN_IMP_SUB_SCOR >= 9 AND VULN_IMP_SUB_SCOR <= 10) THEN 'C'
			  WHEN (VULN_IMP_SUB_SCOR >= 7 AND VULN_IMP_SUB_SCOR <= 8.9) THEN 'MJ'
			  WHEN (VULN_IMP_SUB_SCOR >= 4 AND VULN_IMP_SUB_SCOR <= 6.9) THEN 'MO'
			  WHEN (VULN_IMP_SUB_SCOR >= 2 AND VULN_IMP_SUB_SCOR <= 3.9) THEN 'MI'
			  WHEN (VULN_IMP_SUB_SCOR >= 0.1 AND VULN_IMP_SUB_SCOR <= 1.9) THEN 'I'
			  WHEN (VULN_IMP_SUB_SCOR = 0) THEN 'IF' END  VULN_IMP_CD,
		  VULN_EXPLT_SUB_SCOR,
		  CASE WHEN (VULN_EXPLT_SUB_SCOR >= 9 AND VULN_EXPLT_SUB_SCOR <= 10) THEN 'A'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 7 AND VULN_EXPLT_SUB_SCOR <= 8.9) THEN 'L'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 4 AND VULN_EXPLT_SUB_SCOR <= 6.9) THEN 'P'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 2 AND VULN_EXPLT_SUB_SCOR <= 3.9) THEN 'U'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 0.1 AND VULN_EXPLT_SUB_SCOR <= 1.9) THEN 'R'
			  WHEN (VULN_EXPLT_SUB_SCOR = 0) THEN 'I' END  VULN_RISK_CD

  FROM [LIM8].[CLNT_VULN_INSTC]

  SELECT * FROm LIM8.CLNT_VULN_INSTC

  UPDATE [LIM8].CLNT_VULN_INSTC
  SET  VULN_SEV_CD = CASE WHEN (VULN_OVALL_SCOR >= 9 AND VULN_OVALL_SCOR <= 10) THEN 'C'
			  WHEN (VULN_OVALL_SCOR >= 7 AND VULN_OVALL_SCOR <= 8.9) THEN 'H'
			  WHEN (VULN_OVALL_SCOR >= 4 AND VULN_OVALL_SCOR <= 6.9) THEN 'M'
			  WHEN (VULN_OVALL_SCOR >= 0.1 AND VULN_OVALL_SCOR <= 3.9) THEN 'L'
			  WHEN (VULN_OVALL_SCOR = 0) THEN 'I' END  
      ,VULN_IMP_CD =  CASE WHEN (VULN_IMP_SUB_SCOR >= 9 AND VULN_IMP_SUB_SCOR <= 10) THEN 'C'
			  WHEN (VULN_IMP_SUB_SCOR >= 7 AND VULN_IMP_SUB_SCOR <= 8.9) THEN 'MJ'
			  WHEN (VULN_IMP_SUB_SCOR >= 4 AND VULN_IMP_SUB_SCOR <= 6.9) THEN 'MO'
			  WHEN (VULN_IMP_SUB_SCOR >= 2 AND VULN_IMP_SUB_SCOR <= 3.9) THEN 'MI'
			  WHEN (VULN_IMP_SUB_SCOR >= 0.1 AND VULN_IMP_SUB_SCOR <= 1.9) THEN 'I'
			  WHEN (VULN_IMP_SUB_SCOR = 0) THEN 'IF' END  
      ,RISK_PRBL_CD= CASE WHEN (VULN_EXPLT_SUB_SCOR >= 9 AND VULN_EXPLT_SUB_SCOR <= 10) THEN 'A'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 7 AND VULN_EXPLT_SUB_SCOR <= 8.9) THEN 'L'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 4 AND VULN_EXPLT_SUB_SCOR <= 6.9) THEN 'P'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 2 AND VULN_EXPLT_SUB_SCOR <= 3.9) THEN 'U'
			  WHEN (VULN_EXPLT_SUB_SCOR >= 0.1 AND VULN_EXPLT_SUB_SCOR <= 1.9) THEN 'R'
			  WHEN (VULN_EXPLT_SUB_SCOR = 0) THEN 'I' END  
	WHERE CLNT_ENGMT_CD = 'LIM-8-TR-20170220'


  UPDATE A
  SET A.VULN_CATGY_CD = B.VULN_CATGY_CD
  FROM	LIM8.CLNT_VULN_INSTC A
  JOIN	VULN B
  ON	A.VULN_NM = B.VULN_NM
  WHERE A.CLNT_ENGMT_CD = 'LIM-8-TR-20170220'