CREATE PROCEDURE [dbo].[GetUserPermissionsByName]
(
       @UserKey  INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

      SELECT A.PERMSN_KEY,A.PERMSN_NM,A.PERMSN_DESC,A.DSPL_TXT,C.CHLD_XST_IND,A.SEQ_ORDR,
             D.PERMSN_KEY ModuleID,D.PERMSN_NM ModuleName,C.PERMSN_KEY MenuID,C.PERMSN_NM MenuName,
             B.PERMSN_KEY SubmenuID,B.PERMSN_NM SubmenuName
      FROM   PERMSN                 A
      JOIN   PERMSN_GRP_ASSOC       E
      ON     A.PERMSN_KEY         = E.PERMSN_KEY
      JOIN   PERMSN                 B
      ON     E.SUB_MNU_ID         = B.PERMSN_KEY
      AND     B.PERMSN_TYP_KEY     = 6
      JOIN   PERMSN                 C
      ON     E.MNU_ID             = C.PERMSN_KEY
      AND    C.PERMSN_TYP_KEY     = 5
      JOIN   PERMSN                 D
      ON     E.MDUL_ID            = D.PERMSN_KEY
      AND    D.PERMSN_TYP_KEY     = 4
      JOIN   APPL_ROLE_PERMSN_GRP   F
      ON     E.PERMSN_GRP_KEY     = F.PERMSN_GRP_KEY
      AND     F.ROW_STS_KEY               = 1
      JOIN   USER_APPL_ROLE         G
      ON     F.APPL_ROLE_KEY      = G.APPL_ROLE_KEY
      AND     G.ROW_STS_KEY               = 1
         JOIN   APPL_ROLE                       H
      ON     H.APPL_ROLE_KEY      = G.APPL_ROLE_KEY
      AND     G.ROW_STS_KEY               = 1
         AND  H.ROW_STS_KEY               = 1
         JOIN PERMSN_GRP                       I
         ON   E.PERMSN_GRP_KEY      = I.PERMSN_GRP_KEY
         AND  I.ROW_STS_KEY               = 1
      WHERE  A.ROW_STS_KEY        = 1
         AND  E.ROW_STS_KEY               = 1
      AND    G.USER_ID            = @UserKey
      ORDER BY  C.PERMSN_KEY,B.PERMSN_KEY,A.SEQ_ORDR

END TRY

BEGIN CATCH
    IF @@TRANCOUNT > 0
    ROLLBACK TRANSACTION;

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







