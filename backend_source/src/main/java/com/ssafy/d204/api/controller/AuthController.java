package com.ssafy.d204.api.controller;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserLoginPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.api.response.UserLoginPostRes;
import com.ssafy.d204.api.service.JwtService;
import com.ssafy.d204.api.service.UserService;
import com.ssafy.d204.db.entity.User;
import com.ssafy.d204.general.UserFieldValidator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @ApiImplicitParam(name = "pk_idx", value="유저의 이메일이 아닌 고유 회원번호, db에 저장되는 int 타입의 pk_idx")
    @ApiOperation(value = "pk_idx로 유저 정보 가져오기")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "pk_idx에 해당하는 유저정보가 있을 경우"),
        @ApiResponse(code = 204, message = "유저 정보를 찾지 못한 경우")
    })
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
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "로그인 성공, 토큰과 유저정보 반환"),
        @ApiResponse(code = 401, message = "아이디 혹은 비밀번호 일치하지 않음")
    })
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
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "회원가입 성공"),
        @ApiResponse(code = 409, message = "이미 존재하는 이메일"),
        @ApiResponse(code = 400, message = "이메일/비밀번호 유효성 검사 통과 실패")
    })
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
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "존재하지 않는 이메일."),
        @ApiResponse(code = 404, message = "해당 이메일이 DB에 존재하지 않을 경우. 즉 가입할 수 있는 이메일.")
    })
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
