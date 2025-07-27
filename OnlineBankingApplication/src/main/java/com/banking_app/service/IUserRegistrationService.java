package com.banking_app.service;

import java.io.IOException;

import com.banking_app.dto.UserDTO;

public interface IUserRegistrationService {

	UserDTO saveUserRegistrationDetail(UserDTO userRegistrationData) throws IOException;

}
