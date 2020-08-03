package com.ssafy.jara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.jara.dao.EitherDao;
import com.ssafy.jara.dto.Either;

@Service
public class EitherServiceImpl implements EitherService {
	@Autowired
	EitherDao eitherDao;

	@Override
	public int insertEither(Either either) {
		System.out.println("either=" + either);
		return eitherDao.insertEither(either);
	}

	@Override
	public Either selectEither(int id) {
		System.out.println("id=" + id);
		return eitherDao.selectEither(id);
	}

	@Override
	public int deleteEither(int id) {
		System.out.println("id=" + id);
		return eitherDao.deleteEither(id);
	}

	@Override
	public List<Either> selectListEither() {
		return eitherDao.selectListEither();
	}
}
