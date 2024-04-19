package com.example.sample;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// 总结：utf8 文本经过 gbk 读取（原始文本的字节）解码后乱码，乱码的文本的字节跟原始文本的字节相差不大，
// 再将这些乱码文本的字节以 utf8 编码成串时，得到的文本与初始文本相差不大，基本就最后一个字符不对；
// gbk 文本经过 utf8 读取（原始文本的字节）解码后乱码，乱码的文本的字节跟原始文本的字节相差很大，
// 再将这些乱码文本的字节以 gbk 编码成串时，得到的文本与初始文本相差也就很大
public class CharacterSetTest {

	private static final Charset GBK = Charset.forName("GB2312");
	// private static final Charset GBK = Charset.forName("GBK");
	// private static final Charset GBK = Charset.forName("GB18030");

	@Test
	public void gbk2utf82gbk() throws IOException {
		Path path = Paths.get("D:/git/sample/src/test/resources/character_gbk.txt");
		InputStream inputStream = Files.newInputStream(path);
		byte[] read = new byte[1024];
		inputStream.read(read);
		System.out.println("origin bytes: ");
		for (byte b : read) {
			if (b == 0) {
				break;
			}
			System.out.print(b + " ");
		}
		System.out.println();

		// BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(path), GBK));   // br.readLine() 正常显示
		BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8));
		String gbk2utf8 = br.readLine();
		System.out.println("gbk2utf8: " + gbk2utf8);
		System.out.println("gbk2utf8 bytes:");
		for (byte b : gbk2utf8.getBytes(StandardCharsets.UTF_8)) {
			System.out.print(b + " ");
		}
		System.out.println();

		String gbk2utf82gbk = new String(gbk2utf8.getBytes(StandardCharsets.UTF_8), GBK);
		System.out.println("gbk2utf82gbk: " + gbk2utf82gbk);
		br.close();
	}

	@Test
	public void utf82gbk2utf8() throws IOException {
		Path path = Paths.get("D:/git/sample/src/test/resources/character_utf8.txt");
		InputStream inputStream = Files.newInputStream(path);
		byte[] read = new byte[1024];
		inputStream.read(read);
		System.out.println("origin bytes: ");
		for (byte b : read) {
			if (b == 0) {
				break;
			}
			System.out.print(b + " ");
		}
		System.out.println();
		// BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8));
		BufferedReader br = new BufferedReader(new InputStreamReader(Files.newInputStream(path), GBK));
		String utf82gbk = br.readLine();
		System.out.println("utf82gbk: " + utf82gbk);
		System.out.println("utf82gbk bytes: ");
		for (byte b : utf82gbk.getBytes(GBK)) {
			System.out.print(b + " ");
		}
		System.out.println();

		String utf82gbk2utf8 = new String(utf82gbk.getBytes(GBK), StandardCharsets.UTF_8);
		System.out.println("utf82gbk2utf8: " + utf82gbk2utf8);
		br.close();
	}

}
