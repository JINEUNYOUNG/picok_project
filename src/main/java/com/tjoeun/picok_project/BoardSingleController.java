package com.tjoeun.picok_project;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tjoeun.service.board.BoardCommentService;
import com.tjoeun.service.board.BoardDeleteCommentService;
import com.tjoeun.service.board.BoardDeleteService;
import com.tjoeun.service.board.BoardLikeService;
import com.tjoeun.service.board.BoardService;
import com.tjoeun.service.board.BoardSingle2Service;
import com.tjoeun.service.board.BoardSingleService;
import com.tjoeun.service.board.BoardUpdateService;
import com.tjoeun.service.board.ReplyDeleteService;
import com.tjoeun.service.board.ReplyService;
import com.tjoeun.service.board.ReportService;
import com.tjoeun.vo.MemberVO;

@Controller
public class BoardSingleController {

	//	해당 idx번째 게시글을 들어갔을 때 코드 => 게시글의 작성자, 작성일, 내용, 조회수, 좋아요, 댓글등을 가져와야 함.
	@RequestMapping("/board-single")
	public String boardSingle(HttpServletRequest request, 
			@RequestParam("board_idx") int board_idx,
			Model model) {
		//	작성자, 작성일, 내용, 조회수, 좋아요, 댓글등을 가져와야 함.
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
  public String report(Model model, HttpServletRequest request, @RequestParam("board_idx") int board_idx) {
     
	  String ip = request.getRemoteAddr();
     
     model.addAttribute("board_idx", board_idx);
     model.addAttribute("ip", ip);
     
     AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
     BoardService service = ctx.getBean("reportService", ReportService.class);
     
     service.execute(model);
     return "redirect:board-single";
  }
	
	//	게시글 좋아요
	@RequestMapping(value = "/boardLike", method = RequestMethod.GET)
	public String boardLike(Model model, HttpSession session, @RequestParam("board_idx") int board_idx) {
		
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

}
