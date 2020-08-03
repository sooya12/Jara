package com.ssafy.jara.common.firebase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private final Map<String, String> tokenMap = new HashMap<>();
	
	public void register(final String accountNickname, final String token) {
		tokenMap.put(accountNickname, token);
	}
}
