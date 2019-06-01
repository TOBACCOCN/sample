package com.example.demo.audio;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Wav2pcm {
	
	public static void main(String[] args) throws Exception {
		wav2pcm("C:\\Users\\Administrator\\Desktop\\wav.wav", "C:\\Users\\Administrator\\Desktop\\pcm.pcm");
	}
	
	public static void wav2pcm(String wavPath, String pcmPath) throws Exception {
		FileInputStream fis = new FileInputStream(wavPath);
		FileOutputStream fos = new FileOutputStream(pcmPath);
		byte[] wavBytes = inputStreamToByte(fis);
		byte[] pcmBytes = Arrays.copyOfRange(wavBytes, 44, wavBytes.length);
		fos.write(pcmBytes);
		fis.close();
		fos.close();
	}

	private static byte[] inputStreamToByte(FileInputStream fis) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 8];
		int len;
		while ((len = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		byte[] data = baos.toByteArray();
		baos.close();
		return data;
	}
}
