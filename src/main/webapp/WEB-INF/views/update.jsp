<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Picok</title>

<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/liststyle.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
</head>
<body>

<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>

	<div class="container-fluid bg-light py-5">
		<div class="row py-3">
			<div class="col-md-6 m-auto text-center">
				<h1 class="h2">사진 수정</h1>
			 	수정 페이지 입니다. 
			</div>
		</div>
	</div>
	<br />
	<br />
	<div class="col-lg-5 m-auto">
		<form action="updateOK?board_idx=${singlepage.getBoard_idx()}" method="post" enctype="multipart/form-data">
			<div class="form-group" style="width: 100%; min-height: 200px; background-color: #F5F5F5; border-radius: 10px;">
					<img id="preview" src="/picok_project/resources/upload/<c:out value='${singlepage.getFile_name()}' />" alt="미리보기" style="width: 100%; height: 100%; object-fit: cover;">
			</div>
			<div>
				<input type="file" name="file_name" onchange="photoView(event)"	accept="image/*"><br /> <br/>
			</div>

			<script>
				function photoView(event) {
					const preview = document.getElementById('preview');
					const file = event.target.files[0];
					const reader = new FileReader();
			
					reader.onloadend = function() {
						preview.src = reader.result;
						preview.style.display = 'block';
					}
			
					if (file) {
						reader.readAsDataURL(file);
					} else {
						preview.src = '';
						preview.style.display = 'none';
					}
				}
			</script>

			<div class="form-group">
				<label for="category">카테고리</label> 
				<select name="category" id="category" style="width:150px; height: 50px;">&nbsp;&nbsp;
					<option value="1">Nature</option>
					<option value="2">City</option>
					<option value="3">Daily</option>
				</select>
			</div>
			<div class="form-group">
				<label for="subject">제목</label> <input type="text" class="form-control" id="board_title" name="board_title" value="${singlepage.getBoard_title()}">
			</div>
			<div class="form-group">
				<label for="id">작성자</label> <input type="text" value="${sessionScope.mvo.id}" class="form-control" id="id" name="id" readonly="readonly">
			</div>
			<div class="form-group">
				<label for="content">내용</label>
				<textarea class="form-control" id="board_content" name="board_content" rows="3">${singlepage.getBoard_content()}</textarea>
			</div>

			<div>
				<input type="checkbox" hidden="hidden" name="secretOK" value="y" id="secretOK"> <label for="secretOK"></label>
			</div>
			<div class="form-group d-flex justify-content-center">
				<button type="submit" class="btn btn-primary" onclick="updatebutton()">수정하기</button>
			</div><br /> <br />
		</form>
	</div>

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>

<script>
function updatebutton() {
	alert('수정이 완료되었습니다.');
}
</script>
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>
</body>
</html>