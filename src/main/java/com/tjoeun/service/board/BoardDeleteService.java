package com.tjoeun.service.board;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class BoardDeleteService implements BoardService {
    private static final Logger logger = LoggerFactory.getLogger(BoardSingleService.class);


	@Override
	public void execute(BoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("BoardUpdateService 클래스의 execute model로 받음");
		
		Map<String, Object> map = model.asMap();
		
		int board_idx = (Integer) map.get("board_idx");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		
		boardDAO.writeDelete(board_idx);
	}

}
