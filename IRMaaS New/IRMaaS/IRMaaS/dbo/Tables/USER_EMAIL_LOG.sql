﻿CREATE TABLE [dbo].[USER_EMAIL_LOG] (
    [USER_EMAIL_LOG]     INT            IDENTITY (1, 1) NOT NULL,
    [NTF_MSG_KEY]        INT            NULL,
    [USER_ID]            INT            NOT NULL,
    [FROM_EMAIL_ID]      VARCHAR (150)  NULL,
    [TO_EMAIL_ID]        TEXT           NULL,
    [CC_EMAIL_ID]        TEXT           NULL,
    [BCC_EMAIL_ID]       TEXT           NULL,
    [EMAIL_SND_DT]       DATETIME       NULL,
    [EMAIL_MSG_SBJ_TXT]  VARCHAR (255)  NULL,
    [EMAIL_MSG_CNTN_TXT] TEXT           NULL,
    [SND_SUC_IND]        INT            NULL,
    [RESND_CNT]          INT            NULL,
    [ERR_DESC]           VARCHAR (5000) NULL,
    CONSTRAINT [PK_USER_EMAIL_LOG] PRIMARY KEY CLUSTERED ([USER_EMAIL_LOG] ASC),
    CONSTRAINT [FK_NTF_MSG_39] FOREIGN KEY ([NTF_MSG_KEY]) REFERENCES [dbo].[NTF_MSG] ([NTF_MSG_KEY]),
    CONSTRAINT [FK_USER_PRFL_41] FOREIGN KEY ([USER_ID]) REFERENCES [dbo].[USER_PRFL] ([USER_ID])
);