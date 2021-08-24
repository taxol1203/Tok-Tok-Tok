package com.ssafy.d204;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssafy.d204.general.UserFieldValidator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PasswordEncoderTest {

	@Test
	void doTest() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String a = passwordEncoder.encode("asdf");
		String b = passwordEncoder.encode("asdf");
		Assertions.assertTrue(passwordEncoder.matches("asdf",a));
		Assertions.assertTrue(passwordEncoder.matches("asdf",b));
	}
}
