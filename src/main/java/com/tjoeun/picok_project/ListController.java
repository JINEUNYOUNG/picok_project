package com.tjoeun.picok_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjoeun.service.board.BoardLikeService;
import com.tjoeun.service.board.BoardService;
import com.tjoeun.service.board.UploadService;
import com.tjoeun.service.list.LikeNextService;
import com.tjoeun.service.list.LikeService;
import com.tjoeun.service.list.List2Service;
import com.tjoeun.service.list.ListService;
import com.tjoeun.service.list.MyphotoNextService;
import com.tjoeun.service.list.MyphotoService;
import com.tjoeun.vo.MemberVO;

@Controller
public class ListController {
	
	//게시글 좋아요(리스트에서 ajax통신)
	@RequestMapping(value = "/listBoardLike", method = RequestMethod.POST)
	@ResponseBody
	public String listBoardLike(HttpSession session, @RequestParam("board_idx") int board_idx, Model model) {
	    
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
	//upload 기능(파일 물리적 저장 및 글 정보 db저장) 후 myphoto.jsp로 반환
	@RequestMapping("/uploadOK")
	public String uploadOK(@RequestParam("file_name") MultipartFile file,
            @RequestParam("category") String category,
            @RequestParam("board_title") String board_title,
            @RequestParam("id") String id,
            @RequestParam("board_content") String board_content,
            Model model) {
		
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
	        } catch (IOException e) {
	            e.printStackTrace();
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
		model.addAttribute("id",id);
		model.addAttribute("currentPage",currentPage);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("myphotoNext", MyphotoNextService.class);

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
		model.addAttribute("id",id);
		model.addAttribute("currentPage",currentPage);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("likeNext", LikeNextService.class);
		
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
   
	

}
