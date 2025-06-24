package com.banking_app.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.banking_app.dto.AdminDTO;
import com.banking_app.service.IAdminRegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/onlinebankapplication/")
public class OnlineBankingApplicationController {

	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;

	private static final Logger logger = LoggerFactory.getLogger(OnlineBankingApplicationController.class);
	
	@GetMapping("welcome")
	public String welComePage(Model model,HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		model.addAttribute("adminDTO", new AdminDTO());
		model.addAttribute("baseUrl",baseUrl);
		model.addAttribute("messege", "Hello World!..");
		model.addAttribute("title", "Online Banking Application");
		log.info("welComePage() handler method......");
		return "index";
	}

	@GetMapping("adminRegister")
	public String adminRegister(Model model,HttpServletRequest request) throws UnknownHostException {
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		model.addAttribute("adminDTO", new AdminDTO());
		model.addAttribute("baseUrl",baseUrl);
		 return "adminRegister";
	}

	@GetMapping("userRegister")
	public String userRegister(Model model) {
		model.addAttribute("title", "Online Banking Application");
		return "userRegister";
	}

	@GetMapping("login")
	public String adminLogin(Model model,HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString().replace("onlinebankapplication", "onlinebankapplication-login");
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		model.addAttribute("baseUrl",baseUrl);
		return "login";
	}

	@PostMapping(value = "saveAdminRegistration",consumes=org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
//	@RequestMapping(value = "saveAdminRegistration", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, method = RequestMethod.POST)
	public ResponseEntity<String> saveAdminRegistrationDetail(@ModelAttribute AdminDTO adminRegistrationData,ModelMap modelMap,HttpServletRequest request) throws IOException {
		try {
		logger.info("Executing saveAdminRegistrationDetail() Handler Method........");
		UUID uuId = UUID.randomUUID();
		AdminDTO saveAdminRegistrationDetail = adminRegistrationServiceImpl.saveAdminRegistrationDetail(adminRegistrationData);
		}catch(Exception e) {
			e.printStackTrace();
		}
//		validate form data
//		modelMap.addAttribute("adminRegisterDTO",saveAdminRegistrationDetail);
		modelMap.addAttribute("adminName", adminRegistrationData.getFirstName());
		return ResponseEntity.ok(adminRegistrationData.getFirstName());
	}

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
