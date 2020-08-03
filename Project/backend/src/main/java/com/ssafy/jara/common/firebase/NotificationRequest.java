package com.ssafy.jara.common.firebase;

public class NotificationRequest {

	private String token;
	private String title;
	private String message;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "NotificationRequest [token=" + token + ", title=" + title + ", message=" + message + "]";
	}
	
}
