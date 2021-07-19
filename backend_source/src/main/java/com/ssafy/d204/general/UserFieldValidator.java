package com.ssafy.d204.general;

import java.util.regex.Pattern;

/**
 * Java regex libraries do not need '/' at the start and the end. 
 * @author daebalprime
 * 
 */
public class UserFieldValidator {
	/**
	 * email.length() <= 40
	 * 
	 * @param email
	 * @return
	 */
	public static boolean emailValidation(String email) {
		if(email == null || email.length() > 40) return false;
		String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*[.]{1}[a-zA-Z]{2,3}$";
		return Pattern.matches(pattern, email);
	}
	/**
	 * Password must includes more than 1 special symbols, 1 character, 1 number each.
	 * 9 <= passwd.length() <= 16
	 * @param passwd
	 * @return
	 */
	public static boolean passwdValidation(String passwd) {
		String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{9,16}$";
		return Pattern.matches(pattern, passwd);
	}
	
}
