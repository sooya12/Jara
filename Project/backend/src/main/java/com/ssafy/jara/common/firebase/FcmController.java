package com.ssafy.jara.common.firebase;

import java.io.FileInputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

@RestController
@RequestMapping("/fcm")
public class FcmController {

	@GetMapping("")
	public void fcm(HttpServletRequest request) throws Exception {
		try {
			String path = "jara-fcm-firebase-adminsdk-68twq-d0e84cbd44.json";
			String message_scope = "https://www.googleapis.com/auth/firebase.messaging";
			String[] scopes = {message_scope};
			
			GoogleCredential googleCredential = GoogleCredential.fromStream(new FileInputStream(path)).createScoped(Arrays.asList(scopes));
			googleCredential.refreshToken();
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "Bearer " + googleCredential.getAccessToken());
			
			JSONObject notification = new JSONObject();
			notification.put("title", "test title");
			notification.put("body", "test body");
			
			JSONObject message = new JSONObject();
			message.put("token", "AAAAY1-4pBQ:APA91bEfaIPl9xYtoNk2mR3ICt1K6fMyV_blqGMGsu3KMblVkzZK3UQUDgtUVqgNFyYJN2wWbbAmdQjL-wE6dTX3BUuVFoUMsDs_4rPNOrg4KHS5TwjUA_iHwnncAZT3dcz2YJE4YiWL");
			message.put("notification", notification);
			
			JSONObject jsonParams = new JSONObject();
			jsonParams.put("message", message);
			
			HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
			RestTemplate rt = new RestTemplate();
			
			ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/jara-fcm/messages:send", HttpMethod.POST, httpEntity, String.class);
			
			if(res.getStatusCode() != HttpStatus.OK) {
				System.out.println("FCM-Exception");
				System.out.println(res.getStatusCode().toString());
				System.out.println(res.getHeaders().toString());
				System.out.println(res.getBody().toString());
			} else {
				System.out.println(res.getStatusCode().toString());
				System.out.println(res.getHeaders().toString());
				System.out.println(res.getBody().toLowerCase());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
