CREATE PROCEDURE [dbo].[Analyst_ListCve]
(
       
       @CVE_DESC                    VARCHAR(500),
       @PageNo                      INTEGER,
       @RowspPage					INTEGER,
       @CVE_ID                      VARCHAR(25),
       @Flag                        VARCHAR(1)
)
AS
BEGIN

DECLARE @Count INTEGER

BEGIN TRY
SET NOCOUNT ON

                BEGIN TRAN

                IF @Flag = 'N'
                BEGIN
                     SELECT   COUNT(CVE_ID) TotalCount 
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                     AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ;

                     SELECT   CVE_ID      ,CVE_DESC,PUBL_DT,LST_MOD_DT      
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                     AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ORDER BY  CVE_ID ASC
                     OFFSET ((@PageNo - 1) * @RowspPage) ROWS
                     FETCH NEXT @RowspPage ROWS ONLY;
              END

              IF @Flag = 'A'
              BEGIN
                     SELECT   COUNT(CVE_ID) TotalCount 
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                     AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ;

                     SELECT   CVE_ID      ,CVE_DESC,PUBL_DT,LST_MOD_DT      
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                    AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ORDER BY  PUBL_DT ASC
                     OFFSET ((@PageNo - 1) * @RowspPage) ROWS
                     FETCH NEXT @RowspPage ROWS ONLY;
              END 

              IF @Flag = 'D'
              BEGIN
                     SELECT   COUNT(CVE_ID) TotalCount 
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                     AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ;

                     SELECT   CVE_ID      ,CVE_DESC,PUBL_DT,LST_MOD_DT      
                     FROM   CVE                                     
                     WHERE  ISNULL(CVE_DESC,'') LIKE CASE WHEN @CVE_DESC = '' THEN       ISNULL(CVE_DESC,'') ELSE '%' + @CVE_DESC +'%' END
                    AND     ISNULL(CVE_ID,'') LIKE CASE WHEN @CVE_ID = '' THEN       ISNULL(CVE_ID,'') ELSE   '%' + @CVE_ID +'%'  END
                     ORDER BY  PUBL_DT DESC
                     OFFSET ((@PageNo - 1) * @RowspPage) ROWS
                     FETCH NEXT @RowspPage ROWS ONLY;
              END 
               
              COMMIT TRAN
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