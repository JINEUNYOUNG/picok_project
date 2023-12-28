package com.tjoeun.service.member;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.dao.RegisterDAO;
import com.tjoeun.vo.MemberVO;

public class IdcheckService implements MemberService {

	@Override
	public String execute(MemberVO memberVO, HttpServletRequest request) {
		return null;
	}

	@Override
	public void execute(Model model) {
	    Map<String, Object> map = model.asMap();
	    String id = (String) map.get("id");
	    
	    System.out.println("idcheck id"+id);
	    
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    RegisterDAO registerDAO = ctx.getBean("registerDAO", RegisterDAO.class);
	    int result = registerDAO.idCheck(id);
	    
	    model.addAttribute("res",result);

		
		
		
	}

	@Override
	public void execute(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
	}

}
