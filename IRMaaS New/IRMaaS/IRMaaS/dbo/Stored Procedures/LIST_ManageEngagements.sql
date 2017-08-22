/******************************  
 ** File: IRMaaSAdmin.SQL     
 ** Name: LIST_ManageEngagements  
 ** Desc: This procedure get data from CLNT_ENGMT,ORG tables
** Auth: Prasad varma  
 ** Date: 3/5/2016  
 **************************  
 ** Change History  
 **************************  


** PR   Date         Author                  Description   
 ** --   --------        -------                ------------------------------------  
 ** 1    00/00/1999      xxxxx            
 *******************************/  

CREATE PROCEDURE [dbo].[LIST_ManageEngagements]
(
	  @UserID	 INTEGER,
	  @Flag		 VARCHAR(1)
)

      AS
      BEGIN
            BEGIN TRY

            SET NOCOUNT ON

			IF	@Flag = 'C'
			BEGIN
                  SELECT            A.CLNT_ENGMT_CD,A.CLNT_ENGMT_CD [Engagement Code],B.PAR_ORG_KEY,
									dbo.fnGetParentOrgNameID(B.PAR_ORG_KEY) [Parent Client Name],B.ORG_NM [Client Name],
                                    A.AGR_DT [Agreement Date],A.ENGMT_STRT_DT [Estimated Start Date],
                                    A.ENGMT_EST_END_DT [Estimated End Date],A.ENGMT_COMMT [Status],'' [Action],
									A.CREAT_DT,A.UPDT_DT
                  FROM              CLNT_ENGMT					  A
                  JOIN              ORG                           B
                  ON                A.CLNT_ORG_KEY    =   B.ORG_KEY
                  WHERE            	A.ROW_STS_KEY <> 3
				  AND				B.ROW_STS_KEY  = 1
                  ORDER BY    CASE WHEN ISNULL(A.UPDT_DT,'') = '' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
			END

			IF	@Flag = 'L'
			BEGIN
                  SELECT            A.CLNT_ENGMT_CD,A.CLNT_ENGMT_CD [Engagement Code],B.PAR_ORG_KEY,
									dbo.fnGetParentOrgNameID(B.PAR_ORG_KEY) [Parent Client Name],B.ORG_NM [Client Name],
                                    A.AGR_DT [Agreement Date],A.ENGMT_STRT_DT [Estimated Start Date],
                                    A.ENGMT_EST_END_DT [Estimated End Date],A.ENGMT_COMMT [Status],'' [Action],
									A.CREAT_DT,A.UPDT_DT
                  FROM              CLNT_ENGMT					  A
                  JOIN              ORG                           B
                  ON                A.CLNT_ORG_KEY    =   B.ORG_KEY
				  JOIN				CLNT_ENGMT_USER_ASGN		  C
				  ON				A.CLNT_ENGMT_CD	 =	  C.CLNT_ENGMT_CD
                  WHERE				 C.USER_ID         =   @UserID
				  AND				B.ROW_STS_KEY  = 1
				  AND				A.ROW_STS_KEY <> 3
                  ORDER BY    CASE WHEN ISNULL(A.UPDT_DT,'') = '' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
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
            
      END


