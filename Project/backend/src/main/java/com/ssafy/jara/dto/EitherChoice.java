package com.ssafy.jara.dto;

public class EitherChoice {
	private int id;			// 투표 선택 id
	private int either_id;	// 투표 id
	private boolean pick;	// 선택한 선택지
	
	public EitherChoice() {
		super();
	}
	public EitherChoice(int id, int either_id, boolean pick) {
		super();
		this.id = id;
		this.either_id = either_id;
		this.pick = pick;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEither_id() {
		return either_id;
	}
	public void setEither_id(int either_id) {
		this.either_id = either_id;
	}
	public boolean isPick() {
		return pick;
	}
	public void setPick(boolean pick) {
		this.pick = pick;
	}
	
	@Override
	public String toString() {
		return "EitherChoice [id=" + id + ", either_id=" + either_id + ", pick=" + pick + "]";
	}
}
