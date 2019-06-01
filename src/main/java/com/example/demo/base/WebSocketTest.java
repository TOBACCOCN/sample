package com.example.demo.base;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketTest {

	private static Logger logger = LoggerFactory.getLogger(WebSocketTest.class);

	private static final String BASE_SERVER_URL = "https://trans.eliteei.com:9443";

	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				NettyWebSocketClientHandler.clearMessage();

				String websocketUrl = BASE_SERVER_URL.replace("https:", "wss:").replace("http:", "ws:").replace("9443",
						"8443") + "/intercomwss/service";
//	                String websocketUrl = BASE_SERVER_URL.replace("http:", "ws:") + "/intercomwss/service";
				logger.debug("websocketUrl:{}", websocketUrl);
				Map<String, String> headerMap = new HashMap<String, String>();
//	                headerMap.put("deviceId", ANOTHER_DEVICE_ID);
				headerMap.put("deviceId", "1925");
//	                headerMap.put("md5", ANOTHER_MD5);
				headerMap.put("md5", "0c2885b04a283e6a6e185c85e5e2f238");
				logger.debug("headerMap:{}", headerMap);

				// 这里不需要发送什么信息，等着接受信息即可。
				LinkedHashMap<String, String> msgMap = new LinkedHashMap<String, String>();
				msgMap.put("heart", "{\"msgType\":0,\"msgId\":31}");
				logger.debug(" 持续监听............. ");
				try {
					// 持续 12 秒监听.
					NettyWebSocketSecurityClient.sendMessage(websocketUrl, msgMap, headerMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 此线程中，这里往后的代码不会被执行！
			}
		}).start();

		// 估计上面在 5 秒内足够建立连接了
		try {
			Thread.sleep(1000 * 500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
