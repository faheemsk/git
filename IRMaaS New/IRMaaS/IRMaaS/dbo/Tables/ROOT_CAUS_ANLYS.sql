﻿CREATE TABLE [dbo].[ROOT_CAUS_ANLYS] (
    [ROOT_CAUS_ANLYS_CD]   VARCHAR (10)   NOT NULL,
    [CREAT_DT]             DATETIME       NOT NULL,
    [CREAT_USER_ID]        INT            NOT NULL,
    [UPDT_DT]              DATETIME       NULL,
    [UPDT_USER_ID]         INT            NULL,
    [ROOT_CAUS_ANLYS_NM]   VARCHAR (150)  NOT NULL,
    [ROOT_CAUS_ANLYS_DESC] VARCHAR (1000) NULL,
    CONSTRAINT [PK_ROOT_CAUS_ANLYS] PRIMARY KEY CLUSTERED ([ROOT_CAUS_ANLYS_CD] ASC)
);