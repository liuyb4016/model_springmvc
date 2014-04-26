package cn.liuyb.app.sync.json;

public interface ReturnCode {
    static final int SUCCESS = 0;
    static final int BUSINESS_ERROR = 2;
    static final int CONSTRAINT_VIOLATION_ERROR = 3;
    static final int INVALID_CMD_DATA = 4;
    static final int NOT_TELECOM_CODE = 5;
    static final int ERROR_IMSI_CODE = 6;
    static final int UDB_TOKEN_INVALID = 7;
    static final int EXCEPTION = 11;
    static final int NO_EXIST_BACKUP = 71;
    static final int INVALID_SERVER_CMD = 97;
    static final int INVALID_LOGIN_INFO = 98;// 用户名或密码错误
    static final int INVALID_TOKEN = 99; // token 无效
    static final int NEVER_USED_CODE = -999999;// -999999：假定为永远也不会出现的returnCode
    static final int CLOUD_FOLDER_EXIST = 100000; // 文件已存在
}
