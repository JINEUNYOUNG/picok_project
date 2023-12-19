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
<link rel="stylesheet"
	href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet"
	href="/picok_project/assets/css/fontawesome.min.css">

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png"
	type="image/x-icon">

<!-- 이페이지만을 위한 css -->
<style>
.button-container {
	text-align: center;
}

.button-container button {
	margin: 30px;
}
</style>
</head>


<body>

	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>
	<br/>
	<br/>
	<!-- 내용 -->
	<form action="admin_member"  method="POST" onsubmit="return validateAdmin()">
		<div class="button-container">
			<button class="btn btn-warning" >회원관리</button>
		</div>
	</form>
	<form action="admin_board"  method="POST" onsubmit="return validateAdmin()">
		<div class="button-container">
			<button class="btn btn-warning">게시글관리</button>
		</div>
	</form>
	<form action="admin_contact"  method="POST" onsubmit="return validateAdmin()">
		<div class="button-container">
			<button class="btn btn-warning">문의글관리</button>
		</div>
	</form>
	<br/>
	<br/>



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