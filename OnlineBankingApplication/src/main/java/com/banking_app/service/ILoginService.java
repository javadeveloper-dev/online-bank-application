package com.banking_app.service;

import com.banking_app.dto.MailSenderDTO;

import jakarta.mail.MessagingException;

public interface ILoginService {

	Boolean isPasswordExistsOrNot(String password);
	
	public void sendMailForOTP(MailSenderDTO sendMailDTO) throws MessagingException;

	Boolean isValidOTP(String otp);

	Boolean isPasswordExistsOrNotForReset(String email, String password);

	void updateAdminPassword(String email, String password);

}
