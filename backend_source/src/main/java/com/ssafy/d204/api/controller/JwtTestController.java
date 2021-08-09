package com.ssafy.d204.api.controller;

import com.ssafy.d204.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/jwt")
public class JwtTestController{

    @Autowired
    UserService uSvc;

    @PostMapping(value = "/secret")
    @ApiOperation(value = "jwtInterceptor Test")
    public ResponseEntity<?> getSecret() throws Exception {
        return new ResponseEntity<String>("this is secret!", HttpStatus.OK);
    }
}
