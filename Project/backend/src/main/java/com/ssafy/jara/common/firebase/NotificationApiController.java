package com.ssafy.jara.common.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jara.dto.Account;

@RestController
@RequestMapping("fcm")
public class NotificationApiController {

	@Autowired
	NotificationService notificationService;
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody String token, @RequestBody Account account) {
		notificationService.register(account.getNickname(), token);
		
		return ResponseEntity.ok().build();
	}
}
