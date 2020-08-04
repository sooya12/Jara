package com.ssafy.jara.common.dto.fileupload;

import java.util.Date;

public class BarterFileUpload {
	private int id;						// 파일 번호
	private int item_id;				// 물물교환 번호
	private String origianl_file_name;	// 기존 파일명
	private String stored_file_name;	// 저장된 파일명
	private Date created_at;			// 파일 업로드 날짜
	
	public BarterFileUpload() {
		super();
	}
	public BarterFileUpload(int id, int item_id, String origianl_file_name, String stored_file_name, Date created_at) {
		super();
		this.id = id;
		this.item_id = item_id;
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
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
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
		return "BarterFileUpload [id=" + id + ", item_id=" + item_id + ", origianl_file_name=" + origianl_file_name
				+ ", stored_file_name=" + stored_file_name + ", created_at=" + created_at + "]";
	}
}
