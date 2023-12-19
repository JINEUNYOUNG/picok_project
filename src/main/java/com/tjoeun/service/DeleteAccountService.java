package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.MemberDAO;
import com.tjoeun.vo.MemberVO;

public class DeleteAccountService implements MemberService {

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		String id = request.getParameter("id");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO memberDAO = ctx.getBean("memberDAO", MemberDAO.class);
		
		//세션호출 (mvo)
		HttpSession session = request.getSession();
		
		//탈퇴여부 메세지 전송+세션종료 용 ->jsp 
		int result = memberDAO.deleteAccount(id);
		if (result == 1) {
			session.invalidate();
		} else {}	
	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

}
