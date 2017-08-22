CREATE PROCEDURE [dbo].[UPDATE_ScanFailedStatus]
(
       @PvcFlag      VARCHAR(1),
       @PiHours      INTEGER,
       @ConFlag      VARCHAR(1)

)
       
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON


BEGIN 
              IF @ConFlag = 'D' --Data
              BEGIN
      UPDATE  A 
      SET     FL_STS_KEY                                =      66,    -- [dbo].[fnGetMasterLkpID]('FL_STS_KEY','Scan Failure'),
              UPDT_USER_ID                     =   1,
              UPDT_DT                              =  GETDATE()
         FROM dbo.APPL_FL_UPLOAD_LOG                            A
         JOIN MSTR_LKP                                B
         ON   B.MSTR_LKP_KEY                          =      A.FL_STS_KEY
         AND  B.LKP_ENTY_NM                           =      'Scan in Progress'      
   --   JOIN  MSTR_LKP                                D
         --ON D.MSTR_LKP_KEY                          =      A.DOC_TYP_KEY
       --  AND       D.LKP_ENTY_NM                           =      'Data' 
WHERE   CASE WHEN @PvcFlag = 'M' THEN DATEDIFF(MINUTE, FL_UPLOAD_DT, GETDATE())
                     WHEN @PvcFlag = 'H' THEN DATEDIFF(HOUR, FL_UPLOAD_DT, GETDATE()) END   > @PiHours
              END


              IF @ConFlag = 'R' --Report
              BEGIN
      UPDATE  A 
      SET     RPT_STS_KEY                                =      70,    -- [dbo].[fnGetMasterLkpID]('FL_STS_KEY','Scan Failure'),
              UPDT_USER_ID                     =   1,
              UPDT_DT                              =  GETDATE()
         FROM dbo.RPT_FL_UPLOAD_LOG                             A
         JOIN MSTR_LKP                                B
         ON   B.MSTR_LKP_KEY                          =      A.RPT_STS_KEY
         AND  B.LKP_ENTY_NM                           =      'Scan in Progress'      
   --   JOIN  MSTR_LKP                                D
         --ON D.MSTR_LKP_KEY                          =      A.DOC_TYP_KEY
       --  AND       D.LKP_ENTY_NM                           =      'Data' 
WHERE   CASE WHEN @PvcFlag = 'M' THEN DATEDIFF(MINUTE, A.CREAT_DT, GETDATE())
                     WHEN @PvcFlag = 'H' THEN DATEDIFF(HOUR, A.CREAT_DT, GETDATE()) END   > @PiHours
              END
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