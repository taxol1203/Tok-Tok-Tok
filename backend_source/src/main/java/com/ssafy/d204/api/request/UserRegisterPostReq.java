package com.ssafy.d204.api.request;

import lombok.Data;

@Data
public class UserRegisterPostReq {
	String email;
	String passwd;
	String username;
}
