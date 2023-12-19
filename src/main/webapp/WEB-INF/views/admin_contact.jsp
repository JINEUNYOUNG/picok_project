
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
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png"
	type="image/x-icon">
<style>
	tr > td > a {
	color:black;
    text-decoration: none; 
    font-size:13px !important;
    font-weight: bold;
	}
</style>

</head>


<body>

	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>

	<!-- 내용 -->

	<section class="notice">
		<div class="page-title">
			<div class="container">
			<div class="text-center">
				<h3>문의글관리			
				<input class="btn btn-outline-warning btn-sm" type="button" value="뒤로" onclick="goBack()">
				</h3>
			</div>
			</div>
		</div>
		

		<!-- 검색 -->
		<div id="board-search">
			<div class="container">
			<div style="margin-left:20px; width:130px; ">
			</div> 
				 <div class="search-window">
					<form action="">

				<select id="categorySelect" name="category" onchange="getFilteredData()">
					<option value="0">------</option>
					<option value="미응답">미응답</option>
					<option value="응답완료">응답완료</option>
				</select>	
					</form>
				</div> 
				 </div>

		</div>
		<div id="board-list" style="overflow-y: scroll; max-height: 400px;">
  			<div class="container">
    			<table class="board-table">
					<thead>
						<tr>
							<th scope="col" class="th-category">카테고리</th>
							<th scope="col" class="th-title">제목</th>
							<th scope="col" class="th-content">내용</th>
							<th scope="col" class="th-writer">작성자</th>
							<th scope="col" class="th-date">등록일</th>
							<th scope="col" class="th-answer">답변일</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach var="board" items="${boardList.list}">
							<tr class="tr-text">
									<td>${board.category}</td>

									<td><a href="admin_contactSingle?idx=${board.idx}">${board.title}</a></td>
									<td><a href="admin_contactSingle?idx=${board.idx}">${board.content}</a></td>
									<td>${board.id}</td>
									<td>${board.writeDate}</td>
									<td>${board.answerDate}</td>
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
	<script src="/picok_project/assets/js/adminpage.js"></script>
	<!-- End Script -->
</body>

</html>