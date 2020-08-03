package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.Either;

public interface EitherService {
	public int insertEither(Either either);
	public Either selectEither(int id);
	public int deleteEither(int id);
	public List<Either> selectListEither();
}
