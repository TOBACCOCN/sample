package com.example.demo.base;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GetCountiesFromWorldJs {
	
	public static void main(String[] args) throws IOException {
		byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\Administrator\\Desktop\\word.json"));
		String string = new String(bytes);
		JSONArray jsonArray = JSON.parseArray(string);
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			JSONObject jsonObj = (JSONObject) jsonObject.get("properties");
			String country = jsonObj.getString("name");
			double value = Math.floor(Math.random()*100000);
			if (i % 3 == 0) {
				value = value / 10;
			}
			if (i % 3 == 1) {
				value = value / 100;
			}
			BigDecimal decimal = new BigDecimal(value);
			decimal.setScale(3, BigDecimal.ROUND_HALF_UP);
			builder.append("{\"name\":\""+country+"\",\"simple\":\"\"},");
			i++;
		}
		String content = builder.toString();
		System.out.println("["+content.substring(0, content.length() - 1)+"]");
	}

}
