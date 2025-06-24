const base64Key="xZnY6hMPtoz8RtAbGtG5qAwOKMcszH1MQ1BoUPsA0E4=";
export async function encryptInput(data){
	//Convert base64 key string to bytes then to a Cryptokey
	const rawKey=Uint8Array.from(atob(base64Key),c=>c.charCodeAt(0));
	const cryptoKey=await crypto.subtle.importKey("raw",rawKey,{name:"AES-GCM"},false,["encrypt"]);
	
	// Generate a random initialisation vector
	const iv=crypto.getRandomValues(new Uint8Array(12));
	
	// convert email to bytes and encrypt using AES-GSM
	const encoder=new TextEncoder();
	const encrypted= await crypto.subtle.encrypt(
		{name:"AES-GCM",iv:iv},
		cryptoKey,
		encoder.encode(data)
	);
	
	//Convert encrypted data and iv to base64 so we can send via JSON
	const cipherText=btoa(String.fromCharCode(...new Uint8Array(encrypted)));
	const ivBase64=btoa(String.fromCharCode(...iv));
	
	const resultArray={cipherText,ivBase64};
	return resultArray;
}