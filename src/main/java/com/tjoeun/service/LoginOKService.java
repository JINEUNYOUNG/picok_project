package com.tjoeun.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tjoeun.dao.MemberDAO;
import com.tjoeun.vo.MemberVO;

public class LoginOKService implements MemberService {

	private static final Logger logger = LoggerFactory.getLogger(LoginOKService.class);

	
	@Override
    public String execute(MemberVO memberVO, HttpServletRequest request) {
		logger.info("loginokservice-execute");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO memberDAO = ctx.getBean("memberDAO",MemberDAO.class);
		
		MemberVO mvo = memberDAO.login(memberVO);
		
		

		String url = null;
		if (mvo == null) { 							
			url = "login_fail";
            request.setAttribute("message", "로그인에 실패하였습니다. 다시 로그인해주세요.");
		} else if (mvo.getId().equals("admin")) { 	   									//로그인 성공
			url = "login_admin";
            HttpSession session = request.getSession();
            session.setAttribute("mvo", mvo);
		} else { 								  
			url = "login_success";
			HttpSession session = request.getSession();
			session.setAttribute("mvo", mvo);
	
				
				
		}
		
		return url;
	}

	@Override
	public void execute(Model model) {

	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}


}

