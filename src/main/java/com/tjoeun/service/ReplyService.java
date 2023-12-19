package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardCommentVO;
import com.tjoeun.vo.BoardVO;
import com.tjoeun.vo.ReplyVO;

public class ReplyService implements BoardService {
   
   private static final Logger logger = LoggerFactory.getLogger(ReplyService.class);

   @Override
   public void execute(BoardVO boardVO) {

   }

   @Override
   public void execute(Model model) {
      logger.info("ReplyService 클래스의 execute model로 받음");
      Map<String, Object> map = model.asMap();
      
      ReplyVO replyVO = new ReplyVO();
      int board_idx = (Integer) map.get("board_idx");
      int comment_idx = (Integer) map.get("comment_idx");
      String id = (String) map.get("id");
      String reply_content = (String) map.get("reply_content");
      
      replyVO.setBoard_idx(board_idx);
      replyVO.setComment_idx(comment_idx);
      replyVO.setId(id);
      replyVO.setReply_content(reply_content);
      
      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
   
      //   댓글 작성
      boardDAO.addReply(replyVO);
   }

}