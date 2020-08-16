package com.ssafy.jara.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.jara.dto.Account;
import com.ssafy.jara.dto.Follow;
import com.ssafy.jara.dto.Location;

public interface AccountService {
	
	public int insertAccount(Account account);
	
	public int changeStatus(String code);
	
	public Account selectAccount(Account account);
	
	public String findPassword(String email);
	
	public int duplicateCheck(Account account);
	
	public int changeCode(String email);
	
	public String findCode(String email);
	
	public int changePassword(Account account);
	
	public Account findAccount(int id);
	
	public Account findPartAccount(int id);
	
	public int findEmail(String email);
	
	public List<Account> findAllAccount();
	
	public boolean updateAccount(Account account);
	
	public int deleteAccount(int id);
	
	public int deleteAllFollow(int id);
	
	public int insertFollow(Follow follow);
	
	public int approveFollow(Follow follow);
	
	public List<Follow> findAllFollow();
	
	public int findFollow(Follow follow);
	
	public int deleteFollow(Follow follow);
	
	public List<Integer> findFollower(int following);
	
	public List<Integer> findFollowing(int follower);
	
	public int findIdByEmail(String email);
	
	public int insertNaverAccount(Account account);
	
	public int updateNaverAccount(HashMap<String, Object> hashMap);
	
	public int insertKakaoAccount(Account account);
	
	public int updateKakaoAccount(HashMap<String, Object> hashMap);
	
}
