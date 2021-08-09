package com.ssafy.d204.db.entity;

import lombok.Data;

@Data
public class User {

    long pk_idx;
    String email;
    String passwd;
    String username;
}
