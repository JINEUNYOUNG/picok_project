package com.tjoeun.picok_project;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.service.member.DeleteAccountService;
import com.tjoeun.service.member.MemberService;
import com.tjoeun.service.member.UpdateMemberService;

@Controller
public class AccountController {
	
	@RequestMapping("/deleteAccount")
	public String deleteAccount(HttpServletRequest request, Model model,RedirectAttributes rttr) {
	    model.addAttribute("request", request);

	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    MemberService service = ctx.getBean("deleteAccount", DeleteAccountService.class);
	    service.execute(model);
	    return "quit";
	}
	
	@RequestMapping("/UpdateMember")
	public String updateMember(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberService service = ctx.getBean("updateMember", UpdateMemberService.class);
		service.execute(model);

		return "mypage";
	}

}
