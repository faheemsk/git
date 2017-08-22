CREATE PROCEDURE [dbo].[Analyst_ListVULNCATGY]
(
       @CLNT_ENGMT_CD VARCHAR(30),
       @SchemaName          VARCHAR(20)

)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
DECLARE @Query VARCHAR(MAX)

                     SET @Query='
                     IF ''' +@CLNT_ENGMT_CD+ ''' = ''''
                     BEGIN

                     SELECT  DISTINCT VULN_CATGY_CD,VULN_CATGY_NM,VULN_CATGY_DESC
                     FROM   VULN_CATGY      
                     ORDER  BY     VULN_CATGY_NM 
                     END    
                     ELSE
                     BEGIN
                     SELECT  DISTINCT A.VULN_CATGY_CD,A.VULN_CATGY_NM,A.VULN_CATGY_DESC
                     FROM   VULN_CATGY      A
                     JOIN   '+ @SchemaName+ '.CLNT_VULN_INSTC B
                     ON      A.VULN_CATGY_CD = B.VULN_CATGY_CD
                     WHERE   B.CLNT_ENGMT_CD                   = '''+@CLNT_ENGMT_CD+ '''
                     AND     B.VULN_SEV_CD NOT IN(''I'') 
                     AND     B.VULN_INSTC_STS_CD NOT IN(''D'',''FP'')
					 AND     B.ROW_STS_KEY   =   1
                     ORDER  BY     A.VULN_CATGY_NM 


                     END'

                   --   PRINT (@query)
   EXECUTE(@Query)
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
