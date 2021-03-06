ALTER TABLE CLNT_ENGMT_USER_ASGN ADD USER_APPL_ROLE_KEY   integer  NULL, APPL_ROLE_KEY        integer  NOT NULL 
ALTER TABLE USER_CLNT_SRVC_ASGN ADD USER_APPL_ROLE_KEY   integer  NULL, APPL_ROLE_KEY        integer  NOT NULL 
ALTER TABLE CLNT_RMDTN_PLN ADD  ADJ_RMDTN_PLN_SEV_CD varchar(3)  NULL ,RMDTN_PLN_SEV_CD     varchar(3)  NULL 

INSERT [dbo].[SECUR_PKG] ([SECUR_PKG_CD], [ROW_STS_KEY], [SECUR_PKG_NM], [SECUR_PKG_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GW', 1, N'Get Well', N'This Service Package offering will look to exploit a client''s security vulnerabilities, which may lead to a security breach.  The Package will also include a proactive analysis to determine if a breach has already occurred or is in progress.  This information will allow the guidance in the prioritization and remediation of risks with the client''s current environment.  Examples of Security Assessment Services for Health Check include, but are not limited to; Threat Hunting, Limited Red Team Exercises, and Vulnerability Scanning.   (Point In Time)', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG] ([SECUR_PKG_CD], [ROW_STS_KEY], [SECUR_PKG_NM], [SECUR_PKG_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SW', 1, N'Stay Well', N'This Service Package offering will focus on identification of people, process and technology risks associated with the evolving cyber threat environment.  This includes mapping of risk findings to various compliance frameworks such as HIPAA/HITECH, PCI DSS, FDA regulations etc.  A "Get Well Plan" with an actionable Roadmap will be a deliveriable out of this Service Package.  Examples of Security Assessment Services for Triage include, but are not limited to; Penetration Testing, Security Risk Assessment, Wireless Risk Assessment, Vulnerability Scanning, Compliance and Remediation.  (Point In Time)', GETDATE(), 1, NULL, NULL)

INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GW', N'AV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GW', N'NV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GW', N'RT', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GW', N'TH', 1, GETDATE(), 1, NULL, NULL)

INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SW', N'AV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SW', N'NV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SW', N'RT', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SW', N'TH', 1, GETDATE(), 1, NULL, NULL)


SET IDENTITY_INSERT [dbo].[PERMSN] ON
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (88, 1, 34, 6, N'Manage Engagement Users', N'Manage Engagement Users Submenu', N'Manage Engagement Users', 0, 2, GETDATE(), 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[PERMSN] OFF

UPDATE [PERMSN] SET PAR_PERMSN_KEY=88
WHERE PERMSN_KEY IN(40,41,42,43)

UPDATE PERMSN_GRP_ASSOC SET SUB_MNU_ID=88 WHERE PERMSN_GRP_KEY=4
UPDATE PERMSN SET PERMSN_NM='Reporting Documents',DSPL_TXT='Reporting Documents' WHERE PERMSN_TYP_KEY=5 AND PERMSN_NM='Client Reports Upload'
UPDATE PERMSN SET PERMSN_NM='Reporting Documents',DSPL_TXT='Reporting Documents' WHERE PERMSN_TYP_KEY=6 AND PERMSN_NM='Client Reports Upload'

DELETE FROM APPL_ROLE_PERMSN_GRP WHERE APPL_ROLE_KEY=11 AND PERMSN_GRP_KEY=10

DELETE FROM APPL_ROLE_PERMSN_GRP WHERE APPL_ROLE_KEY=10 AND PERMSN_GRP_KEY=10

SET IDENTITY_INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ON 
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (31, 1, 3, 4, GETDATE(), 3, NULL, NULL)
SET IDENTITY_INSERT [dbo].[APPL_ROLE_PERMSN_GRP] OFF


GO

ALTER PROCEDURE [dbo].[RMDTN_DEL_RMDTN_PLN]
(
       @CLNT_RMDTN_PLN_KEY			INTEGER,       
       @schema						VARCHAR(50)       

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)
DECLARE @Query3 VARCHAR(max)
DECLARE @Query4 VARCHAR(max)
DECLARE @Query5 VARCHAR(max)
DECLARE @id INTEGER

      
              
        SET  @Query1 = 'DELETE FROM '+ @schema+'.CLNT_RISK_RGST_ITM
                       WHERE CLNT_RISK_RGST_KEY  IN (SELECT CLNT_RISK_RGST_KEY FROM '+ @schema+'.CLNT_RISK_RGST
					   WHERE CLNT_RMDTN_PLN_KEY= ' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +')'


              EXECUTE (@Query1)
		--	  PRINT @Query1

		SET	  @Query2 = 'DELETE FROM '+ @schema+'.CLNT_RISK_RGST
                       WHERE CLNT_RMDTN_PLN_KEY  =' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''

              EXECUTE (@Query2)

		SET	 @Query3 = 'DELETE FROM '+ @schema+'.CLNT_RMDTN_PLN_COMMT
                       WHERE CLNT_RMDTN_PLN_KEY  =' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''

              EXECUTE (@Query3)

		SET	   @Query4 = 'DELETE FROM '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM
                       WHERE CLNT_RMDTN_PLN_KEY  =' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''

              EXECUTE (@Query4)

		SET	   @Query5 = 'DELETE FROM '+ @schema+'.CLNT_RMDTN_PLN
                       WHERE CLNT_RMDTN_PLN_KEY  =' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''

              EXECUTE (@Query5)

              SELECT @@ROWCOUNT AS RETVALS
                     
       

       

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
CREATE PROCEDURE [dbo].[RMDTN_ListOwnerUsers]
(

	  @FLAG		        VARCHAR(1),
	  @CLNT_ENGMT_CD	VARCHAR(50)

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

	IF @FLAG = 'R' -- Remediation Owner
	BEGIN

      SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
      FROM    USER_PRFL					A
      JOIN    ORG						B
      ON      A.ORG_KEY				=   B.ORG_KEY
	  JOIN    USER_APPL_ROLE			C
	  ON      A.USER_ID				=   C.USER_ID
	  JOIN	  APPL_ROLE					D
	  ON      C.APPL_ROLE_KEY		=   D.APPL_ROLE_KEY
	  JOIN	  CLNT_ENGMT_USER_ASGN		E
	  ON      C.APPL_ROLE_KEY		=   E.APPL_ROLE_KEY
	  AND	  D.APPL_ROLE_KEY		=   E.APPL_ROLE_KEY
	  AND	  A.USER_ID				=   E.USER_ID
	  AND     A.ROW_STS_KEY			=   1
	  AND     B.ROW_STS_KEY			=   1
	  AND     A.LCK_IND				=   0
	  AND	  D.APPL_ROLE_NM	    = 'Remediation Owner'
	  AND	  E.CLNT_ENGMT_CD		=  @CLNT_ENGMT_CD
      ORDER BY A.USER_ID DESC 
	  END

	IF @FLAG = 'K' -- Risk Registry Owner
	BEGIN

      SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
      FROM    USER_PRFL					A
      JOIN    ORG						B
      ON      A.ORG_KEY				=   B.ORG_KEY
	  JOIN    USER_APPL_ROLE			C
	  ON      A.USER_ID				=   C.USER_ID
	  JOIN	  APPL_ROLE					D
	  ON      C.APPL_ROLE_KEY		=   D.APPL_ROLE_KEY
	  JOIN	  CLNT_ENGMT_USER_ASGN		E
	  ON      C.APPL_ROLE_KEY		=   E.APPL_ROLE_KEY
	  AND	  D.APPL_ROLE_KEY		=   E.APPL_ROLE_KEY
	  AND	  A.USER_ID				=   E.USER_ID
	  AND     A.ROW_STS_KEY			=   1
	  AND     B.ROW_STS_KEY			=   1
	  AND     A.LCK_IND				=   0
	  AND	  D.APPL_ROLE_NM	    = 'Risk Register Owner'
	  AND	  E.CLNT_ENGMT_CD		=  @CLNT_ENGMT_CD
      ORDER BY A.USER_ID DESC 
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
END;


GO

ALTER PROCEDURE [dbo].[RMDTN_UpdatePlanStatus]
(
	@CLNT_RMDTN_PLN_KEY		INTEGER,
	@Userid					INTEGER,
	@schema					VARCHAR(50),
	@Flag					VARCHAR(1)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON	
	DECLARE @Query VARCHAR(max)


	SET		@Query =	'UPDATE '+ @schema+'.CLNT_RMDTN_PLN
						 SET 
								RMDTN_PLN_STS_CD		=   '''+@Flag+''' ,
								UPDT_DT					=	'+'GETDATE()'+',
								UPDT_USER_ID			=	'+CONVERT(VARCHAR,@Userid)+'
						 WHERE	CLNT_RMDTN_PLN_KEY		=  '+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+''

 
	--PRINT    @Query
	 EXECUTE (@Query)
	 SELECT @@ROWCOUNT AS RETVALS

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

ALTER PROCEDURE [dbo].[RMDTN_ReportRemediationOwner]
(
	
	@CLNT_ENGMT_CD	VARCHAR(100),
    @schema			 VARCHAR(50)	
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
 DECLARE @Query VARCHAR(max)

 
 SET		  @Query ='	 
 SELECT   A.RMDTN_OWNR_USER_ID,D.FST_NM+'' ''+D.LST_NM UserName,
		  COUNT(DISTINCT CASE WHEN ISNULL(A.RMDTN_PLN_STS_CD,'''')=''O''  THEN A.CLNT_RMDTN_PLN_KEY ELSE NULL END)''Open'',
		  COUNT(DISTINCT CASE WHEN ISNULL(A.RMDTN_PLN_STS_CD,'''')=''I''  THEN A.CLNT_RMDTN_PLN_KEY ELSE NULL END)''Inprogress'',
		  COUNT(DISTINCT CASE WHEN ISNULL(A.RMDTN_PLN_STS_CD,'''')=''R''  THEN A.CLNT_RMDTN_PLN_KEY ELSE NULL END)''Risk Registered'',
		  COUNT(DISTINCT CASE WHEN ISNULL(A.RMDTN_PLN_STS_CD,'''')=''A''  THEN A.CLNT_RMDTN_PLN_KEY ELSE NULL END)''Risk Accepted'',
		  COUNT(DISTINCT CASE WHEN ISNULL(A.RMDTN_PLN_STS_CD,'''')=''C''  THEN A.CLNT_RMDTN_PLN_KEY ELSE NULL END)''Closed'',
		  COUNT(DISTINCT A.CLNT_RMDTN_PLN_KEY )''UserCount''
 FROM	  '+ @schema+'.CLNT_RMDTN_PLN			  A
 JOIN	  '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM	  B
 ON		  A.CLNT_RMDTN_PLN_KEY					= B.CLNT_RMDTN_PLN_KEY
 JOIN	  '+ @schema+'.CLNT_VULN_INSTC			  C
 ON		  B.CLNT_VULN_INSTC_KEY					= C.CLNT_VULN_INSTC_KEY
 JOIN	  RMDTN_PLN_STS							  E
 ON		  A.RMDTN_PLN_STS_CD					= E.RMDTN_PLN_STS_CD
 LEFT JOIN	  USER_PRFL								  D
 ON		  A.RMDTN_OWNR_USER_ID					= D.USER_ID
 WHERE    C.CLNT_ENGMT_CD						= '''+@CLNT_ENGMT_CD + '''
 GROUP BY A.RMDTN_OWNR_USER_ID,D.FST_NM,D.LST_NM
'
--PRINT @Query
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
ALTER PROCEDURE [dbo].[List_PasswordResetUsers]
(
   @Days         INTEGER
)
AS
BEGIN

BEGIN TRY
SET NOCOUNT ON
					 SELECT USER_ID, USER_TYP_KEY, FST_NM + '' + LST_NM UserName ,EMAIL_ID,FST_NM,LST_NM
                     FROM	USER_PRFL     
                     WHERE  DATEDIFF(DAY,PSWD_RSET_DT,GETDATE()) >  @Days    
                     AND    ROW_STS_KEY = 1
                     
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

-- Sprint3

GO
CREATE PROCEDURE [dbo].[GetUserDetailsByRole]  
(  
       @APPL_ROLE_KEY INTEGER,
	   @USER_TYP_KEY  INTEGER
)  
AS  
BEGIN  
BEGIN TRY	
SET NOCOUNT ON  

  
SELECT A.[USER_ID] ID,A.USER_TYP_KEY,dbo.fnGetMasterLkpNameByID(A.USER_TYP_KEY) [User Type],B.ORG_NM [Organization Name],
	   A.ORG_KEY,FST_NM [First Name],MIDL_NM [Middle Name],LST_NM [Last Name],JOB_TITL_NM [Job Title],EMAIL_ID [EMAIL ID],
	   TEL_NBR [Phone Number],dbo.fnGetMasterLkpNameByID(A.ROW_STS_KEY)[Status], A.STS_COMMT_TXT,A.ROW_STS_KEY,DEPT_NM	
	     
FROM   USER_PRFL					A
JOIN   ORG							B
ON	   A.ORG_KEY				=	B.ORG_KEY
JOIN   USER_APPL_ROLE				C
ON	   A.USER_ID				=	C.USER_ID
JOIN   APPL_ROLE					D
ON	   C.APPL_ROLE_KEY			=	D.APPL_ROLE_KEY
WHERE  D.APPL_ROLE_KEY          =	@APPL_ROLE_KEY 
AND	   A.USER_TYP_KEY			=   @USER_TYP_KEY
 
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

-- Client Engagement User Screen Changes
GO

/******************************
       ** File: IRMaaSAdmin.SQL   
       ** Name: INS_CLNT_ENGMT_USER_ASGN
       ** Desc: This procedure insert data into CLNT_ENGMT_USER_ASGN table
       ** Auth: Prasad varma
       ** Date: 20/4/2016
       **************************
       ** Change History
       **************************
       ** PR   Date          Author                  Description     
       ** --   --------        -------                ------------------------------------
       ** 1    00/00/1999      xxxxx          

       **************************************/

       ALTER PROCEDURE [dbo].[INS_CLNT_ENGMT_USER_ASGN](
       @ROW_STS_KEY           INTEGER,
       @CLNT_ENGMT_CD         VARCHAR(30),
       @USER_ID               INTEGER,
	   @SRV_LST_CD			  VARCHAR(150), 
       @CREAT_USER_ID         INTEGER,
	   @USER_APPL_ROLE_KEY	  INTEGER,
	   @APPL_ROLE_KEY		  INTEGER
       
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON

                  INSERT CLNT_ENGMT_USER_ASGN(ROW_STS_KEY,CLNT_ENGMT_CD ,USER_ID,SECUR_SRVC_LIST_CD,CREAT_DT,CREAT_USER_ID,USER_APPL_ROLE_KEY,APPL_ROLE_KEY) VALUES
                     (@ROW_STS_KEY,@CLNT_ENGMT_CD,@USER_ID,@SRV_LST_CD,GETDATE(),@CREAT_USER_ID,@USER_APPL_ROLE_KEY,@APPL_ROLE_KEY)

                      SELECT @@ROWCOUNT AS RETVAL 

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
              
       END

	   

GO
ALTER PROCEDURE [dbo].[Get_ClntEngmtUserAsgnByID]
(
       @CLNT_ENGMT_CD	VARCHAR(30)
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

	SELECT  DISTINCT A.CLNT_ENGMT_USER_ASGN_KEY, A.ROW_STS_KEY, A.CLNT_ENGMT_CD, A.USER_ID,B.EMAIL_ID,
			A.CREAT_DT, A.CREAT_USER_ID,A.SECUR_SRVC_LIST_CD,B.USER_TYP_KEY,
			dbo.fnGetMasterLkpNameByID(B.USER_TYP_KEY) [User Type],
			[dbo].[fnGetUserNameByID](A.USER_ID) [User Name],B.ORG_KEY, ORG_NM,C.APPL_ROLE_KEY,C.APPL_ROLE_NM


	FROM    CLNT_ENGMT_USER_ASGN    A
	JOIN	USER_PRFL				B
	ON		A.USER_ID		  =		B.USER_ID
	JOIN	USER_APPL_ROLE			E
	ON		B.USER_ID		  =     E.USER_ID
	--AND		A.USER_APPL_ROLE_KEY=	E.USER_APPL_ROLE_KEY
	JOIN    APPL_ROLE				C
	ON		C.APPL_ROLE_KEY		=   E.APPL_ROLE_KEY
	AND		A.APPL_ROLE_KEY		=	E.APPL_ROLE_KEY
	JOIN	ORG						D
	ON      B.ORG_KEY		  =     D.ORG_KEY
	WHERE   CLNT_ENGMT_CD    =		@CLNT_ENGMT_CD


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

CREATE PROCEDURE [dbo].[DelClntEngmtUsrAssgnyID](
	   
	   @CLNT_ENGMT_USER_ASGN_KEY	INTEGER
      
       )

       AS
       BEGIN
              BEGIN TRY

              SET NOCOUNT ON


			  DELETE FROM CLNT_ENGMT_USER_ASGN  
			  WHERE  CLNT_ENGMT_USER_ASGN_KEY	=	@CLNT_ENGMT_USER_ASGN_KEY 
			
              SELECT @@ROWCOUNT AS RETVAL

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
              
       END

GO	   
CREATE PROCEDURE [dbo].[GetUserAppRoleKey]
(
       @USER_ID	INTEGER,
	   @APPL_ROLE_KEY	INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

	SELECT  DISTINCT E.USER_APPL_ROLE_KEY,[dbo].[fnGetUserNameByID](B.USER_ID) [User Name],B.ORG_KEY, ORG_NM,C.APPL_ROLE_KEY,C.APPL_ROLE_NM


	FROM    USER_PRFL				B
	JOIN	USER_APPL_ROLE			E
	ON		B.USER_ID		  =     E.USER_ID
	JOIN    APPL_ROLE				C
	ON		C.APPL_ROLE_KEY	  =     E.APPL_ROLE_KEY
	JOIN	ORG						D
	ON      B.ORG_KEY		  =     D.ORG_KEY
	WHERE   E.USER_ID	      =		@USER_ID
	AND		E.APPL_ROLE_KEY	  =		@APPL_ROLE_KEY
--	AND		D.ORG_KEY		  =		CASE WHEN @OrgID=0 THEN D.ORG_KEY ELSE @OrgID END


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

ALTER PROCEDURE [dbo].[LIST_UserClntSrvcAssn]
(

	@CLNT_ENGMT_CD  VARCHAR(30),
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

--IF @USERTYPEID = 16
--BEGIN
SELECT		C.CLNT_ENGMT_CD,A.SECUR_SRVC_CD,A.SECUR_SRVC_NM,B.SRVC_EST_STRT_DT ,B.SRVC_EST_END_DT,
			[dbo].[fnGetMasterLkpNameByID](Z.USER_TYP_KEY) UserType,Z.ORG_TYP_KEY,Z.ORG_NM,Z.ORG_KEY,
			ISNULL(Z.USER_CLNT_SRVC_ASGN_KEY,0)USER_CLNT_SRVC_ASGN_KEY,ISNULL(Z.FST_NM,'') USERNAME,
			ISNULL(Z.USER_ID,0) USER_ID,ISNULL(Z.ROW_STS_KEY,0) ROW_STS_KEY,
			Z.USER_STRT_DT,Z.USER_END_DT,Z.USER_TYP_KEY,Z.APPL_ROLE_KEY,Z.APPL_ROLE_NM
FROM		SECUR_SRVC				  A
JOIN		CLNT_SECUR_SRVC_ENGMT	  B
ON			A.SECUR_SRVC_CD			= B.SECUR_SRVC_CD
JOIN		CLNT_ENGMT				  C
ON			B.CLNT_ENGMT_CD			= C.CLNT_ENGMT_CD
 JOIN	
(
SELECT      E.CLNT_ENGMT_CD,E.SECUR_SRVC_CD,G.USER_TYP_KEY,E.USER_STRT_DT,E.USER_END_DT,E.USER_CLNT_SRVC_ASGN_KEY,
			ISNULL(G.FST_NM+' '+G.LST_NM,'') FST_NM,D.ORG_NM,D.ORG_KEY,D.ORG_TYP_KEY,G.USER_ID,E.ROW_STS_KEY,I.APPL_ROLE_KEY,I.APPL_ROLE_NM
FROM		USER_CLNT_SRVC_ASGN		  E
JOIN		USER_PRFL				  G
ON			E.USER_ID				= G.USER_ID
JOIN		USER_APPL_ROLE			  H
ON			G.USER_ID				= H.USER_ID
JOIN		APPL_ROLE				  I
ON			H.APPL_ROLE_KEY			= I.APPL_ROLE_KEY
AND			E.APPL_ROLE_KEY			= I.APPL_ROLE_KEY
AND			E.USER_APPL_ROLE_KEY	= H.USER_APPL_ROLE_KEY
JOIN		ORG						  D
ON			G.ORG_KEY				= D.ORG_KEY
AND			CONVERT(VARCHAR(20),E.USER_STRT_DT,101)  = CASE WHEN @USER_STRT_DT = '' THEN CONVERT(VARCHAR(20),E.USER_STRT_DT,101) ELSE @USER_STRT_DT END
AND			CONVERT(VARCHAR(20),E.USER_END_DT,101)	 = CASE WHEN @USER_END_DT  = '' THEN CONVERT(VARCHAR(20),E.USER_END_DT,101)  ELSE @USER_END_DT END
AND         G.USER_TYP_KEY			= CASE WHEN @USER_TYP_KEY = 0 THEN G.USER_TYP_KEY ELSE @USER_TYP_KEY END
AND			ISNULL(G.FST_NM+' '+G.LST_NM,'')  LIKE CASE WHEN ISNULL(@USERNAME,'') = '' THEN ISNULL(G.FST_NM+' '+G.LST_NM,'') ELSE '%' + @USERNAME + '%' END)Z
ON			C.CLNT_ENGMT_CD = Z.CLNT_ENGMT_CD
AND			A.SECUR_SRVC_CD	= Z.SECUR_SRVC_CD
WHERE		C.CLNT_ENGMT_CD	= @CLNT_ENGMT_CD
--AND			Z.APPL_ROLE_NM IN('Optum Engagement Analyst','Partner Engagement Lead','Partner Engagement Analyst')
-- AND			F.USER_ID				= @USER_ID




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
ALTER PROCEDURE [dbo].[GetUserDetailsByRole]  
(  
       @APPL_ROLE_KEY INTEGER,
	   @USER_TYP_KEY  INTEGER,
	   @OrgID		  INTEGER
)  
AS  
BEGIN  
BEGIN TRY	
SET NOCOUNT ON  

  
SELECT A.[USER_ID] ID,A.USER_TYP_KEY,dbo.fnGetMasterLkpNameByID(A.USER_TYP_KEY) [User Type],B.ORG_NM [Organization Name],
	   A.ORG_KEY,FST_NM [First Name],MIDL_NM [Middle Name],LST_NM [Last Name],JOB_TITL_NM [Job Title],EMAIL_ID [EMAIL ID],
	   TEL_NBR [Phone Number],dbo.fnGetMasterLkpNameByID(A.ROW_STS_KEY)[Status], A.STS_COMMT_TXT,A.ROW_STS_KEY,DEPT_NM,
	   D.APPL_ROLE_KEY,D.APPL_ROLE_NM
	     
FROM   USER_PRFL					A
JOIN   ORG							B
ON	   A.ORG_KEY				=	B.ORG_KEY
JOIN   USER_APPL_ROLE				C
ON	   A.USER_ID				=	C.USER_ID
JOIN   APPL_ROLE					D
ON	   C.APPL_ROLE_KEY			=	D.APPL_ROLE_KEY
WHERE  D.APPL_ROLE_KEY          =	@APPL_ROLE_KEY 
AND	   A.USER_TYP_KEY			=   @USER_TYP_KEY
AND	   B.ORG_KEY				=	CASE WHEN @OrgID=0 THEN B.ORG_KEY ELSE @ORGID END
 
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
/****** Object:  StoredProcedure [dbo].[LIST_ManageEngagements]    Script Date: 5/16/2017 7:12:21 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
/******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: LIST_ManageEngagements  
 ** Desc: This procedure get data from CLNT_ENGMT,ORG tables
** Auth: Prasad varma  
 ** Date: 3/5/2016  
 **************************  
 ** Change History  
 **************************  


** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  

ALTER PROCEDURE [dbo].[LIST_ManageEngagements]
(
	  @UserID	 INTEGER,
	  @Flag		 VARCHAR(1)
)

      AS
      BEGIN
            BEGIN TRY

            SET NOCOUNT ON

			IF	@Flag = 'C'
			BEGIN
                  SELECT            A.CLNT_ENGMT_CD,A.CLNT_ENGMT_CD [Engagement Code],B.PAR_ORG_KEY,
									dbo.fnGetParentOrgNameID(B.PAR_ORG_KEY) [Parent Client Name],B.ORG_NM [Client Name],
                                    A.AGR_DT [Agreement Date],A.ENGMT_STRT_DT [Estimated Start Date],
                                    A.ENGMT_EST_END_DT [Estimated End Date],A.ENGMT_COMMT [Status],'' [Action],
									A.CREAT_DT,A.UPDT_DT
                  FROM              CLNT_ENGMT					  A
                  JOIN              ORG                           B
                  ON                A.CLNT_ORG_KEY    =   B.ORG_KEY
                  WHERE            	A.ROW_STS_KEY <> 3
				  AND				B.ROW_STS_KEY  = 1
                  ORDER BY    CASE WHEN ISNULL(A.UPDT_DT,'') = '' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
			END

			IF	@Flag = 'L'
			BEGIN
			      SELECT * FROM (
                  SELECT            DISTINCT A.CLNT_ENGMT_CD,A.CLNT_ENGMT_CD [Engagement Code],B.PAR_ORG_KEY,
									dbo.fnGetParentOrgNameID(B.PAR_ORG_KEY) [Parent Client Name],B.ORG_NM [Client Name],
                                    A.AGR_DT [Agreement Date],A.ENGMT_STRT_DT [Estimated Start Date],
                                    A.ENGMT_EST_END_DT [Estimated End Date],CONVERT(VARCHAR(MAX),A.ENGMT_COMMT) [Status],'' [Action],
									A.CREAT_DT,A.UPDT_DT
                  FROM              CLNT_ENGMT					  A
                  JOIN              ORG                           B
                  ON                A.CLNT_ORG_KEY			  =   B.ORG_KEY
				  JOIN				CLNT_ENGMT_USER_ASGN			  C
				  ON				A.CLNT_ENGMT_CD			  =	  C.CLNT_ENGMT_CD
                  WHERE				C.USER_ID				  =   @UserID
				  AND				B.ROW_STS_KEY			  =  1
				  AND				A.ROW_STS_KEY			  <> 3
UNION


                  SELECT            DISTINCT A.CLNT_ENGMT_CD,A.CLNT_ENGMT_CD [Engagement Code],B.PAR_ORG_KEY,
									dbo.fnGetParentOrgNameID(B.PAR_ORG_KEY) [Parent Client Name],B.ORG_NM [Client Name],
                                    A.AGR_DT [Agreement Date],A.ENGMT_STRT_DT [Estimated Start Date],
                                    A.ENGMT_EST_END_DT [Estimated End Date],CONVERT(VARCHAR(MAX),A.ENGMT_COMMT) [Status],'' [Action],
									A.CREAT_DT,A.UPDT_DT
                  FROM              CLNT_ENGMT					  A
                  JOIN              ORG                           B
                  ON                A.CLNT_ORG_KEY			  =   B.ORG_KEY
				  JOIN				USER_CLNT_SRVC_ASGN			  C
				  ON				A.CLNT_ENGMT_CD			  =	  C.CLNT_ENGMT_CD
                  WHERE				C.USER_ID				  =   @UserID
				  AND				B.ROW_STS_KEY			  =  1
				  AND				A.ROW_STS_KEY			  <> 3)Z
ORDER BY    CASE WHEN ISNULL(UPDT_DT,'') = '' THEN CREAT_DT ELSE UPDT_DT END DESC
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
            
      END

	  
GO
ALTER PROCEDURE [dbo].[RMDTN_INS_CLNT_RISK_RGST]
(
       @CLNT_RISK_RGST_KEY			INTEGER,
	   @ROW_STS_KEY					INTEGER,
	   @CLNT_RMDTN_PLN_KEY			INTEGER,	
	   @ADJ_SEV_CD					VARCHAR(3),
	   @ENT_USER_ID					INTEGER,
	   @ENT_DT						DATETIME,
	   @COMP_CTL_TXT				TEXT,
	   @NTF_DT						DATETIME,
	   @ACPT_USER_ID				INTEGER,
	   @ACPT_DT						DATETIME,
	   @ACPT_USER_SGN_TXT			VARCHAR(150),
	   @RISK_RSPN_CD				VARCHAR(1),
	   @RISK_JSTFY_TXT				TEXT,
	   @RISK_RGST_ITM_TOT_CNT		INTEGER,
	   @USER_ID						INTEGER,
       @Flag						VARCHAR(1),    
       @schema						VARCHAR(50)       

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)
DECLARE @id INTEGER

       IF @Flag = 'D'
       BEGIN
              
        SET   @Query = 'DELETE FROM '+ @schema+'.CLNT_RISK_RGST
                       WHERE CLNT_RISK_RGST_KEY  =   '+ CONVERT(VARCHAR,@CLNT_RISK_RGST_KEY) +''

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
  
		SET          @Query =	'INSERT '+ @schema+'.CLNT_RISK_RGST (ROW_STS_KEY,CLNT_RMDTN_PLN_KEY,ADJ_SEV_CD,
								ENT_USER_ID,ENT_DT,COMP_CTL_TXT,NTF_DT,ACPT_USER_ID,ACPT_DT,ACPT_USER_SGN_TXT,
								RISK_RSPN_CD,RISK_JSTFY_TXT,RISK_RGST_ITM_TOT_CNT,CREAT_DT,CREAT_USER_ID) VALUES

		('+CONVERT(VARCHAR, @ROW_STS_KEY)+',
		'+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
		 '+ isnull('''' + convert(varchar(3),@ADJ_SEV_CD) + '''','null') + ',
		 '+ isnull('''' + convert(varchar,@ENT_USER_ID) + '''','null') + ',
		 '+ isnull('''' + convert(varchar(50),@ENT_DT) + '''','null') + ',
		  '+ isnull('''' + replace(convert(varchar(max),@COMP_CTL_TXT),'''','''''') + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@NTF_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar,@ACPT_USER_ID) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@ACPT_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(150),@ACPT_USER_SGN_TXT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RISK_RSPN_CD) + '''','null') + ',
		  '+ isnull('''' + replace(convert(varchar(max),@RISK_JSTFY_TXT),'''','''''') + '''','null') + ',
		  '+ isnull('''' + convert(varchar,@RISK_RGST_ITM_TOT_CNT) + '''','null') + ',
		  '+'GETDATE()'+',
		  '+CONVERT(VARCHAR,@USER_ID)+') ; SELECT @@IDENTITY' 

		  PRINT @Query
		--  PRINT @id
		--  EXECUTE sp_executesql @Query, N'@Id INTEGER OUTPUT', @NewId OUTPUT
		  -- SELECT @Result = @id
		  DECLARE @Result AS Table (RetValue int)
		  INSERT INTO @Result EXECUTE (@Query)
		  SELECT RetValue FROM @Result
		--  SELECT @id AS RETVAL
                       
       END
	   
       IF @Flag = 'U'
       BEGIN
            
               SET		@Query = 'UPDATE  '+ @schema+'.CLNT_RISK_RGST
               SET      ACPT_USER_ID			= '+ isnull('''' + convert(varchar,@ACPT_USER_ID) + '''','null') + ',
						ACPT_DT					= '+'GETDATE()'+',
						ACPT_USER_SGN_TXT		= '+ isnull('''' + convert(varchar(150),@ACPT_USER_SGN_TXT) + '''','null') + ',
						RISK_RSPN_CD			= '+ isnull('''' + convert(varchar(50),@RISK_RSPN_CD) + '''','null') + ',
						RISK_JSTFY_TXT			=  '+ isnull('''' + replace(convert(varchar(max),@RISK_JSTFY_TXT),'''','''''') + '''','null') + '
               WHERE	CLNT_RISK_RGST_KEY		=  '+CONVERT(VARCHAR,@CLNT_RISK_RGST_KEY)+ +''
              
              
               EXECUTE (@Query)
               SELECT @@ROWCOUNT AS RETVALS
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

ALTER PROCEDURE [dbo].[RMDTN_UpdateItemsCount]
(
	@KEY					INTEGER,
	@TOT_CNT				INTEGER,
	@Userid					INTEGER,
	@schema					VARCHAR(50),
	@Flag					VARCHAR(2) -- RM(Remediation) RG(Registry)

	

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON	
	DECLARE @Query VARCHAR(max)

	IF @Flag		=	'RM'
	BEGIN	
	SET		@Query  =	'UPDATE '+ @schema+'.CLNT_RMDTN_PLN
						 SET	ASSOC_VULN_TOT_CNT		= '+CONVERT(VARCHAR,@TOT_CNT)+ ',
								UPDT_DT					=	'+'GETDATE()'+',
								UPDT_USER_ID			=	'+CONVERT(VARCHAR,@Userid)+'
						 WHERE	CLNT_RMDTN_PLN_KEY		=  '+CONVERT(VARCHAR,@KEY)+''
	END
	
	IF @Flag		=	'RG'
	BEGIN	
	SET		@Query  =	'UPDATE '+ @schema+'.CLNT_RISK_RGST
						 SET	RISK_RGST_ITM_TOT_CNT	= '+CONVERT(VARCHAR,@TOT_CNT)+ ',
								UPDT_DT					=	'+'GETDATE()'+',
								UPDT_USER_ID			=	'+CONVERT(VARCHAR,@Userid)+'
						 WHERE	CLNT_RISK_RGST_KEY		=  '+CONVERT(VARCHAR,@KEY)+''
	END
	
 	IF @Flag		=	'CD'
	BEGIN	
	SET		@Query  =	'UPDATE '+ @schema+'.CLNT_RMDTN_PLN
						 SET	ASSOC_VULN_CMPL_CNT		=   (SELECT COUNT(CLNT_VULN_INSTC_KEY) FROM '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM WHERE CLNT_RMDTN_PLN_KEY ='+CONVERT(VARCHAR,@KEY)+' AND VULN_RMDTN_STS_CD IN(''C'')),
								UPDT_DT					=	'+'GETDATE()'+',
								UPDT_USER_ID			=	'+CONVERT(VARCHAR,@Userid)+'
						 WHERE	CLNT_RMDTN_PLN_KEY		=   '+CONVERT(VARCHAR,@KEY)+''
	END

	IF @Flag		=	'RI'-- Remediation Items
	BEGIN	
	SET		@Query  =	'UPDATE A

						 SET	A.VULN_RMDTN_STS_CD		=  ''A'',
								UPDT_DT					=	'+'GETDATE()'+',
								UPDT_USER_ID			=	'+CONVERT(VARCHAR,@Userid)+'

						 FROM  '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM A 
						 JOIN  '+ @schema+'.CLNT_RISK_RGST			B
						 ON		A.CLNT_RMDTN_PLN_KEY			 =  B.CLNT_RMDTN_PLN_KEY
						 JOIN  '+ @schema+'.CLNT_RISK_RGST_ITM		C
						 ON		B.CLNT_RISK_RGST_KEY			=   C.CLNT_RISK_RGST_KEY
						 AND	A.CLNT_VULN_INSTC_KEY			=	C.CLNT_VULN_INSTC_KEY
						 WHERE	C.CLNT_RISK_RGST_KEY			=  '+CONVERT(VARCHAR,@KEY)+''
	END
	--PRINT    @Query
	EXECUTE (@Query)
	 SELECT @@ROWCOUNT AS RETVALS

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

/******************************
	** File: IRMaaSAdmin.SQL   
	** Name: INS_CLNT_SECUR_SRVC_ENGMTR
	** Desc: This procedure insert data into CLNT_SECUR_SRVC_ENGMTR table
	** Auth: Prasad varma
	** Date: 20/4/2016
	**************************
	** Change History
	**************************
	** PR   Date	        Author                  Description	
	** --   --------        -------                ------------------------------------
	** 1    00/00/1999      xxxxx          

	**************************************/
	

	ALTER PROCEDURE [dbo].[INS_USER_CLNT_SRVC_ASGN](
	@ROW_STS_KEY				 INTEGER,
	@USER_ID					 INTEGER,
	@USER_STRT_DT				 DATETIME,
	@USER_END_DT				 DATETIME,
	@CREAT_USER_ID				 INTEGER,
	@SECUR_SRVC_CD				 VARCHAR(10),
	@CLNT_ENGMT_CD				 VARCHAR(30),
	@USER_APPL_ROLE_KEY			 INTEGER,
	@APPL_ROLE_KEY				 INTEGER
	
	)

	AS
	BEGIN
		BEGIN TRY

		SET NOCOUNT ON

		    INSERT USER_CLNT_SRVC_ASGN(ROW_STS_KEY	,USER_ID,USER_STRT_DT,USER_END_DT,CREAT_DT,CREAT_USER_ID,SECUR_SRVC_CD,CLNT_ENGMT_CD,USER_APPL_ROLE_KEY,APPL_ROLE_KEY) VALUES
			(@ROW_STS_KEY,@USER_ID,@USER_STRT_DT,@USER_END_DT,GETDATE(),@CREAT_USER_ID,@SECUR_SRVC_CD,@CLNT_ENGMT_CD,@USER_APPL_ROLE_KEY,@APPL_ROLE_KEY)

			 SELECT @@ROWCOUNT AS RETVAL 

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
		
	END;

	
GO
ALTER PROCEDURE [dbo].[CreateSchemaTables]
(
@SchemaName VARCHAR(50),
@ORG_KEY     INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

DECLARE @Query1 VARCHAR(max)
DECLARE @Query2 VARCHAR(max)
DECLARE @Query3 VARCHAR(max)
DECLARE @Query4 VARCHAR(max)
DECLARE @Query5 VARCHAR(max)
DECLARE @Query6 VARCHAR(max)
DECLARE @Query7 VARCHAR(max)
DECLARE @Query8 VARCHAR(max)

SET @Query1= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_VULN_INSTC'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.CLNT_VULN_INSTC 
(
       [CLNT_VULN_INSTC_KEY] [int] IDENTITY(1,1) NOT NULL,
       [ROW_STS_KEY] [int] NOT NULL,
       [ORG_KEY] [int] NOT NULL,
       [CLNT_ENGMT_CD] [varchar](30) NOT NULL,
       [SECUR_SRVC_CD] [varchar](10) NOT NULL,
       [VULN_SRC_KEY] [int] NOT NULL,
       [VULN_INSTC_STS_CD] [varchar](3) NOT NULL,
       [VULN_SEV_CD] [varchar](3) NULL,
       [VULN_IMP_CD] [varchar](3) NULL,
       [RISK_PRBL_CD] [varchar](3) NULL,
       [RMDTN_CST_EFFRT_CD] [varchar](3) NULL,
       [VULN_CATGY_CD] [varchar](10) NULL,
       [OWASP_TOP_10_KEY] [int] NULL,
       [CVE_ID] [varchar](25) NULL,
       [OS_KEY] [int] NULL,
       [SRC_VULN_SCAN_ID] [varchar](150) NULL,
       [SRC_VULN_SCAN_STRT_DT] [datetime] NULL,
       [SRC_VULN_SCAN_END_DT] [datetime] NULL,
       [SRC_VULN_INSTC_ID] [varchar](150) NULL,
       [SRC_VULN_ID] [varchar](150) NULL,
       [VULN_NM] [varchar](255) NOT NULL,
       [VULN_DESC] [text] NULL,
       [VULN_CREAT_DT] [datetime] NOT NULL,
       [IPADR] [varchar](39) NULL,
       [PORT_NBR] [int] NULL,
       [SRC_ADVS_TXT] [varchar](1024) NULL,
       [SRC_VULN_BAS_SCOR] [decimal](10, 2) NULL,
       [VULN_BAS_SCOR] [decimal](10, 2) NULL,
       [VULN_IMP_SUB_SCOR] [decimal](10, 2) NULL,
       [VULN_EXPLT_SUB_SCOR] [decimal](10, 2) NULL,
       [VULN_TMPRL_SCOR] [decimal](10, 2) NULL,
       [VULN_ENV_SCOR] [decimal](10, 2) NULL,
       [VULN_OVALL_SCOR] [decimal](10, 2) NULL,
       [VULN_VCTR_TXT] [varchar](100) NULL,
       [NTWK_NM] [varchar](150) NULL,
       [PRTCL_NM] [varchar](255) NULL,
       [HST_NM] [varchar](150) NULL,
       [DOM_NM] [varchar](150) NULL,
       [SFTW_NM] [varchar](150) NULL,
       [APPL_URL] [nvarchar](2000) NULL,
       [NETBIOS_NM] [varchar](150) NULL,
       [MAC_ADR_NM] [varchar](150) NULL,
       [VULN_TECH_COMMT_TXT] [text] NULL,
       [VULN_IMP_COMMT_TXT] [text] NULL,
       [RECOM_COMMT_TXT] [text] NULL,
       [ROOT_CAUS_COMMT_TXT] [text] NULL,
       [APPL_FL_UPLOAD_LOG_KEY] [int] NULL,
       [CREAT_DT] [datetime] NOT NULL,
       [CREAT_USER_ID] [int] NOT NULL,
       [UPDT_DT] [datetime] NULL,
       [UPDT_USER_ID] [int] NULL,
	   [VULN_VLD_DT] [datetime]  NULL,
	   [VULN_CLOS_DT] [datetime]  NULL,
	   [CLOS_RSN_TXT] text NULL,
CONSTRAINT [PK_CLNT_VULN_INSTC] PRIMARY KEY CLUSTERED 
(
       [CLNT_VULN_INSTC_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY] 

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([APPL_FL_UPLOAD_LOG_KEY])
REFERENCES [dbo].[APPL_FL_UPLOAD_LOG] ([APPL_FL_UPLOAD_LOG_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([CVE_ID])
REFERENCES [dbo].[CVE] ([CVE_ID])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([ORG_KEY])
REFERENCES [dbo].[ORG] ([ORG_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([OS_KEY])
REFERENCES [dbo].[OS] ([OS_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([RISK_PRBL_CD])
REFERENCES [dbo].[RISK_PRBL] ([RISK_PRBL_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([RMDTN_CST_EFFRT_CD])
REFERENCES [dbo].[RMDTN_CST_EFFRT] ([RMDTN_CST_EFFRT_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([VULN_CATGY_CD])
REFERENCES [dbo].[VULN_CATGY] ([VULN_CATGY_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([VULN_IMP_CD])
REFERENCES [dbo].[VULN_IMP] ([VULN_IMP_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([VULN_INSTC_STS_CD])
REFERENCES [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([VULN_SEV_CD])
REFERENCES [dbo].[VULN_SEV] ([VULN_SEV_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([VULN_SRC_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_INSTC]  WITH CHECK ADD FOREIGN KEY([CLNT_ENGMT_CD], [SECUR_SRVC_CD])
REFERENCES [dbo].[CLNT_SECUR_SRVC_ENGMT] ([CLNT_ENGMT_CD], [SECUR_SRVC_CD])  
END
ELSE
BEGIN
SELECT -1 Retval
END 
'


--  PRINT  (@query1)
EXECUTE (@Query1)

SET @Query2= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_VULN_SECUR_CTL'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_VULN_SECUR_CTL](
       [CLNT_VULN_INSTC_KEY] [int] NOT NULL,
       [REG_CMPLN_CD] [varchar](20) NOT NULL,
       [REG_CMPLN_VER] [varchar](20) NOT NULL,
       [SECUR_CTL_CD] [varchar](20) NOT NULL,
       [ROW_STS_KEY] [int] NOT NULL,
       [CREAT_DT] [datetime] NULL,
       [CREAT_USER_ID] [int] NULL,
       [UPDT_DT] [datetime] NULL,
       [UPDT_USER_ID] [int] NULL,
CONSTRAINT [PK_CLNT_VULN_SECUR_CTL] PRIMARY KEY CLUSTERED 
(
       [CLNT_VULN_INSTC_KEY] ASC,
       [REG_CMPLN_CD] ASC,
       [REG_CMPLN_VER] ASC,
       [SECUR_CTL_CD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_SECUR_CTL]  WITH CHECK ADD FOREIGN KEY([CLNT_VULN_INSTC_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_VULN_INSTC] ([CLNT_VULN_INSTC_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_SECUR_CTL]  WITH CHECK ADD FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_SECUR_CTL]  WITH CHECK ADD FOREIGN KEY([REG_CMPLN_CD], [REG_CMPLN_VER], [SECUR_CTL_CD])
REFERENCES [dbo].[SECUR_CTL] ([REG_CMPLN_CD], [REG_CMPLN_VER], [SECUR_CTL_CD]) 
SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query2)

SET @Query3= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_ROADMAP_FACT'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT](
       [CLNT_ROADMAP_KEY] [int] NOT NULL,
       [ROW_STS_KEY] [int] NULL,
       [ORG_KEY] [int] NULL,
       [CLNT_ENGMT_CD] [varchar](30) NULL,
       [CSA_DOM_CD] [varchar](10) NOT NULL,
       [VULN_CATGY_CD] [varchar](10) NOT NULL,
       [VULN_SEV_CD] [varchar](3) NOT NULL,
       [RMDTN_CST_EFFRT_CD] [varchar](3) NOT NULL,
       [ROADMAP_TMLN_CD] [char](1) NOT NULL,
       [ROADMAP_EFF_DT] [datetime] NULL,
       [ROADMAP_PUBL_DT] [datetime] NULL,
       [VULN_CNT] [int] NULL,
       [ROADMAP_COMMT] [varchar](5000) NULL,
       [CREAT_DT] [datetime] NOT NULL,
       [CREAT_USER_ID] [int] NOT NULL,
       [UPDT_DT] [datetime] NULL,
       [UPDT_USER_ID] [int] NULL,
CONSTRAINT [PK_CLNT_ROADMAP_FACT] PRIMARY KEY CLUSTERED 
(
       [CLNT_ROADMAP_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_ENGMT_126] FOREIGN KEY([CLNT_ENGMT_CD])
REFERENCES [dbo].[CLNT_ENGMT] ([CLNT_ENGMT_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_CLNT_ENGMT_126]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_CSA_REF_ARCH_DOM_141] FOREIGN KEY([CSA_DOM_CD])
REFERENCES [dbo].[CSA_REF_ARCH_DOM] ([CSA_DOM_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_CSA_REF_ARCH_DOM_141]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_MSTR_LKP_131] FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_MSTR_LKP_131]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_ORG_125] FOREIGN KEY([ORG_KEY])
REFERENCES [dbo].[ORG] ([ORG_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_ORG_125]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_RMDTN_CST_EFFRT_128] FOREIGN KEY([RMDTN_CST_EFFRT_CD])
REFERENCES [dbo].[RMDTN_CST_EFFRT] ([RMDTN_CST_EFFRT_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_RMDTN_CST_EFFRT_128]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_ROADMAP_TMLN_130] FOREIGN KEY([ROADMAP_TMLN_CD])
REFERENCES [dbo].[ROADMAP_TMLN] ([ROADMAP_TMLN_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_ROADMAP_TMLN_130]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_VULN_CATGY_129] FOREIGN KEY([VULN_CATGY_CD])
REFERENCES [dbo].[VULN_CATGY] ([VULN_CATGY_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_VULN_CATGY_129]

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT]  WITH CHECK ADD  CONSTRAINT [FK_VULN_SEV_127] FOREIGN KEY([VULN_SEV_CD])
REFERENCES [dbo].[VULN_SEV] ([VULN_SEV_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_ROADMAP_FACT] CHECK CONSTRAINT [FK_VULN_SEV_127]

SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query3)

SET @Query4= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_RMDTN_PLN'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN](
	[CLNT_RMDTN_PLN_KEY] [int] IDENTITY(1,1) NOT NULL,
	[ROW_STS_KEY] [int] NOT NULL,
	[ORG_KEY] [int] NOT NULL,
	[CLNT_ENGMT_CD] [varchar](30) NOT NULL,
	[RMDTN_OWNR_USER_ID] [int]  NULL,
	[RMDTN_PLN_STS_CD] [char](1) NOT NULL,
	[ADJ_RMDTN_PLN_SEV_CD] varchar(3)  NULL ,
	[RMDTN_PLN_SEV_CD]     varchar(3)  NULL ,
	[RMDTN_PLN_NM] [varchar](150) NOT NULL,
	[RMDTN_PLN_DESC] [text] NULL,
	[RMDTN_PLN_CREAT_DT] [datetime] NOT NULL,
	[RMDTN_PLN_NTF_DT] [datetime] NULL,
	[RMDTN_PLN_STRT_DT] [datetime] NULL,
	[RMDTN_PLN_DUE_DT] [datetime] NULL,
	[RMDTN_PLN_CLOS_DT] [datetime] NULL,
	[ASSOC_VULN_TOT_CNT] [int] NOT NULL,
	[ASSOC_VULN_CMPL_CNT] [int] NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_CLNT_RMDTN_PLN] PRIMARY KEY CLUSTERED 
(
	[CLNT_RMDTN_PLN_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_ENGMT_142] FOREIGN KEY([CLNT_ENGMT_CD])
REFERENCES [dbo].[CLNT_ENGMT] ([CLNT_ENGMT_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN] CHECK CONSTRAINT [FK_CLNT_ENGMT_142]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN]  WITH CHECK ADD  CONSTRAINT [FK_MSTR_LKP_150] FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN] CHECK CONSTRAINT [FK_MSTR_LKP_150]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN]  WITH CHECK ADD  CONSTRAINT [FK_ORG_143] FOREIGN KEY([ORG_KEY])
REFERENCES [dbo].[ORG] ([ORG_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN] CHECK CONSTRAINT [FK_ORG_143]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN]  WITH CHECK ADD  CONSTRAINT [FK_RMDTN_PLN_STS_159] FOREIGN KEY([RMDTN_PLN_STS_CD])
REFERENCES [dbo].[RMDTN_PLN_STS] ([RMDTN_PLN_STS_CD])

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN] CHECK CONSTRAINT [FK_RMDTN_PLN_STS_159]


ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN]  WITH CHECK ADD  CONSTRAINT [FK_USER_PRFL_156] FOREIGN KEY([RMDTN_OWNR_USER_ID])
REFERENCES [dbo].[USER_PRFL] ([USER_ID])


ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN] CHECK CONSTRAINT [FK_USER_PRFL_156]
go

CREATE NONCLUSTERED INDEX IF7_CLNT_RMDTN_PLN ON '+ @SchemaName+ '.CLNT_RMDTN_PLN
( 
	RMDTN_PLN_SEV_CD      ASC
)
go

CREATE NONCLUSTERED INDEX IF8_CLNT_RMDTN_PLN ON '+ @SchemaName+ '.CLNT_RMDTN_PLN
( 
	ADJ_RMDTN_PLN_SEV_CD  ASC
)
go
ALTER TABLE '+ @SchemaName+ '.CLNT_RMDTN_PLN	ADD CONSTRAINT FK_RMDTN_PLN_STS_159 FOREIGN KEY (RMDTN_PLN_STS_CD) REFERENCES RMDTN_PLN_STS(RMDTN_PLN_STS_CD)
go

ALTER TABLE '+ @SchemaName+ '.CLNT_RMDTN_PLN	ADD CONSTRAINT FK_VULN_SEV_182 FOREIGN KEY (RMDTN_PLN_SEV_CD) REFERENCES VULN_SEV(VULN_SEV_CD)

SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query4)

SET @Query5= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_VULN_RMDTN_PLN_ITM'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM](
	[CLNT_VULN_INSTC_KEY] [int] NOT NULL,
	[CLNT_RMDTN_PLN_KEY] [int] NOT NULL,
	[ROW_STS_KEY] [int] NOT NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_CLNT_VULN_RMDTN_PLN_ITM] PRIMARY KEY CLUSTERED 
(
	[CLNT_VULN_INSTC_KEY] ASC,
	[CLNT_RMDTN_PLN_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_RMDTN_PLN_149] FOREIGN KEY([CLNT_RMDTN_PLN_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_RMDTN_PLN] ([CLNT_RMDTN_PLN_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM] CHECK CONSTRAINT [FK_CLNT_RMDTN_PLN_149]

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_VULN_INSTC_148] FOREIGN KEY([CLNT_VULN_INSTC_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_VULN_INSTC] ([CLNT_VULN_INSTC_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM] CHECK CONSTRAINT [FK_CLNT_VULN_INSTC_148]

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM]  WITH CHECK ADD  CONSTRAINT [FK_MSTR_LKP_160] FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])

ALTER TABLE '+ @SchemaName+ '.[CLNT_VULN_RMDTN_PLN_ITM] CHECK CONSTRAINT [FK_MSTR_LKP_160]



SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query5)


SET @Query6= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_RMDTN_PLN_COMMT'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN_COMMT](
	[CLNT_RMDTN_PLN_COMMT_KEY] [int] IDENTITY(1,1) NOT NULL,
	[CLNT_RMDTN_PLN_KEY] [int] NOT NULL,
	[ENT_DT] [datetime] NOT NULL,
	[COMMT_TXT] [text] NOT NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_CLNT_RMDTN_PLN_COMMT] PRIMARY KEY CLUSTERED 
(
	[CLNT_RMDTN_PLN_COMMT_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN_COMMT]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_RMDTN_PLN_147] FOREIGN KEY([CLNT_RMDTN_PLN_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_RMDTN_PLN] ([CLNT_RMDTN_PLN_KEY])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RMDTN_PLN_COMMT] CHECK CONSTRAINT [FK_CLNT_RMDTN_PLN_147]

SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query6)

SET @Query7= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_RISK_RGST'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST](
	[CLNT_RISK_RGST_KEY] [int] IDENTITY(1,1) NOT NULL,
	[ROW_STS_KEY] [int] NOT NULL,
	[CLNT_RMDTN_PLN_KEY] [int] NULL,
	[ADJ_SEV_CD] [varchar](3) NOT NULL,
	[ENT_USER_ID] [int] NOT NULL,
	[ENT_DT] [datetime] NOT NULL,
	[COMP_CTL_TXT] [text] NOT NULL,
	[NTF_DT] [datetime] NULL,
	[ACPT_USER_ID] [int] NULL,
	[ACPT_DT] [datetime] NULL,
	[ACPT_USER_SGN_TXT] [varchar](150) NULL,
	[RISK_RSPN_CD] [char](1) NULL,
	[RISK_JSTFY_TXT] [text] NULL,
	[RISK_RGST_ITM_TOT_CNT] [int] NOT NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_CLNT_RISK_RGST] PRIMARY KEY CLUSTERED 
(
	[CLNT_RISK_RGST_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_RMDTN_PLN_172] FOREIGN KEY([CLNT_RMDTN_PLN_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_RMDTN_PLN] ([CLNT_RMDTN_PLN_KEY])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_CLNT_RMDTN_PLN_172]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_MSTR_LKP_167] FOREIGN KEY([ROW_STS_KEY])
REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_MSTR_LKP_167]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_RISK_RSPN_168] FOREIGN KEY([RISK_RSPN_CD])
REFERENCES [dbo].[RISK_RSPN] ([RISK_RSPN_CD])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_RISK_RSPN_168]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_USER_PRFL_157] FOREIGN KEY([ACPT_USER_ID])
REFERENCES [dbo].[USER_PRFL] ([USER_ID])
ALTER TABLE [dbo].[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_USER_PRFL_157]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_USER_PRFL_158] FOREIGN KEY([ENT_USER_ID])
REFERENCES [dbo].[USER_PRFL] ([USER_ID])
ALTER TABLE [dbo].[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_USER_PRFL_158]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST]  WITH CHECK ADD  CONSTRAINT [FK_VULN_SEV_169] FOREIGN KEY([ADJ_SEV_CD])
REFERENCES [dbo].[VULN_SEV] ([VULN_SEV_CD])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST] CHECK CONSTRAINT [FK_VULN_SEV_169]

SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query7)

SET @Query8= '
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '''+ @SchemaName+'''  AND TABLE_NAME =''CLNT_RISK_RGST'')
BEGIN
CREATE TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST_ITM](
	[CLNT_RISK_RGST_KEY] [int] NOT NULL,
	[CLNT_VULN_INSTC_KEY] [int] NOT NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_CLNT_RISK_RGST_ITM] PRIMARY KEY CLUSTERED 
(
	[CLNT_RISK_RGST_KEY] ASC,
	[CLNT_VULN_INSTC_KEY] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST_ITM]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_RISK_RGST_166] FOREIGN KEY([CLNT_RISK_RGST_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_RISK_RGST] ([CLNT_RISK_RGST_KEY])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST_ITM] CHECK CONSTRAINT [FK_CLNT_RISK_RGST_166]
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST_ITM]  WITH CHECK ADD  CONSTRAINT [FK_CLNT_VULN_INSTC_173] FOREIGN KEY([CLNT_VULN_INSTC_KEY])
REFERENCES '+ @SchemaName+ '.[CLNT_VULN_INSTC] ([CLNT_VULN_INSTC_KEY])
ALTER TABLE '+ @SchemaName+ '.[CLNT_RISK_RGST_ITM] CHECK CONSTRAINT [FK_CLNT_VULN_INSTC_173]

SELECT 1 Retval
END
ELSE
BEGIN
SELECT -1 Retval
END 
'
EXECUTE (@Query8)

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

