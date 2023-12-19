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
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.MemberVO;

public class Admin_deleteService implements BoardService, MemberService {
	private static final Logger logger = LoggerFactory.getLogger(MyphotoService.class);

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(BoardVO boardVO) {
	}

	@Override
	public void execute(Model model) {
	    Map<String, Object> map = model.asMap();
	    String page = (String) map.get("referer");
	    logger.info("referer"+ page);

	    String postIdsParam =  (String) map.get("postIdsParam");
        if (page.contains("/admin_member")) {
        	admin_memberdelete(postIdsParam);
        } else if (page.contains("/admin_board")) {
        	admin_boarddelete(postIdsParam);
        }
        
      
	}

	private void admin_boarddelete(String postIdsParam) {
		String[] postIdsArray = postIdsParam.split(",");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);

		for (String deleteidx : postIdsArray) {
			int idxToDelete = Integer.parseInt(deleteidx);
			boardDAO.deleteByIdx(idxToDelete);
		}
	}

	private void admin_memberdelete(String postIdsParam) {
		String[] postIdsArray = postIdsParam.split(",");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberDAO memberDAO = ctx.getBean("memberDAO", MemberDAO.class);
		
		for (String deleteidx : postIdsArray) {
			String idxToDelete = deleteidx;
			memberDAO.deleteById(idxToDelete);
		}
	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

}
