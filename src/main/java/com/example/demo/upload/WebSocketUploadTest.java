package com.example.demo.upload;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketUploadTest {
	
//	private static String IP = "192.168.16.128";
//	private static int PORT = 6379;
//	private static String PASSWORD = "water@abc123";
//	private static int MAX_ACTIVE = 500;
//	private static int MAX_IDLE = 5;
//	private static int MAX_WAIT = 10000;
//	private static int TIMEOUT = 10000;
	
	public static void main(String[] args) {
//		JedisPoolConfig config = new JedisPoolConfig();
//		config.setMaxTotal(MAX_ACTIVE);
//		config.setMaxIdle(MAX_IDLE);
//		config.setMaxWaitMillis(MAX_WAIT);
//		JedisPool jedisPool = new JedisPool(config, IP, PORT, TIMEOUT);
//		Jedis jedis = jedisPool.getResource();
//		jedis.auth(PASSWORD);
//		Map<String, String> map = new HashMap<String, String>();
//		for (int i = 1; i <= 1; i++) {
//			map.put("sn:" + i, jedis.get("sn:" + i));
//		}
//		jedisPool.close();
//		String fileName = "C:\\Users\\Administrator\\Desktop\\1.pcm";
//		String recogFileName = "C:\\Users\\Administrator\\Desktop\\1.txt";
		File txtDir = new File("C:\\Users\\Administrator\\Desktop\\demo2");
		File[] txtFiles = txtDir.listFiles();
		File audioDir = new File("D:\\download\\中文英文语音数据\\中文30小时\\wav\\train\\S0003");
		File[] audioFiles = audioDir.listFiles();
		
//		String userId = "1854";
//		String authorize = "cc6bdc8d49e24bb3a2e2003b738edd5e";
		for (int i = 0; i < txtFiles.length; i++) {
//			String json = map.get("sn:" + i);
//			String deviceId = JSONObject.fromObject(json).getString("deviceId");
//			String authorize = JSONObject.fromObject(json).getString("token");
//			new Thread(new UploadRunnable(fileName, deviceId, authorize)).start();
//			new Thread(new UploadRunnable(fileName, "1001908", "42e26092988748898762277a4f43ed08")).start();
//			new Thread(new UploadRunnable(fileName, recogFileName, "2", "fe90e80fb26d040e576d973c4eb4c9ca")).start();
			System.out.println(audioFiles[i].getPath());
			System.out.println(txtFiles[i].getPath());
			new Thread(new UploadRunnable(audioFiles[i].getPath(), txtFiles[i].getPath(), "2", "fe90e80fb26d040e576d973c4eb4c9ca")).start();
		}
	}

}

class UploadRunnable implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(WebSocketUploadTest.class);
	
	private String fileName;
	private String recogFileName;
	private String userId;
	private String token;
	
//	public UploadRunnable(String fileName, String userId, String token) {
	public UploadRunnable(String fileName, String recogFileName, String userId, String token) {
		super();
		this.fileName = fileName;
		this.recogFileName = recogFileName;
		this.userId = userId;
		this.token = token;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public void run() {
//		System.out.println(Md5Utils.md5ForFile(new File("C:\\Users\\Administrator\\Desktop\\1.pcm")));
//		WifiUploadClient uploadClient = new WifiUploadClient(fileName, userId, token);
		WifiUploadClient uploadClient = new WifiUploadClient(fileName, recogFileName, userId, token);
		int startToSend = uploadClient.startToSend();
		logger.info("start to send: "+startToSend);
	}
	
}
