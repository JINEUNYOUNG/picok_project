package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.MemberVO;

public class BoardUpdateService implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardUpdateService.class);

	@Override
	public void execute(BoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("BoardUpdateService 클래스의 execute model로 받음");
		Map<String, Object> map = model.asMap();
		
		int board_idx = (Integer) map.get("board_idx");
		String category = (String) map.get("category");
		String id = (String) map.get("id");
	    String board_title = (String) map.get("board_title");
	    String board_content = (String) map.get("board_content");
	    String file_name = (String) map.get("file_name");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		   
		boardDAO.writeUpdate(board_idx, id, category, board_title, board_content, file_name);
	}

}
