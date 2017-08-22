CREATE TABLE [dbo].[OWASP_TOP_10] (
    [OWASP_TOP_10_KEY] INT            IDENTITY (1, 1) NOT NULL,
    [OWASP_CD]         VARCHAR (10)   NOT NULL,
    [OWASP_NM]         VARCHAR (150)  NOT NULL,
    [OWASP_DESC]       VARCHAR (1000) NULL,
    [OWASP_SHRT_DESC]  VARCHAR (255)  NULL,
    [PUBL_DT]          DATE           NULL,
    [CURR_IND]         INT            NULL,
    [CREAT_DT]         DATETIME       NOT NULL,
    [CREAT_USER_ID]    INT            NOT NULL,
    [UPDT_DT]          DATETIME       NULL,
    [UPDT_USER_ID]     INT            NULL,
    CONSTRAINT [PK_OWASP_TOP_10] PRIMARY KEY CLUSTERED ([OWASP_TOP_10_KEY] ASC)
);