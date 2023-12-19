package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.RegisterDAO;
import com.tjoeun.vo.MemberVO;

public class NicknamecheckService implements MemberService {

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(Model model) {
	    Map<String, Object> map = model.asMap();
	    String nickname = (String) map.get("nickname");
	    
	    System.out.println("nicknamecheck nicknmae"+nickname);
	    
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    RegisterDAO registerDAO = ctx.getBean("registerDAO", RegisterDAO.class);
	    int result = registerDAO.nicknameCheck(nickname);
	    
	    model.addAttribute("res",result);

		
	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub

	}

}
