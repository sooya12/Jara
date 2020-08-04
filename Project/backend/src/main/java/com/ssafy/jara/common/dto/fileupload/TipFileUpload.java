package com.ssafy.jara.common.dto.fileupload;

import java.util.Date;

public class TipFileUpload {
	private int id;						// 파일 번호
	private int tip_id;					// 팁 번호
	private String origianl_file_name;	// 기존 파일명
	private String stored_file_name;	// 저장된 파일명
	private Date created_at;			// 파일 업로드 날짜
	
	public TipFileUpload() {
		super();
	}
	public TipFileUpload(int id, int tip_id, String origianl_file_name, String stored_file_name, Date created_at) {
		super();
		this.id = id;
		this.tip_id = tip_id;
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
	public int getTip_id() {
		return tip_id;
	}
	public void setTip_id(int tip_id) {
		this.tip_id = tip_id;
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
		return "TipFileUpload [id=" + id + ", tip_id=" + tip_id + ", origianl_file_name=" + origianl_file_name
				+ ", stored_file_name=" + stored_file_name + ", created_at=" + created_at + "]";
	}
}
