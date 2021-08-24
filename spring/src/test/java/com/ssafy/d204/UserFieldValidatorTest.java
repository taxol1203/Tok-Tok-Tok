package com.ssafy.d204;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.d204.general.UserFieldValidator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserFieldValidatorTest {
	
	@Test
	void doTest() {
		Assertions.assertTrue(UserFieldValidator.emailValidation("abc@efg.hi"));
		Assertions.assertTrue(UserFieldValidator.emailValidation("daebalprime@gmail.com"));
		// too short
		Assertions.assertFalse(UserFieldValidator.emailValidation("k@n.s"));
		// more than 40 chars.
		Assertions.assertFalse(UserFieldValidator.emailValidation("leokekoaabcdefghioasdfjkasdkflasdflkjasdfasdfasdfgasdf@n.s"));
		Assertions.assertFalse(UserFieldValidator.emailValidation("naver.com"));
		Assertions.assertFalse(UserFieldValidator.emailValidation("daebalprime@onfleek"));
		Assertions.assertFalse(UserFieldValidator.passwdValidation("1a2a3a4a"));
		Assertions.assertFalse(UserFieldValidator.passwdValidation("1a2a3a4@"));
		Assertions.assertTrue(UserFieldValidator.passwdValidation("1a2a3a4a!"));
		Assertions.assertFalse(UserFieldValidator.passwdValidation("1aaaaaaaaaaaaaaa"));
		Assertions.assertFalse(UserFieldValidator.passwdValidation("1222222222222222"));
		Assertions.assertFalse(UserFieldValidator.passwdValidation("1a2a3a4a5a6a7a8a!"));
	}
}
