package com.tjoeun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;

public class Admin_boardService implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(Admin_boardService.class);

	@Override
	public void execute(BoardVO boardVO) {

	}

	@Override
	public void execute(Model model) {
		logger.info("admin_boardservice의 execute메소드");

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO",BoardDAO.class);
		BoardList boardList = ctx.getBean("boardList",BoardList.class);

		boardList.setList(boardDAO.adminBoard());

		model.addAttribute("boardList",boardList);

	}

}
