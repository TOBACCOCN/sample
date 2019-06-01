package com.example.demo.amap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CalculatorDistance {

	public static void main(String[] args) {
//		String start = "浙江省杭州市西湖区";  
//		String end = "郑州市金水区";  
//	  
//		String startLngLat = getLngLat(start);  
//		String endLngLat = getLngLat(end);  
		String startLngLat = "113.924624,22.504315";
		String endLngLat = "113.924002,22.502828";

		System.out.println(startLngLat);
		System.out.println(endLngLat);
		Long distance = getDistance(startLngLat, endLngLat);
		System.out.println(distance);
	}

//	private static String getLngLat(String address) {
//		// 返回输入地址address的经纬度信息, 格式是 经度,纬度
//		String url = "http://restapi.amap.com/v3/geocode/geo?key=508eb8fd7521df6de675d0b09e6179b0&address="
//				+ address;
//		String result = sendHttpRequest(url); // 高德接品返回的是JSON格式的字符串
//
//		JSONArray ja = JSONObject.fromObject(result).getJSONArray("geocodes");
//		return JSONObject.fromObject(ja.getString(0)).get("location").toString();
//	}

	private static Long getDistance(String startLngLat, String endLngLat) {
		// 返回起始地startLonLat与目的地endAddr之间的距离，单位：米
		String url = "http://restapi.amap.com/v3/distance?key=508eb8fd7521df6de675d0b09e6179b0&origins="
				+ startLngLat + "&destination=" + endLngLat;
		String result = sendHttpRequest(url);
		JSONArray jsonArray = JSONObject.fromObject(result).getJSONArray("results");
		return Long.parseLong(JSONObject.fromObject(jsonArray.getString(0)).get("distance").toString());
	}

	private static String sendHttpRequest(String requestUrl) {
		// 用JAVA发起http请求，并返回json格式的结果
		StringBuffer result = new StringBuffer();
		try {
			URL url = new URL(requestUrl);
			URLConnection conn = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}