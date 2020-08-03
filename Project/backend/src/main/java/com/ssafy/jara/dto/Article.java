package com.ssafy.jara.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Article {

	private int id;							// 번호 	article pk
	private int writer;						// 작성자 	account fk
	private String contents;				// 내용
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date created_at;				// 작성일
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private Date updated_at;				// 수정일
//	private int likes;						// 좋아요 수
	private int shares;						// 공유 수
	private List<ArticleComment> comments; 	// 댓글 목록
	private List<Integer> likeAccounts;		// 좋아요 사용자 목록
	
	public Article() {}

	public Article(int id, int writer, String contents, Date created_at, Date updated_at,
			/* int likes, */ int shares, List<ArticleComment> comments, List<Integer> likeAccounts) {
		super();
		this.id = id;
		this.writer = writer;
		this.contents = contents;
		this.created_at = created_at;
		this.updated_at = updated_at;
		/* this.likes = likes; */
		this.shares = shares;
		this.comments = comments;
		this.likeAccounts = likeAccounts;
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

	/*
	 * public int getLikes() { return likes; }
	 * 
	 * public void setLikes(int likes) { this.likes = likes; }
	 */
	
	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public List<ArticleComment> getComments() {
		return comments;
	}

	public void setComments(List<ArticleComment> comments) {
		this.comments = comments;
	}
	
	public List<Integer> getLikeAccounts() {
		return likeAccounts;
	}

	public void setLikeAccounts(List<Integer> likeAccounts) {
		this.likeAccounts = likeAccounts;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", writer=" + writer + ", contents=" + contents + ", created_at=" + created_at
				+ ", updated_at=" + updated_at + /* ",  likes=" + likes + */ ", shares="  + shares + "]";
	}
	
}
