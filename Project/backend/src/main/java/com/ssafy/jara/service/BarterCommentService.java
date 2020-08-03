package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.BarterComment;

public interface BarterCommentService {
	public int insertBarterComment(BarterComment barterComment);
	public int updateBarterComment(BarterComment barterComment);
	public int deleteBarterComment(int id);
	public List<BarterComment> selectListBarterComment(int item_id);
}
