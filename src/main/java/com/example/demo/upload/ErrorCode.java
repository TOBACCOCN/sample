package com.example.demo.upload;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by janson on 2018/5/10.
 */

public class ErrorCode {
    public static final int Code_OK = 200;
    public static final int Code_OKOCR = 201;
    public static final int Code_OKTransTTS = 202;
    public static final int Code_OKOnlySpe = 203;
    public static final int Code_SERVER_RESULT_START_TO_RECEIVE = 500;
    public static final int Code_LoginSuccess = 501;

    public static final int Code_Unknown = -1000;
    public static final int Code_NullObj = -1002;
    public static final int Code_NullUuid = -1003;
    public static final int Code_NoSuportRecogType = -1004;
    public static final int Code_NullParams = -1005;
    public static final int Code_OwesFees = -1006;
    public static final int Code_PoorNetwork = -1007;
    public static final int Code_EngineIniting = -1008;
    public static final int Code_SrcLanguageNull = -1009;
    public static final int Code_DestLanguageNull = -1010;
    public static final int Code_Translating = -1011;
    public static final int Code_TTSPlaying = -1012;
    public static final int Code_RecognizeLanguageNoSupport = -1013;
    public static final int Code_TranslateSrcLanguageNoSupport = -1014;
    public static final int Code_TranslateDestLanguageNoSupport = -1015;
    public static final int Code_TTSLanguageNoSupport = -1016;
    public static final int Code_RecognizeExceptionInWaiting = -1017;
    public static final int Code_RecognizeConnectFailed = -1018;
    public static final int Code_RecognizeConnectTimeOut = -1019;
    public static final int Code_RecognizeFailed = -1020;
    public static final int Code_RecognizeGetResultTimeOut = -1021;
    public static final int Code_RecognizeConnectExceptionFailed = -1022;

    public static final int Code_TranslateConnectExceptionFailed = -1023;
    public static final int Code_TranslateGetTokenFailed = -1024;
    public static final int Code_TranslateFailed = -1025;
    public static final int Code_EngineInitFailed= -1026;
    public static final int Code_RecognizeTooLong = -1027;

    public static final int Code_pleasecheckuserinfo = -1028;
    public static final int Code_sessionbeginerror = -1029;
    public static final int Code_tokeninvalid = -1030;
    public static final int Code_serverresulterror = -1031;
    public static final int Code_cannotlinkt = -1032;
    public static final int Code_nothavettscontent = -1033;
    public static final int Code_vadiniterror = -1034;
    public static final int Code_speechtimeout = -1035;
    public static final int Code_waitserverresponseresulttimeout = -1036;
    public static final int Code_BadRequest = -1037;
    public static final int Code_Unauthorized = -1038;
    public static final int Code_UnGranted = -1039;
    public static final int Code_Internal = -1040;
    public static final int Code_NotSupported = -1041;
    public static final int Code_TooManyRequests = -1042;
    public static final int Code_ServiceUnAvailable = -1043;
    public static final int Code_ServiceUnknownFormat = -1044;
    public static final int Code_ServiceMismatch = -1045;
    public static final int Code_GetAsrServiceError = -1046;
    public static final int Code_SpeechRecognizeError = -1047;
    public static final int Code_SpeechRecognizeEmpty = -1048;
    public static final int Code_GetAiServiceError = -1049;
    public static final int Code_AiInternalError = -1050;
    public static final int Code_AiNotSupported = -1051;
    public static final int Code_GetTtsServiceError = -1052;
    public static final int Code_TtsServiceInternalError = -1053;
    public static final int Code_AsrTimeOutint = -1054;
    public static final int Code_AsrStreamingTimeOutint = -1055;

    public static final int Code_ERROR_AUDIO = -1056;
    public static final int Code_ERROR_SPEECH_TIMEOUT = -1057;
    public static final int Code_ERROR_CLIENT = -1058;
    public static final int Code_ERROR_INSUFFICIENT_PERMISSIONS = -1059;
    public static final int Code_ERROR_NETWORK = -1060;
    public static final int Code_ERROR_NO_MATCH = -1061;
    public static final int Code_ERROR_RECOGNIZER_BUSY = -1062;
    public static final int Code_ERROR_SERVER = -1063;
    public static final int Code_ERROR_NETWORK_TIMEOUT = -1064;

    public static final int Code_SdcardNoMounted = -1065;
    public static final int Code_WifiUploadSwitchClose = -1066;

    public static final int Code_FileNoExist = -1067;
    public static final int Code_FileIsDirs = -1068;
    public static final int Code_OtherException = -1069;

    public static final int Code_Md5Error = -1070;
    public static final int Code_IOException = -1071;
    public static final int Code_JSONException = -1072;
    public static final int Code_SERVER_RESULT_MD5_FAILED = -1073;
    public static final int Code_WifiUploadFileFailed = -1074;


    public static final int Code_LoginAuthing = -1075;
    public static final int Code_StartLogin = -1076;

    public static final int Code_DeviceNotProducedByOurCompany = -1077;
    public static final int Code_DeviceIsNotAvailable = -1078;


    public static final int Code_StartGetEngines = -1079;

    public static final int Code_InvalidDeviceId = -1080;
    public static final int Code_InvalidToken = -1081;

    public static final int Code_GetEnginesing = -1082;
    public static final int Code_GetEnginesSuccess = -1083;

    public static final int Code_Expiredtoken = -1084;

    public static final int Code_ErrorParams = -1085;

    public static final int Code_Recognizeing = -1086;

    public static final int Code_NoSuportEngine = -1087;
    public static final int Code_NoEngineSuportTheLanguage = -1088;

    public static final int Code_ConnectServerFailed = -1089;

    public static final int Code_FileSpeechNowChangToMicSpeech = -1090;
    public static final int Code_MicSpeechNowChangToFileSpeech = -1091;
    public static final int Code_NullFilePath = -1092;
    public static final int Code_File_READ_ERROR = -1093;
    public static final int Code_Get_AccessToken_ERROR = -1094;
    public static final int Code_PoorAudioQuality = -1095;
    public static final int Code_AuthenticationFailed = -1096;
    public static final int Code_VoiceServerBackendIssues = -1097;
    public static final int Code_UserRequestQPSExceedsLimit = -1098;
    public static final int Code_TheUserDailylv_DayRequests_ExceedsTheLimit = -1099;
    public static final int Code_SpeechServerBackendIdentificationErrorProblem = -1100;
    public static final int Code_AudioIsTooLong = -1101;
    public static final int Code_AudioDataProblem = -1102;
    public static final int Code_TheInputAudioFileIsTooLarge = -1103;
    public static final int Code_SampleRateParameterIsNotInTheOption = -1104;
    public static final int Code_AudioFormatParameterIsNotInOption = -1105;

    public static final int Code_Working = -1106;
    public static final int Code_ErrorGetBitmap = -1107;
    public static final int Code_OCR_Exception = -1108;
    public static final int Code_OCR_RecogniseFail = -1109;

    public static int RooboErrorCode2Standard(int errorcode) {
        int trescode = Code_Unknown;
        switch(errorcode) {
            case 100:
                trescode = Code_pleasecheckuserinfo;
                break;
            case 101:
                trescode = Code_sessionbeginerror;
                break;
            case 102:
                trescode = Code_tokeninvalid;
                break;
            case 103:
                trescode = Code_serverresulterror;
                break;
            case 104:
                trescode = Code_PoorNetwork;
                break;
            case 200:
                trescode = Code_nothavettscontent;
                break;
            case 201:
                trescode = Code_vadiniterror;
                break;
            case 202:
                trescode = Code_speechtimeout;
                break;
            case 203:
                trescode = Code_waitserverresponseresulttimeout;
                break;
            case 400:
                trescode = Code_BadRequest;
                break;
            case 401:
                trescode = Code_Unauthorized;
                break;
            case 402:
                trescode = Code_UnGranted;
                break;
            case 500:
                trescode = Code_Internal;
                break;
            case 501:
                trescode = Code_NotSupported;
                break;
            case 502:
                trescode = Code_TooManyRequests;
                break;
            case 601:
                trescode = Code_ServiceUnAvailable;
                break;
            case 602:
                trescode = Code_ServiceUnknownFormat;
                break;
            case 603:
                trescode = Code_ServiceMismatch;
                break;
            case 604:
                trescode = Code_GetAsrServiceError;
                break;
            case 605:
                trescode = Code_SpeechRecognizeError;
                break;
            case 606:
                trescode = Code_SpeechRecognizeEmpty;
                break;
            case 607:
                trescode = Code_GetAiServiceError;
                break;
            case 608:
                trescode = Code_AiInternalError;
                break;
            case 609:
                trescode = Code_AiNotSupported;
                break;
            case 610:
                trescode = Code_GetTtsServiceError;
                break;
            case 611:
                trescode = Code_TtsServiceInternalError;
                break;
            case 614:
                trescode = Code_AsrTimeOutint;
                break;
            case 615:
                trescode = Code_AsrStreamingTimeOutint;
                break;
            case 10102:
                trescode = Code_ERROR_NETWORK;
                break;
            default:
                trescode = Code_Unknown;
                break;
        }
        return trescode;
    }

    public static JSONObject toJsonResult(int errorCode) {
        try {
            JSONObject json = new JSONObject();
            json.put("err_no", errorCode);
            //json.put("error_msg", errorMsg);
            return json;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject toJsonResult(int errorCode,JSONArray result) {
        try {
            JSONObject json = new JSONObject();
            json.put("err_no", errorCode);
            json.put("result", result);
            return json;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
