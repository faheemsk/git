CREATE FUNCTION [dbo].[fnGetMasterLkpID]
(	
	@EntityType	VARCHAR(100),
    @EntityName	VARCHAR(500)
)
RETURNS INTEGER
AS
BEGIN
    DECLARE @EntityID INTEGER
      
      SELECT @EntityID = MSTR_LKP_KEY FROM [MSTR_LKP] 
      WHERE	 LKP_ENTY_TYP_NM = @EntityType AND LKP_ENTY_NM  = @EntityName

    RETURN @EntityID
END

