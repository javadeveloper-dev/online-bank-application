package com.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	@GetMapping("welcome")
	public String welComePage(Model model,HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		String baseUrlForLogin = fullUrl.substring(0, fullUrl.lastIndexOf("/")) + "-login/";
		model.addAttribute("adminDTO", new AdminDTO());
		model.addAttribute("baseUrl",baseUrl);
		model.addAttribute("baseUrlForLogin",baseUrlForLogin);
		model.addAttribute("messege", "Hello World!..");
		model.addAttribute("title", "Online Banking Application");
		log.info("welComePage() handler method......");
		return "index";
	}
}
