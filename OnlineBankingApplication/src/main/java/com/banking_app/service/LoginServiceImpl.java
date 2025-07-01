package com.banking_app.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.banking_app.dao.ILoginDAO;
import com.banking_app.dto.LoginDTO;
import com.banking_app.dto.MailSenderDTO;
import com.banking_app.util.CommonUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private ILoginDAO loginDAOImpl;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	private String otp;
	
	@Override
	public Boolean isPasswordExistsOrNot(LoginDTO loginDTO) {
		String email=loginDTO.getEmail();
		String password=loginDTO.getPassword();
		return loginDAOImpl.existsByEmailAndPassword(email,password);
	}


	@Override
	public void sendMailForOTP(MailSenderDTO sendMailDTO) throws MessagingException {
		 MimeMessage mimeMessage = javaMailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
      
         helper.setFrom(sendMailDTO.getFrom());
         helper.setTo(sendMailDTO.getTo());
         helper.setSubject("Email Verification OTP");
         String otp=CommonUtil.generateOTP();
         this.otp=otp;
         String htmlSubject = 
        		    "<!DOCTYPE html>" +
        		    "<html>" +
        		    "<head>" +
        		        "<style>" +
        		            "body { font-family: Arial, sans-serif; }" +
        		        "</style>" +
        		    "</head>" +
        		    "<body>" +
        		        "<p><strong style='font-size: 20px;'>Dear XYZ,</strong></p>" +

        		        "<p><strong style='font-size: 18px;'>"
        		            + "To complete the email verification process and ensure the security of your account, we kindly request your assistance."
        		        + "</strong></p>" +

        		        "<p><strong style='font-size: 22px; color: #2c3e50;'>Your One-Time Password (OTP) is:</strong></p>" +

        		        "<p style='font-size: 28px; font-weight: bold; color: #e74c3c;'>"
        		            + otp +
        		        "</p>" +

        		        "<p><strong style='font-size: 18px;'>Please enter the OTP in the designated verification field.</strong></p>" +

        		        "<p><strong style='font-size: 18px;'>Thank you,<br/>XYZ Bank</strong></p>" +
        		    "</body>" +
        		    "</html>";
         helper.setText(htmlSubject,true);
           // Set true for HTML content
//         FileSystemResource fileSystemResource=new FileSystemResource(new File(mailSenderDTO.getFilePath()));
//         helper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
         // Send the email
         javaMailSender.send(mimeMessage);
	}


	@Override
	public Boolean isValidOTP(String otp) {
		return this.otp.equals(otp);
	}
	
}
