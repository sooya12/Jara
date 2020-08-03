package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.TipComment;

@Mapper
public interface TipCommentDao {

	public int insertTipComment(TipComment tipComment);
	
	public List<TipComment> selectTipComments(int tip_id);
	
	public TipComment selectTipComment(int id);
	
	public int updateTipComment(TipComment tipComment);
	
	public int deleteTipComment(int id);
	
	public int deleteTipComments(int tip_id);
}
