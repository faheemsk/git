﻿SET IDENTITY_INSERT [USER_SECUR_DTL] ON
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (1, 1, 1, 20, N'Optum', 0, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (2, 1, 1, 21, N'Optum', 0, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (3, 1, 1, 22, N'Optum', 0, GETDATE(), 1, NULL, NULL)
SET IDENTITY_INSERT [USER_SECUR_DTL] OFF