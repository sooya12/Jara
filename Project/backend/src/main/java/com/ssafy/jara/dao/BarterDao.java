package com.ssafy.jara.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Barter;

@Mapper
public interface BarterDao {
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
