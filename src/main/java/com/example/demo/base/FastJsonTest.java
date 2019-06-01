package com.example.demo.base;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonTest {
	
	public static void main(String[] args) {
		JSONObject jsonObject = JSON.parseObject("{\"name\":\"zyh\"}");
		System.out.println(jsonObject);
		JSONArray jsonArray = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]");
		System.out.println(jsonArray);
		
		Zhang zhang = JSON.parseObject("{\"name\":\"zyh\"}", Zhang.class);
		System.out.println(zhang);
		List<Zhang> zhangs = JSON.parseArray("[{\"name\":\"zyh\"}, {\"name\":\"zys\"}]", Zhang.class);
		System.out.println(zhangs);
		
		System.out.println(JSON.toJSONString(zhang));
		System.out.println(JSON.toJSONString(zhangs));
		
		System.out.println(jsonObject.toJSONString());
		System.out.println(jsonArray.toJSONString());
	}

}

class Zhang {
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Zhang [name=" + name + "]";
	}
	
}
