package com.tjoeun.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.AlarmDAO;

public class AlarmDeleteService {

	public void execute(int alarm_idx) {
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO",AlarmDAO.class);
		alarmDAO.deleteSingleAlarm(alarm_idx);
		
		
	}

}
