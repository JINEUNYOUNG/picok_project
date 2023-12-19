package com.tjoeun.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.dao.MemberDAO;
import com.tjoeun.vo.MemberList;
import com.tjoeun.vo.MemberVO;

public class Admin_memberSearchService implements MemberService {

	private static final Logger logger = LoggerFactory.getLogger(Admin_memberService.class);

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(Model model) {
		logger.info("admin_membersearchservice의 execute메소드");

	    Map<String, Object> map = model.asMap();
	    String query = (String) map.get("query");
	    logger.info("검색"+query);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO memberDAO = ctx.getBean("memberDAO",MemberDAO.class);
		MemberList memberList = ctx.getBean("memberList",MemberList.class);

		memberList.setList(memberDAO.adminMemberSearch(query));

		logger.info("search결과"+memberList.toString());
		
		model.addAttribute("memberList",memberList);

	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}
}
