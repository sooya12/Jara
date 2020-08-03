package com.ssafy.jara.common.firebase;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FcmInitializer {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(FcmInitializer.class);
	private static final String FIREBASE_CONFIG_PATH = "jara-fcm-firebase-adminsdk-68twq-d0e84cbd44.json";
	
	@PostConstruct
	public void initialize() {
		try {
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(new ClassPathResource(FIREBASE_CONFIG_PATH).getInputStream())).build(); // 비밀키 증명
			
			if(FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
				logger.info("Firebase application has been initialized");
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
