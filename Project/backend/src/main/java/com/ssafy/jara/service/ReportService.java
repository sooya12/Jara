package com.ssafy.jara.service;

import com.ssafy.jara.dto.Report;

public interface ReportService {
	public int insertReport(Report report);
	
	public int countReport(String nickname);
	
	public int findAccusedId(String nickname);
}
