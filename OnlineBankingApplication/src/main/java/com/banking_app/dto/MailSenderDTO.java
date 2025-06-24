package com.banking_app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MailSenderDTO {
	private String from;
	private String to;
	private String subject;
	private String body;
	private String message;
	private String filePath;
	private Byte[] attachementFile;
}

