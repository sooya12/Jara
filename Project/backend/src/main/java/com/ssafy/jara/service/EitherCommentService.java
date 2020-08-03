package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.EitherComment;

public interface EitherCommentService {
	public int insertEitherComment(EitherComment eitherComment);
	public int updateEitherComment(EitherComment eitherComment);
	public int deleteEitherComment(int id);
	public List<EitherComment> selectListEitherComment(int either_id);
}
