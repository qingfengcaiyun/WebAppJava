package org.glibs.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

public class VerifyCode {

	public VerifyCode() {
	}

	public VerifyCode(int width, int height) {
		this.buildVerifyImage(width, height);
	}

	private Image image;
	private String code;

	public Image getImage() {
		return image;
	}

	public String getCode() {
		return code;
	}

	public void buildVerifyImage(int width, int height) {
		char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

		// 取随机产生的认证码
		this.code = "";
		// 4代表4位验证码,如果要生成更多位的认证码,则加大数值
		for (int i = 0; i < 4; ++i) {
			this.code += mapTable[(int) (mapTable.length * Math.random())];
		}

		if (width <= 0) {
			width = 60;
		}
		if (height <= 0) {
			height = 20;
		}

		this.image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = this.image.getGraphics();
		// 设定背景色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);

		// 将认证码显示到图像中,如果要生成更多位的认证码,增加drawString语句
		g.setColor(Color.black);
		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));

		String str = this.code.substring(0, 1);
		g.drawString(str, 8, 17);
		str = this.code.substring(1, 2);
		g.drawString(str, 20, 15);
		str = this.code.substring(2, 3);
		g.drawString(str, 35, 18);
		str = this.code.substring(3, 4);
		g.drawString(str, 45, 15);

		// 随机产生10个干扰点
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			int x = rand.nextInt(width);
			int y = rand.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		// 释放图形上下文
		g.dispose();
	}
}
