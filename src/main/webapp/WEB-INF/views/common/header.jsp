<%@page import="com.tjoeun.vo.MemberVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Picok</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/css/bootstrap.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/js/bootstrap.bundle.min.js"></script>
<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
    #socketAlert {
        position: fixed;
        top: 20px;
        right: 20px;
        display: none;
        background-color: #F4EEEE;
        z-index: 9999;
        opacity: 80%;
        border-radius:10px;
    }
	 #alarmList {
	    display: none; 
	    position: absolute; 
	    right: 0; 
	    top: 60px; 
	    width: 300px;
	    background-color: rgba(255, 255, 255, 0.9);
	    border: 1px solid #ccc;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        z-index: 9999; 
	    max-height: 300px; 
	    overflow-y: scroll; 
	    padding: 10px;
	 }
     .custom {
       font-size: 15px;
       text-decoration:none; 
   	 }
   	 .custom_deleteAll {
       font-size: 15px;
       text-decoration:none; 
   	   font-weight: bold;
   	 }
   	 .custom_delete {
       text-decoration:none; 
   	 }
   	 .header {
       font-size: 15px;
       text-decoration:none;
       background-color:white;
   	   position: sticky; 
       top: 0; 
       z-index: 100; 
       text-align: right;
   	 }
	   	 @keyframes shake {
	  0% { transform: translateX(0); }
	  25% { transform: translateX(-5px) rotate(-5deg); }
	  50% { transform: translateX(5px) rotate(5deg); }
	  75% { transform: translateX(-5px) rotate(-5deg); }
	  100% { transform: translateX(0); }
	}
	
	.shaking-icon {
	  animation: shake 0.5s ease infinite;
	}
	
</style>



</head>
<body>
    <div id="socketAlert" style="display: none; padding: 10px;">
        <!-- 알림창(웹소켓) -->
    </div>

	<!-- 헤더 -->
	<nav class="navbar navbar-expand-lg navbar-light shadow">
		<div
			class="container d-flex justify-content-between align-items-center">
			<a class="navbar-brand text-info logo h1 align-self-center"
				href="index"> Picok </a>

			<button class="navbar-toggler border-0" type="button"
				data-bs-toggle="collapse" data-bs-target="#templatemo_main_nav"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div
				class="align-self-center collapse navbar-collapse flex-fill d-lg-flex justify-content-lg-between"
				id="templatemo_main_nav">
				<div class="flex-fill">
					<ul class="nav navbar-nav d-flex align-items-center justify-content-between mx-lg-auto">
						<li class="nav-item"><a class="nav-link"
							href="list?category=1&currentPage=1">Nature</a></li>
						<li class="nav-item"><a class="nav-link"
							href="list?category=2&currentPage=1">City</a></li>
						<li class="nav-item"><a class="nav-link"
							href="list?category=3&currentPage=1">Daily</a></li>
						<li class="nav-item dropdown">
							<a class="nav-icon position-relative text-decoration-none dropdown-toggle"
							href="#" id="navbarDropdown" role="button"
							data-bs-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false">Picok</a>
							<div class="dropdown-menu" aria-labelledby="picokDropdown">
								<a class="dropdown-item" href="about">About</a>
								<a class="dropdown-item" href="contact">Contact</a>
								<a class="dropdown-item" href="qna">QnA</a>
							</div></li>
				</div>

				<!-- 로그인시 닉네임 출력 -->
				<c:set var="mvo" value="${sessionScope.mvo}" />
				<%-- mvo가 null이 아니면 로그인이 성공한 상태이므로 사용자 이름을 표시한다 --%>
				<c:if test="${not empty mvo}">
					<span style="font-size: 15px;">${mvo.nickname}님 안녕하세요!&nbsp;&nbsp;
					</span>
				</c:if>

				<div class="navbar align-self-center d-flex">

					<!-- 1.로그인안됐다 ->login.jsp 로, 2. 로그인됐다 -> upload.jsp로. -->
					<c:choose>
						<c:when test="${empty mvo}">
						  <a class="nav-icon position-relative text-decoration-none" href="#" onclick="showAlert()">
						    <i class="fa fa-fw fa-pencil-square-o text-dark mr-3"></i>
						  </a>
						<a class="nav-icon position-relative text-decoration-none" id="alarmIcon" href="#" onclick="showAlert()">
								<i class="fa fa-bell-o" id="bellIcon" aria-hidden="true"></i></a>

						<a class="nav-icon position-relative text-decoration-none" id="chatIcon" href="showAlert()"" >
								<i class="fa fa-commenting-o" id="chat" aria-hidden="true"></i></a>
						</c:when>
						<c:otherwise>
							<a class="nav-icon position-relative text-decoration-none"
								href="upload"> <i
								class="fa fa-fw fa-pencil-square-o text-dark mr-3"></i>
							</a>
							
						<a class="nav-icon position-relative text-decoration-none" id="alarmIcon" href="#" >
								<i class="fa fa-bell-o" id="bellIcon" aria-hidden="true"></i></a>
						
						<div class="alarmList" id="alarmList" style="display: none;">
						    <button id="deleteAllButton" class="btn btn-danger">전체 삭제</button>
						</div>
						
						<a class="nav-icon position-relative text-decoration-none" id="chatIcon" href="/picok_project/chat" >
								<i class="fa fa-commenting-o" id="chat" aria-hidden="true"></i></a>
						</c:otherwise>
					</c:choose>
				
					<!-- 마이메뉴 드롭다운 -->
					<div class="dropdown">
						<a
							class="nav-icon position-relative text-decoration-none dropdown-toggle"
							href="#" id="navbarDropdown" role="button"
							data-bs-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i
							class="fa fa-user-o"></i>
						</a>
						<div class="dropdown-menu">
							<c:if test="${empty mvo}">
								<a class="dropdown-item" href="login">Login</a>
								<a class="dropdown-item" href="join">Join</a>
							</c:if>
							<c:if test="${not empty mvo}">
								<c:choose>
									<c:when test="${mvo.mem_lv == 1}">
										<a class="dropdown-item"
											href="admin">Admin</a>
									</c:when>
									<c:otherwise>
										<!-- Regular menu items -->
										<a class="dropdown-item"
											href="mypage">MyPage</a>
										<a class="dropdown-item"
											href="myphoto?id=${mvo.id}">MyPhoto</a>
										<a class="dropdown-item"
											href="like?id=${mvo.id}">Like</a>
									</c:otherwise>
								</c:choose>
								<a class="dropdown-item"
									href="logout">Logout</a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</nav>


<script>

// 로그인 안됐을 떄 alert띄움
function showAlert() {
  alert("로그인이 필요합니다.");
  window.location.href = "login";
}

<%
MemberVO mvo = (MemberVO) session.getAttribute("mvo");
String memberId = (mvo != null) ? mvo.getId() : ""; // mvo가 null이 아니면 id 값을 추출하고, 그렇지 않으면 빈 문자열로 설정
%>

var memberId = '<%= memberId %>';

// 처음 로딩 시에 알람리스트 가져오기 (이후에는 업데이트가 있을 경우)
$(document).ready(function() {
	alarmList();
	alarmCount();
});


// 알람목록 불러오기
function alarmList(){
	 $.ajax({
	        url : '/picok_project/alarmList',
	        type : 'get',
	        data : {'memberId' : memberId },
	        dataType : "json", 
	        success : function(data){
				console.log("success");
				console.log(data);
	         	var a='';
	            if (data.length > 0) {
	                a += '<div class="header">';
	                a += '<a id="deleteAllButton" class="custom_deleteAll" onclick="deleteAllAlarm()">전체 삭제</a> </div>';
	            }
	         	 $.each(data, function(key, value){ 
	                 var category = value.category;
	                 const alarmdate = getTimeDifference(value.alarmdate);	
	                 a += '<div>';
	                 a += '<div class="small text-gray-500">' + alarmdate  + ' <a class="custom_delete" style="float: right;" onclick="deleteAlarm(' + value.alarm_idx + ');">x</a></div>';
	                 if (category == "reply") {
	                     a += '<span class="font-weight-bold"><a href="#" class="custom" onclick="alarmClick(' + value.board_idx + ',\'' + value.alarm_idx + '\');">'+ value.from_id + '님이 [' +value.board_title+']에 <mark><strong>댓글</strong></mark>을 달았습니다</a></span>';
	                 } else if (category == "like") {
	                     a += '<span class="font-weight-bold"><a href="#" class="custom"  onclick="alarmClick(' + value.board_idx + ',\'' + value.alarm_idx + '\');">' + value.from_id + '님이 [' +value.board_title+']를 <mark><strong>좋아요</strong></mark> 했습니다.</a></span>';
	                 }
	                 a += '</div><hr/>';
	             });
	             // 알림 목록을 #alarmList 요소에 추가
	             $("#alarmList").html(a);

 	     	    alarmCount();
	        }
	    });
	 }
	 
var alarmListHidden = true;

// 클릭하면 알람 목록이 닫겨져 있는지? 확인 후 목록을 띄움 or 닫음
function toggleAlarmList() {
    if (alarmListHidden) {
        $("#alarmList").show();
        alarmListHidden = false;
    } else {
        $("#alarmList").hide();
        alarmListHidden = true;
    }
}

// 아이콘 클릭 시에 위 함수 실행
$("#alarmIcon").click(function() {
    toggleAlarmList();
});

// 알람 목록 갱신 및 숨김 처리를 위한 함수
function updateAlarmList() {
    if (!alarmListHidden) {
        alarmList();
        $("#alarmList").hide();
        alarmListHidden = true;
    }
}

// 알람 업데이트가 있는지 30초마다 돌려보기
if (memberId != ""){
	setInterval(checkUpdate, 30000);
}

// 업데이트가 있는지? 
function checkUpdate() {
        $.ajax({
            type: "GET",
            url: "/picok_project/api/check-update",
            data: { userId: memberId }, // 현재 사용자의 ID 전달
            success: function(response) {
                if (response.update) {
                	if (response.update) { // update가 true면 db를 돌아 리스트를 가져오도록 
                		alarmList();
                		alarmCount();
                		console.log("update=true");
                	} else {
                		console.log("update=false");
                	}
                } else {
                	console.log("None");
                }
            },
            error: function(error) {
                console.error("업데이트 확인 실패:", error);
            }
        });
}


// 현시간과 비교(~시간 전으로 표시하기 위함)
function getTimeDifference(alarmDate) {
    const currentDate = new Date();
    const alarmDateObj = new Date(alarmDate);

    const timeDifference = currentDate - alarmDateObj;

    const hoursDifference = Math.floor(timeDifference / 1000 / 60 / 60) - 9;

    if (hoursDifference < 1) {
        // 1시간 이내인 경우
        return "방금 전";
    } else if (hoursDifference < 24) {
        // 24시간 이내인 경우
        return hoursDifference + "시간 전";
    } else {
        // 24시간 이상인 경우
        const daysDifference = Math.floor(hoursDifference / 24);
        return daysDifference + "일 전";
    }
}

// 알람 x 클릭(개별삭제)
function deleteAlarm(alarm_idx){
    console.log("deleteAlarm");
    $.ajax({
        url: '/picok_project/alarmDelete',
        type: 'post',
        data: {'alarm_idx': alarm_idx},
        success: function () {
            alarmList();
        },
    });
}

// 알람 전체 삭제 클릭(전체 삭제)
function deleteAllAlarm(){
    console.log(memberId);
    $.ajax({
        url: '/picok_project/alarmDeleteAll',
        type: 'post',
        data: {'from_id': memberId},
        success: function () {
            alarmList();
    	    alarmCount();
        },
    });
}

// 알람목록클릭(개별삭제 및 상세페이지로 이동)
function alarmClick(board_idx, alarm_idx){
    console.log("alarmClick");
    $.ajax({
        url: '/picok_project/alarmDelete',
        type: 'post',
        data: {'alarm_idx': alarm_idx, 'board_idx': board_idx},
        success: function () {
            location.href = "/picok_project/board-single?board_idx=" + board_idx;
        },
        error: function () {
        }
    });
}

$(document).ready(function() {
    alarmCount();
  });

// 알람 수 가져와서 아이콘 바꿔 껴 주기 
function alarmCount(){
    var count = 0;
	 $.ajax({
	        url : '/picok_project/alarmCount',
	        type : 'get',
	        data : {'to_id' : memberId },
	        dataType : "json", 
	        success: function (alarm) {
	            var count = alarm.count;
	        	
	            if (count !== 0) {
			      // 알람이 있는 경우
			      $("#bellIcon").removeClass("fa fa-bell-o");
			      $("#bellIcon").addClass("fa fa-bell shaking-icon"); 
			      $("#bellIcon").addClass("fa fa-bell");
			      setTimeout(function() {
			          $("#bellIcon").removeClass("shaking-icon");
			      }, 500); 

			    } else {
			      // 알람이 없는 경우
			      $("#bellIcon").removeClass("fa fa-bell");
			      $("#bellIcon").addClass("fa fa-bell-o");  
			    }
	    }
	 });
}
</script>

</body>
</html>