package com.example.sample.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AESUtilTest {

	@Test
	public void encode() {
		String encode = AESUtil.encode("hello");
		log.info("ENCODE: [{}]", encode);
	}

	@Test
	public void decode() {
		String decode = AESUtil.decode(AESUtil.encode("hello"));
		log.info("DECODE: [{}]", decode);
	}
}