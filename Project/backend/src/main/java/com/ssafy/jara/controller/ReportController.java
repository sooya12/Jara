package com.ssafy.jara.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.common.service.jwt.JwtService;
import com.ssafy.jara.dto.Report;
import com.ssafy.jara.service.AccountService;
import com.ssafy.jara.service.ReportService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/jara/reports")
public class ReportController {

	@Autowired
	ReportService reportService;

	@Autowired
	AccountService accountService;
	
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "사용자 신고 등록", response = String.class)
	@PostMapping("")
	private ResponseEntity<String> insertReport(@RequestBody Report report) {

		if (reportService.insertReport(report) > 0) { // 신고테이블에 추가

			// 신고 테이블에 같은 아이디 값이 3회 이상 이면 회원 탈퇴
//			if(reportService.countReport(report.getAccused_nickname())>=3) {
//				System.out.println("count 3이상");
//				
//				int id = reportService.findAccusedId(report.getAccused_nickname());
//				System.out.println("id : "+id);

			// 회원 탈퇴시키기
//				accountService.deleteAllFollow(id); // 팔로잉 팔로워일때 삭제
//				
//				if(accountService.deleteAccount(id) > 0) { // 회원 삭제
//					return new ResponseEntity<String>("success", HttpStatus.OK);
//				}
//				
//			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);

	}

	@ApiOperation(value = "관리자 페이지 - 신고 리스트", response = String.class)
	@GetMapping("admin")
	private ResponseEntity<List<Report>> selectListReport(@RequestHeader(value="token") String token) {
		Map<String, Object> tokenMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();
		
		System.out.println("token: " + token);
		
		tokenMap.putAll(jwtService.get(token));
		accountMap = (Map<String, Object>) tokenMap.get("Account");
		String nickname = (String) accountMap.get("nickname");
		
		if (nickname.equals("관리자")) {
			List<Report> report = reportService.selectListReport();
			if (!reportService.selectListReport().equals(null)) {
				System.out.println(report);
				return new ResponseEntity<List<Report>>(report, HttpStatus.OK);
			} else {
				System.out.println("내역이 없습니다.");
				return new ResponseEntity<List<Report>>(HttpStatus.NO_CONTENT);
			}
		} else {
			System.out.println("Error: 권한이 없습니다.");
			return new ResponseEntity<List<Report>>(HttpStatus.FORBIDDEN);
		}
	}
	
	@ApiOperation(value = "관리자 페이지 - 계정 삭제", response = String.class)
	@DeleteMapping("admin")
	private ResponseEntity<List<Report>> deleteAccount(@RequestBody Report report, @RequestHeader(value="token") String token) {
		Map<String, Object> tokenMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();
		
		System.out.println("token: " + token);
		
		tokenMap.putAll(jwtService.get(token));
		accountMap = (Map<String, Object>) tokenMap.get("Account");
		String nickname = (String) accountMap.get("nickname");
		
		if (nickname.equals("관리자")) {
			int id = reportService.findAccusedId(report.getAccused_nickname());
			
			accountService.deleteAllFollow(id); // 팔로잉 팔로워일때 삭제
					
			if(accountService.deleteAccount(id) > 0) { // 회원 삭제
			
				return new ResponseEntity<List<Report>>(reportService.selectListReport(), HttpStatus.OK);
			} else {
				return new ResponseEntity<List<Report>>(HttpStatus.BAD_REQUEST);
			}
		} else {
			System.out.println("Error: 권한이 없습니다.");
			return new ResponseEntity<List<Report>>(HttpStatus.FORBIDDEN);
		}
	}
}
