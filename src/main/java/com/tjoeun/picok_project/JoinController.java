package com.tjoeun.picok_project;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.tjoeun.service.member.IdcheckService;
import com.tjoeun.service.member.InsertService;
import com.tjoeun.service.member.MemberService;
import com.tjoeun.service.member.NicknamecheckService;
import com.tjoeun.vo.MemberVO;

@Controller
public class JoinController {
	
	@RequestMapping("/joinOK")
	public String joinOK(HttpServletRequest request, Model model, MemberVO memberVO) {
	    

		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String addr = address+" "+detailAddress;
		
	   AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	   MemberService service = ctx.getBean("insert", InsertService.class);
	   
	   memberVO.setAddr(addr);
	   
	   service.execute(memberVO);
		return "login";
	}

	//회원가입 - 아이디 닉네임 중복검사 (ajax사용) & 요청jsp로 반환 
	@RequestMapping(value="/idCheck", method = RequestMethod.POST)
	@ResponseBody
	   public String idCheck(@RequestParam("id") String id, Model model) {
		model.addAttribute("id",id);
        AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
        // 회원 정보 저장 서비스 실행
        MemberService service = ctx.getBean("idcheck", IdcheckService.class);
        service.execute(model);
		
        Map<String, Object> resultMap = model.asMap();
        String response = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
        
	}
	//회원가입 - 아이디 닉네임 중복검사 (ajax사용) & 요청jsp로 반환 
	@RequestMapping(value="/nicknameCheck", method = RequestMethod.POST)
	@ResponseBody
	public String nicknameCheck(@RequestParam("nickname") String nickname, Model model) {
		model.addAttribute("nickname",nickname);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		// 회원 정보 저장 서비스 실행
		MemberService service = ctx.getBean("nicknamecheck", NicknamecheckService.class);
		service.execute(model);
		
		Map<String, Object> resultMap = model.asMap();
		String response = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;	
	}

}
