package com.ssafy.jara.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jara.dto.Report;

@Mapper
public interface ReportDao {
	public int insertReport(Report report);
	
	public int countReport(String nickname);
	
	public int findAccusedId(String nickname);
	
	public List<Report> selectListReport();
	
	public String findNickname(int reporter_id);
}
