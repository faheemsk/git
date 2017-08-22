--SP_RENAME 'APO3.CLNT_VULN_INSTC.VALD_DT', 'VULN_VLD_DT' , 'COLUMN'

---SP_RENAME 'APO3.CLNT_VULN_INSTC.CLSD_DT', 'VULN_CLOS_DT' , 'COLUMN'

---SP_RENAME 'APO3.CLNT_VULN_INSTC.CLSD_COMMT', 'CLOS_RSN_TXT' , 'COLUMN'

--ALTER TABLE APO3.CLNT_VULN_INSTC ALTER COLUMN CLOS_RSN_TXT TEXT
GO
ALTER PROCEDURE [dbo].[Analyst_INS_CLNT_VULN_INSTC]
(
       @ROW_STS_KEY               INTEGER,
       @ORG_KEY                          INTEGER,
       @CLNT_ENGMT_CD                    VARCHAR(30),  
       @SECUR_SRVC_CD                    VARCHAR(10),
       @VULN_SRC_KEY              INTEGER,
       @VULN_INSTC_STS_CD         VARCHAR(3),
       @VULN_SEV_CD               VARCHAR(3),
       @VULN_IMP_CD               VARCHAR(3),
       @RISK_PRBL_CD              VARCHAR(3),
       @RMDTN_CST_EFFRT_CD        VARCHAR(3) = NULL,
       @VULN_CATGY_CD		      VARCHAR(10),
       @CVE_ID                                  VARCHAR(25),
       @SRC_VULN_INSTC_ID         VARCHAR(150),
       @VULN_NM                          VARCHAR(255),
       @VULN_DESC                        TEXT,
       @IPADR                            VARCHAR(39),
       @PORT_NBR                         INTEGER,
       @SRC_ADVS_TXT              VARCHAR(1024),
       @VULN_BAS_SCOR                    DECIMAL(10,2),
       @VULN_IMP_SUB_SCOR         DECIMAL(10,2),
       @VULN_EXPLT_SUB_SCOR DECIMAL(10,2),
       @VULN_TMPRL_SCOR           DECIMAL(10,2),
       @VULN_ENV_SCOR                    DECIMAL(10,2),
       @VULN_VCTR_TXT                    VARCHAR(100),
       @NTWK_NM                          VARCHAR(150),
       @PRTCL_NM                         VARCHAR(255),
       @HST_NM                                  VARCHAR(150),
       @DOM_NM                                  VARCHAR(150),
       @SFTW_NM                          VARCHAR(150),
       @OS_KEY                                  INTEGER,
       @APPL_URL                         NVARCHAR(2000),
       @NETBIOS_NM                       VARCHAR(150),
       @MAC_ADR_NM                       VARCHAR(150),
       @VULN_TECH_COMMT_TXT TEXT,
       @VULN_IMP_COMMT_TXT        TEXT,
       @RECOM_COMMT_TXT           TEXT,
       @ROOT_CAUS_COMMT_TXT    TEXT,
       @USER_ID                          INTEGER,
       @VULN_OVALL_SCOR           DECIMAL(10,2),
       @OWASP_TOP_10_KEY          INTEGER,
	   @VULN_VLD_DT						VARCHAR(50),
	   @VULN_CLOS_DT						VARCHAR(50),
	   @CLOS_RSN_TXT					TEXT,
       @schema                                  VARCHAR(50)

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query nVARCHAR(max)
DECLARE @id INTEGER


SET          @Query = 'INSERT '+ @schema+'.CLNT_VULN_INSTC (ROW_STS_KEY,ORG_KEY, CLNT_ENGMT_CD, SECUR_SRVC_CD,VULN_SRC_KEY,VULN_INSTC_STS_CD,VULN_SEV_CD,VULN_IMP_CD,
RISK_PRBL_CD,RMDTN_CST_EFFRT_CD, VULN_CATGY_CD,CVE_ID,SRC_VULN_INSTC_ID,
VULN_NM,VULN_DESC,VULN_CREAT_DT,IPADR,PORT_NBR,SRC_ADVS_TXT,VULN_BAS_SCOR,VULN_IMP_SUB_SCOR,VULN_EXPLT_SUB_SCOR,
VULN_TMPRL_SCOR,VULN_ENV_SCOR,VULN_VCTR_TXT,NTWK_NM,PRTCL_NM,HST_NM,DOM_NM,SFTW_NM,OS_KEY,APPL_URL,NETBIOS_NM,
MAC_ADR_NM,VULN_TECH_COMMT_TXT,VULN_IMP_COMMT_TXT,RECOM_COMMT_TXT,ROOT_CAUS_COMMT_TXT,
CREAT_DT,CREAT_USER_ID,VULN_OVALL_SCOR,OWASP_TOP_10_KEY,VULN_VLD_DT	,VULN_CLOS_DT,CLOS_RSN_TXT) VALUES

('+CONVERT(VARCHAR, @ROW_STS_KEY)+',
'+CONVERT(VARCHAR,@ORG_KEY)+',
'''+CONVERT(VARCHAR,@CLNT_ENGMT_CD)+''',
''' +CONVERT(VARCHAR,@SECUR_SRVC_CD)+''',
'+CONVERT(VARCHAR,@VULN_SRC_KEY)+',
''' +CONVERT(VARCHAR,@VULN_INSTC_STS_CD)+''',
  '+ isnull('''' + convert(varchar(3),@VULN_SEV_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@VULN_IMP_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@RISK_PRBL_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(3),@RMDTN_CST_EFFRT_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(10),@VULN_CATGY_CD) + '''','null') + ',
  '+ isnull('''' + convert(varchar(25),@CVE_ID) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@SRC_VULN_INSTC_ID) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(255),@VULN_NM),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_DESC),'''','''''') + '''','null') + ',
'+'GETDATE()'+',
  '+ isnull('''' + convert(varchar(39),@IPADR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@PORT_NBR) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(1024),@SRC_ADVS_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_BAS_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_IMP_SUB_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_EXPLT_SUB_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_TMPRL_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@VULN_ENV_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar(100),@VULN_VCTR_TXT) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@NTWK_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(255),@PRTCL_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@HST_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@DOM_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@SFTW_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@OS_KEY) + '''','null') + ',
  '+ isnull('''' + convert(varchar(2000),@APPL_URL) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@NETBIOS_NM) + '''','null') + ',
  '+ isnull('''' + convert(varchar(150),@MAC_ADR_NM) + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_TECH_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@VULN_IMP_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@RECOM_COMMT_TXT),'''','''''') + '''','null') + ',
  '+ isnull('''' + replace(convert(varchar(max),@ROOT_CAUS_COMMT_TXT),'''','''''') + '''','null') + ',
' + 'GETDATE()'+',
'+CONVERT(VARCHAR,@USER_ID)+',
  '+ isnull('''' + convert(varchar,@VULN_OVALL_SCOR) + '''','null') + ',
  '+ isnull('''' + convert(varchar,@OWASP_TOP_10_KEY) + '''','null') + ',
   '+ isnull('''' + convert(varchar(50),@VULN_VLD_DT) + '''','null') + ',
    '+ isnull('''' + convert(varchar(50),@VULN_CLOS_DT) + '''','null') + ',
	 '+ isnull('''' + replace(convert(varchar(max),@CLOS_RSN_TXT),'''','''''') + '''','null') + ') ; SELECT @@IDENTITY' 

--  PRINT @Query
--  PRINT @id
--  EXECUTE sp_executesql @Query, N'@Id INTEGER OUTPUT', @NewId OUTPUT
  -- SELECT @Result = @id
  DECLARE @Result AS Table (RetValue int)
  INSERT INTO @Result EXECUTE (@Query)
  SELECT RetValue FROM @Result
--  SELECT @id AS RETVAL
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
ALTER PROCEDURE [dbo].[Analyst_UpdateVulnerabilityInstance]
(
       @Flag                             VARCHAR(1),
       @SECUR_SRVC_CD                    VARCHAR(10),
       @IPADR                            VARCHAR(39),
       @SFTW_NM                          VARCHAR(150),
       @HST_NM                                  VARCHAR(150),
       @DOM_NM                                  VARCHAR(150),
       @APPL_URL                         NVARCHAR(2000),
       @OS_KEY                                  INTEGER,
       @MAC_ADR_NM                       VARCHAR(150),
       @PORT_NBR                         INTEGER,
       @CLNT_VULN_INSTC_KEY INTEGER,
       @VULN_INSTC_STS_CD         VARCHAR(3),
       @VULN_SEV_CD               VARCHAR(3),
       @VULN_IMP_CD               VARCHAR(3),
       @RISK_PRBL_CD              VARCHAR(3),
       @RMDTN_CST_EFFRT_CD        VARCHAR(3),
       @VULN_CATGY_CD        VARCHAR(10),
       @CVE_ID                                  VARCHAR(25),
       @VULN_NM                          VARCHAR(255),
       @VULN_DESC                        TEXT,
       @SRC_ADVS_TXT              VARCHAR(1024),
       @VULN_BAS_SCOR                    DECIMAL(10,2),
       @VULN_IMP_SUB_SCOR         DECIMAL(10,2),
       @VULN_EXPLT_SUB_SCOR              DECIMAL(10,2),
       @VULN_TMPRL_SCOR           DECIMAL(10,2),
       @VULN_ENV_SCOR                    DECIMAL(10,2),
       @VULN_VCTR_TXT                    VARCHAR(100),
       @VULN_TECH_COMMT_TXT              TEXT,
       @VULN_IMP_COMMT_TXT        TEXT,
       @RECOM_COMMT_TXT           TEXT,
       @ROOT_CAUS_COMMT_TXT    TEXT,
       @USER_ID                          INTEGER,
       @VULN_OVALL_SCOR           DECIMAL(10,2),
       @OWASP_TOP_10_KEY          INTEGER,
	   @VULN_VLD_DT						VARCHAR(50),
	   @VULN_CLOS_DT						VARCHAR(50),
	   @CLOS_RSN_TXT					TEXT,
       @schema                                  VARCHAR(50)   

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)
       
              IF @Flag      =      'E' -- ETL
                       
              BEGIN

              SET           @Query = 'UPDATE     '+ @schema+'.CLNT_VULN_INSTC
              
              SET           VULN_INSTC_STS_CD    =      ''' +CONVERT(VARCHAR,@VULN_INSTC_STS_CD)+''' ,
                           VULN_SEV_CD                =      '+ isnull('''' + convert(varchar(50),@VULN_SEV_CD) + '''','null') + ',
                           VULN_IMP_CD                =      '+ isnull('''' + convert(varchar(50),@VULN_IMP_CD) + '''','null') + ',
                           RISK_PRBL_CD         =      '+ isnull('''' + convert(varchar(50),@RISK_PRBL_CD) + '''','null') + ',
                           RMDTN_CST_EFFRT_CD   =      '+ isnull('''' + convert(varchar(50),@RMDTN_CST_EFFRT_CD) + '''','null') + ',
                           VULN_CATGY_CD   =      '+ isnull('''' + convert(varchar(50),@VULN_CATGY_CD) + '''','null') + ',
                           CVE_ID                     =      '+ isnull('''' + convert(varchar(25),@CVE_ID) + '''','null') + ',
                           VULN_NM                           =      '+ isnull('''' + replace(convert(varchar(255),@VULN_NM),'''','''''') + '''','null') + ',
                           VULN_DESC                  =      '+ isnull('''' + replace(convert(varchar(max),@VULN_DESC),'''','''''') + '''','null') + ',
                           SRC_ADVS_TXT         =      '+ isnull('''' + convert(varchar(max),@SRC_ADVS_TXT) + '''','null') + ',
                           VULN_BAS_SCOR        =      '+ isnull('''' + convert(varchar(50),@VULN_BAS_SCOR) + '''','null') + ',
                           VULN_IMP_SUB_SCOR    =      '+ isnull('''' + convert(varchar(50),@VULN_IMP_SUB_SCOR) + '''','null') + ',
                           VULN_EXPLT_SUB_SCOR =   '+ isnull('''' + convert(varchar(50),@VULN_EXPLT_SUB_SCOR) + '''','null') + ',
                           VULN_TMPRL_SCOR            =      '+ isnull('''' + convert(varchar(50),@VULN_TMPRL_SCOR) + '''','null') + ',
                           VULN_ENV_SCOR        =      '+ isnull('''' + convert(varchar(50),@VULN_ENV_SCOR) + '''','null') + ',
                           VULN_VCTR_TXT        =      '+ isnull('''' + convert(varchar(max),@VULN_VCTR_TXT) + '''','null') + ',
                           VULN_TECH_COMMT_TXT =   '+ isnull('''' + replace(convert(varchar(max),@VULN_TECH_COMMT_TXT),'''','''''') + '''','null') + ',
                           VULN_IMP_COMMT_TXT   =      '+ isnull('''' + replace(convert(varchar(max),@VULN_IMP_COMMT_TXT),'''','''''') + '''','null') + ',
                           RECOM_COMMT_TXT            =      '+ isnull('''' +replace(convert(varchar(max),@RECOM_COMMT_TXT),'''','''''') + '''','null') + ',
                           ROOT_CAUS_COMMT_TXT =      '+ isnull('''' + replace(convert(varchar(max),@ROOT_CAUS_COMMT_TXT),'''','''''') + '''','null') + ',   
                           UPDT_DT                           =      '+' GETDATE() '+',
                           UPDT_USER_ID         =      '+convert(varchar(50),@USER_ID)+ ',
                           VULN_OVALL_SCOR            =      '+ isnull('''' + convert(varchar(50),@VULN_OVALL_SCOR) + '''','null') + ',
                           OWASP_TOP_10_KEY     =      '+ isnull('''' + convert(varchar(50),@OWASP_TOP_10_KEY) + '''','null') + ',
						   VULN_VLD_DT	     =      '+ isnull('''' + convert(varchar(50),@VULN_VLD_DT) + '''','null') + ',
						   VULN_CLOS_DT     =      '+ isnull('''' + convert(varchar(50),@VULN_CLOS_DT) + '''','null') + ',
						   CLOS_RSN_TXT     =      '+ isnull('''' + replace(convert(varchar(max),@CLOS_RSN_TXT),'''','''''') + '''','null') + '

                           WHERE  CLNT_VULN_INSTC_KEY  =      '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +''

                           EXECUTE (@Query)
                           SELECT @@ROWCOUNT AS RETVAL

                     END

              IF @Flag    =      'M' -- Manual Entry
                       
              BEGIN

              SET         @Query = 'UPDATE     '+ @schema+'.CLNT_VULN_INSTC

              SET         SECUR_SRVC_CD                       =      ''' +CONVERT(VARCHAR,@SECUR_SRVC_CD)+''',         
                          IPADR                     =     '+ isnull('''' + convert(varchar(50),@IPADR) + '''','null') + ',                         
                          SFTW_NM                   =      '+ isnull('''' + convert(varchar(150),@SFTW_NM) + '''','null') + ',                      
                          HST_NM                    =     '+ isnull('''' + convert(varchar(150),@HST_NM) + '''','null') + ',                      
                          DOM_NM                    =     '+ isnull('''' + convert(varchar(150),@DOM_NM) + '''','null') + ',                            
                          APPL_URL                  =     '+ isnull('''' + convert(varchar(2000),@APPL_URL) + '''','null') + ',                  
                          OS_KEY                    =     '+ isnull('''' + convert(varchar(50),@OS_KEY) + '''','null') + ',                              
                          MAC_ADR_NM                =     '+ isnull('''' + convert(varchar(50),@MAC_ADR_NM) + '''','null') + ',                     
                          PORT_NBR                  =      '+ isnull('''' + convert(varchar(50),@PORT_NBR) + '''','null') + ',
                          VULN_INSTC_STS_CD                   =      ''' +CONVERT(VARCHAR,@VULN_INSTC_STS_CD)+''' ,
                          VULN_SEV_CD               =      '+ isnull('''' + convert(varchar(50),@VULN_SEV_CD) + '''','null') + ',
                           VULN_IMP_CD              =      '+ isnull('''' + convert(varchar(50),@VULN_IMP_CD) + '''','null') + ',
                           RISK_PRBL_CD                       =      '+ isnull('''' + convert(varchar(50),@RISK_PRBL_CD) + '''','null') + ',
                           RMDTN_CST_EFFRT_CD          =      '+ isnull('''' + convert(varchar(50),@RMDTN_CST_EFFRT_CD) + '''','null') + ',
                           VULN_CATGY_CD          =      '+ isnull('''' + convert(varchar(50),@VULN_CATGY_CD) + '''','null') + ',
                           CVE_ID                   =      '+ isnull('''' + convert(varchar(50),@CVE_ID) + '''','null') + ',
                           VULN_NM                           =      '+ isnull('''' + replace(convert(varchar(255),@VULN_NM),'''','''''') + '''','null') + ',
                           VULN_DESC                  =      '+ isnull('''' + replace(convert(varchar(max),@VULN_DESC),'''','''''') + '''','null') + ',
                           SRC_ADVS_TXT                       =      '+ isnull('''' + convert(varchar(max),@SRC_ADVS_TXT) + '''','null') + ',
                           VULN_BAS_SCOR               =      '+ isnull('''' + convert(varchar(50),@VULN_BAS_SCOR) + '''','null') + ',
                           VULN_IMP_SUB_SCOR           =      '+ isnull('''' + convert(varchar(50),@VULN_IMP_SUB_SCOR) + '''','null') + ',
                           VULN_EXPLT_SUB_SCOR         =         '+ isnull('''' + convert(varchar(50),@VULN_EXPLT_SUB_SCOR) + '''','null') + ',
                           VULN_TMPRL_SCOR          =      '+ isnull('''' + convert(varchar(50),@VULN_TMPRL_SCOR) + '''','null') + ',
                           VULN_ENV_SCOR               =      '+ isnull('''' + convert(varchar(50),@VULN_ENV_SCOR) + '''','null') + ',
                         VULN_VCTR_TXT                        =      '+ isnull('''' + convert(varchar(max),@VULN_VCTR_TXT) + '''','null') + ',
                            VULN_TECH_COMMT_TXT =   '+ isnull('''' + replace(convert(varchar(max),@VULN_TECH_COMMT_TXT),'''','''''') + '''','null') + ',
                           VULN_IMP_COMMT_TXT   =      '+ isnull('''' + replace(convert(varchar(max),@VULN_IMP_COMMT_TXT),'''','''''') + '''','null') + ',
                           RECOM_COMMT_TXT            =      '+ isnull('''' +replace(convert(varchar(max),@RECOM_COMMT_TXT),'''','''''') + '''','null') + ',
                           ROOT_CAUS_COMMT_TXT =      '+ isnull('''' + replace(convert(varchar(max),@ROOT_CAUS_COMMT_TXT),'''','''''') + '''','null') + ',   
                          UPDT_DT                   =     '+'GETDATE()'+',
                          UPDT_USER_ID                        =     '+convert(varchar(50),@USER_ID)+ ',
                          VULN_OVALL_SCOR           =      '+ isnull('''' + convert(varchar(50),@VULN_OVALL_SCOR) + '''','null') + ',
                           OWASP_TOP_10_KEY                   =      '+ isnull('''' + convert(varchar(50),@OWASP_TOP_10_KEY) + '''','null') + ',
						     VULN_VLD_DT	     =      '+ isnull('''' + convert(varchar(50),@VULN_VLD_DT) + '''','null') + ',
						   VULN_CLOS_DT     =      '+ isnull('''' + convert(varchar(50),@VULN_CLOS_DT) + '''','null') + ',
						   CLOS_RSN_TXT     =      '+ isnull('''' + replace(convert(varchar(max),@CLOS_RSN_TXT),'''','''''') + '''','null') + '

                          WHERE  CLNT_VULN_INSTC_KEY  =      '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +''
                          EXECUTE (@Query)
                                           SELECT @@ROWCOUNT AS RETVAL
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
/****** Object:  StoredProcedure [dbo].[CreateSchemaView]    Script Date: 2/20/2017 3:46:35 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
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
					A.DOM_NM,A.VULN_SEV_CD,A.IPADR,D.VULN_INSTC_STS_CD,D.VULN_INSTC_STS_NM,O.SECUR_SRVC_NM,A.VULN_OVALL_SCOR,VULN_VLD_DT,VULN_CLOS_DT,convert(varchar,CLOS_RSN_TXT) CLOS_RSN_TXT,
					A.SRC_VULN_SCAN_ID,CONVERT(VARCHAR,A.SRC_VULN_SCAN_STRT_DT,101)STRT_DT,A.CREAT_DT,G.LST_MOD_DT,G.CWE_ID,
					CONVERT(VARCHAR,A.SRC_VULN_SCAN_END_DT,101)END_DT,A.SFTW_NM,A.HST_NM,A.VULN_VCTR_TXT,
					ISNULL(A.APPL_URL,'''')APPL_URL,E.OS_NM,A.NETBIOS_NM,H.VULN_IMP_CD,-- K.SECUR_CTL_FAM_NM,K.SECUR_OBJ_NM,K.SECUR_CTL_NM,
					A.MAC_ADR_NM,A.PORT_NBR,CONVERT(VARCHAR(MAX),A.VULN_DESC)VULN_DESC,CONVERT(VARCHAR(MAX),A.VULN_TECH_COMMT_TXT)VULN_TECH_COMMT_TXT,
					CONVERT(VARCHAR(MAX),A.VULN_IMP_COMMT_TXT)VULN_IMP_COMMT_TXT,CONVERT(VARCHAR(MAX),A.RECOM_COMMT_TXT)RECOM_COMMT_TXT,
					CONVERT(VARCHAR(MAX),A.ROOT_CAUS_COMMT_TXT)ROOT_CAUS_COMMT_TXT,G.CVE_ID,G.CVE_DESC,A.VULN_BAS_SCOR,A.VULN_TMPRL_SCOR,A.VULN_ENV_SCOR,C.VULN_SEV_NM,H.VULN_IMP_NM,
					A.RISK_PRBL_CD,I.RISK_PRBL_NM,A.VULN_CATGY_CD,L.VULN_CATGY_NM,A.OWASP_TOP_10_KEY,
					M.OWASP_NM,A.SECUR_SRVC_CD,M.OWASP_CD,A.ROW_STS_KEY,A.ORG_KEY,VULN_IMP_SUB_SCOR,VULN_EXPLT_SUB_SCOR,VULN_CREAT_DT,SRC_ADVS_TXT,NTWK_NM,PRTCL_NM,
					SRC_VULN_SCAN_STRT_DT,SRC_VULN_SCAN_END_DT,B.LKP_ENTY_NM [Source Name],A.CREAT_USER_ID,E.OS_KEY,SRC_VULN_BAS_SCOR,G.UPDT_DT,
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
			AND	    J.SECUR_CTL_CD			=	K.SECUR_CTL_CD
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
