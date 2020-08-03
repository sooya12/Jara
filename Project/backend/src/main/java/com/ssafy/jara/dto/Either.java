package com.ssafy.jara.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Either {
	private int id;				// 투표 번호
	private int writer;			// 투표 작성자
	private String question;	// 투표 주제
	private String choiceA;		// 투표 선택지A
	private String choiceB;		// 투표 선택지B
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date created_at;	// 투표 작성일
	private boolean status;		// 투표 상태
	
	public Either() {
		super();
	}
	public Either(int id, int writer, String question, String choiceA, String choiceB, Date created_at,
			boolean status) {
		super();
		this.id = id;
		this.writer = writer;
		this.question = question;
		this.choiceA = choiceA;
		this.choiceB = choiceB;
		this.created_at = created_at;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWriter() {
		return writer;
	}
	public void setWriter(int writer) {
		this.writer = writer;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getChoiceA() {
		return choiceA;
	}
	public void setChoiceA(String choiceA) {
		this.choiceA = choiceA;
	}
	public String getChoiceB() {
		return choiceB;
	}
	public void setChoiceB(String choiceB) {
		this.choiceB = choiceB;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Either [id=" + id + ", writer=" + writer + ", question=" + question + ", choiceA=" + choiceA
				+ ", choiceB=" + choiceB + ", created_at=" + created_at + ", status=" + status + "]";
	}
}
