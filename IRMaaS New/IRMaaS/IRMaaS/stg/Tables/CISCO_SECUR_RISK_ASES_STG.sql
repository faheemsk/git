CREATE TABLE [stg].[CISCO_SECUR_RISK_ASES_STG] (
    [RISK_NBR]               INT            NULL,
    [RISK_NM]                VARCHAR (255)  NULL,
    [CTL_EFF]                VARCHAR (150)  NULL,
    [RISK_LVL_IMP]           VARCHAR (150)  NULL,
    [RISK_LVL_PRBL]          VARCHAR (150)  NULL,
    [RISK_LVL_OVALL]         VARCHAR (150)  NULL,
    [RISK_DESC]              TEXT           NULL,
    [IMP_COMMT]              TEXT           NULL,
    [CTL_DEFICIENCIES]       TEXT           NULL,
    [RMDTN_RECOM]            TEXT           NULL,
    [MITG_CTL_AREA]          VARCHAR (2000) NULL,
    [RLVN_STRG_TRND]         VARCHAR (2000) NULL,
    [APPL_FL_UPLOAD_LOG_KEY] INT            NOT NULL,
    [CREAT_DT]               DATETIME       NOT NULL,
    [CREAT_USER_ID]          INT            NOT NULL
);