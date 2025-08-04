package com.banking_app.service;

import com.banking_app.dto.MailSenderDTO;

import jakarta.mail.MessagingException;

public interface ILoginService {
	
	public void sendMailForOTP(MailSenderDTO sendMailDTO) throws MessagingException;

	Boolean isValidOTP(String otp);

	Boolean isPasswordExistsOrNotForReset(String email, String password);

}
