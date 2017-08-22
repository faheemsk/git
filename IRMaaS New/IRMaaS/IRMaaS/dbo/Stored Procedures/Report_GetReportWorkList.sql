CREATE PROCEDURE  [dbo].[Report_GetReportWorkList]
(

       @PiUserID                  INTEGER

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

       SELECT		 [Client NAME],CLNT_ORG_KEY, CLNT_ENGMT_CD,CLNT_ENGMT_NM,
                     RPT_NM,RPT_FL_UPLOAD_LOG_KEY,FL_NM,UPDT_DT,RPT_STS_KEY,LKP_ENTY_NM,
					 CREAT_DT 
					 FROM(
       SELECT        DISTINCT  H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,
                     K.RPT_NM,J.RPT_FL_UPLOAD_LOG_KEY,J.FL_NM,J.UPDT_DT,J.RPT_STS_KEY,I.LKP_ENTY_NM,
					 J.CREAT_DT
       FROM          CLNT_SECUR_SRVC_ENGMT							 A
       JOIN          CLNT_ENGMT                                      B
       ON            A.CLNT_ENGMT_CD                          =      B.CLNT_ENGMT_CD
       JOIN          USER_CLNT_SRVC_ASGN							 G
       ON            B.CLNT_ENGMT_CD                          =      G.CLNT_ENGMT_CD
       JOIN          ORG                                                    H
       ON            H.ORG_KEY                                =      B.CLNT_ORG_KEY 
       LEFT JOIN     RPT_FL_UPLOAD_LOG								 J
       ON            A.CLNT_ENGMT_CD                          =      J.CLNT_ENGMT_CD
       LEFT JOIN     RPT_NM                                          K
       ON            J.RPT_NM_KEY							  =		 K.RPT_NM_KEY
       LEFT JOIN     MSTR_LKP                                        I
       ON            I.MSTR_LKP_KEY                           =		 J.RPT_STS_KEY
	 --  AND			 I.LKP_ENTY_NM							  <>     'Scan Failure'
       WHERE         G.USER_ID                                =		 @PiUserID 
	   AND			 B.ROW_STS_KEY							  =		 1
       
	   UNION

       SELECT        DISTINCT H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,
                     K.RPT_NM,J.RPT_FL_UPLOAD_LOG_KEY,J.FL_NM,J.UPDT_DT,J.RPT_STS_KEY,I.LKP_ENTY_NM,
					 J.CREAT_DT
       FROM          CLNT_SECUR_SRVC_ENGMT							 A
       JOIN          CLNT_ENGMT                                      B
       ON            A.CLNT_ENGMT_CD                          =      B.CLNT_ENGMT_CD
       JOIN          CLNT_ENGMT_USER_ASGN							 G
       ON            B.CLNT_ENGMT_CD                          =      G.CLNT_ENGMT_CD
       JOIN          ORG                                                    H
       ON            H.ORG_KEY                                =      B.CLNT_ORG_KEY 
       LEFT JOIN     RPT_FL_UPLOAD_LOG								 J
       ON            A.CLNT_ENGMT_CD                          =      J.CLNT_ENGMT_CD
       LEFT JOIN     RPT_NM                                          K
       ON            J.RPT_NM_KEY							  =		 K.RPT_NM_KEY
       LEFT JOIN     MSTR_LKP                                        I
       ON            I.MSTR_LKP_KEY                           =		 J.RPT_STS_KEY
	--   AND			 I.LKP_ENTY_NM							  <>     'Scan Failure'
       WHERE         G.USER_ID                                =		 @PiUserID 
	   AND			 B.ROW_STS_KEY							  =		 1
	   
		)Z
	--	WHERE ISNULL(Z.RPT_STS_KEY,'') <> 70 
	   ORDER BY		 CASE WHEN ISNULL(UPDT_DT,'') ='' THEN CREAT_DT ELSE UPDT_DT END DESC
	    
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
