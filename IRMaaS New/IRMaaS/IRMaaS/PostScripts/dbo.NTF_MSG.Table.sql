
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
