package com.example.sample.websocketupload;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by janson on 2018/5/17.
 */

public class WifiUploadClient {

    // private final static String SERVER_URL = "wss://test-trans.eliteei.com:8443/device/wifiUpload";
    private final static String SERVER_URL = "ws://127.0.0.1:8443/device/wifiUpload";

    private String mfileName;
    private String recogFileName;
    private String muserId;
    private String mtoken;
    private FileWebSocketClient mclient;

    public WifiUploadClient(String fileName, String recogFileName, String userId, String token) {
        super();
        this.mfileName = fileName;
        this.recogFileName = recogFileName;
        this.muserId = userId;
        this.mtoken = token;
        // this.mSendCallBack = mSendCallBack;
        // this.file = new File(fileName);
    }

    private static final String Item_userId = "userId";
    private static final String Item_token = "token";

    public int startToSend() {
        if (null == mfileName || "".equals(mfileName)) {
            return ErrorCode.Code_NullParams;
        }

        if (null == muserId || "".equals(muserId)) {
            return ErrorCode.Code_NullParams;
        }

        if (null == mtoken || "".equals(mtoken)) {
            return ErrorCode.Code_NullParams;
        }

        File tfile = new File(mfileName);
        if (tfile.exists()) {
            if (tfile.exists()) {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(Item_userId, muserId);
                headers.put(Item_token, mtoken);
                try {
                    // Md5Utils
                    mclient = new FileWebSocketClient(new URI(SERVER_URL), headers, this.mfileName, this.recogFileName,
                            this.muserId, this.mtoken);
                    mclient.setHasConnecting(true);
                    // TrustManager[] tm = {new MyX509TrustManager()};
                    // SSLContext sslContext = SSLContext.getInstance("TLS");
                    // sslContext.init(null, tm, /* new java.security.SecureRandom() */null);
                    // SSLSocketFactory ssf = sslContext.getSocketFactory();
                    // mclient.setSocket(ssf.createSocket());
                    mclient.connect();
                    return ErrorCode.Code_OK;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ErrorCode.Code_OtherException;
            } else {
                return ErrorCode.Code_FileIsDirs;
            }
        } else {
            return ErrorCode.Code_FileNoExist;
        }

    }

    public int getCurErrorCode() {
        if (null != mclient) {
            return mclient.getCurErrorCode();
        }
        return ErrorCode.Code_NullObj;
    }

    public boolean getIsSendSuccess() {
        if (null != mclient) {
            return mclient.getIsSendSuccess();
        }
        return false;
    }

    public boolean getHasConnecting() {
        if (null != mclient) {
            return mclient.getHasConnecting();
        }
        return false;
    }

    public boolean getConnectedState() {
        if (null != mclient) {
            return mclient.getConnectedState();
        }
        return false;
    }

}
