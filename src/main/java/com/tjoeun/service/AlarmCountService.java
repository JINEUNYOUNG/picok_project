package com.tjoeun.service;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.tjoeun.dao.AlarmDAO;

public class AlarmCountService {

	public int execute(String to_id) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDAO alarmDAO = ctx.getBean("alarmDAO",AlarmDAO.class);
	return alarmDAO.countAlarm(to_id);
	}

}
