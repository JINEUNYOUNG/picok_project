<%-- <%@page import="vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.BoardDAO"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<!DOCTYPE html>
<html lang="en">
<head>

<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet"
	href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/liststyle.css">

<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<script type="text/javascript"
	src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script type="text/javascript"
	src="/picok_project/assets/js/bootstrap.bundle.min.js" defer="defer"></script>
<script type="text/javascript" src="/picok_project/assets/js/ajax.js"
	defer="defer"></script>
<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png"
	type="image/x-icon">

</head>
<body>

	<%
	request.setCharacterEncoding("UTF-8");

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String nickname = request.getParameter("nickname");
	String email = request.getParameter("email");
	String addr = request.getParameter("addr");
	String phone_num = request.getParameter("phone_num");
	%>



	<!-- 헤더 include -->
	<jsp:include page="common/header.jsp"></jsp:include>


	<section class="bg-light py-5">
		<div class="container my-4">
			<div class="row text-center py-2">
				<div class="col-lg-6 m-auto">
					<h1 class="h1">My Page</h1>
					<p>profile</p>
				</div>
			</div>

			<div class="row d-flex flex-row">
				<!--Controls-->
				<div class="col-1 align-self-center">
					<!-- 프로필 이미지나 아이콘 등을 여기에 추가할 수 있습니다. -->
				</div>


				<!-- 프로필 정보 -->
				<form action="UpdateMember?id=${sessionScope.mvo.id}&name=${sessionScope.mvo.name} "
					method="POST" align="center">
					<div class="row d-flex flex-row">
						<div class="col-lg-5 m-auto">
							<div class="row">
								아이디<input value="${sessionScope.mvo.id}" disabled="disabled" type="text" name="id" id="id" style="margin: 10px;" required="required">
								이름<input value="${sessionScope.mvo.name}" disabled="disabled" type="text" name="name" id="name" style="margin: 10px;" required="required">
								비밀번호<input value="" placeholder="숫자/대문자/소문자/특수문자를 모두 포함하여 8자 이상" type="password" id="password" name="password" style="margin: 10px;" required="required" onchange="passwordCheckFunction()">
								비밀번호확인
								<input type="password" id="password2" name="password2" style="margin: 10px;" required="required" onchange="passwordCheckFunction()">
								닉네임
								<div class="row">
									<div class="col-md-8">
										<input value="${sessionScope.mvo.nickname}" type="text"
											id="nickname" name="nickname" class="form-control"
											style="margin-right: 10px;" required="required">
									</div>
									<div class="col-md-4">
										<button class="btn btn-outline-info" type="button"
											onclick="nicknameCheck()">중복검사</button>
									</div>
								</div>

								이메일<input value="${sessionScope.mvo.email}" type="email"
									name="email" style="margin: 10px;" required="required">
								<td>주소<input type="text" id="postcode" name="zipcode"
									size="5" style="margin: 10px;" placeholder="우편번호" readonly><input
									type="button" value="우편번호검색"
									style="width: 130px; margin: 10px;" onclick="checkPost()">
									<input value="${sessionScope.mvo.addr}" type="text"
									style="margin: 10px;" maxLength="20" id="address"
									name="address" placeholder="주소" readonly> <input
									class="form-control" type="text" maxLength="20"
									style="margin: 10px;" id="detailAddress" name="detailAddress"
									placeholder="상세주소"></td>
								</td>전화번호<input value="${sessionScope.mvo.phone_num}"
									type="phone_num" name="phone_num" style="margin: 10px;"
									required="required">


								<h5 id="passwordCheckMessage"
									style="color: red; font-weight: bold;"></h5>
								<h5 id="nicknameCheckMessage"
									style="color: red; font-weight: bold;"></h5>
								<h5 id="errorMessage" style="color: blue; font-weight: bold;">
							</div>
							<div class="form-group d-flex justify-content-center">
								<button type="submit" class="btn btn-info">저장하기</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button type="button" class="btn btn-info"
									onclick="location.href='mypage'">돌아가기</button>
							</div>
						</div>
					</div>
				</form>

				<div class="modal fade" id="messageModal2" tabindex="-1"
					aria-labelledby="messageType2" aria-hidden="true">
					<div class="modal-dialog modal-dialog-centered">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
								<h4 id="messageType2" class="modal-title">
									<%-- ${messageType} --%>
								</h4>
							</div>
							<div class="modal-body" id="messageContent2">
								<%-- ${messageContent} --%>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									data-bs-dismiss="modal">닫기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
<div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="messageModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content" id="messageCheck">
      <div class="modal-header">
        <h5 class="modal-title" id="messageType"></h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" id="messageContent">
         ${messageType}
         ${messageContent}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" id="messageModal2" tabindex="-1" aria-labelledby="messageType2" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <h4 id="messageType2" class="modal-title"></h4>
      </div>
      <div class="modal-body" id="messageContent2">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-info" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>


	<!-- 푸터 include -->
	<jsp:include page="common/footer.jsp"></jsp:include>

	<!-- Script -->
	<script src="https://kit.fontawesome.com/a5f5e6fa14.js"
		crossorigin="anonymous"></script>
	<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
	<script src="/picok_project/assets/js/templatemo.js"></script>
	<script src="/picok_project/assets/js/custom.js"></script>
	<script
		src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

	<script>
		function checkPost() {
			new daum.Postcode({
				oncomplete : function(data) {
					// 우편번호와 주소 정보를 해당 필드에 넣는다.
					document.getElementById('postcode').value = data.zonecode; // 우편번호 (5자리)
					document.getElementById('address').value = data.address;
				}
			}).open();
		}
	</script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>