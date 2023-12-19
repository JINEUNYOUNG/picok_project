<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="assets/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/templatemo.css">
<link rel="stylesheet" href="assets/css/custom.css">
<link rel="stylesheet" href="assets/css/fontawesome.min.css">
<link rel="stylesheet" href="assets/css/liststyle.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Roboto:wght@100;200;300;400;500;700;900&display=swap">
<link rel="icon" href="assets/img/ms-icon-310x310.png" type="image/x-icon">
	
<style>
font-family: SF Pro KR, SF Pro Display, SF Pro Icons, AOS Icons, Apple Gothic,
	HY Gulim, MalgunGothic, HY Dotum, Lexi Gulim, Helvetica Neue, Helvetica,
	Arial, sans-serif ; .layerPopup img {
	margin-bottom: 20px;
}

.layerPopup:before {
	display: block;
	content: "";
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, .5);
	z-index: 9000
}

.layerPopup .layerBox {
	z-index: 10000;
	position: fixed;
    left: 50%; /* 왼쪽 위치를 50%로 설정 */
    top: 50%; /* 위쪽 위치를 50%로 설정 */
    transform: translate(-50%, -50%); /* 중앙 정렬을 위한 transform 설정 */
	padding: 30px;
	background: #fff;
	border-radius: 6px;
}

.layerPopup .layerBox .title {
	margin-bottom: 10px;
	padding-bottom: 10px;
	font-weight: 600;
	border-bottom: 1px solid #d9d9d9;
}

.layerPopup .layerBox .btnTodayHide {
	font-size: 14px;
	font-weight: 600;
	color: black;
	float: left;
	text-decoration: none;
	width: 150px;
	height: 30px;
	line-height: 30px;
	border: black solid 1px;
	text-align: center;
	text-decoration: none;
}

.layerPopup div {
	display: inline;
}

.layerPopup form {
	margin-top: 5px;
	font-size: 16px;
	font-weight: 600;
	weight: 100%;
	height: 30px;
	line-height: 30px
}

.layerPopup #close {
	font-size: 16px;
	font-weight: 600;
	width: 40px;
	height: 30px;
	color: black;
	float: right;
	line-height: 30px;
	text-align: center;
	text-decoration: underline;
}

.layerPopup a {
	text-decoration: none;
	color: black;
	width: 50px;
	height: 40px;
}
</style>


</head>


<body>
    <div class="layerPopup" id="layer_popup" style="visibility: hidden;">
        <div class="layerBox">
            <h2 class="title">7월의 추천 사진전</h2>
            <div class="cont">
                <p>
                    <img src="assets/img/popup.jpg" width="350" height="500" usemap="#popup" alt="event page">
                </p>
            </div>
            <form name="pop_form">
                <div id="check">
                    <input type="checkbox" name="chkbox" value="checkbox" id="chkbox">
                    <label for="chkbox">&nbsp;&nbsp;오늘 하루동안 보지 않기</label>
                </div>
                <div id="close">
                    <a href="javascript:closePop();">닫기</a>
                </div>
            </form>
        </div>
    </div>

<script>
    function closePop() {
        if (document.pop_form.chkbox.checked) {
            setCookie("popupShown", "true", 1);
        }
        document.getElementById('layer_popup').style.visibility = "hidden";
    }

    function setCookie(name, value, expiredays) {
        var todayDate = new Date();
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + escape(value) + "; path=/; expires=" + todayDate.toGMTString() + ";";
    }

    var cookiedata = document.cookie;
    if (cookiedata.indexOf("popupShown=true") < 0) {
        document.getElementById('layer_popup').style.visibility = "visible";
    }
</script>
</body>
</html>
