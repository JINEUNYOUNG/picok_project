package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.dao.ContactDAO;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.ContactVO;

public class Admin_boardFilter implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(Admin_boardService.class);

	@Override
	public void execute(BoardVO boardVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Model model) {
		logger.info("admin_boardservice의 execute메소드");
		
	    Map<String, Object> map = model.asMap();
	    String category = (String) map.get("category");
	    
	    logger.info(category);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
		if (category.contentEquals("응답완료") || category.contentEquals("미응답")) {
		ContactDAO contactDAO = ctx.getBean("contactDAO",ContactDAO.class);
		ContactVO contactVO = ctx.getBean("contactVO",ContactVO.class);
		
		contactVO.setList(contactDAO.contactBoardFilter(category));

		model.addAttribute("boardList",contactVO);
		} else {
		BoardDAO boardDAO = ctx.getBean("boardDAO",BoardDAO.class);
		BoardList boardList = ctx.getBean("boardList",BoardList.class);

		boardList.setList(boardDAO.adminBoardFilter(category));

		model.addAttribute("boardList",boardList);
		}

	}

}
