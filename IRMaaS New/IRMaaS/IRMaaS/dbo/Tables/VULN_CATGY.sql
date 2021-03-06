﻿CREATE TABLE [dbo].[VULN_CATGY](
	[VULN_CATGY_CD] [varchar](10) NOT NULL,
	[VULN_CATGY_NM] [varchar](150) NOT NULL,
	[VULN_CATGY_DESC] [varchar](1000) NULL,
	[CREAT_DT] [datetime] NOT NULL,
	[CREAT_USER_ID] [int] NOT NULL,
	[UPDT_DT] [datetime] NULL,
	[UPDT_USER_ID] [int] NULL,
 CONSTRAINT [PK_VULN_CATGY] PRIMARY KEY CLUSTERED 
(
	[VULN_CATGY_CD] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
