package com.tjoeun.socket;

import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.ChatDAO;

public class ChatCheckService {

	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		
		String from_id = (String) map.get("from_id");
		String to_id = (String) map.get("to_id");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");

		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);

		//1. 이미 있는 채팅방인지 체크 (true면 있다, false면 없다)
		Boolean result = chatDAO.chatCheck(from_id, to_id);
		System.out.println(result);
		
		//2. 없으면 채팅방 새로 만들어주기 / 있으면 내가 나갔던 채팅방이면 나가기 기록 없애줌.
		if (!result) {
			chatDAO.chatCreate(from_id, to_id);	
		} else {
			chatDAO.chatReEnter(from_id, to_id);
		}
	}
}
