package com.tjoeun.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.ReplyVO;

@Service
public class BoardSingleService implements BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardSingleService.class);

    @Override
    public void execute(BoardVO boardVO) { }

    @Override
    public void execute(Model model) {
        logger.info("BoardSingleService의 boardSingle-execute model로 받음");
        

        // 컨트롤러에서 전달한 Model 객체에서 "idx" 파라미터를 가져옴
      Map<String, Object> map = model.asMap();
      int board_idx = (Integer) map.get("board_idx");
      String ip = (String) map.get("ip");
      String member_id = (String) map.get("member_id");
      
      logger.info(board_idx+"");

      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);

      // 나머지 로직 (조회, 좋아요 확인, 댓글 조회 등) 처리
  
      // 조회 검증(이미 조회를 한 사람인지 판별)
      if (boardDAO.selectBoardView(board_idx, ip) == 0) { // 조회수 1 증가 
         boardDAO.addViewNum(board_idx);
         boardDAO.uploadView(board_idx, ip);
      }
	    // 조회 검증(이미 조회를 한 사람인지 판별)
	    if (boardDAO.selectBoardView(board_idx, ip) == 0) { // 조회수 1 증가 
	    	boardDAO.addViewNum(board_idx);
	    	boardDAO.uploadView(board_idx, ip);
	    }
	    
	    
	    BoardVO result = boardDAO.boardSingleList(board_idx);
	    
	    // 조회한 데이터를 Model에 저장
	    model.addAttribute("singlepage", result);
	    model.addAttribute("listResult", boardDAO.viewComment(board_idx));
	    
	    //작성자의 다른 글 3개*3 = 9개를 가져옴(9개면 3개씩 나눠서)
	    ArrayList<BoardVO> result2 = boardDAO.otherBoardById(result.getId());
	    System.out.println("result2"+result2);
	    System.out.println("resultsize"+result2.size());
	    if (result2.size() >= 7) {  //3페이지까지 전부 사용하는 경우 
	        List<BoardVO> slide1 = result2.subList(0, 3); // 1~3번째 게시물
	        List<BoardVO> slide2 = result2.subList(3, 6); // 4~6번째 게시물
	        List<BoardVO> slide3 = result2.subList(6,result2.size()); // 7~번째 게시물
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", slide2);
	        model.addAttribute("slide3", slide3);
	    } else if (result2.size() >= 4) { //2페이지만 사용하는 경우 (4개 이상 7개 미만) 
	        List<BoardVO> slide1 = result2.subList(0, 3); 
	        List<BoardVO> slide2 = result2.subList(3, result2.size());
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", slide2);
	        model.addAttribute("slide3", new ArrayList<BoardVO>());  //모자란 대로 빈 리스트 반환 
	    } else { //1페이지만 사용하는 경우 (4개 미만)
	        List<BoardVO> slide1 = result2.subList(0, result2.size()); 
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", new ArrayList<BoardVO>());
	        model.addAttribute("slide3", new ArrayList<BoardVO>());
	    }


	    // 좋아요 한 유저인지 판별 => mvo.getId() ? id?
	    boolean likes = (member_id != null) ? boardDAO.selectLike(board_idx, member_id) : false;
	    model.addAttribute("likes", likes);
	    
	    // 좋아요 개수를 얻어옴
	    int countLikes = boardDAO.countLikes(board_idx);
	    model.addAttribute("countLikes", countLikes);
	
	    // 신고 여부 확인
	    boolean report = boardDAO.ReportCheck(board_idx, ip); 
	    model.addAttribute("report", report);
	    
	    // 대댓글
        ArrayList<ReplyVO> reply = boardDAO.selectReply(board_idx);
        model.addAttribute("reply", reply);

    }
   //   조회수 중복증가 방지를 위해 사용자 ip 주소를 가져오는 메소드
   protected String getRemoteAddr(HttpServletRequest request){
       return request.getRemoteAddr();
   }

}