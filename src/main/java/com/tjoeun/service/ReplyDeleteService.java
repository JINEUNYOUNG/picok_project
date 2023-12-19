package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class ReplyDeleteService implements BoardService {

   private static final Logger logger = LoggerFactory.getLogger(ReplyDeleteService.class);
   
   @Override
   public void execute(BoardVO boardVO) {}

   @Override
   public void execute(Model model) {
      logger.info("ReplyDeleteService 클래스의 execute model로 받음");
      Map<String, Object> map = model.asMap();
      
      System.out.println(map.get("reply_idx").getClass().getName());
      
      int board_idx = (Integer) map.get("board_idx");
      int reply_idx = (Integer) map.get("reply_idx");
      
      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
      
      //   댓글 삭제
      boardDAO.deleteReply(board_idx, reply_idx);
   }

}