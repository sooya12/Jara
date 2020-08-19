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
	public int updateBarterStatus(HashMap<String, Object> hashMap);
	public int updateBarterImgSrc(HashMap<String, Object> hashMap);
}
