package com.banking_app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IAdminLoginDAO;
import com.banking_app.entity.Admin;
import com.banking_app.service.IAdminLoginService;

import jakarta.transaction.Transactional;
@Service
public class AdminLoginServiceImpl implements IAdminLoginService {
	
	@Autowired
	private IAdminLoginDAO adminLoginDAOImpl;
	
	@Autowired	
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Boolean isPasswordExistsOrNot(String email,String password) {
		Optional<Admin> admin=adminLoginDAOImpl.findByEmail(email);
		if(admin.isPresent()) {
			return bCryptPasswordEncoder.matches(password, admin.get().getPassword());
		}
		return false;
	}

	public Boolean isPasswordExistsOrNotForReset(String email,String password) {
		return adminLoginDAOImpl.existsByEmailAndPassword(email, password);
	}

	@Override
	@Transactional
	public void updateAdminPassword(String email, String password) {
		adminLoginDAOImpl.updateAdminPassword(email, password);
	}
	
}
