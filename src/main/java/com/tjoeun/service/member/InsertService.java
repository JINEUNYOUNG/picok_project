package com.tjoeun.service.member;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.RegisterDAO;
import com.tjoeun.vo.MemberVO;


@Service("insertService")
public class InsertService implements MemberService {

	private static final Logger logger = LoggerFactory.getLogger(InsertService.class);

	@Autowired
	private RegisterDAO registerDAO; 
	
	@Override
	public void execute(MemberVO memberVO) {
		logger.info("InsertService 클래스의 execute() 메소드 실행 - VO 사용");
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    // 회원 정보 저장 서비스 실행
	    RegisterDAO registerDAO = ctx.getBean("registerDAO", RegisterDAO.class);
	   logger.info(memberVO.toString());
		// 회원 정보를 데이터베이스에 저장하는 로직
	   registerDAO.insertMember(memberVO);
	}

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(Model model) {
	}

}
