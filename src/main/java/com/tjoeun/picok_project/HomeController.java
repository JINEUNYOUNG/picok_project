package com.tjoeun.picok_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjoeun.service.Admin_boardFilter;
import com.tjoeun.service.Admin_boardService;
import com.tjoeun.service.Admin_contactService;
import com.tjoeun.service.Admin_contactSingleService;
import com.tjoeun.service.Admin_deleteService;
import com.tjoeun.service.Admin_memberSearchService;
import com.tjoeun.service.Admin_memberService;
import com.tjoeun.service.AlarmCountService;
import com.tjoeun.service.AlarmDeleteAllService;
import com.tjoeun.service.AlarmDeleteService;
import com.tjoeun.service.AlarmService;
import com.tjoeun.service.AutoLoginService;
import com.tjoeun.service.BoardCommentService;
import com.tjoeun.service.BoardDeleteCommentService;
import com.tjoeun.service.BoardDeleteService;
import com.tjoeun.service.BoardLikeService;
import com.tjoeun.service.BoardService;
import com.tjoeun.service.BoardSingle2Service;
import com.tjoeun.service.BoardSingleService;
import com.tjoeun.service.BoardUpdateService;
import com.tjoeun.service.ContactOKService;
import com.tjoeun.service.ContactService;
import com.tjoeun.service.DeleteAccountService;
import com.tjoeun.service.IdcheckService;
import com.tjoeun.service.InsertService;
import com.tjoeun.service.LikeNextService;
import com.tjoeun.service.LikeService;
import com.tjoeun.service.List2Service;
import com.tjoeun.service.ListService;
import com.tjoeun.service.LoginOKService;
import com.tjoeun.service.MemberService;
import com.tjoeun.service.MyphotoNextService;
import com.tjoeun.service.MyphotoService;
import com.tjoeun.service.NicknamecheckService;
import com.tjoeun.service.ReplyDeleteService;
import com.tjoeun.service.ReplyService;
import com.tjoeun.service.ReportService;
import com.tjoeun.service.SendEmailService;
import com.tjoeun.service.UpdateMemberService;
import com.tjoeun.service.UploadService;
import com.tjoeun.socket.ChatCheckService;
import com.tjoeun.socket.ChatContentService;
import com.tjoeun.socket.ChatListService;
import com.tjoeun.vo.AlarmVO;
import com.tjoeun.vo.ContactVO;
import com.tjoeun.vo.MemberVO;



@Controller
public class HomeController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
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
	
	
	
	
	//뷰페이지반환 + 기능구현 컨트롤러

	//로그인 시도 시 결과 반환
	@RequestMapping("/loginOK")
	public String loginOK(HttpServletRequest request, HttpServletResponse response, MemberVO memberVO,	
            @RequestParam("id") String id, 
            @RequestParam("password") String password, 
            @RequestParam(value = "rememberMe", defaultValue = "false") boolean rememberMe) {

		logger.info("loginOK 컨트롤러55555555");	
		logger.info("id: " + id + ", password: " + password + ", rememberMe: " + rememberMe); //자동 로그인용 추가됨/////////////

		// 기존의 로그인 처리 로직을 수행
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MemberService service = ctx.getBean("loginOK", LoginOKService.class);
	   
		String url = service.execute(memberVO, request);
	   	   
		logger.info("url: " + url); 
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
	
	//
	@RequestMapping("/admin_contact")
	public String admin_contact(Model model, ContactVO contactVO) {
		logger.info("admin_contact 컨트롤러");
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
		logger.info("admin_board 컨트롤러");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("adminBoard", Admin_boardService.class);
		service.execute(model);
		return "admin_board";
	}
	
	//관리자페이지(멤버) 넘어온 쿼리가 있을 시 검색 후, 없을 시에 전체 리스트 반환 
	@RequestMapping("/admin_member")
	public String admin_member(HttpServletRequest request, Model model) {
		logger.info("admin_member 컨트롤러");
		
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
		logger.info("admin_delete 컨트롤러");
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
		logger.info("admin_boardFilter 컨트롤러");
		model.addAttribute("category",category);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("adminBoardFilter", Admin_boardFilter.class);

		service.execute(model);
	
        Map<String, Object> resultMap = model.asMap();
        logger.info("resultMap"+resultMap+"");
        
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
		logger.info(contactVO+"");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ContactService service = ctx.getBean("adminContactSingle", Admin_contactSingleService.class);
		service.execute(contactVO,model);

		
		return "admin_contactSingle";
	}
	
	//upload 기능(파일 물리적 저장 및 글 정보 db저장) 후 myphoto.jsp로 반환
	@RequestMapping("/uploadOK")
	public String uploadOK(@RequestParam("file_name") MultipartFile file,
            @RequestParam("category") String category,
            @RequestParam("board_title") String board_title,
            @RequestParam("id") String id,
            @RequestParam("board_content") String board_content,
            Model model) {
		logger.info("uploadOK 컨트롤러");
		
	    String file_name = file.getOriginalFilename();

	    // 파일 업로드 처리
	    if (!file.isEmpty()) {
	        try {
	            String os = System.getProperty("os.name").toLowerCase();
	            String uploadDirectory;
	             if (os.contains("mac")) {
	                uploadDirectory = "/Users/jineunyoung/upload/";
	            } else {
	                uploadDirectory = "c:/upload/";
	            }

	            Path filePath = Paths.get(uploadDirectory, file_name);
	            Files.write(filePath, file.getBytes());
	            logger.info("upload경로는 여기"+uploadDirectory);
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("오류남 ");
	        }
	    } else { }

	    model.addAttribute("category", category);
	    model.addAttribute("board_title", board_title);
	    model.addAttribute("id", id);
	    model.addAttribute("board_content", board_content);
	    model.addAttribute("file_name", file_name); 

	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("upload", UploadService.class);

	    service.execute(model);
	    return "redirect:myphoto";
	}	
	
	//내가 올린 글 조회 페이지(첫번째 페이지로, 1페이지 분량의 글만 조회해서 model로 반환)
	@RequestMapping("/myphoto")
	public String myphoto(HttpServletRequest request, Model model) {

	    HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("mvo");
	    String id = mvo.getId();
	    model.addAttribute("id", id);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("myphoto", MyphotoService.class);
		
		service.execute(model);
		
		return "myphoto";
	}

	//내가 올린 글 조회 페이지(두번째 페이지부터, ajax를 통한 무한스크롤 구현을 위해 map객체에 담아 비동기전송)
	@ResponseBody
	@RequestMapping(value="/myphotoNext", method=RequestMethod.GET)
	public String myphotoNext(@RequestParam("id") String id, @RequestParam("currentPage") int currentPage, Model model) {
		logger.info("myphotonext 컨트롤러");
		model.addAttribute("id",id);
		model.addAttribute("currentPage",currentPage);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("myphotoNext", MyphotoNextService.class);

		service.execute(model);
	
        Map<String, Object> resultMap = model.asMap();
        logger.info("resultMap"+resultMap+"");
        
        String boardList = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            boardList = objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("boardList"+boardList);

        return boardList;
    }
	
	//내가 좋아요 한 글 조회 페이지(첫번째 페이지로, 1페이지 분량의 글만 조회해서 model로 반환)
	@RequestMapping("/like")
	public String like(HttpServletRequest request, Model model) {

	    HttpSession session = request.getSession();
	    MemberVO mvo = (MemberVO) session.getAttribute("mvo");
	    String id = mvo.getId();
	    model.addAttribute("id", id);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("like", LikeService.class);
		
		service.execute(model);
	
		return "like";
	}
	
	//내가 좋아요 한 글 조회 페이지(두번째 페이지부터, ajax를 통한 무한스크롤 구현을 위해 map객체에 담아 비동기전송)
	@ResponseBody
	@RequestMapping(value="/likeNext", method=RequestMethod.GET)
	public String likeNext(@RequestParam("id") String id, @RequestParam("currentPage") int currentPage, Model model) {
		logger.info("likenext 컨트롤러");
		model.addAttribute("id",id);
		model.addAttribute("currentPage",currentPage);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("likeNext", LikeNextService.class);
		
		service.execute(model);
		
		Map<String, Object> resultMap = model.asMap();
		logger.info("resultMap"+resultMap+"");
		
		String boardList = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			boardList = objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return boardList;
	}

	//카테고리를 받아 페이지네이션 후 최신순으로 리스트 반환
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("request",request);
		
		HttpSession session =request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("mvo");
	    if (memberVO != null) {
	        model.addAttribute("id", memberVO.getId());
	    } else {
	        model.addAttribute("id", "비로그인 사용자");
	    }
	    
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("list", ListService.class);
		
		service.execute(model);
				
		return "list";
	}

	//카테고리를 받아 페이지네이션 후 인기순으로 리스트 반환
	@RequestMapping("/list2")
	public String list2(HttpServletRequest request, Model model) {
		model.addAttribute("request",request);
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("mvo");
	    if (memberVO != null) {
	        model.addAttribute("id", memberVO.getId());
	    } else {
	        model.addAttribute("id", "비로그인 사용자");
	    }
	    
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("list2", List2Service.class);
		
		service.execute(model);
		
		return "list";
	}
   
   //contact.jsp에서 문의 메일 보내면 내용 upload 
   @RequestMapping("/contactOK")
   public String contackOK(Model model, ContactVO contactVO) {
		logger.info("contactOK 컨트롤러");
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    ContactService service = ctx.getBean("contactOK", ContactOKService.class);
		service.execute(contactVO,model);
	    model.addAttribute("message", "정상적으로 제출되었습니다. 감사합니다!");

	return "contact";
   }
   
   private final JavaMailSenderImpl mailSender;

   
   @Autowired
   public HomeController(JavaMailSenderImpl mailSender) {
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

 //수현======================================================================================================
	@RequestMapping("/joinOK")
	public String joinOK(HttpServletRequest request, Model model, MemberVO memberVO) {
	    logger.info("컨트롤러의 joinOK() 메소드 실행 - 커맨드 객체 사용");
	    

		String address = request.getParameter("address");
		String detailAddress = request.getParameter("detailAddress");
		String addr = address+" "+detailAddress;
		
	   AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	   MemberService service = ctx.getBean("insert", InsertService.class);
	   
	   memberVO.setAddr(addr);
	   
	   logger.info(memberVO.toString());
	   service.execute(memberVO);
		return "login";
	}

	//회원가입 - 아이디 닉네임 중복검사 (ajax사용) & 요청jsp로 반환 
	@RequestMapping(value="/idCheck", method = RequestMethod.POST)
	@ResponseBody
	   public String idCheck(@RequestParam("id") String id, Model model) {
        logger.info("idCheck 컨트롤러");
		model.addAttribute("id",id);
        AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
        // 회원 정보 저장 서비스 실행
        MemberService service = ctx.getBean("idcheck", IdcheckService.class);
        service.execute(model);
		
        Map<String, Object> resultMap = model.asMap();
        String response = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            response = objectMapper.writeValueAsString(resultMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
        
	}
	//회원가입 - 아이디 닉네임 중복검사 (ajax사용) & 요청jsp로 반환 
	@RequestMapping(value="/nicknameCheck", method = RequestMethod.POST)
	@ResponseBody
	public String nicknameCheck(@RequestParam("nickname") String nickname, Model model) {
		logger.info("nicknamecheck 컨트롤러");
		model.addAttribute("nickname",nickname);
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		// 회원 정보 저장 서비스 실행
		MemberService service = ctx.getBean("nicknamecheck", NicknamecheckService.class);
		service.execute(model);
		
		Map<String, Object> resultMap = model.asMap();
		String response = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return response;	
	}
	
//민아==================================================================================================

	@RequestMapping("/deleteAccount")
	public String deleteAccount(HttpServletRequest request, Model model,RedirectAttributes rttr) {
	    logger.info("deleteAccount 컨트롤러");		
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
	
//진욱==================================================================================================
	
	//	해당 idx번째 게시글을 들어갔을 때 코드 => 게시글의 작성자, 작성일, 내용, 조회수, 좋아요, 댓글등을 가져와야 함.
	@RequestMapping("/board-single")
	public String boardSingle(HttpServletRequest request, 
			@RequestParam("board_idx") int board_idx,
			Model model) {
		//	작성자, 작성일, 내용, 조회수, 좋아요, 댓글등을 가져와야 함.
		logger.info("컨트롤러의 board-single() 메소드 실행");
		String ip = request.getRemoteAddr();
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("mvo");
	    if (memberVO != null) {
	        model.addAttribute("member_id", memberVO.getId());
	    } else {
	        model.addAttribute("member_id", "비로그인 사용자");
	    }
	    
	    model.addAttribute("board_idx", board_idx);
	    model.addAttribute("ip", ip);
	            
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("boardSingleService", BoardSingleService.class);
	    service.execute(model);
	    
	    return "board-single";
	}

	
	//	수정하러 갈 페이지
	@RequestMapping("/update")
	public String update(@RequestParam("board_idx") int board_idx, Model model) {
		model.addAttribute("board_idx",board_idx);
		
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("boardSingle2Service", BoardSingle2Service.class);
		service.execute(model);
		
		return "update";
	}	
			
			
	//	board-single에서 수정하기를 눌렀을 때 발생하는 코드 구현 => 작성자가 올린 idx의 정보를 모두 가져와 화면에 출력
	@RequestMapping("/updateOK")
	public String updateOK(@RequestParam("file_name") MultipartFile file,
			@RequestParam("board_idx") int board_idx,
            @RequestParam("category") String category,
            @RequestParam("board_title") String board_title,
            @RequestParam("id") String id,
            @RequestParam("board_content") String board_content,
            Model model, RedirectAttributes redirect) {
		logger.info("updateOK 컨트롤러");
		
	    String file_name = file.getOriginalFilename();

	    // 파일 업로드 처리
	    if (!file.isEmpty()) {
	        try {
	            String os = System.getProperty("os.name").toLowerCase();
	            String uploadDirectory;
	             if (os.contains("mac")) {
	                uploadDirectory = "/Users/jineunyoung/upload/";
	            } else {
	                uploadDirectory = "c:/upload/";
	            }  
	            Path filePath = Paths.get(uploadDirectory, file_name);
	            Files.write(filePath, file.getBytes());
	            logger.info(uploadDirectory);
	        } catch (IOException e) {
	            e.printStackTrace();
	            logger.info("오류남 ");
	        }
	    } else { }

	    model.addAttribute("board_idx", board_idx);
	    model.addAttribute("category", category);
	    model.addAttribute("board_title", board_title);
	    model.addAttribute("id", id);
	    model.addAttribute("board_content", board_content);
	    model.addAttribute("file_name", file_name); 

	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("boardUpdateService", BoardUpdateService.class);

	    service.execute(model);
		
	    redirect.addAttribute("board_idx",board_idx);
	    
		return "redirect:board-single";
	}

	//	게시글 삭제 메소드
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("board_idx") int board_idx) {
		logger.info("delete 컨트롤러");
		logger.info("board_idx: " + board_idx);
		
		model.addAttribute("board_idx", board_idx);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("boardDeleteService", BoardDeleteService.class);
		
	    service.execute(model);
	    
	    return "list";
	}
	// 업데이트 정보를 갖고 있는 Map
    private static Map<String, Boolean> updateInfoMap = new ConcurrentHashMap<>();

    
	//	댓글 작성메소드
	@RequestMapping(value = "/comment", method = RequestMethod.GET)
	public String comment(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("board_idx") int board_idx,
			@RequestParam("id") String id,
			@RequestParam("comment_content") String comment_content) {
		
		model.addAttribute("board_idx", board_idx);
		model.addAttribute("id", id);
		model.addAttribute("comment_content", comment_content);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("boardCommentService", BoardCommentService.class);
		
	    service.execute(model);
	    
		// 업데이트 정보에 올려두기. 
        updateInfoMap.put(id, true);
	    
		return "redirect:board-single";
	}
	
	// 클라이언트 요청이 오면 getOrDefault로 Map에 해당 id에 대한 업데이트 정보가 있는지 확인 후 return 
	@RequestMapping(value = "/api/check-update", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Boolean>> checkUpdate(@RequestParam("userId") String userId) {
        // 클라이언트가 요청 시 업데이트 여부 확인
        Boolean update = updateInfoMap.getOrDefault(userId, false);
        updateInfoMap.put(userId, false); // 업데이트 여부 초기화
        Map<String, Boolean> response = Collections.singletonMap("update", update);
        return ResponseEntity.ok(response);
    }

	
	//	댓글 삭제 메소드
	@RequestMapping(value = "/deleteComment", method = RequestMethod.GET)
	public String deleteComment(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("board_idx") int board_idx,
			@RequestParam("comment_idx") String comment_idx) {
		
		model.addAttribute("board_idx", board_idx);
		model.addAttribute("comment_idx", comment_idx);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("boardDeleteCommentService", BoardDeleteCommentService.class);
		
		service.execute(model);
		return "redirect:board-single";
	}
	
//  게시글 신고
  @RequestMapping(value = "/report", method = RequestMethod.GET)
  public String report(Model model, HttpServletRequest request, 
        @RequestParam("board_idx") int board_idx) {
     System.out.println("(************************************ board idx=" + board_idx);
     logger.info("컨트롤러의 report 메소드 실행");
     logger.info("컨트롤러의 report 메소드 board_idx: " + board_idx);
     String ip = request.getRemoteAddr();
     logger.info("컨트롤러의 report 메소드 ip: " + ip);
     
     model.addAttribute("board_idx", board_idx);
     model.addAttribute("ip", ip);
     
     AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
     BoardService service = ctx.getBean("reportService", ReportService.class);
     
     service.execute(model);
     return "redirect:board-single";
  }
	
	//	게시글 좋아요
	@RequestMapping(value = "/boardLike", method = RequestMethod.GET)
	public String boardLike(Model model, HttpSession session,
			@RequestParam("board_idx") int board_idx) {
		logger.info("컨트롤러의 boardLike 메소드 board_idx: " + board_idx);
		
		MemberVO mvo = (MemberVO) session.getAttribute("mvo"); 
		String member_id = mvo.getId();
		
		model.addAttribute("board_idx", board_idx);
		model.addAttribute("member_id", member_id);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("boardLikeService", BoardLikeService.class);
		
		service.execute(model);
		
		// 업데이트 정보에 올려두기. 
        updateInfoMap.put(member_id, true);
        
		return "redirect:board-single";
	}
	
//  대댓글
  @RequestMapping(value = "/reply", method = RequestMethod.GET) 
  public String reply(Model model, HttpSession session, HttpServletRequest request,
        @RequestParam("board_idx") int board_idx,
        @RequestParam("comment_idx") int comment_idx,
        @RequestParam("id") String id,
        @RequestParam("reply_content") String reply_content) {
     
     model.addAttribute("board_idx", board_idx);
     model.addAttribute("comment_idx", comment_idx);
     model.addAttribute("id", id);
     model.addAttribute("reply_content", reply_content);
     
     AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
      ReplyService service = ctx.getBean("replyService", ReplyService.class);
     
      service.execute(model);
     
     return "redirect:board-single";
  }
	//대댓글 삭제
	@RequestMapping(value = "/deleteReply", method = RequestMethod.GET) 
	public String replyDelete (Model model, HttpSession session, HttpServletRequest request,
	      @RequestParam("board_idx") int board_idx,
	      @RequestParam("reply_idx") int reply_idx) {
	   
	   model.addAttribute("board_idx", board_idx);
	   model.addAttribute("reply_idx", reply_idx);
	   
	   AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	   ReplyDeleteService service = ctx.getBean("replyDeleteService", ReplyDeleteService.class);
	   
	   service.execute(model);
	   
	   return "redirect:board-single";
	}

//은영(추가)=======================================================
	//게시글 좋아요(리스트에서 ajax통신)
	@RequestMapping(value = "/listBoardLike", method = RequestMethod.POST)
	@ResponseBody
	public String listBoardLike(HttpSession session,
	        @RequestParam("board_idx") int board_idx, Model model) {
	    logger.info("컨트롤러의 boardLike 메소드 board_idx: " + board_idx);
	    
	    MemberVO mvo = (MemberVO) session.getAttribute("mvo"); 
	    String member_id = mvo.getId();
	    
	    model.addAttribute("board_idx", board_idx);
	    model.addAttribute("member_id", member_id);
	    
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    BoardService service = ctx.getBean("boardLikeService", BoardLikeService.class);
	    
	    service.execute(model);
	    
	    if (model.containsAttribute("result")) {
	    return "success_add"; // 결과 값을 반환합니다.
	    } else {
	    return "success_cancel";}
	    

	}
	
	//알람 수
	@ResponseBody
	@RequestMapping(value = "/alarmCount", method=RequestMethod.GET)
	public Map<String, Integer> alramCount(String to_id) {
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    AlarmCountService service = ctx.getBean("alarmCount", AlarmCountService.class);             
	    int count = service.execute(to_id);
	    
	    Map<String, Integer> response = new HashMap<>();
	    response.put("count", count);
	    
	    return response;
	}

	
	
	//알람목록
	@ResponseBody
	@RequestMapping(value = "/alarmList", method=RequestMethod.GET)
	public List<AlarmVO> alarmList(String memberId) {
	    logger.info("ddd" + memberId);

		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmService service = ctx.getBean("alarm", AlarmService.class);				
		System.out.println(service.execute(memberId));
		return service.execute(memberId);
	}	
	
	
	//알람개별삭제
	@ResponseBody
	@RequestMapping(value = "/alarmDelete", method=RequestMethod.POST)
	public String alarmDelete(int alarm_idx) {
		logger.info("알람클릭->지우기");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDeleteService service = ctx.getBean("alarmDelete", AlarmDeleteService.class);				
		service.execute(alarm_idx);
		
	    return "";
	}
	
	//알람전체삭제
	@ResponseBody
	@RequestMapping(value = "/alarmDeleteAll", method=RequestMethod.POST)
	public String alarmDeleteAll(String from_id) {
		logger.info("알람전체지우기");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		AlarmDeleteAllService service = ctx.getBean("alarmDeleteAll", AlarmDeleteAllService.class);				
		service.execute(from_id);
		
		return "";
	}
	

	//채팅창리스트 전부
	@ResponseBody
	@RequestMapping(value="/chatList", method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String chatList(@RequestParam("from_id") String from_id, Model model) {
		logger.info("chatList 컨트롤러");
		model.addAttribute("from_id",from_id);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatListService service = ctx.getBean("chatList", ChatListService.class);

		Map<String, Object> resultMap = service.execute(model);
	
        logger.info("resultMap"+resultMap+"");
        
        String chatList = "";
        try {
        	
            ObjectMapper objectMapper = new ObjectMapper();
            chatList = objectMapper.writeValueAsString(resultMap);
        	} catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return chatList;
    }
	//개별 채팅 1건의 메시지 리스트 
	@ResponseBody
	@RequestMapping(value="/chatContent", method=RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public String chatContent(@RequestParam("chat_idx") int chat_idx, @RequestParam("user") String user, Model model) {
		logger.info("chatContent 컨트롤러");
		model.addAttribute("chat_idx",chat_idx);
		model.addAttribute("user",user);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatContentService service = ctx.getBean("chatContent", ChatContentService.class);
		
		Map<String, Object> resultMap = service.execute(model);
		
		logger.info("resultMap"+resultMap+"");
		
		String chatContent = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			chatContent = objectMapper.writeValueAsString(resultMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return chatContent;
	}
	//채팅방으로 가기 전에 이미 존재하는지 체크 
	@RequestMapping("/chatCheck")
	public String chatCheck(@RequestParam("from_id") String from_id,@RequestParam("to_id") String to_id, Model model) {
		model.addAttribute("from_id",from_id);
		model.addAttribute("to_id",to_id);
		
	    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	    ChatCheckService service = ctx.getBean("chatCheck", ChatCheckService.class);
		service.execute(model);
		
		return "chat";
	}	


}
