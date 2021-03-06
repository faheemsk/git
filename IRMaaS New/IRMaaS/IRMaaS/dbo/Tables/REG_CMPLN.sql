﻿CREATE TABLE [dbo].[REG_CMPLN] (
    [REG_CMPLN_CD]      VARCHAR (20)   NOT NULL,
    [REG_CMPLN_VER]     VARCHAR (20)   NOT NULL,
    [REG_CMPLN_NM]      VARCHAR (200)  NULL,
    [REG_CMPLN_DESC]    VARCHAR (2000) NULL,
    [REG_CMPLN_PUBL_DT] DATE           NULL,
    [CREAT_DT]          DATETIME       NOT NULL,
    [CREAT_USER_ID]     INT            NOT NULL,
    [UPDT_DT]           DATETIME       NULL,
    [UPDT_USER_ID]      INT            NULL,
    CONSTRAINT [PK_REG_CMPLN] PRIMARY KEY CLUSTERED ([REG_CMPLN_CD] ASC, [REG_CMPLN_VER] ASC)
);