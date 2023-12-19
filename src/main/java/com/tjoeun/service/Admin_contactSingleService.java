package com.tjoeun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.tjoeun.dao.ContactDAO;
import com.tjoeun.vo.ContactVO;

public class Admin_contactSingleService implements ContactService {
	private static final Logger logger = LoggerFactory.getLogger(ListService.class);

	@Override
	public void execute(ContactVO contactVO, Model model) {
	    logger.info("admin_contactsingleservice-execute ");
	    
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ContactDAO contactDAO = ctx.getBean("contactDAO",ContactDAO.class);
		
		contactVO = contactDAO.contactSingle(contactVO);
		
		logger.info(contactVO+"");
		model.addAttribute("boardList",contactVO);

	}

	@Override
	public void execute(Model model) {
		// TODO Auto-generated method stub
		
	}

}
