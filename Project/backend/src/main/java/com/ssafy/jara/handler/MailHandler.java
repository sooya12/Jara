package com.ssafy.jara.handler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHandler {
	private JavaMailSender sender;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;

	// MailHandler 생성자
	public MailHandler(JavaMailSender jSender) throws MessagingException {
		this.sender = jSender;
		message = jSender.createMimeMessage();
		// MimeMessageHelper의 생성자 
		// 메시지, 여러 사람에게 보낼 것인가, 기본 인코딩 방식
		messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	}

	// 송신 정보
	public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException {
		messageHelper.setFrom(email, name);
	}

	// 수신 정보
	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}

	// 메일 제목
	public void setSubject(String subject) throws MessagingException {
		messageHelper.setSubject(subject);
	}

	// 메일 내용
	public void setText(String text) throws MessagingException {
		// 메일 내용, html 적용 여부(true = html형식으로 보임) 
		messageHelper.setText(text, true);
	}

	// addInline 간단한 첨부파일
	// https://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch26s03.html 참고
	public void addInline(String contentId, Resource resource) throws MessagingException {
		messageHelper.addInline(contentId, resource);
	}

	public void addInline(String contentId, File file) throws MessagingException {
		messageHelper.addInline(contentId, file);
	}

	public void addInline(String contentId, DataSource source) throws MessagingException {
		messageHelper.addInline(contentId, source);
	}

	// 실제 메일 발송 메서드
	public void send() {
		try {
			sender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}