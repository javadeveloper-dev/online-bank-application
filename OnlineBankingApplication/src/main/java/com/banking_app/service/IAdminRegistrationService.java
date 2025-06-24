package com.banking_app.service;

import java.io.IOException;

import com.banking_app.dto.AdminDTO;
import com.banking_app.dto.LoginDTO;

public interface IAdminRegistrationService {

		public AdminDTO saveAdminRegistrationDetail(AdminDTO adminDTO) throws IOException;

		public Boolean isEmailAlreadyPresentOrNot(String email);

		public Boolean isAccountNoAlreadyExists(String accountNo);

		public Boolean isMobileNoAlreadyExists(String mobileNo);

}
