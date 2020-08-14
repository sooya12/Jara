package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.BarterComment;

@Mapper
public interface BarterCommentDao {
	public int insertBarterComment(BarterComment barterComment);
	public int updateBarterComment(BarterComment barterComment);
	public int deleteBarterComment(int id);
	public List<BarterComment> selectListBarterComment(int item_id);
	public BarterComment selectBarterComment(int id);
}
