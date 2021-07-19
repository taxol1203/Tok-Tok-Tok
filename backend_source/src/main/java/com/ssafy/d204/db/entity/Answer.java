package com.ssafy.d204.db.entity;

import lombok.Data;

@Data
public class Answer {
	int pk_idx;
	String content;
	int fk_next_idx;
}
