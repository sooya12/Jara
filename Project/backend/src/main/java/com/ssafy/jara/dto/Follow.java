package com.ssafy.jara.dto;

public class Follow {
	private int follower;
	private int following;
	private boolean status;
	
	public Follow() {}

	public Follow(int follower, int following, boolean status) {
		super();
		this.follower = follower;
		this.following = following;
		this.status = status;
	}


	public int getFollower() {
		return follower;
	}
	public void setFollower(int follower) {
		this.follower = follower;
	}
	public int getFollowing() {
		return following;
	}
	public void setFollowing(int following) {
		this.following = following;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
