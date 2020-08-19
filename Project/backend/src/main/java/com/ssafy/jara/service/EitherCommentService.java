package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.EitherComment;

public interface EitherCommentService {
	// 투표 댓글 등록
	public int insertEitherComment(EitherComment eitherComment);
	
	// 투표 댓글 수정
	public int updateEitherComment(EitherComment eitherComment);
	
	// 투표 댓글 삭제
	public int deleteEitherComment(int id);
	
	// 투표 전체 댓글 조회
	public List<EitherComment> selectListEitherComment(int either_id);
	
	// 해당하는 투표의 댓글 조회
	public EitherComment selectEitherComment(int id);
}
