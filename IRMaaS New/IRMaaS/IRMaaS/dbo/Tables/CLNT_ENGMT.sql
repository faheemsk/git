﻿CREATE TABLE [dbo].[CLNT_ENGMT] (
    [CLNT_ENGMT_CD]    VARCHAR (30)   NOT NULL,
    [ROW_STS_KEY]      INT            NOT NULL,
    [CLNT_ORG_KEY]     INT            NOT NULL,
    [SECUR_PKG_CD]     VARCHAR (10)   NOT NULL,
    [ENGMT_STS_KEY]    INT            NOT NULL,
    [CLNT_ENGMT_NM]    VARCHAR (150)  NULL,
    [AGR_DT]           DATETIME       NOT NULL,
    [ENGMT_STRT_DT]    DATETIME       NOT NULL,
    [ENGMT_EST_END_DT] DATETIME       NULL,
    [CLNT_ENGMT_DESC]  VARCHAR (1000) NULL,
    [ENGMT_COMMT]      TEXT           NULL,
    [CLNT_PUBL_DT]     DATETIME       NULL,
    [CREAT_DT]         DATETIME       NOT NULL,
    [CREAT_USER_ID]    INT            NOT NULL,
    [UPDT_DT]          DATETIME       NULL,
    [UPDT_USER_ID]     INT            NULL,
    CONSTRAINT [PK_CLNT_ENGMT] PRIMARY KEY CLUSTERED ([CLNT_ENGMT_CD] ASC),
    CONSTRAINT [FK_MSTR_LKP_1] FOREIGN KEY ([ENGMT_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_MSTR_LKP_2] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_ORG_44] FOREIGN KEY ([CLNT_ORG_KEY]) REFERENCES [dbo].[ORG] ([ORG_KEY]),
    CONSTRAINT [FK_SECUR_PKG_4] FOREIGN KEY ([SECUR_PKG_CD]) REFERENCES [dbo].[SECUR_PKG] ([SECUR_PKG_CD]),
    CONSTRAINT [AK1_CLNT_ENGMT] UNIQUE NONCLUSTERED ([CLNT_ORG_KEY] ASC, [SECUR_PKG_CD] ASC, [AGR_DT] ASC)
);