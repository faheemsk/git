USE [IRMaaSDev]
GO
/****** Object:  StoredProcedure [dbo].[Analyst_INSClientVulnerabiltysecctrl]    Script Date: 2/28/2017 5:26:18 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[RMDTN_INS_CLNT_RMDTN_PLN]
(
       @CLNT_RMDTN_PLN_KEY			INTEGER,
	   @ROW_STS_KEY					INTEGER,
	   @ORG_KEY						INTEGER,	
	   @CLNT_ENGMT_CD				VARCHAR(30),				
       @RMDTN_OWNR_USER_ID			INTEGER,
	   @RMDTN_PLN_STS_CD            VARCHAR(1),
	   @RMDTN_PLN_NM				VARCHAR(150),
	   @RMDTN_PLN_DESC				TEXT,
	   @RMDTN_PLN_CREAT_DT			DATETIME,
	   @RMDTN_PLN_NTF_DT			DATETIME,
	   @RMDTN_PLN_STRT_DT			DATETIME,
	   @RMDTN_PLN_DUE_DT			DATETIME,
	   @RMDTN_PLN_CLOS_DT			DATETIME,
	   @ASSOC_VULN_TOT_CNT			INTEGER,
	   @ASSOC_VULN_CMPL_CNT 		INTEGER,
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
              
        SET   @Query = 'DELETE FROM '+ @schema+'.CLNT_RMDTN_PLN
                       WHERE CLNT_RMTN_PLN_KEY  =' +CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
  
		SET          @Query = 'INSERT '+ @schema+'.CLNT_RMDTN_PLN (ROW_STS_KEY,ORG_KEY, CLNT_ENGMT_CD,RMDTN_OWNR_USER_ID, 
					RMDTN_PLN_STS_CD,RMDTN_PLN_NM,RMDTN_PLN_DESC,RMDTN_PLN_CREAT_DT,RMDTN_PLN_NTF_DT,RMDTN_PLN_STRT_DT,
					RMDTN_PLN_DUE_DT,RMDTN_PLN_CLOS_DT,ASSOC_VULN_TOT_CNT,ASSOC_VULN_CMPL_CNT,CREAT_DT,CREAT_USER_ID) VALUES

		('+CONVERT(VARCHAR, @ROW_STS_KEY)+',
		'+CONVERT(VARCHAR,@ORG_KEY)+',
		'''+CONVERT(VARCHAR,@CLNT_ENGMT_CD)+''',
		 '+ isnull('''' + convert(varchar(10),@RMDTN_OWNR_USER_ID) + '''','null') + ',
		 '+ isnull('''' + convert(varchar(1),@RMDTN_PLN_STS_CD) + '''','null') + '
		  '+ isnull('''' + convert(varchar(150),@RMDTN_PLN_NM) + '''','null') + ',
		  '+ isnull('''' + replace(convert(varchar(max),@RMDTN_PLN_DESC),'''','''''') + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RMDTN_PLN_CREAT_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RMDTN_PLN_NTF_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RMDTN_PLN_STRT_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RMDTN_PLN_DUE_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar(50),@RMDTN_PLN_CLOS_DT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar,@ASSOC_VULN_TOT_CNT) + '''','null') + ',
		  '+ isnull('''' + convert(varchar,@ASSOC_VULN_CMPL_CNT) + '''','null') + ',
		  '+'GETDATE()'+',
		  '+CONVERT(VARCHAR,@USER_ID)+') ; SELECT @@IDENTITY' 

		--  PRINT @Query
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
            
               SET   @Query = 'UPDATE  '+ @schema+'.CLNT_RMDTN_PLN
               SET     ROW_STS_KEY			=   '+ CONVERT(VARCHAR,@ROW_STS_KEY) +',
                       RMDTN_OWNR_USER_ID	=   '+ isnull('''' + convert(varchar(10),@RMDTN_OWNR_USER_ID) + '''','null') + ',   
					   RMDTN_PLN_STS_CD		=	'+ isnull('''' + convert(varchar(1),@RMDTN_PLN_STS_CD) + '''','null') + ',
					   RMDTN_PLN_NM			=	'+ isnull('''' + convert(varchar(150),@RMDTN_PLN_NM) + '''','null') + ',
					   RMDTN_PLN_DESC		=	'+ isnull('''' + replace(convert(varchar(max),@RMDTN_PLN_DESC),'''','''''') + '''','null') + ',
					   RMDTN_PLN_CREAT_DT	=	'+ isnull('''' + convert(varchar(50),@RMDTN_PLN_CREAT_DT) + '''','null') + ',
					   RMDTN_PLN_NTF_DT		=	'+ isnull('''' + convert(varchar(50),@RMDTN_PLN_NTF_DT) + '''','null') + ',
					   RMDTN_PLN_STRT_DT	=	'+ isnull('''' + convert(varchar(50),@RMDTN_PLN_STRT_DT) + '''','null') + ',
					   RMDTN_PLN_DUE_DT		=	'+ isnull('''' + convert(varchar(50),@RMDTN_PLN_DUE_DT) + '''','null') + ',
					   RMDTN_PLN_CLOS_DT	=	'+ isnull('''' + convert(varchar(50),@RMDTN_PLN_CLOS_DT) + '''','null') + ',
					   ASSOC_VULN_TOT_CNT	=	'+ isnull('''' + convert(varchar,@ASSOC_VULN_TOT_CNT) + '''','null') + ',
					   ASSOC_VULN_CMPL_CNT	=	'+ isnull('''' + convert(varchar,@ASSOC_VULN_CMPL_CNT) + '''','null') + ',
                       UPDT_DT				=   '+'GETDATE()'+'
					   UPDT_USER_ID			=	'+ CONVERT(VARCHAR,@USER_ID) +'
               WHERE   CLNT_RMTN_PLN_KEY =      '+ CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY) +''
              
              
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
CREATE PROCEDURE [dbo].[RMDTN_INS_CLNT_VULN_RMDTN_PLN_ITM]
(
       @CLNT_VULN_INSTC_KEY			INTEGER,
	   @CLNT_RMDTN_PLN_KEY			INTEGER,
	   @VULN_RMDTN_STS_CD           VARCHAR(1),			
	   @CLOS_DT						DATETIME,
	   @ROW_STS_KEY					INTEGER,
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
              
        SET   @Query = 'DELETE FROM '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM
                       WHERE CLNT_VULN_INSTC_KEY  =   '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +''

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
  
		SET          @Query =	'INSERT '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM (CLNT_RMDTN_PLN_KEY,VULN_RMDTN_STS_CD,
								CLOS_DT,ROW_STS_KEY,CREAT_DT,CREAT_USER_ID) VALUES

		('+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
		'+ isnull('''' + convert(varchar(1),@VULN_RMDTN_STS_CD) + '''','null') + ',
		'+ isnull('''' + convert(varchar(50),@CLOS_DT) + '''','null') + ',
		'+CONVERT(VARCHAR, @ROW_STS_KEY)+',
		'+'GETDATE()'+',
		'+CONVERT(VARCHAR,@USER_ID)+') ; SELECT @@IDENTITY' 

		--  PRINT @Query
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
            
               SET   @Query = 'UPDATE  '+ @schema+'.CLNT_VULN_RMDTN_PLN_ITM
               SET     CLNT_RMDTN_PLN_KEY	=   '+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
                       VULN_RMDTN_STS_CD	=   '+ isnull('''' + convert(varchar(1),@VULN_RMDTN_STS_CD) + '''','null') + ',  
					   CLOS_DT				=	'+ isnull('''' + convert(varchar(50),@CLOS_DT) + '''','null') + ',
					   ROW_STS_KEY			=	'+ CONVERT(VARCHAR,@ROW_STS_KEY) +',
                       UPDT_DT				=   '+'GETDATE()'+'
					   UPDT_USER_ID			=	'+ CONVERT(VARCHAR,@USER_ID) +'
               WHERE   CLNT_VULN_INSTC_KEY  =   '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +''
              
              
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
CREATE PROCEDURE [dbo].[RMDTN_INS_CLNT_RMDTN_PLN_COMMT]
(
       @CLNT_RMDTN_PLN_COMMT_KEY	INTEGER,
	   @CLNT_RMDTN_PLN_KEY			INTEGER,		
	   @ENT_DT						DATETIME,
	   @COMMT_TXT					TEXT,
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
              
        SET   @Query = 'DELETE FROM '+ @schema+'.CLNT_RMDTN_PLN_COMMT
                       WHERE CLNT_RMDTN_PLN_COMMT_KEY  =   '+ CONVERT(VARCHAR,@CLNT_RMDTN_PLN_COMMT_KEY) +''

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
  
		SET          @Query =	'INSERT '+ @schema+'.CLNT_RMDTN_PLN_COMMT (CLNT_RMDTN_PLN_KEY,ENT_DT,
								COMMT_TXT,CREAT_DT,CREAT_USER_ID) VALUES

		('+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
		'+ isnull('''' + convert(varchar(50),@ENT_DT) + '''','null') + ',
		'+ isnull('''' + replace(convert(varchar(max),@COMMT_TXT),'''','''''') + '''','null') + ',
		'+'GETDATE()'+',
		'+CONVERT(VARCHAR,@USER_ID)+') ; SELECT @@IDENTITY' 

		--  PRINT @Query
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
            
               SET   @Query = 'UPDATE  '+ @schema+'.CLNT_RMDTN_PLN_COMMT
               SET     CLNT_RMDTN_PLN_KEY	=   '+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
                       ENT_DT				=   '+ isnull('''' + convert(varchar(50),@ENT_DT) + '''','null') + ',  
					   COMMT_TXT			=	'+ isnull('''' + replace(convert(varchar(max),@COMMT_TXT),'''','''''') + '''','null') + ',
                       UPDT_DT				=   '+'GETDATE()'+'
					   UPDT_USER_ID			=	'+ CONVERT(VARCHAR,@USER_ID) +'
               WHERE   CLNT_RMDTN_PLN_COMMT_KEY  =   '+ CONVERT(VARCHAR,@CLNT_RMDTN_PLN_COMMT_KEY) +''
              
              
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
                       WHERE CLNT_RMDTN_PLN_COMMT_KEY  =   '+ CONVERT(VARCHAR,@CLNT_RMDTN_PLN_COMMT_KEY) +''

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
  
		SET          @Query =	'INSERT '+ @schema+'.CLNT_RMDTN_PLN_COMMT (CLNT_RMDTN_PLN_KEY,ENT_DT,
								COMMT_TXT,CREAT_DT,CREAT_USER_ID) VALUES

		('+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
		'+ isnull('''' + convert(varchar(50),@ENT_DT) + '''','null') + ',
		'+ isnull('''' + replace(convert(varchar(max),@COMMT_TXT),'''','''''') + '''','null') + ',
		'+'GETDATE()'+',
		'+CONVERT(VARCHAR,@USER_ID)+') ; SELECT @@IDENTITY' 

		--  PRINT @Query
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
            
               SET   @Query = 'UPDATE  '+ @schema+'.CLNT_RMDTN_PLN_COMMT
               SET     CLNT_RMDTN_PLN_KEY	=   '+CONVERT(VARCHAR,@CLNT_RMDTN_PLN_KEY)+',
                       ENT_DT				=   '+ isnull('''' + convert(varchar(50),@ENT_DT) + '''','null') + ',  
					   COMMT_TXT			=	'+ isnull('''' + replace(convert(varchar(max),@COMMT_TXT),'''','''''') + '''','null') + ',
                       UPDT_DT				=   '+'GETDATE()'+'
					   UPDT_USER_ID			=	'+ CONVERT(VARCHAR,@USER_ID) +'
               WHERE   CLNT_RMDTN_PLN_COMMT_KEY  =   '+ CONVERT(VARCHAR,@CLNT_RMDTN_PLN_COMMT_KEY) +''
              
              
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