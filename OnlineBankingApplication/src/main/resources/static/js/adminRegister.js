const loaderWrapper=document.getElementById("loader-wrapper");

 function validateName(name){
	var nameValue=name.value.trim();
    const regexForName=/^[A-Z]+[a-z]{1,58}$/;
    if(regexForName.test(nameValue)==false){
        modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter the Valid Name";
        if (!nameValue || !regexForName.test(nameValue)) {
          // alert("Enter a valid name."); // Replace modal with an alert
        $("#exampleModalCenter").modal('show');
         name.value='';
         name.focus();
         name.select();
        }
        return;
    }
    var firstName=document.getElementById("firstName").value.trim();
    var lastName=document.getElementById("lastName").value.trim();
    if(firstName===lastName){
        modalPopup.children[0].childNodes[1].childNodes[3].innerText="First Name and Last Name are Not Same";
        $("#exampleModalCenter").modal('show');
    }
}


function validateAddress(address){
  let addr=address.value.trim();
  const regexForAddress=/[!@#\$%\^\&*\)\(+=._-]/g;
  const regexForMultipleSpaces=/\s{2,}/;
  if(addr.match(addr)==true){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter the Valid Address";
    $("#exampleModalCenter").modal('show');
    return;
  }
  if(addr.length<10){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Address is too small. Minimum Length is 10 characters";
    $("#exampleModalCenter").modal('show');
    return;
  }else if(addr.length>100){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Address is too large. Maximum Length is 100 characters";
    $("#exampleModalCenter").modal('show');
    return;
  }
  if(regexForAddress.test(addr)==true){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter the Valid Address.No Special Character allowed.";
    $("#exampleModalCenter").modal('show');
    return;
  }
  if(regexForMultipleSpaces.test(addr)==true){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Address contain extra white space.";
    $("#exampleModalCenter").modal('show');
    return;
  }

}

function validateEmail(emailElement){
  let email=emailElement.value.trim();
  const regexForEmail=/^[a-zA-Z0-9.]{1,64}@[a-zA-Z]{1,253}\.[a-zA-Z]{2,}$/;
  if(regexForEmail.test(email)==false){
      modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter the Valid Email Address";
      $("#exampleModalCenter").modal('show');
      return;
  }
 validateFieldAlreadyExists(email,'Email','email');
}

function validatePassword(passwordElement){
  let password=passwordElement.value.trim();
  const regexForPassword=/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[~!@#$%^&*()<>:]).{6,12}$/

  if(password.length<6){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Password is small. Minimum Length of Password is 6 characters.";
    $("#exampleModalCenter").modal('show');
    passwordElement.value="";
    return;
  }
  if(password.length>12){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Password is Large. Maximum Length of Password is 12 characters.";
    $("#exampleModalCenter").modal('show');
    passwordElement.value="";
    return;
  }
  if(regexForPassword.test(password)==false){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter the Valid Password";
    $("#exampleModalCenter").modal('show');
    passwordElement.value="";
    return;
  }
}

function validateConfirmPassword(confirmPasswordElement){
  var password=document.getElementById("password").value.trim();
  var confirmPassword=confirmPasswordElement.value.trim();
  if(confirmPassword!==password){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Entered Password and Confirm Password are not same.";
    $("#exampleModalCenter").modal('show');
    confirmPasswordElement.value="";
    return ;
  }
}



function validateAccountNumber(accountNumberElement){
  if(accountNumberElement.value===""){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter Account Number";
    $("#exampleModalCenter").modal('show');
    return ;
  }
  var accountNumber=accountNumberElement.value.trim();
  const regexForAccountNumber = /^[0-9]{8,12}$/;
  if(regexForAccountNumber.test(accountNumber)==false){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter Valid Account Number";
    $("#exampleModalCenter").modal('show');
    return ;
  }
  if(accountNumber.length<8){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Account Number is small. Minimum length of Account Number is 8";
    $("#exampleModalCenter").modal('show');
    return ;
  }
  if(accountNumber.length>12){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Account Number is large. Maximum length of Account Number is 12";
    $("#exampleModalCenter").modal('show');
    return ;
  }
	validateFieldAlreadyExists(accountNumber,'AccountNo','accountNo');
}



 
// function validateDateOfBirth(dateOfBirthElement){
//   const input = dateOfBirthElement;
//   const dateValue = input.value; // yyyy-MM-dd
//   if (dateValue) {
//     const [year, month, day] = dateValue.split('-'); // Split the value
//     const formattedDate = `${day}/${month}/${year}`; // Convert to dd/MM/yyyy
//     input.value = formattedDate; // Update input value
//   }
// }

function validateMobileNo(mobileNoElement){
	var mobileNo=mobileNoElement.value.trim();
	const regexForMobileNo=/^[6-9][0-9]{9}$/;
	if(regexForMobileNo.test(mobileNo)==false){
	  modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter Valid Mobile Number...";
	    $("#exampleModalCenter").modal('show');
	    mobileNoElement.value="";
	    return ;  
	}
	validateFieldAlreadyExists(mobileNo,'MobileNo','mobileNo');
		
}
function validateNationality(nationalityElement){
  var nationality=nationalityElement.value.trim();
  const regexForNationality=/^[a-zA-Z]{2,50}$/;
  if(regexForNationality.test(nationality)==false){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Enter Valid Nationality";
    $("#exampleModalCenter").modal('show');
    nationalityElement.value="";
    return ; 
  }
}

function validateProfilePhoto(profilePhotoElement){
  var profilePhotoSize=profilePhotoElement.files[0].size;
  if(profilePhotoSize>2097152){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Profile Photo Size is Maximum.Min size is 20KB and Max. Size is 2MB";
    $("#exampleModalCenter").modal('show');
    profilePhotoElement.value="";
    return ; 
  }
  if(profilePhotoSize<50000){
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Profile Photo Size is Minimum.Min size is 20KB and Max. Size is 2MB";
    $("#exampleModalCenter").modal('show');
    profilePhotoElement.value="";
    return ; 
  }
}

function showLoader(){
	loaderWrapper.style.display="flex";
	document.body.classList.add("blur");
}

function hideLoader(){
	loaderWrapper.style.display="none";
	document.body.classList.remove("blur");
}


async function saveRegistrationForm(formBody,event){
  showLoader();
  event.preventDefault(); 
  var form=formBody;
  var role=formBody.dropdownMenuButton.innerText.trim();
  if(role==="Select Role"){
    hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Please Select the Role";
    $("#exampleModalCenter").modal('show');
    event.preventDefault();
    return ;
  }
  var gender=formBody.gender.value;
  if(gender==="" || gender===null || gender===undefined){
    hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Please Select the Gender";
    $("#exampleModalCenter").modal('show');
    event.preventDefault();
    return ;
  }

  var dateOfBirthValue=formBody.dateOfBirth.value;
  if(dateOfBirthValue===""){
	hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Please Select the Date of Birth";
    $("#exampleModalCenter").modal('show');
    event.preventDefault();
    return ;
  }

  var dateOfBirth=new Date(dateOfBirthValue);
  var todayDate=new Date();
  if(dateOfBirth.getFullYear()>todayDate.getFullYear()){
    hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Date of Birth is not greater than Curruent Date.";
    $("#exampleModalCenter").modal('show');    
    event.preventDefault();
    return;
  }
  if(dateOfBirth.getFullYear()===todayDate.getFullYear() && dateOfBirth.getMonth()+1>todayDate.getMonth()+1){
    hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Date of Birth is not greater than Curruent Date.";
    $("#exampleModalCenter").modal('show');    
    event.preventDefault();
    return;
  }
  if(dateOfBirth.getFullYear()===todayDate.getFullYear() && dateOfBirth.getMonth()+1===todayDate.getMonth()+1 && dateOfBirth.getDate()+1>todayDate.getDate()+1){
    hideLoader();
    modalPopup.children[0].childNodes[1].childNodes[3].innerText="Date of Birth is not greater than Curruent Date.";
    $("#exampleModalCenter").modal('show');    
    event.preventDefault();
    return;
  }
 
  var saveRegistereData=getFormDataFromFormBody(formBody);
  var baseUrl=document.getElementById("baseUrl").value;
  const url=`${baseUrl}saveAdminRegistration?adminRegistrationData`;
  const options={
	  method:'POST',
	  body:saveRegistereData,
  };
 	const response=await fetch(url,options);
 	/*
 	if(response.status===200){
		hideLoader();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText="Admin Register Successfully....";
    	$("#exampleModalCenter").modal('show');    
    	event.preventDefault();
    	makeFormReadonly(formBody);
    	return;
	 }else{
		hideLoader();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText="Please Contact System Administrator...";
    	$("#exampleModalCenter").modal('show');    
    	event.preventDefault();
    	return;
	 } */
 	if(response.status!==200){
		hideLoader();
		modalPopup.children[0].childNodes[1].childNodes[3].innerText="Please Contact System Administrator...";
    	$("#exampleModalCenter").modal('show');    
    	event.preventDefault();
    	return;
	 }else{
		 window.location.href = `${baseUrl}welcome`;
		 hideLoader();
		 return;
	 }
}         


async function validateFieldAlreadyExists(fieldValue,fieldName,variableName){
	try{
		var baseUrl=document.getElementById("baseUrl").value;
		var mainUrl=`${baseUrl}is${fieldName}AlreadyExists?${variableName}=${fieldValue}`;
		const response=fetch(mainUrl,{
			method:'GET',
			header:{
				'Content-Type':'application/json'
			}
		});
		const fieldExists=await response;
		if(fieldExists.status===200){
        modalPopup.children[0].childNodes[1].childNodes[3].innerText=`${fieldName} already Exists..`;
      	$("#exampleModalCenter").modal('show');
      	return;
		}
	}catch(error){
		console.log(error);
	}	
}


function getFormDataFromFormBody(formBody){
	
	 const formData = new FormData();
	 for (let a of formBody) {
        if (a.tagName === 'INPUT' && a.type !== 'file') {
            formData.append(a.name, a.value);
        }else if (a.tagName === 'INPUT' && a.checked == true) {
          formData.append(a.name, a.value);
      	}else if (a.tagName === 'INPUT' && a.type === 'file' && a.accept === "image/jpeg") {
            formData.append(a.name, a.files[0]);
        }
    }
  return formData;
}

function makeFormReadonly(formBody){
	for (let a of formBody) {
       a.readOnly=true;
       a.style.pointerEvents="none";
    }
}