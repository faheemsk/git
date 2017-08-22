CREATE PROCEDURE [dbo].[List_UserByOrgIDandUserType]
(
      @OrgID			INT,
	  @USER_TYP_KEY     VARCHAR(20),
	  @FLAG		        VARCHAR(1)

)
AS
BEGIN
BEGIN TRY
SET NOCOUNT ON

	IF @FLAG = 'L'
	BEGIN

      SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
      FROM    USER_PRFL           A
      JOIN    ORG                 B
      ON      A.ORG_KEY			=  B.ORG_KEY
	  JOIN    USER_APPL_ROLE	  C
	  ON      A.USER_ID			=  C.USER_ID
	  JOIN	  APPL_ROLE           D
	  ON      C.APPL_ROLE_KEY   =  D.APPL_ROLE_KEY
	  JOIN	  APPL_ROLE_PERMSN_GRP E
	  ON      C.APPL_ROLE_KEY    =  E.APPL_ROLE_KEY
	  JOIN	  PERMSN_GRP_ASSOC		F
	  ON	  E.PERMSN_GRP_KEY	=  F.PERMSN_GRP_KEY
	  JOIN	  PERMSN					G
	  ON	  F.PERMSN_KEY		= G.PERMSN_KEY
      WHERE   A.ORG_KEY			=  CASE WHEN @OrgID = 0 THEN A.ORG_KEY ELSE @OrgID END
	  AND     A.USER_TYP_KEY	=  CASE WHEN @USER_TYP_KEY = 'Internal' THEN 16 WHEN @USER_TYP_KEY = 'Client' THEN 17 WHEN @USER_TYP_KEY = 'Partner' THEN 18 END
	  AND     A.ROW_STS_KEY		=  1
	  AND     B.ROW_STS_KEY		=  1
	  AND     A.LCK_IND			=  0
	  AND	 G.PERMSN_NM	= 'Add User to Service'
	  -- Add Document upload

      ORDER BY A.USER_ID DESC 
	  END


	  IF @FLAG = 'A'
	BEGIN

      SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
      FROM    USER_PRFL           A
      JOIN    ORG                 B
      ON      A.ORG_KEY			=  B.ORG_KEY
	  JOIN    USER_APPL_ROLE	  C
	  ON      A.USER_ID			=  C.USER_ID
	  JOIN	  APPL_ROLE           D
	  ON      C.APPL_ROLE_KEY   =  D.APPL_ROLE_KEY
	  JOIN	  APPL_ROLE_PERMSN_GRP E
	  ON      C.APPL_ROLE_KEY    =  E.APPL_ROLE_KEY
	  JOIN	  PERMSN_GRP_ASSOC		F
	  ON	  E.PERMSN_GRP_KEY	=  F.PERMSN_GRP_KEY
	  JOIN	  PERMSN					G
	  ON	  F.PERMSN_KEY		= G.PERMSN_KEY
      WHERE   A.ORG_KEY			=  CASE WHEN @OrgID = 0 THEN A.ORG_KEY ELSE @OrgID END
	  AND     A.USER_TYP_KEY	=  CASE WHEN @USER_TYP_KEY = 'Internal' THEN 16 WHEN @USER_TYP_KEY = 'Client' THEN 17 WHEN @USER_TYP_KEY = 'Partner' THEN 18 END
	  AND     A.ROW_STS_KEY		=  1
	  AND     A.LCK_IND			=  0
	  AND     B.ROW_STS_KEY		=  1
	  AND	 G.PERMSN_NM		= 'Add Document upload'
	  

      ORDER BY A.USER_ID DESC 
	  END
IF @FLAG = 'C'
	BEGIN

      SELECT  DISTINCT A.USER_ID,(FST_NM+' '+LST_NM) [User Name] 
      FROM    USER_PRFL           A
      JOIN    ORG                 B
      ON      A.ORG_KEY			=  B.ORG_KEY
	  WHERE   A.ORG_KEY			=  CASE WHEN @OrgID = 0 THEN A.ORG_KEY ELSE @OrgID END
	  AND     A.USER_TYP_KEY	=  CASE WHEN @USER_TYP_KEY = 'Client' THEN 17  END
	  AND     A.ROW_STS_KEY		=  1
	  AND     B.ROW_STS_KEY		=  1
	  AND     A.LCK_IND			=  0

      ORDER BY A.USER_ID DESC 
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