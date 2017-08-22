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
:r .\PostScripts\dbo.MSTR_LKP.Table.sql

:r .\PostScripts\dbo.ORG.Table.sql
:r .\PostScripts\dbo.CNTRY_CD.Table.sql
:r .\PostScripts\dbo.ST_CD.Table.sql

:r .\PostScripts\dbo.PERMSN.Table.sql
:r .\PostScripts\dbo.PERMSN_GRP.Table.sql
:r .\PostScripts\dbo.PERMSN_GRP_ASSOC.Table.sql
:r .\PostScripts\dbo.APPL_ROLE.Table.sql
:r .\PostScripts\dbo.APPL_ROLE_PERMSN_GRP.Table.sql

:r .\PostScripts\dbo.USER_PRFL.Table.sql
:r .\PostScripts\dbo.USER_APPL_ROLE.Table.sql
:r .\PostScripts\dbo.USER_SECUR_DTL.Table.sql

:r .\PostScripts\dbo.NTF_MSG.Table.sql

:r .\PostScripts\dbo.SECUR_PKG.Table.sql
:r .\PostScripts\dbo.SECUR_SRVC.Table.sql
:r .\PostScripts\dbo.SECUR_PKG_OFR.Table.sql

:r .\PostScripts\dbo.CVSS_SCOR_MTRC.Table.sql
:r .\PostScripts\dbo.RISK_PRBL.Table.sql
:r .\PostScripts\dbo.RMDTN_CST_EFFRT.Table.sql
:r .\PostScripts\dbo.ROOT_CAUS_ANLYS.Table.sql

:r .\PostScripts\dbo.VULN_IMP.Table.sql
:r .\PostScripts\dbo.VULN_INSTC_STS.Table.sql
:r .\PostScripts\dbo.VULN_SEV.Table.sql


--Reference table: Having dependency with dbo.MSTR_LKP table
:r .\PostScripts\dbo.CD_XREF.Table.sql

:r .\PostScripts\dbo.RPT_NM.Table.sql
:r .\PostScripts\dbo.OS_CATGY.Table.sql
:r .\PostScripts\dbo.OWASP_TOP_10.Table.sql
:r .\PostScripts\dbo.VULN_CATGY.Table.sql