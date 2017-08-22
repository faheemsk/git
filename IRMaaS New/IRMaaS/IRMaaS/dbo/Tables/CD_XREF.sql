﻿CREATE TABLE [dbo].[CD_XREF] (
    [CD_XREF_KEY]       INT           IDENTITY (1, 1) NOT NULL,
    [SRC_KEY]           INT           NOT NULL,
    [REFERRENCE_TYP_NM] VARCHAR (100) NOT NULL,
    [TGT_REF_CD]        VARCHAR (10)  NOT NULL,
    [SRC_REF_CD]        VARCHAR (10)  NULL,
    [SRC_REF_NM]        VARCHAR (150) NULL,
    [CREAT_DT]          DATETIME      NOT NULL,
    [CREAT_USER_ID]     INT           NOT NULL,
    [UPDT_DT]           DATETIME      NULL,
    [UPDT_USER_ID]      INT           NULL,
    CONSTRAINT [PK_CD_XREF] PRIMARY KEY CLUSTERED ([CD_XREF_KEY] ASC),
    CONSTRAINT [FK_MSTR_LKP_30] FOREIGN KEY ([SRC_KEY]) REFERENCES [dbo].[MSTR_LKP] ([MSTR_LKP_KEY])
);