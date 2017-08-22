
CREATE PROCEDURE [dbo].[Analyst_GetCROSSWALKBYCNTLCD]
(
       @PRI_SECUR_CTL_CD    VARCHAR(500)
)
AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
              
              SELECT DISTINCT A.SEC_SECUR_CTL_CD [SECUR_CTL_CD],A.SEC_REG_CMPLN_CD [REG_CMPLN_CD],
                     B.SECUR_CTL_NM,A.SEC_REG_CMPLN_VER REG_CMPLN_VER,
                     B.SECUR_CTL_FAM_CD,B.SECUR_CTL_FAM_NM,B.SECUR_OBJ_CD,B.SECUR_OBJ_NM,B.SECUR_CTL_DESC
              FROM   [SECUR_CTL_MAP]       A
              JOIN   [SECUR_CTL]           B
              ON     A.SEC_SECUR_CTL_CD  = B.SECUR_CTL_CD
			  AND	 A.SEC_REG_CMPLN_CD	 = B.REG_CMPLN_CD
			  AND	 A.SEC_REG_CMPLN_VER = B.REG_CMPLN_VER
              JOIN   REG_CMPLN             C
              ON     A.SEC_REG_CMPLN_CD  = C.REG_CMPLN_CD
			  CROSS APPLY FnSplit(@PRI_SECUR_CTL_CD,',') D
              WHERE  A.PRI_SECUR_CTL_CD    = D.items
              ORDER BY A.SEC_REG_CMPLN_CD 
                            


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
