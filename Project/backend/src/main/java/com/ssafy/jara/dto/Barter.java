package com.ssafy.jara.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Barter {
	private int id;				// 물물교환 번호
	private int writer;			// 물물교환 작성자
	private int tag_id;			// 태그 id
	private String title;		// 물물교환 제목
	private int price;			// 물물교환 가격
	private String contents;	// 물물교환 내용
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date created_at;	// 물물교환 작성일
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date updated_at;	// 물물교환 수정일
	private boolean status;		// 물물교환 거래상태
	private int hits;			// 물물교환 조회수
	
	private String img_src;
	
	public Barter() {
		super();
	}
	public Barter(int id, int writer, int tag_id, String title, int price, String contents, Date created_at,
			Date updated_at, boolean status, int hits, String img_src) {
		super();
		this.id = id;
		this.writer = writer;
		this.tag_id = tag_id;
		this.title = title;
		this.price = price;
		this.contents = contents;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.status = status;
		this.hits = hits;
		this.img_src = img_src;
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
	public int getTag_id() {
		return tag_id;
	}
	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	@Override
	public String toString() {
		return "Barter [id=" + id + ", writer=" + writer + ", tag_id=" + tag_id + ", title=" + title + ", price="
				+ price + ", contents=" + contents + ", created_at=" + created_at + ", updated_at=" + updated_at
				+ ", status=" + status + ", hits=" + hits + ", img_src=" + img_src + "]";
	}
}
