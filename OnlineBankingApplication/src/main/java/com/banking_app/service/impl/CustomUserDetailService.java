package com.banking_app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IAdminLoginDAO;
import com.banking_app.dao.IUserLoginDAO;
import com.banking_app.entity.Admin;
import com.banking_app.entity.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private IAdminLoginDAO adminLoginDAO;
	
	@Autowired
	private IUserLoginDAO userLoginDAO;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userEntity=userLoginDAO.findByEmail(username);
		if(userEntity.isPresent()) {
			return org.springframework.security.core.userdetails.User.withUsername(userEntity.get().getEmail())
					.password(userEntity.get().getPassword())
					.roles("USER")
					.build();
		}
		
		Optional<Admin> adminEntity=adminLoginDAO.findByEmail(username);
		if(adminEntity.isPresent()) {
			return org.springframework.security.core.userdetails.User.withUsername(adminEntity.get().getEmail())
					.password(adminEntity.get().getPassword())
					.roles("ADMIN")
					.build();
		}
		 throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
