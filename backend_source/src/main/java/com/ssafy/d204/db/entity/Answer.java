package com.ssafy.d204.db.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
@JsonAutoDetect
public class Answer {
	int pk_idx;
	String content;
	int fk_next_idx = 2;

	public Answer() {
		super();
	}
	
	public Answer(String content) {
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
