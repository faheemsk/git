
CREATE PROCEDURE  [dbo].[Report_ListReportFileUpload]
(
       @CLNT_ENGMT_CD             VARCHAR(30)   
)

AS
BEGIN


BEGIN TRY
SET NOCOUNT ON
              SELECT A.RPT_FL_UPLOAD_LOG_KEY,B.RPT_NM,A.CREAT_DT UpdatedOn,C.FST_NM +' '+ C.LST_NM UpdatedBy,D.LKP_ENTY_NM,
					 A.RPT_STS_KEY,A.UPDT_USER_ID,A.FL_NM,A.FL_FLDR_PTH,A.FL_SZ
              FROM   RPT_FL_UPLOAD_LOG		A
			  JOIN	 RPT_NM				    B
			  ON	 A.RPT_NM_KEY		=	B.RPT_NM_KEY
			  JOIN	 USER_PRFL				C
			  ON	 C.USER_ID			=	A.CREAT_USER_ID
			  JOIN	 MSTR_LKP				D
			  ON	 A.RPT_STS_KEY	    =	D.MSTR_LKP_KEY
              WHERE  A.CLNT_ENGMT_CD    =	@CLNT_ENGMT_CD
			  AND	 D.LKP_ENTY_NM		<> 'Scan Failure'
			  ORDER BY CASE WHEN ISNULL(A.UPDT_DT,'') ='' THEN A.CREAT_DT ELSE A.UPDT_DT END DESC
                     
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



