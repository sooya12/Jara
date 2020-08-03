package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Either;

@Mapper
public interface EitherDao {
	public int insertEither(Either either);
	public Either selectEither(int id);
	public int deleteEither(int id);
	public List<Either> selectListEither();
}
