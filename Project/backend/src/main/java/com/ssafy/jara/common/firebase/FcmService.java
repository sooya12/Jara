package com.ssafy.jara.common.firebase;

import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.Logger;
import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushNotification;

@Service
public class FcmService {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(FcmService.class);
	
	public void send(final NotificationRequest notificationRequest) throws InterruptedException, ExecutionException {
		Message message = Message.builder()
				.setToken(notificationRequest.getToken())
				.setWebpushConfig(WebpushConfig.builder().putHeader("ttl", "300")
						.setNotification(new WebpushNotification(notificationRequest.getTitle(), notificationRequest.getMessage()))
						.build())
				.build();
		
		String response = FirebaseMessaging.getInstance().sendAsync(message).get();
		logger.info("Send message : " + response);
	}
}
