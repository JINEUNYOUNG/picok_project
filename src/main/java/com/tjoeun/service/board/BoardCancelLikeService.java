package com.tjoeun.service.board;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.MemberVO;

public class BoardCancelLikeService implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardCancelLikeService.class);

	@Override
	public void execute(BoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("ReportService 클래스의 execute model로 받음");
		Map<String, Object> map = model.asMap();
		
		int board_idx = (Integer) map.get("board_idx");
		String id = (String) map.get("id");
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		
		//	좋아요 취소
		boardDAO.cancelLike(board_idx, id);
		
	}

}
