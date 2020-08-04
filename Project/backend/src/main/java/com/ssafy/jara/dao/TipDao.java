package com.ssafy.jara.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Tip;

@Mapper
public interface TipDao {

	public int insertTip(Tip tip);
	
	public List<Tip> selectListTip();
	
	public List<Tip> selectListTipTag(int tag_id);
	
	public List<Tip> selectListTipSearch(String searchWord);
	
	public List<Tip> selectListTipTagSearch(HashMap<String, String> hashMap);
	
	public Tip selectTip(int id);
	
	public int updateTip(Tip tip);
	
	public int updateTipHits(int id); 
	
	public int deleteTip(int id);
	
	public int insertTipLike(HashMap<String, Integer> hashMap);
	
	public List<Integer> selectTipLikeAccounts(int tip_id);
	
	public int selectTipLike(HashMap<String, Integer> hashMap);
	
	public int deleteTipLike(HashMap<String, Integer> hashMap);
	
	public int deleteTipLikes(int tip_id);
	
	public int insertTipScrap(HashMap<String, Integer> hashMap);
	
	public List<Tip> selectListTipScrap(int user_id);
}
