CREATE FUNCTION [dbo].[fnGetMasterLkpNameByID]
(	
    @EntityID	INTEGER
)
RETURNS NVARCHAR(MAX)
AS
BEGIN
    DECLARE @EntityName NVARCHAR(500)
      
      SELECT @EntityName = LKP_ENTY_NM FROM [MSTR_LKP] 
      WHERE	 MSTR_LKP_KEY = @EntityID

    RETURN @EntityName
END

