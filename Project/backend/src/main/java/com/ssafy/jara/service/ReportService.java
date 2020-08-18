package com.ssafy.jara.service;

import java.util.List;

import com.ssafy.jara.dto.Report;

public interface ReportService {
	public int insertReport(Report report);

	public int countReport(String nickname);

	public int findAccusedId(String nickname);

	public List<Report> selectListReport();
	
	public String findNickname(int reporter_id);
}
