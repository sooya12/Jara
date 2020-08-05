package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.Account;
import com.ssafy.jara.dto.Follow;
import com.ssafy.jara.dto.Location;

public interface AccountService {
	
	public int insertAccount(Account account);
	
	public int changeStatus(String code);
	
	public Account selectAccount(Account account);
	
	public int duplicateCheck(Account account);
	
	public int changeCode(String email);
	
	public String findCode(String email);
	
	public int changePassword(Account account);
	
	public Account findAccount(int id);
	
	public int findEmail(String email);
	
	public List<Account> findAllAccount();
	
	public boolean updateAccount(Account account);
	
	public int insertFollow(Follow follow);
	
	public int approveFollow(Follow follow);
	
	public List<Follow> findAllFollow();
	
	public int findFollow(Follow follow);
	
	public int deleteFollow(Follow follow);
	
	public List<Integer> findFollower(int following);
	public List<Integer> findFollowing(int follower);
	
	public double findX(String name);
	public double findY(String name);
	
	public Location findLocation(String name);
	
	public List<Location> findAllLocation();

	
}
