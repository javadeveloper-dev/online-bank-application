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
import org.springframework.http.MediaType;
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

import com.banking_app.dto.MailSenderDTO;
import com.banking_app.service.IAdminRegistrationService;
import com.banking_app.service.ILoginService;
import com.banking_app.util.CommonUtil;
import com.banking_app.util.EncryptUtil;
import com.banking_app.util.LoginUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/onlinebankapplication/login")
public class LoginController {

	@Autowired(required = true)
	private ILoginService loginServiceImpl;

	@Autowired(required = true)
	private IAdminRegistrationService adminRegistrationServiceImpl;

	private String email;

	private String decryptAESData;

	@CrossOrigin(origins = "*")
	@PostMapping(value = "isEmailPresentForLogin" , consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> isEmailPresentForLogin(@RequestBody Map<String, String> requestData)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String encryptedEmail = requestData.get("email");
		String iv = requestData.get("iv");
		String email = EncryptUtil.decryptAESData(encryptedEmail, iv);
		Boolean isValidEmail = adminRegistrationServiceImpl.isEmailAlreadyPresentOrNot(email);
		ResponseEntity<String> returnStatus;
		if (isValidEmail) {
			returnStatus = ResponseEntity.status(HttpStatus.OK).body("Valid User");
		} else {
			returnStatus = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid User...");
		}
		return returnStatus;
	}

	@PostMapping("isPasswordExistsOrNot")
	public ResponseEntity<String> isPasswordExistsOrNot(@RequestBody Map<String,String> encryptedData) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String encryptedPassword = encryptedData.get("password");
		String iv = encryptedData.get("iv");
		String password = decryptAESData = EncryptUtil.decryptAESData(encryptedPassword, iv);
		Boolean isPasswordPresent = loginServiceImpl.isPasswordExistsOrNot(password);
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
	public ResponseEntity<Map<String, String>> generateNewCaptch() throws IOException {
		String captchaString = CommonUtil.generateCaptcha(5);
		String captchaImageInString = CommonUtil.generateCaptchaImageString(captchaString);
		Map<String, String> response = new HashMap<String, String>();
		response.put("captchaImage", "data:image/png;base64," + captchaImageInString);
		response.put("captchaValue", captchaString);
		return ResponseEntity.ok(response);
	}

	@GetMapping("loadForgotPassword")
	public String loadForgotPassword(Model model, HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		return "loadForgotPasswordForOTP";
	}

//	@PostMapping("loadOTPPage")
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "loadOTPPage")
	public String loadOTPPage(Model model, HttpServletRequest request) throws MessagingException {
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		return "loadOTP";

	}

	@PostMapping(value = "validateOTP", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> validateOTP(@RequestBody Map<String, String> otp) {
		String otpValue = otp.get("otp");
		Boolean validOTP = loginServiceImpl.isValidOTP(otpValue);
		return validOTP == true ? ResponseEntity.ok("OTP Validated Successfully")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid OTP");
	}

	@PostMapping(value = "sendOTP", consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendOTP(@RequestBody Map<String, String> email) throws MessagingException {
		String emailData = email.get("email");
		MailSenderDTO mailSenderDTO = new MailSenderDTO();
		mailSenderDTO.setFrom("tayadepankaj1999@gmail.com");
		mailSenderDTO.setTo(emailData);
		mailSenderDTO.setMessage("Hello");
		mailSenderDTO.setBody("HELLO");
		mailSenderDTO.setSubject("Hello");
		try {
			loginServiceImpl.sendMailForOTP(mailSenderDTO);
			this.email = emailData;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error in sending OTP");
		}
		return ResponseEntity.ok("OTP sent successfully to " + emailData);
	}

	@GetMapping("forgotPassword")
	public String forgotPassword(Model model, HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		return "loadForgotPassword";
	}

	@PostMapping(value = "isPasswordExistsOrNotForResetPassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> isPasswordExistsOrNotForReset(@RequestBody Map<String, String> requestData)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, javax.crypto.BadPaddingException {
		String encryptedPassword = requestData.get("password");
		String iv = requestData.get("iv");
		String password = decryptAESData = EncryptUtil.decryptAESData(encryptedPassword, iv);
		Boolean isPasswordExists = loginServiceImpl.isPasswordExistsOrNotForReset(this.email, password);
		ResponseEntity<String> responseEntity;
		if (isPasswordExists) {
			responseEntity = new ResponseEntity<String>("Password Already Exists", HttpStatus.CONFLICT);
		} else {
			responseEntity = new ResponseEntity<String>("Password is not exists", HttpStatus.OK);
		}
		return responseEntity;

	}

	@PostMapping(value = "savePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> savePassword(@RequestBody Map<String, String> requestData)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, javax.crypto.BadPaddingException {
		String encryptedPassword = requestData.get("password");
		String iv = requestData.get("iv");
		String password = decryptAESData = EncryptUtil.decryptAESData(encryptedPassword, iv);
		ResponseEntity<String> responseEntity;
		try {
			loginServiceImpl.updateAdminPassword(this.email, password);
			responseEntity = new ResponseEntity<String>("Password Saved Successfully", HttpStatus.CONFLICT);
			this.email = "";
		} catch (Exception e) {

			responseEntity = new ResponseEntity<String>("Password is not exists", HttpStatus.OK);
		}
		return responseEntity;
	}
	
	@GetMapping({"adminLogin","userLogin"})
	public String login(Model model, HttpServletRequest request) {
		String fullUrl = request.getRequestURL().toString();
		model.addAttribute("baseUrl", LoginUtil.getBaseUrl(fullUrl));
		model.addAttribute("baseUrlForLogin", LoginUtil.getBaseUrlFromLastSlash(fullUrl));
		if(fullUrl.contains("adminLogin")) {
			model.addAttribute("loginType", "Admin Login");
		}else {
			model.addAttribute("loginType", "User Login");
		}
		return "login";
	}
}
