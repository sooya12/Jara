package com.ssafy.jara.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Report;

@Mapper
public interface ReportDao {
	public int insertReport(Report report);
}
