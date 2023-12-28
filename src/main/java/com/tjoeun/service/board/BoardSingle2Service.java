package com.tjoeun.service.board;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class BoardSingle2Service implements BoardService {
	
    private static final Logger logger = LoggerFactory.getLogger(BoardSingleService.class);

	@Override
	public void execute(BoardVO boardVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Model model) {
        logger.info("BoardSingleService의 boardSingle-execute model로 받음");

        // 컨트롤러에서 전달한 Model 객체에서 "idx" 파라미터를 가져옴
      Map<String, Object> map = model.asMap();
      int board_idx = (Integer) map.get("board_idx");

      logger.info(board_idx+"");

      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);

	  BoardVO result = boardDAO.boardSingleList(board_idx);

	  model.addAttribute("singlepage", result);

    }

}
