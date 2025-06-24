 package com.banking_app.dto;

import org.springframework.web.multipart.MultipartFile;

import com.banking_app.validate.AddressValidator;
import com.banking_app.validate.ConfirmPasswordValidator;
import com.banking_app.validate.ImageValidator;
import com.banking_app.validate.NameValidator;
import com.banking_app.validate.PasswordValidator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@ConfirmPasswordValidator
public class AdminDTO {

	private Integer id;
	
	@NameValidator
	private String firstName;

	@NameValidator	
	private String lastName;
		
	
	@AddressValidator
	private String address;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Please provide a valid email address")
	private String email;

	@PasswordValidator
	private String password;
	
//	@NotBlank(message="Please Enter Confirm Password.")
	@ConfirmPasswordValidator
	private String confirmPassword;

	@NotBlank(message = "Account Number cannot be empty")
	@Pattern(regexp = "^\\d{12}$", message = "Account Number must be 12 digits")
	private String accountNumber;

	@NotBlank(message="Please Select the Role.....")
	private String selectedRole;
	
	@NotBlank(message="Please Select the Date.....")
	private String dateOfBirth;

	@NotBlank(message = "Mobile Number cannot be empty")
	@Pattern(regexp = "^\\d{10}$", message = "Mobile Number must be 10 digits")
	private String mobileNo;
	
	@NotBlank(message="Please select the gender....")
	private String gender;

	@NotBlank(message="Enter the nationality....")
	@Size(max=62,min=2,message="Min. 2 character is required...")
	private String nationality;

	@ImageValidator
	private MultipartFile profilePhoto; 
}
