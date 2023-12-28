package com.tjoeun.service.chat;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.ChatDAO;

//웹소켓을 통해 서버에서 받은 챗 내용을 세션에 저장 한 뒤에 db insert를 위해 서비스단으로 왔다
public class ChatInsertService {

	public void execute(String from_id, int chat_idx, String message) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		System.out.println("서비스단 "+from_id+chat_idx+message);
		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);
		chatDAO.chatInsert(from_id, chat_idx, message);

    }
}