package com.example.demo.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Enter2blank {
	
	public static void main(String[] args) throws IOException {
		doEnter2blank("C:\\Users\\Administrator\\Desktop\\demo.txt", "C:\\Users\\Administrator\\Desktop\\zyh.txt");
	}

	private static void doEnter2blank(String srcPath, String destPath) throws IOException {
		BufferedReader  br = new BufferedReader(new FileReader(srcPath));
		String line = null;
		StringBuilder builder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			builder.append(line).append(" ");
		}
		br.close();
		BufferedWriter writer = new BufferedWriter(new FileWriter(destPath));
		String output = builder.toString();
		output = output.substring(0, output.length() - 1);
		writer.write(builder.toString());
		writer.close();
	}
	
}
