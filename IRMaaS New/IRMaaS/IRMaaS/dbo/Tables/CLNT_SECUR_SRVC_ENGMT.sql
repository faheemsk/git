﻿CREATE TABLE [dbo].[CLNT_SECUR_SRVC_ENGMT] (
    [CLNT_ENGMT_CD]      VARCHAR (30) NOT NULL,
    [SECUR_SRVC_CD]      VARCHAR (10) NOT NULL,
    [ROW_STS_KEY]        INT          NOT NULL,
    [SRVC_ENGMT_STS_KEY] INT          NOT NULL,
    [SRVC_EST_STRT_DT]   DATETIME     NOT NULL,
    [SRVC_EST_END_DT]    DATETIME     NULL,
    [FL_LCK_IND]         INT          NOT NULL,
    [CREAT_DT]           DATETIME     NOT NULL,
    [CREAT_USER_ID]      INT          NOT NULL,
    [UPDT_DT]            DATETIME     NULL,
    [UPDT_USER_ID]       INT          NULL,
    CONSTRAINT [PK_CLNT_SECUR_SRVC_ENGMT] PRIMARY KEY CLUSTERED ([CLNT_ENGMT_CD] ASC, [SECUR_SRVC_CD] ASC),
    CONSTRAINT [FK_CLNT_ENGMT_61] FOREIGN KEY ([CLNT_ENGMT_CD]) REFERENCES [dbo].[CLNT_ENGMT] ([CLNT_ENGMT_CD]),
    CONSTRAINT [FK_MSTR_LKP_47] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_MSTR_LKP_48] FOREIGN KEY ([SRVC_ENGMT_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_SECUR_SRVC_43] FOREIGN KEY ([SECUR_SRVC_CD]) REFERENCES [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD])
);