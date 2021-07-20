package com.ssafy.d204.db.entity;

import lombok.Data;

@Data
public class Answer {
	int pk_idx;
	String content;
	int fk_next_idx;
	
	public Answer(String content) {
		super();
		this.content = content;
	}

	public Answer(int pk_idx, String content) {
		super();
		this.pk_idx = pk_idx;
		this.content = content;
	}
	
	public Answer(int pk_idx, String content, int fk_next_idx) {
		super();
		this.pk_idx = pk_idx;
		this.content = content;
		this.fk_next_idx = fk_next_idx;
	}

	public Answer(int pk_idx, int fk_next_idx) {
		super();
		this.pk_idx = pk_idx;
		this.fk_next_idx = fk_next_idx;
	}
	
}
