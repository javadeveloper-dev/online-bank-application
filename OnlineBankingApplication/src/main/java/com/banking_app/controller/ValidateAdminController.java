package com.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banking_app.service.IAdminRegistrationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/onlinebankapplication/validateAdmin/")
public class ValidateAdminController {
	
	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;
	
	@GetMapping("isEmailAlreadyExists")
	public ResponseEntity<String> emailAlreadyExists(@RequestParam String email) {
		Boolean emailPresent=adminRegistrationServiceImpl.isEmailAlreadyPresentOrNot(email);
		ResponseEntity<String> returnStatus;
		if (emailPresent) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Email Already Exists");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email does not exist.");
		}
		return returnStatus;
	}
	
	@GetMapping("isAccountNoAlreadyExists")
	public ResponseEntity<String> isAccountNoAlreadyExists(@RequestParam String accountNo) {
		log.info("isAccountNoAlreadyExists() handler method called with accountNo: {}", accountNo);
		Boolean emailPresent=adminRegistrationServiceImpl.isAccountNoAlreadyExists(accountNo);
		ResponseEntity<String> returnStatus;
		if (emailPresent) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Account No Exists");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account No exist.");
		}
		return returnStatus;
	}
	
	@GetMapping("isMobileNoAlreadyExists")
	public ResponseEntity<String> isMobileNoAlreadyExists(@RequestParam String mobileNo) {
		log.info("isMobileNoAlreadyExists() handler method called with mobileNo: {}", mobileNo);
		Boolean moblieNoPresent=adminRegistrationServiceImpl.isMobileNoAlreadyExists(mobileNo);
		ResponseEntity<String> returnStatus;
		if (moblieNoPresent) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Monile No Exists");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mobile No Not exist.");
		}
		return returnStatus;
	}
}
