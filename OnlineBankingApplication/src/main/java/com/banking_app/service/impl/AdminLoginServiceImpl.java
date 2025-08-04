package com.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IAdminLoginDAO;
import com.banking_app.service.IAdminLoginService;

import jakarta.transaction.Transactional;
@Service
public class AdminLoginServiceImpl implements IAdminLoginService {
	
	@Autowired
	private IAdminLoginDAO adminLoginDAOImpl;
	
	@Override
	public Boolean isPasswordExistsOrNot(String email,String password) {
		return adminLoginDAOImpl.existsByEmailAndPassword(email, password);
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
