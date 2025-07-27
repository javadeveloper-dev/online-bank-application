package com.banking_app.controller;

import java.io.IOException;
import java.net.UnknownHostException;
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
import com.banking_app.service.IAdminRegistrationService;
import com.banking_app.util.LoginUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/onlinebankapplication/admin/")
public class AdminController {
	
	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;
	
	@GetMapping("loadAdminRegister")
	public String adminRegister(Model model,HttpServletRequest request) throws UnknownHostException {
		log.info("adminRegister() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("adminDTO", new AdminDTO());
		 return "adminRegister";
	}
	
	@PostMapping(value = "saveAdminRegistration",consumes=org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> saveAdminRegistrationDetail(@ModelAttribute AdminDTO adminRegistrationData,ModelMap modelMap,HttpServletRequest request) throws IOException {
		try {
		log.info("Executing saveAdminRegistrationDetail() Handler Method........");
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
}
