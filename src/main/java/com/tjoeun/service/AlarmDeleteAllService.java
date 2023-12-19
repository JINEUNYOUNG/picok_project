package com.tjoeun.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.AlarmDAO;

public class AlarmDeleteAllService {

	public void execute(String from_id) {
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO",AlarmDAO.class);
		alarmDAO.deleteAllAlarm(from_id);
		
		
	}

}
