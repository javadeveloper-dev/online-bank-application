package com.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IUserLoginDAO;
import com.banking_app.dto.MailSenderDTO;
import com.banking_app.service.IUserLoginService;

import jakarta.mail.MessagingException;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	
	@Autowired
	private IUserLoginDAO userLoginDAOImpl;
	
	@Override
	public Boolean isPasswordExistsOrNot(String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMailForOTP(MailSenderDTO sendMailDTO) throws MessagingException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isValidOTP(String otp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isPasswordExistsOrNotForReset(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserPassword(String email, String password) {
		// TODO Auto-generated method stub
		
	}

	

}
