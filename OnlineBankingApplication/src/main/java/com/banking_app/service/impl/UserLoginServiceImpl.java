package com.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IUserLoginDAO;
import com.banking_app.service.IUserLoginService;

import jakarta.transaction.Transactional;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	
	@Autowired
	private IUserLoginDAO userLoginDAOImpl;
	
	@Autowired	
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Boolean isPasswordExistsOrNot(String email,String password) {
		return userLoginDAOImpl.existsByEmailAndPassword(email, passwordEncoder.encode(password));
	}

	@Override
	public Boolean isPasswordExistsOrNotForReset(String email, String password) {
		return userLoginDAOImpl.existsByEmailAndPassword(email,passwordEncoder.encode(password));
	}

	@Override
	@Transactional
	public void updateUserPassword(String email, String password) {
		userLoginDAOImpl.updateUserPassword(email,passwordEncoder.encode(password));
	}

}
