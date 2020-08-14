package com.ssafy.jara.dto;

public class Report {
	private int reporter_id; // 신고자 id
	private String accused_nickname; // 피신고자 닉네임
	private String contents; // 신고사유

	public Report() {
		super();
	}

	public Report(int reporter_id, String accused_nickname, String contents) {
		super();
		this.reporter_id = reporter_id;
		this.accused_nickname = accused_nickname;
		this.contents = contents;
	}

	public int getReporter_id() {
		return reporter_id;
	}

	public void setReporter_id(int reporter_id) {
		this.reporter_id = reporter_id;
	}

	public String getAccused_nickname() {
		return accused_nickname;
	}

	public void setAccused_nickname(String accused_nickname) {
		this.accused_nickname = accused_nickname;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Report [reporter_id=" + reporter_id + ", accused_nickname=" + accused_nickname + ", contents="
				+ contents + "]";
	}
}
