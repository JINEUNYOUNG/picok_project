<%@page import="com.tjoeun.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.tjoeun.dao.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html lang="en">
<head>

<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet" href="/picok_project/assets/css/adminpage.css">
<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<!-- js파일로 -->
<script src="/picok_project/assets/js/adminpage.js" defer="defer"></script>

<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png"
	type="image/x-icon">
</head>


<body>

	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>

	<!-- 내용 -->
	<section class="notice">
		<div class="page-title">
			<div class="container">
			<div class="text-center">
				<h3>회원관리			
				<input class="btn btn-outline-warning btn-sm" type="button" value="뒤로" onclick="goBack()">
				</h3>
			</div>
			</div>
		</div>

		<!-- 검색 -->
		<div id="board-search1" style="text-align:center;">
			<div class="container">
				<form action="admin_member">
					<div class="search-wrap">
						<label class="blind" for="searchInput">회원 검색</label> 
						<input id="searchInput" style="width:600px;" type="search" name="query" value="${searchQuery}" placeholder="이름/아이디/닉네임/이메일로 검색할 수 있습니다." />
						<button type="button" class="btn btn-dark" id="search-button">검색</button>
					</div>
				</form>
			</div>
		</div>
		
		
		<div>
		<form action="admin_delete">
				<div class="d-flex justify-content-end">
				<input type="button" id="deleteButton" class="btn btn-warning btn-sm ml-auto"
					style="font-size: 10px; margin-right:70px;" value="삭제" onclick="deleteMember()" />
			</div>
		</form>
		</div>
		<div id="member-list" style="overflow-y: scroll; max-height: 400px;">
  			<div class="container">
    			<table class="board-table">
					<thead>
						<tr>

							<th scope="col" class="th-check"></th>
							<th scope="col" class="th-id">아이디</th>
							<th scope="col" class="th-name">이름</th>
							<th scope="col" class="th-nickname">닉네임</th>
							<th scope="col" class="th-email">이메일</th>
							<th scope="col" class="th-addr">주소</th>
							<th scope="col" class="th-phone_num">휴대폰번호</th>
							<th scope="col" class="th-joindate">가입일</th>
							<th scope="col" class="th-mem_lv">권한</th>
						</tr>
					</thead>
					<tbody id="searchResults">
						<c:forEach var="member" items="${memberList.list}">
							<tr class="tr-text">
								<td><input type="checkbox" class="checkbox"
									data-member-idx="${member.getId()}"></td>
								<td>${member.getId()}</td>
								<td>${member.getName()}</td>
								<td>${member.getNickname()}</td>
								<td>${member.getEmail()}</td>
								<td>${member.getAddr()}</td>
								<td>${member.getPhone_num()}</td>
								<td>${member.getJoindate()}</td>
								<td><c:choose>
										<c:when test="${member.getMem_lv() == 1}">관리자</c:when>
										<c:when test="${member.getMem_lv() == 0}">일반회원</c:when>
									</c:choose></td>
							</tr>
						</c:forEach>


					</tbody>
				</table>
			</div>
		</div>
		
<div style="text-align: center;">
	<span style="font-size:50px;">...</span>
</div>


	</section>

	<!-- 푸터 include -->
	<jsp:include page="common/footer.jsp"></jsp:include>

	<!-- Start Script -->
	<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
	<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
	<script src="/picok_project/assets/js/templatemo.js"></script>
	<script src="/picok_project/assets/js/custom.js"></script>
	<!-- End Script -->
</body>

</html>