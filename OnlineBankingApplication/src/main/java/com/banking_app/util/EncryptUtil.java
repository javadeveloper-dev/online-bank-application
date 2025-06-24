package com.banking_app.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {
	public static String decryptAESData(String data,String iv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String secretKey="xZnY6hMPtoz8RtAbGtG5qAwOKMcszH1MQ1BoUPsA0E4=";
		byte[] cipherTextByteArr = Base64.getDecoder().decode(data);
		byte[] ivByteArr=Base64.getDecoder().decode(iv);
		
		byte[] secretKeyBytes = Base64.getDecoder().decode(secretKey);
		
		SecretKeySpec keySpec=new SecretKeySpec(secretKeyBytes, "AES");
		GCMParameterSpec paramSpec=new GCMParameterSpec(128,ivByteArr);
		
		Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec,paramSpec);
		
		String decryptedData=new String(cipher.doFinal(cipherTextByteArr),StandardCharsets.UTF_8);
		return decryptedData;
	}
}
