package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Account;
import com.ssafy.jara.dto.Follow;
import com.ssafy.jara.dto.Location;

@Mapper
public interface AccountDao {
	
	public int insertAccount(Account account);
	
	public int changeStatus(String code);
	
	public Account selectAccount(Account account);
	
	public int duplicateCheck(Account account);
	
	public String changeCode(String email);
	
	public int findCode(String email);
	
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
