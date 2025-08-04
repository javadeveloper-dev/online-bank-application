package com.banking_app.service.impl;

import java.io.IOException;
import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IUserRegisterDAO;
import com.banking_app.dto.UserDTO;
import com.banking_app.entity.User;
import com.banking_app.service.IUserRegistrationService;
import com.banking_app.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRegistrationServiceImpl implements IUserRegistrationService {

	@Autowired(required = true)
	private ModelMapper mapper;
	
	public String email;
	
	@Autowired(required = true)
	private IUserRegisterDAO entityManagerFactory;
	
	public UserDTO saveUserRegistrationDetail(UserDTO userDTO) throws IOException {
		Byte[] byteArrayFrombyteArray = CommonUtil.getByteArrayFrombyteArray(userDTO.getProfilePhoto().getBytes());
		Date dateOfBirth=CommonUtil.convertStringDateIntoSQLDate(userDTO.getDateOfBirth());
		User user=mapper.map(userDTO, User.class);
		user.setProfilePhoto(byteArrayFrombyteArray);
		user.setDateOfBirth(dateOfBirth);
		User savedUserEntity = entityManagerFactory.save(user);
		UserDTO savedUserDTO=mapper.map(savedUserEntity, UserDTO.class);
		log.info("User registration successful for user: {}", savedUserDTO.getFirstName());
		return savedUserDTO; // Return the saved user data
	}

	@Override
	public Boolean isEmailAlreadyPresentOrNot(String email) {
		Boolean isEmailPresent=entityManagerFactory.existsByEmail(email);
		if(isEmailPresent) {
			this.email=email;
		}else {
			this.email="";
		}
		return entityManagerFactory.existsByEmail(email);
	}

	@Override
	public Boolean isAccountNoAlreadyExists(String accountNo) {
		return entityManagerFactory.existsByAccountNo(accountNo);
	}

	@Override
	public Boolean isMobileNoAlreadyExists(String mobileNo) {
		return entityManagerFactory.existsByMobileNo(mobileNo);
	}

}
