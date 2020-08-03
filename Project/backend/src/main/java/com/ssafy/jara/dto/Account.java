package com.ssafy.jara.dto;

import java.util.Date;
import java.util.List;

public class Account {
	private int id;				// 회원 number
	private String nickname;	// 별명
	private String email;		// 이메일
	private String password;	// 비밀번호
	private boolean sex;		// 성별
	private Date birthday; 		// 생일
	private String location;	// 주소
	private String bio;			// 소개글
	
	private List<Integer> followerList;
	private List<Integer> followingList;
	
	private double x;
	private double y;

	
	public Account() {}

	public Account(int id, String nickname, String email, String password, boolean sex, Date birthday, String location,
			String bio, List<Integer> followerList, List<Integer> followingList, double x, double y) {
		super();
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.sex = sex;
		this.birthday = birthday;
		this.location = location;
		this.bio = bio;
		this.followerList = followerList;
		this.followingList = followingList;
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public int getId() {
		return id;
	}

	public List<Integer> getFollowerList() {
		return followerList;
	}

	public void setFollowerList(List<Integer> followerList) {
		this.followerList = followerList;
	}

	public List<Integer> getFollowingList() {
		return followingList;
	}

	public void setFollowingList(List<Integer> followingList) {
		this.followingList = followingList;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", nickname=" + nickname + ", email=" + email + ", password=" + password + ", sex="
				+ sex + ", birthday=" + birthday + ", location=" + location + ", bio=" + bio + ", followerList="
				+ followerList + ", followingList=" + followingList + ", x=" + x + ", y=" + y + "]";
	}


}
