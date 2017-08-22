--DROP PROCEDURE LISTPERMSN
CREATE PROCEDURE [dbo].[LIST_Permissions]
(
       @GroupID INTEGER
)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

IF @GroupID = 0 
BEGIN

SELECT          A.PERMSN_KEY,A.PERMSN_NM,A.PERMSN_DESC,A.DSPL_TXT,D.PERMSN_KEY ModuleID,D.PERMSN_NM ModuleName,
              C.PERMSN_KEY MenuID,C.PERMSN_NM MenuName,B.PERMSN_KEY SubmenuID,B.PERMSN_NM SubmenuName,
              A.CHLD_XST_IND,A.SEQ_ORDR,A.UPDT_DT 
FROM            PERMSN                 A
JOIN            PERMSN                 B
ON            A.PAR_PERMSN_KEY     = B.PERMSN_KEY
AND           B.PERMSN_TYP_KEY     = 6
JOIN            PERMSN                 C
ON            B.PAR_PERMSN_KEY     = C.PERMSN_KEY
AND           C.PERMSN_TYP_KEY     = 5
JOIN            PERMSN                 D
ON            C.PAR_PERMSN_KEY     = D.PERMSN_KEY
AND           D.PERMSN_TYP_KEY     = 4
WHERE           A.ROW_STS_KEY        = 1
ORDER BY   A.PAR_PERMSN_KEY ASC   


END 

IF @GroupID > 0 
BEGIN

SELECT          A.PERMSN_KEY,A.PERMSN_NM,A.PERMSN_DESC,A.DSPL_TXT,D.PERMSN_KEY ModuleID,D.PERMSN_NM ModuleName,
              C.PERMSN_KEY MenuID,C.PERMSN_NM MenuName,B.PERMSN_KEY SubmenuID,B.PERMSN_NM SubmenuName,
              A.CHLD_XST_IND,A.SEQ_ORDR,A.UPDT_DT
FROM            PERMSN                 A
JOIN            PERMSN                 B
ON            A.PAR_PERMSN_KEY     = B.PERMSN_KEY
AND           B.PERMSN_TYP_KEY     = 6
JOIN            PERMSN                 C
ON            B.PAR_PERMSN_KEY     = C.PERMSN_KEY
AND           C.PERMSN_TYP_KEY     = 5
JOIN            PERMSN                 D
ON            C.PAR_PERMSN_KEY     = D.PERMSN_KEY
AND           D.PERMSN_TYP_KEY     = 4
JOIN            PERMSN_GRP_ASSOC         E
ON                     A.PERMSN_KEY                = E.PERMSN_KEY
WHERE           A.ROW_STS_KEY        = 1
AND                    E.PERMSN_GRP_KEY      = @GroupID
ORDER BY   A.PAR_PERMSN_KEY ASC 

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

