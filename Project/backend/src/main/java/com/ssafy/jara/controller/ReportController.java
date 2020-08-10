package com.ssafy.jara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.dto.Report;
import com.ssafy.jara.service.ReportService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/jara/reports")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@ApiOperation(value = "사용자 신고 등록", response = String.class)
	@PostMapping("")
	private ResponseEntity<String> insertBarter(@RequestBody Report report) {
		if (reportService.insertReport(report) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
		}
	}
}
