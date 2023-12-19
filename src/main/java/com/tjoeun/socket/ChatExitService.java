package com.tjoeun.socket;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.ChatDAO;

public class ChatExitService {

	public void execute(int chat_idx, String user) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");

		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);
		Boolean result = chatDAO.chatExitCheck(chat_idx);
		
		if (result) { //exit_id가 이미 찍혀 있는 경우(상대방이 나간 경우) 아에 채팅창을 지운다. 
			chatDAO.chatDelete(chat_idx);
			
		} else { //내가 처음 나가는 경우 exit_id에 내 id를 찍어준다.->이 정보로 채팅방 목록을 띄워줄 때 제외된다. 
			chatDAO.chatExit(chat_idx,user);
		}
		
	}

}
