CREATE PROCEDURE [dbo].[Report_GetReportWorkListSearch]
(
       @PiUserID                  INTEGER,
       @OrgName                   VARCHAR(150),
       @CLNT_ENGMT_CD             VARCHAR(30),  
       @CLNT_ENGMT_NM             VARCHAR(150),
       @RPT_NM                    VARCHAR(150),
       @FL_NM                     VARCHAR(150),
       @UPDT_DT                   VARCHAR(50),
       @Status                    VARCHAR(50)   

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON


        SELECT         [Client NAME],CLNT_ORG_KEY, CLNT_ENGMT_CD,CLNT_ENGMT_NM,
                     RPT_NM,RPT_FL_UPLOAD_LOG_KEY,FL_NM,UPDT_DT,RPT_STS_KEY,LKP_ENTY_NM,
                                  CREAT_DT 
                                    FROM(
                                  SELECT       DISTINCT  H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,
                     K.RPT_NM,J.RPT_FL_UPLOAD_LOG_KEY,J.FL_NM,CONVERT(VARCHAR(20),J.UPDT_DT,101) UPDT_DT,J.RPT_STS_KEY,I.LKP_ENTY_NM,
                                  CONVERT(VARCHAR(20),J.CREAT_DT,101) CREAT_DT
       FROM          CLNT_SECUR_SRVC_ENGMT                                               A
       JOIN          CLNT_ENGMT                                      B
       ON            A.CLNT_ENGMT_CD                          =      B.CLNT_ENGMT_CD
       JOIN          USER_CLNT_SRVC_ASGN                                          G
       ON            B.CLNT_ENGMT_CD                          =      G.CLNT_ENGMT_CD
       JOIN          ORG                                             H
       ON            H.ORG_KEY                                =      B.CLNT_ORG_KEY 
       LEFT JOIN     RPT_FL_UPLOAD_LOG                                                   J
       ON            A.CLNT_ENGMT_CD                          =      J.CLNT_ENGMT_CD
      
       LEFT JOIN     RPT_NM                                          K
       ON            J.RPT_NM_KEY                      =   K.RPT_NM_KEY
      
       LEFT JOIN     MSTR_LKP                                        I
       ON            I.MSTR_LKP_KEY                           =   J.RPT_STS_KEY
         
       --   AND               I.LKP_ENTY_NM                                                  <>     'Scan Failure'        
       WHERE         G.USER_ID                                =   @PiUserID 
       AND           ISNULL(H.ORG_NM,'')           LIKE CASE WHEN @OrgName = '' THEN       ISNULL(H.ORG_NM,'') ELSE '%' + @OrgName + '%' END
       AND           ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN       A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END     
       AND           ISNULL(B.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN       B.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%'   END  
           AND           ISNULL(K.RPT_NM,'')           LIKE CASE WHEN @RPT_NM = '' THEN       ISNULL(K.RPT_NM,'') ELSE '%' + @RPT_NM + '%' END
              AND           ISNULL(CONVERT(VARCHAR(20),J.UPDT_DT,101),'') = CASE WHEN @UPDT_DT  = '' THEN ISNULL(CONVERT(VARCHAR(20),J.UPDT_DT,101),'')  ELSE @UPDT_DT END
       AND           ISNULL(J.FL_NM,'')            LIKE CASE WHEN @FL_NM = '' THEN       ISNULL(J.FL_NM,'') ELSE '%' + @FL_NM + '%' END
          AND           ISNULL(I.LKP_ENTY_NM ,'')   =     CASE WHEN @Status = '' THEN ISNULL(I.LKP_ENTY_NM ,'')  ELSE @Status  END
       AND                 B.ROW_STS_KEY                                                  =           1
          UNION

       SELECT        DISTINCT H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,
                     K.RPT_NM,J.RPT_FL_UPLOAD_LOG_KEY,J.FL_NM,CONVERT(VARCHAR(20),J.UPDT_DT,101) UPDT_DT,J.RPT_STS_KEY,I.LKP_ENTY_NM,
                                  CONVERT(VARCHAR(20),J.CREAT_DT,101) CREAT_DT
       FROM          CLNT_SECUR_SRVC_ENGMT                                               A
       JOIN          CLNT_ENGMT                                      B
       ON            A.CLNT_ENGMT_CD                          =      B.CLNT_ENGMT_CD
       JOIN          CLNT_ENGMT_USER_ASGN                                                G
       ON            B.CLNT_ENGMT_CD                          =      G.CLNT_ENGMT_CD
       JOIN          ORG                                             H
       ON            H.ORG_KEY                                =      B.CLNT_ORG_KEY 
       LEFT JOIN     RPT_FL_UPLOAD_LOG                                                   J
       ON            A.CLNT_ENGMT_CD                          =      J.CLNT_ENGMT_CD
       
       LEFT JOIN     RPT_NM                                                                            K
       ON            J.RPT_NM_KEY                                          =            K.RPT_NM_KEY
       
       LEFT JOIN     MSTR_LKP                                                                          I
       ON            I.MSTR_LKP_KEY                           =            J.RPT_STS_KEY
   
       --   AND               I.LKP_ENTY_NM                                                  <>     'Scan Failure'       
       WHERE         G.USER_ID                                =   @PiUserID 
       AND           ISNULL(H.ORG_NM,'')           LIKE CASE WHEN @OrgName = '' THEN       ISNULL(H.ORG_NM,'') ELSE '%' + @OrgName + '%' END
       AND           ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN       A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END     
       AND           ISNULL(B.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN       B.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%'   END  
          AND           ISNULL(K.RPT_NM,'')           LIKE CASE WHEN @RPT_NM = '' THEN       ISNULL(K.RPT_NM,'') ELSE '%' + @RPT_NM + '%' END
          AND           ISNULL(CONVERT(VARCHAR(20),J.UPDT_DT,101),'') = CASE WHEN @UPDT_DT  = '' THEN ISNULL(CONVERT(VARCHAR(20),J.UPDT_DT,101),'')  ELSE @UPDT_DT END
       AND           ISNULL(J.FL_NM,'')            LIKE CASE WHEN @FL_NM = '' THEN       ISNULL(J.FL_NM,'') ELSE '%' + @FL_NM + '%' END
           AND           ISNULL(I.LKP_ENTY_NM ,'')   =     CASE WHEN @Status = '' THEN ISNULL(I.LKP_ENTY_NM ,'')  ELSE @Status  END
          AND               B.ROW_STS_KEY                                                  =           1) Z      
       --  WHERE             ISNULL(Z.RPT_STS_KEY,'')       <> 70
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