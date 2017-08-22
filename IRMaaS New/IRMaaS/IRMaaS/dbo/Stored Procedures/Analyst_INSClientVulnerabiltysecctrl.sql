CREATE PROCEDURE [dbo].[Analyst_INSClientVulnerabiltysecctrl]
(
       @CLNT_VULN_INSTC_KEY       INTEGER,
       @SECUR_CTL_CD              VARCHAR(20),
       @USER_ID                   INTEGER,
       @REG_CMPLN_CD              VARCHAR(20),
       @REG_CMPLN_VER             VARCHAR(20),
       @ROW_STS_KEY               INTEGER,
       @Flag                      VARCHAR(1),    
       @schema                    VARCHAR(50)       

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(max)

       IF @Flag = 'D'
       BEGIN
              
        SET   @Query = 'DELETE FROM '+ @schema+'.CLNT_VULN_SECUR_CTL
                                    WHERE CLNT_VULN_INSTC_KEY  ='+CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY)

              EXECUTE (@Query)
              SELECT @@ROWCOUNT AS RETVALS
                     
       END

       IF @Flag = 'I'
       BEGIN
        SET   @Query = 'INSERT '+ @schema+'.CLNT_VULN_SECUR_CTL(CLNT_VULN_INSTC_KEY,REG_CMPLN_CD,REG_CMPLN_VER,SECUR_CTL_CD,CREAT_DT,
                                    CREAT_USER_ID,ROW_STS_KEY) VALUES
                                  ( '+CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY)+','+CONVERT(VARCHAR,+''''+@REG_CMPLN_CD)+''''+','+CONVERT(VARCHAR,+''''+@REG_CMPLN_VER)+''''+','+
                                  CONVERT(VARCHAR,+''''+@SECUR_CTL_CD)+''''+','+'GETDATE()'+','+CONVERT(VARCHAR,@USER_ID)+','+CONVERT(VARCHAR,@ROW_STS_KEY)+')'

                                                --  PRINT @Query
                                    EXECUTE (@Query)    
              SELECT @@ROWCOUNT AS RETVAL
                       
       END


       IF @Flag = 'U'
       BEGIN
              
               SET   @Query = 'UPDATE  '+ @schema+'.CLNT_VULN_SECUR_CTL
               SET     ROW_STS_KEY         =      '+ CONVERT(VARCHAR,@ROW_STS_KEY) +',
                       UPDT_USER_ID        =      '+ CONVERT(VARCHAR,@USER_ID) +',
                       UPDT_DT             =      '+'GETDATE()'+'
               WHERE   CLNT_VULN_INSTC_KEY =      '+ CONVERT(VARCHAR,@CLNT_VULN_INSTC_KEY) +'
               AND      SECUR_CTL_CD        =     '''+ CONVERT(VARCHAR,@SECUR_CTL_CD) +''''
              
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

