﻿CREATE TABLE [stg].[REG_CMPLN_STG] (
    [REG_CMPLN_CD]      VARCHAR (20)   NOT NULL,
    [REG_CMPLN_VER]     VARCHAR (20)   NOT NULL,
    [REG_CMPLN_NM]      VARCHAR (200)  NULL,
    [REG_CMPLN_DESC]    VARCHAR (2000) NULL,
    [REG_CMPLN_PUBL_DT] DATE           NULL,
    [CREAT_DT]          DATETIME       NOT NULL,
    [CREAT_USER_ID]     INT            NOT NULL,
    CONSTRAINT [PK_REG_CMPLN_STG] PRIMARY KEY CLUSTERED ([REG_CMPLN_CD] ASC, [REG_CMPLN_VER] ASC)
);