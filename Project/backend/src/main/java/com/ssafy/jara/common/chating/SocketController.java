package com.ssafy.jara.common.chating;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SocketController {

	@MessageMapping("/receive")
	@SendTo("/send")
	
	public Socket SocketHandler(Socket socket) {
		String userName = socket.getUserName();
		String content = socket.getContent();
		
		Socket result = new Socket(userName, content);
		
		return result;
	}
}
