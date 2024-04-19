package com.example.sample.base;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DownloadImage {

	public static void main(String[] args) throws IOException {
		Document document = Jsoup.connect("https://cdn.jsdelivr.net/gh/wanglindl/TVlogo@main/img/").timeout(10000).get();
		Elements as = document.select("table a[rel='nofollow']");
		String downloadDir = "D:/download/logo/";
		as.forEach(a -> {
			try {
				downloadImage(downloadDir, a.absUrl("href"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
	}

	private static void downloadImage(String downloadDir, String imageUrl) throws Exception {
		URL url = new URL(imageUrl);
		String filename = imageUrl.substring(imageUrl.lastIndexOf("/"));
		InputStream in = url.openStream(); // 打开图片链接的输入流

		Path path = Paths.get(downloadDir + filename);
		// 创建父目录（如果不存在）
		Path parentDir = path.getParent();
		if (parentDir != null && !Files.exists(parentDir)) {
			Files.createDirectories(parentDir);
		}
		OutputStream out = Files.newOutputStream(path); // 创建图片文件的输出流

		byte[] buffer = new byte[2048];
		int length;

		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length); // 将图片数据写入文件
		}

		in.close(); // 关闭输入流
		out.close(); // 关闭输出流
	}


}
