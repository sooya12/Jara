package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.TipComment;

public interface TipCommentService {

	public int insertTipComment(TipComment tipComment);
	
	public List<TipComment> selectTipComments(int tip_id);
	
	public TipComment selectTipComment(int id);
	
	public int updateTipComment(TipComment tipComment);
	
	public int deleteTipComment(int id);
	
	public int deleteTipComments(int tip_id);
}
