package com.ssafy.d204.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuperController {

    @SuppressWarnings("unused")
    protected ResponseEntity<?> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Exception: " + e.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
