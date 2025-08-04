package com.banking_app.service;

public interface IUserLoginService {
	
	Boolean isPasswordExistsOrNot(String email,String password);

	Boolean isPasswordExistsOrNotForReset(String email, String password);

	void updateUserPassword(String email, String password);
}
