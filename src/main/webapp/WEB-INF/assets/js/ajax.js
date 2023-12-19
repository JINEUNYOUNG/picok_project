
function joinOK() {
	let id = $("#id").val();
	let nickname = $("#nickname").val();
	let name = $("#name").val();
	let email = $("#email").val();
	let address = $("#address").val();
	let phone_num = $("#phone_num").val();
	
	
    let pw = $("#password").val();
    let pw2 = $("#password2").val();
    let number = pw.search(/[0-9]/g);
    let english = pw.search(/[a-z]/ig);
    let spece = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

    if (pw.length < 8 || pw.length > 20) {
        alert("비밀번호는 8자리 ~ 20자리 이내로 입력해주세요.");
        return false;

    } else if (pw.search(/\s/) != -1) {
        alert("비밀번호는 공백 없이 입력해주세요.");
        return false;

    } else if (number < 0 || english < 0 || spece < 0) {
        alert("비밀번호는 영문,숫자,특수문자를 혼합하여 입력해주세요.");
        return false;

    } else if ((number < 0 && english < 0) || (english < 0 && spece < 0) || (spece < 0 && number < 0)) {
        alert("비밀번호는 영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
        return false;

    } else if (/(\w)\1\1\1/.test(pw)) {
        alert('비밀번호는 같은 문자를 4번 이상 사용하실 수 없습니다.');
        return false;

    } else if (pw.search(id) > -1) {
        alert("비밀번호에 아이디가 포함되었습니다.");
        return false;
    } else if (false === reg.test(pw)) {
        alert('비밀번호는 8자 이상이어야 하며, 22숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        return false;
    } else if (pw != pw2) {
		alert("비밀번호가 상이합니다.");
		return false;
    } else if (id == "" || name =="" || nickname == "" || email == "" || address == "" || phone_num == ""){
    	alert("모두 입력 바랍니다.");
    	return false;
    } else {
        alert("가입이 완료되었습니다. 로그인 하시기 바랍니다.");
        return true;
    }

		
}



//	비밀번호가 일치하는가 확인하는 함수

function passwordCheckFunction() {
    let id = $("#id").val();
    let pw = $("#password").val();
    let number = pw.search(/[0-9]/g);
    let english = pw.search(/[a-z]/ig);
    let spece = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    let reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

    if (pw.length < 8 || pw.length > 20) {
        alert("8자리 ~ 20자리 이내로 입력해주세요.");
        return false;

    } else if (pw.search(/\s/) != -1) {
        alert("비밀번호는 공백 없이 입력해주세요.");
        return false;

    } else if (number < 0 || english < 0 || spece < 0) {
        alert("영문,숫자,특수문자를 혼합하여 입력해주세요.");
        return false;

    } else if ((number < 0 && english < 0) || (english < 0 && spece < 0) || (spece < 0 && number < 0)) {
        alert("영문,숫자, 특수문자 중 2가지 이상을 혼합하여 입력해주세요.");
        return false;

    } else if (/(\w)\1\1\1/.test(pw)) {
        alert('같은 문자를 4번 이상 사용하실 수 없습니다.');
        return false;

    } else if (pw.search(id) > -1) {
        alert("비밀번호에 아이디가 포함되었습니다.");
        return false;
    } else {
        alert("비밀번호가 정상적으로 입력되었습니다.");
        return true;
    }

    if (false === reg.test(pw)) {
        alert('비밀번호는 8자 이상이어야 하며, 22숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        return false;
    } else {
        alert("비밀번호가 정상적으로 입력되었습니다.");
        return true;
    }
}
    
function passwordSameFunction() {
    let pw = $("#password").val();
    let pw2 = $("#password2").val();
    
	
	if (pw != pw2) {
		alert("비밀번호가 상이합니다.");
	} else {}
    

}

//	아이디 중복 검사를 실행하는 함수
function idCheck() {
	let id = document.getElementById('id').value;

	$.ajax({
		type: 'POST', // 요청 방식
		url: '/picok_project/idCheck',
		data: {
			id: id
		},
		success: res => {
			  const parsedRes = JSON.parse(res); // JSON 문자열을 객체로 파싱

			  switch (parsedRes.res) {
			    case 1:
			      $('#messageContent').html('아이디를 입력하고 중복 체크 버튼을 누르세요.');
			      $('#messageCheck').attr('class', 'modal-content panel-warning');
			      $('#id').val('');
			      break;
			    case 2:
			      $('#messageContent').html('사용중인 아이디 입니다.');
			      $('#messageCheck').attr('class', 'modal-content panel-warning');
			      $('#id').val('');
			      break;
			    case 3:
			      $('#messageContent').html('사용 가능한 아이디입니다.');
			      $('#messageCheck').attr('class', 'modal-content panel-success');
			      break;
			    default:
			      // 기본 동작
			      break;
			  }
			  $('#messageModal').modal('show');
			}
		,
		error: e => console.log('1요청 실패:', e.status)
	});
}


//	닉네임 중복 검사를 실행하는 함수
function nicknameCheck() {
	let nickname = document.getElementById('nickname').value;

//	jQuery ajax
	$.ajax({
		type: 'POST', // 요청 방식
		url: '/picok_project/nicknameCheck',
		data: {
			nickname: nickname
		},
		success: res => {
			  const parsedRes = JSON.parse(res); // JSON 문자열을 객체로 파싱

			  switch (parsedRes.res) {
			    case 1:
			      $('#messageContent').html('닉네임을 입력하고 중복 체크 버튼을 누르세요.');
			      $('#messageCheck').attr('class', 'modal-content panel-warning');
			      $('#nickname').val('');
			      break;
			    case 2:
			      $('#messageContent').html('사용중인 닉네임 입니다.');
			      $('#messageCheck').attr('class', 'modal-content panel-warning');
			      $('#nickname').val('');
			      break;
			    case 3:
			      $('#messageContent').html('사용 가능한 닉네임입니다.');
			      $('#messageCheck').attr('class', 'modal-content panel-success');
			      break;
			    default:
			      // 기본 동작
			      break;
			  }
			  $('#messageModal').modal('show');
			}
		,
		error: e => console.log('1요청 실패:', e.status)
	});
}


