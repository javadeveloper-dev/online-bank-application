package com.banking_app.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

import com.banking_app.constants.LoginConstants;

public class CommonUtil {
		
	public static Byte[] getByteArrayFrombyteArray(byte[] byteArray) {
		return java.util.stream.IntStream.range(0,byteArray.length).mapToObj(i->byteArray[i]).toArray(Byte[]::new);
	}
	
	public static Date convertStringDateIntoSQLDate(String date) {
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateConvert=LocalDate.parse(date,formatter);
		return java.sql.Date.valueOf(dateConvert);
		
	}
	
	public static String generateCaptcha(int captchaLength) {
		
		StringBuffer captchaBuffer=new StringBuffer();
		Random random = new Random();
		while(captchaBuffer.length()<captchaLength) {
			int index=(int)random.nextInt(62);
			captchaBuffer.append(LoginConstants.BASE_CAPTCHA_STRING.substring(index, index+1));
		}
		return captchaBuffer.toString();
	}

	public static String generateCaptchaImageString(String captchaString) throws IOException {
		int width=160, height=35;
		BufferedImage bufferedImage=new BufferedImage(width,height,BufferedImage.OPAQUE);
		Graphics graphics=bufferedImage.createGraphics();
		graphics.setFont(new Font("Arial",Font.BOLD,20));
		graphics.setColor(new Color(169,169,169));
		graphics.fillRect(0,0,width,height);
		graphics.setColor(new Color(255,255,255,255));
		graphics.drawString(captchaString, 20,25);
		
		ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
		ImageIO.write(bufferedImage,"png",outputStream);
		byte[] imageBytes=outputStream.toByteArray();
		return Base64.getEncoder().encodeToString(imageBytes);
	}
	
	public static String generateOTP() {
		StringBuffer otpBuffer=new StringBuffer(4);
		Random random=new Random();
		int i=1;
		while(i<=4) {
			int index=(int) random.nextInt(9);
			otpBuffer.append(LoginConstants.BASE_NUMBER_FOR_OTP.substring(index,index+1));
			i++;
		}
		return otpBuffer.toString();
	}
}
