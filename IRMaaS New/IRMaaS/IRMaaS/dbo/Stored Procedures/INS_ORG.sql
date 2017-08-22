
  
/******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: INS_ORG  
 ** Desc: This procedure insert data into ORG table  
 ** Auth: Prasad varma  
 ** Date: 13/4/2016  
 **************************  
 ** Change History  
 **************************  
 ** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  
  
 CREATE PROCEDURE [dbo].[INS_ORG](  
 @PAR_ORG_KEY          INTEGER,  
 @ORG_TYP_KEY          INTEGER,  
 @ORG_INDUS_KEY        INTEGER,  
 @ROW_STS_KEY          INTEGER,  
 @ORG_NM               VARCHAR(150),  
 @STR_ADR_1            VARCHAR(255),  
 @STR_ADR_2            VARCHAR(255),  
 @CTY_NM               VARCHAR(255),  
 @ST_NM                VARCHAR(255),  
 @CNTRY_NM             VARCHAR(255),  
 @PST_CD               VARCHAR(20),  
 @ORG_DESC_TXT         VARCHAR(1000),  
 @CREAT_USER_ID        INTEGER ,
 @STS_COMMT_TXT		   TEXT       
  
  
 )  
 AS  
 BEGIN  
  BEGIN TRY  
  
  SET NOCOUNT ON  
  
    
  
   INSERT ORG(PAR_ORG_KEY, ORG_TYP_KEY, ORG_INDUS_KEY, ROW_STS_KEY, ORG_NM , STR_ADR_1, STR_ADR_2,CTY_NM ,ST_NM, CNTRY_NM ,  
   PST_CD,ORG_DESC, CREAT_DT,CREAT_USER_ID,STS_COMMT_TXT) VALUES  
   (CASE WHEN @PAR_ORG_KEY=0 THEN NULL ELSE @PAR_ORG_KEY END, @ORG_TYP_KEY,@ORG_INDUS_KEY,@ROW_STS_KEY,@ORG_NM,@STR_ADR_1,@STR_ADR_2,@CTY_NM,@ST_NM,@CNTRY_NM,@PST_CD,@ORG_DESC_TXT,  
   GETDATE(),@CREAT_USER_ID,@STS_COMMT_TXT)  
  
   SELECT SCOPE_IDENTITY() AS RETVAL  
  
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
    
 END
 







