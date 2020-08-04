package com.ssafy.jara.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.jara.common.service.jwt.JwtService;
import com.ssafy.jara.dto.Account;
import com.ssafy.jara.dto.Follow;
import com.ssafy.jara.dto.Location;
import com.ssafy.jara.handler.MailHandler;
import com.ssafy.jara.service.AccountService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/accounts")
public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AccountService accountService;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	JavaMailSender javaMailSender;
	

//	@Async
//	public void sendMail(String email) {
//		SimpleMailMessage simpleMessage = new SimpleMailMessage();
//		simpleMessage.setFrom("lcy00707@gmail.com"); 
//		simpleMessage.setTo(email);
//		simpleMessage.setSubject("이메일 인증");
//		simpleMessage.setText("인증번호: 123456");
//		javaMailSender.send(simpleMessage);
//	}


	@ApiOperation(value = "닉네임과 이메일 중복 체크하여 회원가입 처리", response = String.class)
	@PostMapping("signup")
	private ResponseEntity<String> signupAccount(@RequestBody Account account) throws MessagingException, UnsupportedEncodingException {

		if (accountService.duplicateCheck(account) < 1) {
			if (accountService.insertAccount(account) > 0) {
//				sendMail(account.getEmail());
				
				//-5
//				MailHandler sendMail = new MailHandler(javaMailSender);
//				sendMail.setSubject("[이메일 인증]");
//				sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
//						.append("자라에 가입해주셔서 감사합니다.<br>" +account.getEmail())
//						.append("' target='_blenk'>이메일 인증 확인</a>").toString());
//				sendMail.setFrom("lcy00707@gmail.com","jara");
//				sendMail.setTo(account.getEmail());
//				sendMail.send();
				
				MailHandler sendMail = new MailHandler(javaMailSender);
				sendMail.setSubject("[이메일 인증]");
				sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
						.append("자라에 가입해주셔서 감사합니다.<br>" +account.getEmail())
						.append("<a href=http://i3a308.p.ssafy.io/>이메일 인증하기</a>").toString());

				sendMail.setFrom("lcy00707@gmail.com","jara");
				sendMail.setTo(account.getEmail());
				sendMail.send();
				
				
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
//	@ApiOperation(value = "이메일과 비밀번호로 로그인 처리", response = Account.class)
//	@PostMapping("signin")
//	private ResponseEntity<Account> loginAccount(@RequestBody Account account, HttpSession session) {
//		Account findAccount = accountService.selectAccount(account);
//
//		if (!findAccount.equals(null)) {
//			session.setAttribute("accountInfo", findAccount);
//		}
//
//		return new ResponseEntity<Account>(accountService.selectAccount(account), HttpStatus.OK);
//	}


	@ApiOperation(value = "이메일과 비밀번호로 로그인 처리", response = Account.class)
	@PostMapping("signin")
	private ResponseEntity<Account> loginAccount(@RequestBody Account account, HttpServletResponse response) {
		Account findAccount = accountService.selectAccount(account);
		System.out.println("findAccount=" + findAccount);
		if (findAccount != null) {
			String token = jwtService.create(findAccount);
			response.setHeader("jwt-auth-token", token);
			return new ResponseEntity<Account>(findAccount, HttpStatus.OK);
		} else {
			return new ResponseEntity<Account>(findAccount, HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "비밀번호 변경 처리", response = Account.class)
	@PutMapping("changepw")
	private ResponseEntity<String> changePassword(@RequestBody Account account) {
		if (accountService.changePassword(account) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원넘버로 회원 정보 조회하기")
	@GetMapping("{id}")
	private ResponseEntity<Account> findAccount(@PathVariable int id){
		Account account = accountService.findAccount(id); // 해당 id 유저 값
		
		account.setFollowerList(accountService.findFollowing(id));
		account.setFollowingList(accountService.findFollower(id));

		account.setX(accountService.findX(account.getLocation()));
		account.setY(accountService.findY(account.getLocation()));
		
		System.out.println(account.getId()+"+"+account.getNickname());
		if(account.equals(null) || account.getId()==0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}
	
	@ApiOperation(value = "전체 회원 정보(유저) 조회하기")
	@GetMapping("")
	public ResponseEntity<List<Account>> findAllAccount(){
		List<Account> accounts = accountService.findAllAccount();
		if(accounts.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원 정보 수정하기")
	@PutMapping("{id}")
	private ResponseEntity<String> updateAccount(@RequestBody Account account){
		boolean total = accountService.updateAccount(account);
		if(!total) {
			return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
			
		}
		return new ResponseEntity<String>("success",HttpStatus.OK);
	}
	
//	@ApiOperation(value = "이메일 보내기")
//	@GetMapping("email")
//	public ResponseEntity<Boolean> findEmail(@RequestParam String email) throws MessagingException, UnsupportedEncodingException{
//		
//		MailHandler sendMail = new MailHandler(javaMailSender);
//		sendMail.setSubject("[이메일 인증]");
//		sendMail.setText(new StringBuffer().append("<h1>메일인증</h1>")
//				.append("자라에 가입해주셔서 감사합니다.<br>" +email)
//				.append("<a href=http://i3a308.p.ssafy.io/>이메일 인증하기</a>").toString());
//
//		sendMail.setFrom("lcy00707@gmail.com","jara");
//		sendMail.setTo(email);
//		sendMail.send();
//		
//		return new ResponseEntity<Boolean>(accountService.findEmail(email) > 0 , HttpStatus.OK);
//	}
	
//	@ApiOperation(value = "전체 팔로우 조회하기")
//	@GetMapping("follow")
//	public ResponseEntity<List<Follow>> findAllFollow(){
//		return new ResponseEntity<List<Follow>>(accountService.findAllFollow(),HttpStatus.OK);
//	}
	
//	@ApiOperation(value = "해당 사용자(following)가 다른 사용자(follower)를 팔로잉하는 중인지 조회")
//	@GetMapping("follow/{following}/{follower}")
//	public ResponseEntity<Boolean> findFollow(@PathVariable("following") int following, @PathVariable("follower") int follower) {
//		Follow follow = new Follow();
//		follow.setFollowing(following);
//		follow.setFollower(follower);
//		
//		return new ResponseEntity<Boolean>(accountService.findFollow(follow) > 0, HttpStatus.OK);
//	}
	
//	@ApiOperation(value = "팔로우 여부 확인 후 팔로우 취소/추가")
//	@PostMapping("follow")
//	public ResponseEntity<String> setFollow(@RequestBody Follow follow) {
//		if(accountService.findFollow(follow) > 0) { // 이미 팔로우하는 경우 - 팔로우 취소
//			if(accountService.deleteFollow(follow) > 0) {
//				return new ResponseEntity<String>("success", HttpStatus.OK);
//			}
//			
//			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
//		
//		} else { // 아직 팔로우하지 않은 경우 - 팔로우 추가
//			if(accountService.insertFollow(follow) > 0) { 
//				return new ResponseEntity<String>("success", HttpStatus.OK);
//			}
//			
//			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
//		}
//	}
	
	@ApiOperation(value = "팔로우 요청 보내기")
	@PostMapping("follow")
	public ResponseEntity<String> requestFollow(@RequestBody Follow follow) {
		
		if(accountService.findFollow(follow) > 0) { //  팔로우하는 경우 - 팔로우 취소
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		
		} else { //  팔로우하지 않은 경우 - 팔로우 추가
			if(accountService.insertFollow(follow) > 0) { 
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "팔로우 요청 승인하기")
	@PutMapping("follow")
	public ResponseEntity<String> approveFollow(@RequestBody Follow follow) {
		
		if(accountService.approveFollow(follow) > 0) { //  팔로우 돼있는 경우
			return new ResponseEntity<String>("success", HttpStatus.OK);
		
		} else { //  팔로우하지 않은 경우 - 팔로우 추가
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "언팔로우 하기")
	@DeleteMapping("follow")
	public ResponseEntity<List<Integer>> Unfollow(@RequestBody Follow follow) {
	
		accountService.deleteFollow(follow); // 팔로우 정보 지움 = 언팔로우
	      
	    List<Integer> followerList = accountService.findFollowing(follow.getFollowing());
	      
	    return new ResponseEntity<List<Integer>>(followerList, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "토큰에 해당하는 회원정보 조회")
	@GetMapping("/info")
	private ResponseEntity<Map<String, Object>> infoAccount(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.putAll(jwtService.get(request.getHeader("token")));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String,Object>>((Map<String, Object>) resultMap.get("Account"), HttpStatus.OK);
	}
	
//	@ApiOperation(value = "전체 위치 조회하기")
//	@GetMapping("location")
//	public ResponseEntity<List<Location>> findAllLocation(){
//		List<Location> locations = accountService.findAllLocation();
//		if(locations.isEmpty()) {
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<Location>>(locations, HttpStatus.OK);
//	}
//	
//	@ApiOperation(value = "구이름으로  x 조회하기")
//	@GetMapping("location/{name}")
//	private ResponseEntity<Double> findX(@PathVariable String name){
//		
//		double x = accountService.findX(name);
//
//		return new ResponseEntity<Double>(x, HttpStatus.OK);
//	}

}
