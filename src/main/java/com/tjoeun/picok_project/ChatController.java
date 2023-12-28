package com.tjoeun.picok_project;

import java.util.Map;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjoeun.service.chat.ChatCheckService;
import com.tjoeun.service.chat.ChatContentService;
import com.tjoeun.service.chat.ChatListService;

@Controller
public class ChatController {
	
	//채팅창리스트 전부
	@ResponseBody
	@RequestMapping(value="/chatList", method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String chatList(@RequestParam("from_id") String from_id, Model model) {
		model.addAttribute("from_id",from_id);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatListService service = ctx.getBean("chatList", ChatListService.class);

		Map<String, Object> resultMap = service.execute(model);
	        
        String chatList = "";
        try {
        	
            ObjectMapper objectMapper = new ObjectMapper();
            chatList = objectMapper.writeValueAsString(resultMap);
        	} catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return chatList;
    }
	//개별 채팅 1건의 메시지 리스트 
	@ResponseBody
	@RequestMapping(value="/chatContent", method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String chatContent(@RequestParam("chat_idx") int chat_idx, @RequestParam("user") String user, Model model) {
		model.addAttribute("chat_idx",chat_idx);
		model.addAttribute("user",user);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatContentService service = ctx.getBean("chatContent", ChatContentService.class);
		
		Map<String, Object> resultMap = service.execute(model);
		
		String chatContent = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			chatContent = objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return chatContent;
	}
	
	//채팅방으로 가기 전에 이미 존재하는지 체크 
	@RequestMapping("/chatCheck")
	public String chatCheck(@RequestParam("from_id") String from_id,@RequestParam("to_id") String to_id, Model model) {
		model.addAttribute("from_id",from_id);
		model.addAttribute("to_id",to_id);
		
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    ChatCheckService service = ctx.getBean("chatCheck", ChatCheckService.class);
		service.execute(model);
		
		return "chat";
	}	

}
