﻿CREATE TABLE [dbo].[USER_EVNT_LOG] (
    [USER_EVNT_LOG_KEY]  INT            IDENTITY (1, 1) NOT NULL,
    [USER_ID]            INT            NOT NULL,
    [ACT_NM]             VARCHAR (100)  NULL,
    [MDUL_NM]            VARCHAR (100)  NULL,
    [EVNT_DT]            DATETIME       NULL,
    [EVNT_DESC]          VARCHAR (1000) NULL,
    [CLNT_ENGMT_CD]      VARCHAR (30)   NULL,
    [CLNT_SRVC_ENGMT_CD] VARCHAR (150)  NULL,
    [IPADR]              VARCHAR (20)   NULL,
    [SFTW_INFO_TXT]      VARCHAR (5000) NULL,
    [FILL_TXT]           TEXT           NULL,
    CONSTRAINT [PK_USER_EVNT_LOG] PRIMARY KEY CLUSTERED ([USER_EVNT_LOG_KEY] ASC),
    CONSTRAINT [FK_USER_PRFL_38] FOREIGN KEY ([USER_ID]) REFERENCES [dbo].[USER_PRFL] ([USER_ID])
);