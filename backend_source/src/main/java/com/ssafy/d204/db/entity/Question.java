package com.ssafy.d204.db.entity;

import lombok.Data;

@Data
public class Question {
	
	int pk_idx;
	String content;
	
	public Question(String string) {
		content = string;
	}

	public Question(int pk_idx, String content) {
		super();
		this.pk_idx = pk_idx;
		this.content = content;
	}
	
	/*
	 * public Question(int idx, String content) { super(); this.pk_idx = idx;
	 * this.content = content; }
	 * 
	 * public int getIdx() { return pk_idx; }
	 * 
	 * public void setIdx(int idx) { this.pk_idx = idx; }
	 * 
	 * public String getContent() { return content; }
	 * 
	 * public void setContent(String content) { this.content = content; }
	 */
	
}
