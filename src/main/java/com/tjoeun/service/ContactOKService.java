package com.tjoeun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.ContactDAO;
import com.tjoeun.vo.ContactVO;

public class ContactOKService implements ContactService {
	private static final Logger logger = LoggerFactory.getLogger(ListService.class);


	public void execute(ContactVO contactVO, Model model) {
	    logger.info("contactok-execute ");
	    
	
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ContactDAO contactDAO = ctx.getBean("contactDAO",ContactDAO.class);
		
		contactDAO.contactInsert(contactVO);
		
	}


	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
	}

}
