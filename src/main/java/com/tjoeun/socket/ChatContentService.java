package com.tjoeun.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.ChatDAO;
import com.tjoeun.vo.ChatVO;
import com.tjoeun.vo.MessageVO;

public class ChatContentService {

	public Map<String, Object> execute(Model model) {
		System.out.println("chatContent서비스");
		
	    Map<String, Object> map = model.asMap();
	    int chat_idx = (Integer) map.get("chat_idx");
	    String user = (String) map.get("user");
	    
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
		ChatDAO chatDAO = ctx.getBean("chatDAO",ChatDAO.class);
		
		//누른 채팅방의 안읽은 메시지 전부 읽음으로 바꿔주기 
		chatDAO.updateMessageRead(chat_idx, user);

		
		//채팅방 번호를 갖고 가서 안의 메시지 다 가져오기
		ArrayList<MessageVO> chatList = new ArrayList<MessageVO>();
		chatList = chatDAO.chatContentByIdx(chat_idx);
	    map.put("chatList", chatList);
	    

	    ctx.close();

	    return map;
	}
}
