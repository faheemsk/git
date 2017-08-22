﻿CREATE TABLE [dbo].[OS] (
    [OS_KEY]        INT           IDENTITY (1, 1) NOT NULL,
    [OS_NM]         VARCHAR (150) NULL,
    [OS_CATGY_KEY]  INT           NOT NULL,
    [CREAT_DT]      DATETIME      NOT NULL,
    [CREAT_USER_ID] INT           NOT NULL,
    [UPDT_DT]       DATETIME      NULL,
    [UPDT_USER_ID]  INT           NULL,
    CONSTRAINT [PK_OS] PRIMARY KEY CLUSTERED ([OS_KEY] ASC),
    CONSTRAINT [FK_OS_CATGY_82] FOREIGN KEY ([OS_CATGY_KEY]) REFERENCES [dbo].[OS_CATGY] ([OS_CATGY_KEY])
);