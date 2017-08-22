CREATE PROCEDURE [dbo].[GetEngmtUploadWorkList]
(
       @PiUserID                  INTEGER,
       @OrgName                   VARCHAR(150),
       @CLNT_ENGMT_CD             VARCHAR(30),  
       @CLNT_ENGMT_NM             VARCHAR(150),
       @SECUR_SRVC_NM             VARCHAR(150),
       @STRT_DT                   VARCHAR(10),
       @END_DT                    VARCHAR(10),
       @PiUserType                INTEGER,
       @PvcFlag                   VARCHAR(1),
       @UpdatedDate				  VARCHAR(10)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON

IF @PvcFlag = 'A' -- Analyst
BEGIN
       SELECT [Client NAME],CLNT_ORG_KEY, CLNT_ENGMT_CD,CLNT_ENGMT_NM,SECUR_SRVC_NM,SECUR_SRVC_CD,
                     FL_LCK_IND,[Start Date],[End Date],USER_ID,UpdatedDate,
                     FileCount,CREAT_DT
                     FROM(
SELECT      H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,G.USER_STRT_DT [Start Date],G.USER_END_DT [End Date],G.USER_ID,
                     MAX(J.FL_UPLOAD_DT) UpdatedDate,
                     CASE WHEN ISNULL(J.CLNT_ENGMT_CD,'')='' THEN 0 ELSE 1 END FileCount,B.CREAT_DT
       FROM   CLNT_SECUR_SRVC_ENGMT             A
       JOIN   CLNT_ENGMT                               B
       ON            A.CLNT_ENGMT_CD                   =      B.CLNT_ENGMT_CD
       AND           B.ROW_STS_KEY              =   1
       JOIN   SECUR_SRVC                               D
       ON            D.SECUR_SRVC_CD                   =      A.SECUR_SRVC_CD
       JOIN   USER_CLNT_SRVC_ASGN               G
       ON            B.CLNT_ENGMT_CD                   =      G.CLNT_ENGMT_CD
       AND           D.SECUR_SRVC_CD                   =   G.SECUR_SRVC_CD
       JOIN   ORG                                             H
       ON            H.ORG_KEY                         =      B.CLNT_ORG_KEY 
       AND           H.ROW_STS_KEY              =   1
       LEFT JOIN APPL_FL_UPLOAD_LOG             J
       ON            B.CLNT_ENGMT_CD                   =      J.CLNT_ENGMT_CD
       AND           D.SECUR_SRVC_CD                   =      J.SECUR_SRVC_CD  
       WHERE  G.USER_ID                         =   @PiUserID 
       AND           ISNULL(H.ORG_NM,'') LIKE CASE WHEN @OrgName = '' THEN  ISNULL(H.ORG_NM,'') ELSE '%' + @OrgName + '%' END
       AND           ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN   A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END 
       AND           ISNULL(B.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN   B.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%'   END    
       AND           ISNULL(D.SECUR_SRVC_NM,'') LIKE CASE WHEN @SECUR_SRVC_NM = '' THEN   D.SECUR_SRVC_NM ELSE '%' + @SECUR_SRVC_NM + '%' END
       AND           CONVERT(VARCHAR(20),G.USER_STRT_DT,101)  = CASE WHEN @STRT_DT  = '' THEN CONVERT(VARCHAR(20),G.USER_STRT_DT,101)  ELSE @STRT_DT END
       AND           CONVERT(VARCHAR(20),G.USER_END_DT,101)   = CASE WHEN @END_DT  = '' THEN CONVERT(VARCHAR(20),G.USER_END_DT,101)  ELSE @END_DT END
       GROUP BY H.ORG_NM,B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,G.USER_STRT_DT,G.USER_END_DT,G.USER_ID,B.CREAT_DT,J.CLNT_ENGMT_CD)B
       WHERE   ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')    = CASE WHEN @UpdatedDate  = '' THEN ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')  ELSE @UpdatedDate END
       ORDER BY CASE WHEN ISNULL(UpdatedDate,'') ='' THEN CREAT_DT ELSE UpdatedDate END DESC
                           
END

IF @PvcFlag = 'E' -- Engmt Lead
BEGIN
       SELECT [Client NAME],CLNT_ORG_KEY, CLNT_ENGMT_CD,CLNT_ENGMT_NM,SECUR_SRVC_NM,SECUR_SRVC_CD,
                     FL_LCK_IND,[Start Date],[End Date],USER_ID,UpdatedDate,
                     FileCount,CREAT_DT
                     FROM
       (SELECT H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,A.SRVC_EST_STRT_DT [Start Date],A.SRVC_EST_END_DT [End Date],G.USER_ID,
                     MAX(J.FL_UPLOAD_DT) UpdatedDate,CASE WHEN ISNULL(J.CLNT_ENGMT_CD,'')='' THEN 0 ELSE 1 END FileCount,
                     B.CREAT_DT
       FROM   CLNT_SECUR_SRVC_ENGMT             A
       JOIN   CLNT_ENGMT                               B
       ON            A.CLNT_ENGMT_CD                   =      B.CLNT_ENGMT_CD
       AND           B.ROW_STS_KEY              =   1
       JOIN   SECUR_SRVC                               D
       ON            D.SECUR_SRVC_CD                   =      A.SECUR_SRVC_CD
       JOIN   CLNT_ENGMT_USER_ASGN       G
       ON            B.CLNT_ENGMT_CD                   =      G.CLNT_ENGMT_CD
       JOIN   ORG                                             H
       ON            H.ORG_KEY                         =      B.CLNT_ORG_KEY
       AND           H.ROW_STS_KEY              =   1
       LEFT JOIN APPL_FL_UPLOAD_LOG             J
       ON            B.CLNT_ENGMT_CD                   =      J.CLNT_ENGMT_CD
       AND           D.SECUR_SRVC_CD                   =      J.SECUR_SRVC_CD  
       WHERE  G.USER_ID                         =             CASE WHEN @PiUserID =0              THEN G.USER_ID ELSE @PiUserID END
       AND           ISNULL(H.ORG_NM,'')           LIKE CASE WHEN @OrgName = ''            THEN       ISNULL(H.ORG_NM,'') ELSE '%' + @OrgName +'%' END
       AND           ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN   A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END 
       AND           ISNULL(B.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN   B.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%' END      
       AND           ISNULL(D.SECUR_SRVC_NM,'') LIKE CASE WHEN @SECUR_SRVC_NM = '' THEN   D.SECUR_SRVC_NM ELSE '%' + @SECUR_SRVC_NM + '%' END
       AND           CONVERT(VARCHAR(20),A.SRVC_EST_STRT_DT,101)     = CASE WHEN @STRT_DT  = '' THEN CONVERT(VARCHAR(20),A.SRVC_EST_STRT_DT,101)  ELSE @STRT_DT END
       AND           CONVERT(VARCHAR(20),A.SRVC_EST_END_DT,101)      = CASE WHEN @END_DT  = ''  THEN CONVERT(VARCHAR(20),A.SRVC_EST_END_DT,101)  ELSE @END_DT END
       GROUP BY H.ORG_NM,B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,A.SRVC_EST_STRT_DT,A.SRVC_EST_END_DT,G.USER_ID,B.CREAT_DT,J.CLNT_ENGMT_CD)B
       WHERE   ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')    = CASE WHEN @UpdatedDate  = '' THEN ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')  ELSE @UpdatedDate END
       ORDER BY CASE WHEN ISNULL(UpdatedDate,'') ='' THEN CREAT_DT ELSE UpdatedDate END DESC
                           
END

IF @PvcFlag = 'P' -- Partner Lead
BEGIN

       SELECT [Client NAME],CLNT_ORG_KEY, CLNT_ENGMT_CD,CLNT_ENGMT_NM,SECUR_SRVC_NM,SECUR_SRVC_CD,
                     FL_LCK_IND,[Start Date],[End Date],USER_ID,UpdatedDate,
                     FileCount,CREAT_DT
                     FROM
       (SELECT H.ORG_NM [Client NAME],B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,A.SRVC_EST_STRT_DT [Start Date],A.SRVC_EST_END_DT [End Date],G.USER_ID,
                     MAX(J.FL_UPLOAD_DT) UpdatedDate,CASE WHEN ISNULL(J.CLNT_ENGMT_CD,'')='' THEN 0 ELSE 1 END FileCount,
                     B.CREAT_DT
       FROM   CLNT_SECUR_SRVC_ENGMT             A
       JOIN   CLNT_ENGMT                               B
       ON            A.CLNT_ENGMT_CD                   =      B.CLNT_ENGMT_CD
       AND           B.ROW_STS_KEY              =   1
       JOIN   SECUR_SRVC                               D
       ON            D.SECUR_SRVC_CD                   =      A.SECUR_SRVC_CD
       JOIN   CLNT_ENGMT_USER_ASGN       G
       ON            B.CLNT_ENGMT_CD                   =      G.CLNT_ENGMT_CD
       JOIN   ORG                                             H
       ON            H.ORG_KEY                         =      B.CLNT_ORG_KEY
       AND           H.ROW_STS_KEY              =   1
       CROSS APPLY dbo.FnSplit(G.SECUR_SRVC_LIST_CD,',') AS K
       LEFT JOIN APPL_FL_UPLOAD_LOG             J
       ON            B.CLNT_ENGMT_CD                   =      J.CLNT_ENGMT_CD
       AND           D.SECUR_SRVC_CD                   =      J.SECUR_SRVC_CD  
       WHERE  G.USER_ID                         =      @PiUserID
       AND           D.SECUR_SRVC_CD                   =   K.items
       AND           ISNULL(H.ORG_NM,'')           LIKE CASE WHEN @OrgName = ''            THEN       ISNULL(H.ORG_NM,'') ELSE '%' + @OrgName +'%' END
       AND           ISNULL(A.CLNT_ENGMT_CD,'') LIKE CASE WHEN @CLNT_ENGMT_CD = '' THEN   A.CLNT_ENGMT_CD ELSE '%' + @CLNT_ENGMT_CD +'%' END 
       AND           ISNULL(B.CLNT_ENGMT_NM,'') LIKE CASE WHEN @CLNT_ENGMT_NM = '' THEN   B.CLNT_ENGMT_NM ELSE '%' + @CLNT_ENGMT_NM + '%' END      
       AND           ISNULL(D.SECUR_SRVC_NM,'') LIKE CASE WHEN @SECUR_SRVC_NM = '' THEN   D.SECUR_SRVC_NM ELSE '%' + @SECUR_SRVC_NM + '%' END
       AND           CONVERT(VARCHAR(20),A.SRVC_EST_STRT_DT,101)     = CASE WHEN @STRT_DT  = '' THEN CONVERT(VARCHAR(20),A.SRVC_EST_STRT_DT,101)  ELSE @STRT_DT END
       AND           CONVERT(VARCHAR(20),A.SRVC_EST_END_DT,101)      = CASE WHEN @END_DT  = ''  THEN CONVERT(VARCHAR(20),A.SRVC_EST_END_DT,101)  ELSE @END_DT END
       GROUP BY H.ORG_NM,B.CLNT_ORG_KEY, A.CLNT_ENGMT_CD,B.CLNT_ENGMT_NM,D.SECUR_SRVC_NM,D.SECUR_SRVC_CD,
                     A.FL_LCK_IND,A.SRVC_EST_STRT_DT,A.SRVC_EST_END_DT,G.USER_ID,B.CREAT_DT,J.CLNT_ENGMT_CD)B
       WHERE   ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')    = CASE WHEN @UpdatedDate  = '' THEN ISNULL(CONVERT(VARCHAR(20),UpdatedDate,101),'')  ELSE @UpdatedDate END
       ORDER BY CASE WHEN ISNULL(UpdatedDate,'') ='' THEN CREAT_DT ELSE UpdatedDate END DESC
                           
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



