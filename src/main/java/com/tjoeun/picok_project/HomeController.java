package com.tjoeun.picok_project;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tjoeun.service.login.AutoLoginService;
import com.tjoeun.vo.MemberVO;

@Controller
public class HomeController {
	
	//단순 뷰페이지 반환 컨트롤러
	@RequestMapping("/")
	public String home(HttpServletRequest request) {

		//자동로그인 쿠키 확인 후에 페이지 로딩
		Cookie[] cookies = request.getCookies();
		String autoLoginId = "none";

		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if (cookie.getName().equals("autoLogin")) {
		            autoLoginId = cookie.getValue();
		            break;
		        }
		    }
		}

		if (!"none".equals(autoLoginId)) {
		    try {
		        AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		        AutoLoginService service = ctx.getBean("autoLogin", AutoLoginService.class);
		        MemberVO mvo = service.execute(autoLoginId);

		        // mvo값을 세션에 올려줌 (로그인처리)
		        HttpSession session = request.getSession();
		        session.setAttribute("mvo", mvo);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		return "index";
	}
	
	@RequestMapping("/index")
    public String index() {
		return "redirect:/";
	}    
	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	@RequestMapping("/qna")
	public String qna() {
		return "qna";
	}
	@RequestMapping("/join")
	public String join() {
		return "join";
	}
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}
	@RequestMapping("/upload")
	public String upload(HttpServletRequest request, Model model) {
		return "upload";
	}
	@RequestMapping("/mypage")
	public String mypage(HttpServletRequest request, Model model) {
		return "mypage";
	}
	@RequestMapping("/mypageUpdate")
	public String mypageUpdate(HttpServletRequest request, Model model) {
		return "mypageUpdate";
	}
	@RequestMapping("/chat")
	public String chat(HttpServletRequest request, Model model) {
		return "chat";
	}


}
