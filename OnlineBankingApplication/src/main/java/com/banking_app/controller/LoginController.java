package com.banking_app.controller;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banking_app.dto.LoginDTO;
import com.banking_app.dto.MailSenderDTO;
import com.banking_app.service.IAdminRegistrationService;
import com.banking_app.service.ILoginService;
import com.banking_app.util.CommonUtil;
import com.banking_app.util.EncryptUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/onlinebankapplication-login/")
public class LoginController {
	
	@Autowired(required = true)
	private ILoginService loginServiceImpl;
	
	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value="isEmailPresentForLogin",method=RequestMethod.POST,consumes=org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping("isEmailPresentForLogin")
	public ResponseEntity<String> isEmailPresentForLogin(@RequestBody Map<String, String> requestData) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		String encryptedEmail=requestData.get("email");
		String iv=requestData.get("iv");
		String email=EncryptUtil.decryptAESData(encryptedEmail, iv);
		Boolean isValidEmail=adminRegistrationServiceImpl.isEmailAlreadyPresentOrNot(email);
		ResponseEntity<String> returnStatus;
		if (isValidEmail) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Valid User");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid User...");
		}
		return returnStatus;
	}
	
	@PostMapping("isPasswordExistsOrNot")
	public ResponseEntity<String> isPasswordExistsOrNot(@RequestBody LoginDTO loginDTO){
		Boolean isPasswordPresent=loginServiceImpl.isPasswordExistsOrNot(loginDTO);
		ResponseEntity<String> returnStatus;
		if (isPasswordPresent) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Passsword is Correct");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password is Incorrect");
		}
		return returnStatus;
	}
	
	@GetMapping("generateCaptcha")
	@ResponseBody
	public ResponseEntity<Map<String,String>> generateNewCaptch() throws IOException {
		String captchaString=CommonUtil.generateCaptcha(5);
		String captchaImageInString=CommonUtil.generateCaptchaImageString(captchaString);
		Map<String,String> response=new HashMap<String,String>();
		response.put("captchaImage","data:image/png;base64,"+captchaImageInString);
		response.put("captchaValue",captchaString);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("loadForgotPassword")
	public String loadForgotPassword(Model model,HttpServletRequest request){
		String fullUrl = request.getRequestURL().toString();
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		String baseUrlForLogin=fullUrl.substring(0,fullUrl.lastIndexOf("-"))+"/";
		model.addAttribute("baseUrl",baseUrl);
		model.addAttribute("baseUrlForLogin",baseUrlForLogin);
		return "forgotPassword";
	}
	
	@GetMapping("loadOTPPage")
	public String loadOTPPage(Model model,HttpServletRequest request) throws MessagingException{
		String fullUrl = request.getRequestURL().toString();
		String baseUrl=fullUrl.substring(0,fullUrl.lastIndexOf("/")+1);
		String baseUrlForLogin=fullUrl.substring(0,fullUrl.lastIndexOf("-"))+"/";
		model.addAttribute("baseUrl",baseUrl);
		model.addAttribute("baseUrlForLogin",baseUrlForLogin);
		MailSenderDTO mailSenderDTO=new MailSenderDTO();
		mailSenderDTO.setFrom("tayadepankaj1999@gmail.com");
		mailSenderDTO.setTo("tayadepankaj1999@gmail.com");
		mailSenderDTO.setMessage("Hello");
		mailSenderDTO.setBody("HELLO");
		mailSenderDTO.setSubject("Hello");
//		loginServiceImpl.sendMailForOTP(mailSenderDTO);
		return "loadOTP";
//		return "redirect:loadOTP";

	}
}
