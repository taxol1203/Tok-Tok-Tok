package com.ssafy.d204.db.entity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class User {
	long memberId;
	String email;
	String passwd;
	String username;
}
