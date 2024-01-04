<img width="206" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/2c545e6a-e876-47a4-b383-f625453bd322" align="right">



# 📷 사진공유 기반 소셜미디어 웹사이트 Picok

      : JAVA(Spring) + Oracle 을 이용한 웹사이트 개발 프로젝트    
      
<img width="658" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/5057f891-8d96-48af-9eb7-8b917c00f1da" align="right">

# 🔎 프로젝트개요


1. 프로젝트명    
:     Picok [Picture + “콕”]

2. 수행기간       
:     23.06.20~23.09.15

3. 수행인원       
:     4명

4. 프로젝트 소개  
:     개인 사진 아카이브 및 공유, 커뮤니케이션을 포함한 웹사이트 개발 프로젝트  
      - 이미지 업로드를 포함한  CRUD 게시판 
      - 로그인(일반/관리자) / 비로그인 유저를 구분하여 화면 구축  
      - 좋아요/신고/댓글+채팅(웹소켓) 기능을 통한 커뮤니케이션 기능 추가  
      - 뷰페이지는 SSR + ajax를 통한 동적 업데이트  

5. 담당구현기능
      - DB 테이블 구성 
      - 헤더, 푸터 구현
      - 이미지를 포함한 업로드 & 사진게시판리스트(최신순, 인기순) & 페이지네이션 구현
      - 관리자페이지(회원관리, 게시글관리, 문의글관리) 구현
      - 마이페이지(좋아요 리스트, 내가 올린 글 리스트) 구현  
      - 댓글&좋아요 알람 구현
      - 실시간채팅 구현
      - 간단좋아요 & 공지글 팝업 구현
  
  
# ⚒️ 개발환경  
      ∙ 개발환경 : Java 1.8 / STS3 / Maven / DBeaver  
      ∙ 서버 : Apache Tomcat 8.5  
      ∙ 디자인패턴 : MVC2  
      ∙ 통신 : ajax / formSubmit / WebSocket  
      ∙ 데이터베이스 : ORACLE / JDBC   
      
# 🗣️ 협업툴 
      : Notion / Discord / Github


# 📑 기능명세서
<div align="center">
   <img width="960" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/20be51dd-bdf9-4dd6-b68f-b383f5137d4c">  
</div>   


# 🖥️ 화면설계
<div align="center">
  <img width="960" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/c8565f53-7128-4dae-a97d-399d0212b145">
</div>   

# 💾 DB설계
<div align="center">
  <img width="960" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/7a208d03-870a-4646-97ef-021ff0d8d363">

</div>   


# 🚨 개발노트       

<details> <summary>무한스크롤 (like 및 myphoto)</summary>


![Dec-19-2023 14-27-15](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/72c7e9d1-2702-424b-b036-f67c2eccd315)  

: 보기 편한 갤러리를 생각하다 생각난 무한스크롤, 썸네일로만 배치해서 myphoto나 like 페이지에 구현했다.  

- 스크롤을 진행하면서 새로운 데이터가 동적으로 로드되는 것이 좀 더 풍부한 경험으로 느껴졌다. (기존의 게시판은 페이지네이션으로 이루어져 있다.)
- 처음 로딩 시 가져오는 글이 4*2개로, 한 번에 많은 데이터를 로드할 필요가 없어 좋았다.

### LOGIC

1. **처음 페이지 로딩 시, 조건에 맞는 사진 4*2개를 가져와 배치한다.**   
2. **스크롤이벤트리스너를 등록해서 바닥에 닿으면, 추가 데이터를 로딩하는 ajax를 호출한다.**  
3. **다음  4*1개의 글 정보를 가져오는데 성공하면, html에 추가해 주는 함수를 부른다.**    

```jsx
<script>
var isLoading = false; // 데이터 로딩 중인지 여부를 확인하기 위한 변수

window.addEventListener('scroll', handleScroll); // 스크롤 이벤트 리스너 등록

// 스크롤 이벤트 핸들러 (스크롤이 맨 아래에 도달했을 때 추가 데이터 로딩)
function handleScroll() {
	if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
		loadMoreData();
	}
}

// 추가 데이터를 가져오는 ajax함수
function loadMoreData() {
	  var id = '<%= userId %>';

	  if (isLoading) { // 아직 로딩 중이라면, 돌아간다.
	    return;
	  } 
	  
	 isLoading = true; 

// id와 현재페이지 정보를 가져가 다음 게시글 데이터를 JSON으로 가져오는 ajax 호출 
	  $.ajax({
		    url: '/picok_project/likeNext',
		    type: 'GET', // GET 요청으로 설정
		    data: {
		      id: id,
		      currentPage: currentPage,
	    	},
		    success: function (data) {
			      updateUI(data); // UI 업데이트 함수 
			      var jsonData = JSON.parse(data);
			      //현재 페이지가 마지막이면, 이벤트 리스너 삭제(바닥에 닿아도 더 호출하지 않는다.)
			      if (currentPage >= jsonData.boardList.totalPages + 1) { 
				        window.removeEventListener('scroll', handleScroll);
			      }

			      currentPage++; // 페이지+1
			      isLoading = false; // 로딩 끝 
			      },
		    error: function (error) {
		    	console.error(error);
		    },
		  });
		}
}
</script>
```

</details>  

<details> <summary>관리자 : 게시글관리 / 회원관리 / 문의글관리</summary>  

**게시글관리**  

![admin_board](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/7d9fa8d2-1736-4b1d-9e14-cb44e8233ac7)  

**회원관리**  

![admin_member](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/fa5d031b-346b-4275-bd6f-f3c432566e31)  

**문의글작성**  

![qna_q](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/8c9e6e8c-0029-438c-a863-c6cb9848536e)  

**문의글관리**  

![qna_a](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/edbf7945-3597-491c-8ff8-7f68d67dc007)  

  
### **POINT**

1. 관리자 계정으로 로그인 시에, 해당 관리화면으로 접근할 수 있도록
2. 다중 선택 및 삭제 (ajax)
3. 카테고리별 로딩 (ajax)
4. 회원검색 
5. 문의글 답변 시 네이버메일 발신 (JavaMailSenderImpl)

- 다중 선택 및 삭제

```jsx
// 게시글 다중삭제 함수
function deleteData() {
    var checkboxes = document.getElementsByClassName('checkbox');

    var selectedIds = [];

    // forEach로 체크된 데이터의 idx를 가져와 배열안에 추가한다 
    Array.prototype.forEach.call(checkboxes, function(checkbox) {
        if (checkbox.checked) {
            var boardIdx = checkbox.getAttribute('data-board-idx');
            selectedIds.push(boardIdx);
        }
    });

    // 선택된 글이 있다면 컨트롤러로 보내 삭제하는 함수를 호출한다
    if (selectedIds.length === 0) {
        alert('선택된 글이 없습니다.');
    } else {
        var xhr = new XMLHttpRequest();
        var url = '/picok_project/admin_delete'; 
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert('삭제 요청이 성공적으로 완료되었습니다.');
                    window.location.reload(); 
                } else {
                    alert('삭제 요청이 실패하였거나 오류가 발생하였습니다.');
                }
            }
        };
        xhr.send('postIds=' + encodeURIComponent(selectedIds.join(',')));
				// 배열들의 값을 ,로 결합해서 POST방식으로 보낸다
    }
}
```

- 메일 발신

```jsx
private final JavaMailSenderImpl mailSender;
   
   @Autowired
   public HomeController(JavaMailSenderImpl mailSender) {
       this.mailSender = mailSender;
   }

   @RequestMapping("/sendEmail")
   public String sendEmail(HttpServletRequest request, Model model) {
		// 답변 작성 내용은 폼에서 파라미터로 받아온다
		String subject =request.getParameter("reply-title");
		String content = request.getParameter("reply-content");
		String to = request.getParameter("reply-email");
		String idx = request.getParameter("reply-idx");

		// 발신인은 mailSender에 지정된 이름을 가져온다(servlet-context에 JavaMailSender bean을 설정해두었다)
		String from = mailSender.getUsername(); 

		model.addAttribute("idx",idx);
		model.addAttribute("content",content); // 회신완료으로 변경 용 
		
		try {
				MimeMessage mail = mailSender.createMimeMessage();
				MimeMessageHelper mailHelper = new MimeMessageHelper(mail, "UTF-8");
	
				// 보내는 사람, 받는 사람, 제목, 내용을 셋팅
				mailHelper.setFrom(from);	
				mailHelper.setTo(to);	
				mailHelper.setSubject(subject);	
				mailHelper.setText(content);	
	
				mailSender.send(mail);
			
		    AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		    ContactService service = ctx.getBean("sendEmail", SendEmailService.class);
				// idx랑 content를 갖고 가 contact DB에 회신완료로 변경하는 service
				service.execute(model); 
		    model.addAttribute("message", "메일 발송완료 되었습니다.");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "redirect:admin_contact";
	}
```

</details>

<details> <summary>아이콘으로 간단좋아요 (ajax) </summary>  

![like](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/ccff2e11-bbb9-461e-9914-455b9cd4dbba)  


### LOGIC

1. 페이지 로딩 시, 해당 페이지에 맞는 게시글 정보에 추가로 해당 페이지 분량의 좋아요 상태를 boolean으로 가져온다. 
2. 아이콘 클릭 시 현재 좋아요 상태에 따라 좋아요를 추가 혹은 취소하는 ajax를 호출한다.

```jsp
<!-- 비회원이 아니고 + 글 작성자가 아닌 경우에는 하트 표시 (좋아요를 눌렀는지 likdesList를 받아와서 유무로 아이콘을 바꿔껴줌) -->
<c:when test="${sessionScope.mvo != null && mvo.id != board.getId()}">
	<img id="heart" src="assets/img/${likesList[i.index] ? 'like_on' : 'like_off'}.png" 
	style="width: 30px; float: right; cursor: pointer;"
	title="${likesList[i.index] ? 'on' : 'off'}" 
	data-board-idx="${board.getBoard_idx()}" 
	data-board-title="${board.getBoard_title()}" 
	data-id="${board.getId()}" 
	data-login="${mvo.id}" 
	onclick="like(this)">
</c:when>

<!-- 비회원상태 or 글 작성자 인 경우 하트 아에 생략 -->
<c:otherwise></c:otherwise>
```

아이콘 클릭 시에 간단 좋아요 구현 부분  
```jsx
//좋아요 추가 or 취소하는 ajax 요청을 보냄 (XMLHttpRequest사용), 통신 성공 시 아이콘도 바꿔껴준다.

function like(element) {
	var boardIdx = element.getAttribute("data-board-idx");

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				if (xhr.responseText === "success_add") {
					console.log("OK");
					//통신 성공 시, 현재 아이콘 상태 확인 후 변경해줌.
					const isHeart = element.getAttribute("title") === "on";
					if (isHeart) {
						element.setAttribute('src', 'assets/img/like_off.png');
						element.setAttribute('title', 'off');
					} else {
						element.setAttribute('src', 'assets/img/like_on.png');
						element.setAttribute('title', 'on');
					}
				} else {
					console.log("취소한 건")
				}
			} else {
				console.error("Ajax 요청 실패");
			}
		}
	};

	xhr.open('POST', 'listBoardLike', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send('board_idx=' + boardIdx);
}
```
</details>  

<details> <summary>실시간채팅(웹소켓) </summary>  

![chat](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/a245c1f1-b6a9-4813-9bb8-fbeee5136872)  

### 구현 POINT

1. 좌측 채팅방리스트는 주기적 ajax 호출로 업데이트 + 채팅창은 로딩 시 db에서 가져오고 이 후는 소켓을 통해 서버에서 실시간 메세지를 받아 처리해준다
2. 채팅방 나가기 시, 나간 유저의 id를 기억해두어 양 쪽 모두 나가기 완료 시에만 채팅방이 삭제되도록
3. 상대의 닉네임 옆 아이콘 클릭하여 채팅방에 접근 시, 기존에 있는 채팅방인지 확인 후 없을 시에 새로 insert 해준다
4. 좌측 채팅방은 최근 업데이트 순으로 정렬되며, 읽지 않은 메시지는 붉게 표시된다  

### LOGIC(기본 채팅)

1. 채팅방에 접근(닉네임 옆 채팅 아이콘 클릭 or 헤더에 채팅 메뉴 클릭) 
2. 로그인 상태 체크하고 소켓이 열림
3. 좌측 채팅방 리스트 중 채팅방을 하나 선택하면 DB를 돌아 해당하는 채팅방의 메시지를 가져오는 함수를 호출한다.
4. 메세지 전송 시 html에 추가해주고, 소켓을 통해 값을 서버로 보낸다 
5. 서버에 올라온 메시지가 있을 시(내가 보낸 것이 아닌), 화면에 추가해 주는 함수를 호출한다. 

- 소켓을 통해 실시간 통신하는 부분

```jsx
<script>
let url ="alarm";
let ws;

// 로그인 확인 하고 소켓 초기화
if ($('#user').val().trim() != '') {
    ws = new SockJS(url);

    // 1 소켓이 열릴 떄 		
   	ws.onopen = function () {
   	onsole.log('서버 연결 성공');
	};
       
	// 2 서버에 올라온 메시지가 있을 시
	ws.onmessage = function(event) {
		console.log(event);
		
		var parts = event.data.split('/');
		var id = parts[0];
		var msg = parts[1];
		if (id != $('#user').val()){
		print1(id, msg);} //상대방의 채팅 추가해주는 함수 print1
		
		$('#chatContent').scrollTop($('#chatContent').prop('scrollHeight'));
	};
	
	// 3 소켓이 닫힐 시 
	ws.onclose = function() {
		console.log('웹 소켓 종료');
	};

	// 4 에러 발생시
	ws.onerror = function (evt) {
		console.log(evt.data);
	};
}

	
// 메시지를 보내면 웹소켓을 통해 값을 서버로 보내줌(유저id + 메시지)
$('#msg').keydown(function() {
	if (event.keyCode == 13) {
		var message = $(this).val();
		var userId = $('#user').val();
		const data = {
		action: 'ChatInfo2',
		from_id: userId,
		message: message
		};
		ws.send(JSON.stringify(data)); // 서버에 메세지를 보낸다 
		print($('#user').val(), $(this).val()); //내 채팅 추가해주는 함수 print
		$('#msg').val('');
		$('#msg').focus();
		$('#chatContent').scrollTop($('#chatContent').prop('scrollHeight'));
	}
});
```

```java
//세션에 올라온 메시지를 받아 처리하는 클래스 
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException{
		String payload = message.getPayload();
		ObjectMapper objectMapper = new ObjectMapper(); 
		JsonNode jsonNode;
       
		jsonNode = objectMapper.readTree(payload);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		ChatInsertService service = ctx.getBean("chatInsert", ChatInsertService.class);
		ChatExitService service2 = ctx.getBean("chatExit", ChatExitService.class);

		// 채팅 메시지 가져오는 중(받은 3가지 정보 모두 가져오기)

		// 가장 처음 채팅방 접근 시 chat_idx를 받아와 세션에 저장해 둔다
		if (jsonNode.has("chat_idx")&&jsonNode.has("action")) { 
		    int chat_idx = Integer.parseInt(jsonNode.get("chat_idx").asText());
		    session.getAttributes().put("chat_idx", chat_idx);

		// 채팅 메세지를 입력 했을 때, id와 메세지를 받아와 db에 insert  
		} else if (!jsonNode.has("chat_idx")&&jsonNode.has("action"))  { 
			String from_id = jsonNode.get("from_id").asText();
			String message2 = jsonNode.get("message").asText();
			int chat_idx = (Integer)session.getAttributes().get("chat_idx");
			service.execute(from_id, chat_idx, message2);

      // 해당 채팅방에 속한 세션인 경우, 메시지 전송
      for (WebSocketSession clientSession : sessions) {
          if (clientSession.isOpen() && chat_idx == (Integer) clientSession.getAttributes().get("chat_idx")) {
              clientSession.sendMessage(new TextMessage(from_id + "/" + message2));
          }
      }

//...

	}
```

- 채팅방리스트를 ajax를 호출해 가져오는 부분
    - 주기적으로 ajax 호출하여 업데이트 되도록 구현했으나, 빈페이지가 잠시 떴다 적용되는 식의 플래시 현상으로 Promise를 추가구현했다

```jsx
//전체 채팅방 리스트를 띄워주는 함수 
function chatList() {
    console.log("chatList");

    // 채팅 컨테이너 초기화를 비동기로 처리하는 Promise 생성
    function initializeChatContainerAsync() {
        return new Promise((resolve) => {
            $.ajax({
                url: '/picok_project/chatList',
                type: 'get',
                data: { 'from_id': $('#user').val() },
                dataType: "json",
                success: function (chatList) {
                    // chatList에서 chatVO 목록을 가져오기
                    var chatVOList = chatList.chatList;

                    // 메시지를 미리 생성
                    var chatItems = [];

                    // chatVOList를 순회하면서 HTML 생성 및 추가
                    chatVOList.forEach(function (chatVO) {
										
										// ... 생략

                    });

                    // 초기화가 완료되면 메시지를 한 번에 추가
                    $('#chatContainer').html(chatItems.join(''));

                    // 클릭하면 개별 채팅창을 표시해주는 chatContent함수 호출 
                    $('.chat-item').click(function () {
                        var chatIdx = $(this).data('chat-idx');
                        var chatId = $(this).data('id');
                        chatContent(chatIdx, chatId);
                    });
                    resolve(); 
                }
            });
        });
    }

	  // 채팅 컨테이너 초기화 작업을 비동기로 수행 (플래시현상 때문에 추가함)
	  initializeChatContainerAsync()
	      .then(() => {
	      })
	      .catch((error) => {
	          console.error('채팅 컨테이너 초기화 중 오류 발생:', error);
	      });
}
```

</details>  

<details> <summary>트러블슈팅 1 실시간 알림 로직변경 </summary>  
	
  ![alarm](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/b8aa301d-abab-4c25-bd40-c04dd012e84d)


### **ISSUE**  
처음 로직은 단순하게 30초마다 ajax를 호출해 alarmList를 가져오도록 짰는데, 여기서 문제는 알람DB에 매번 접속 해서 조회 후 가져오는 방식이 성능에 매우 좋지 않아 보였다.  (알람을 위해 실시간 웹소켓 대신 ajax로 주기적 폴링하는 방식을 채택했다. 알람 특성상 어느정도 실시간만 보장되어도 괜찮지 않을까 하는 생각으로..)  


### **SO,**    
업데이트 된 경우에만 DB를 돌 수 있도록, 사용자별 업데이트 여부를 맵(Map)으로 넣어 관리하는 방식으로 바꿔보았다  
클라이언트에서는 주기적으로 이 맵에 접근해서 업데이트여부를 확인 후 true인 경우에 DB에 접근하여 데이터를 가져오는 것  

### **LOGIC** 
1. 댓글작성, 좋아요추가 성공 시에 업데이트 여부를 맵에 저장해 둔다. ‘{id : update여부(boolean)}‘형태  
2. 맵에 접근 하여 업데이트 여부를 확인하고(이 후 바로 초기화) true인 경우에만 DB에 접근해 알람 리스트를 가져온다. 

```java
// 업데이트 정보를 갖고 있는 Map
    private static Map<String, Boolean> updateInfoMap = new ConcurrentHashMap<>();

//	게시글 좋아요 insert
	@RequestMapping(value = "/boardLike", method = RequestMethod.GET)
	public String boardLike(Model model, HttpSession session, int board_idx) {
		
		// .. 좋아요 insert 기능 부분(service단으로 연결)
		
		// 업데이트 정보를 맵에 올려두기. 
        updateInfoMap.put(member_id, true);
        
		return "redirect:board-single";
	}

// 클라이언트 요청이 오면 getOrDefault로 Map에 해당 id에 대한 업데이트 정보가 있는지 확인 후 return 
	@RequestMapping(value = "/api/check-update", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Boolean>> checkUpdate(@RequestParam("userId") String userId) {

        Boolean update = updateInfoMap.getOrDefault(userId, false); // id를 갖고가서 업데이트 여부를 가져온다. 기본 false
        Map<String, Boolean> response = Collections.singletonMap("update", update); // 업데이트 여부를 response에 담아

        updateInfoMap.put(userId, false); // 업데이트 여부 초기화

        return ResponseEntity.ok(response);
    **}**
```

```jsx
// 처음 로딩 시에 알람리스트 가져오기 (이후에는 업데이트가 있을 경우)
$(document).ready(function() {
	alarmList();
});

// 로그인 한 경우, 알람 업데이트가 있는지 30초마다 돌려보기
if (memberId != ""){
	setInterval(checkUpdate, 30000);
}

// 알림 업데이트가 있는지 서버에 api 호출
function checkUpdate() {
        $.ajax({
            type: "GET",
            url: "/picok_project/api/check-update",
            data: { userId: memberId }, // 현재 사용자의 ID 전달
            success: function(response) {
                if (response.update) {
                	if (response.update) { 
                		alarmList(); // alarm db를 돌아 리스트를 가져오는 함수 
                		// console.log("update=true");
                	} else {
                		// console.log("update=false");
                	}
                } else {
                	// console.log("None");
                }
            },
            error: function(error) {
                console.error("업데이트 확인 실패:", error);
            }
        });
}
```

</details>
<details> <summary>트러블슈팅 2 페이지다운  </summary>  
	
### **ISSUE**
	
- 초반 웹페이지 접속 시, 문제없이 잘 구동되다가 갑자기 페이지가 하얗게 비는 이슈가 있었다. 어떤 조건에서 오류가 발생한건지 알아내기가 어려웠지만 디버깅끝에 결국 특정 로직을 몇 번 돌면 발생하는 것을 찾아내었다.
- 헤더의 게시판 이동 메뉴 *10 시 발생하는 것을 알 수 있었는데, 코드를 살펴보니 게시글 데이터를 가져오기 위해 db 연결 후 connection close를 누락한 것.  (종료되지 않은 커넥션풀이 점점 중첩되어 maxactive(10이었다)를 초과)
- 커넥션풀 고갈로 새로운 요청이 처리 되지 못해 흰 화면이 표시된 것이었다. 
  
```java  
<!-- context.xml에서 connection pool을 관리한다. -->
<?xml version="1.0" encoding="UTF-8"?>

<Context>

<WatchedResource>WEB-INF/web.xml</WatchedResource>
<WatchedResource>${catalina.base}/conf/web.xml</WatchedResource>

<!-- name : DBCP 이름, 커넥션 풀 이름, "jdbc/"는 항상 똑같이 쓰고 "/" 뒤에 커넥션 풀 이름을 적는다. 
     type : 데이테베이스 연결에 사용할 자바 클래스 이름, 반드시 이 클래스를 사용해야 한다. 
     auth : DBCP를 톰캣이 관리한다는 의미로 Container라 적어준다. 
     maxActive : 데이터베이스 연결 풀의 최대값, 최대 연결 허용 개수 
     maxIdle : 접속을 유지하는 데이터베이스 연결 풀의 최대 개수, 항상 연결을 유지하는 풀의 개수 
     maxWait : 데이터베이스 접속을 위해서 기다리는 최대 시간, -1을 쓰면 대기 시간 없이 바로 접속한다. 
     username : 사용자 계정 
     password : 사용자 비밀번호 
     driverClassName : 데이터베이스 드라이버 클래스 이름 
     url : 데이터베이스 접속을 위한 경로 -->

<Resource name="jdbc/oracle" auth="Container" type="javax.sql.DataSource" maxActive="10" maxIdle="10" maxWait="-1" url="jdbc:oracle:thin:@localhost:1521:xe" driverClassName="oracle.jdbc.driver.OracleDriver" username="~~" password="~~" />
</Context>
```
- connection 관리를 좀 더 세심하게 해야겠다. 넘치면 이렇게 다운되는구나 실습해 볼 수 있었다.
</details>
<details> <summary>트러블슈팅 3 이미지경로 문제</summary>  

### **ISSUE**  
: 글 업로드 후 바로 이미지 출력이 불가능, 서버를 새로 시작해야 확인 가능한 이슈가 있었다.  기존에 프로젝트 안 정적폴더에 이미지를 넣고 출력하는 식으로 구현하였으나, 서버에 올릴 때 폴더를 읽어 오니까 당연히 볼 수 없었던 것. 미리 준비해두고 사용하는 방식이라면 문제가 없으나, 우리 프로젝트는 사진 업로드가 주요 기능이므로 다른 방법을 찾아야 했다.  

<업로드 경로>  

1. resource/static 아래에 정적 폴더로 이미지를 넣고 출력  
	서버 구동 시 올라간다. 미리 넣어두고 정적자원으로 사용할 경우.  
    
2. 외부 폴더 매핑 (채택)  
        서버 외부에 업로드된 파일을 저장하고, 서버는 해당 외부 폴더를 매핑하여 사용하는 방법  
	톰캣의 컨텍스트 외부로 경로 매핑하려면 server.xml에 다음을 추가  
	```java
	<Context docBase="/Users/jineunyoung/upload/" path="/picok_project/resources/upload/" reloadable="true"/>
	```  

3. 클라우드 이용하기  
	최근에 AWS 배포 강의 들으면서 S3를 이용하는 방식을 깨달았다.  
	현업에서도 이 쪽을 잘 사용하는 편이라고..  적용시켜보고 싶다. 단점은 비용!

5. 외에도 정적폴더를 사용하면서, 서버를 자동으로 재시작하게 하거나, 정적 리소스 요청에 대해 기본 경로를 설정해주는 방법이 있는 것 같다.  

</details>   
<details><summary>추가기능 (팝업공지, 최근글 슬라이드)</summary>  

### 팝업공지   
<img width="292" alt="image" src="https://github.com/JINEUNYOUNG/picok_project/assets/131138113/d84d31a7-5ca2-4b16-97f4-b42d63e191e8" align="right">

```jsx
    // 보지않기 체크 되었을 경우, 쿠키를 생성하는 함수 호출
    function closePop() {
        if (document.pop_form.chkbox.checked) {
            setCookie("popupShown", "true", 1);
        }
        document.getElementById('layer_popup').style.visibility = "hidden";
    }

    // 이름 및 만료일 등으로 쿠키를 생성하낟.
    function setCookie(name, value, expiredays) {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
    }

    // 쿠키 값을 체크하고, 표시를 결정 
    var cookiedata = document.cookie;
    if (cookiedata.indexOf("popupShown=true") < 0) {
        document.getElementById('layer_popup').style.visibility = "visible";
    }
```   
.   
  
### 작성자의 최근글 슬라이드   
![slide](https://github.com/JINEUNYOUNG/picok_project/assets/131138113/e7cee2ac-612c-47d4-ac5c-afc9475758d9)

```java

@Service
public class BoardSingleService implements BoardService {

    @Override
    public void execute(Model model) {

      // ...생략 (해당 글 데이터를 가져오고 조회, 좋아요 확인, 댓글 조회 등 처리 부분)

	    //작성자의 최근 글 3*3 을 추가로 가져온다
	    ArrayList<BoardVO> result2 = boardDAO.otherBoardById(result.getId());

	    if (result2.size() >= 7) {  //3 슬라이드를 전부 사용하는 경우 
	        List<BoardVO> slide1 = result2.subList(0, 3); // 1~3번째 게시물
	        List<BoardVO> slide2 = result2.subList(3, 6); // 4~6번째 게시물
	        List<BoardVO> slide3 = result2.subList(6,result2.size()); // 7~번째 게시물
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", slide2);
	        model.addAttribute("slide3", slide3);
	    } else if (result2.size() >= 4) { //2페이지만 사용하는 경우 (4개 이상 7개 미만) 
	        List<BoardVO> slide1 = result2.subList(0, 3); 
	        List<BoardVO> slide2 = result2.subList(3, result2.size());
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", slide2);
	        model.addAttribute("slide3", new ArrayList<BoardVO>());  //빈리스트를 반환하도록 
	    } else { //1페이지만 사용하는 경우 (4개 미만)
	        List<BoardVO> slide1 = result2.subList(0, result2.size()); 
	        model.addAttribute("slide1", slide1);
	        model.addAttribute("slide2", new ArrayList<BoardVO>());
	        model.addAttribute("slide3", new ArrayList<BoardVO>());
	    }
```

</details>


# 🔗 시연영상  
:     [https://youtu.be/AYHfJ97bato?si=tNMM-Hv51olvuin-  ](https://youtu.be/Z4mQvn4z9Pc)


# 🔥 성장 경험
: 프로젝트에서 문서 및 일정 관리를 담당했고, 회의 중에는 에러사항에 대해 서로 피드백을 주고받으며 효율적인 협업환경을 조성할 수 있도록 노력했습니다.  
코드 리뷰를 통해 코드의 가독성과 유연성을 향상 시키는 동시에 다른 팀원들의 코드를 읽으며 코드 해석능력을 키울 수 있었습니다.    
다음 프로젝트에서는 효율을 높이는 쪽으로 새로운 기술을 많이 적용 해 보고 싶습니다.  
현재는 AWS배포를 적용해보려 하나씩 진행 중입니다.  

