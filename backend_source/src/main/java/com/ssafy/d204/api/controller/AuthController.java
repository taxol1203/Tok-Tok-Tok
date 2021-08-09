package com.ssafy.d204.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserLoginPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.api.response.UserLoginPostRes;
import com.ssafy.d204.api.service.JwtService;
import com.ssafy.d204.api.service.UserService;
import com.ssafy.d204.db.entity.User;
import com.ssafy.d204.general.UserFieldValidator;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController extends SuperController {

  UserService uSvc;
  JwtService jwtSvc;
  PasswordEncoder passwordEncoder;

  public AuthController(UserService uSvc, JwtService jwtSvc, PasswordEncoder passwordEncoder) {
    this.uSvc = uSvc;
    this.jwtSvc = jwtSvc;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping(value = "/user/{pk_idx}")
  @ApiOperation(value = "pk_idx로 유저 정보 가져오기")
  public ResponseEntity<?> getUserByIdx(@PathVariable int pk_idx) throws Exception {
    try {
      User user = uSvc.getUserByIdx(pk_idx);
      if (user == null) {
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<User>(user, HttpStatus.OK);
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }


  @PostMapping(value = "/login")
  @ApiOperation(value = "유저 id와 passwd로 로그인")
  public ResponseEntity<?> login(@RequestBody UserLoginPostReq loginInfo) throws Exception {
    try {
      User user = uSvc.getUserById(loginInfo.getEmail());
      if (user == null) {
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
      }
      if (passwordEncoder.matches(loginInfo.getPasswd(), user.getPasswd())) {
        UserLoginPostRes ret = new UserLoginPostRes();
        String token = jwtSvc.create(user);
        ret.setToken(token); // 추후 jwt 적용 예정
        user.setPasswd("");
        ret.setUser(user);
        return new ResponseEntity<UserLoginPostRes>(ret, HttpStatus.OK);
      } else {
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
      }
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }

  @PostMapping(value = "/register")
  @ApiOperation(value = "회원가입")
  public ResponseEntity<?> register(@RequestBody UserRegisterPostReq registerInfo)
      throws Exception {
    try {
      System.out.println(registerInfo);
//			int result =
//			if(registerInfo != null) {
//				System.out.println(registerInfo.getEmail());
//				System.out.println(registerInfo.getPasswd());
//			}
//			System.out.println(UserFieldValidator.passwdValidation(registerInfo.getPasswd()));
//			System.out.println(UserFieldValidator.emailValidation(registerInfo.getEmail()));
      if (registerInfo == null
          || registerInfo.getEmail() == null
          || registerInfo.getPasswd() == null
          || !UserFieldValidator.emailValidation(registerInfo.getEmail())
          || !UserFieldValidator.passwdValidation(registerInfo.getPasswd())) {
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
      }
      registerInfo.setPasswd(passwordEncoder.encode(registerInfo.getPasswd()));
      uSvc.registUser(registerInfo);

      return new ResponseEntity<Void>(HttpStatus.OK);
    } catch (Exception e) { // TODO : SQL Exception이랑 분리는 해야한다
      // 이미 존재하는 회원 정보 (UNIQUE 필드 : 이메일)
      // SQLIntegrityConstraintViolationException
      e.printStackTrace();
      return new ResponseEntity<Void>(HttpStatus.CONFLICT);
    }
  }

  @PostMapping(value = "/checkemail")
  @ApiOperation(value = "이메일 중복 확인")
  public ResponseEntity<?> checkEmail(@RequestBody DuplicateCheckEmailPostReq email)
      throws Exception {
    try {
      User user = uSvc.checkEmail(email);
      if (user == null) {
        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<Void>(HttpStatus.OK);
    } catch (Exception e) {
      return exceptionHandling(e);
    }
  }
}
