package com.banking_app.service;

import com.banking_app.dto.LoginDTO;
import com.banking_app.dto.MailSenderDTO;

import jakarta.mail.MessagingException;

public interface ILoginService {

	Boolean isPasswordExistsOrNot(LoginDTO loginDTO);
	
	public void sendMailForOTP(MailSenderDTO sendMailDTO) throws MessagingException;
}
