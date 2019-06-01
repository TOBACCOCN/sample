package com.example.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class QRCodeUtil {

	private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

	public static void main(String[] args) throws Exception {
		// 生成二维码
		String url = "https://qr.alipay.com/fkx09314x4cvcllpxtmr3bd";
		int width = 258;
		int height = 258;
		String qrCodeFileName = "D:\\download\\AliPayQR.png";
		String type = "png";
		encode(url, width, height, qrCodeFileName, type);

		// 解析二维码
		decode(qrCodeFileName);
	}

	public static void encode(String url, int width, int height, String qrCodeFileName, String type) throws Exception {
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height);
		OutputStream os = new FileOutputStream(new File(qrCodeFileName));
		MatrixToImageWriter.writeToStream(matrix, type, os);
		logger.info(">>>>> WRITE QR_CODE TO {}, DONE", qrCodeFileName);
	}

	public static void decode(String qrCodeFileName) throws Exception  {
		QRCodeReader reader = new QRCodeReader();
		BufferedImage bufferedImage = ImageIO.read(new File(qrCodeFileName));
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Result result = reader.decode(bitmap);
		logger.info(">>>>> RESULT: {}", result.toString());
	}

}
