<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>

<title>Picok</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
</head>


<body>
<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>

   <!-- 배너 -->
    <section class="bg-light py-3">
	<div class="container-fluid bg-light py-5">
		<div class="row py-3">
			<div class="col-md-6 m-auto ">
				<h1 class="h1">About picok</h1><br/>
                    <hr/><br/>
                    <p>   
                  PICOK은 누구나 이미지를 올리고 다운로드할 수 있는 플랫폼입니다. 사진 초보이든 스스로를 
                  프로 작가로 여기든 상관없이, 함께 공유해보세요. 
               </p>
                    <p> 
                  PICOK을 통해 이미지를 찾고 공유하는 새로운 경험을 만나보세요.
                  
                  
                  
                        
                    </p>
                </div>
						<div class="mx-auto col-md-6 col-lg-4 order-lg-last">
                    <img class="img-fluid"  style="700px;"src="/picok_project/assets/img/img.jpg" alt="About Hero">
                </div>
            </div>
        </div>
    </section>
    <!-- 배너끝 -->

    <!-- Start Section -->
    <section class="container py-5">
        <div class="row text-center pt-5 pb-3">
            <div class="col-lg-6 m-auto">
                <h1 class="h1">Our Services</h1>
                <p>
                    Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                    Lorem ipsum dolor sit amet.
                </p>
            </div>
        </div>

    </section>
    <!-- End Section -->


 <!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>


<!-- Start Script -->
 <script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
 <script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
 <script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
 <script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
 <script src="/picok_project/assets/js/templatemo.js"></script>
 <script src="/picok_project/assets/js/custom.js"></script>
</body>

</html>