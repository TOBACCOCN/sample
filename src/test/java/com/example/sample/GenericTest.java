package com.example.sample;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GenericTest {

	private List<String> l;

	@Test
	public void generic() throws NoSuchFieldException {
		// 注意这里的 list 对象是 ArrayList 的子类对象，这样在 getActualTypeArguments() 时才能拿到泛型类型
		List<String> list = new ArrayList<String>(){};
		list.add("");
		Class<?> superclass = list.getClass().getSuperclass();
		log.info("[{}]", superclass);
		Type type = list.getClass().getGenericSuperclass();
		log.info("[{}]", type);
		Type pt = ((ParameterizedType) list.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		log.info("pt: [{}]", pt.getTypeName());

		ParameterizedType parameterizedType = (ParameterizedType) GenericTest.class.getDeclaredField("l").getGenericType();
		log.info("parameterizedType: [{}]", parameterizedType.getTypeName());

		Class<?>[] interfaces = list.getClass().getInterfaces();
		for (Class<?> clazz : interfaces) {
			log.info("[{}]", clazz);
		}
	}

}
