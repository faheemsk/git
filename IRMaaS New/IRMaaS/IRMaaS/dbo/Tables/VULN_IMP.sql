﻿CREATE TABLE [dbo].[VULN_IMP] (
    [VULN_IMP_CD]       VARCHAR (3)     NOT NULL,
    [VULN_IMP_NM]       VARCHAR (150)   NOT NULL,
    [VULN_IMP_ORDR_NBR] INT             NULL,
    [CVSS_IMP_SCOR_MIN] DECIMAL (10, 2) NULL,
    [CVSS_IMP_SCOR_MAX] DECIMAL (10, 2) NULL,
    [CREAT_DT]          DATETIME        NOT NULL,
    [CREAT_USER_ID]     INT             NOT NULL,
    [UPDT_DT]           DATETIME        NULL,
    [UPDT_USER_ID]      INT             NULL,
    CONSTRAINT [PK_VULN_IMP] PRIMARY KEY CLUSTERED ([VULN_IMP_CD] ASC)
);