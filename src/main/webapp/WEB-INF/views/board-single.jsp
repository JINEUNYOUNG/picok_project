<%@page import="com.tjoeun.vo.BoardCommentVO"%>
<%@page import="com.tjoeun.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@page import="com.tjoeun.vo.BoardVO"%>
<%@page import="com.tjoeun.vo.ReplyVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">

<head>
<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="apple-touch-icon" href="/picok_project/assets/img/apple-icon.png">
<link rel="shortcut icon" type="image/x-icon" href="/picok_project/assets/img/favicon.ico">
<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" type="text/css" href="/picok_project/assets/css/slick.min.css">
<link rel="stylesheet" type="text/css" href="/picok_project/assets/css/slick-theme.css">
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
<style>
	#other {
	height:150px;
	object-fit: cover;
	}
</style>
</head>

<body>

	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>

	<!-- 내용 -->
	<section class="bg-light">
		<div class="container pb-5" style="max-width: 70%">
			<div class="row">
				<div class="col-lg-7 mt-5">
					<div class="card mb-5">
						<img class="card-img img-fluid" src="/picok_project/resources/upload/<c:out value='${singlepage.getFile_name()}' />" alt="Card image cap" id="product-detail">
					</div>
					                    <div class="row">
                        <div class="col-1 align-self-center">
                            <a href="#multi-item-example" role="button" data-bs-slide="prev">
                                <i class="text-dark fas fa-chevron-left"></i>
                                <span class="sr-only">Previous</span>
                            </a>
                        </div>
                        <div id="multi-item-example" class="col-10 carousel slide carousel-multi-item" data-bs-ride="carousel">
                            <!--Start Slides-->
                            <div class="carousel-inner product-links-wap" role="listbox">
						<div class="carousel-item active">
						    <div class="row">
						        <c:forEach var="image" items="${slide1}">
						            <div class="col-4">
						                <a href="<c:url value='/board-single' />?board_idx=${image.board_idx}">
						                    <img class="card-img img-fluid" id="other" src="/picok_project/resources/upload/${image.file_name}" alt="Product Image" >
						                </a>
						            </div>
						        </c:forEach>
						    </div>
						</div>
						<div class="carousel-item">
						    <div class="row">
						        <c:forEach var="image2" items="${slide2}">
						            <div class="col-4">
						                <a href="<c:url value='/board-single' />?board_idx=${image.board_idx}">
						                    <img class="card-img img-fluid" id="other" src="/picok_project/resources/upload/${image2.file_name}" alt="Product Image">
						                </a>
						            </div>
						        </c:forEach>
						    </div>
						</div>
						<div class="carousel-item">
						    <div class="row">
						        <c:forEach var="image3" items="${slide3}">
						            <div class="col-4">
						                <a href="<c:url value='picok_project/singlepage' />?board_idx=${image.board_idx}">
						                    <img class="card-img img-fluid" id="other" src="/picok_project/resources/upload/${image3.file_name}" alt="Product Image">
						                </a>
						            </div>
						        </c:forEach>
						    </div>
						</div>

                            </div>
                                                    </div>
                        <div class="col-1 align-self-center">
                            <a href="#multi-item-example" role="button" data-bs-slide="next">
                                <i class="text-dark fas fa-chevron-right"></i>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                            </div>
				</div>
				<div class="col-md-5 mt-5" style="max-width: 500px;">
					<div class="card">
						<div class="card-body">
							<h1 class="h1" style="font-style: italic;" data-board-title="${singlepage.getBoard_title()} id="${singlepage.getBoard_title()}>${singlepage.getBoard_title()}</h1><br />
							<ul class="list-inline">
								<li class="list-inline-item">
									<h5>작성자</h5>
								</li>
								<li class="list-inline-item">
									<p class="text-muted" onclick="clickId()">
										<strong>${singlepage.getId()}</strong> <input type="hidden" id="writer-id" value="${singlepage.getId()}" />
											<c:choose><c:when test="${singlepage.getId() != mvo.id}">
											&nbsp;<i class="fa fa-commenting-o" aria-hidden="true"></i></c:when></c:choose>
										


									</p>
								</li><br/>
								<li class="list-inline-item">
									<h5>작성일</h5>
								</li>
								<li class="list-inline-item">
									<p class="text-muted">
										<strong>${singlepage.getBoard_reg_date()}</strong>
									</p>
								</li>
								<br/>
								<li class="list-inline-item">
									<h5>내용</h5>
								<li class="list-inline-item">
									<p class="text-muted">${singlepage.getBoard_content()}</p>
								</li>
							</ul>
							<ul class="list-inline">
								<p class="py-1">
									<i class="fa fa-eye text-warning"></i> 
									<span class="list-inline-item text-dark">조회수${singlepage.getView_num()}</span> 
									<i class="fa fa-thumbs-up text-warning"></i> 
									<span class="list-inline-item text-dark">좋아요 ${countLikes}</span>
								</p>
							</ul>
							<input type="hidden" name="product-title" value="Activewear">
							<div class="row">
								<div class="col-auto">
									<ul class="list-inline pb-3"></ul>
								</div>
								<div class="col-auto">
									<ul class="list-inline pb-3"></ul>
								</div>
							</div>
							<div class="row pb-2">
								<div class="col d-grid">
									<!-- 수정 버튼 -->
									<c:choose>
										<c:when test="${empty mvo}"></c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${singlepage.getId() == mvo.id}">
													<button type="submit" class="btn btn-info btn-lg" onclick="editPost()" style="color: white;">수정</button>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${likes == false}">
															<button type="button" class="btn btn-outline-info btn-lg" onclick="addLikes()">좋아요</button>
														</c:when>
														<c:otherwise>
															<button type="button" class="btn btn-info btn-lg" onclick="addLikes()">좋아요 취소</button>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col d-grid">
									<c:choose>
										<c:when test="${empty mvo}"></c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${singlepage.getId() == mvo.id}">
													<button class="btn btn-danger btn-lg" onclick="deletePost()">삭제</button>
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${report == false}">
															<button type="button" class="btn btn-outline-warning btn-lg" onclick="addReport()">신고하기</button>
														</c:when>
														<c:otherwise>
															<button type="button" class="btn btn-warning btn-lg" disabled>신고 완료</button>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</div>
								<div><br /> <br />
									<h5>댓글</h5>
									<hr>
									<div class="comment-view">
										<div id="commentList">
											<c:choose>
												<c:when test="${empty listResult}">
			                                       <p>아직 댓글이 없습니다.</p>
			                                    </c:when>
			                                    <c:otherwise>
			                                       <c:forEach var="comment" items="${listResult}">
			                                          <div>
			                                             <mark><strong>[${comment.nickname}]</strong></mark>&nbsp;:&nbsp;${comment.comment_content}&nbsp;&nbsp;
			                                            	 <i class="fa fa-eraser" aria-hidden="true" style="color:gray;" onclick="deleteComment(${singlepage.getBoard_idx()},${comment.comment_idx})"></i>
			                                                   <i class="fa fa-reply" aria-hidden="true" style="color:#61677A;" onclick="reply(${comment.comment_idx})"></i><br/>
			                                             <c:forEach var="reply" items="${reply}">
			                                             <div class="reply">
			                                                <!-- 댓글 내용 -->
			                                                <c:if test="${comment.comment_idx == reply.comment_idx}">
			                                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↳&nbsp;${reply.nickname} : ${reply.getReply_content()} 
			                                                    
			                                                
			                                                   <!-- 답글작성자면 삭제버튼 -->
			                                                   <c:if test="${reply.id == mvo.id}">&nbsp;
			                                                   <i class="fa fa-eraser" aria-hidden="true" style="color:gray;" onclick="deleteReply(${singlepage.getBoard_idx()},${reply.reply_idx})"></i>
			                                                   </c:if>
			                                                </c:if>
			                                             </div>
			                                             </c:forEach>
			                                             <br/>
			                                              
			                                       
			                                             
			                                             <!-- 대댓글 입력 폼 -->
			                                             <textarea rows="3" class="form-control" id="textarea-${comment.comment_idx}" 
			                                                style="resize: none; width: 370px; display: none;" placeholder="답글을 입력하세요."></textarea>
			                                             <button id="button-${comment.comment_idx}" class="btn btn-light-sm" style="display: none; text-align: right;">작성</button>
			                                          </div>
			                                       </c:forEach>
			                                    </c:otherwise>
											</c:choose>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 댓글 폼 -->
					<div class="comment">
						<div class="comment-body">
							<br />
						</div>
						<%
						MemberVO id = (MemberVO) session.getAttribute("mvo");
						BoardCommentVO bcvo = (BoardCommentVO) session.getAttribute("bcvo");
						%>
						<div>
						작성자 
							<input type="text" value="${mvo.nickname}" readonly="readonly" style="width: 100%" /> 
							<input type="hidden" id="writer" value="${mvo.id}">
							<input type="hidden" id="board_idx" value="${singlepage.getBoard_idx()}"> <input type="hidden" id="category" value="${singlepage.getCategory()}">
							<input type="hidden" value="${singlepage.getBoard_idx()}">
						</div>
						댓글
						<div class="form-group d-flex">
							<c:choose>
								<c:when test="${empty mvo}">
									<textarea class="form-control" id="comment" name="comment" rows="2" style="resize: none; width: 600px;" readonly="readonly">로그인 후에 작성할 수 있습니다.</textarea>
								</c:when>
								<c:otherwise>
									<textarea class="form-control" id="comment" name="comment" rows="4" style="resize: none; width: 600px;"></textarea>
								</c:otherwise>
							</c:choose>
							<button type="submit" class="btn btn-outline-info ml-2" style="width: 100px;" onclick="addComment()">작성</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>

<!-- js -->
<script>

//댓글
function addComment() {
	// 작성한 댓글 내용 가져오기
	var commentContent = document.getElementById("comment").value;
	// 작성자 정보 가져오기
	var commentWriter = document.getElementById("writer").value; // writer는 댓글 작성자, writer-id는 게시글 작성자
	var commentBoardIdx = document.getElementById("board_idx").value;
	var element = document.querySelector('[data-board-title]');
	var boardTitle = element.getAttribute("data-board-title");
	var writer = document.getElementById("writer-id").value;
	var boardIdx = document.getElementById("board_idx").value;

	// Ajax를 사용하여 댓글 저장 요청
	$.ajax({
		type: "GET",
		url: "comment?board_idx=" + commentBoardIdx + "&id=" + commentWriter + "&comment_content=" + commentContent,
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		error: function() {
			alert('실패.');
		},
		success: function(data) {

			location.reload();
		}
	});
}

//	댓글 삭제 메소드
function deleteComment(board_idx, comment_idx) {
	// Ajax를 사용하여 댓글 저장 요청
	$.ajax({
		type: "GET",
		url: "deleteComment?board_idx=" + board_idx + "&comment_idx=" + comment_idx,
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		error: function() {
			alert('실패.');
		},
		success: function(data) {
			location.reload();
		}
	});
}

//	좋아요
function addLikes() {
	var writer = document.getElementById("writer-id").value; // writer는 댓글 작성자, writer-id는 게시글 작성자
	var boardIdx = document.getElementById("board_idx").value;
	var element = document.querySelector('[data-board-title]');
	var boardTitle = element.getAttribute("data-board-title");
	var loginId = document.getElementById("writer").value;
	
	// Ajax를 사용하여 댓글 저장 요청
	$.ajax({
		type: "GET",
		url: "boardLike?board_idx=" + boardIdx + "&id=" + writer,
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		error: function() {
			alert('좋아요 누르기에 실패.');
		},
		success: function(data) {
			location.reload();
			//아래는 웹소켓 알림 띄우기
		//if (writeId == loginId) { //자기 글 아닐때만 알림띄워야하는데 test상 본인 글도 보이도록 잠시 닫아 둠 
			if (socket) {
				let socketMsg = "like," + loginId + "," + writer
						+ "," + boardIdx + "," + boardTitle + ","
						+ "gg";
				console.log(socketMsg);
				socket.send(socketMsg);
	//	}
			} else {
				console.log("소켓안열림.")
			}
		}
	});
}

//	좋아요 취소
function cancelLikes() {
	var writer = document.getElementById("writer-id").value; // writer는 댓글 작성자, writer-id는 게시글 작성자
	var boardIdx = document.getElementById("board_idx").value;
	// Ajax를 사용하여 댓글 저장 요청
	$.ajax({
		type: "GET",
		url: "boardLike?board_idx=" + boardIdx + "&id=" + writer,
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		error: function() {
	        alert('좋아요 취소에 실패했습니다.');
		},
		success: function(data) {
			if (data === "success") {
                location.reload(); // 좋아요 취소 성공 시 페이지 리로드
            } 
		}
	});
}

//	작성자 게시글 삭제 메소드
function deletePost() {
	var category = document.getElementById("category").value;
	var boardWriter = document.getElementById("board_idx").value;
	var xhr = new XMLHttpRequest();
	$.ajax({
		type: "GET",
		url: "delete?board_idx=" + encodeURIComponent(boardWriter),
		contentType: "application/x-www-form-urlencoded;charset=UTF-8",
		error: function() {
			alert('게시글 삭제에 실패했습니다.');
		},
		success: function(data) {
			alert("삭제 완료되었습니다.");
            window.location.href = "list?category=" + encodeURIComponent(category) + "&currentPage=1";

		}
	});
}

// 작성자 게시글 수정 메소드
function editPost() {
	var board_idx = ${singlepage.getBoard_idx()};
	var xhr = new XMLHttpRequest();
	xhr.open("POST", "update", true);
	xhr.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4 && xhr.status === 200) {
			location.href = "update?board_idx="+ board_idx;
		}
	};
	xhr.send("&board_idx=" + encodeURIComponent(board_idx));
}

//아래로 상자가 생겨서 채팅창으로 가게 떠야한다. 
function clickId() {
	var confirmation = confirm("채팅방으로 갑니다.");
    if (confirmation) {
		var writer = document.getElementById("writer-id").value;
		var user = document.getElementById("writer").value;
		location.href = "/picok_project/chatCheck?from_id="+user+"&to_id="+writer;
	};

}

function addReport() {
	   var category = document.getElementById("category").value;
	   var commentBoardIdx = document.getElementById("board_idx").value;
	   // Ajax를 사용하여 댓글 저장 요청
	   $.ajax({
	      type: "GET",
	      url: "report?board_idx=" + commentBoardIdx,
	      contentType: "application/x-www-form-urlencoded;charset=UTF-8",
	      error: function() {
	         alert('실패.');
	      },
	      success: function(data) {
	         window.location.href = "list?category=" + encodeURIComponent(category) + "&currentPage=1";
	      }
	   });
	}
function reply(commentIdx) {
    // textarea와 button을 ID를 사용하여 선택
     var textarea = document.getElementById("textarea-" + commentIdx);
     var button = document.getElementById("button-" + commentIdx);
     
     console.log(textarea);
     
     // textarea와 button을 보이도록 설정
     textarea.style.display = "block";
     button.style.display = "block";
     
     // 작성하기 버튼 클릭 이벤트 설정
     button.addEventListener("click", function() {
       // 여기에서 textarea에 입력된 내용을 가져와서 서버로 전송하도록 구현합니다.
       // 서버로 댓글 작성을 위한 요청을 보내는 코드 작성
       
      // 작성한 댓글 내용 가져오기
      var replyContent = textarea.value;
      // 작성자 정보 가져오기
      var replyWriter = document.getElementById("writer").value;
      var replyBoardIdx = document.getElementById("board_idx").value;
      var url = "reply?board_idx=" + replyBoardIdx 
            + "&id=" + replyWriter 
            + "&reply_content=" + replyContent 
            + "&comment_idx=" + commentIdx;
     console.log(replyContent);
     console.log(url);
     // Ajax를 사용하여 댓글 저장 요청
     $.ajax({
         type: "GET", // 요청 메서드 (GET)
         url: url,
         contentType: "application/x-www-form-urlencoded;charset=UTF-8",
         success: function(response) {
             // 성공적으로 댓글을 서버에 보냈을 때 실행되는 부분
             // 적절한 동작 수행, 페이지 새로고침 또는 댓글 목록 갱신 등
             location.reload(); // 페이지 새로고침 예시
         },
         error: function(error) {
             // 댓글 작성 실패 시 실행되는 부분
             console.error("댓글 작성 실패:", error);
             alert("댓글 작성에 실패했습니다.");
         }
     });
  });
}

//대댓글 삭제
function deleteReply(board_idx, reply_idx) {
   // Ajax를 사용하여 댓글 저장 요청
   $.ajax({
      type: "GET",
      url: "deleteReply?board_idx=" + board_idx + "&reply_idx=" + reply_idx,
      contentType: "application/x-www-form-urlencoded;charset=UTF-8",
      error: function() {
         alert('답글 삭제 실패.');
      },
      success: function(data) {
         location.reload();
      }
   });
}

</script>
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>

</body>

</html>