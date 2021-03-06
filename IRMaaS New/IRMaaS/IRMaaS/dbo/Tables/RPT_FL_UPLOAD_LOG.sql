﻿CREATE TABLE [dbo].[RPT_FL_UPLOAD_LOG] (
    [RPT_FL_UPLOAD_LOG_KEY] INT           IDENTITY (1, 1) NOT NULL,
    [ROW_STS_KEY]           INT           NOT NULL,
    [ORG_KEY]               INT           NOT NULL,
    [CLNT_ENGMT_CD]         VARCHAR (30)  NOT NULL,
    [RPT_NM_KEY]            INT           NOT NULL,
    [RPT_STS_KEY]           INT           NOT NULL,
    [FL_NM]                 VARCHAR (150) NOT NULL,
    [FL_FLDR_PTH]           VARCHAR (500) NOT NULL,
    [FL_SZ]                 VARCHAR (100) NULL,
    [RPT_PUBL_DT]           DATETIME      NULL,
    [CREAT_USER_ID]         INT           NOT NULL,
    [CREAT_DT]              DATETIME      NOT NULL,
    [UPDT_USER_ID]          INT           NULL,
    [UPDT_DT]               DATETIME      NULL,
    CONSTRAINT [PK_RPT_FL_UPLOAD_LOG] PRIMARY KEY CLUSTERED ([RPT_FL_UPLOAD_LOG_KEY] ASC),
    CONSTRAINT [FK_CLNT_ENGMT_13] FOREIGN KEY ([CLNT_ENGMT_CD]) REFERENCES [dbo].[CLNT_ENGMT] ([CLNT_ENGMT_CD]),
    CONSTRAINT [FK_MSTR_LKP_20] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_MSTR_LKP_22] FOREIGN KEY ([RPT_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_ORG_21] FOREIGN KEY ([ORG_KEY]) REFERENCES [dbo].[ORG] ([ORG_KEY]),
    CONSTRAINT [FK_RPT_NM_14] FOREIGN KEY ([RPT_NM_KEY]) REFERENCES [dbo].[RPT_NM] ([RPT_NM_KEY])
);