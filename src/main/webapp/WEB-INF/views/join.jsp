<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Picok</title>
<link rel="stylesheet" href="/picok_project/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/templatemo.css">
<link rel="stylesheet" href="/picok_project/assets/css/custom.css">
<link rel="stylesheet" href="/picok_project/assets/css/fontawesome.min.css">
<link rel="stylesheet" href="/picok_project/assets/css/liststyle.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<script type="text/javascript" src="/picok_project/assets/js/jquery-3.7.0.js"></script>
<script type="text/javascript" src="/picok_project/assets/js/bootstrap.js" defer="defer"></script>
<script type="text/javascript" src="/picok_project/assets/js/ajax.js" defer="defer"></script>
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">

</head>

   <!-- 헤더 include -->
   <jsp:include page="common/header.jsp"></jsp:include>

	<div class="container-fluid bg-light py-5">
		<div class="row py-3">
			<div class="col-md-6 m-auto text-center">
				<h1 class="h1">회원가입</h1>
			</div>
		</div>
	</div>
<div class="col-md-6 m-auto" style="font-size: 10px;">  
	<div class="form-group" style="margin-left: 120px; font-size:10px; border: none;">
      
        <form method="post" action="joinOK" onsubmit="return joinOK()">
         <table class="table" style="text-align: center;"><br/><br/>
               <tr>
				<td style="text-align:left; margin-left:10px; width: 120px; vertical-align: middle;"><h6>아이디</h6></td>
                  <td>
                  <input class="form-control" type="text" id="id" name="id" maxLength="15" placeholder=""></td>
                  <td class="info" style="border:none;vertical-align: middle; width: 120px; text-align: center;">
                     <button class="btn btn-outline-info" type="button" onclick="idCheck()">중복검사</button>
                  </td>
               </tr>
               <tr>
                  <td style="width: 120px; vertical-align: middle; text-align:left; margin-left:10px; "><h6>비밀번호</h6></td>
                  <td>
                  <input class="form-control" type="password" id="password" name="password" maxLength="20" placeholder="숫자/대문자/소문자/특수문자를 포함해 8자 이상" autocomplete="off" onchange="passwordCheckFunction()"></td>
               </tr>
               <tr>
                  <td style="width: 120px; vertical-align: middle; text-align:left; margin-left:10px; "><h6>비밀번호확인</h6></td>
                  <td>
                  <input class="form-control" type="password" id="password2" name="password2" maxLength="20" placeholder="" autocomplete="off" onchange="passwordSameFunction()"></td>
               </tr>
               <tr>
                  <td style="width: 120px; vertical-align: middle;text-align:left; margin-left:10px; "><h6>이름</h6></td>
                  <td>
                  <input class="form-control" type="text" id="name" name="name" maxLength="20" placeholder=""></td>
               </tr>
               <tr>
                  <td style="width: 120px; vertical-align: middle;text-align:left; margin-left:10px; "><h6>닉네임</h6></td>
                  <td>
                  <input class="form-control" type="text" id="nickname" name="nickname" maxLength="20" placeholder="" ></td>
                  <td class="info" style=" border:none; vertical-align: middle; width: 120px; text-align: center;">
                     <button class="btn btn-outline-info" type="button" onclick="nicknameCheck()">중복검사</button>
                  </td>
               </tr>
               <tr>
                  <td style="width: 120px; vertical-align: middle;text-align:left; margin-left:10px; "><h6>이메일</h6></td>
                  <td><input class="form-control" type="text" id="email" name="email" maxLength="20" placeholder=""></td>
               </tr>
               <tr>
                  <td style="text-align: center;vertical-align: middle;text-align:left; margin-left:10px; "><h6>주소</h6></td>
                  <td style="text-align: left;">
                     <input type="text" id="postcode" name="zipcode" size="5" readonly> 
                     <input type="button" value="우편번호검색" onclick="checkPost()"><br/>
                     <input class="form-control" type="text"  maxLength="20" id="address" name="address" placeholder="" readonly>
                     <input class="form-control" type="text"  maxLength="20" id="detailAddress" name="detailAddress" placeholder="상세주소"></td>
               </tr>
               <tr>
                  <td style="width: 120px;vertical-align: middle;text-align:left; margin-left:10px; "><h6>핸드폰번호</h6></td>
                  <td>
                  <input class="form-control" type="text" id="phone_num" name="phone_num" maxLength="20" placeholder="핸드폰번호 '-'없이 숫자만 입력하세요."></td>
               </tr>
               <tr>  
               <td colspan="3" style=" border:none; text-align: center;">
                 
                  <h6 id="passwordCheckMessage" style="color: red; font-weight: bold;"></h6>
                  <h6 id="idCheckMessage" style="color: red; font-weight: bold;"></h6>
                  <h6 id="nicknameCheckMessage" style="color: red; font-weight: bold;"></h6>
           	 	  <h6 id="errorMessage" style="color: blue; font-weight: bold;"></h6> 
           	 	 
                  <input class="btn btn-info" type="submit" value="회원가입"/> 
                  <input class="btn btn-outline-info" type="reset" value="다시쓰기"/>
               </td>
            </tr>
      </table>
   </form>
  </div><br/><br/>
</div>

<!-- 회원 저장 모달 창 -->
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
<script src="https://kit.fontawesome.com/a5f5e6fa14.js"></script>
<script src="/picok_project/assets/js/jquery-1.11.0.min.js"></script>
<script src="/picok_project/assets/js/jquery-migrate-1.2.1.min.js"></script>
<script src="/picok_project/assets/js/bootstrap.bundle.min.js"></script>
<script src="/picok_project/assets/js/templatemo.js"></script>
<script src="/picok_project/assets/js/custom.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
function checkPost() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode; // 우편번호 (5자리)
            document.getElementById('address').value = data.address;
        }
    }).open();
}
</script>
</body>

</html>
