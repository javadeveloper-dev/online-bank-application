
import {encryptInput} from './EncryptUtil.js';

var modalPopup = document.getElementById("exampleModalCenter");
let loaderOverlay = document.getElementById("loaderOverlay");
let isEmailValidateElement=document.getElementById("isEmailValidate");
let isPasswordValidateElement=document.getElementById("isPasswordValidate");
let isCaptchaValidateElement=document.getElementById("isCaptchValidate");
var baseUrl = document.getElementById("baseUrl").value;
//document.addEventListener("DOMContentLoaded",generateCaptcha());
async function validateEmailForLogin(emailElement,event) {
	
    // Show loader and blur background
    loaderOverlay.classList.remove("d-none");
	const regexForEmail = /^[a-zA-Z0-9.]{1,64}@[a-zA-Z]{1,253}\.[a-zA-Z]{2,}$/;
	let email = emailElement.value.trim();
	let validateEmail = document.getElementById("validateEmail");
    
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

	// Java Side Validation.
	
	var mainUrl = `${baseUrl}isEmailPresentForLogin`;
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
	
	// Js Side Validation
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
	
	var mainUrl = `${baseUrl}isPasswordExistsOrNot`;
	const loginData = JSON.stringify({
		email: email,
		password: password
	});
	const options = {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
		},
		body: loginData
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















function validateUserNameAndPassword(userNameId, passwordId) {
	var userName = document.getElementById(userNameId);
	var password = document.getElementById(userNameId);
	const regexForEmail = /^[a-zA-Z0-9.]{1,64}@[a-zA-Z]{1,253}\.[a-zA-Z]{2,}$/;
	if (regexForEmail.test(userName) == false) {
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Enter the Valid Email Address";
		$("#exampleModalCenter").modal('show');
		return;
	}
	validateFieldAlreadyExists(userName, 'Email', 'email');

	password = password.trim();
	const regexForPassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()<>:]).{6,12}$/

	if (password.length < 6) {
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Password is small. Minimum Length of Password is 6 characters.";
		$("#exampleModalCenter").modal('show');
		passwordElement.value = "";
		return;
	}
	if (password.length > 12) {
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Password is Large. Maximum Length of Password is 12 characters.";
		$("#exampleModalCenter").modal('show');
		passwordElement.value = "";
		return;
	}
	if (regexForPassword.test(password) == false) {
		modalPopup.children[0].childNodes[1].childNodes[3].innerText = "Enter the Valid Password";
		$("#exampleModalCenter").modal('show');
		passwordElement.value = "";
		return;
	}

}

async function validateFieldAlreadyExists(fieldValue, fieldName, variableName) {
	try {
		
		var mainUrl = `${baseUrl}is${fieldName}AlreadyExists?${variableName}=${fieldValue}`;
		const response = fetch(mainUrl, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		});
		const fieldExists = await response;
		if (fieldExists.status === 200) {
			modalPopup.children[0].childNodes[1].childNodes[3].innerText = `${fieldName} already Exists..`;
			$("#exampleModalCenter").modal('show');
			return;
		}
	} catch (error) {
		console.log(error);
	}

	
	var mainUrl = `${baseUrl}isPasswordIsPresentOrNot?email=${encodeURIComponent(password)}`;
	const response = await fetch(mainUrl, options);
	if (response.status !== 200) {
		emailElement.value = "";
		validateEmail.innerText = "Entered Email Does Not Exists";
		validateEmail.classList = "text-danger";
		emailElement.style.border = "1px solid red";
		return;
	} else {
		validateEmail.innerText = "";
		validateEmail.classList.remove("text-danger");
		emailElement.style.border = "none";
	}
}

async function generateCaptcha(){
	var baseUrl = document.getElementById("baseUrl").value;
	var mainUrl =`${baseUrl}generateCaptcha`;
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


function login(emailId,passwordId,captchaId,event){
	var emailElement=document.getElementById(emailId);
	var passwordElement=document.getElementById(passwordId);
	if(isEmailValidateElement.value==="false" || isEmailValidateElement.value===""){
		event.preventDefault();
		return ;
	}
	if(isPasswordValidateElement.value==="false" || isPasswordValidateElement.value===""){
		event.preventDefault();
		return ;
	}
	if(isCaptchaValidateElement.value==="false" || isCaptchaValidateElement.value===""){
		event.preventDefault();
		return ;
	}
}


function resetLoginForm(){
	isEmailValidateElement.value="";
	isPasswordValidateElement.value="";
	isCaptchaValidateElement.value="";	
}


async function renderForgotPassword(){
	var urlForgotPasswordLoad=baseUrl+"loadForgotPassword";
	const options ={
		method:'GET',
		header:{
			'Content-type':'application/json'
		}
	};
	const response=await fetch(options,urlForgotPasswordLoad);
	
}

if(document.getElementById("forgotPasswordId")!==null){
document.getElementById("forgotPasswordId").addEventListener("click",async ()=>{
	var urlForgotPasswordLoad=baseUrl+"loadForgotPassword";
	const options ={
		method:'GET',
		header:{
			'Content-type':'application/json'
		}
	};
	const response=await fetch(options,urlForgotPasswordLoad);
});
}


function validateEmailAndGetOTP(event){
	let emailElement=document.getElementById("emailId");
	validateEmailForLogin(emailElement,event);
	console.log("Email validated Successfully...");
	window.location.href = baseUrl + "loadOTPPage";
}

window.validateEmailForLogin = validateEmailForLogin;
window.validateEmailAndGetOTP = validateEmailAndGetOTP;


