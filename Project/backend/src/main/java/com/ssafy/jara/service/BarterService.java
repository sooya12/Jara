package com.ssafy.jara.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.jara.dto.Barter;

public interface BarterService {
	public int insertBarter(Barter barter);
	public Barter selectBarter(int id);
	public int updateBarter(Barter barter);
	public int deleteBarter(int id);
	public List<Barter> selectListBarter();
	public List<Barter> selectListBarterTag(int tag_id);
	public List<Barter> selectListBarterSearch(String searchWord);
	public int updateBarterHits(int id);
	public int updateBarterStatus(HashMap<String, Object> hashMap);
	public int updateBarterImgSrc(HashMap<String, Object> hashMap);
}
