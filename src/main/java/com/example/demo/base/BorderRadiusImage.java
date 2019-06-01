package com.example.demo.base;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BorderRadiusImage {
	
	public static void main(String[] args) throws IOException {
		makeCircularImg("C:\\Users\\TOBACCO\\Desktop\\2.png", "C:\\Users\\TOBACCO\\Desktop\\22.png", 192, 56);
	}
	
  /***
    *
    * @param srcFilePath 源图片文件路径
    * @param circularImgSavePath 新生成的图片的保存路径，需要带有保存的文件名和后缀
    * @param targetSize 文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
    * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
    * @return  文件的保存路径
    * @throws IOException
    */
   public static String makeCircularImg(String srcFilePath, String circularImgSavePath,int targetSize, int cornerRadius) throws IOException {
       BufferedImage bufferedImage = ImageIO.read(new File(srcFilePath));
       BufferedImage circularBufferImage = roundImage(bufferedImage,targetSize,cornerRadius);
       ImageIO.write(circularBufferImage, "png", new File(circularImgSavePath));
       return circularImgSavePath;
   }

   private static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
       BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
       Graphics2D g2 = outputImage.createGraphics();
       g2.setComposite(AlphaComposite.Src);
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g2.setColor(Color.WHITE);
       g2.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
       g2.setComposite(AlphaComposite.SrcAtop);
       g2.drawImage(image, 0, 0, null);
       g2.dispose();
       return outputImage;
   }

}
