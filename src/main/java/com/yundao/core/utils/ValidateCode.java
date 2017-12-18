package com.yundao.core.utils;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;

/**
 * 验证码
 * 
 * @author Jon Chiang
 * @date 2016年7月19日
 */
public class ValidateCode {
	private static Properties props = new Properties();

	private static Producer kaptchaProducer = null;

	static {
		// Switch off disk based caching.
		ImageIO.setUseCache(false);

		props.put(Constants.KAPTCHA_BORDER, "no");
		props.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "5");
		props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		props.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "1234567890");
		props.put(Constants.KAPTCHA_IMAGE_WIDTH, "120");
		props.put(Constants.KAPTCHA_IMAGE_HEIGHT, "50");
		Config config = new Config(props);
		kaptchaProducer = config.getProducerImpl();
	}

	public static String generatorValidateCode(OutputStream out) throws IOException {
		// create the text for the image
		String capText = kaptchaProducer.createText();
		// create the image with the text
		BufferedImage bi = kaptchaProducer.createImage(capText);
		// write the data out
		ImageIO.write(bi, "jpg", out);
		return capText;
	}
	
	public static void main(String[] args) {
		try {
			generatorValidateCode(new FileOutputStream("d:/a.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
