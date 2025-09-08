package com.banking_app.controller;

import java.security.Principal;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banking_app.util.LoginUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	 @RequestMapping("error")
	    public String handleError(Model model, HttpServletRequest request , Principal principle) {
	        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	        String fullUrl = request.getRequestURL().toString();
	        String originalUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
			String baseUrl = LoginUtil.getBaseUrl(fullUrl);
			if(originalUri.contains("admin")) {
				baseUrl+="admin/";
			}else {
				baseUrl+="user/";
			}
			String defaultPage="";
			if(principle!=null) {
				defaultPage="dashboard";
			}else {
				defaultPage="welcome";
			}	
			model.addAttribute("baseUrl",baseUrl+defaultPage);
	        if (status != null) {
	            int statusCode = Integer.parseInt(status.toString());
	            if (statusCode == 404) {
	                return "error/404";  // must exist in templates/error/404.html
	            }
	            if (statusCode == 500) {
	                return "error/500";  // templates/error/500.html
	            }
	        }
	        return "error/error"; // fallback generic error page
	    }
}
