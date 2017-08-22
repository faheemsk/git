/*
Post-Deployment Script Template							
--------------------------------------------------------------------------------------
 This file contains SQL statements that will be appended to the build script.		
 Use SQLCMD syntax to include a file in the post-deployment script.			
 Example:      :r .\myfile.sql								
 Use SQLCMD syntax to reference a variable in the post-deployment script.		
 Example:      :setvar TableName MyTable							
               SELECT * FROM [$(TableName)]					
--------------------------------------------------------------------------------------
*/

--Reference tables


SET IDENTITY_INSERT [dbo].[MSTR_LKP] ON 

INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, N'Row Status', N'Active', N'Row Level Status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, N'Row Status', N'Deactive', N'Row Level Status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, N'Row Status', N'Delete', N'Row Level Status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, N'Permission Type', N'Module', N'Module Level Permission', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, N'Permission Type', N'Menu', N'Menu Level Permission', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, N'Permission Type', N'Submenu', N'Submenu Level Permission', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, N'Permission Type', N'Permission', N'Last Level Permission', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, N'Organization Type', N'Internal', N'Organization Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, N'Organization Type', N'Client', N'Organization Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, N'Organization Type', N'Partner', N'Organization Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, N'Organization Industry', N'Life Sciences', N'Organization Industry Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, N'Organization Industry', N'Payer', N'Organization Industry Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, N'Organization Industry', N'Provider', N'Organization Industry Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, N'User Activation', N'Lock', N'User Activation information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, N'User Activation', N'Unlock', N'User Activation information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, N'User Type', N'Internal', N'User Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, N'User Type', N'Client', N'User Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, N'User Type', N'Partner', N'User Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, N'User Type', N'Administrator', N'User Type Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (20, N'Security Question', N'What is your mother''s maiden name?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (21, N'Security Question', N'What city were you born in?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (22, N'Security Question', N'What was your first pet''s name?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (23, N'Security Question', N'What was the name of your high school?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (24, N'Security Question', N'What was your childhood nickname?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (25, N'Security Question', N'What is the name of your favorite childhood friend?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (26, N'Security Question', N'What is the middle name of your youngest child?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (27, N'Security Question', N'In what city did you meet your spouse/significant other?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (28, N'Security Question', N'What school did you attend for sixth grade?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (29, N'Security Question', N'What is your maternal grandmother''s maiden name?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (30, N'Security Question', N'What is your paternal grandfather''s maiden name?', N'Security Questions Information', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (31, N'Source', N'Tripwire IP360', N'The tool used for Network Vulnerability Scans as a part of the Network Vulnerability Assessment.', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (32, N'Source', N'CISCO', N'CISCO Partner', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (33, N'Source', N'WebInspect', N'The tool used for Application Vulnerability Scans as a part of the Application Vulnerability Assessment.', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (34, N'Source', N'PPMO', N'Project & Portfolio Management Optics', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (35, N'Source', N'Manual', N'User created data', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (36, N'File Status', N'New', N'Whenever user uploads a file it wil be in new status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (37, N'File Status', N'To Be Processed', N'Once a service locked it will be in To be Processed status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (38, N'File Status', N'ETL in Process', N'Once ETL picks the file it will be in ETL in Processs status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (39, N'File Status', N'ETL Success', N'Once the file successfully processed by ETL it will in Success status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (40, N'File Status', N'ETL Failure', N'If any error occured during the ETL process it will be in Failure status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (41, N'File Status', N'Archive', N'Once the file moved to archive it will be in Archive status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (42, N'Document Type', N'Data', N'Document Type to upload a file', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (43, N'Document Type', N'Reports', N'Document Type to upload a file', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (44, N'Document Type', N'Evidence', N'Document Type to upload a file', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (45, N'Service Status', N'Not Reviewed', N'Default Status for a service in analyst module', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (46, N'Service Status', N'Reviewed', N'Once a service reviewed it will be in Reviewed status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (47, N'Report Status', N'Published', N'Status will be updated to Published after reports are reviewed', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (48, N'Report Status', N'Not Published', N'Default status for files uploaded in Reporter module', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (49, N'CVE_FILE', N'Modified', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-Modified.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (50, N'CVE_FILE', N'2002', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2002.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (51, N'CVE_FILE', N'2003', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2003.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (52, N'CVE_FILE', N'2004', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2004.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (53, N'CVE_FILE', N'2005', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2005.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (54, N'CVE_FILE', N'2006', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2006.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (55, N'CVE_FILE', N'2007', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2007.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (56, N'CVE_FILE', N'2008', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2008.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (57, N'CVE_FILE', N'2009', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2009.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (58, N'CVE_FILE', N'2010', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2010.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (59, N'CVE_FILE', N'2011', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2011.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (60, N'CVE_FILE', N'2012', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2012.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (61, N'CVE_FILE', N'2013', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2013.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (62, N'CVE_FILE', N'2014', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2014.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (63, N'CVE_FILE', N'2015', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2015.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (64, N'CVE_FILE', N'2016', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2016.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (65, N'File Status', N'Scan in Progress', N'If any error occured during the ETL process it will be in Failure status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (66, N'File Status', N'Scan Failure', N'Once the file moved to archive it will be in Archive status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (67, N'Engagement Status', N'Open', N'Once an Engagement created it will be in Open status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (68, N'Engagement Status', N'Published', N'Once after publshing the dashboard to client it will be in published status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (69, 'Report Status', 'Scan in Progress', 'Once a report is uploaded it will be in Scan inprogress status', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (70, N'Report Status', N'Scan Failure', N'If ECG Scan failed status will be updated to Scan Failure', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (71, N'Source', N'Common Format v1.0', N'For ingesting data into the Intelligent Hub in a common structure and format', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (72, N'CVE_FILE', N'2017', N'https://nvd.nist.gov/feeds/xml/cve/nvdcve-2.0-2017.xml.zip', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (73, N'File Status', N'ETL Staging: In-Progress', N'ETL job picks the uploaded file for staging process', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (74, N'File Status', N'ETL Staging: Success', N'ETL job completes the staging file load', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[MSTR_LKP] ([MSTR_LKP_KEY], [LKP_ENTY_TYP_NM], [LKP_ENTY_NM], [LKP_ENTY_DESC], [ACTV_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (75, N'File Status', N'ETL Staging: Failure', N'ETL job failed during the staging file load', 1, GETDATE(), 2, NULL, NULL)

SET IDENTITY_INSERT [dbo].[MSTR_LKP] OFF




SET IDENTITY_INSERT [dbo].[ORG] ON 

INSERT [dbo].[ORG] ([ORG_KEY], [ROW_STS_KEY], [PAR_ORG_KEY], [ORG_TYP_KEY], [ORG_INDUS_KEY], [ORG_NM], [STR_ADR_1], [STR_ADR_2], [CTY_NM], [ST_NM], [CNTRY_NM], [PST_CD], [ORG_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, NULL, 8, NULL, N'OPTUM', N'Address1', N'Address2', N'City', N'State', N'US', N'11111', N'Description', NULL, GETDATE(), '2', NULL, NULL)
SET IDENTITY_INSERT [dbo].[ORG] OFF


INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ABW', N'Aruba', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AFG', N'Afghanistan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AGO', N'Angola', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AIA', N'Anguilla', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ALA', N'Aland Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ALB', N'Albania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AND', N'Andorra', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ANT', N'Netherlands Antilles', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ARE', N'United Arab Emirates', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ARG', N'Argentina', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ARM', N'Armenia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ASM', N'American Samoa', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ATG', N'Antigua and Barbuda', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AUS', N'Australia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AUT', N'Austria', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AZE', N'Azerbaijan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BDI', N'Burundi', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BEL', N'Belgium', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BEN', N'Benin', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BFA', N'Burkina Faso', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BGD', N'Bangladesh', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BGR', N'Bulgaria', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BHR', N'Bahrain', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BHS', N'Bahamas', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BIH', N'Bosnia and Herzegovina', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BLM', N'Saint-BarthTlemy', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BLR', N'Belarus', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BLZ', N'Belize', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BMU', N'Bermuda', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BOL', N'Bolivia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BRA', N'Brazil', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BRB', N'Barbados', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BRN', N'Brunei Darussalam', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BTN', N'Bhutan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'BWA', N'Botswana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CAF', N'Central African Republic', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CAN', N'Canada', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CHE', N'Switzerland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CHL', N'Chile', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CHN', N'China', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CIV', N'C(te d''Ivoire', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CMR', N'Cameroon', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'COD', N'Democratic Republic of the Congo', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'COG', N'Congo', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'COK', N'Cook Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'COL', N'Colombia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'COM', N'Comoros', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CPV', N'Cape Verde', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CRI', N'Costa Rica', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CUB', N'Cuba', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CYM', N'Cayman Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CYP', N'Cyprus', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CZE', N'Czech Republic', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DEU', N'Germany', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DJI', N'Djibouti', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DMA', N'Dominica', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DNK', N'Denmark', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DOM', N'Dominican Republic', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'DZA', N'Algeria', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ECU', N'Ecuador', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'EGY', N'Egypt', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ERI', N'Eritrea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ESH', N'Western Sahara', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ESP', N'Spain', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'EST', N'Estonia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ETH', N'Ethiopia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FIN', N'Finland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FJI', N'Fiji', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FLK', N'Falkland Islands (Malvinas)', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FRA', N'France', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FRO', N'Faeroe Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FSM', N'Micronesia, Federated States of', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GAB', N'Gabon', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GBR', N'United Kingdom of Great Britain and Northern Ireland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GEO', N'Georgia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GGY', N'Guernsey', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GHA', N'Ghana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GIB', N'Gibraltar', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GIN', N'Guinea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GLP', N'Guadeloupe', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GMB', N'Gambia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GNB', N'Guinea-Bissau', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GNQ', N'Equatorial Guinea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GRC', N'Greece', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GRD', N'Grenada', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GRL', N'Greenland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GTM', N'Guatemala', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GUF', N'French Guiana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GUM', N'Guam', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'GUY', N'Guyana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HKG', N'Hong Kong Special Administrative Region of China', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HND', N'Honduras', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HRV', N'Croatia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HTI', N'Haiti', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HUN', N'Hungary', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IDN', N'Indonesia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IMN', N'Isle of Man', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IND', N'India', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IRL', N'Ireland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IRN', N'Iran, Islamic Republic of', GETDATE(), 1, NULL, NULL)
GO
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IRQ', N'Iraq', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ISL', N'Iceland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ISR', N'Israel', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ITA', N'Italy', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'JAM', N'Jamaica', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'JEY', N'Jersey', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'JOR', N'Jordan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'JPN', N'Japan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KAZ', N'Kazakhstan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KEN', N'Kenya', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KGZ', N'Kyrgyzstan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KHM', N'Cambodia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KIR', N'Kiribati', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KNA', N'Saint Kitts and Nevis', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KOR', N'Republic of Korea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'KWT', N'Kuwait', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LAO', N'Lao People''s Democratic Republic', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LBN', N'Lebanon', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LBR', N'Liberia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LBY', N'Libyan Arab Jamahiriya', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LCA', N'Saint Lucia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LIE', N'Liechtenstein', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LKA', N'Sri Lanka', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LSO', N'Lesotho', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LTU', N'Lithuania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LUX', N'Luxembourg', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'LVA', N'Latvia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MAC', N'Macao Special Administrative Region of China', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MAF', N'Saint-Martin (French part)', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MAR', N'Morocco', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MCO', N'Monaco', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MDA', N'Moldova', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MDG', N'Madagascar', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MDV', N'Maldives', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MEX', N'Mexico', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MHL', N'Marshall Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MKD', N'The former Yugoslav Republic of Macedonia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MLI', N'Mali', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MLT', N'Malta', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MMR', N'Myanmar', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MNE', N'Montenegro', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MNG', N'Mongolia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MNP', N'Northern Mariana Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MOZ', N'Mozambique', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MRT', N'Mauritania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MSR', N'Montserrat', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MTQ', N'Martinique', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MUS', N'Mauritius', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MWI', N'Malawi', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MYS', N'Malaysia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MYT', N'Mayotte', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NAM', N'Namibia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NCL', N'New Caledonia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NER', N'Niger', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NFK', N'Norfolk Island', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NGA', N'Nigeria', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NIC', N'Nicaragua', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NIU', N'Niue', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NLD', N'Netherlands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NOR', N'Norway', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NPL', N'Nepal', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NRU', N'Nauru', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NZL', N'New Zealand', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'OMN', N'Oman', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PAK', N'Pakistan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PAN', N'Panama', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PCN', N'Pitcairn', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PER', N'Peru', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PHL', N'Philippines', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PLW', N'Palau', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PNG', N'Papua New Guinea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'POL', N'Poland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PRI', N'Puerto Rico', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PRK', N'Democratic People''s Republic of Korea', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PRT', N'Portugal', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PRY', N'Paraguay', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PSE', N'Occupied Palestinian Territory', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PYF', N'French Polynesia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'QAT', N'Qatar', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'REU', N'R_union', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ROU', N'Romania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RUS', N'Russian Federation', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RWA', N'Rwanda', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SAU', N'Saudi Arabia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SDN', N'Sudan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SEN', N'Senegal', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SGP', N'Singapore', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SHN', N'Saint Helena', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SJM', N'Svalbard and Jan Mayen Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SLB', N'Solomon Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SLE', N'Sierra Leone', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SLV', N'El Salvador', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SMR', N'San Marino', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SOM', N'Somalia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SPM', N'Saint Pierre and Miquelon', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SRB', N'Serbia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'STP', N'Sao Tome and Principe', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SUR', N'Suriname', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SVK', N'Slovakia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SVN', N'Slovenia', GETDATE(), 1, NULL, NULL)
GO
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SWE', N'Sweden', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SWZ', N'Swaziland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SYC', N'Seychelles', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SYR', N'Syrian Arab Republic', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TCA', N'Turks and Caicos Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TCD', N'Chad', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TGO', N'Togo', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'THA', N'Thailand', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TJK', N'Tajikistan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TKL', N'Tokelau', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TKM', N'Turkmenistan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TLS', N'Timor-Leste', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TON', N'Tonga', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TTO', N'Trinidad and Tobago', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TUN', N'Tunisia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TUR', N'Turkey', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TUV', N'Tuvalu', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TZA', N'United Republic of Tanzania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'UGA', N'Uganda', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'UKR', N'Ukraine', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'URY', N'Uruguay', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'USA', N'United States of America', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'UZB', N'Uzbekistan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VAT', N'Holy See', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VCT', N'Saint Vincent and the Grenadines', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VEN', N'Venezuela (Bolivarian Republic of)', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VGB', N'British Virgin Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VIR', N'United States Virgin Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VNM', N'Viet Nam', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'VUT', N'Vanuatu', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'WLF', N'Wallis and Futuna Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'WSM', N'Samoa', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'YEM', N'Yemen', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ZAF', N'South Africa', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ZMB', N'Zambia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[CNTRY_CD] ([CNTRY_CD], [CNTRY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'ZWE', N'Zimbabwe', GETDATE(), 1, NULL, NULL)



SET IDENTITY_INSERT [dbo].[ST_CD] ON 

INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, N'AL', N'USA', N'Alabama', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, N'AK', N'USA', N'Alaska', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, N'AZ', N'USA', N'Arizona', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, N'AR', N'USA', N'Arkansas', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, N'CA', N'USA', N'California', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, N'CO', N'USA', N'Colorado', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, N'CT', N'USA', N'Connecticut', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, N'DE', N'USA', N'Delaware', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, N'DC', N'USA', N'District of Columbia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, N'FL', N'USA', N'Florida', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, N'GA', N'USA', N'Georgia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, N'HI', N'USA', N'Hawaii', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, N'ID', N'USA', N'Idaho', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, N'IL', N'USA', N'Illinois', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, N'IN', N'USA', N'Indiana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, N'IA', N'USA', N'Iowa', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, N'KS', N'USA', N'Kansas', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, N'KY', N'USA', N'Kentucky', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, N'LA', N'USA', N'Louisiana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (20, N'ME', N'USA', N'Maine', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (21, N'MD', N'USA', N'Maryland', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (22, N'MA', N'USA', N'Massachusetts', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (23, N'MI', N'USA', N'Michigan', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (24, N'MN', N'USA', N'Minnesota', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (25, N'MS', N'USA', N'Mississippi', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (26, N'MO', N'USA', N'Missouri', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (27, N'MT', N'USA', N'Montana', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (28, N'NE', N'USA', N'Nebraska', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (29, N'NV', N'USA', N'Nevada', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (30, N'NH', N'USA', N'New Hampshire', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (31, N'NJ', N'USA', N'New Jersey', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (32, N'NM', N'USA', N'New Mexico', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (33, N'NY', N'USA', N'New York', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (34, N'NC', N'USA', N'North Carolina', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (35, N'ND', N'USA', N'North Dakota', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (36, N'OH', N'USA', N'Ohio', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (37, N'OK', N'USA', N'Oklahoma', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (38, N'OR', N'USA', N'Oregon', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (39, N'PA', N'USA', N'Pennsylvania', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (40, N'RI', N'USA', N'Rhode Island', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (41, N'SC', N'USA', N'South Carolina', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (42, N'SD', N'USA', N'South Dakota', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (43, N'TN', N'USA', N'Tennessee', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (44, N'TX', N'USA', N'Texas', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (45, N'UT', N'USA', N'Utah', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (46, N'VT', N'USA', N'Vermont', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (47, N'VA', N'USA', N'Virginia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (48, N'WA', N'USA', N'Washington', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (49, N'WV', N'USA', N'West Virginia', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (50, N'WI', N'USA', N'Wisconsin', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (51, N'WY', N'USA', N'Wyoming', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (52, N'AS', N'USA', N'American Samoa', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (53, N'GU', N'USA', N'Guam', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (54, N'MP', N'USA', N'Northern Mariana Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (55, N'PR', N'USA', N'Puerto Rico', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (56, N'UM', N'USA', N'U.S. Minor Outlying Islands', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[ST_CD] ([ST_CD_KEY], [ST_CD], [CNTRY_CD], [ST_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (57, N'VI', N'USA', N'U.S. Virgin Islands', GETDATE(), 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[ST_CD] OFF


SET IDENTITY_INSERT [dbo].[PERMSN] ON 

INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, NULL, 4, N'Administration', N'Administration Module', N'Administration', 1, 1, CAST(0x0000A64900D65BDA AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, NULL, 4, N'Client Engagement', N'Client Engagement Module', N'Client Engagement', 1, 1, CAST(0x0000A64900D65BDA AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, NULL, 4, N'Engagement Data Upload', N'Engagement Data Upload Module', N'Engagement Data Upload', 1, 1, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, 1, NULL, 4, N'Analyst', N'Analyst Module', N'Analyst', 1, 1, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, 1, NULL, 4, N'Reporting', N'Reporting Module', N'Reporting', 1, 1, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, 1, 1, 5, N'Manage Roles & Permissions', N'Manage Roles & Permissions Menu', N'Manage Roles & Permissions', 1, 1, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, 1, 1, 5, N'Manage Organizations', N'Manage Client Information Menu', N'Manage Client Information', 0, 2, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, 1, 1, 5, N'Manage Users', N'Manage Partner Information Menu', N'Manage Partner Information', 0, 3, CAST(0x0000A64900D65BDB AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, 1, 1, 5, N'User Activation', N'User Activation Menu', N'User Activation', 0, 3, CAST(0x0000A64900D65BDC AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, 1, 6, 6, N'Manage Roles', N'Manage Roles Sub Menu', N'Manage Roles', 0, 1, CAST(0x0000A64900D65BDC AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, 1, 6, 6, N'Manage Permission Groups', N'Manage Permission Groups Sub Menu', N'Manage Permission Groups', 0, 2, CAST(0x0000A64900D65BDC AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, 1, 6, 6, N'View Detailed Permissions', N'View Detailed Permissions Sub Menu', N'View Detailed Permissions', 0, 3, CAST(0x0000A64900D65BDC AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, 1, 10, 7, N'Add Role', N'Role for add role Google India', N'Add Role', 0, 1, CAST(0x0000A64900D65BDC AS DateTime), 1, CAST(0x0000A6B8010116DF AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, 1, 10, 7, N'Update Role', N'Roles Permission
manage permisson/ view detailed permisson', N'Update Role', 0, 2, CAST(0x0000A64900D65BDC AS DateTime), 1, CAST(0x0000A6B801021A85 AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, 1, 10, 7, N'View Role', N'Roles Permissionss', N'View Role', 0, 3, CAST(0x0000A64900D65BDC AS DateTime), 1, CAST(0x0000A6B201210C03 AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, 1, 10, 7, N'Delete Role', N'Roles Permissions', N'Delete Role', 0, 4, CAST(0x0000A64900D65BDD AS DateTime), 1, CAST(0x0000A6AD00D226C1 AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, 1, 11, 7, N'Add Permission Group', N'Add Permission Group Permissions', N'Add Permission Group', 0, 1, CAST(0x0000A64900D65BDD AS DateTime), 1, CAST(0x0000A6AB012C63CF AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, 1, 11, 7, N'Update Permission Group', N'Update Permission Group Permission', N'Update Permission Group', 0, 2, CAST(0x0000A64900D65BDD AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, 1, 11, 7, N'View Permission Group', N'View Permission Group Permission
view detailed permisson', N'View Permission Group', 0, 3, CAST(0x0000A64900D65BDD AS DateTime), 1, CAST(0x0000A6B801010E63 AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (20, 1, 12, 7, N'View Detailed Permissions', N'View Detailed Permissions', N'View Detailed Permissions Permission', 0, 1, CAST(0x0000A64900D65BDD AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (21, 1, 7, 6, N'Manage Organizations Sub Menu', N'Manage Organizations Sub Menu', N'Manage Organizations Sub Menu', 0, 1, CAST(0x0000A64900D65BDD AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (22, 1, 21, 7, N'Add Organization', N'Add Organization Permission', N'Add Organization', 0, 1, CAST(0x0000A64900D65BDE AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (23, 1, 21, 7, N'Update Organization', N'Update Organization Permission', N'Update Organization', 0, 2, CAST(0x0000A64900D65BDE AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (24, 1, 21, 7, N'View Organization', N'View Organization Permission', N'View Organization', 0, 3, CAST(0x0000A64900D65BDE AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (25, 1, 8, 6, N'Manage Users Sub menu', N'Manage Users Sub Menu', N'Manage Users Sub menu', 0, 1, CAST(0x0000A64900D65BDE AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (26, 1, 25, 7, N'Add User', N'Add User Permission', N'Add User', 0, 1, CAST(0x0000A64900D65BDE AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (27, 1, 25, 7, N'Update User', N'Update User Permission', N'Update User', 0, 2, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (28, 1, 25, 7, N'View User', N'View User Permission', N'View User', 0, 3, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (29, 1, 9, 6, N'User Activation Sub menu', N'User Activation Sub Menu', N'User Activation Sub menu', 0, 1, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (30, 1, 29, 7, N'Update User Activation', N'Update User Activation Permission', N'Update User Activation', 0, 1, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (31, 1, 29, 7, N'View User Activation', N'View User Activation Permission', N'View User Activation', 0, 2, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (32, 1, 11, 7, N'Delete Permission Group', N'Delete Permission Group Permission', N'Delete Permission Group', 0, 3, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (33, 1, 12, 7, N'Edit Detailed Permission Description', N'Edit Detailed Permissions Description', N'Edit Detailed Permission Description', 0, 1, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (34, 1, 2, 5, N'Client Engagement', N'Client Engagement Menu', N'Client Engagement', 1, 1, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (35, 1, 34, 6, N'Manage Engagements', N'Manage Engagements Submenu', N'Manage Engagements', 0, 1, CAST(0x0000A64900D65BDF AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (36, 1, 35, 7, N'Add Engagement', N'Add Engagement permission', N'Add Engagement', 0, 1, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (37, 1, 35, 7, N'Edit Engagement', N'Edit Engagement permission', N'Edit Engagement', 0, 2, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (38, 1, 35, 7, N'View Engagement', N'View Engagement permission', N'View Engagement', 0, 3, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (39, 1, 35, 7, N'Delete Engagement', N'Delete Engagement permission', N'Delete Engagement', 0, 4, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (40, 1, 35, 7, N'Add User to Service', N'Add User to Service permission', N'Add User to Service', 0, 5, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (41, 1, 35, 7, N'Edit User to Service', N'Edit User to Service permission', N'Edit User to Service', 0, 6, CAST(0x0000A64900D65BE0 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (42, 1, 35, 7, N'View User to Service', N'View User to Service permission', N'View User to Service', 0, 7, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (43, 1, 35, 7, N'Delete User to Service', N'Delete User to Service permission', N'Delete User to Service', 0, 8, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (45, 1, 3, 5, N'Engagement Data Upload', N'Engagement Data Upload Menu', N'Engagement Data Upload', 0, 1, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (46, 1, 45, 6, N'Engagement Data Upload', N'Engagement Data Upload Submenu', N'Engagement Data Upload', 0, 1, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (47, 1, 46, 7, N'Add Document upload', N'Add Engagement Data upload permission', N'Add Engagement Data upload', 0, 1, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (48, 1, 46, 7, N'Edit Document upload', N'Edit Engagement Data upload permission', N'Edit Engagement Data upload', 0, 2, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (49, 1, 46, 7, N'View Document upload', N'View Engagement Data upload permission', N'View Engagement Data upload', 0, 3, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (50, 1, 46, 7, N'Delete Document upload', N'Delete Engagement Data upload permission', N'Delete Engagement Data upload', 0, 4, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (51, 1, 46, 7, N'Lock Service Data', N'Lock Service Data', N'Lock Service Data', 0, 5, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (52, 1, 4, 5, N'Analyst Validation Module', N'Manage Users Menu', N'Analyst Validation Module menu', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (53, 1, 52, 6, N'Analyst Validation Module Sub menu', N'Analyst Validation Module Sub Menu', N'Analyst Validation Module Sub menu', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (54, 1, 53, 7, N'View Vulnerabilities', N'View Vulnerabilities', N'Analyst Validation View Vulnerability', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (55, 1, 53, 7, N'Review Vulnerabilities', N'Review Vulnerabilities', N'Analyst Validation Review Vulnerability', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (56, 1, 5, 5, N'Client Reports Upload', N'Client Reports Upload Menu', N'Client Reports Upload', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (57, 1, 56, 6, N'Client Reports Upload', N'Client Reports Upload Submenu', N'Client Reports Upload', 0, 1, CAST(0x0000A64900D65BE2 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (58, 1, 57, 7, N'Upload Client Reports', N'Upload Client Reports permission', N'Upload Client Reports', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (59, 1, 57, 7, N'View Client Reports', N'View Client Reports permission', N'View Client Reports', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (60, 1, 57, 7, N'Publish or Unpublish Reports', N'Publish or Unpublish Reports permission', N'Publish or Unpublish Reports', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (61, 1, 57, 7, N'View Dashboard', N'View Dashboard permission', N'View Dashboard', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (62, 1, 57, 7, N'Publish Dashboard', N'Publish Dashboard permission', N'Publish Dashboard', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (63, 1, 5, 5, N'Remediation Tracking', N'Remediation Tracking Menu', N'Remediation Tracking', 0, 1, CAST(0x0000A64900D65BE3 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (64, 1, 63, 6, N'Remediation Tracking', N'Remediation Tracking Submenu', N'Remediation Tracking', 0, 1, CAST(0x0000A64900D65BE4 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (65, 1, 64, 7, N'Remediate', N'Remediate permission', N'Remediate', 0, 1, CAST(0x0000A64900D65BE4 AS DateTime), 1, CAST(0x0000A65000357C6C AS DateTime), 0)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (66, 1, 64, 7, N'View Remediation', N'View Remediation permission', N'View Remediation', 0, 1, CAST(0x0000A64900D65BE4 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (67, 1, 5, 5, N'Dashboard', N'Dashboard', N'Dashboard Menu', 0, 1, CAST(0x0000A6C100FFD145 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (68, 1, 67, 6, N'Dashboard', N'Dashboard', N'Dashboard Submenu', 1, 1, CAST(0x0000A6C100FFD551 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (69, 1, 68, 7, N'Dashboard', N'Dashboard', N'Dashboard Permission', 0, 1, CAST(0x0000A6C100FFD948 AS DateTime), 1, NULL, NULL)
INSERT [dbo].[PERMSN] ([PERMSN_KEY], [ROW_STS_KEY], [PAR_PERMSN_KEY], [PERMSN_TYP_KEY], [PERMSN_NM], [PERMSN_DESC], [DSPL_TXT], [CHLD_XST_IND], [SEQ_ORDR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (44, 1, 46, 7, N'Unlock Service Data', N'Unlock Service Data permission', N'Unlock Service Data', 0, 9, CAST(0x0000A64900D65BE1 AS DateTime), 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[PERMSN] OFF
SET IDENTITY_INSERT [dbo].[PERMSN_GRP] ON 

INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, N'Security Admin - Manage Organization and User', N'Allows the user to manage the Organizations and Users. (i.e. Add, Edit, and View). Also, allows the user to manage the User Activation. (i.e. Unlock the locked accounts and Activate the Inactive accounts)', NULL, CAST(0x0000A6BB00B690F5 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, N'Manage Roles & Permissions', N'Allows the user to manage the Roles and Permissions. (i.e. Add, Edit, and View Roles)', NULL, CAST(0x0000A6BB010635CF AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, N'Client Engagement - Manage Engagements', N'Allows users to Manage Engagements. (i.e. Create, Update, View, and Delete)', NULL, CAST(0x0000A6BE00F5CE96 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, 1, N'Client Engagement - Manage Engagement User', N'Allows the users to Manage users to the services (i.e. Add, Update, and View)', NULL, CAST(0x0000A6BE00F622FA AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, 1, N'Engagement Data Upload - Upload', N'Allows the user to respond to the Services (i.e. Allows the user to Add, Update, View, and Delete the documents for the services) and  Lock the service data.', NULL, CAST(0x0000A6BE00F67299 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, 1, N'Engagement Data Upload - Unlock', N'Allows the user to Unlock the service data', NULL, CAST(0x0000A6BE00F6E910 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, 1, N'Vulnerabilities - Review', N'Allows to ''Add, Edit, Remove'' the ''Vulnerabilities'' in the engagement, and as well allows to do the ''Review Complete'' action.  Also, allows the user to ''View, and Edit'' the remediation status', NULL, CAST(0x0000A6BE00F744A1 AS DateTime), 3, CAST(0x0000A6C1011E91F6 AS DateTime), 3)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, 1, N'Report and Dashboards - RW', N'Allows the user to ''Add, View, Delete, and Publish'' the reports, and allows the user to View and Publish the Dashboard to client', NULL, CAST(0x0000A6BE00F7BBA1 AS DateTime), 3, CAST(0x0000A6C1011FE973 AS DateTime), 3)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, 1, N'Report and Dashboards - RO', N'View Reports and Dashboards', NULL, CAST(0x0000A6BE00F84C1C AS DateTime), 3, CAST(0x0000A6C101168B38 AS DateTime), 3)
INSERT [dbo].[PERMSN_GRP] ([PERMSN_GRP_KEY], [ROW_STS_KEY], [PERMSN_GRP_NM], [PERMSN_GRP_DESC], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, 1, N'Report and Dashboards - Remediate', N'Report and Dashboards - Remediate', NULL, CAST(0x0000A6BE00FB4E2E AS DateTime), 3, CAST(0x0000A6C101162D55 AS DateTime), 3)
SET IDENTITY_INSERT [dbo].[PERMSN_GRP] OFF
SET IDENTITY_INSERT [dbo].[PERMSN_GRP_ASSOC] ON 

INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, 1, 22, 1, 7, 21, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, 1, 23, 1, 7, 21, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, 1, 24, 1, 7, 21, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, 1, 1, 26, 1, 8, 25, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, 1, 1, 27, 1, 8, 25, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, 1, 1, 28, 1, 8, 25, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, 1, 1, 30, 1, 9, 29, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, 1, 1, 31, 1, 9, 29, CAST(0x0000A6BB00B69916 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, 1, 2, 13, 1, 6, 10, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, 1, 2, 14, 1, 6, 10, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, 1, 2, 15, 1, 6, 10, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, 1, 2, 16, 1, 6, 10, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, 1, 2, 17, 1, 6, 11, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, 1, 2, 18, 1, 6, 11, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, 1, 2, 19, 1, 6, 11, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, 1, 2, 20, 1, 6, 12, CAST(0x0000A6BB01064360 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, 1, 3, 36, 2, 34, 35, CAST(0x0000A6BE00F5CE97 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, 1, 3, 37, 2, 34, 35, CAST(0x0000A6BE00F5CE98 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, 1, 3, 38, 2, 34, 35, CAST(0x0000A6BE00F5CE98 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (20, 1, 3, 39, 2, 34, 35, CAST(0x0000A6BE00F5CE99 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (21, 1, 4, 40, 2, 34, 35, CAST(0x0000A6BE00F622FA AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (22, 1, 4, 41, 2, 34, 35, CAST(0x0000A6BE00F622FB AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (23, 1, 4, 42, 2, 34, 35, CAST(0x0000A6BE00F622FB AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (24, 1, 4, 43, 2, 34, 35, CAST(0x0000A6BE00F622FB AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (25, 1, 5, 47, 3, 45, 46, CAST(0x0000A6BE00F67299 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (26, 1, 5, 48, 3, 45, 46, CAST(0x0000A6BE00F67299 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (27, 1, 5, 49, 3, 45, 46, CAST(0x0000A6BE00F6729A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (28, 1, 5, 50, 3, 45, 46, CAST(0x0000A6BE00F6729A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (29, 1, 5, 51, 3, 45, 46, CAST(0x0000A6BE00F6729A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (30, 1, 6, 44, 3, 45, 46, CAST(0x0000A6BE00F6E911 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (48, 1, 10, 65, 5, 63, 64, CAST(0x0000A6C101162D65 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (49, 1, 10, 69, 5, 67, 68, CAST(0x0000A6C101162D65 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (50, 1, 9, 59, 5, 56, 57, CAST(0x0000A6C101168B39 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (51, 1, 9, 69, 5, 67, 68, CAST(0x0000A6C101168B3A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (68, 1, 7, 54, 4, 52, 53, CAST(0x0000A6C1011E91F8 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (69, 1, 7, 55, 4, 52, 53, CAST(0x0000A6C1011E91F8 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (72, 1, 8, 58, 5, 56, 57, CAST(0x0000A6C1011FE975 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (73, 1, 8, 59, 5, 56, 57, CAST(0x0000A6C1011FE976 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (74, 1, 8, 60, 5, 56, 57, CAST(0x0000A6C1011FE976 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (75, 1, 8, 61, 5, 56, 57, CAST(0x0000A6C1011FE976 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (76, 1, 8, 62, 5, 56, 57, CAST(0x0000A6C1011FE977 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[PERMSN_GRP_ASSOC] ([PERMSN_GRP_ASSOC_KEY], [ROW_STS_KEY], [PERMSN_GRP_KEY], [PERMSN_KEY], [MDUL_ID], [MNU_ID], [SUB_MNU_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (77, 1, 8, 69, 5, 67, 68, CAST(0x0000A6C1011FE977 AS DateTime), 3, NULL, NULL)
SET IDENTITY_INSERT [dbo].[PERMSN_GRP_ASSOC] OFF
SET IDENTITY_INSERT [dbo].[APPL_ROLE] ON 

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
SET IDENTITY_INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ON 

INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, 1, 1, CAST(0x0000A6BB00B71869 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, 2, 2, CAST(0x0000A6BB0106AFDF AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, 3, 3, CAST(0x0000A6BE00FB9A88 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, 1, 4, 7, CAST(0x0000A6BE00FBE776 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, 1, 4, 8, CAST(0x0000A6BE00FBE776 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, 1, 4, 6, CAST(0x0000A6BE00FBE777 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, 1, 4, 5, CAST(0x0000A6BE00FBE777 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, 1, 4, 4, CAST(0x0000A6BE00FBE777 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, 1, 5, 9, CAST(0x0000A6BE00FC2845 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, 1, 5, 5, CAST(0x0000A6BE00FC2846 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, 1, 5, 4, CAST(0x0000A6BE00FC2846 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, 1, 6, 9, CAST(0x0000A6BE00FD0DF7 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, 1, 6, 5, CAST(0x0000A6BE00FD0DF8 AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, 1, 7, 7, CAST(0x0000A6BE00FD457A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, 1, 7, 8, CAST(0x0000A6BE00FD457A AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, 1, 7, 6, CAST(0x0000A6BE00FD457B AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, 1, 7, 5, CAST(0x0000A6BE00FD457C AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, 1, 8, 9, CAST(0x0000A6BE00FD839E AS DateTime), 3, NULL, NULL)
INSERT [dbo].[APPL_ROLE_PERMSN_GRP] ([USER_ROLE_PERMSN_GRP_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [PERMSN_GRP_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, 1, 9, 10, CAST(0x0000A6BE00FDD51E AS DateTime), 3, NULL, NULL)
SET IDENTITY_INSERT [dbo].[APPL_ROLE_PERMSN_GRP] OFF



SET IDENTITY_INSERT [dbo].[USER_PRFL] ON 

INSERT [dbo].[USER_PRFL] ([USER_ID], [ROW_STS_KEY], [ORG_KEY], [USER_TYP_KEY], [FST_NM], [LST_NM], [MIDL_NM], [JOB_TITL_NM], [EMAIL_ID], [TEL_NBR], [MAC_ADR_NM], [USER_VERF_IND], [LCK_IND], [LOGIN_ATMPT_CNT], [LST_LOGIN_DT], [PSWD_RSET_DT], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, 1, 16, N'Super', N'Ten', N'', N'', N'ossadmin@healthitplus.net', N'', NULL, 1, 0, 0, GETDATE(), GETDATE(), NULL, GETDATE(), '2', NULL, NULL)
INSERT [dbo].[USER_PRFL] ([USER_ID], [ROW_STS_KEY], [ORG_KEY], [USER_TYP_KEY], [FST_NM], [LST_NM], [MIDL_NM], [JOB_TITL_NM], [EMAIL_ID], [TEL_NBR], [MAC_ADR_NM], [USER_VERF_IND], [LCK_IND], [LOGIN_ATMPT_CNT], [LST_LOGIN_DT], [PSWD_RSET_DT], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, 1, 16, N'ETL', N'Admin', N'', N'', N'odsdevadmin@healthitplus.net', N'', NULL, 1, 0, 0, GETDATE(), GETDATE(), NULL, GETDATE(), '2', NULL, NULL)
INSERT [dbo].[USER_PRFL] ([USER_ID], [ROW_STS_KEY], [ORG_KEY], [USER_TYP_KEY], [FST_NM], [LST_NM], [MIDL_NM], [JOB_TITL_NM], [EMAIL_ID], [TEL_NBR], [MAC_ADR_NM], [USER_VERF_IND], [LCK_IND], [LOGIN_ATMPT_CNT], [LST_LOGIN_DT], [PSWD_RSET_DT], [STS_COMMT_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, 1, 16, N'CStest', N'10', N'', N'', N'ossdevadmin@healthitplus.net', N'', NULL, 1, 0, 0, GETDATE(), GETDATE(), NULL, GETDATE(), '2', NULL, NULL)
SET IDENTITY_INSERT [dbo].[USER_PRFL] OFF

SET IDENTITY_INSERT [dbo].[USER_APPL_ROLE] ON 

INSERT [dbo].[USER_APPL_ROLE] ([USER_APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [USER_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, 1, 1, CAST(0x0000A6BB00B8735F AS DateTime), 1, NULL, NULL)
INSERT [dbo].[USER_APPL_ROLE] ([USER_APPL_ROLE_KEY], [ROW_STS_KEY], [APPL_ROLE_KEY], [USER_ID], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, 2, 3, CAST(0x0000A6BB00D4AD85 AS DateTime), 1, NULL, NULL)

SET IDENTITY_INSERT [dbo].[USER_APPL_ROLE] OFF
SET IDENTITY_INSERT [USER_SECUR_DTL] ON
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (1, 1, 1, 20, N'Optum', 0, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (2, 1, 1, 21, N'Optum', 0, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[USER_SECUR_DTL] ([USER_SECUR_DTL_KEY], [ROW_STS_KEY], [USER_ID], [SECUR_QUES_KEY], [ANS_TXT], [SEQ_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (3, 1, 1, 22, N'Optum', 0, GETDATE(), 1, NULL, NULL)
SET IDENTITY_INSERT [USER_SECUR_DTL] OFF


GO
SET IDENTITY_INSERT [dbo].[NTF_MSG] ON 

INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, 1, N'MAIL_USER_ACCOUNT_CREATION', N'Your Optum Security Web Portal account has been created', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
Thank you for enrolling and welcome to the Optum Security Web Portal.
<br/><br/>
Your Optum Security Web Portal account with username $$USER_EMAIL$$ has been successfully created.  A notification email containing your account password will be sent shortly.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, 1, N'MAIL_USER_PASSWORD_CREATION', N'Your Optum Security Web Portal account password', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
Your Optum Security Web Portal one-time use password is  $$USER_PASSWORD$$
<br/><br/>
Log in to the Optum Security Web Portal with your username and this password. Once you log in successfully, you will be required to change this password.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, 1, N'MAIL_USER_PROFILE_PASSWORD_CHANGED', N'Your Optum Security Web Portal password has been changed', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The password for your Optum Security Web Portal account with username "$$USER_NAME$$" was successfully changed. You may log in to Optum Security Web Portal with your new password.
<br/><br/>
If you did not make this change, please contact the portal administrator.
<br/>
<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, 1, N'MAIL_TO_ADMIN_IF_USER_CHANGES_PASSWORD_FIRST_TIME', N'Password for Optum Security Web Portal account with username “$$USER_NAME$$” has been changed', N'$$IMG$$<br/><br/>
Dear $$ADMIN_NAME$$,
<br/><br/>
The password for Optum Security Web Portal account with username "$$USER_NAME$$" has been successfully changed. 
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, 1, N'MAIL_USER_PROFILE_PASSWORD_UPDATE', N'Your Optum Security Web Portal profile has been updated', N'$$IMG$$<br/><br/>
Dear $$USER_NAME_1$$,
<br/><br/>
Your Optum Security Web Portal profile "$$USER_NAME_2$$" was successfully updated. You may log in to Optum Security Web Portal to access your account. 
<br/><br/>
If you did not make changes to your account profile, please contact the portal administrator.
<br/>
<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, 1, N'MAIL_USER_ACCOUNT_DEACTIVATED', N'Your Optum Security Web Portal account has been deactivated', N'$$IMG$$<br/><br/>
Dear $$USER_NAME_1$$,
<br/><br/>
Your Optum Security Web Portal account with username "$$USER_NAME_2$$" was deactivated. 
<br/><br/>
Please contact the portal administrator.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, 1, N'MAIL_USER_ACCOUNT_REACTIVATED', N'Your Optum Security Web Portal account has been reactivated', N'$$IMG$$<br/><br/>
Dear $$USER_NAME_1$$,
<br/><br/>
Your Optum Security Web Portal account with username "$$USER_NAME_2$$" was reactivated. A temporary password will be sent in a separate email.
<br/><br/>
If you did not request the account reactivation, please contact the portal administrator.
<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, 1, N'MAIL_ON_NOTIFY_ACCOUNT_PASSWORD_EXPIRATION', N'Your Optum Security Web Portal account password is about to expire', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/>
Your Optum Security Web Portal account password is about to expire. You must log in and change the password before “$$DATE_OF_EXPIRATION$$” to prevent your account from being locked. 
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-09-08 08:05:22.220' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, 1, N'MAIL_USER_ACCOUNT_LOCKED', N'Your Optum Security Web Portal account has been locked', N'$$IMG$$<br/><br/>
Dear $$USER_NAME_1$$,
<br/>
Your Optum Security Web Portal account with username "$$USER_NAME_1$$" was locked because the maximum number of incorrect login attempts was exceeded/of account inactivity for the past 60 days/you failed to provide correct answers to the security questions. 
<br/><br/>
Please contact the portal administrator for assistance.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved.</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, 1, N'MAIL_TO_ADMIN_USER_ACCOUNT_LOCKED', N'Optum Security Web Portal account with username “$$USER_NAME_1$$” has been locked', N'$$IMG$$<br/><br/>
Dear $$USER_NAME_1$$,
<br/>
The Optum Security Web Portal account with username "$$LOCKED_USER_NAME$$" was locked because the maximum number of incorrect login attempts was exceeded/of inactivity for the past 60 days/the user failed to provide correct answers to the security questions. 
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (11, 1, N'MAIL_USER_ACCOUNT_UNLOCKED', N'Your Optum Security Web Portal account has been unlocked', N'$$IMG$$<br/><br/> 
Dear $$USER_NAME_1$$,
<br/><br/>
Your Optum Security Web Portal account with username "$$USER_NAME_2$$" was unlocked and the one-time use Password is $$PASSWORD$$ <br/>
<br/><br/>
You may login to the Optum Security Web Portal with your username and this password. 
<br/>
Once you log in successfully, you will be required to change this password.
<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>
', CAST(N'2016-07-20 13:00:27.430' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (12, 1, N'EMAIL_USER_LOGGEING_FROM_DIFFERENT_BROWSER', N'New log in from < browser/device >', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
Your Optum Security Web Portal account with username "$$USER_NAME$$" was recently accessed from a new <device/browser>.
<br/>
If you did not initiate this activity, please contact the portal administrator.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.433' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (13, 1, N'MAIL_ENGMNT_CREATION', N'Engagement "$$ENG_CODE$$" has been created', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The engagement “$$ENG_CODE$$” was successfully created with the following engagement details:
<br/><br/>
Engagement Code: $$ENG_CODE$$<br/>
Client Name: $$CLNT_ORG_NAME$$<br/>
Engagement Package Name: $$PKG_NAME$$<br/>
Projected Engagement Start Date: $$ENG_STRT_DATE$$<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.433' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (14, 1, N'MAIL_ENGMNT_UPDATION', N'Engagement details for the “$$ENG_CODE$$” have been updated', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The engagement details for the “$$ENG_CODE$$” were successfully updated by $$UPDATED_USER_NAME$$ as follows:
<br/><br/>
Engagement Code: $$ENG_CODE$$<br/>
Client Name: $$CLNT_ORG_NAME$$<br/>
Engagement Package Name: $$PKG_NAME$$<br/>
Projected Engagement Start Date: $$ENG_STRT_DATE$$<br/>
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.433' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (15, 1, N'MAIL_ASSIGN_CLIENT_ENGMNT_LEAD', N'Point of Contact for Engagement “$$ENG_CODE$$” ', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
You have been assigned as the Engagement Lead for the engagement “$$ENG_CODE$$”. <br/>
If you believe this assignment is in error, please contact the portal administrator immediately.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.433' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (16, 1, N'MAIL_ASSIGN_PARTNER_ENGMNT_LEAD', N'Engagement “$$ENG_CODE$$” is assigned', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
You have been assigned as the Engagement Lead for the engagement “$$ENG_CODE$$”. 
<br/>
If you believe this assignment is in error, please contact the portal administrator immediately.
<br/><br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.433' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (17, 1, N'MAIL_ASSIGN_USER_SCHDULE', N'Engagement “$$ENG_CODE$$” has been scheduled', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
You have been assigned to engagement “$$ENG_CODE$$” by $$ENGMENT_SCHEDULED_BY$$.
<br/>
Please log in with your username and password to begin performing your tasks as specified by $$ENGMENT_LEAD$$.
<br/>
Please contact $$ENGMENT_LEAD$$ with any questions or concerns.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (18, 1, N'MAIL_REASSIGN_USER_SCHDULE', N'Engagement “$$ENG_CODE$$” has been reassigned', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The engagement “$$ENG_CODE$$” has been reassigned to another $$ANLST_OR_PRTNR$$ $$ASSIGNED_TO$$.
<br/>
Please ensure that any documents, files, or relevant work materials relating to this engagement are transferred to $$ASSIGNED_BY$$ and removed from your laptop or personal network drives.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (19, 1, N'MAIL_ENGMNT_DUE_DATE_PASSED', N'Engagement “$$ENG_CODE$$” Service due date has passed', N'$$IMG$$<br/><br/> 
Dear $$USER_NAME$$,
<br/><br/>
The engagement “$$ENG_CODE$$ $$SERVICE_NAME$$” due date has passed.
<br/>
Please contact $$ENGMENT_LEAD$$ to provide an estimated completion date. You should log in to the Security Web Portal to complete your tasks as soon as possible.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved.</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (20, 1, N'MAIL_ENGMNT_DUE_DATE_APRCHD', N'Engagement “$$ENG_CODE$$” due date is approaching', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The engagement “$$ENG_CODE$$” is due within 15 days of the estimated completion date. 
<br/>
If this date is in danger of not being met, contact the client and Director of Commercial Services to provide an update. Log in to your engagement  $$ENG_CODE$$ to view its details and update completion dates accordingly. Also update PPM Optics to reflect any changes in completion dates.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (21, 1, N'MAIL_TO_ENGMNT_LEAD_DUE_DATE_PASSED', N'Engagement “$$ENG_CODE$$” due date has passed', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The engagement “$$ENG_CODE$$” due date has passed. 
<br/>
Please contact your Director of Commercial Services to provide an estimated completion date. Log in to your engagement $$ENG_CODE$$ to view its details.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.437' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (22, 1, N'MAIL_TO_ENGMNTPARTNER_LEAD_FILE_UPLOAD', N'File for Engagement “$$ENG_CODE$$” service “$$SERVICE_NAME$$” has been uploaded', N'$$IMG$$<br/><br/>
Dear $$ENG_LEAD$$,
<br/>
The file "$$FILE_NAME$$" for engagement “$$ENG_CODE$$” service “$$SERVICE_NAME$$”  has been successfully uploaded. 
<br/>
Please log in with your username and password to view its details.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (23, 1, N'MAIL_TO_ENGMNTPARTNER_LEAD_ON_ALL_SERVICES_UPLOAD', N'File(s) for all services of Engagement “$$ENG_CODE$$” has been uploaded', N'$$IMG$$<br/><br/>
Dear $$ENG_LEAD$$,
<br/>
The following file(s) for the engagement “$$ENG_CODE$$” has been uploaded:
<br/>
<table><tr><th>&nbsp;</th><th>File</th><th>Uploaded By</th></tr>
$$HTML_CONTENT_OF_FILE$$
</table>
<br/>
Please log in with your username and password to view its details.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (24, 1, N'MAIL_TO_FILE_UPLOADED_USER_ON_MALICIOUS_FILE', N'File uploaded for Engagement “$$ENG_CODE$$” has failed', N'$$IMG$$<br/><br/>
Dear "$$ANALYST$$",
<br/>
The file “$$FILE_NAME$$” that you uploaded on $$DATE_TIME_STAMP$$ for engagement “$$ENG_CODE$$” has failed because it may contain a malicious threat.  
<br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (25, 1, N'MAIL_TO_LEAD_USER_ON_MALICIOUS_FILE_UPLOAD', N'File uploaded for Engagement “$$ENG_CODE$$” has failed', N'$$IMG$$<br/><br/>
Dear "$$LEAD$$",
<br/>
The file “$$FILE_NAME$$” uploaded by $$ANALYST$$ on $$DATE_TIME_STAMP$$ for engagement “$$ENG_CODE$$” has failed because it may contain a malicious threat.
<br/>
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (26, 1, N'MAIL_TO_ENGMNT_ANALYST_ON_LOCK_SERVICE', N'Service “$$SERVICE_NAME$$” for engagement “$$ENG_CODE$$” has been locked', N'$$IMG$$<br/><br/>
Dear "$$ENG_ANALYST$$",
<br/>
The Service “$$SERVICE_NAME$$” for engagement “$$ENG_CODE$$” has been locked. You can no longer upload any files.
<br/>
Please contact the portal administrator.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (27, 1, N'MAIL_TO_ENGMNT_ANALYST_ON_UNLOCK_SERVICE', N'Service “$$SERVICE_NAME$$” for engagement “$$ENG_CODE$$” has been unlocked', N'$$IMG$$<br/><br/>
Dear "$$ENG_ANALYST$$",
<br/>
The Service “$$SERVICE_NAME$$” for engagement “$$ENG_CODE$$” has been unlocked. You may log in and upload the required file(s).
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-07-20 13:00:27.440' AS DateTime), 1, NULL, NULL)
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (28, 1, N'MAIL_ENGMT_PUBLISHED', N' Engagement “$$ENG_CODE$$” final report has been published', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/>
The Reports and Dashboard for engagement “$$ENG_CODE$$” have been published to the Web Portal.
<br/>
To view details, log in with your username and password.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(N'2016-05-11 14:27:16.737' AS DateTime), 1, NULL, NULL)
 INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
 VALUES (29, 1, N'MAIL_ADMIN_CLIENT_ONBOARD_SUCCESS', N'Client "$$CLIENT_ORG_NAME$$" onboarding process has been successfully completed', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The client "$$CLIENT_ORG_NAME$$" onboarding process has been successfully completed. 
<br/><br/>
Please contact the portal administrator for proceeding further.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(0x0000A60300EE348D AS DateTime), 1, NULL, NULL)

-- 30.      Application creates an Client Organization and sends notification to the Web Application Administrator - onboard fail
INSERT [dbo].[NTF_MSG] ([NTF_MSG_KEY], [ROW_STS_KEY], [NTF_TYP_NM], [MSG_SBJ_TXT], [MSG_CNTN_TXT], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) 
VALUES (30, 1, N'MAIL_ADMIN_CLIENT_ONBOARD_FAIL', N'Client "$$CLIENT_ORG_NAME$$" onboarding process failed', N'$$IMG$$<br/><br/>
Dear $$USER_NAME$$,
<br/><br/>
The client "$$CLIENT_ORG_NAME$$" onboarding process has been failed.
<br/><br/>
Please contact the portal administrator for assistance.
<br/><br/>
Regards,<br/>
System Administrator<br/>
Optum Technology.<br/><br/>
<center>&#169; Optum Inc., $$YEAR$$ - All Rights Reserved</center><br/><br/><br/>', CAST(0x0000A60300EE348D AS DateTime), 1, NULL, NULL)
SET IDENTITY_INSERT [dbo].[NTF_MSG] OFF




INSERT [dbo].[SECUR_PKG] ([SECUR_PKG_CD], [ROW_STS_KEY], [SECUR_PKG_NM], [SECUR_PKG_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HC', 1, N'Health Check', N'This Service Package offering will look to exploit a client''s security vulnerabilities, which may lead to a security breach.  The Package will also include a proactive analysis to determine if a breach has already occurred or is in progress.  This information will allow the guidance in the prioritization and remediation of risks with the client''s current environment.  Examples of Security Assessment Services for Health Check include, but are not limited to; Threat Hunting, Limited Red Team Exercises, and Vulnerability Scanning.   (Point In Time)', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG] ([SECUR_PKG_CD], [ROW_STS_KEY], [SECUR_PKG_NM], [SECUR_PKG_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', 1, N'Triage', N'This Service Package offering will focus on identification of people, process and technology risks associated with the evolving cyber threat environment.  This includes mapping of risk findings to various compliance frameworks such as HIPAA/HITECH, PCI DSS, FDA regulations etc.  A "Get Well Plan" with an actionable Roadmap will be a deliveriable out of this Service Package.  Examples of Security Assessment Services for Triage include, but are not limited to; Penetration Testing, Security Risk Assessment, Wireless Risk Assessment, Vulnerability Scanning, Compliance and Remediation.  (Point In Time)', GETDATE(), 1, NULL, NULL)



INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AC', 2, N'Architecture Compliance Assessment', N'The IT Architecture assessment evaluates a client''s enterprise current state against it''s 3-5 year Strategic IT Plan to define changes required to satisfy future state architectural and control-based requirements.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'AV', 1, N'Application Vulnerability Assessment', N'A application vulnerability assessment is the process of testing, reviewing and analyzing an application and related components for possible security vulnerabilities. It is used to evaluate the application architecture against possible threats through the application layer.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'NV', 1, N'Network Vulnerability Assessment', N'A network vulnerability assessment is the process of testing, reviewing and analyzing computer network assets for possible security vulnerabilities. It is used to evaluate the security architecture and defense of a network against possible threats.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'PT', 1, N'Penetration Testing', N'Penetration testing (pentest) is a short term and targeted exercise against the computer system to exploint vulnerabilities and gain access to the client''s IT infrastructure, application, network, and data component''s.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RT', 1, N'Limited Red Team Assessment', N'Limited Red Team assessments consist of an independent group of highly skilled security Subject Matter Experts that will similate an attack against the clients digital infrastructure to test the client''s security defenses.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'SR', 1, N'Security Risk Assessment', N'A Security Risk Assessment is an objective analysis of the effectiveness of the current security controls that protect an organization''s assets and determination of the probability of losses to assets.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TH', 1, N'Threat Hunting (Cyber Intelligence Services)', N'Threat Hunting refers to a network discovery against known Indicators of Compromise (IoC).  The goals is to identify malicious activity beyond traditional security solutions.  It can include both manual and machine-assisted tecniques.', GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD], [ROW_STS_KEY], [SECUR_SRVC_NM], [SECUR_SRVC_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'WR', 1, N'Wireless Risk Assessment', N'A Wireless Risk Assessment is the process of testing, reviewing and analyzing a wireless network and related components to ensure the network  is hardened against unauthorized actors attacking the client''s resources.', GETDATE(), 1, NULL, NULL)



INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HC', N'AV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HC', N'NV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HC', N'RT', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'HC', N'TH', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'AC', 2, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'AV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'NV', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'PT', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'SR', 1, GETDATE(), 1, NULL, NULL)
INSERT [dbo].[SECUR_PKG_OFR] ([SECUR_PKG_CD], [SECUR_SRVC_CD], [ROW_STS_KEY], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'TR', N'WR', 1, GETDATE(), 1, NULL, NULL)



SET IDENTITY_INSERT [dbo].[CVSS_SCOR_MTRC] ON 

INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (1, N'2.0', N'Base Metric', N'Access Vector', N'AV', N'Local', N'L', CAST(0.395 AS Decimal(10, 3)), 1, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (2, N'2.0', N'Base Metric', N'Access Vector', N'AV', N'Adjacent_Network', N'A', CAST(0.646 AS Decimal(10, 3)), 1, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (3, N'2.0', N'Base Metric', N'Access Vector', N'AV', N'Network', N'N', CAST(1.000 AS Decimal(10, 3)), 1, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (4, N'2.0', N'Base Metric', N'Access Complexity', N'AC', N'High', N'H', CAST(0.350 AS Decimal(10, 3)), 2, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (5, N'2.0', N'Base Metric', N'Access Complexity', N'AC', N'Medium', N'M', CAST(0.610 AS Decimal(10, 3)), 2, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (6, N'2.0', N'Base Metric', N'Access Complexity', N'AC', N'Low', N'L', CAST(0.710 AS Decimal(10, 3)), 2, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (7, N'2.0', N'Base Metric', N'Authentication', N'Au', N'Multiple_Instances', N'M', CAST(0.450 AS Decimal(10, 3)), 3, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (8, N'2.0', N'Base Metric', N'Authentication', N'Au', N'Single_Instance', N'S', CAST(0.560 AS Decimal(10, 3)), 3, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (9, N'2.0', N'Base Metric', N'Authentication', N'Au', N'None', N'N', CAST(0.704 AS Decimal(10, 3)), 3, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (10, N'2.0', N'Base Metric', N'Confidentiality Impact', N'C', N'None', N'N', CAST(0.000 AS Decimal(10, 3)), 4, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (11, N'2.0', N'Base Metric', N'Confidentiality Impact', N'C', N'Partial', N'P', CAST(0.275 AS Decimal(10, 3)), 4, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (12, N'2.0', N'Base Metric', N'Confidentiality Impact', N'C', N'Complete', N'C', CAST(0.660 AS Decimal(10, 3)), 4, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (13, N'2.0', N'Base Metric', N'Integrity Impact', N'I', N'None', N'N', CAST(0.000 AS Decimal(10, 3)), 5, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (14, N'2.0', N'Base Metric', N'Integrity Impact', N'I', N'Partial', N'P', CAST(0.275 AS Decimal(10, 3)), 5, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (15, N'2.0', N'Base Metric', N'Integrity Impact', N'I', N'Complete', N'C', CAST(0.660 AS Decimal(10, 3)), 5, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (16, N'2.0', N'Base Metric', N'Availability Impact', N'A', N'None', N'N', CAST(0.000 AS Decimal(10, 3)), 6, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (17, N'2.0', N'Base Metric', N'Availability Impact', N'A', N'Partial', N'P', CAST(0.275 AS Decimal(10, 3)), 6, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (18, N'2.0', N'Base Metric', N'Availability Impact', N'A', N'Complete', N'C', CAST(0.660 AS Decimal(10, 3)), 6, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (19, N'2.0', N'Temporal Metric', N'Exploitability', N'E', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 7, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (20, N'2.0', N'Temporal Metric', N'Exploitability', N'E', N'Unproven that exploit exists', N'U', CAST(0.850 AS Decimal(10, 3)), 7, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (21, N'2.0', N'Temporal Metric', N'Exploitability', N'E', N'Proof of concept code', N'POC', CAST(0.900 AS Decimal(10, 3)), 7, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (22, N'2.0', N'Temporal Metric', N'Exploitability', N'E', N'Functional exploit exists', N'F', CAST(0.950 AS Decimal(10, 3)), 7, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (23, N'2.0', N'Temporal Metric', N'Exploitability', N'E', N'High', N'H', CAST(1.000 AS Decimal(10, 3)), 7, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (24, N'2.0', N'Temporal Metric', N'Remediation Level', N'RL', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 8, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (25, N'2.0', N'Temporal Metric', N'Remediation Level', N'RL', N'Official fix', N'OF', CAST(0.870 AS Decimal(10, 3)), 8, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (26, N'2.0', N'Temporal Metric', N'Remediation Level', N'RL', N'Temporary fix', N'TF', CAST(0.900 AS Decimal(10, 3)), 8, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (27, N'2.0', N'Temporal Metric', N'Remediation Level', N'RL', N'Workaround', N'W', CAST(0.950 AS Decimal(10, 3)), 8, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (28, N'2.0', N'Temporal Metric', N'Remediation Level', N'RL', N'Unavailable', N'U', CAST(1.000 AS Decimal(10, 3)), 8, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (29, N'2.0', N'Temporal Metric', N'Report Confidence', N'RC', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 9, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (30, N'2.0', N'Temporal Metric', N'Report Confidence', N'RC', N'Unconfirmed', N'UC', CAST(0.900 AS Decimal(10, 3)), 9, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (31, N'2.0', N'Temporal Metric', N'Report Confidence', N'RC', N'Uncorroborated', N'UR', CAST(0.950 AS Decimal(10, 3)), 9, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (32, N'2.0', N'Temporal Metric', N'Report Confidence', N'RC', N'Confirmed', N'C', CAST(1.000 AS Decimal(10, 3)), 9, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (33, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'Not Defined', N'ND', CAST(0.000 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (34, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'None', N'N', CAST(0.000 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (35, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'Low (light loss)', N'L', CAST(0.100 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (36, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'Low-Medium', N'LM', CAST(0.300 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (37, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'Medium-High', N'MH', CAST(0.400 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (38, N'2.0', N'Environmental Metric', N'Collateral Damage Potential', N'CDP', N'High (catastrophic loss)', N'H', CAST(0.500 AS Decimal(10, 3)), 10, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (39, N'2.0', N'Environmental Metric', N'Target Distribution', N'TD', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 11, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (40, N'2.0', N'Environmental Metric', N'Target Distribution', N'TD', N'None 0%', N'N', CAST(0.000 AS Decimal(10, 3)), 11, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (41, N'2.0', N'Environmental Metric', N'Target Distribution', N'TD', N'Low 0-25%', N'L', CAST(0.250 AS Decimal(10, 3)), 11, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (42, N'2.0', N'Environmental Metric', N'Target Distribution', N'TD', N'Medium 26-75%', N'M', CAST(0.750 AS Decimal(10, 3)), 11, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (43, N'2.0', N'Environmental Metric', N'Target Distribution', N'TD', N'High 76-100%', N'H', CAST(1.000 AS Decimal(10, 3)), 11, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (44, N'2.0', N'Environmental Metric', N'Confidentiality Requirement', N'CR', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 12, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (45, N'2.0', N'Environmental Metric', N'Confidentiality Requirement', N'CR', N'Low', N'L', CAST(0.500 AS Decimal(10, 3)), 12, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (46, N'2.0', N'Environmental Metric', N'Confidentiality Requirement', N'CR', N'Medium', N'M', CAST(1.000 AS Decimal(10, 3)), 12, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (47, N'2.0', N'Environmental Metric', N'Confidentiality Requirement', N'CR', N'High', N'H', CAST(1.510 AS Decimal(10, 3)), 12, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (48, N'2.0', N'Environmental Metric', N'Integrity Requirement', N'IR', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 13, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (49, N'2.0', N'Environmental Metric', N'Integrity Requirement', N'IR', N'Low', N'L', CAST(0.500 AS Decimal(10, 3)), 13, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (50, N'2.0', N'Environmental Metric', N'Integrity Requirement', N'IR', N'Medium', N'M', CAST(1.000 AS Decimal(10, 3)), 13, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (51, N'2.0', N'Environmental Metric', N'Integrity Requirement', N'IR', N'High', N'H', CAST(1.510 AS Decimal(10, 3)), 13, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (52, N'2.0', N'Environmental Metric', N'Availability Requirement', N'AR', N'Not Defined', N'ND', CAST(1.000 AS Decimal(10, 3)), 14, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (53, N'2.0', N'Environmental Metric', N'Availability Requirement', N'AR', N'Low', N'L', CAST(0.500 AS Decimal(10, 3)), 14, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (54, N'2.0', N'Environmental Metric', N'Availability Requirement', N'AR', N'Medium', N'M', CAST(1.000 AS Decimal(10, 3)), 14, NULL, NULL, 2, GETDATE())
INSERT [dbo].[CVSS_SCOR_MTRC] ([CVSS_SCOR_MTRC_KEY], [CVSS_VER], [MTRC_GRP_NM], [MTRC_NM], [MTRC_VCTR_CD], [MTRC_VAL_TXT], [MTRC_VAL_VCTR_CD], [MTRC_VAL_SCOR], [VCTR_ORDR], [UPDT_USER_ID], [UPDT_DT], [CREAT_USER_ID], [CREAT_DT]) VALUES (55, N'2.0', N'Environmental Metric', N'Availability Requirement', N'AR', N'High', N'H', CAST(1.510 AS Decimal(10, 3)), 14, NULL, NULL, 2, GETDATE())
SET IDENTITY_INSERT [dbo].[CVSS_SCOR_MTRC] OFF



INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'A', N'AlmostCertain', 5, CAST(9.00 AS Decimal(10, 2)), CAST(10.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'I', N'Informational', 0, CAST(0.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'L', N'Likely', 4, CAST(7.00 AS Decimal(10, 2)), CAST(8.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'P', N'Possible', 3, CAST(4.00 AS Decimal(10, 2)), CAST(6.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'R', N'Rare', 1, CAST(0.10 AS Decimal(10, 2)), CAST(1.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RISK_PRBL] ([RISK_PRBL_CD], [RISK_PRBL_NM], [RISK_PRBL_ORDR_NBR], [CVSS_EXPLT_SCOR_MIN], [CVSS_EXPLT_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'U', N'Unlikely', 2, CAST(2.00 AS Decimal(10, 2)), CAST(3.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)



INSERT [dbo].[RMDTN_CST_EFFRT] ([RMDTN_CST_EFFRT_CD], [RMDTN_CST_EFFRT_NM], [RMDTN_CST_EFFRT_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'H', N'High', 3, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RMDTN_CST_EFFRT] ([RMDTN_CST_EFFRT_CD], [RMDTN_CST_EFFRT_NM], [RMDTN_CST_EFFRT_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'L', N'Low', 1, GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RMDTN_CST_EFFRT] ([RMDTN_CST_EFFRT_CD], [RMDTN_CST_EFFRT_NM], [RMDTN_CST_EFFRT_ORDR_NBR], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'M', N'Medium', 2, GETDATE(), 2, NULL, NULL)



INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC1', N'Account Management ', N'Account Termination, Disabling, Reviews', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC10', N'Denial-of-Service ', N'Hardware, software or personnel', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC11', N'Identity Access Management ', N'Identity Access Management', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC12', N'Incident Response ', N'Incident Handling process or Incident handling Training', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC13', N'Information Security Policy', N'Information Security Policy', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC14', N'Access Control ', N'Network, Application, Database, Mobile, Least Privilege, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC15', N'Logging / Monitoring ', N'Logging, monitoring, notification, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC16', N'Malicious Code Protection', N'Network, Application, Database, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC17', N'Network Architecture – segmentation ', N'Application, Network, Database, segregation, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC18', N'Network Ingress Filtering ', N'Rulesets/ACLs - firewalls, Web Application Filtering, Routers, Switches', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC19', N'Personnel Controls ', N'Pre-hire Screening, Termination Process, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC2', N'Application Partitioning ', N'Web services from Management functions', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC20', N'Physical Access Mechanisms ', N'Restriction to transmission media/lines, Physical Access Monitoring, Visitor control, environmental controls, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC21', N'Remote Access ', N'VPN, Multi-Factor Auth, NAT, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC22', N'Security Awareness Training', N'Security Awareness Training', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC23', N'Security Testing ', N'Operating System/Network, Application, Database, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC24', N'Separation of Duties ', N'Ops, Development, Privileged/Non-Privileged, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC25', N'System Access Enforcement ', N'Operating System/Network, Application, Database, Device-Device, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC26', N'System Security Plan ', N'User rules and responsibilities', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC27', N'System / Application Patching ', N'Periodicity, manual or automated, processes, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC28', N'Transmission Integrity ', N'Digital signatures, Crypto hashes, etc', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC29', N'Wireless Access ', N'Configuration, Encryption, Testing', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC3', N'Asset Inventory ', N'Software, hardware, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC4', N'Business Continuity / Disaster Recovery ', N'Backups, hardware, software, processes', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC5', N'Coding Practices ', N'Input validation, forced browsing, SQL injection, error handling, information leakage, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC6', N'Security Configuration Management', N'Hardware and/or software', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC7', N'Data / System Classification ', N'Data marking, classification, media sanitation, or systems', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC8', N'Data Encryption ', N'Stored or in-transit', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[ROOT_CAUS_ANLYS] ([ROOT_CAUS_ANLYS_CD], [ROOT_CAUS_ANLYS_NM], [ROOT_CAUS_ANLYS_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'RC9', N'Data Flow Enforcement ', N'Control of sensitive data between systems', GETDATE(), 2, NULL, NULL)




INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'C', N'Catastrophic', 5, CAST(9.00 AS Decimal(10, 2)), CAST(10.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'I', N'Insignificant', 1, CAST(0.10 AS Decimal(10, 2)), CAST(1.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'IF', N'Informational', 0, CAST(0.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MO', N'Moderate', 3, CAST(4.00 AS Decimal(10, 2)), CAST(6.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MI', N'Minor', 2, CAST(2.00 AS Decimal(10, 2)), CAST(3.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_IMP] ([VULN_IMP_CD], [VULN_IMP_NM], [VULN_IMP_ORDR_NBR], [CVSS_IMP_SCOR_MIN], [CVSS_IMP_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'MJ', N'Major', 4, CAST(7.00 AS Decimal(10, 2)), CAST(8.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)



INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'C', N'Closed', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'CE', N'Closed With Exception', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'D', N'Duplicate', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'FP', N'False Positive', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'O', N'Open', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_INSTC_STS] ([VULN_INSTC_STS_CD], [VULN_INSTC_STS_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'V', N'Validated', GETDATE(), 2, NULL, NULL)



INSERT [dbo].[VULN_SEV] ([VULN_SEV_CD], [VULN_SEV_NM], [VULN_SEV_ORDR_NBR], [OVALL_SCOR_MIN], [OVALL_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'C', N'Critical', 4, CAST(9.00 AS Decimal(10, 2)), CAST(10.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_SEV] ([VULN_SEV_CD], [VULN_SEV_NM], [VULN_SEV_ORDR_NBR], [OVALL_SCOR_MIN], [OVALL_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'H', N'High', 3, CAST(7.00 AS Decimal(10, 2)), CAST(8.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_SEV] ([VULN_SEV_CD], [VULN_SEV_NM], [VULN_SEV_ORDR_NBR], [OVALL_SCOR_MIN], [OVALL_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'I', N'Informational', 0, CAST(0.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_SEV] ([VULN_SEV_CD], [VULN_SEV_NM], [VULN_SEV_ORDR_NBR], [OVALL_SCOR_MIN], [OVALL_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'L', N'Low', 1, CAST(0.10 AS Decimal(10, 2)), CAST(3.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)
INSERT [dbo].[VULN_SEV] ([VULN_SEV_CD], [VULN_SEV_NM], [VULN_SEV_ORDR_NBR], [OVALL_SCOR_MIN], [OVALL_SCOR_MAX], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'M', N'Medium', 2, CAST(4.00 AS Decimal(10, 2)), CAST(6.90 AS Decimal(10, 2)), GETDATE(), 2, NULL, NULL)



--Reference table: Having dependency with dbo.MSTR_LKP table


INSERT INTO dbo.CD_XREF (SRC_KEY,REFERRENCE_TYP_NM,TGT_REF_CD,SRC_REF_CD,SRC_REF_NM, CREAT_DT,CREAT_USER_ID,UPDT_DT,UPDT_USER_ID) VALUES

((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'A','5','Critical', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'L','4','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'P','3','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'U','2','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'R','1','Very Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'I','0','Info', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'A', NULL,'Almost Certain', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'L', NULL,'Likely', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'P',NULL,'Possible', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'U',NULL,'Unlikely', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Risk Probability', 'R',NULL,'Rare', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'C','9','Critical', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MJ','8','Very High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MJ','7','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MO','5','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MI','3','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'I','1','Very Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'IF','0','Info', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'C',NULL,'Catastrophic', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MJ',NULL,'Major', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MO',NULL,'Moderate', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'MI',NULL,'Minor', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Impact', 'I',NULL,'Insignificant', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'C','5','Critical', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'H','4','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'M','3','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'L','2','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'L','1','Very Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'I','0','Informational', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'H',NULL,'High Risk', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'M',NULL,'Medium Risk', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Vulnerability Severity', 'L',NULL,'Low Risk', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Remediation Cost Effort', 'H','5','Very High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Remediation Cost Effort', 'H','4','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Remediation Cost Effort', 'M','3','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Remediation Cost Effort', 'L','2','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'CISCO'),'Remediation Cost Effort', 'L','1','Very Low', GETDATE(), 2, NULL, NULL ),



((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'A','A','AlmostCertain', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'I','I','Informational', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'L','L','Likely', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'P','P','Possible', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'R','R','Rare', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Risk Probability', 'U','U','Unlikely', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'C','C','Catastrophic', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'I','I','Insignificant', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'IF','IF','Informational', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'MI','MI','Minor', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'MJ','MJ','Major', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Impact', 'MO','MO','Moderate', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Severity', 'C','C','Critical', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Severity', 'H','H','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Severity', 'I','I','Informational', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Severity', 'L','L','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Severity', 'M','M','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Remediation Cost Effort', 'H','H','High', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Remediation Cost Effort', 'M','M','Medium', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Remediation Cost Effort', 'L','L','Low', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A1','OWASP-A1','Injection', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A10','OWASP-A10','Unvalidated Redirects and Forwards', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A2','OWASP-A2','Broken Authentication and Session Management', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A3','OWASP-A3','Cross-Site Scripting (XSS)', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A4','OWASP-A4','Insecure Direct Object References', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A5','OWASP-A5','Security Misconfiguration', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A6','OWASP-A6','Sensitive Data Exposure', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A7','OWASP-A7','Missing Function Level Access Control', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A8','OWASP-A8','Cross-Site Request Forgery (CSRF)', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'OWASP-A9','OWASP-A9','Using Components with Known Vulnerabilities', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-1','RCA-1','Insufficient Patch Management', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-2','RCA-2','Insufficient Threat Management', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-3','RCA-3','Lack of Security Baselines', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-4','RCA-4','Poor Integration of Security into the System Development Life Cycle', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-5','RCA-5','Security Architecture Weaknesses', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-6','RCA-6','Inadequate Incident Response Procedures', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-7','RCA-7','Inadequate Training', GETDATE(), 2, NULL, NULL ),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Root Cause Analysis', 'RCA-8','RCA-8','Lack of Security Policies or Policy Enforcement', GETDATE(), 2, NULL, NULL ),


((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','1','1','Inventory / Asset Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','10','10','Logging and Monitoring',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','11','11','Email and Browser Protections',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','12','12','Network Access Controls ',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','13','13','Perimeter Defenses',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','14','14','Identity Access Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','15','15','Data Loss Prevention',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','16','16','Data Encryption',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','17','17','Security Organization Maturity',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','18','18','Incident Response Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','19','19','Cyber Defense / APT  Exercises',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','2','2','Security vernance, Risk & Compliance',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','20','20','Security Awareness',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','21','21','Data Protection',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','22','22','Patch Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','23','23','Mobile and IoT Security',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','24','24','Physical/Environmental Security',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','3','3','Security Configuration Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','4','4','Threat and Vulnerability Management',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','5','5','Malware Defenses',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','6','6','Application Software Security',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','7','7','Wireless Access Controls',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','8','8','Business Continuity/Disaster Recovery ',GETDATE(),2, NULL, NULL),
((SELECT MSTR_LKP_KEY FROM dbo.MSTR_LKP WHERE LKP_ENTY_TYP_NM = 'Source' and LKP_ENTY_NM = 'Common Format v1.0'),'Vulnerability Category','9','9','Privileged User Management',GETDATE(),2, NULL, NULL);



SET IDENTITY_INSERT [dbo].[RPT_NM] ON 

INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, NULL, N'Executive Summary', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, N'AV', N'Application Vulnerability Assessment', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, N'AC', N'Architecture Compliance Assessment', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, N'RT', N'Limited Red Team Assessment', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, N'NV', N'Network Vulnerability Assessment', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, N'PT', N'Penetration Testing', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, N'SR', N'Security Risk Assessment', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, N'TH', N'Threat Hunting (Cyber Intelligence Services)', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[RPT_NM] ([RPT_NM_KEY], [SECUR_SRVC_CD], [RPT_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, N'WR', N'Wireless Risk Assessment', GETDATE(), 2, NULL, NULL)
SET IDENTITY_INSERT [dbo].[RPT_NM] OFF


SET IDENTITY_INSERT [dbo].[OS_CATGY] ON 

INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, N'Windows', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, N'Linux', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, N'Unix', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, N'Mac', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, N'VMWare', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, N'MainFrame', GETDATE(), 2, NULL, NULL)
INSERT [dbo].[OS_CATGY] ([OS_CATGY_KEY], [OS_CATGY_NM], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, N'Other', GETDATE(), 2, NULL, NULL)
SET IDENTITY_INSERT [dbo].[OS_CATGY] OFF


SET IDENTITY_INSERT [dbo].[OWASP_TOP_10] ON 


INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (1, N'A1', N'Injection', N'Injection flaws, such as SQL, OS, and LDAP injection occur when untrusted data is sent to an interpreter as part of a command or query. The attacker’s hostile data can trick the interpreter into executing unintended commands or accessing data without proper authorization.', N'Untrusted Data input allows malicious script execution in SQL, OS, or LDAP.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (2, N'A2', N'Broken Authentication and Session Management', N'Application functions related to authentication and session management are often not implemented correctly, allowing attackers to compromise passwords, keys, or session tokens, or to exploit other implementation flaws to assume other users’ identities.', N'Weak authentication allows compromise of the application and misuse of user identity.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (3, N'A3', N'Cross-Site Scripting (XSS)', N'XSS flaws occur whenever an application takes untrusted data and sends it to a web browser without proper validation or escaping. XSS allows attackers to execute scripts in the victim’s browser which can hijack user sessions, deface web sites, or redirect the user to malicious sites.', N'Unvalidated input data allows script execution in browser to hijack session or redirect to malicious sites.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (4, N'A4', N'Insecure Direct Object References', N'A direct object reference occurs when a developer exposes a reference to an internal implementation object, such as a file, directory, or database key. Without an access control check or other protection, attackers can manipulate these references to access unauthorized data.', N'Weak access controls allow attackers to manipulate internal objects.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (5, N'A5', N'Security Misconfiguration', N'od security requires having a secure configuration defined and deployed for the application, frameworks, application server, web server, database server, and platform. Secure settings should be defined, implemented, and maintained, as defaults are often insecure. Additionally, software should be kept up to date.', N'Insecure configuration of OS, App, or DB layers to include patching/default settings provide multiple-layered attack vectors.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (6, N'A6', N'Sensitive Data Exposure', N'Many web applications do not properly protect sensitive data, such as credit cards, tax IDs, and authentication credentials. Attackers may steal or modify such weakly protected data to conduct credit card fraud, identity theft, or other crimes. Sensitive data deserves extra protection such as encryption at rest or in transit, as well as special precautions when exchanged with the browser.', N'Sensitive data may be stolen while in-transit or at-rest due to lack of encryption, poor coding practices, or security misconfigurations.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (7, N'A7', N'Missing Function Level Access Control', N'Most web applications verify function level access rights before making that functionality visible in the UI. However, applications need to perform the same access control checks on the server when each function is accessed. If requests are not verified, attackers will be able to forge requests in order to access functionality without proper authorization.', N'Unverified access control to functions may result in forged requests without authorization.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (8, N'A8', N'Cross-Site Request Forgery (CSRF)', N'A CSRF attack forces a logged-on victim’s browser to send a forged HTTP request, including the victim’s session cookie and any other automatically included authentication information, to a vulnerable web application. This allows the attacker to force the victim’s browser to generate requests the vulnerable application thinks are legitimate requests from the victim.', N'This is a browser-based attack that uses the victim''s own authentication/cookies for attacks against the application.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (9, N'A9', N'Using Components with Known Vulnerabilities', N'Components, such as libraries, frameworks, and other software modules, almost always run with full privileges. If a vulnerable component is exploited, such an attack can facilitate serious data loss or server takeover. Applications using components with known vulnerabilities may undermine application defenses and enable a range of possible attacks and impacts.', N'Use of libraries or software modules with vulnerabilities result in attacks against the application,database, or OS due to escalated privileges of the component.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

INSERT [dbo].[OWASP_TOP_10] ([OWASP_TOP_10_KEY], [OWASP_CD], [OWASP_NM], [OWASP_DESC], [OWASP_SHRT_DESC], [PUBL_DT], [CURR_IND], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (10, N'A10', N'Unvalidated Redirects and Forwards', N'Web applications frequently redirect and forward users to other pages and websites, and use untrusted data to determine the destination pages. Without proper validation, attackers can redirect victims to phishing or malware sites, or use forwards to access unauthorized pages.', N'Use of untrusted data to control application redirects can send victims to phishing/malware sites while forwards can result in access to unauthorized pages.', CAST(N'2013-12-06' AS Date), 1, GETDATE(), 2, NULL, NULL)

SET IDENTITY_INSERT [dbo].[OWASP_TOP_10] OFF



INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'1', N'Inventory / Asset Management', N'Asset classification, lifecycle management for software, hardware, and other assets of the organization', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'10', N'Logging and Monitoring', N'Logging, monitoring, notification, SIEM, Event Correlation for application/database/server/network; Honey Pot, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'11', N'Email and Browser Protections', N'Spam and Phishing detection controls, Prevent targeted attacks, Url  caterization', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'12', N'Network Access Controls ', N'Disable unnecessary ports and services. Network Segmentation, Content filtering, DPI etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'13', N'Perimeter Defenses', N'Firewall, Web Application FWs, IDS/IPS for Network, Server, Application, Database, Routers, Switches etc.; Transmission Integrity , Web Proxy (Egress Filtering), ', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'14', N'Identity Access Management', N'Entitlement review, Role Management, Least Privilege, Policy Enforcement etc.
Single Sign On, Smart Card, Biometrics, VPN, Multi-Factor Auth, Password Management, SAML Token, NAT, etc. ', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'15', N'Data Loss Prevention', N'Control of sensitive and confidential data between systems. Include content checking of emails', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'16', N'Data Encryption', N'Encryption for data in transit and at rest.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'17', N'Security Organization Maturity', N'Organization created and maintains efficient processes that help their business maintain security, ensure compliance, and sustain maturity of security controls. .', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'18', N'Incident Response Management', N'Defined process for the Handling of security Incidents; defined incident process; Incident handling Training.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'19', N'Cyber Defense / APT  Exercises', N'Red Team exercise and Advance Persistent Threat Simulations, Threat Hunting, Advanced Field Analytics', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'2', N'Security vernance, Risk & Compliance', N'Policy Management (exceptions & self assessment), Audit and Security assessment Management, Compliance Management, IT Risk Management etc. 
Data Classification, Data Ownership / Stewardship', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'20', N'Security Awareness', N'Security Awareness Training, Certification and Compliance', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'21', N'Data Protection', N'Database Activity Monitoring. De-Identification, Data Masking, Tagging, Meta Data, Data Loss Prevention, Cryptographic Services, Intellectual Property Protection, Secure Disposal of Data etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'22', N'Patch Management', N'Periodicity, manual or automated Patching processes for all Infrastructure components (Server, database, application, router etc.)', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'23', N'Mobile and IoT Security', N'Mobile devices, BYOD, Medical devices, Internet of things. MDM capabilities.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'24', N'Physical/Environmental Security', N'Physical Security of assets, Power, Temperature, & Humidity environmental controls', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'3', N'Security Configuration Management', N'Hardware and/or software security baseline, compliance standards and base built', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'4', N'Threat and Vulnerability Management', N'Pen test, Vulnerability test, Compliance test, Baseline test of Operating System/Network, Application, Database, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'5', N'Malware Defenses', N'Endpoint  and Network; Anti-Virus, Anti-Malware filtering, Application Whitelisting', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'6', N'Application Software Security', N'Enforcement of secure coning practice. Security Code reviews, Input validation, forced browsing, SQL injection, error handling, information leakage, etc.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'7', N'Wireless Access Controls', N'Authorized wireless access points. Segregation between Internal network and guest networks. Wireless IDS/IPS.  Roque access points', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'8', N'Business Continuity/Disaster Recovery ', N'Backups, hardware, software, business continuity processes, disaster recovery.', GETDATE(), 2, NULL, NULL)

INSERT [dbo].[VULN_CATGY] ([VULN_CATGY_CD], [VULN_CATGY_NM], [VULN_CATGY_DESC], [CREAT_DT], [CREAT_USER_ID], [UPDT_DT], [UPDT_USER_ID]) VALUES (N'9', N'Privileged User Management', N'Separation of duties between Administrative accounts and users with elevated permissions from Ops, Development, Non-Privileged users.  Password Vaulting, ', GETDATE(), 2, NULL, NULL)


GO
