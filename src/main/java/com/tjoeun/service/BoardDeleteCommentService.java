package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.AlarmDAO;
import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class BoardDeleteCommentService implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardDeleteCommentService.class);

	@Override
	public void execute(BoardVO boardVO) {}

	@Override
	public void execute(Model model) {
		logger.info("BoardDeleteCommentService 클래스의 execute model로 받음");
		Map<String, Object> map = model.asMap();
		
		System.out.println(map.get("comment_idx").getClass().getName());
		
		int board_idx = (Integer) map.get("board_idx");
		int comment_idx = Integer.parseInt((String) map.get("comment_idx"));
		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO", AlarmDAO.class);

		// 1.댓글 삭제시 알람에도 지우기 (알람 선택 시 comment_idx가 필요하므로 알람부터 지워야 함)
		alarmDAO.deleteComment(board_idx, comment_idx); 
		// 2.댓글 삭제
		boardDAO.deleteComment(board_idx, comment_idx);
	}

}
