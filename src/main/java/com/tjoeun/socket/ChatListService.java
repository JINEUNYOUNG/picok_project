package com.tjoeun.socket;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.ChatDAO;
import com.tjoeun.vo.ChatVO;

public class ChatListService {

	public Map<String, Object> execute(Model model) {
		System.out.println("chatList서비스");
		
	    Map<String, Object> map = model.asMap();
	    String from_id = (String) map.get("from_id");
	    
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);
		
		ArrayList<ChatVO> chatList = new ArrayList<ChatVO>();
		chatList = chatDAO.chatListById(from_id);
		map.put("chatList", chatList);
		System.out.println("latest" +chatList);

	    ctx.close();

	    return map;
	}
}
