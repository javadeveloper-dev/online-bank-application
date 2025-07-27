package com.banking_app.service.impl;

import java.io.IOException;
import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.banking_app.dao.IUserRegisterDAO;
import com.banking_app.dto.AdminDTO;
import com.banking_app.dto.UserDTO;
import com.banking_app.entity.Admin;
import com.banking_app.entity.User;
import com.banking_app.service.IUserRegistrationService;
import com.banking_app.util.CommonUtil;


public class UserRegistrationServiceImpl implements IUserRegistrationService {

	@Autowired(required = true)
	private ModelMapper mapper;
	
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
		return savedUserDTO; // Return the saved user data
	}

}
