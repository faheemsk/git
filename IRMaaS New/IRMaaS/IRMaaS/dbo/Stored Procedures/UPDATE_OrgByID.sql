 
 CREATE PROCEDURE [dbo].[UPDATE_OrgByID]  
( @ORG_KEY     INTEGER,  
 @ROW_STS_KEY    INTEGER,  
 @PAR_ORG_KEY     INTEGER,  
 @ORG_TYP_KEY    INTEGER,  
 @ORG_INDUS_KEY    INTEGER,  
 @ORG_NM      VARCHAR(150),  
 @STR_ADR_1     VARCHAR(255),  
 @STR_ADR_2     VARCHAR(255),  
 @CTY_NM      VARCHAR(255),  
 @ST_NM      VARCHAR(255),  
 @CNTRY_NM     VARCHAR(255),  
 @PST_CD      VARCHAR(20),  
 @ORG_DESC_TXT    TEXT,  
 @UPDT_USER_ID    INTEGER,    
 @STS_COMMT_TXT    TEXT       
)  
  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  
  
BEGIN   
      UPDATE dbo.ORG  
      SET    ROW_STS_KEY    =   @ROW_STS_KEY,  
			 PAR_ORG_KEY    =   CASE WHEN @PAR_ORG_KEY=0 THEN NULL ELSE @PAR_ORG_KEY END,  
			 ORG_TYP_KEY    =   @ORG_TYP_KEY,  
			 ORG_INDUS_KEY  =   @ORG_INDUS_KEY,  
			 ORG_NM			=   @ORG_NM,  
			 STR_ADR_1      =   @STR_ADR_1,  
			 STR_ADR_2      =   @STR_ADR_2,  
			 CTY_NM			=   @CTY_NM,  
			 ST_NM			=   @ST_NM,  
			 CNTRY_NM       =   @CNTRY_NM,  
			 PST_CD			=   @PST_CD,  
			 ORG_DESC       =   @ORG_DESC_TXT,  
			 UPDT_DT		=   GETDATE(),  
			 UPDT_USER_ID   =   @UPDT_USER_ID,
			 STS_COMMT_TXT  =	@STS_COMMT_TXT
            
      WHERE  ORG_KEY		=   @ORG_KEY  
        
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
END    
 







