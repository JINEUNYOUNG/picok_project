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

public class BoardLikeService implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardLikeService.class);

	@Override
	public void execute(BoardVO boardVO) {}

	@Override
	public void execute(Model model) {
		logger.info("BoardLikeService 클래스의 execute model로 받음");
		Map<String, Object> map = model.asMap();
		
		int board_idx = (Integer) map.get("board_idx");
		String member_id = (String) map.get("member_id");
		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO", AlarmDAO.class);
		
		if(boardDAO.selectLike(board_idx, member_id)) {
			//System.out.println("selectLike 좋아요했니? 네");
			// 좋아요 이미 했으면 취소시키기
			boardDAO.cancelLike(board_idx, member_id);
			// 취소하면 알람에도 지우기
			alarmDAO.cancelLike(board_idx, member_id); 
			
		} else {
			//System.out.println("selectLike 좋아요했니? 아뇨");
			// 좋아요 안 한 사람일 때 like테이블에 추가 
			boardDAO.addLikes(board_idx, member_id);
			// 좋아요 하면 알림테이블에도 추가 
			alarmDAO.addLike(board_idx, member_id); 
			String result = "add";
			model.addAttribute("result",result);
		}
	}

}
