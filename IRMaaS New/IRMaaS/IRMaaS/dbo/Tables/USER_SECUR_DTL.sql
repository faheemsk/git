﻿CREATE TABLE [dbo].[USER_SECUR_DTL] (
    [USER_SECUR_DTL_KEY] INT            IDENTITY (1, 1) NOT NULL,
    [ROW_STS_KEY]        INT            NOT NULL,
    [USER_ID]            INT            NOT NULL,
    [SECUR_QUES_KEY]     INT            NOT NULL,
    [ANS_TXT]            VARCHAR (1000) NULL,
    [SEQ_ORDR_NBR]       INT            DEFAULT ((0)) NOT NULL,
    [CREAT_DT]           DATETIME       NOT NULL,
    [CREAT_USER_ID]      INT            NOT NULL,
    [UPDT_DT]            DATETIME       NULL,
    [UPDT_USER_ID]       INT            NULL,
    CONSTRAINT [PK_USER_SECUR_DTL] PRIMARY KEY CLUSTERED ([USER_SECUR_DTL_KEY] ASC),
    CONSTRAINT [FK_MSTR_LKP_74] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_MSTR_LKP_81] FOREIGN KEY ([SECUR_QUES_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
    CONSTRAINT [FK_USER_PRFL_88] FOREIGN KEY ([USER_ID]) REFERENCES [dbo].[USER_PRFL] ([USER_ID])
);