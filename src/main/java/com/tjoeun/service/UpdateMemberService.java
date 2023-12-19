package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.MemberDAO;
import com.tjoeun.vo.MemberVO;

public class UpdateMemberService implements MemberService{

	private static final Logger logger = LoggerFactory.getLogger(UpdateMemberService.class);

	
	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(Model model) {
		Map<String, Object> map = model.asMap();
	
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		logger.info(request.toString());
	
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String addr = address + detailAddress;
		String phone_num = request.getParameter("phone_num");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO memberDAO = ctx.getBean("memberDAO", MemberDAO.class);
		
		MemberVO memberVO = ctx.getBean("memberVO", MemberVO.class);
		memberVO.setId(id);
		memberVO.setName(name);
		memberVO.setPassword(password);
		memberVO.setNickname(nickname);
		memberVO.setEmail(email);
		memberVO.setAddr(addr);
		memberVO.setPhone_num(phone_num);
		logger.info(memberVO.toString());
		
		//DB실행
		memberDAO.updateMember(memberVO);
		logger.info(memberVO.toString());
		
		//세션업데이트 부분
		MemberVO mvo = ctx.getBean("memberVO", MemberVO.class);
		HttpSession session = request.getSession();
		mvo = (MemberVO) session.getAttribute("mvo");
		mvo.setId(id);
		mvo.setPassword(password);
		mvo.setNickname(nickname);
		mvo.setEmail(email);
		mvo.setAddr(addr);
		mvo.setPhone_num(phone_num);
		
		session.setAttribute("mvo", mvo);
		
		//탈퇴여부 메세지 전송+세션종료 용 ->jsp 
		String message = "수정되었습니다.";
		model.addAttribute("message",message);
	
		
	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}


}