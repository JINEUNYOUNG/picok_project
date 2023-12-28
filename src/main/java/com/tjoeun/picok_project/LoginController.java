package com.tjoeun.picok_project;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.service.login.LoginOKService;
import com.tjoeun.service.member.MemberService;
import com.tjoeun.vo.MemberVO;

@Controller
public class LoginController {
	
	//로그인 시도 시 결과 반환
	@RequestMapping("/loginOK")
	public String loginOK(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO,	
            @RequestParam("id") String id, 
            @RequestParam("password") String password, 
            @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe) {

		// 기존의 로그인 처리 로직을 수행
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberService service = ctx.getBean("loginOK", LoginOKService.class);
	   
		String url = service.execute(memberVO, request);
	   	   
	    if (url.contentEquals("login_success")) {

			// 로그인 성공 시 자동 로그인을 처리하는 로직 추가
			if (rememberMe) {    
				// 자동 로그인 쿠키 생성 및 설정
				Cookie autoLoginCookie = new Cookie("autoLogin", memberVO.getId());
				autoLoginCookie.setMaxAge(30 * 24 * 60 * 60); // 30일 동안 유효
				autoLoginCookie.setPath("/"); // 쿠키의 유효 범위 설정 (루트 경로)
				
				response.addCookie(autoLoginCookie);

			}
	        return "forward:mypage";
	    } else if (url.contentEquals("login_admin")) {
	        return "adminpage";
	    } else {
	    	return "login";
	    }
	 }

	//로그아웃 시 세션정보 리셋, 로그아웃 
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) { //자동 로그인용 수정됨
	    
	    // 예시: 자동 로그인 토큰 제거 
	    Cookie[] cookies = request.getCookies();  
	    for (Cookie cookie : cookies) {
	        if (cookie.getName().equals("autoLogin")) {
	            cookie.setMaxAge(0); // 쿠키 만료
	            cookie.setPath("/");
	            response.addCookie(cookie);
	            break;
	        }
	    } 
	    
	    HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("mvo");
	    if (session != null) {
	        session.invalidate();
	    }
	    return "logout";
	}
	

}
