package com.tjoeun.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjoeun.vo.MemberVO;

public class EchoHandler extends TextWebSocketHandler{
	//로그인 한 인원 전체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();

	
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
    	System.out.println(".........");
        super.afterConnectionEstablished(session);
        sessions.add(session); 
    }

	
	//메시지를 받을 때는 접속 한 사람의 (id, message) + 채팅방번호를 받아야 한다 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException{
		System.out.println("chatinfo 받아봅시다.");

        String payload = message.getPayload();

        ObjectMapper objectMapper = new ObjectMapper(); 
        JsonNode jsonNode;
    
		jsonNode = objectMapper.readTree(payload);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatInsertService service = ctx.getBean("chatInsert", ChatInsertService.class);
		ChatExitService service2 = ctx.getBean("chatExit", ChatExitService.class);

		//채팅 메시지 가져오는 중(받은 3가지 정보 모두 가져오기)
		if (jsonNode.has("chat_idx")&&jsonNode.has("action")) {
		    int chat_idx = Integer.parseInt(jsonNode.get("chat_idx").asText());
			// chat_idx는 처음 접속 시에만 가져오므로 세션에 저장 해서 꺼내 쓴다.
		    session.getAttributes().put("chat_idx", chat_idx);
		    
		} else if (!jsonNode.has("chat_idx")&&jsonNode.has("action"))  { //채팅 친 사람 
			String from_id = jsonNode.get("from_id").asText();
			String message2 = jsonNode.get("message").asText();

			int chat_idx = (Integer)session.getAttributes().get("chat_idx");
			//채팅메시지 db에 넣어주기
			service.execute(from_id, chat_idx, message2);

	        // 해당 채팅방에 속한 세션들에게만 메시지 전송
	        for (WebSocketSession clientSession : sessions) {
	            if (clientSession.isOpen() && chat_idx == (Integer) clientSession.getAttributes().get("chat_idx")) {
	                clientSession.sendMessage(new TextMessage(from_id + "/" + message2));
	            }
	        }


		//나가기 누른 채팅창. action2로 찾아 받아주기  
		} else if (jsonNode.has("chat_idx")&&jsonNode.has("action2")) {
			int chat_idx = Integer.parseInt(jsonNode.get("chat_idx").asText());
			session.getAttributes().put("chat_idx", chat_idx);
			String user = jsonNode.get("user").asText();
			System.out.println("chat_idx 받은거! "+chat_idx+user);
			service2.execute(chat_idx, user);
			afterConnectionClosed(session, null);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {
		System.out.println("Socket 끊음");
		sessions.remove(session);
		session.close();

	}

    }