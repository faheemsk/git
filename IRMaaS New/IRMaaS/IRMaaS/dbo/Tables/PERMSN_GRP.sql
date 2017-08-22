﻿CREATE TABLE [dbo].[PERMSN_GRP] (
    [PERMSN_GRP_KEY]  INT            IDENTITY (1, 1) NOT NULL,
    [ROW_STS_KEY]     INT            NOT NULL,
    [PERMSN_GRP_NM]   VARCHAR (100)  NOT NULL,
    [PERMSN_GRP_DESC] VARCHAR (1000) NULL,
    [STS_COMMT_TXT]   TEXT           NULL,
    [CREAT_DT]        DATETIME       NOT NULL,
    [CREAT_USER_ID]   INT            NOT NULL,
    [UPDT_DT]         DATETIME       NULL,
    [UPDT_USER_ID]    INT            NULL,
    CONSTRAINT [PK_PERMSN_GRP] PRIMARY KEY CLUSTERED ([PERMSN_GRP_KEY] ASC),
    CONSTRAINT [FK_MSTR_LKP_70] FOREIGN KEY ([ROW_STS_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])
);