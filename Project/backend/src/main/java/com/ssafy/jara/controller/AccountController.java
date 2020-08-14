package com.ssafy.jara.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
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

import com.ssafy.jara.common.service.jwt.JwtService;
import com.ssafy.jara.common.weather.WeatherService;
import com.ssafy.jara.dto.Account;
import com.ssafy.jara.dto.Article;
import com.ssafy.jara.dto.Follow;
import com.ssafy.jara.dto.Tip;
import com.ssafy.jara.handler.MailHandler;
import com.ssafy.jara.service.AccountService;
import com.ssafy.jara.service.ArticleCommentService;
import com.ssafy.jara.service.ArticleService;
import com.ssafy.jara.service.TipCommentService;
import com.ssafy.jara.service.TipService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/jara/accounts")
public class AccountController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private ArticleCommentService articleCommentService;

	@Autowired
	private TipService tipService;

	@Autowired
	private TipCommentService tipCommentService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	WeatherService weatherService;
	

	@ApiOperation(value = "닉네임과 이메일 중복 체크하여 회원가입 처리", response = String.class)
	@PostMapping("signup")
	private ResponseEntity<String> signupAccount(@RequestBody Account account)
			throws MessagingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

		if (accountService.duplicateCheck(account) < 1) {
			// 비밀번호 암호화
//			String hashPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
//			account.setPassword(hashPassword);
//			
//			System.out.println("hashPassword : "+hashPassword);


			if (accountService.insertAccount(account) > 0) {
				
				// 6자리 인증코드
				Account reaccount = accountService.findAccount(account.getId());

//				System.out.println("code : "+reaccount.getCode());
//				System.out.println("nickname : "+reaccount.getNickname());

				MailHandler sendMail = new MailHandler(javaMailSender);
				sendMail.setSubject("[JARA 회원가입 사용자 인증]");
				sendMail.setText(new StringBuffer()
						.append("<center><h1 style='background-color:#388E3C; color:white;'>JARA 메일 인증 안내입니다</h1><br>" + 
								"<p>JARA를 이용해 주셔서 진심으로 감사합니다.<br>아래의 인증코드를 입력하시면 가입이 정상적으로 완료됩니다.</p><br>" + 
								"<h2 style='background-color:#e6e6e6; color:black;'>")
						.append("인증코드 : <b>" + reaccount.getCode() + "<br></h2>")
//						.append("<button type='button' onclick = 'location.href = 'http://localhost:3030/accounts/certification' ' " + 
//								"style='border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none; display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;background-color:#388E3C;'>인증하러가기</button></center>").toString());

						
//						 .append("<a href='http://localhost:3030/accounts/certification'>이메일 인증하기</a></center>").toString());
						.append("<a href='http://i3a308.p.ssafy.io/accounts/certification'>이메일 인증하기</a>").toString());

				sendMail.setFrom("jaraauth@gmail.com", "JARA");
				sendMail.setTo(account.getEmail());
				sendMail.send();

				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원가입 시 이메일 인증", response = String.class)
	@PostMapping("certification/{code}")
	private ResponseEntity<String> certification(@PathVariable String code) {

		if (accountService.changeStatus(code) > 0) {

			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT); //
	}


	@ApiOperation(value = "이메일과 비밀번호로 로그인 처리", response = Account.class)
	@PostMapping("signin")
	private ResponseEntity<Account> loginAccount(@RequestBody Account account, HttpServletResponse response) {
		
		// 비밀번호와 비교
//		Account findAccount =null;
//
//		if(BCrypt.checkpw(account.getPassword(),accountService.findPassword(account.getEmail()))) { // 기존 비밀번호와 같음
//			findAccount = accountService.selectAccount(account); // 로그인
//		}else {
//			findAccount = accountService.selectAccount(account); // 로그인
//		}

		Account findAccount= accountService.selectAccount(account); // 로그인
		
//		findAccount.setPTY(weatherService.selectPTY(findAccount.getLocation()));
//		findAccount.setSKY(weatherService.selectSKY(findAccount.getLocation()));
//		findAccount.setT1H(weatherService.selectT1H(findAccount.getLocation()));
		
		System.out.println("findAccount=" + findAccount);
		if (!findAccount.equals(null)) {
			String token = jwtService.create(findAccount);
			response.setHeader("jwt-auth-token", token);
			return new ResponseEntity<Account>(findAccount, HttpStatus.OK);
		} else {
			return new ResponseEntity<Account>(findAccount, HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "비밀번호 변경하기 전 인증 코드 발송", response = String.class)
	@PostMapping("changepwd")
	private void changePassword(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {

		// 인증코드 변경하기
		accountService.changeCode(email);
		String ncode = accountService.findCode(email);
		System.out.println(ncode);

		MailHandler sendMail = new MailHandler(javaMailSender);
		sendMail.setSubject("[JARA 비밀번호 변경 사용자 인증]");
		sendMail.setText(new StringBuffer()
				.append("<center><h1 style='background-color:#388E3C; color:white;'>JARA 메일 인증 안내입니다</h1><br>" + 
						"<p>JARA를 이용해 주셔서 진심으로 감사합니다.<br>아래의 인증코드를 입력하시면 비밀번호 변경이 가능합니다.</p><br>" + 
						"<h2 style='background-color:#e6e6e6; color:black;'>")
				.append("인증코드 : <b>" + ncode + "</b><br></h2>")
//				.append("<button type='button' onclick = 'location.href = 'http://localhost:3030/accounts/setnewpw' ' " + 
//						"style='border: none;color: white;padding: 15px 32px;text-align: center;text-decoration: none; display: inline-block;font-size: 16px;margin: 4px 2px;cursor: pointer;background-color:#388E3C;'>인증하러가기</button></center>").toString());

				
//				 .append("<a href='http://localhost:3030/accounts/certification'>이메일 인증하기</a></center>").toString());			
				.append("<a href='http://i3a308.p.ssafy.io/accounts/setnewpwd'>비밀번호 변경하기</a>").toString());


		sendMail.setFrom("jaraauth@gmail.com", "JARA");
		sendMail.setTo(email);
		sendMail.send();

	}

	@ApiOperation(value = "비밀번호 변경 처리", response = Account.class)
	@PutMapping("setnewpwd")
	private ResponseEntity<String> setNewPassword(@RequestBody Account account) {
		
		// 비밀번호 암호화
//		String hashPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
//		account.setPassword(hashPassword);
		
		if (accountService.changePassword(account) > 0) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	

	@ApiOperation(value = "회원넘버로 회원 정보 조회하기")
	@GetMapping("{id}")
	private ResponseEntity<Account> findAccount(@PathVariable int id) {
		
		Account account = accountService.findPartAccount(id); // 해당 id 유저 값

		account.setFollowerList(accountService.findFollowing(id));
		account.setFollowingList(accountService.findFollower(id));
		
		account.setMyArticleList(articleService.selectListMyArticle(id));
		
		for (int i = 0; i < account.getMyArticleList().size(); i++) {
			Article article = account.getMyArticleList().get(i);

			article.setComments(articleCommentService.selectArticleComments(article.getId())); // 전체 댓글 조회
			article.setLikeAccounts(articleService.selectArticleLikeAccount(article.getId())); // 전체 좋아요 사용자 조회
					
		}

		account.setScrapTipList(tipService.selectListTipScrap(id));
		
		for (int i = 0; i < account.getScrapTipList().size(); i++) {
			Tip tip = account.getScrapTipList().get(i);

			tip.setComments(tipCommentService.selectTipComments(tip.getId()));
			tip.setLikeAccounts(tipService.selectTipLikeAccounts(tip.getId()));
		}

		System.out.println(account.getId() + "+" + account.getNickname());
		
		if (account.equals(null) || account.getId() == 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@ApiOperation(value = "전체 회원 정보(유저) 조회하기")
	@GetMapping("")
	public ResponseEntity<List<Account>> findAllAccount() {
		List<Account> accounts = accountService.findAllAccount();
		if (accounts.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Account>>(accounts, HttpStatus.OK);
	}

	@ApiOperation(value = "회원 정보 수정하기")
	@PutMapping("{id}")
	private ResponseEntity<String> updateAccount(@RequestBody Account account) {
		boolean total = accountService.updateAccount(account);
		if (!total) {
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);

		}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@ApiOperation(value = "회원 정보 삭제하기")
	@DeleteMapping("{id}")
	private ResponseEntity<String> deleteAccount(@RequestBody Account account) {

		accountService.deleteAllFollow(account.getId()); // 팔로잉 팔로워일때 삭제

		if(accountService.deleteAccount(account.getId()) > 0) { // 회원 삭제
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
	}
	
	
	

	@ApiOperation(value = "팔로우 요청 보내기")
	@PostMapping("follow")
	public ResponseEntity<String> requestFollow(@RequestBody Follow follow) {

		if (accountService.findFollow(follow) > 0) { // 팔로우하는 경우 - 팔로우 취소
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);

		} else { // 팔로우하지 않은 경우 - 팔로우 추가
			if (accountService.insertFollow(follow) > 0) {
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}
			return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "팔로우 요청 승인하기")
	@PutMapping("follow")
	public ResponseEntity<String> approveFollow(@RequestBody Follow follow) {

		if (accountService.approveFollow(follow) > 0) { // 팔로우 돼있는 경우
			return new ResponseEntity<String>("success", HttpStatus.OK);

		} else { // 팔로우하지 않은 경우 - 팔로우 추가
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
	private ResponseEntity<Map<String, Object>> infoAccount(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();
		try {
			resultMap.putAll(jwtService.get(request.getHeader("token")));
			accountMap = (Map<String, Object>) resultMap.get("Account");
			String location = (String) accountMap.get("location");
			accountMap.put("PTY",weatherService.selectPTY(location));
			accountMap.put("SKY",weatherService.selectSKY(location));
			accountMap.put("T1H",weatherService.selectT1H(location));
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(accountMap, HttpStatus.OK);
	}

	@ApiOperation(value = "네이버 로그인")
	@GetMapping("/signin/naver")
	private ResponseEntity<String> loginNaver() throws UnsupportedEncodingException {
		String clientId = "y_9J6LuNu9tyN5tgnmEN";
//		String redirectURI = URLEncoder.encode("https://i3a308.p.ssafy.io/accounts/signin/naver/access", "UTF-8");
		String redirectURI = URLEncoder.encode("http://localhost:8081/jara/accounts/signin/naver/access", "UTF-8");
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		apiURL += "&client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&state=" + state;
		
		System.out.println(apiURL);
		
		return new ResponseEntity<String>(apiURL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "네이버 로그인 접근 토큰")
	@GetMapping("/signin/naver/access")
	private ResponseEntity<Account> accessTokenNaver(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String clientId = "y_9J6LuNu9tyN5tgnmEN";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "8bMro7T5Dt";// 애플리케이션 클라이언트 시크릿값";
		String code = request.getParameter("code");
		String state = request.getParameter("state");
//		String redirectURI = URLEncoder.encode("https://i3a308.p.ssafy.io/accounts/signin/naver", "UTF-8");
		String redirectURI = URLEncoder.encode("http://localhost:8081/jara/signin/naver", "UTF-8");
		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		
		String access_token = "";
		String refresh_token = "";
		
		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			
			System.out.println("responseCode=" + responseCode);
			
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			String inputLine;
			StringBuffer res = new StringBuffer();
			
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			
			if (responseCode == 200) {
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(res.toString());
				access_token = (String) obj.get("access_token");
				refresh_token = (String) obj.get("refresh_token");
				
				// 회원 프로필 조회
				String header = "Bearer " + access_token; // Bearer 다음에 공백 추가

				apiURL = "https://openapi.naver.com/v1/nid/me";

				Map<String, String> requestHeaders = new HashMap<>();
				requestHeaders.put("Authorization", header);
				String responseBody = get(apiURL, requestHeaders);

				JSONObject resBody = (JSONObject)parser.parse(responseBody);
				JSONObject resObj = (JSONObject) resBody.get("response");
				
				String nickname = URLDecoder.decode((String) resObj.get("nickname"));
				String sex = (String) resObj.get("gender");
				String email = (String) resObj.get("email");
				String birthday = (String) resObj.get("birthday");
				
				System.out.println(nickname + " " + sex + " " + email + " " + birthday);
				
				if(accountService.findEmail(email) > 0) {
					Account account = accountService.findPartAccount(accountService.findIdByEmail(email));
					
					if(!account.equals(null)) {
						String token = jwtService.create(account);
						response.setHeader("jwt-auth-token", token);
					}
					
					return new ResponseEntity<Account>(account, HttpStatus.OK); // 처음 소셜 로그인 사용자가 아닌 경우
				} 
				
				Account account = new Account();
				account.setNickname(nickname);
				account.setSex(sex.equals("F") ? true : false);
				account.setEmail(email);
				account.setAccess_token(access_token);
				account.setRefresh_token(refresh_token);
				
				accountService.insertNaverAccount(account);
				
				Account resultAccount = accountService.findPartAccount(account.getId());
				
				return new ResponseEntity<Account>(resultAccount, HttpStatus.OK); // 처음 소셜 로그인 사용자인 경우 -> 지역 입력 페이지로 가야한다!!!!!!
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "네이버 로그인으로 회원가입 시 주소 수정")
	@PutMapping("/signin/naver/access")
	private ResponseEntity<Account> updateNaverAccount(@RequestBody Account account) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("id", account.getId());
		hashMap.put("location", account.getLocation());
		
		if(accountService.updateNaverAccount(hashMap) > 0) {
			return new ResponseEntity<Account>(accountService.findPartAccount(account.getId()), HttpStatus.OK);
		}
		
		return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
	}
	
	
	/* 네이버 회원 프로필 조회에 필요한 메서드 */
	private static String get(String apiUrl, Map<String, String> requestHeaders) {
		HttpURLConnection con = connect(apiUrl);
		try {
			con.setRequestMethod("GET");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
				return readBody(con.getInputStream());
			} else { // 에러 발생
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	
}
