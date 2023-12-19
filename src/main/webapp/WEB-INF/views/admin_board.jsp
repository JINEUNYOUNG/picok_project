
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


</head>


<body>

	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>

	<!-- 내용 -->

	<section class="notice">
		<div class="page-title">
			<div class="container">
			<div class="text-center">
				<h3>게시글관리			
				<input class="btn btn-outline-warning btn-sm" type="button" value="뒤로" onclick="goBack()">
				</h3>
			</div>
			</div>
		</div>
		

		<!-- 검색 -->
		<div id="board-search">
			<div class="container">
				<!-- <div class="search-window">
					<form action="">
						<div class="search-wrap">
							<label for="search" class="blind">공지사항 내용 검색</label> <input
								id="search" type="search" name="" placeholder="검색어를 입력해주세요."
								value="">
							<button type="submit" class="btn btn-dark" id="search-button">검색</button>
					</form>
				</div> -->
				 </div>
			<div style="margin-left:100px; width:130px; ">
				<select id="categorySelect" name="category" onchange="getFilteredData()">
					<option value="0">------</option>
					<option value="1">nature</option>
					<option value="2">city</option>
					<option value="3">daily</option>
				</select>	
			</div> 



			<form action="admin_delete">
				<div class="d-flex justify-content-end">
					<input type="button" id="deleteButton" class="btn btn-warning btn-sm ml-auto"
						style="font-size: 10px; margin-right:70px;" value="삭제" onclick="deleteData()" />
				</div>
			</form>
		</div>
		<div id="board-list" style="overflow-y: scroll; max-height: 400px;">
  			<div class="container">
    			<table class="board-table">
					<thead>
						<tr>
							<th scope="col" class="th-check"></th>
							<th scope="col" class="th-category">카테고리</th>
							<th scope="col" class="th-title">제목</th>
							<th scope="col" class="th-content">내용</th>
							<th scope="col" class="th-writer">작성자</th>
							<th scope="col" class="th-date">등록일</th>
							<th scope="col" class="th-report">신고</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach var="board" items="${boardList.list}">
							<tr class="tr-text">
								<td><input type="checkbox" class="checkbox"
									data-board-idx="${board.getBoard_idx()}"></td>
								<td><c:choose>
										<c:when test="${board.getCategory() == 1}">nature</c:when>
										<c:when test="${board.getCategory() == 2}">city</c:when>
										<c:when test="${board.getCategory() == 3}">daily</c:when>
									</c:choose></td>
								<td>${board.getBoard_title()}</td>
								<td style="text-align: left; padding-right: 10px;">${board.getBoard_content()}</td>
								<td>${board.getId()}</td>
								<td>${board.getBoard_reg_date()}</td>
								<td>${board.getReport_num()}</td>
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