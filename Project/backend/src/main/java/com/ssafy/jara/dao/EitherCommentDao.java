package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.EitherComment;

@Mapper
public interface EitherCommentDao {
	public int insertEitherComment(EitherComment eitherComment);
	public int updateEitherComment(EitherComment eitherComment);
	public int deleteEitherComment(int id);
	public List<EitherComment> selectListEitherComment(int either_id);
}
