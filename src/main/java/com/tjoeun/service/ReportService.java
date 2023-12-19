package com.tjoeun.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.BoardDAO;
import com.tjoeun.vo.BoardVO;

public class ReportService implements BoardService {

   private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
   
   @Override
   public void execute(BoardVO boardVO) {}

   @Override
   public void execute(Model model) {
      logger.info("ReportService 클래스의 execute model로 받음");
      ///////   코드 추가 시작 ////////
      Map<String, Object> map = model.asMap();
      
      int board_idx = (Integer) map.get("board_idx");
      String ip = (String) map.get("ip");
      
      AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      BoardDAO boardDAO = ctx.getBean("boardDAO", BoardDAO.class);
      
      //   신고기능 위해 수정
      BoardVO boardVO = boardDAO.boardSingleList(board_idx);
      boardVO.setReport_num(boardVO.getReport_num() + 1);
      System.out.println(boardVO.getReport_num()+"sdfsdddddd");
      //   신고 개수 넘어가면 삭제되는 코드
      if(boardVO.getReport_num() >= 3) {
         boardDAO.reportDelete(board_idx);
      } else {
         boardDAO.report(board_idx, ip);
         boardDAO.reportNumUpdate(board_idx, boardVO.getReport_num());
      }
      ///////   코드 추가 끝 ////////
   }

}