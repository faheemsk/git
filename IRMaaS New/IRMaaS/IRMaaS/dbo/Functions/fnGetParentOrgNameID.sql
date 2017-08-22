CREATE FUNCTION [dbo].[fnGetParentOrgNameID]
(	
	@ParentKey INTEGER
)
RETURNS VARCHAR(150)
AS
BEGIN
    DECLARE @EntityID VARCHAR(150)
      
      SELECT @EntityID = ORG_NM FROM ORG 
      WHERE	 ORG_KEY = @ParentKey

    RETURN @EntityID
END

