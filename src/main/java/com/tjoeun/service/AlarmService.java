package com.tjoeun.service;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.AlarmDAO;
import com.tjoeun.vo.AlarmVO;
import com.tjoeun.vo.BoardVO;

public class AlarmService implements BoardService {

	@Override
	public void execute(BoardVO boardVO) {

	}

	@Override
	public void execute(Model model) {

	}

	public List<AlarmVO> execute(String memberId) {
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO",AlarmDAO.class);
			
		System.out.println(alarmDAO.alarmList(memberId));
	return alarmDAO.alarmList(memberId);
		
	}
}
