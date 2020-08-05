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
