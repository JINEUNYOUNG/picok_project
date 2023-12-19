package com.tjoeun.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.AlarmDAO;
import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardCommentVO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.MemberVO;

public class BoardCommentService implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(BoardCommentService.class);

	@Override
	public void execute(BoardVO boardVO) { }

	@Override
	public void execute(Model model) {
		logger.info("BoardUpdateService 클래스의 execute model로 받음");
		Map<String, Object> map = model.asMap();
		
		BoardCommentVO boardCommentVO = new BoardCommentVO();
		int board_idx = (Integer) map.get("board_idx");
		String id = (String) map.get("id");
		String comment_content = (String) map.get("comment_content");
		
		boardCommentVO.setBoard_idx(board_idx);
		boardCommentVO.setId(id);
		boardCommentVO.setComment_content(comment_content);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO", AlarmDAO.class);
	
		//	1. 댓글 insert
		boardDAO.addComment(boardCommentVO); 
		
		//  2. 알림 insert 
		alarmDAO.addComment(boardCommentVO);
		
		
	}

}
