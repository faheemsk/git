﻿CREATE TABLE [dbo].[CNTRY_CD] (
    [CNTRY_CD]      VARCHAR (10)  NOT NULL,
    [CNTRY_NM]      VARCHAR (150) NOT NULL,
    [CREAT_DT]      DATETIME      NOT NULL,
    [CREAT_USER_ID] INT           NOT NULL,
    [UPDT_DT]       DATETIME      NULL,
    [UPDT_USER_ID]  INT           NULL,
    CONSTRAINT [PK_CNTRY_CD] PRIMARY KEY CLUSTERED ([CNTRY_CD] ASC)
);