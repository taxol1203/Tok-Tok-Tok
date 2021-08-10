package com.ssafy.d204.api.service;

import com.ssafy.d204.api.request.DuplicateCheckEmailPostReq;
import com.ssafy.d204.api.request.UserLoginPostReq;
import com.ssafy.d204.api.request.UserRegisterPostReq;
import com.ssafy.d204.api.response.UserLoginPostRes;
import com.ssafy.d204.db.entity.User;
import com.ssafy.d204.db.repository.UserDao;
import com.ssafy.d204.general.UserFieldValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;
    JwtService jwtSvc;
    PasswordEncoder passwordEncoder;

    UserServiceImpl(UserDao userDao, JwtService jwtSvc, PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.jwtSvc = jwtSvc;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> getUserByIdx(int pk_idx) {
        try {
            User user = userDao.getUserByIdx(pk_idx);
            if (user == null) {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> loginUser(UserLoginPostReq loginInfo) {
        try {
            User user = userDao.getUser(loginInfo.getEmail());
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
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> registUser(UserRegisterPostReq info) {
        try {
            System.out.println(info);
//			int result =
//			if(info != null) {
//				System.out.println(info.getEmail());
//				System.out.println(info.getPasswd());
//			}
//			System.out.println(UserFieldValidator.passwdValidation(info.getPasswd()));
//			System.out.println(UserFieldValidator.emailValidation(info.getEmail()));
            if (info == null
                || info.getEmail() == null
                || info.getPasswd() == null
                || !UserFieldValidator.emailValidation(info.getEmail())
                || !UserFieldValidator.passwdValidation(info.getPasswd())) {
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
            info.setPasswd(passwordEncoder.encode(info.getPasswd()));
            userDao.registUser(info);

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) { // TODO : SQL Exception이랑 분리는 해야한다
            // 이미 존재하는 회원 정보 (UNIQUE 필드 : 이메일)
            // SQLIntegrityConstraintViolationException
            e.printStackTrace();
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

    }

    @Override
    public ResponseEntity<?> checkEmail(DuplicateCheckEmailPostReq email) {
        try {
            User user = userDao.checkEmail(email);
            if (user == null) {
                return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
