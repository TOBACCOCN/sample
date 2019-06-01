package com.example.demo.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnectionDownloadTest {

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= 100; i++) {
			new Thread(new MyRunnable(i)).start();
		}
	}

}

class MyRunnable implements Runnable {

	private int i;

	public MyRunnable(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		try {
			URL httpurl = new URL("https://trans.eliteei.com:9449/TeamViewerHost.apk");
			HttpURLConnection httpConn = (HttpURLConnection) httpurl.openConnection();
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("GET");// 设置URL请求方法
			// 可设置请求头
//			httpConn.setRequestProperty("Content-Type", "application/octet-stream");
			httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
			httpConn.setRequestProperty("Charset", "UTF-8");
			write2File(httpConn.getInputStream(), new File("F://temp//" + i + ".apk"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    private void write2File(InputStream inputStream, File file) throws Exception {
        int len;
        byte[] buf = new byte[8192];
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fos =  new FileOutputStream(file);
        while((len = inputStream.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        inputStream.close();
        fos.close();
    }

}