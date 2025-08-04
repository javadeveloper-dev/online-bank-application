package com.banking_app.service;

public interface IAdminLoginService {
	
	Boolean isPasswordExistsOrNot(String email,String password);

	Boolean isPasswordExistsOrNotForReset(String email, String password);

	void updateAdminPassword(String email, String password);
	
}
