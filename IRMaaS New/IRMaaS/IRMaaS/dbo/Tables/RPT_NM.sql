﻿CREATE TABLE [dbo].[RPT_NM] (
    [RPT_NM_KEY]    INT           IDENTITY (1, 1) NOT NULL,
    [SECUR_SRVC_CD] VARCHAR (10)  NULL,
    [RPT_NM]        VARCHAR (150) NOT NULL,
    [CREAT_DT]      DATETIME      NOT NULL,
    [CREAT_USER_ID] INT           NOT NULL,
    [UPDT_DT]       DATETIME      NULL,
    [UPDT_USER_ID]  INT           NULL,
    CONSTRAINT [PK_RPT_NM] PRIMARY KEY CLUSTERED ([RPT_NM_KEY] ASC),
    CONSTRAINT [FK_SECUR_SRVC_24] FOREIGN KEY ([SECUR_SRVC_CD]) REFERENCES [dbo].[SECUR_SRVC] ([SECUR_SRVC_CD])
);