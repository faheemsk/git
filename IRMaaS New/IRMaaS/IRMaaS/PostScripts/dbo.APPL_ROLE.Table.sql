﻿SET IDENTITY_INSERT [dbo].[APPL_ROLE] ON 

INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (1, 1, N'Web App Admin', N'Allows the user to manage entire Web App Admin functionalities', NULL, CAST(0x0000A6BB00B70408 AS DateTime), 3, NULL, NULL, 16)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (2, 2, N'Manage Roles', N'Allows the user to manage roles', NULL, CAST(0x0000A6BB01067582 AS DateTime), 3, NULL, NULL, 16)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (3, 1, N'Engagement Coordinator', N'Allows the user to Add, View, Update and Delete the engagement', NULL, CAST(0x0000A6BE00FB9A87 AS DateTime), 3, CAST(0x0000A6BE00FB9A87 AS DateTime), 3, 16)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (4, 1, N'Optum Engagement Lead', N'Allows the user to Manage Users to services, and Lock/ Unlock the service data', NULL, CAST(0x0000A6BE00FBE776 AS DateTime), 3, CAST(0x0000A6BE00FBE776 AS DateTime), 3, 16)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (5, 1, N'Partner Engagement Lead', N'Allows the user to Manage Users (Add, View, Update and Delete) to services, and Lock the service data', NULL, CAST(0x0000A6BE00FC2845 AS DateTime), 3, CAST(0x0000A6BE00FC2845 AS DateTime), 3, 18)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (6, 1, N'Partner Engagement Analyst', N'Allows the user to Manage documents to services, and allows the user to Lock the services data', NULL, CAST(0x0000A6BE00FD0DF7 AS DateTime), 3, CAST(0x0000A6BE00FD0DF7 AS DateTime), 3, 18)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (7, 1, N'Optum Engagement Analyst', N'Allows the user to Manage documents to services, and allows the user to Lock the services data', NULL, CAST(0x0000A6BE00FD457A AS DateTime), 3, CAST(0x0000A6BE00FD457A AS DateTime), 3, 16)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (8, 1, N'Client Engagement User', N'Allows the user to view the Engagement data', NULL, CAST(0x0000A6BE00FD839E AS DateTime), 3, CAST(0x0000A6BE00FD839E AS DateTime), 3, 17)
INSERT [dbo].[APPL_ROLE] ([APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_NM], [APPL_ROLE_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID], [USER_TYP_KEY]) VALUES (9, 1, N'Remediation Coordinator', N'Remediation Coordinator', NULL, CAST(0x0000A6BE00FDD51E AS DateTime), 3, CAST(0x0000A6BE00FDD51E AS DateTime), 3, 16)

SET IDENTITY_INSERT [dbo].[APPL_ROLE] OFF