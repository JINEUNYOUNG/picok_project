<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/liststyle.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">

<style>
.centered {
   display: flex;
   justify-content: center;
   align-items: center;
}

.img-naverButton {
   position: relative;
   display: inline-block;
   overflow: hidden;
   width: 48px;
   height: 48px;
   margin: 0 6px 12px;
   padding: 0 0 0 48px;
   white-space: nowrap;
   line-height: 48px;
   font-size: 14px;
   font-weight: 400;
   text-align: center;
   border-radius: 30px;
   border: none;
   background-repeat: no-repeat;
   background-color: #03c75a;
   background-size: 20px;
   background-position: 14px 15px;
   color: #fff;
   cursor: pointer;
   position: relative;
}

.img-naverButton::before {
   content: "N";
   position: absolute;
   top: 50%; /* 버튼 상단과 텍스트 중앙을 맞춤 */
   transform: translateY(-50%);
   left: 32%; /* 텍스트 왼쪽 여백 조정 */
   font-size: 25px;
   font-weight: bold;
}
</style>
</head>

<body>

<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>

<div class="container-fluid bg-light py-5">
	<div class="row py-3">
		<div class="col-md-6 m-auto text-center">
			<h1 class="h1">로그인</h1>
				<p>picok을 더 편리하게 이용하세요</p>
		</div>
	</div>
</div>

    <% if (request.getAttribute("message") != null) { %>
        <script>
            alert("${requestScope.message}");
        </script>
    <% } %>

<div class="col-lg-4 m-auto">
  <div class="form-group"><br/>
    <form action="loginOK" method="POST"  id="myForm">
      <input class="form-control"  type="hidden" name="command" value="login" style="width: 80px;height:30px;"><br />
      	<p>아이디&nbsp;&nbsp;&nbsp;&nbsp;
      <input class="form-control"  type="text" name="id" required="required"></p>
      	<p>패스워드 
      <input class="form-control"  type="password" name="password" required="required"></p><br />
      
       <div class="form-group form-check">
<input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe" checked>
      					  로그인 유지
  				  </div>
      
      <div style="display: flex; justify-content: flex-end; align-items: center; height: 50px;">
        <a href="../index.jsp" class="btn btn-outline-info">돌아가기</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="submit" class="btn btn-info" id="submitButton" value="로그인">
      </div>
      
    </form><br/>
             
         <div class="position-relative">
            <!-- 네이버 로그인 버튼 노출 영역 -->
            <h6 class="text-center">다른 계정 로그인</h6>

            <!-- 네이버 로그인 버튼 노출 영역 -->
            <div id="naver_id_login" style="text-align: center;">
               <a href="${url}"> 
             <button class="img-naverButton"></button>
               </a>
            </div>
<!--
               <img src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"
                  width="233" />
            <button class="img-kakaoButton"
               onclick="location.href='kakaoLogin.jsp'"></button>
            <button class="img-facebookButton"></button>
-->
         </div>
      </div>
   </div>
   <br/>

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>

<!-- Script -->
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>
<script src="/picok_project/assets/js/alert.js"></script>

</body>

</html>