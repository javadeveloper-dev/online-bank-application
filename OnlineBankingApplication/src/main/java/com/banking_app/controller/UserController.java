package com.banking_app.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking_app.dto.AdminDTO;
import com.banking_app.dto.UserDTO;
import com.banking_app.service.IUserRegistrationService;
import com.banking_app.util.LoginUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/onlinebankapplication/user/")
public class UserController {
	
	@Autowired(required = true)
	private IUserRegistrationService userRegistrationServiceImpl;
	
	
	
	@GetMapping("loadUserRegister")
	public String userRegister(Model model,HttpServletRequest request) {
		log.info("userRegister() handler method called...");
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("title", "Online Banking Application");
		return "userRegister";
	}
	
	@PostMapping(value = "saveAdminRegistration",consumes=org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> saveAdminRegistrationDetail(@ModelAttribute UserDTO userRegistrationData,ModelMap modelMap,HttpServletRequest request) throws IOException {
		try {
		log.info("Executing saveAdminRegistrationDetail() Handler Method........");
		UUID uuId = UUID.randomUUID();
		UserDTO saveUserDetail = userRegistrationServiceImpl.saveUserRegistrationDetail(userRegistrationData);
		}catch(Exception e) {
			e.printStackTrace();
		}
//		validate form data
//		modelMap.addAttribute("adminRegisterDTO",saveAdminRegistrationDetail);
		modelMap.addAttribute("adminName", userRegistrationData.getFirstName());
		return ResponseEntity.ok(userRegistrationData.getFirstName());
	}
}
