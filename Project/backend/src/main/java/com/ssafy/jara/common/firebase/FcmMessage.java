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
