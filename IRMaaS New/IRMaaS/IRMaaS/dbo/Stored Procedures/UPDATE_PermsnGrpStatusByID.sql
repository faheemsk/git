CREATE PROCEDURE [dbo].[UPDATE_PermsnGrpStatusByID]
(     
      @FLAG                         VARCHAR(2),
      @PERMSN_GRP_KEY               INTEGER
              
)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

BEGIN 
     
     IF @FLAG = 'IA' -- InActive
      BEGIN
     
      UPDATE  dbo.PERMSN_GRP_ASSOC 
      SET    ROW_STS_KEY                  =   2
      WHERE  PERMSN_GRP_KEY             =   @PERMSN_GRP_KEY

      UPDATE dbo.PERMSN_GRP
      SET    ROW_STS_KEY                  =   2
      WHERE  PERMSN_GRP_KEY             =   @PERMSN_GRP_KEY

        
      SELECT @@ROWCOUNT AS RETVAL
      END
        
        
       IF @FLAG = 'D'	-- Delete
        BEGIN 
             
UPDATE  dbo.PERMSN_GRP_ASSOC 
      SET    ROW_STS_KEY                  =   3
        WHERE  PERMSN_GRP_KEY             =   @PERMSN_GRP_KEY

      UPDATE dbo.PERMSN_GRP
      SET    ROW_STS_KEY                  =   3
        WHERE  PERMSN_GRP_KEY             =   @PERMSN_GRP_KEY
      
        SELECT @@ROWCOUNT AS RETVAL
        
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







