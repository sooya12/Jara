package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.Either;
import com.ssafy.jara.dto.EitherChoice;

public interface EitherService {
	public int insertEither(Either either);
	public Either selectEither(int id);
	public int updateEither(int id);
	public int deleteEither(int id);
	public List<Either> selectListEither();
	public List<Either> selectPartialListEither(int s_idx, int count);
	public int pickEither(EitherChoice eitherChoice);
	public List<EitherChoice> selectEitherPickList(int either_id);
	public List<Integer> selectChoiceAList(int either_id);
	public List<Integer> selectChoiceBList(int either_id);
}
