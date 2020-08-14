package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Either;
import com.ssafy.jara.dto.EitherChoice;

@Mapper
public interface EitherDao {
	public int insertEither(Either either);
	public Either selectEither(int id);
	public int updateEither(int id);
	public int deleteEither(int id);
	public List<Either> selectListEither();
	public int pickEither(EitherChoice eitherChoice);
	public List<EitherChoice> selectEitherPickList(int either_id);
	public List<Integer> selectChoiceAList(int either_id);
	public List<Integer> selectChoiceBList(int either_id);
	
}
