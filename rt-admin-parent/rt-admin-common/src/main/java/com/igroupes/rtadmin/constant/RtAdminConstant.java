package com.igroupes.rtadmin.constant;

public interface RtAdminConstant {
     String VERIFY_CODE_COOKIE_KEY = "vc";
     String USER_INFO_REQUEST_ATTRIBUTE_KEY = "request_user_info";
     String SYSTEM_LOG_PATH_HEADER_KEY = "X-ACTION-ADDR";
     // 通用的操作成功与失败编码
     int RESULT_SUCCESS = 1;
     int RESULT_FAIL = 2;

     Long ADMIN_SUPER_ID = 0L;

     String URL_DECODE_CHARSET = "UTC-8";
}
