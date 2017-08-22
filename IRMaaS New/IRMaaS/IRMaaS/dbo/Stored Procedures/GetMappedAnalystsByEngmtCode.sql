CREATE PROCEDURE [dbo].[GetMappedAnalystsByEngmtCode]  
(  
       @UserType		VARCHAR(1),
	   @CLNT_ENGMT_CD	VARCHAR(30)
)  
AS  
BEGIN  
BEGIN TRY  
SET NOCOUNT ON  

IF	@UserType	=	'I'
BEGIN
	SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
    FROM    USER_CLNT_SRVC_ASGN     A
    JOIN	USER_PRFL				B
	ON		A.USER_ID	=	B.USER_ID
	WHERE	A.CLNT_ENGMT_CD	=	@CLNT_ENGMT_CD
	AND		B.USER_TYP_KEY	=	16
	AND		A.ROW_STS_KEY	=	1	
END

IF	@UserType	=	'P'
BEGIN
	SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
    FROM    USER_CLNT_SRVC_ASGN     A
    JOIN	USER_PRFL				B
	ON		A.USER_ID	=	B.USER_ID
	WHERE	A.CLNT_ENGMT_CD	=	@CLNT_ENGMT_CD
	AND		B.USER_TYP_KEY	=	18
	AND		A.ROW_STS_KEY	=	1	
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
