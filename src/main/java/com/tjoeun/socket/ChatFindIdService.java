package com.tjoeun.socket;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.ChatDAO;

public class ChatFindIdService {

	public String execute(int chat_idx, String from_id) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);
		String otherId = chatDAO.chatFindId(from_id, chat_idx);		

		return otherId;
	}

}
