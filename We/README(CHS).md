

### :feet: 20.08.03

##### Firebase Cloud Messaging에 대해 공부

> Spring boot에서 토큰과 로직을 처리하고 Vue.js에서 알림을 줄 수 있도록
>
> - https://firebase.google.com/docs/cloud-messaging
> - [https://velog.io/@skygl/FCM-Spring-Boot%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%EC%9B%B9-%ED%91%B8%EC%8B%9C-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0](https://velog.io/@skygl/FCM-Spring-Boot를-사용하여-웹-푸시-기능-구현하기)
>
> Firebase에 project 생성
>
> Admin SDK 구성 스니펫 비공개 키 생성
>
> Spring boot에 관련 java 코드 구현

-----



### :feet: 20.08.04

##### Firebase Clude Messaging을 구현하려 노력

##### https://firebase.google.com/docs/cloud-messaging/migrate-v1#windows 에서 제공해준 코드를 이용하였으나, 구현 실패

``` bash
package com.ssafy.jara.common.firebase;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Messaging {
	private static final String PROJECT_ID = "fcm-jara";
	private static final String BASE_URL = "https://fcm.googleapis.com";
	private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

	private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
	private static final String[] SCOPES = { MESSAGING_SCOPE };

	private static final String TITLE = "새로운 알림이 도착!";
	private static final String BODY = "새로운 게시물/팁이 등록되었어요! 어서 가보세요!";
	public static final String MESSAGE_KEY = "message";
	
	private static final String GOOGLE_APPLICATION_CREDENTIALS = "jara-fcm-firebase-adminsdk-68twq-d0e84cbd44.json";

	/**
	 * Retrieve a valid access token that can be use to authorize requests to the
	 * FCM REST API.
	 *
	 * @return Access token.
	 * @throws IOException
	 */
	// [START retrieve_access_token]
	private static String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential.fromStream(new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS).getInputStream()) // jara-fcm-firebase-adminsdk-68twq-d0e84cbd44.json
				.createScoped(Arrays.asList(SCOPES));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();
	}
	// [END retrieve_access_token]

	/**
	 * Create HttpURLConnection that can be used for both retrieving and publishing.
	 *
	 * @return Base HttpURLConnection.
	 * @throws IOException
	 */
	private static HttpURLConnection getConnection() throws IOException {
		// [START use_access_token]
		URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
		return httpURLConnection;
		// [END use_access_token]
	}

	/**
	 * Send request to FCM message using HTTP.
	 *
	 * @param fcmMessage Body of the HTTP request.
	 * @throws IOException
	 */
	private static void sendMessage(JsonObject fcmMessage) throws IOException {
		HttpURLConnection connection = getConnection();
		connection.setDoOutput(true);
		DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
		outputStream.writeBytes(fcmMessage.toString());
		outputStream.flush();
		outputStream.close();

		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			String response = inputstreamToString(connection.getInputStream());
			System.out.println("Message sent to Firebase for delivery, response:");
			System.out.println(response);
		} else {
			System.out.println("Unable to send message to Firebase:");
			String response = inputstreamToString(connection.getErrorStream());
			System.out.println(response);
		}
	}

	/**
	 * Send a message that uses the common FCM fields to send a notification message
	 * to all platforms. Also platform specific overrides are used to customize how
	 * the message is received on Android and iOS.
	 *
	 * @throws IOException
	 */
	private static void sendOverrideMessage() throws IOException {
		JsonObject overrideMessage = buildOverrideMessage();
		System.out.println("FCM request body for override message:");
		prettyPrint(overrideMessage);
		sendMessage(overrideMessage);
	}

	/**
	 * Build the body of an FCM request. This body defines the common notification
	 * object as well as platform specific customizations using the android and apns
	 * objects.
	 *
	 * @return JSON representation of the FCM request body.
	 */
	private static JsonObject buildOverrideMessage() {
		JsonObject jNotificationMessage = buildNotificationMessage();

		JsonObject messagePayload = jNotificationMessage.get(MESSAGE_KEY).getAsJsonObject();
		messagePayload.add("android", buildAndroidOverridePayload());

		JsonObject apnsPayload = new JsonObject();
		apnsPayload.add("headers", buildApnsHeadersOverridePayload());
		apnsPayload.add("payload", buildApsOverridePayload());

		messagePayload.add("apns", apnsPayload);

		jNotificationMessage.add(MESSAGE_KEY, messagePayload);

		return jNotificationMessage;
	}

	/**
	 * Build the android payload that will customize how a message is received on
	 * Android.
	 *
	 * @return android payload of an FCM request.
	 */
	private static JsonObject buildAndroidOverridePayload() {
		JsonObject androidNotification = new JsonObject();
		androidNotification.addProperty("click_action", "android.intent.action.MAIN");

		JsonObject androidNotificationPayload = new JsonObject();
		androidNotificationPayload.add("notification", androidNotification);

		return androidNotificationPayload;
	}

	/**
	 * Build the apns payload that will customize how a message is received on iOS.
	 *
	 * @return apns payload of an FCM request.
	 */
	private static JsonObject buildApnsHeadersOverridePayload() {
		JsonObject apnsHeaders = new JsonObject();
		apnsHeaders.addProperty("apns-priority", "10");

		return apnsHeaders;
	}

	/**
	 * Build aps payload that will add a badge field to the message being sent to
	 * iOS devices.
	 *
	 * @return JSON object with aps payload defined.
	 */
	private static JsonObject buildApsOverridePayload() {
		JsonObject badgePayload = new JsonObject();
		badgePayload.addProperty("badge", 1);

		JsonObject apsPayload = new JsonObject();
		apsPayload.add("aps", badgePayload);

		return apsPayload;
	}

	/**
	 * Send notification message to FCM for delivery to registered devices.
	 *
	 * @throws IOException
	 */
	public static void sendCommonMessage() throws IOException {
		JsonObject notificationMessage = buildNotificationMessage();
		System.out.println("FCM request body for message using common notification object:");
		prettyPrint(notificationMessage);
		sendMessage(notificationMessage);
	}

	/**
	 * Construct the body of a notification message request.
	 *
	 * @return JSON of notification message.
	 */
	private static JsonObject buildNotificationMessage() {
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", TITLE);
		jNotification.addProperty("body", BODY);

		JsonObject jMessage = new JsonObject();
		jMessage.add("notification", jNotification);
		jMessage.addProperty("topic", "news");

		JsonObject jFcm = new JsonObject();
		jFcm.add(MESSAGE_KEY, jMessage);

		return jFcm;
	}

	/**
	 * Read contents of InputStream into String.
	 *
	 * @param inputStream InputStream to read.
	 * @return String containing contents of InputStream.
	 * @throws IOException
	 */
	private static String inputstreamToString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		Scanner scanner = new Scanner(inputStream);
		while (scanner.hasNext()) {
			stringBuilder.append(scanner.nextLine());
		}
		return stringBuilder.toString();
	}

	/**
	 * Pretty print a JsonObject.
	 *
	 * @param jsonObject JsonObject to pretty print.
	 */
	private static void prettyPrint(JsonObject jsonObject) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(jsonObject) + "\n");
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 1 && args[0].equals("common-message")) {
			sendCommonMessage();
		} else if (args.length == 1 && args[0].equals("override-message")) {
			sendOverrideMessage();
		} else {
			System.err.println("Invalid command. Please use one of the following commands:");
			// To send a simple notification message that is sent to all platforms using the
			// common
			// fields.
			System.err.println("./gradlew run -Pmessage=common-message");
			// To send a simple notification message to all platforms using the common
			// fields as well as
			// applying platform specific overrides.
			System.err.println("./gradlew run -Pmessage=override-message");
		}
	}
}
```

##### Firebase 사용이 쉽다고 하였는데, 쉽지 않았음



##### 유저 페이지 기능 추가

>Account Dto에 myArticleList(유저가 작성한 게시글(피드)), scrapTipList(스크랩한 팁) 추가
>
>/account/{user_id} GET 요청 시, List들을 같이 넘겨 줌

-----



### :feet: 20.08.05

##### Firebase Clude Messaging을 구현하기 위해 다시 도전

###### pom.xml

```bash
<!-- Firebase -->
<dependency>
	<groupId>com.google.firebase</groupId>
	<artifactId>firebase-admin</artifactId>
	<version>6.8.1</version>
</dependency>
		
<!-- OKHttp 3 -->
<dependency>
	<groupId>com.squareup.okhttp3</groupId>
	<artifactId>okhttp</artifactId>
	<version>4.2.2</version>
</dependency>

```



###### FcmMessage.java

```b
package com.ssafy.jara.common.firebase;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FcmMessage {

	private boolean validate_only;
	private Message message;
	
	public FcmMessage() {}
	
	public FcmMessage(boolean validate_only, Message message) {
		this.validate_only = validate_only;
		this.message = message;
	}
	
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Message {
		private Notification notification;
		private String token;
		
		public Message() {}
		
		public Message(Notification notification, String token) {
			this.notification = notification;
			this.token = token;
		}
	}
	
	@AllArgsConstructor
	@Getter
	@Setter
	public static class Notification {
		private String title;
		private String body;
		private String image;
		
		public Notification() {}
		
		public Notification(String title, String body, String image) {
			this.title = title;
			this.body = body;
			this.image = image;
		}
	}

}
```



###### FcmService.java

```b
package com.ssafy.jara.common.firebase;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.JsonObject;
import com.ssafy.jara.common.firebase.FcmMessage.Message;
import com.ssafy.jara.common.firebase.FcmMessage.Notification;

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@Component
@RequiredArgsConstructor
public class FcmService {
	
	public final String API_URL = "https://fcm.googleapis.com/v1/projects/jara-fcm/messages:send";
	
	public void sendMessageTo(String targetToken, String title, String body) throws IOException {
//		String message = makeMessage(targetToken, title, body);
		
		JsonObject jNotification = new JsonObject();
		jNotification.addProperty("title", title);
		jNotification.addProperty("body", body);

		JsonObject jMessage = new JsonObject();
		jMessage.add("notification", jNotification);
//		jMessage.addProperty("topic", "news");
		jMessage.addProperty("token", targetToken);

		JsonObject jFcm = new JsonObject();
		jFcm.add("message", jMessage);
		
		System.out.println(jFcm);
		
		OkHttpClient client = new OkHttpClient();
//		RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
		RequestBody requestBody = RequestBody.create(jFcm.toString(), MediaType.get("application/json; charset=utf-8"));
		
		Request request = new Request.Builder()
									.url(API_URL)
									.post((okhttp3.RequestBody) requestBody)
									.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
									.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
									.build();
		
		Response response = client.newCall(request).execute();
		
		System.out.println(response.body().string());
	}
	
	private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
//		FcmMessage fcmMessage = FcmMessage.builder()
//									.message(FcmMessage.Message.builder()
//									.token(targetToken)
//									.notification(FcmMessage.Notification.builder()
//										.title(title)
//										.body(body)
//										.image(null)
//										.build()
//									)
//									.build()
//									)
//									.validate_only(false)
//									.build();
		
		Notification notification = new Notification(title, body, null);
		Message message = new Message(notification, targetToken);
		FcmMessage fcmMessage = new FcmMessage(true, message); 
		
		return objectMapper.writeValueAsString(fcmMessage);
	}

	private String getAccessToken() throws IOException {
		String GOOGLE_APPLICATION_CREDENTIALS = "jara-fcm-firebase-adminsdk-68twq-d0e84cbd44.json";
		
		// GoogleCredentials : GoogleApi 사용을 위한 oauth2를 이용해 인증한 대상을 나타내는 객체
		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(GOOGLE_APPLICATION_CREDENTIALS).getInputStream()) // json 객체의 인스턴스를 얻음
				.createScoped(List.of("https://www.googleapis.com/auth/cloud-platform")); // 인증하는 서버에서 필요로 하는 권한 지정
		
		googleCredentials.refreshIfExpired(); // AccessToken 생성
		
		return googleCredentials.getAccessToken().getTokenValue(); // getAccessToken()으로 토큰을 받아옴 / getTokenValue()로 토큰을 얻음
	}
}
```



##### AticleController에서 sendMessageTo()를 호출하여 메시지를 보내보았으나, 토큰이 denied 되어서 실패



##### Tips 댓글 등록 시, DB에 등록하고 해당 댓글 객체 프론트로 전송

##### Tips 상세 조회 시, 좋아하는 user_id 목록 및 댓글 목록 추가 전송

##### Tips 좋아요 기능 구현 변경



##### 비록 FCM을 구현하지 못하였지만, Lombok에 대해서 알게 되었다.

-----



### :feet: 20.08.07

##### Naver Lucy XSS Filter를 이용하여 XSS 보안 강화

>src/main/resources/lucy-xss-servlet-filter-rule.xml

```bash
<?xml version="1.0" encoding="UTF-8"?>

<config xmlns="http://www.navercorp.com/lucy-xss-servlet">
    <defenders>
        <!-- XssPreventer 등록 -->
        <defender>
            <name>xssPreventerDefender</name>
            <class>com.navercorp.lucy.security.xss.servletfilter.defender.XssPreventerDefender</class>
        </defender>

        <!-- XssSaxFilter 등록 -->
        <defender>
            <name>xssSaxFilterDefender</name>
            <class>com.navercorp.lucy.security.xss.servletfilter.defender.XssSaxFilterDefender</class>
            <init-param>
                <param-value>lucy-xss-sax.xml</param-value>   <!-- lucy-xss-filter의 sax용 설정파일 -->
                <param-value>false</param-value>        <!-- 필터링된 코멘트를 남길지 여부, 성능 효율상 false 추천 -->
            </init-param>
        </defender>

        <!-- XssFilter 등록 -->
        <defender>
            <name>xssFilterDefender</name>
            <class>com.navercorp.lucy.security.xss.servletfilter.defender.XssFilterDefender</class>
            <init-param>
                <param-value>lucy-xss.xml</param-value>    <!-- lucy-xss-filter의 dom용 설정파일 -->
                <param-value>false</param-value>         <!-- 필터링된 코멘트를 남길지 여부, 성능 효율상 false 추천 -->
            </init-param>
        </defender>
    </defenders>

    <!-- default defender 선언, 필터링 시 지정한 defender가 없으면 여기 정의된 default defender를 사용해 필터링 한다. -->
    <default>
        <defender>xssPreventerDefender</defender>
    </default>

</config>
```

>pom.xml

```bash
<!-- Lucy XSS Filter -->
<dependency>
	<groupId>com.navercorp.lucy</groupId>
	<artifactId>lucy-xss-servlet</artifactId>
	<version>2.0.0</version>
</dependency>
```



##### 그러나 Lucy XSS Filter는 @RequestBody로 들어오는 JSON 객체를 필터링 해주지 않음. 그래서 따로 필터를 구현 - JsonFilter

> com/ssafy/jara/config/FilterConfig.java

```bash
package com.ssafy.jara.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import com.ssafy.jara.filter.JsonFilter;

@Configuration
public class FilterConfig implements WebMvcConfigurer {

	// Naver Lucy XSS Filter bean 등록
	@Bean
    public FilterRegistrationBean<XssEscapeServletFilter> getFilterRegistraionBean() {
    	FilterRegistrationBean<XssEscapeServletFilter> registraionBean = new FilterRegistrationBean<XssEscapeServletFilter>();
    	registraionBean.setFilter(new XssEscapeServletFilter());
    	registraionBean.setOrder(1);
    	registraionBean.addUrlPatterns("/*");
    	
    	return registraionBean;
    }
	
	// Json XSS Filter bean 등록
	@Bean
	public FilterRegistrationBean<JsonFilter> getJsonFilterRegistrationBean() {
		FilterRegistrationBean<JsonFilter> registrationBean = new FilterRegistrationBean<JsonFilter>(new JsonFilter());
		registrationBean.setOrder(2);
		registrationBean.addUrlPatterns("/*");
		
		return registrationBean;
	}
	
}
```

>com/ssafy/jara/filter/JsonFilter.java
>
><script></script> , #{, #{}, ${, ${} 등 특정 문자열만 필터링 

```bash
package com.ssafy.jara.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JsonFilter extends OncePerRequestFilter {
	
	// Json value XSS Filter를 위한 필터링 값 리스트
	public static ArrayList<String> checkList = new ArrayList<String>(Arrays.asList("<script>", "</script>", "#{", "#{}", "${", "${}"));   
	
	@Override
	protected void doFilterInternal(
		HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
		throws ServletException, IOException {

		chain.doFilter(new SimpleXssFilterInternal(req), resp);
	}
	
	public static class SimpleXssFilterInternal extends HttpServletRequestWrapper {
		
		private byte[] body;
		
		public SimpleXssFilterInternal(HttpServletRequest request) {
			super(request);
			
			try {
				InputStream is = request.getInputStream();
				
				if(is != null) {
					StringBuffer sb = new StringBuffer();
					
					while(true) {
						int data = is.read();
						
						if(data < 0) 
							break;
						
						sb.append((char) data);
					}
					
					String result = doWork(sb.toString());
					
					body = result.getBytes(StandardCharsets.UTF_8);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public ServletInputStream getInputStream() throws IOException {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
			
			return new ServletInputStream() {
				
				@Override
				public int read() throws IOException {
					return byteArrayInputStream.read();
				}
				
				@Override
				public void setReadListener(ReadListener listener) {}
				
				@Override
				public boolean isReady() {
					return true;
				}
				
				@Override
				public boolean isFinished() {
					return byteArrayInputStream.available() == 0;
				}
			};
		}
		
		private String doWork(String input) {
			
			for (int i = 0; i < checkList.size(); i++) {
				String s = checkList.get(i);
				
				if(input.indexOf(s) >= 0) {
					input = input.replaceAll(s, StringEscapeUtils.escapeHtml4(s));
				}
			}
			
			return input;
		}
	}
	
}

```

-----



### :feet: 20.08.07

##### Json Filter 사용 시, 한글 깨짐 현상 해결

```bash
package com.ssafy.jara.filter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class JsonFilter extends OncePerRequestFilter {
	
	protected static Log log = LogFactory.getLog(JsonFilter.class);
	
	// Json value XSS Filter를 위한 필터링 값 리스트
	public static ArrayList<String> checkList = new ArrayList<String>(Arrays.asList("<script>", "</script>", "#{", "#{}", "${", "${}"));   
	
	@Override
	protected void doFilterInternal(
		HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
		throws ServletException, IOException {

		chain.doFilter(new SimpleXssFilterInternal(req), resp);
	}
	
	public static class SimpleXssFilterInternal extends HttpServletRequestWrapper {
		
		private byte[] body;
		
		public SimpleXssFilterInternal(HttpServletRequest request) {
			super(request);
			
			try {
				
				InputStream is = request.getInputStream();
				
				InputStreamReader isr = new InputStreamReader(is, "UTF-8"); // 한글 깨짐 해결
				
				if(isr != null) {
					StringBuffer sb = new StringBuffer();
					
					while(true) {
						int data = isr.read();
						
						if(data < 0) 
							break;
						
						sb.append((char) data);
					}
					
					String result = doWork(sb.toString());
					body = result.getBytes(StandardCharsets.UTF_8);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public ServletInputStream getInputStream() throws IOException {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.body);
			
			return new ServletInputStream() {
				
				@Override
				public int read() throws IOException {
					return byteArrayInputStream.read();
				}
				
				@Override
				public void setReadListener(ReadListener listener) {}
				
				@Override
				public boolean isReady() {
					return true;
				}
				
				@Override
				public boolean isFinished() {
					return byteArrayInputStream.available() == 0;
				}
			};
		}
		
		private String doWork(String input) {
			
			for (int i = 0; i < checkList.size(); i++) {
				String s = checkList.get(i);
				
				if(input.indexOf(s) >= 0) {
					input = input.replaceAll(s, StringEscapeUtils.escapeHtml4(s));
				} 
			}
						
			return input;
		}
	}
	
}
```

에서

```bash
InputStream is = request.getInputStream();
				
InputStreamReader isr = new InputStreamReader(is, "UTF-8"); // 한글 깨짐 해결
```

이 부분의 코드로 해결



##### Sub3 1차 평가

- 발표자를 맡게 되서 발표 준비
  - 기존 PPT에 내용을 삭제하고 추가해서 compact한 PPT 자료 준비
  - AWS 서버를 사용한 시연 준비
- PPT 자료로 최종 기획안 / 이번주 구현 내용 / AWS 배포 주기 발표 후 시연

-----



### :feet: 20.08.10

##### Article, Either Comment 수정 시 반환값 수정

##### Location 테이블에 격자 위도/경도 (nx/ny) 정보 추가

##### 기상청 동네기상예보 api 연동

```bash
package com.ssafy.jara.common.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

	@GetMapping("")
	public String getWeather() throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst"); /*URL*/
//		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=서비스키"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("DysdYWgqnU55g9Q7PoDso1H3%2BQJFLffwk4YkWWCS4cSluMp9qKnSyq0J0u1PXFITKErf1yqWK%2FrwEUKlHTVePw%3D%3D", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + "DysdYWgqnU55g9Q7PoDso1H3%2BQJFLffwk4YkWWCS4cSluMp9qKnSyq0J0u1PXFITKErf1yqWK%2FrwEUKlHTVePw%3D%3D"); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("40", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON)Default: XML*/
        
        Calendar cal = Calendar.getInstance(Locale.KOREA);
//        System.out.println(cal);
//        System.out.println(cal.get(cal.YEAR));
//        System.out.println(cal.get(cal.MONTH) + 1);
//        System.out.println(cal.get(cal.DATE));
//        System.out.println(cal.get(cal.HOUR_OF_DAY));
        
        String year = String.valueOf(cal.get(cal.YEAR));
        String month = String.valueOf(cal.get(cal.MONTH) + 1);
        String day = String.valueOf(cal.get(cal.DATE));
//        String time = String.valueOf(cal.get(cal.HOUR_OF_DAY)); 
        
//        System.out.println(year + ((month.length() < 2)? "0" + month : month) + day);
//        System.out.println((time.length() < 2 ? "0" + time : time) + "00");
        
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(year + ((month.length() < 2)? "0" + month : month) + day, "UTF-8")); /*현재날짜(20200810) 발표*/
//        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode((time.length() < 2 ? "0" + time : time) + "00", "UTF-8")); /*현재시각 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode("1750", "UTF-8")); /*현재시각 발표(정시단위)*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("61", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("129", "UTF-8")); /*예보지점 Y 좌표*/
       
        System.out.println(urlBuilder.toString());
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        return sb.toString();
	}
}
```

##### Schedule 기능 추가

```bash
package com.ssafy.jara.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.setThreadNamePrefix("my-scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
	}

}
```

```bash
package com.ssafy.jara.common.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherServer extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(WeatherServer.class, args);
	}
}
```



### :sweat_smile: 어려웠던 점

##### 공공데이터포털에서 Service Key를 UTF-8로 인코딩한 값을 줘서 SERVICE ERROR SERVICE_KEY_IS_NOT_REGISTERED_ERROR 30 발생

##### api 호출 시, 위도/경도 값이 격자 위도/경도라서 기존 Location 테이블 위도/경도 값과 맞지 않음

##### Spring boot Schedule에 대한 구현 경험 부족

-----





