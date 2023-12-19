package com.tjoeun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardList;
import com.tjoeun.vo.BoardVO;

public class List2Service implements BoardService {
	
	private static final Logger logger = LoggerFactory.getLogger(ListService.class);


	@Override
	public void execute(BoardVO boardVO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Model model) {
        logger.info("list2service-execute ");
        
		Map<String, Object> map = model.asMap();
		//key-request인 value를 받아와 request에 다시 넣어준다. 
		HttpServletRequest request = (HttpServletRequest) map.get("request");

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardDAO boardDAO = ctx.getBean("boardDAO",BoardDAO.class);
		
		int pageSize = 12;
		int currentPage = 1;
		String category = request.getParameter("category");
		
		try {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
		} catch (NumberFormatException e) {	}
		
		int totalCount = boardDAO.selectCountByCategory(category);
		//이제 한페이지에 글갯수, 지금페이지, 전체갯수 알고있음
		
			
		BoardList list = ctx.getBean("boardList",BoardList.class);
		list.initBoardList(pageSize, totalCount, currentPage);
		
		//hashmap에 시작번호 끝번호 넣어서 보내서 list 받아오는 메소드 실행함
		HashMap<String, Object> hmap = new HashMap<String,Object>();
	    hmap.put("startNo", list.getStartNo());
	    hmap.put("endNo", list.getEndNo());
	    hmap.put("category", category);

	    list.setList(boardDAO.Boardlist2ByCategory(hmap));
	    
	    logger.info(list.toString());
	    model.addAttribute("boardList", list);


	    //like정보를 받아오는 메소드 실행함 (지금 로그인된 세션의 id와, startNo~endNo까지 돌아가며 비교해서)
	    String id = (String) map.get("id");
	    List<Boolean> likesList = new ArrayList<Boolean>();

	    if (!"비로그인 사용자".equals(id)) {
	        for (int i = 0; i < 9; i++) {
	            boolean isLiked = boardDAO.selectLike(list.getList().get(i).getBoard_idx(), id);
	            likesList.add(isLiked);
	        }
	    }
	    model.addAttribute("likesList", likesList);
	
	}

}
