CREATE TABLE [dbo].[CVSS_SCOR_MTRC] (
    [CVSS_SCOR_MTRC_KEY] INT             IDENTITY (1, 1) NOT NULL,
    [CVSS_VER]           VARCHAR (20)    NULL,
    [MTRC_GRP_NM]        VARCHAR (100)   NULL,
    [MTRC_NM]            VARCHAR (100)   NULL,
    [MTRC_VCTR_CD]       VARCHAR (3)     NOT NULL,
    [MTRC_VAL_TXT]       VARCHAR (100)   NULL,
    [MTRC_VAL_VCTR_CD]   VARCHAR (3)     NOT NULL,
    [MTRC_VAL_SCOR]      DECIMAL (10, 3) NOT NULL,
    [VCTR_ORDR]          INT             NULL,
    [UPDT_USER_ID]       INT             NULL,
    [UPDT_DT]            DATETIME        NULL,
    [CREAT_USER_ID]      INT             NOT NULL,
    [CREAT_DT]           DATETIME        NOT NULL,
    CONSTRAINT [PK_CVSS_SCOR_MTRC] PRIMARY KEY CLUSTERED ([CVSS_SCOR_MTRC_KEY] ASC)
);