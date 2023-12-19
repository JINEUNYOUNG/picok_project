package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class UploadService implements BoardService {

	private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

	@Override
	public void execute(BoardVO boardVO) {
	}

	@Override
	public void execute(Model model) {
		logger.info("upload-execute model로 받음");
	    Map<String, Object> map = model.asMap();
	    
	    String category = (String) map.get("category");
	    String board_title = (String) map.get("board_title");
	    String id = (String) map.get("id");
	    String board_content = (String) map.get("board_content");
	    String file_name = (String) map.get("file_name");

	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
	   
	    boardDAO.upload(file_name, id, category, board_title, board_content);
	    logger.info("boardDAO upload 실행 완료");
		
		
	}


}
