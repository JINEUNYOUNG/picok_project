<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Picok</title>

<!-- 부트스트랩 외 아이콘 -->
<link rel="stylesheet"
   href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">

<link rel="stylesheet" href="/picok_project/assets/css/qna.css">
<!-- 파비콘 -->
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">

</head>
<body>


<!-- 헤더 include -->
<jsp:include page="common/header.jsp"></jsp:include>

   <!-- Start Content Page -->
   <div class="container-fluid bg-light py-5">
      <div class="row py-5">
         <div class="col-md-6 m-auto text-center">
            <h1 class="h1">QnA</h1>
            <p>
               궁금한 점을 확인해보세요. <br /> <br />
            </p>
         </div>
      </div>
   </div>
<br/>
<br/>

<div class="d-flex justify-content-center">
   <fieldset id="accordion">
      <label> <span>I can't log in/I forgot my password, what shoud I do?</span> <input type="radio" value="bar1"
         name="accordion">
         <div>
            <p>You can retrieve your username and password by typing your e-mail address on the Forgot pass page.
            After submitting the form you should receive your account information shortly. Please take a close look and try again.
            Make sure you have cookies enabled and your firewall settings are OK.
            If you don't receive your account information, please contact us. You'll probably need to change your e-mail address.</p>
         </div>
      </label> <label> <span>How can I change my username/password?</span> <input type="radio" value="bar2"
         name="accordion">
         <div>
            <p>Unfortunately you can't change your username. You can change your password at My account / Profile.
            Type the same new pass twice and it will be changed.</p>
         </div>
      </label> <label> <span>I can't find a photo I downloaded</span> <input type="radio" value="bar3"
         name="accordion">
         <div>
            <p>We have no control over the images of our artists. They come and go.
            If it's not there any more, it has probably been deleted from the site.
            Always make a note of the artist's username when you download a file from our site so that you can contact the person later if there's an emergency.</p>
         </div>
      </label>
   </fieldset>
</div>

<!-- 푸터 include -->
<jsp:include page="common/footer.jsp"></jsp:include>


<!-- Start Script -->
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>
 <!-- End Script -->

</body>
</html>