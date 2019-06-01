package com.example.demo.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;

public class JSONTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "zhangsan");
		map.put("gender", "male");
		String jsonString = JSON.toJSONString(map, true);
		JSONObject object = JSONObject.fromObject(map);
		System.out.println(object.toString());
		System.out.println(jsonString);
//		String result = "{\"status\": \"good\"}";
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		int status = jsonObject.getInt("status");
//		System.out.println(status);
//		System.out.println(System.currentTimeMillis());
		
//		String personStr = "{\"name\":\"zyh\",\"addr\":\"shenzhen\"}";
//		Person person = JSON.parseObject(personStr, Person.class);
//		System.out.println(person.toString());
		Person person = new Person();
		person.setAge(2);
		
		System.out.println(person);
		
//		com.alibaba.fastjson.JSONObject jsonObj = new com.alibaba.fastjson.JSONObject();
//		System.out.println(jsonObj.getIntValue("ai"));
//		FileOutputStream outputStream = new FileOutputStream(new File("d:\\f.txt"));//创建文件字节输出流对象
//		ObjectOutputStream ois = new ObjectOutputStream(outputStream);
//		ois.writeObject(person);
//		ois.close();
		Class.forName("aaaaaaaa");
		System.out.println(System.getProperty("java.library.path"));
	}
	
}

class Person {
	
	private String name;
	
	private int age;
	
	private String gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender + "]";
	}
	
}
