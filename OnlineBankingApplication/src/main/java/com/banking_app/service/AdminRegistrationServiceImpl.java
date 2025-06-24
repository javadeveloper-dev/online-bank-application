package com.banking_app.service;

import java.io.IOException;
import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking_app.dao.IAdminRegisterDAO;
import com.banking_app.dto.AdminDTO;
import com.banking_app.dto.LoginDTO;
import com.banking_app.entity.Admin;
import com.banking_app.exception.EmailAlreadyExistsException;
import com.banking_app.util.CommonUtil;

@Service
public class AdminRegistrationServiceImpl implements IAdminRegistrationService {

	@Autowired(required=true)
	private IAdminRegisterDAO entityManagerFactory;
	
	
	@Autowired
	private ModelMapper mapper;
	
	public Boolean isEmailAlreadyPresentOrNot(String email) {
		return entityManagerFactory.existsByEmail(email);
	}

	@Override
	public AdminDTO saveAdminRegistrationDetail(AdminDTO adminDTO) throws IOException {
		Byte[] byteArrayFrombyteArray = CommonUtil.getByteArrayFrombyteArray(adminDTO.getProfilePhoto().getBytes());
		Date dateOfBirth=CommonUtil.convertStringDateIntoSQLDate(adminDTO.getDateOfBirth());
		Admin admin=mapper.map(adminDTO, Admin.class);
		admin.setProfilePhoto(byteArrayFrombyteArray);
		admin.setDateOfBirth(dateOfBirth);
		Admin savedAdminEntity = entityManagerFactory.save(admin);
		AdminDTO savedAdminDTO=mapper.map(savedAdminEntity, AdminDTO.class);
		return savedAdminDTO;
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
