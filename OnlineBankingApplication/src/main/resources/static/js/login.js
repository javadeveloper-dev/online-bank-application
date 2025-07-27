
import {encryptInput} from './EncryptUtil.js';

var modalPopup = document.getElementById("exampleModalCenter");
let loaderOverlay = document.getElementById("loaderOverlay");
let isEmailValidateElement=document.getElementById("isEmailValidate");
let isPasswordValidateElement=document.getElementById("isPasswordValidate");
let isCaptchaValidateElement=document.getElementById("isCaptchValidate");
var baseUrl = document.getElementById("baseUrl").value+"login/";

var baseUrlForLogin;
if(document.getElementById("baseUrlForLogin")!==null){
    baseUrlForLogin = document.getElementById("baseUrlForLogin").value;
}
async function validateEmailForLogin(emailElement,event) {
	//event.preventDefault();
    // Show loader and blur background
    loaderOverlay.classList.remove("d-none");
	const regexForEmail = /^[a-zA-Z0-9.]{1,64}@[a-zA-Z]{1,253}\.[a-zA-Z]{2,}$/;
	let email = emailElement.value.trim();
	let validateEmail = document.getElementById("validateEmail");
	var passwordElement = document.getElementById("passwordId");
	var captchaElement = document.getElementById("captchaId");
	// Js Side Validation
	if (regexForEmail.test(email) == false) {
		emailElement.value = "";
		validateEmail.innerText = "Please Enter Valid Email...";
		validateEmail.classList = "text-danger";
		emailElement.style.border = "1px solid red";
		emailElement.focus();
		isEmailValidateElement.value="false";
		loaderOverlay.classList.add("d-none");
    	if (event && typeof event.preventDefault === "function") {
            event.preventDefault();
        }
		return false;
	} else {
		validateEmail.innerText = "";
		validateEmail.classList.remove("text-danger");
		emailElement.style.border = "";
		validateEmail.style="";
		isEmailValidateElement.value="true";
	}
	const encryptedData=await encryptInput(email);
	console.log(encryptedData);
	const options = {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
		body: JSON.stringify({
			email: encryptedData.cipherText,
			iv: encryptedData.ivBase64
		}) 
	};
	
	var mainUrl = `${baseUrlForLogin}isEmailPresentForLogin`;
	//var mainUrl = `${baseUrl}isEmailPresentForLogin?email=${encodeURIComponent(email)}`;
	const response = await fetch(mainUrl, options);
	if (response.status !== 200) {
		emailElement.value = "";
		validateEmail.innerText = "Entered Email Does Not Exists";
		validateEmail.classList = "text-danger";
		emailElement.style.border = "1px solid red";
		emailElement.focus();
		loaderOverlay.classList.add("d-none");
		isEmailValidateElement.value="false";
		if (event && typeof event.preventDefault === "function") {
            event.preventDefault();
        }
		return false;
	} else {
		validateEmail.innerText = "";
		validateEmail.classList.remove("text-danger");
		emailElement.style.border = "";
		validateEmail.style="";
		loaderOverlay.classList.add("d-none");
		isEmailValidateElement.value="true";
		return true;
	}
}


async function validatePasswordForLogin(passwordElement) {	
	if(isPasswordValidateElement.value==="true"){
		return;
	}
	const regexForPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()<>:]).{6,12}$/
	let password = passwordElement.value.trim();
	let validatePassword = document.getElementById("validatePassword");
	var emailElement = document.getElementById("emailId");
	var email = emailElement.value.trim();
	
	if (email === '') {
		passwordElement.value = "";
		validateEmail.innerText = "Please Enter Email..";
		validateEmail.classList = "text-danger";
		validateEmail.style.border = "1px solid red";
		emailElement.focus();
		loaderOverlay.classList.add("d-none");
		isPasswordValidateElement.value="false";
		return false;
	}
	
	if (password==='') {
		passwordElement.value = "";
		validatePassword.innerText = "Please Enter Password...";
		validatePassword.classList = "text-danger";
		validatePassword.style.border = "1px solid red";
		loaderOverlay.classList.add("d-none");
		isPasswordValidateElement.value="false";
		passwordElement.focus();
		return false;
	}else if (password.length < 6) {
		passwordElement.value = "";
		validatePassword.innerText = "Minimum Password Length is 6";
		validatePassword.classList = "text-danger";
		validatePassword.style.border = "1px solid red";
		loaderOverlay.classList.add("d-none");
		isPasswordValidateElement.value="false";
		passwordElement.focus();
		return false;
	} else if (password.length > 12) {
		passwordElement.value = "";
		validatePassword.innerText = "Maximum Password length is 12";
		validatePassword.classList = "text-danger";
		passwordElement.style.border = "1px solid red";
		passwordElement.focus();
		loaderOverlay.classList.add("d-none");
		isPasswordValidateElement.value="false";
		return false;
	} else {
		validatePassword.innerText = "";
		validatePassword.classList.remove("text-danger");
		passwordElement.style.border = "";
		validatePassword.style="";
		isPasswordValidateElement.value="false";
	}

	if (regexForPassword.test(password) == false) {
		passwordElement.value = "";
		validatePassword.innerText = "Please Enter Valid Password...";
		validatePassword.classList = "text-danger";
		passwordElement.style.border = "1px solid red";
		loaderOverlay.classList.add("d-none");
		passwordElement.focus();
		isPasswordValidateElement.value="false";
		return false;
	} else {
		validatePassword.innerText = "";
		validatePassword.classList.remove("text-danger");
		passwordElement.style.border = "";
		isPasswordValidateElement.value="true";
	}

	//Server side validation
	// Java Side Validation.
	
	var mainUrl = `${baseUrlForLogin}isPasswordExistsOrNot`;
	const encryptedData=await encryptInput(password);
		console.log(encryptedData);
		const options = {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'Accept': 'application/json'
			},
			body: JSON.stringify({
				password: encryptedData.cipherText,
				iv: encryptedData.ivBase64
			}) 
		};
	

	const response = await fetch(mainUrl, options);
	if (response.status !== 200) {
		passwordElement.value = "";
		validatePassword.innerText = "Entered Password Does Not Exists";
		validatePassword.classList = "text-danger";
		passwordElement.style.border = "1px solid red";
		passwordElement.focus();
		loaderOverlay.classList.add("d-none");
		isPasswordValidateElement.value="false";
		return false;
	} else {
		validatePassword.innerText = "";
		validatePassword.classList.remove("text-danger");
		passwordElement.style.border = "none";
		loaderOverlay.classList.add("d-none");
		validatePassword.style="";
		isPasswordValidateElement.value="true";
		return true;
	}
	
}

async function generateCaptcha(){
	//var baseUrl = document.getElementById("baseUrl").value;
	var mainUrl =`${baseUrlForLogin}generateCaptcha`;
    const options=fetch(mainUrl,{
    method:"GET",
    headers:{
		'Content-type':'application/json'
		}
		});
		
		try{
		const response=await fetch(mainUrl,options);
		const data = await response.json();
		document.getElementById("captchaValueId").value=data.captchaValue;
		document.getElementById("captchaImage").src = data.captchaImage;
		if(!response.ok){
			throw new ("Failed to Fetch Captcha");
			}
		}
		catch(error){
			console.log(error);
		}
}

function validateCaptcha(captchaId){
		var captchaElement=document.getElementById(captchaId);
		var  captchaValue=captchaElement.value.trim();
		var hiddenCaptcha=document.getElementById("captchaValueId").value;
		var validateCaptcha=document.getElementById("validateCaptcha");
		if(captchaValue===''){
			captchaElement.value = "";
			validateCaptcha.innerText = "Please Enter Captcha....";
			validateCaptcha.classList = "text-danger";
			captchaElement.style.border = "1px solid red";
		    captchaElement.focus();
		    isCaptchaValidateElement.value="false";
			return false;
		}else if(captchaValue!==hiddenCaptcha){
			captchaElement.value = "";
			validateCaptcha.innerText = "Captcha is Incorrect......";
			validateCaptcha.classList = "text-danger";
			captchaElement.style.border = "1px solid red";
		    captchaElement.focus();
		    generateCaptcha();
		    isCaptchaValidateElement.value="false";
		    return false;
		}else {
			validateCaptcha.innerText = "";
			validateCaptcha.classList.remove("text-danger");
			captchaElement.style.border = "none";
			validateCaptcha.style="";
			isCaptchaValidateElement.value="true";
			return true;
	}
}


function login(event){
	const loginType= document.getElementById("loginType").innerText;
    loaderOverlay.classList.add("d-none");
	if(isEmailValidateElement.value==="false" || isEmailValidateElement.value===""){
		event.preventDefault();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Please Enter Email to Login...";
		modalPopup.children[0].childNodes[1].childNodes[3].classList="text-danger text-center";
		$("#exampleModalCenter").modal('show');
		return ;
	} 
	if(isPasswordValidateElement.value==="false" || isPasswordValidateElement.value===""){
		event.preventDefault();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Please Enter Password to Login...";
		modalPopup.children[0].childNodes[1].childNodes[3].classList="text-danger text-center";
		$("#exampleModalCenter").modal('show');		
			return ;
	}
	if(isCaptchaValidateElement.value==="false" || isCaptchaValidateElement.value===""){
		event.preventDefault();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Enter Valid Captcha to Login...";
		modalPopup.children[0].childNodes[1].childNodes[3].classList="text-danger text-center";
		$("#exampleModalCenter").modal('show');
		return ;
	}
	modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Loggin Successful Redirecting to Home Page...";
	modalPopup.children[0].childNodes[1].childNodes[3].classList="text-success text-center";
	$("#exampleModalCenter").modal('show');
	
	//add logic on the basis of loginType to redirect for login validation .
	 var loginUrl=baseUrlForLogin;
	if (loginType === "Admin Login") {
		loginUrl +=   "adminLogin";
	} else {
		loginUrl +=  "userLogin";
	}
		setTimeout(() => {
			//Add link redirect to home page
			  loaderOverlay.classList.add("d-none");
	          window.location.href = loginUrl;
			   }
			 , 5000); // Redirect after 2 seconds
}


function resetLoginForm(){
	isEmailValidateElement.value="";
	isPasswordValidateElement.value="";
	isCaptchaValidateElement.value="";	
}


if(document.getElementById("forgotPasswordId")!==null){
document.getElementById("forgotPasswordId").addEventListener("click",async ()=>{
	var urlForgotPasswordLoad=baseUrlForLogin+"loadForgotPassword";
	const options ={
		method:'GET',
		header:{
			'Content-type':'application/json'
		}
	};
	const response=await fetch(options,urlForgotPasswordLoad);
});
}


async function validateOTP(event){
	event.preventDefault();
	let otp=document.getElementById("otpId").value;
	let validateOTP=document.getElementById("validateOTP");
	if(otp.trim()==='' || otp.length!==4){
        validateOTP.innerText="Please Enter OTP...";
        validateOTP.classList="text-danger";
        otp.style.border="1px solid red";
		const countdownElement = document.getElementById("otpTimer");
        otp.focus();
        return ;
	}
	// For validating OTP
	const options = {
		method:"POST",
		headers:{
			'Content-type':'application/json'
		},
		body:JSON.stringify({otp:otp})
	};
	var urlForOTPValidate=baseUrlForLogin+"validateOTP";
	const response=await fetch(urlForOTPValidate , options);
	if(response.status===200){
		window.location.href=baseUrl+"forgotPassword";
			
	}else{
		validateOTP.innerText="Entered OTP is Incorrect...";
        validateOTP.classList="text-danger";
        otp.style.border="1px solid red";
        otp.focus();
        return;		
	}
	console.log(response);
}
async function validateEmailAndGetOTP(event){
	//add loader 	
	loaderOverlay.classList.remove("d-none");
	event.preventDefault();
	let emailElement=document.getElementById("emailId");
	console.log("Email validated Successfully...");
	const options = {
		method:"POST",
		headers:{
			'Content-type':'application/json'
		},
		body:JSON.stringify({email:emailElement.value})
	};
	var urlForSendOTP=baseUrlForLogin+"sendOTP";
	const response=await fetch(urlForSendOTP , options);
	if(response.status===200){
		window.location.href =baseUrl+"loadOTPPage";
		loader.addClass("d-none");
	}
}

function startCountdown(durationInSeconds) {
    let timer = durationInSeconds;
    const countdownElement = document.getElementById("otpTimer");
	const otpElement = document.getElementById("otpId");

	const interval = setInterval(() => {
        const seconds = timer % 60;

        countdownElement.textContent = `Time remaining: ${seconds < 10 ? '0' : ''}${seconds} seconds`;

        if (timer <= 0) {
            clearInterval(interval);
			countdownElement.innerHTML = `<a href="#" class="text-danger" onchange="requestNewOtp();>Request New OTP</a>`;
			otpElement.innerHtML = "";
			otpElement.readOnly = true;	
			const nextButton = document.querySelector('button[type="submit"].btn-primary');
	        if (nextButton) {
                nextButton.disabled = true; 
				nextButton.classList.add('disabled')
            }
			return;
			}

        timer--;
    }, 1000);
}


async function validatePasswordForReset(passwordElement){
		loaderOverlay.classList.remove("d-none");
		const regexForPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()<>:]).{6,12}$/
		let password = passwordElement.value.trim();
		let validatePassword = document.getElementById("validatePassword");
		let rePasswordField=document.getElementById("rePasswordId");
		let isPasswordValidateElement=document.getElementById("isPasswordValidate");
		let nextButton=document.getElementById("resetPasswordBtn");
		
		// Js Side Validation
		
		if (password==='') {
			passwordElement.value = "";
			rePasswordField.disabled = true;
			validatePassword.innerText = "Please Enter Password...";
			validatePassword.classList = "text-danger";
			validatePassword.style.border = "1px solid red";
			loaderOverlay.classList.add("d-none");
			isPasswordValidateElement.value="false";
			passwordElement.focus();
			nextButton.disabled = true;
			return false;
		}else if (password.length < 6) {
			rePasswordField.disabled = true;
			passwordElement.value = "";
			validatePassword.innerText = "Minimum Password Length is 6";
			validatePassword.classList = "text-danger";
			validatePassword.style.border = "1px solid red";
			loaderOverlay.classList.add("d-none");
			isPasswordValidateElement.value="false";
			nextButton.disabled = true;
			passwordElement.focus();
			return false;
		} else if (password.length > 12) {
			rePasswordField.disabled = true;
			passwordElement.value = "";
			validatePassword.innerText = "Maximum Password length is 12";
			validatePassword.classList = "text-danger";
			passwordElement.style.border = "1px solid red";
			passwordElement.focus();
			loaderOverlay.classList.add("d-none");
			isPasswordValidateElement.value="false";
			nextButton.disabled = true;
			return false;
		} else {
			validatePassword.innerText = "";
			validatePassword.classList="text-danger";
			passwordElement.style.border = "";
			validatePassword.style="";
			isPasswordValidateElement.value="false";
			rePasswordField.disabled = false;
			nextButton.disabled = false;
		}

		if (regexForPassword.test(password) == false) {
			rePasswordField.disabled = true;
			passwordElement.value = "";
			validatePassword.innerText = "Please Enter Valid Password...";
			validatePassword.classList = "text-danger";
			passwordElement.style.border = "1px solid red";
			loaderOverlay.classList.add("d-none");
			passwordElement.focus();
			nextButton.disabled = true;
			isPasswordValidateElement.value="false";
			return false;
		} else {
			validatePassword.innerText = "";
			validatePassword.classList.remove="text-danger";
			passwordElement.style.border = "";
			isPasswordValidateElement.value="true";
			rePasswordField.disabled = false;
			nextButton.disabled = false;
		}

		//Server side validation
		// Java Side Validation.
		var mainUrl = `${baseUrlForLogin}isPasswordExistsOrNotForResetPassword`;
		const encryptedPassword=await encryptInput(password);
		
		const options = {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
					},
					body:JSON.stringify({
						password:encryptedPassword.cipherText,
						iv:encryptedPassword.ivBase64
					})
				};
				
			const response=await fetch(mainUrl, options);
			if(response.status===409){
				validatePassword.innerText = "Password Already Exists..";
				validatePassword.classList = "text-danger";
				isPasswordValidateElement="false";
				loaderOverlay.classList.add("d-none");
				nextButton.disabled = true;
				return;
			}else if(response.status===500){
				validatePassword.innerText = "Internal Server Error. Please Try Again Later.";
				validatePassword.classList = "text-danger";
				loaderOverlay.classList.add("d-none");
				isPasswordValidateElement="false";
				nextButton.disabled = true;
				return;
			}else if(response.status===200){
				isPasswordValidateElement="true";
				validatePassword.innerText = "";
				validatePassword.classList.remove = "text-danger";
				loaderOverlay.classList.add("d-none");
				nextButton.disabled = false;
				return;
			}
} 

function validateRePassword(){
	loaderOverlay.classList.remove("d-none");
	const password= document.getElementById("passwordId").value.trim();
	const rePassword=document.getElementById("rePasswordId").value.trim();
	let isRePasswordValidateElement=document.getElementById("isRePasswordValidate");
	let nextButton=document.getElementById("resetPasswordBtn");
	if(rePassword===""){
		loaderOverlay.classList.add("d-none");
		validateRePassword.innerHTML="Please Re Enter Password.";
		validateRePassword.classList.add="text-danger";
		isRePasswordValidateElement.value="false";
		nextButton.disabled = true;
		return false;
	}else if(password!==rePassword){
		loaderOverlay.classList.add("d-none");
		validateRePassword.innerHTML="Both Password are not match. Enter correct Password.";
		validateRePassword.classList.add="text-danger";
		isRePasswordValidateElement.value="false";
		nextButton.disabled = true;
		return false;
	}else{
		loaderOverlay.classList.add("d-none");
		validateRePassword.innerHTML="";
		validateRePassword.classList.remove="text-danger";
		isRePasswordValidateElement.value="true";
		nextButton.disabled = false;
		return true;
	}
}

function closeModalPopup() {
	$("#exampleModalCenter").modal('hide');	
}

window.onload = function() {
	const currentPage = window.location.pathname; 
	  if (currentPage.includes("loadOTPPage")) { 
	      startCountdown(30); 
	  }
};

async function savePassword(event){
	event.preventDefault();
	loaderOverlay.classList.remove("d-none");
	const password=document.getElementById("passwordId").value.trim();
	const encryptedPassword=await encryptInput(password);
	var url=`${baseUrlForLogin}savePassword`;
	const options= {
		method:'POST',
		headers:{
			'Content-type':'application/json'
		},
		body:JSON.stringify({
				password:encryptedPassword.cipherText,
				iv:encryptedPassword.ivBase64
		})
	}
	const response=await fetch(url,options);
	if(response.status!==200){
		loaderOverlay.classList.add("d-none");
	}else{
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Password Reset Successfully. Redirecting to Login Page...";
		modalPopup.children[0].childNodes[1].childNodes[3].classList="text-success text-center";
		$("#exampleModalCenter").modal('show');
		setTimeout(() => {
            window.location.href = baseUrlForLogin + "login";
		        }
		 , 2000); // Redirect after 2 seconds
		loaderOverlay.classList.add("d-none");
		}
}

window.validateEmailForLogin = validateEmailForLogin;
window.validateEmailAndGetOTP = validateEmailAndGetOTP;
window.generateCaptcha =generateCaptcha ;
window.savePassword=savePassword;
window.validateOTP=validateOTP;
window.validateRePassword=validateRePassword;
window.validatePasswordForLogin=validatePasswordForLogin;
window.validatePasswordForReset=validatePasswordForReset;
window.login=login;
window.validateCaptcha=validateCaptcha
window.closeModalPopup=closeModalPopup;