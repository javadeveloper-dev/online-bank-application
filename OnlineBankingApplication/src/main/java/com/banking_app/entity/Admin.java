package com.banking_app.entity;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin_register",schema="admin")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_name", length = 10)
	private String firstName;

	@Column(name = "last_name", length = 10)
	private String lastName;

	@Column(name = "addr", length = 100)
	private String address;

	@Column(name = "email", length = 320)
	private String email;

	@Column(name = "password", length = 12)
	private String password;

	@Column(name = "account_no", length = 12)
	private String accountNumber;

	@Column(name = "role", length = 5)
	private String role;

	@Column(name = "date_of_birth" )
	private Date dateOfBirth;

	@Column(name = "mob_no")
	private String mobileNo;

	@Column(name = "gender", length = 1)
	private char gender;

	@Column(name = "nationality", length = 10)
	private String nationality;

	@Lob
	@Column(name="profile_photo",columnDefinition="MEDIUMBLOB")
	private Byte[] profilePhoto;
	
	@Column(name = "security_que", length = 50)
	private String securityQuestion;
	
	@Column(name = "security_ans", length = 50)
	private String securityAnswer;
}
