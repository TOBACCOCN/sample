package com.example.sample.websocketupload;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.Map;

/**
 * Created by janson on 2018/5/17.
 */

@Slf4j
public class FileWebSocketClient extends WebSocketClient {

    // private static Logger logger = LoggerFactory.getLogger(FileWebSocketClient.class);

    private final static int CLIENT_STATE_REQUEST_SEND = 0;
    private final static int CLIENT_STATE_SEND_FINISHED = 1;
    private final static int SERVER_RESULT_MD5_FAILED = -3;
    private final static int SERVER_RESULT_START_TO_RECEIVE = 1;
    private final static int SERVER_RESULT_RECEIVE_SUCCESS = 2;

    private SendDataThread mSendDataThread = null;

    private String mfileName;
    private String recogFileName;
    private String muserId;
    private String mtoken;

    private boolean msendSuccess = false;
    private int mcurerrorcode = ErrorCode.Code_OK;

    private volatile boolean mHasConnecting = false;
    private volatile boolean mConnectedState = false;
    private boolean audioUploaded = false;
    private boolean textUploaded = false;

    // public FileWebSocketClient(URI serverUri, Map<String, String> httpHeaders,String fileName, String userId, String token) {
    FileWebSocketClient(URI serverUri, Map<String, String> httpHeaders, String fileName, String recogFileName,
                        String userId, String token) {
        super(serverUri, httpHeaders);
        this.mfileName = fileName;
        this.recogFileName = recogFileName;
        this.muserId = userId;
        this.mtoken = token;
        httpHeaders.put("deviceId", userId);
        httpHeaders.put("md5", token);
    }

    private void setCurErrorCode(int code) {
        mcurerrorcode = code;
    }

    int getCurErrorCode() {
        return mcurerrorcode;
    }

    boolean getIsSendSuccess() {
        return msendSuccess;
    }

    void setHasConnecting(boolean flag) {
        this.mHasConnecting = flag;
    }

    boolean getHasConnecting() {
        return this.mHasConnecting;
    }

    private void setConnectedState(boolean flag) {
        this.mConnectedState = flag;
    }

    public boolean getConnectedState() {
        return this.mConnectedState;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.info(">>>>> onOpen(),filewebsocket,start");
        msendSuccess = false;
        setConnectedState(true);
        setHasConnecting(false);

        int trescode = requestSendFileToServer();
        log.info(">>>>> onOpen(),filewebsocket,trescode=[{}]", trescode);
        setCurErrorCode(trescode);
        log.info(">>>>> onOpen(),filewebsocket,end");
    }

    private int requestSendFileToServer() {
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
                try {
                    String suffix = getSuffix(mfileName);
                    log.info(">>>>> file suffix: [{}]", suffix);
                    // String md5 = md5(tfile);

                    String md5 = Md5Utils.md5ForFile(tfile);
                    if (null != md5) {
                        JSONObject json = new JSONObject();
                        json.put("state", CLIENT_STATE_REQUEST_SEND);
                        json.put("fileType", suffix);
                        json.put("MD5", md5);
                        json.put("srcLang", "zh-CN");
                        // json.put("destLang", "en-US");
                        //  json.put("createTime", "20190313130900000");
                        this.send(json.toString());
                    } else {
                        return ErrorCode.Code_Md5Error;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return ErrorCode.Code_JSONException;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ErrorCode.Code_OtherException;
                }
            } else {
                return ErrorCode.Code_FileIsDirs;
            }
        } else {
            return ErrorCode.Code_FileNoExist;
        }

        return ErrorCode.Code_OK;
    }


    private String getSuffix(String name) {
        if (null != name) {
            int point = name.lastIndexOf(".");
            if (-1 == point) {
                return "undefined";
            }
            String suffix = name.substring(name.lastIndexOf(".") + 1);
            return suffix;
        } else
            return "nofile";
    }

    @Override
    public void onMessage(String message) {
        log.info(">>>>> onMessage(),filewebsocket,start");
        log.info(">>>>> onMessage(),filewebsocket,message=[{}]", message);
        int terrorcode = handleMessage(message);
        log.info(">>>>> onMessage(),filewebsocket,terrorcode=[{}]", terrorcode);
        setCurErrorCode(terrorcode);
        if (ErrorCode.Code_SERVER_RESULT_START_TO_RECEIVE == terrorcode) {
            // wait send file
        } else if (ErrorCode.Code_OK == terrorcode) {
            if (textUploaded) {
                this.close();
            }
        } else if (ErrorCode.Code_SERVER_RESULT_MD5_FAILED == terrorcode) {
            this.close();
        } else if (ErrorCode.Code_OtherException == terrorcode) {
            this.close();
        }

        log.info(">>>>> onMessage(),filewebsocket,end");
    }

    private int handleMessage(String message) {
        if (null == message || "".equals(message)) {
            return ErrorCode.Code_NullParams;
        }
        log.info(">>>>> handleMessage(),filewebsocket,message=[{}]",message);
        try {
            JSONObject json = new JSONObject(message);
            if (SERVER_RESULT_START_TO_RECEIVE == json.getInt("result")) {
                long position = json.getLong("length");
                if (audioUploaded && !textUploaded) {
                    mSendDataThread = new SendDataThread(position, false);
                } else {
                    mSendDataThread = new SendDataThread(position, true);
                }
                mSendDataThread.start();
                return ErrorCode.Code_SERVER_RESULT_START_TO_RECEIVE;
            } else if (SERVER_RESULT_RECEIVE_SUCCESS == json.getInt("result")) {
                if (audioUploaded && !textUploaded) {
                    JSONObject jsn = new JSONObject();
                    jsn.put("state", CLIENT_STATE_REQUEST_SEND);
                    // jsn.put("MD5", Md5Utils.md5ForFile(new File(mfileName)));
                    jsn.put("MD5", Md5Utils.md5ForFile(new File(recogFileName)));
                    jsn.put("srcLang", "zh-CN");
                    jsn.put("fileType", "txt");
                    send(jsn.toString());
                } else {
                    msendSuccess = true;
                }
                return ErrorCode.Code_OK;
            } else if (SERVER_RESULT_MD5_FAILED == json.getInt("result")) {
                // callBack(ERROR_CODE_MD5_FAILED);
                // this.close();
                return ErrorCode.Code_SERVER_RESULT_MD5_FAILED;
            } else {
                // client.close();
                return ErrorCode.Code_OtherException;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // callBack(ERROR_CODE_SEVER_RESPONSE_ERRO);
            return ErrorCode.Code_JSONException;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info(">>>>> onClose(),filewebsocket,start");
        log.info(">>>>> onClose(),filewebsocket,code=[{}]", code);
        log.info(">>>>> onClose(),filewebsocket,reason=[{}]", reason);
        log.info(">>>>> onClose(),filewebsocket,remote=[{}]", remote);
        if (msendSuccess) {
            setCurErrorCode(ErrorCode.Code_OK);
        } else {
            setCurErrorCode(ErrorCode.Code_PoorNetwork);
        }

        setConnectedState(false);
        setHasConnecting(false);
        log.info(">>>>> onClose(),filewebsocket,end");
    }

    @Override
    public void onError(Exception ex) {
        log.info(">>>>> onError(),filewebsocket,start");
        if (null != ex) {
            log.info(">>>>> onError(),filewebsocket,ex=[{}]", ex.toString());
        }
        if (msendSuccess) {
            setCurErrorCode(ErrorCode.Code_OK);
        } else {
            setCurErrorCode(ErrorCode.Code_PoorNetwork);
        }

        setConnectedState(false);
        setHasConnecting(false);
        log.info(">>>>> onError(),filewebsocket,end");
    }

    public void shutDown() {
        if (null != mSendDataThread) {
            mSendDataThread.shutdown();
            mSendDataThread = null;
        }
    }

    private class SendDataThread extends Thread {
        private final static int BUFFER_LENGTH = 1024 * 8;

        private boolean run = true;

        void shutdown() {
            run = false;
        }

        private long position;
        private boolean isAudioFile;

        SendDataThread(long position, boolean isAudioFile) {
            super();
            this.position = position;
            this.isAudioFile = isAudioFile;
        }

        @Override
        public void run() {
            int terrorcode;
            RandomAccessFile randomFile = null;

            if (null == mfileName || "".equals(mfileName)) {
                terrorcode = ErrorCode.Code_FileNoExist;
            } else {
                try {
                    if (isAudioFile) {
                        randomFile = new RandomAccessFile(mfileName, "r");
                    } else {
                        randomFile = new RandomAccessFile(recogFileName, "r");
                    }
                    randomFile.seek(position);
                    byte[] buffer = new byte[BUFFER_LENGTH];
                    while (run) {
                        int len;
                        if ((len = randomFile.read(buffer)) != -1) {
                            if (BUFFER_LENGTH == len) {
                                send(buffer);
                            } else {
                                byte[] temp = new byte[len];
                                System.arraycopy(buffer, 0, temp, 0, len);
                                send(temp);
                            }
                        } else {
                            JSONObject json = new JSONObject();
                            try {
                                json.put("state", CLIENT_STATE_SEND_FINISHED);
                                send(json.toString());
                                if (isAudioFile) {
                                    audioUploaded = true;
                                } else {
                                    textUploaded = true;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        // randomFile.write("def".getBytes(), 0, 3);
                    }
                    terrorcode = ErrorCode.Code_OK;
                } catch (IOException e) {
                    e.printStackTrace();
                    terrorcode = ErrorCode.Code_FileNoExist;
                }

                if (null != randomFile) {
                    try {
                        randomFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            setCurErrorCode(terrorcode);
            super.run();
        }
    }

}
