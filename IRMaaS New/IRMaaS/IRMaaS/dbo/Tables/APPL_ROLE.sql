﻿CREATE TABLE [dbo].[APPL_ROLE] (
    [APPL_ROLE_KEY]  INT            IDENTITY (1, 1) NOT NULL,
    [ROW_STS_KEY]    INT            NOT NULL,
    [APPL_ROLE_NM]   VARCHAR (100)  NOT NULL,
    [APPL_ROLE_DESC] VARCHAR (1000) NULL,
    [STS_COMMT_TXT]  TEXT           NULL,
    [USER_TYP_KEY]   INT            NOT NULL,
    [CREAT_DT]       DATETIME       NOT NULL,
    [CREAT_USER_ID]  INT            NOT NULL,
    [UPDT_DT]        DATETIME       NULL,
    [UPDT_USER_ID]   INT            NULL,
    CONSTRAINT [PK_APPL_ROLE] PRIMARY KEY CLUSTERED ([APPL_ROLE_KEY] ASC),
    CONSTRAINT [FK_MSTR_LKP_93] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY]),
	CONSTRAINT [FK_MSTR_LKP_114] FOREIGN KEY ([USER_TYP_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])
);