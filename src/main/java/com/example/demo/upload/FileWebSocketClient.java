package com.example.demo.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.Map;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by janson on 2018/5/17.
 */

public class FileWebSocketClient extends WebSocketClient {

	private static Logger logger = LoggerFactory.getLogger(FileWebSocketClient.class);

	public final static int CLIENT_STATE_REQUEST_SEND = 0;
	public final static int CLINET_STATE_SEND_FINISHED = 1;

	public final static int SERVER_RESULT_NOT_JSON = -1;
	public final static int SERVER_RESULT_ILL_STATE = -2;
	public final static int SERVER_RESULT_MD5_FAILED = -3;

	public final static int SERVER_RESULT_INTERNAL_ERROR = -100;

	public final static int SERVER_RESULT_START_TO_RECEIVE = 1;
	public final static int SERVER_RESULT_RECEIVE_SUCCESS = 2;

	public final static int ERROR_CODE_NO_ERRO = 0;
	public final static int ERROR_CODE_URI_ERROR = -1;
	public final static int ERROR_CODE_FILE_NOT_EXIST = -2;
	public final static int ERROR_CODE_FILE_IO_ERROR = -3;
	public final static int ERROR_CODE_SEVER_RESPONSE_ERRO = -4;
	public final static int ERROR_CODE_NETWORK_ERRO = -5;
	public final static int ERROR_CODE_MD5_FAILED = -6;

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

//    public FileWebSocketClient(URI serverUri, Map<String, String> httpHeaders,String fileName, String userId, String token) {
	public FileWebSocketClient(URI serverUri, Map<String, String> httpHeaders, String fileName, String recogFileName,
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

	public int getCurErrorCode() {
		return mcurerrorcode;
	}

	public boolean getIsSendSuccess() {
		return msendSuccess;
	}

	public void setHasConnecting(boolean flag) {
		this.mHasConnecting = flag;
	}

	public boolean getHasConnecting() {
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
		logger.info("onOpen(),filewebsocket,start");
		msendSuccess = false;
		setConnectedState(true);
		setHasConnecting(false);

		int trescode = requestSendFileToServer();
		logger.info("onOpen(),filewebsocket,trescode=" + trescode);
		setCurErrorCode(trescode);
		logger.info("onOpen(),filewebsocket,end");
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
					logger.info("file suffix: " + suffix);
//					String md5 = md5(tfile);

                    String md5 = Md5Utils.md5ForFile(tfile);
					if (null != md5) {
						JSONObject json = new JSONObject();
						json.put("state", CLIENT_STATE_REQUEST_SEND);
						json.put("fileType", suffix);
						json.put("MD5", md5);
						json.put("srcLang", "zh-CN");
//                        json.put("destLang", "en-US");
//                        json.put("createTime", "20190313130900000");
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
		logger.info("onMessage(),filewebsocket,start");
		logger.info("onMessage(),filewebsocket,message=" + message);
		int terrorcode = handleMessage(message);
		logger.info("onMessage(),filewebsocket,terrorcode=" + terrorcode);
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

		logger.info("onMessage(),filewebsocket,end");
	}

	private int handleMessage(String message) {
		if (null == message || "".equals(message)) {
			return ErrorCode.Code_NullParams;
		}
		logger.info("handleMessage(),filewebsocket,message=" + message);
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
//					jsn.put("MD5", Md5Utils.md5ForFile(new File(mfileName)));
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
		logger.info("onClose(),filewebsocket,start");
		logger.info("onClose(),filewebsocket,code=" + code);
		logger.info("onClose(),filewebsocket,reason=" + reason);
		logger.info("onClose(),filewebsocket,remote=" + remote);
		if (msendSuccess) {
			setCurErrorCode(ErrorCode.Code_OK);
		} else {
			setCurErrorCode(ErrorCode.Code_PoorNetwork);
		}

		setConnectedState(false);
		setHasConnecting(false);
		logger.info("onClose(),filewebsocket,end");
	}

	@Override
	public void onError(Exception ex) {
		logger.info("onError(),filewebsocket,start");
		if (null != ex) {
			logger.info("onError(),filewebsocket,ex=" + ex.toString());
		}
		if (msendSuccess) {
			setCurErrorCode(ErrorCode.Code_OK);
		} else {
			setCurErrorCode(ErrorCode.Code_PoorNetwork);
		}

		setConnectedState(false);
		setHasConnecting(false);
		logger.info("onError(),filewebsocket,end");
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

		public void shutdown() {
			run = false;
		}

		private long position;
		private boolean isAudioFile;

		public SendDataThread(long position, boolean isAudioFile) {
			super();
			this.position = position;
			this.isAudioFile = isAudioFile;
		}

		@Override
		public void run() {
			int terrorcode = ErrorCode.Code_OK;
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
								json.put("state", CLINET_STATE_SEND_FINISHED);
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
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					terrorcode = ErrorCode.Code_FileNoExist;
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
