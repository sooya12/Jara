package com.ssafy.jara.common.dto.fileupload;

import java.util.Date;

public class ArticleFileUpload {
	private int id;						// 파일 번호
	private int article_id;				// 아티클 번호
	private String origianl_file_name;	// 기존 파일명
	private String stored_file_name;	// 저장된 파일명
	private Date created_at;			// 파일 업로드 날짜
	
	public ArticleFileUpload() {
		super();
	}
	public ArticleFileUpload(int id, int article_id, String origianl_file_name, String stored_file_name,
			Date created_at) {
		super();
		this.id = id;
		this.article_id = article_id;
		this.origianl_file_name = origianl_file_name;
		this.stored_file_name = stored_file_name;
		this.created_at = created_at;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getArticle_id() {
		return article_id;
	}
	public void setArticle_id(int article_id) {
		this.article_id = article_id;
	}
	public String getOrigianl_file_name() {
		return origianl_file_name;
	}
	public void setOrigianl_file_name(String origianl_file_name) {
		this.origianl_file_name = origianl_file_name;
	}
	public String getStored_file_name() {
		return stored_file_name;
	}
	public void setStored_file_name(String stored_file_name) {
		this.stored_file_name = stored_file_name;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	
	@Override
	public String toString() {
		return "ArticleFileUpload [id=" + id + ", article_id=" + article_id + ", origianl_file_name="
				+ origianl_file_name + ", stored_file_name=" + stored_file_name + ", created_at=" + created_at + "]";
	}
}
