package com.tjoeun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;

public class MyphotoNextService implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(MyphotoService.class);

	@Override
	public void execute(BoardVO boardVO) {

	}

	@Override
	public void execute(Model model) {
	    Map<String, Object> map = model.asMap();
	    String id = (String) map.get("id");
	    int currentPage = (Integer) map.get("currentPage");
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);

	    int pageSize = 8;
	    int totalCount = boardDAO.selectCount();

	    BoardList list = ctx.getBean("boardList", BoardList.class);

	    list.initBoardList(pageSize, totalCount, currentPage);


	    HashMap<String, Object> hmap = new HashMap<String, Object>();
	    hmap.put("startNo", list.getStartNo());
	    hmap.put("endNo", list.getEndNo());
	    hmap.put("id", id);

		list.setList(boardDAO.BoardlistById(hmap));
	    
		model.addAttribute("boardList", list);

	}
}
