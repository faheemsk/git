﻿CREATE PROCEDURE [dbo].[UPDATE_PermsnGrpByID]
(	
	@PERMSN_GRP_KEY			INTEGER,
	@ROW_STS_KEY			INTEGER,
	@PERMSN_GRP_NM			VARCHAR(100),
	@PERMSN_GRP_DESC		VARCHAR(1000),
	@UPDT_USER_ID			INTEGER,
	@STS_COMMT_TXT			TEXT  
			  
)

AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

BEGIN 
      UPDATE dbo.PERMSN_GRP
      SET    ROW_STS_KEY			=   @ROW_STS_KEY,
			 PERMSN_GRP_NM			=   @PERMSN_GRP_NM,
			 PERMSN_GRP_DESC		=	@PERMSN_GRP_DESC,
			 UPDT_DT				=	GETDATE(),
	         UPDT_USER_ID			=   @UPDT_USER_ID,
			 STS_COMMT_TXT          =   @STS_COMMT_TXT 

      WHERE  PERMSN_GRP_KEY			=   @PERMSN_GRP_KEY
      
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
END;






