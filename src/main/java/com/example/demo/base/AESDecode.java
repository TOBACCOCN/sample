package com.example.demo.base;

import com.example.demo.util.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESDecode {

	private static Logger logger = LoggerFactory.getLogger(AESDecode.class);

	private static  void decode(String srcFilePath, String desFilePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(srcFilePath));
		BufferedWriter writer = new BufferedWriter(new FileWriter(desFilePath));
		String line;
		StringBuilder builder  = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			builder.append(line);
		}
		writer.write(Objects.requireNonNull(AESUtil.decode(builder.toString())));
		reader.close();
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		decode("", "");
		logger.info(">>>>> DONE");
	}

}
