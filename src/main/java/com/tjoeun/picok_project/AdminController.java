package com.tjoeun.picok_project;

import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjoeun.service.admin.Admin_boardFilter;
import com.tjoeun.service.admin.Admin_boardService;
import com.tjoeun.service.admin.Admin_contactService;
import com.tjoeun.service.admin.Admin_contactSingleService;
import com.tjoeun.service.admin.Admin_deleteService;
import com.tjoeun.service.admin.Admin_memberSearchService;
import com.tjoeun.service.admin.Admin_memberService;
import com.tjoeun.service.admin.ContactOKService;
import com.tjoeun.service.admin.ContactService;
import com.tjoeun.service.admin.SendEmailService;
import com.tjoeun.service.board.BoardService;
import com.tjoeun.service.member.MemberService;
import com.tjoeun.vo.ContactVO;
import com.tjoeun.vo.MemberVO;

@Controller
public class AdminController {
	

	//contact게시판 
	@RequestMapping("/admin_contact")
	public String admin_contact(Model model, ContactVO contactVO) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ContactService service = ctx.getBean("adminContact", Admin_contactService.class);
		service.execute(contactVO,model);
			return "admin_contact";
		
	}
		
	//관리자페이지 접근 시 세션정보 확인, 페이지반환
	@RequestMapping("/admin")
    public String admin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MemberVO mvo = (MemberVO) session.getAttribute("mvo");
        if (mvo.getMem_lv()==1) {
            return "adminpage";
        } else {
        	return "redirect:login";
        }
    }
		
	//관리자페이지(게시글) 조회 후 반환
	@RequestMapping("/admin_board")
	public String admin_board(HttpServletRequest request, Model model) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("adminBoard", Admin_boardService.class);
		service.execute(model);
		return "admin_board";
	}
	
	//관리자페이지(멤버) 넘어온 쿼리가 있을 시 검색 후, 없을 시에 전체 리스트 반환 
	@RequestMapping("/admin_member")
	public String admin_member(HttpServletRequest request, Model model) {
	    String query = request.getParameter("query");
	    model.addAttribute("query",query);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberService service = ctx.getBean("adminMember", Admin_memberService.class);
		MemberService service2 = ctx.getBean("adminMemberSearch", Admin_memberSearchService.class);
		if (query != null && !query.isEmpty()) {
			service2.execute(model);  			// 검색어가 제공된 경우 검색 기능 수행
		} else {
			service.execute(model);				// 전체 리스트
		}
		return "admin_member";
	}
	
	//관리자페이지에서 다중삭제기능(ajax사용) & 요청jsp로 반환(멤버 또는 게시글) 
	@RequestMapping(value="/admin_delete", method = RequestMethod.POST)
	@ResponseBody
	public String admin_delete(HttpServletRequest request, Model model) {
        String referer = request.getHeader("referer");
		String postIdsParam = request.getParameter("postIds");

		model.addAttribute("postIdsParam",postIdsParam);
        model.addAttribute("referer",referer);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberService service = ctx.getBean("adminDelete", Admin_deleteService.class);
		service.execute(model);

			return null;
		}

	//관리자페이지(게시판) 필터기능(ajax) 결과물을 map객체에 담아 전송 
	@ResponseBody
	@RequestMapping(value="/admin_boardFilter", method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String admin_boardFilter(@RequestParam("category") String category, Model model) {
		model.addAttribute("category",category);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("adminBoardFilter", Admin_boardFilter.class);

		service.execute(model);
	
        Map<String, Object> resultMap = model.asMap();
        
        String boardList = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            boardList = objectMapper.writeValueAsString(resultMap);
        	} catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return boardList;
    }
	
	@RequestMapping("/admin_contactSingle")
	public String admin_contactSingle(Model model,ContactVO contactVO) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ContactService service = ctx.getBean("adminContactSingle", Admin_contactSingleService.class);
		service.execute(contactVO,model);

		
		return "admin_contactSingle";
	}
	

	   //contact.jsp에서 문의 메일 보내면 내용 upload 
	   @RequestMapping("/contactOK")
	   public String contackOK(Model model, ContactVO contactVO) {
		    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		    ContactService service = ctx.getBean("contactOK", ContactOKService.class);
			service.execute(contactVO,model);
		    model.addAttribute("message", "정상적으로 제출되었습니다. 감사합니다!");

		return "contact";
	   }
	   
	   private final JavaMailSenderImpl mailSender;

	   
	   @Autowired
	   public AdminController(JavaMailSenderImpl mailSender) {
	       this.mailSender = mailSender;
	   }

	   @RequestMapping("/sendEmail")
	   public String sendEmail(HttpServletRequest request, Model model) {
			String subject =request.getParameter("reply-title");
			String content = request.getParameter("reply-content");
		    String from = mailSender.getUsername(); 
			String to = request.getParameter("reply-email");
			
			String idx = request.getParameter("reply-idx");
			model.addAttribute("idx",idx);
			model.addAttribute("content",content);
			
			try {
				MimeMessage mail = mailSender.createMimeMessage();
				MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");

				mailHelper.setFrom(from);	// 보내는 사람 셋팅
				mailHelper.setTo(to);		// 받는 사람 셋팅
				mailHelper.setSubject(subject);	// 제목 셋팅
				mailHelper.setText(content);	// 내용 셋팅

				mailSender.send(mail);
				
			    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
			    ContactService service = ctx.getBean("sendEmail", SendEmailService.class);
				service.execute(model);
			    model.addAttribute("message", "메일 발송완료 되었습니다.");
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return "redirect:admin_contact";
		}



}
