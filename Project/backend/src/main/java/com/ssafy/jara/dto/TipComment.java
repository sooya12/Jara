package com.ssafy.jara.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TipComment {

	private int id;				// 번호 tip_comment pk
	private int tip_id;			// 팁 번호 fk
	private int writer;			// 작성자 account fk
	private String contents;	// 내용
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date created_at; 	// 작성일
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date updated_at;	// 수정일
	
	public TipComment() {}

	public TipComment(int id, int tip_id, int writer, String contents, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.tip_id = tip_id;
		this.writer = writer;
		this.contents = contents;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTip_id() {
		return tip_id;
	}

	public void setTip_id(int tip_id) {
		this.tip_id = tip_id;
	}

	public int getWriter() {
		return writer;
	}

	public void setWriter(int writer) {
		this.writer = writer;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "TipComment [id=" + id + ", tip_id=" + tip_id + ", writer=" + writer + ", contents=" + contents
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	
}
