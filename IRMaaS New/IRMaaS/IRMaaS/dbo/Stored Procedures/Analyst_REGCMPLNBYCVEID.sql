
CREATE PROCEDURE [dbo].[Analyst_REGCMPLNBYCVEID]
(
       @CVEID VARCHAR(50)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
              
              SELECT A.SECUR_CTL_MAP_KEY,A.PRI_REG_CMPLN_CD,A.PRI_REG_CMPLN_VER,A.PRI_SECUR_CTL_CD,
                     C.REG_CMPLN_NM,C.REG_CMPLN_CD,B.REG_CMPLN_CD,B.REG_CMPLN_VER
              FROM   SECUR_CTL_MAP         A
              JOIN   CVE_SECUR_CTL_MAP     B
              ON     A.PRI_SECUR_CTL_CD  = B.SECUR_CTL_CD
              JOIN   REG_CMPLN             C
              ON     A.PRI_REG_CMPLN_CD  = C.REG_CMPLN_CD
              WHERE  B.CVE_ID            = @CVEID
			  AND	 B.ROW_STS_KEY		 = 1
              UNION
              SELECT A.SECUR_CTL_MAP_KEY,A.SEC_REG_CMPLN_CD,A.SEC_REG_CMPLN_VER,A.SEC_SECUR_CTL_CD,
                     C.REG_CMPLN_NM,C.REG_CMPLN_CD,B.REG_CMPLN_CD,B.REG_CMPLN_VER
              FROM   SECUR_CTL_MAP         A
              JOIN   CVE_SECUR_CTL_MAP     B
              ON     A.SEC_SECUR_CTL_CD  = B.SECUR_CTL_CD
              JOIN   REG_CMPLN                    C
              ON     A.SEC_REG_CMPLN_CD  = C.REG_CMPLN_CD
              WHERE  B.CVE_ID            = @CVEID
			  AND	 B.ROW_STS_KEY		 = 1


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
