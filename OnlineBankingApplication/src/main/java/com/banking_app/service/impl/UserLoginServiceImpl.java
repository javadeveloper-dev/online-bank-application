package com.banking_app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IUserLoginDAO;
import com.banking_app.entity.Admin;
import com.banking_app.entity.User;
import com.banking_app.service.IUserLoginService;

import jakarta.transaction.Transactional;

@Service
public class UserLoginServiceImpl implements IUserLoginService {
	
	@Autowired
	private IUserLoginDAO userLoginDAOImpl;
	
	@Autowired	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Boolean isPasswordExistsOrNot(String email,String password) {
		Optional<User> user=userLoginDAOImpl.findByEmail(email);
		if(user.isPresent()) {
			return bCryptPasswordEncoder.matches(password, user.get().getPassword());
		}
		return false;
	}

	@Override
	public Boolean isPasswordExistsOrNotForReset(String email, String password) {
		return userLoginDAOImpl.existsByEmailAndPassword(email,password);
	}

	@Override
	@Transactional
	public void updateUserPassword(String email, String password) {
		userLoginDAOImpl.updateUserPassword(email,password);
	}

}
