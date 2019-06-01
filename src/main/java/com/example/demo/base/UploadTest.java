package com.example.demo.base;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UploadTest {
	
	public static void main(String[] args) throws Exception {
		String BOUNDARY = "fa9e6f4d77b74954b256222f7d4f7e0d";
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		String url = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		url = url.replace("ACCESS_TOKEN", "8_RNwSZuZF9STMvVf_Re1Pl-lBC7T_EHK-1GQxV1yLFwBy5RHMzuQtGVGnmJfE4c3l8gW-ScshMuv4dxRD61v-A1p77GlvWt2GQV9dj3w8O2sM7XDFROAtGTTTAgiCJbCdo2zyaBQ98I0isLH1MULeAGAHPG").replace("TYPE", "image");
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("charset", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
        
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        InputStream in = null;
        Map<String, File> files = new HashMap<String, File>();
        files.put("publicAccount.png", new File("D:\\ui\\images\\publicAccount.png"));
        // 发送文件数据
        if (files != null) {
        for (Map.Entry<String, File> file : files.entrySet()) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(PREFIX);
	        sb.append(BOUNDARY);
	        sb.append(LINEND);
	        // name是post中传参的键 filename是文件的名称
	        sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
	        sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
	        sb.append(LINEND);
	        outStream.write(sb.toString().getBytes());
	
	        InputStream is = new FileInputStream(file.getValue());
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = is.read(buffer)) != -1) {
	        	outStream.write(buffer, 0, len);
	        }
	
	        is.close();
	        outStream.write(LINEND.getBytes());
        }
        
        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        if (res == 200) {
	        in = conn.getInputStream();
	        int ch;
	        StringBuilder sb2 = new StringBuilder();
	        while ((ch = in.read()) != -1) {
	        	sb2.append((char) ch);
	        }
	        System.out.println(sb2.toString());
        }
        outStream.close();
        conn.disconnect();
        }
	}

}
