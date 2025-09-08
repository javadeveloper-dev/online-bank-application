package com.banking_app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking_app.dto.AdminDTO;
import com.banking_app.util.LoginUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/onlinebankapplication/")
public class OnlineBankingApplicationController {
	
	public static String userNameForSession;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("welcome")
	public String welComePage(Model model,HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("adminDTO", new AdminDTO());
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl)+ "login/");
		model.addAttribute("messege", "Hello World!..");
		model.addAttribute("title", "Online Banking Application");
		log.info("welComePage() handler method......");
		return "index";
	}
	
	@PostMapping("setSession")
	public ResponseEntity<String> setSession(@RequestBody Map<String,String> requestData,Model model,HttpServletRequest request)  {
		ResponseEntity<String> responseEntity;
		try {
		String userName = requestData.get("userName");
		String password = requestData.get("password");
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
		
		Authentication authentication=authenticationManager.authenticate(authenticationToken);
		
		SecurityContext securityContext=SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		
		HttpSession session=request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT",securityContext);
		
		session.setAttribute("userName", userName);
		responseEntity=new ResponseEntity<String>("Session Set Successfully..",HttpStatus.OK);
	}catch(Exception e) {
		responseEntity=new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
	}
		return responseEntity;
	}
	
	@GetMapping("logout")
	public String logout(Model model,HttpServletRequest request,HttpSession session) {
		log.info("logout() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		log.info("Curruent Session"+request.getSession().getAttribute("userName"));
		session.setAttribute("userName", request.getSession().getAttribute("userName"));
		session.removeAttribute("userName");
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		return "index";
	}
	
	
	@GetMapping("error")
	public String errorPage(Model model,HttpServletRequest request) {
		log.info("errorPage() handler method called...");
		Object status = request.getAttribute("javax.servlet.error.status_code");
//		String fullUrl = request.getRequestURL().toString();
//		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
//		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl)+ "login/");
//		model.addAttribute("title", "Online Banking Application");
		return "error22";
	}
}
