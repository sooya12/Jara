package com.ssafy.jara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.jara.dao.BarterDao;
import com.ssafy.jara.dto.Barter;

@Service
public class BarterServiceImpl implements BarterService {
	@Autowired
	BarterDao barterDao;

	@Override
	public int insertBarter(Barter barter) {
		System.out.println("barter=" + barter);
		return barterDao.insertBarter(barter);
	}

	@Override
	public int updateBarter(Barter barter) {
		System.out.println("barter=" + barter);
		return barterDao.updateBarter(barter);
	}
	
	@Override
	public int deleteBarter(int id) {
		System.out.println("id=" + id);
		return barterDao.deleteBarter(id);
	}

	@Override
	public List<Barter> selectListBarter() {
		return barterDao.selectListBarter();
	}

	@Override
	public List<Barter> selectListBarterTag(int tag_id) {
		System.out.println("tag_id=" + tag_id);
		return barterDao.selectListBarterTag(tag_id);
	}

	@Override
	public List<Barter> selectListBarterSearch(String searchWord) {
		System.out.println("searchWord=" + searchWord);
		return barterDao.selectListBarterSearch(searchWord);
	}

	@Override
	public Barter selectBarter(int id) {
		System.out.println("id=" + id);
		return barterDao.selectBarter(id);
	}

	@Override
	public int updateBarterHits(int id) {
		System.out.println("id=" + id);
		return barterDao.updateBarterHits(id);
	}

}
