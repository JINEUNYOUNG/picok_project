<%@page import="com.tjoeun.vo.MemberVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Picok</title>

<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet"
   href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<link rel="stylesheet" href="/picok_project/assets/css/qna.css">
<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>


<style>
.title {
	font-size: 15px;
	font-weight: 100;	
}
#chatTitle {
    color: #434242;
    font-stretch:extra-expanded;
    font-size: 18px;
    text-align: left;
    position: absolute;
    top: 0;
    width: 100%;
    right: 0;
    background-color: #fff;
    padding: 10px;
    display: flex; /* 자식 요소를 가로로 나열하기 위해 flexbox를 사용합니다. */
    justify-content: space-between; /* 자식 요소를 양쪽으로 정렬합니다. */
    align-items: center; /* 자식 요소를 수직 가운데로 정렬합니다. */
}

#msgContainer {
    position: absolute;
    bottom: 0;
    width: 100%; /* chatContent의 너비와 동일하게 설정 */
    right: 0; /* 오른쪽 여백을 0으로 설정 */
    background-color: #fff; /* 선택적으로 배경색 설정 가능 */
    padding: 10px;
    display:none;
}

.card-body1{
	height:500px;
    padding: 10px;
    margin: 5px;
}
.chat-item {
    position: relative;
    padding: 10px;
    margin: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    width:400px;
    max-height: 200px;
    text-align: left;
}
.card.h-100 {
    display: flex;
    flex-direction: column;
}

.chatContent {
	margin-top:30px;
	padding-bottom:40px;
	display:none;
}

.latest {
    background-color: #f1f1f1;
}

#chatContainer {
    display: flex;
    flex-wrap: wrap; 
}


.other {
    background-color: #F0F0F0;
    padding: 5px;
    margin-bottom: 10px;
    border-radius: 5px;
    text-align: left; 
    width: 200px;
    margin-right: auto; 
}

.me {
    background-color: #E4F1FF;
    padding: 5px;
    margin-bottom: 10px;
    border-radius: 5px;
    text-align: right; 
    width: 200px;
    margin-left: auto; 
}
.id{color:#537FE7;
	font-size:13px;	
	font-weight:400;
}
.txt{color:#2D2727;
	font-size:16px;	
	font-weight:400;
}
.date{color:#61677A;
	font-size:13px;
	font-weight:100;
}
.notRead {
    background-color: #FFEBEB; 
}
.notRead::before {
    content: "\2022"; 
    color: red; 
    font-weight:bolder; 
position: absolute;
top: 0;
right: 0;
margin-right: 10px;    
}


</style>
</head>

<body>




<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>
		<input type="hidden" name="user" id="user" class="form-control" placeholder="유저명" value="${mvo.getId()}"/>

    <!-- Start Featured Product -->
    <section class="bg-light">
        <div class="container py-5">
<div class="container">
		<div class="container text-center">
	    	<div class="row justify-content-center">
	    	
	    		<!-- 왼쪽 전체 채팅방 리스트(#chatContainer) -->
			    <div class="col-md-3 mb-3">
			        <div class="card h-100">
			            <div class="card-body1">
			    <h1 class="h1">chat</h1>
			                <div id ="chatContainer" class="chatContainer" style="max-height: 100%; overflow-y: auto;">
			                </div>
			            </div>
			        </div>
			    </div>
			    
			    <!-- 오른쪽 개별 채팅창 부분(#chatContent) -->
			    <div class="col-md-6 mb-6"> 
			        <div class="card h-100">
			            <div class="card-body1">
						    <div id="chatTitle">
						    </div>
							<div id="chatContent" class="chatContent" style="max-height: 95%; overflow-y: auto;">
							    <!-- 채팅 추가되는 부분 -->
							    <div class="chat-item read" data-chat-idx="1">
							    </div>
							</div>
						    <div id="msgContainer">
						        <input type="text" name="msg" id="msg" placeholder="대화 내용을 입력하세요." class="form-control" disabled/>
						    </div>
							</div>
			        </div>
			    </div>
			</div>
       </div>
	</div>
 </div>
</section>

<script>
let url ="alarm";
//웹 소켓
let ws;

//채팅창 선택하면 웹소켓을 통해 채팅방 idx 서버로 보냄
function openChat(chatIdx) {
	   		 
    const data = {
            action: 'ChatInfo1',
            chat_idx: chatIdx
    };
       ws.send(JSON.stringify(data));
           
	$('#msg').attr('disabled', false);
	$('#msg').focus();
}

// 로그인 된 거 맞는지 체크 하고 소켓 초기화
if ($('#user').val().trim() != '') {
    ws = new SockJS(url);

  	    // 1 소켓이 열릴 떄 		
   	ws.onopen = function () {
   		 console.log('서버 연결 성공');
	};
       
	// 2 서버에 올라온 메시지가 있을 시
	ws.onmessage = function(event) {
		console.log(event);
		
		var parts = event.data.split('/');
		var id = parts[0];
		var msg = parts[1];
   			console.log(id);
   			console.log(msg);
   			if (id != $('#user').val()){
   			print1(id, msg);}
		
		$('#chatContent').scrollTop($('#chatContent').prop('scrollHeight'));
	};
	
	// 3 소켓이 닫힐 시 
	ws.onclose = function() {
	        console.log('웹 소켓 연결이 정상적으로 종료되었습니다. 코드: ${event.code}, 이유: ${event.reason}');
	        console.error('웹 소켓 연결이 비정상적으로 종료되었습니다. 코드: ${event.code}, 이유: ${event.reason}');
	};

	// 4 에러 발생시
	ws.onerror = function (evt) {
		console.log(evt.data);
	};
}

//내 채팅 화면에 띄워주기 
function print(user, txt) {
	
    	let temp = '';
    	temp += '<div class = "chat-item me" id="socketmsg" >';
    	temp += ' <div class="txt">' + txt + '</div>';
    	temp += ' <div class="date">' + new Date().toLocaleTimeString() + '</div>';
    	temp += '</div>';
    			
    	$('#chatContent').append(temp);	
}
	
//상대방의 채팅 화면에 띄워주기 
function print1(user, txt) {
	console.log("sdfsdf");
    	let temp = '';
    	temp += '<div class = "chat-item other" id="socketmsg" >';
    	temp += ' <div class="id">' + user + '</div>';
    	temp += ' <div class="txt">' + txt + '</div>';
    	temp += ' <div class="date">' + new Date().toLocaleTimeString() + '</div>';
    	temp += '</div>';
    			
    	$('#chatContent').append(temp);	
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

        ws.send(JSON.stringify(data));

		print($('#user').val(), $(this).val()); //본인 대화창에 뜨게 html 추가
		
        $('#msg').val('');
		$('#msg').focus();
		
		$('#chatContent').scrollTop($('#chatContent').prop('scrollHeight'));
		setTimeout(chatList, 1000); 
	}
});

//전체 채팅방 리스트를 띄워줌
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
                    console.log(chatList);

                    // chatList에서 chatVO 목록을 가져오기
                    var chatVOList = chatList.chatList;

                    // 메시지를 미리 생성
                    var chatItems = [];

                    // chatVOList를 순회하면서 HTML 생성 및 추가
                    chatVOList.forEach(function (chatVO) {
                        var writeDate = new Date(chatVO.latest.writedate);
                        writeDate.setHours(writeDate.getHours() + 9);
                        writeDate = writeDate.toLocaleTimeString();

                        var read = (chatVO.latest.write_id !== null && chatVO.latest.write_id !== $('#user').val() && !chatVO.latest.readdate) ? 'notRead' : '';

                        if (chatVO.from_id != $('#user').val()) {
                            var chatItem = '<div class="chat-item ' + read + '" data-chat-idx="' + chatVO.chat_idx + '" data-id="' + chatVO.from_id + '">';
                            chatItem += '<div class="id">' + chatVO.from_id + '</div>';
                        } else {
                            var chatItem = '<div class="chat-item ' + read + '" data-chat-idx="' + chatVO.chat_idx + '" data-id="' + chatVO.to_id + '">';
                            chatItem += '<div class="id">' + chatVO.to_id + '</div>';
                        }
                        if (!chatVO.latest.writedate) {
                            chatItem += '<div class="txt"></div>';
                            chatItem += '<div class="date"></div>';
                        } else {
                            chatItem += '<div class="txt">' + chatVO.latest.write_content + '</div>';
                            chatItem += '<div class="date">' + writeDate + '</div>';
                        }
                        chatItem += '</div>';

                        chatItems.push(chatItem);
                    });

                    // 초기화가 완료되면 메시지를 한 번에 추가
                    $('#chatContainer').html(chatItems.join(''));

                    // 클릭하면 개별 채팅창이 뜨게 됨 
                    $('.chat-item').click(function () {
                        var chatIdx = $(this).data('chat-idx');
                        var chatId = $(this).data('id');

                        chatContent(chatIdx, chatId);
                        openChat(chatIdx);
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


//선택한 개별 채팅방 메시지를 받아 화면에 띄워줌
function chatContent(chatIdx,chatId) {
     $('#msg').val('');
	 $('#msg').focus();

	 var chatContentHtml = ' ';
	 var user = $('#user').val();

     $('#chatTitle').html('∙ <span class="title">'+chatId+' 님과의 채팅창</span> <i class="fa fa-sign-out" aria-hidden="true" onclick="chatExit('+chatIdx+',\''+user+'\')"></i>');
     

	 $('#chatContent').html(chatContentHtml);
	
	 $.ajax({
	     url: '/picok_project/chatContent',
	     type: 'get',
	     data: { 'chat_idx': chatIdx,'user':$('#user').val() },
	     dataType: "json",
	     success: function (chatContent) {
	         console.log(chatContent);
	         $('#chatContent').prop('hidden', false);

	         chatContent.chatList.forEach(function (item) {
	        	    var itemHtml = ''; 
	        	    var itemClass = ''; // 상대방 채팅인지 내 채팅인지(me / other로 나눠 표시)
	        	    var writeDate = new Date(item.writedate);
	        	    writeDate.setHours(writeDate.getHours() + 9); 
	        	    writeDate = writeDate.toLocaleTimeString();

	        	    
					if (item.write_id != $('#user').val()) {
				        itemClass = 'other'; 
	        	    	 itemHtml =
	 	        	        '<div class="chat-item ' + itemClass + '" data-chat-idx="' + item.chat_idx + '">' +
	 	        	        '<div class="id">'+ item.write_id + '</div>' +
	 	        	        '<div class="txt">'+item.write_content + '</div>' +
	                        '<div class="date">'+writeDate +'</div>' +
	 	        	        '</div>';
					} else {
						itemClass = 'me';
	        	    	 itemHtml =
	 	        	        '<div class="chat-item ' + itemClass + '" data-chat-idx="' + item.chat_idx + '">' +
	 	        	        '<div class="txt">'+item.write_content + '</div>' +
	                        '<div class="date">'+writeDate +'</div>' +
	 	        	        '</div>';
					}

	        	    chatContentHtml += itemHtml;
	        	    
	        	});

	     	 setTimeout(chatList, 500); 

	         $('#chatContent').html(chatContentHtml);
	         $('#chatContent').css('display', 'block');
	         $('#msgContainer').css('display', 'block');

			
			 $('#chatContent').scrollTop($('#chatContent').prop('scrollHeight'));
	     }
	});
}

//채팅창 나가기 
function chatExit(chatIdx,user) {
    var confirmation = confirm("채팅을 나가시겠습니까?");
    if (confirmation) {
        const data = {
                action2: 'ChatExit',
                chat_idx: chatIdx,
                user: user
        };
        ws.send(JSON.stringify(data));
    	 setTimeout(chatList, 500); 
    	 ws.close();
         $('#chatContent').html('');
         $('#chatTitle').html('');
         $('#msgContainer').html('');

     };	
}

//초기화 시 chatList
chatList();

//주기적으로 채팅방리스트 ajax 돌리기 
setInterval(chatList, 5000);  

</script>
        

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>


<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>

</body>
</html>