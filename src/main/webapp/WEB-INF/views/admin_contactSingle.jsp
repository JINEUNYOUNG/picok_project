
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
    text-decoration: none; /* 하이퍼링크의 밑줄 제거 */
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
				<input class="btn btn-outline-warning btn-sm" type="button" value="뒤로" onclick="goBack2()">
				</h3>
			</div>
			</div>
		</div>
<section class="content" style="align-content: center;">
  <div class="container" style="justify-content: center; align-items: center; height: 500px;">
    <div class="row justify-content-md-center">
      <div class="col-md-4">
        <div class="notice-content">
<h4>문의글</h4>
<div class="form-group row">
  <label class="col-sm-2 col-form-label">제목:</label>
  <div class="col-sm-4">
    <input type="text" class="form-control" value="${boardList.title}" readonly="readonly">
  </div>
  <label class="col-sm-2 col-form-label">작성일:</label>
  <div class="col-sm-4">
    <input type="text" class="form-control" value="${boardList.writeDate}" readonly="readonly">
  </div>
</div>
<div class="form-group">
  <label for="writer">작성자:</label>
  <input type="text" class="form-control" id="writer" value="${boardList.id}" readonly="readonly">
</div>
<div class="form-group">
  <label for="email">이메일:</label>
  <input type="text" class="form-control" id="email" value="${boardList.email}" readonly="readonly">
</div>
<div class="form-group">
  <label for="inquiry-content">문의 내용:</label>
  <textarea class="form-control" id="inquiry-content" rows="5" readonly="readonly">${boardList.content}</textarea>
</div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="reply-form">
          <h4>답변 작성</h4>
          <form action="sendEmail">
            <div class="form-group">
              <input type="hidden" class="form-control" id="reply-email" name="reply-email" value=${boardList.email} >
              <input type="hidden" class="form-control" id="reply-email" name="reply-idx" value=${boardList.idx} >
              <label for="reply-title">제목:</label>
              <input type="text" class="form-control" id="reply-title" name="reply-title" value="[picok]답변드립니다.">
            </div>
            <div class="form-group">
              <label for="reply-content">내용:</label>
              <textarea class="form-control" id="reply-content" name="reply-content" rows="5">${boardList.answer}</textarea>
            </div><br/>
            <button type="submit" class="btn btn-primary">답변 작성</button>
          </form>
        </div>
      </div>
    </div>
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