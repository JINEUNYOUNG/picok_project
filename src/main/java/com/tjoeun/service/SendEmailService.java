package com.tjoeun.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.Model;

import com.tjoeun.dao.ContactDAO;
import com.tjoeun.vo.ContactVO;


public class SendEmailService implements ContactService {

	@Override
	public void execute(ContactVO contactVO, Model model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute(Model model) {
	
      Map<String, Object> map = model.asMap();
      String idxString = (String) map.get("idx");
      int idx = Integer.parseInt(idxString); 
      String content = (String) map.get("content"); 
	
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    ContactDAO contactDAO = ctx.getBean("contactDAO", ContactDAO.class);
		contactDAO.contactOK(idx,content);				

	}

}
