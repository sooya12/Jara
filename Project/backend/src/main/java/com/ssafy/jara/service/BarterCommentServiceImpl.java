package com.ssafy.jara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.jara.dao.BarterCommentDao;
import com.ssafy.jara.dto.BarterComment;

@Service
public class BarterCommentServiceImpl implements BarterCommentService {
	@Autowired
	BarterCommentDao barterCommentDao;

	@Override
	public int insertBarterComment(BarterComment barterComment) {
		System.out.println("barterComment=" + barterComment);
		return barterCommentDao.insertBarterComment(barterComment);
	}

	@Override
	public int updateBarterComment(BarterComment barterComment) {
		System.out.println("barterComment=" + barterComment);
		return barterCommentDao.updateBarterComment(barterComment);
	}

	@Override
	public int deleteBarterComment(int id) {
		System.out.println("id=" + id);
		return barterCommentDao.deleteBarterComment(id);
	}

	@Override
	public List<BarterComment> selectListBarterComment(int item_id) {
		return barterCommentDao.selectListBarterComment(item_id);
	}

}
