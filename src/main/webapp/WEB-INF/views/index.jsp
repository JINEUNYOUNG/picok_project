<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/templatemo.css">
<link rel="stylesheet" href="assets/css/custom.css">
<link rel="stylesheet" href="assets/css/fontawesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
</head>
<body>

<% if (request.getAttribute("message") != null) { %>
    <script>
        alert("<%= request.getAttribute("message") %>");
    </script>
<% } %>
<%@ include file="popup.jsp" %>

<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>

	<!-- 사진(슬라이드) 3장 분량-->
	<div id="template-mo-zay-hero-carousel" class="carousel slide"
		data-bs-ride="carousel">
		<ol class="carousel-indicators">
			<li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="0" class="active"></li>
			<li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="1"></li>
			<li data-bs-target="#template-mo-zay-hero-carousel" data-bs-slide-to="2"></li>
		</ol>
		<div class="carousel-inner">
			<div class="carousel-item active">
				<div class="container">
					<div class="row p-5">
						<div class="mx-auto col-md-8 col-lg-6 order-lg-last">
							<img class="img-fluid" src="assets/img/landscape_nature_Carpathians_mountains_mist_forest_spring_green-117050.jpg!d.jpeg" alt="">
						</div>
						<div class="col-lg-6 mb-0 d-flex align-items-center">
							<div class="text-align-left align-self-center">
								<h1 class="h1 text-info">
									<b>Nature</b> picture of June
								</h1>
								<h3 class="h2">230511 [shin]</h3>
								<p>
									 #hujifilm #xt30ii #xf50140 <br/>
									Green Sight
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="carousel-item">
				<div class="container">
					<div class="row p-5">
						<div class="mx-auto col-md-8 col-lg-6 order-lg-last">
							<img class="img-fluid" src="assets/img/japan-5029658.jpg" alt="">
						</div>
						<div class="col-lg-6 mb-0 d-flex align-items-center">
							<div class="text-align-left">
								<h1 class="h1 text-info">
									<b>City</b> picture of June
								</h1>
								<h3 class="h2">Kakumanbuchi [제제]</h3>
								<p>
									05.22~06.01 Trip
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="carousel-item">
				<div class="container">
					<div class="row p-5">
						<div class="mx-auto col-md-8 col-lg-6 order-lg-last">
							<img class="img-fluid" src="assets/img/KakaoTalk_Photo_2023-07-11-21-18-29.jpeg" alt="">
						</div>
						<div class="col-lg-6 mb-0 d-flex align-items-center">
							<div class="text-align-left">
								<h1 class="h1 text-info">
									<b>Daily</b> picture of June
								</h1>
								<h3 class="h2">노노 [캔따개]</h3>
								<p>
									hujifilm x-t30 7artisans 35mm f1.2<br/>
									우리집 고양이 굉장히 귀여워요. 감사합니다.  
								</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev text-decoration-none w-auto ps-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="prev"> <i class="fas fa-chevron-left"></i>
		</a> <a class="carousel-control-next text-decoration-none w-auto pe-3" href="#template-mo-zay-hero-carousel" role="button" data-bs-slide="next"> <i class="fas fa-chevron-right"></i>
		</a>
	</div>
	<!-- 사진(슬라이드) 끝 -->
	
	<!--  부가 메뉴(3개) about, qna, contact -->
	<section class="container py-5">
		<div class="row text-center pt-3">
			<div class="col-lg-6 m-auto">
				<h1 class="h1">Site of Picok</h1>
				<p>Picok의 사이트에 대해 알아보세요</p>
			</div>
		</div>
		<div class="row">
			<div class="col-12 col-md-4 p-5 mt-3">
				<a href="about">
					<img src="assets/img/pexels-photo-1745937.jpeg" class="rounded-circle img-fluid border"></a>
				<h5 class="text-center mt-3 mb-3">About Picok</h5>
			</div>
			<div class="col-12 col-md-4 p-5 mt-3">
				<a href="qna">
					<img src="assets/img/question.jpg" class="rounded-circle img-fluid border"></a>
				<h2 class="h5 text-center mt-3 mb-3">QnA</h2>
			</div>
			<div class="col-12 col-md-4 p-5 mt-3">
				<a href="contact">
					<img src="assets/img/category_img_02.jpg" class="rounded-circle img-fluid border"></a>
				<h2 class="h5 text-center mt-3 mb-3">Contact Us</h2>
			</div>
		</div>
	</section>
	<!-- 부가 메뉴 끝 -->
	
	<!-- 하부 -->
	<section class="bg-light">
		<div class="container py-5">
			<div class="row text-center py-3">
				<div class="col-lg-6 m-auto">
					<h1 class="h1">Upload and Manage</h1>
					<p>사진을 업로드 하고 한눈에 관리하세요</p>
				</div>
			</div>
		</div>

	</section>

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>

<!-- js -->
<script src="assets/js/jquery-1.11.0.min.js"></script>
<script src="assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/templatemo.js"></script>
<script src="assets/js/custom.js"></script>
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>

</body>

</html>