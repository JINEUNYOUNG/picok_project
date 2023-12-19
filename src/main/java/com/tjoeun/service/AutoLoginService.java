package com.tjoeun.service;

import javax.servlet.http.HttpSession;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.MemberDAO;
import com.tjoeun.vo.MemberVO;

public class AutoLoginService {

	public MemberVO execute(String autoLoginId) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO dao = ctx.getBean("memberDAO", MemberDAO.class);
		MemberVO mvo = dao.autoLogin(autoLoginId);
		
		return mvo;
				
	}

}
