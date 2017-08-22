CREATE FUNCTION [dbo].[fnGetUserNameByID]
(	
    @Userid	INTEGER
)
RETURNS NVARCHAR(MAX)
AS
BEGIN
    DECLARE @UserName NVARCHAR(500)
   
      SELECT @UserName = FST_NM+' '+LST_NM  FROM USER_PRFL
      WHERE	 USER_ID = @Userid

    RETURN @UserName
END

