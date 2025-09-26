package com.banking_app.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.banking_app.dto.AddAccountDTO;
import com.banking_app.dto.AdminDTO;
import com.banking_app.service.IAdminAddAccountCategoryService;
import com.banking_app.service.IAdminRegistrationService;
import com.banking_app.util.LoginUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/onlinebankapplication/admin/")
public class AdminController {
	
	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;
	
	@Autowired(required = true)
	private IAdminAddAccountCategoryService adminAddAccountCategoryServiceImpl;
	
	private Integer adminId;
	
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
	
	@PostMapping(value = "saveAdminRegistrationData",consumes=org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
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
	
	@GetMapping("dashboard")
	public String adminDashboard(Model model,HttpServletRequest request,HttpSession session) {
		log.info("adminDashboard() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		log.info("Curruent Session"+request.getSession().getAttribute("userName"));
		session.setAttribute("userName", request.getSession().getAttribute("userName"));
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("userName", session);
		return "loadDashboard";
	}
	
	@GetMapping({"withdraw","deposite","transfer"})
	public String loadFinancesPage(Model model,HttpServletRequest request,HttpSession session) {
		log.info("loadFinancesPage() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		log.info("Curruent Session"+request.getSession().getAttribute("userName"));
		session.setAttribute("userName", request.getSession().getAttribute("userName"));
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("userName", session);
		model.addAttribute("loginType","Admin");
		if(fullUrl.contains("withdraw")) {
			model.addAttribute("financeType", "Withdraw");
		}else if(fullUrl.contains("deposit")) {
		   	model.addAttribute("financeType", "Deposit");
		}else if(fullUrl.contains("transfer")) {
			model.addAttribute("financeType", "Transfer");
		}
		return "loadFinances";
	}
	
	@GetMapping("addAccount")
	public String loadAddAccountPage(Model model,HttpServletRequest request,HttpSession session) {
		log.info("loadAddAccountPage() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		log.info("Curruent Session"+request.getSession().getAttribute("userName"));
		session.setAttribute("userName", request.getSession().getAttribute("userName"));
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("userName", session);
		model.addAttribute("operation", "insert");
		model.addAttribute("addAccountDTO", new AddAccountDTO());
		return "loadAddAccount";
	}
	
	@PostMapping(value = "saveUpdateAddAccountData",consumes="application/json")
	public ResponseEntity<String> saveUpdateAddAccountData(@RequestBody AddAccountDTO addAccountData,ModelMap modelMap,HttpServletRequest request) throws IOException {
		try {
		String userName= (String)request.getSession().getAttribute("userName");
		log.info("Executing saveAddAccountData() Handler Method........");
		adminId = adminRegistrationServiceImpl.getAdminIdByUserName(userName);
		addAccountData.setAdminId(adminId);
		UUID uuId = UUID.randomUUID();
		AddAccountDTO savedAddAccountDetail = adminAddAccountCategoryServiceImpl.saveUpdateAddAccountData(addAccountData);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		if(addAccountData.getAccountId()!=null && addAccountData.getAccountId()>0) {
			return ResponseEntity.ok("Account Category Data is updated succesfully...");
		}
		return ResponseEntity.ok("Account Category Data is saved succesfully...");
	}
	
	
	@GetMapping("checkAccountNameAvailability")
	public ResponseEntity<String> checkAccountNameAvailability(@RequestParam String accountName) {
		try {
		log.info("checkAccountNameAvailability() handler method called...");
		Boolean isAccountAvailableOrNot= adminAddAccountCategoryServiceImpl.isAccountAvailable(accountName);
		return new ResponseEntity<String>(isAccountAvailableOrNot.toString(),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	   
	@GetMapping("existingAccount")
	public String loadExistingAccount(Model model,HttpServletRequest request) throws UnknownHostException {
		log.info("loadExistingAccount() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		String userName= (String)request.getSession().getAttribute("userName");
		if(adminId==null) {
			adminId = adminRegistrationServiceImpl.getAdminIdByUserName(userName);
		}
  		List<AddAccountDTO> adminCategoryList =adminAddAccountCategoryServiceImpl.getAllAccountCategoryList(adminId); 
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("adminCategoryList",adminCategoryList);
		return "loadExistingAccount";
	}
	
	
	@GetMapping("editAccountCategory/{accountId}")
	public String editAccountCategory(Model model,HttpServletRequest request,@PathVariable("accountId") Integer accountId) throws UnknownHostException {
		log.info("loadeditAccountCategory() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		AddAccountDTO addAccountDTO=adminAddAccountCategoryServiceImpl.getAccountCategoryById(accountId);
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("addAccountDTO",addAccountDTO);
		model.addAttribute("operation","update");
		return "loadAddAccount";
	}
	
	@DeleteMapping("deleteAccountCategory")
	public ResponseEntity<String> deleteAccountCategory(@RequestBody Map<String, Integer> deleteRequestData,Model model,HttpServletRequest request) throws UnknownHostException {
		try { 
		log.info("loaddeleteAccountCategory() handler method called...");
		model.addAttribute("title", "Online Banking Application");
		String fullUrl = request.getRequestURL().toString();
		Integer accountId=deleteRequestData.get("accountId"); 
		if(adminId==null) {
			String userName= (String)request.getSession().getAttribute("userName");
			adminId = adminRegistrationServiceImpl.getAdminIdByUserName(userName);
		}
		adminAddAccountCategoryServiceImpl.deleteAccountCategory(accountId);
  		List<AddAccountDTO> adminCategoryList =adminAddAccountCategoryServiceImpl.getAllAccountCategoryList(adminId); 
		model.addAttribute("baseUrlForAdmin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("adminCategoryList",adminCategoryList);
		}catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("Account Category Deleted Successfully...",HttpStatus.OK);
	}
}
