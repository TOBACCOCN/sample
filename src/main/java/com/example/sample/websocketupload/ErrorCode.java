package com.example.sample.websocketupload;

/**
 * Created by janson on 2018/5/10.
 */

class ErrorCode {

    static final int Code_OK = 200;
    static final int Code_SERVER_RESULT_START_TO_RECEIVE = 500;

    static final int Code_NullObj = -1002;
    static final int Code_NullParams = -1005;
    static final int Code_PoorNetwork = -1007;


    static final int Code_FileNoExist = -1067;
    static final int Code_FileIsDirs = -1068;
    static final int Code_OtherException = -1069;

    static final int Code_Md5Error = -1070;
    static final int Code_JSONException = -1072;
    static final int Code_SERVER_RESULT_MD5_FAILED = -1073;

}
