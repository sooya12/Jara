package com.ssafy.jara.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BarterComment {
	private int id;				// 물물교환 댓글 번호
	private int item_id;		// 물물교환 번호
	private int writer;			// 물물교환 댓글 작성자
	private String contents;	// 물물교환 댓글 내용
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date created_at;	// 물물교환 댓글 작성일
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date updated_at;	// 물물교환 댓글 수정일
	
	public BarterComment() {
		super();
	}
	public BarterComment(int id, int item_id, int writer, String contents, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.item_id = item_id;
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
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
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
		return "BarterComment [id=" + id + ", item_id=" + item_id + ", writer=" + writer + ", contents=" + contents
				+ ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
}
