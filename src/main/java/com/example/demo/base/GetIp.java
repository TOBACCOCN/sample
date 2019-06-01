package com.example.demo.base;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetIp {

	public static void main(String[] args) throws IOException {
//		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Documents\\access.log"),
//				StandardCharsets.UTF_8);
//		Set<String> set = new HashSet<>();
//		for (String string : lines) {
//			if (string.endsWith("\"Go-http-client/1.1\"")) {
//				set.add(string.substring(0, string.indexOf("-")));
//			}
//		}
//		for (String string : set) {
//			System.out.println(string);
//		}
//		System.out.println(set.size());
		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Desktop\\1.txt"),
				StandardCharsets.UTF_8);
		List<String> lines2 = Files.readAllLines(Paths.get("C:\\Users\\Administrator\\Desktop\\2.txt"),
				StandardCharsets.UTF_8);
		Set<String> set = new HashSet<>();
		for (String string : lines) {
			set.add(string);
		}
		for (String string : lines2) {
			set.add(string);
		}
		for (String string : set) {
			System.out.println(string);
		}
		System.out.println(set.size());
	}

}
