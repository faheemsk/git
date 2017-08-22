CREATE TABLE [dbo].[OS_CATGY_MAP] (
    [OS_CATGY_KEY]  INT           NULL,
    [OS_CATGY_NM]   VARCHAR (150) NULL,
    [OS_NM_QUAL]    VARCHAR (150) NULL,
    [CREAT_DT]      DATETIME      NOT NULL,
    [CREAT_USER_ID] INT           NOT NULL,
    [UPDT_DT]       DATETIME      NULL,
    [UPDT_USER_ID]  INT           NULL
);