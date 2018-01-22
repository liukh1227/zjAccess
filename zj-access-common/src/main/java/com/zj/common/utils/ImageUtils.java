/**
 * 
 */
package com.zj.common.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author liukh
 * @date 2016-12-27 下午12:00:17
 */
public class ImageUtils {
	/** 图片格式：JPG */
	private static final String PICTRUE_FORMATE_JPG = "jpg";

	/**
	 * 给指定的图片添加水印
	 * @author liukh
	 * @date 2016-12-27 下午12:02:41
	 * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
	 * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
	 * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha  透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(String targetImg, String waterImg,
			int x, int y, float alpha) {
		try {
			File file = new File(targetImg);
			Image image = ImageIO.read(file);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);

			Image waterImage = ImageIO.read(new File(waterImg)); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));

			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @author liukh
	 * @date 2016-12-27 下午5:05:32
	 * @param localFile 目标图片文件
	 * @param waterIn 水印图片输入流
	 * @param x 水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
	 * @param y 水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
	 * @param alpha  透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
	 */
	public final static void pressImage(File localFile , InputStream waterIn,
			int x, int y, float alpha) {
		try {
			Image image = ImageIO.read(localFile);
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0, width, height, null);
			Image waterImage = ImageIO.read(waterIn); // 水印文件
			int width_1 = waterImage.getWidth(null);
			int height_1 = waterImage.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
					alpha));
			
			int widthDiff = width - width_1;
			int heightDiff = height - height_1;
			if (x < 0) {
				x = widthDiff / 2;
			} else if (x > widthDiff) {
				x = widthDiff;
			}
			if (y < 0) {
				y = heightDiff / 2;
			} else if (y > heightDiff) {
				y = heightDiff;
			}
			g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
			g.dispose();
			ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, localFile);
			if(waterIn != null){
				waterIn.close();
			}
		} catch (IOException e) {
			
			try {
				if(waterIn != null){
					waterIn.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	  pressImage("D:/test/734044229821337373.png", "D:/test/watermarkPicture.png", -1, -1, 1);
	}

}
