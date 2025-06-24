var modalPopup=document.getElementById("exampleModalCenter");
function selectRole(role) {
    document.getElementById('selectedRole').value = role;
    document.getElementById('dropdownMenuButton').innerHTML = role;
  }
  function resetRole(dropdownRole){
    document.getElementById('selectedRole').removeAttribute("value");
    document.getElementById('dropdownMenuButton').innerHTML='Select Role';
  }

  var maleRadioBtn=document.querySelector("#maleRadio");
  var femaleRadioBtn=document.querySelector("#femaleRadio");

let updateAccount=document.querySelector("#btnForUpdateAccount");
if(updateAccount!==null){
if(updateAccount.innerHTML==="Update Details"){
  maleRadioBtn.setAttribute("disabled","disabled");
  femaleRadioBtn.setAttribute("disabled","disabled");
}
}

var inputFields=document.querySelectorAll("input");
if(updateAccount!==null){
	updateAccount.addEventListener("click",()=>{
  updateAccount.innerHTML="Save Changes";
  maleRadioBtn.removeAttribute("disabled");
  femaleRadioBtn.removeAttribute("disabled");
  console.log(updateAccount);
  console.log(inputFields);
  
  for(var input of inputFields){
    if(input.id!=="custId"){
    input.removeAttribute("readonly");
    }
  }
  document.querySelector("textarea").removeAttribute("readonly");
  document.removeEventListener("click",maleRadioBtn);
  document.removeEventListener("click",femaleRadioBtn);
  updateAccount.setAttribute("onclick","saveChanges();");
  // updateAccount.setAttribute("type","submit");
 
// Reset Form 
let formCloseBtn=document.querySelector(".btn-close");
formCloseBtn.addEventListener("click",()=>{
  // formCloseBtn.reset();
  updateAccount.removeAttribute("onclick");
  updateAccount.innerHTML="Update Details";
  maleRadioBtn.setAttribute("disabled","disabled");
  femaleRadioBtn.setAttribute("disabled","disabled");

  // Resetting the Form 
  maleRadioBtn.checked=false;
  femaleRadioBtn.checked=false;

  for(var input of inputFields){
    input.setAttribute("readonly",true);
    input.value="";
  }
  document.querySelector("textarea").setAttribute("readonly",true);
  document.querySelector("textarea").value="";
  document.addEventListener("click",maleRadioBtn);
  document.addEventListener("click",femaleRadioBtn);
})
})
}

function closeModalPopup(){
    $('#exampleModalCenter').modal('hide');
}



