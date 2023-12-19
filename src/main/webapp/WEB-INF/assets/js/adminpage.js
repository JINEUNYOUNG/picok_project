
//카테고리별 글을 따로 모아보기.. 
function getFilteredData() {
	var category = document.getElementById("categorySelect").value;

	if (category === "0") {
		location.reload(true); 
		// 카테고리가 0인 경우 초기화면으로 돌아감
	} else {
		// 카테고리가 0이 아닌 경우 필터링된 데이터를 가져옴
		var xhr = new XMLHttpRequest();
		xhr.open('GET', 'admin_boardFilter?category=' + category, true);
		xhr.setRequestHeader('Content-Type', 'application/json; charset=utf-8');

		xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                	var responseData = JSON.parse(xhr.responseText);
                	console.log(responseData);
                	var boardList = responseData.boardList.list;
                	console.log(boardList);
                    // 테이블 업데이트
                    var containsUnansweredOrCompleted = boardList.some(function(item) {
                        return item.category === "미응답" || item.category === "응답완료";
                    });

                    if (containsUnansweredOrCompleted) {
                        updateTable2WithData(boardList); // 새로운 함수 호출
                    } else {
                        updateTableWithData(boardList); // 새로운 함수 호출
                    }
                	console.log("데이터 받음");
				} else {
					// 서버로부터 데이터를 받지 못했을 경우 에러 처리
					console.error("Failed to get filtered data.");
				}
			}
		};
        xhr.send();

	}
}


function updateTableWithData(boardList) {
    var tableBody = document.getElementById("tbody");
    if (tableBody !== null) {
        tableBody.innerHTML = ""; // 기존 내용 비우기
        console.log(boardList);

        for (var i = 0; i < boardList.length; i++) {
            var board = boardList[i];
            var date = new Date(board.board_reg_date);
	        var board_reg_date = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
	        var category;
		        if (board.category === "1") {
		            category = "nature";
		        } else if (board.category === "2") {
		            category = "city";
		        } else {
		            category = "daily";
		        }
		        
            var newRow = `
                <tr>
                	<td><input type="checkbox" class="checkbox" data-board-idx="${board.board_idx}"></td>
					<td>`+category+`</td>
                    <td>${board.board_title}</td>
                    <td>${board.board_content}</td>
                    <td>${board.id}</td>
                    <td>`+board_reg_date+`</td>
                    <td>${board.report_num}</td>
                </tr>`;
            tableBody.innerHTML += newRow;
        }
    } else {
        console.error("테이블 바디를 찾을 수 없습니다.");
    }
}
function updateTable2WithData(boardList) {
	var tableBody = document.getElementById("tbody");
	if (tableBody !== null) {
		tableBody.innerHTML = ""; // 기존 내용 비우기
		console.log(boardList);
		
		for (var i = 0; i < boardList.length; i++) {
			var board = boardList[i];
			var date = new Date(board.writeDate);
			if (board.answerDate){
				var answerdate = new Date(board.answerDate);
				var board_answer_date = answerdate.getFullYear() + '-' + (answerdate.getMonth() + 1).toString().padStart(2, '0') + '-' + answerdate.getDate().toString().padStart(2, '0');				
			} else { var board_answer_date ='-';}
			
			var board_reg_date = date.getFullYear() + '-' + (date.getMonth() + 1).toString().padStart(2, '0') + '-' + date.getDate().toString().padStart(2, '0');
			var category;
			var newRow = `
				<tr>
					<td>${board.category}</td>
					<td>${board.title}</td>
					<td>${board.content}</td>
					<td>${board.id}</td>
					<td>`+board_reg_date+`</td>
					<td>`+board_answer_date+`</td>
				</tr>`;
			tableBody.innerHTML += newRow;
		}
	} else {
		console.error("테이블 바디를 찾을 수 없습니다.");
	}
}


//게시글 다중삭제 함수
function deleteData() {
    var checkboxes = document.getElementsByClassName('checkbox');

    var selectedIds = [];

    // forEach로 체크된 데이터의 idx를 가져와 배열안에 추가한다 
    Array.prototype.forEach.call(checkboxes, function(checkbox) {
        if (checkbox.checked) {
            var boardIdx = checkbox.getAttribute('data-board-idx');
            selectedIds.push(boardIdx);
        }
    });

    // 선택된 글이 있다면 컨트롤러로 보내 삭제하는 함수를 호출한다
    if (selectedIds.length === 0) {
        alert('선택된 글이 없습니다.');
    } else {
        var xhr = new XMLHttpRequest();
        var url = '/picok_project/admin_delete'; 
        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert('삭제 요청이 성공적으로 완료되었습니다.');
                    window.location.reload(); 
                } else {
                    alert('삭제 요청이 실패하였거나 오류가 발생하였습니다.');
                }
            }
        };
        xhr.send('postIds=' + encodeURIComponent(selectedIds.join(',')));
				// 배열들의 값을 ,로 결합해서 POST방식으로 보낸다
    }
}


//회원탈퇴시키기
function deleteMember() {
    var checkboxes = document.getElementsByClassName('checkbox');
    var selectedIds = [];

    Array.prototype.forEach.call(checkboxes, function(checkbox) {
        if (checkbox.checked) {
            var memberIdx = checkbox.getAttribute('data-member-idx');
            selectedIds.push(memberIdx);
        }
    });

    if (selectedIds.length === 0) {
        alert('선택된 회원이 없습니다.');
        return;
    }

    var confirmation = confirm('정말로 선택한 회원을 삭제하시겠습니까?');
    if (confirmation) {
        var xhr = new XMLHttpRequest();
        var url = '/picok_project/admin_delete';

        xhr.open('POST', url, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    alert('삭제 요청이 성공적으로 완료되었습니다.');
                    window.location.reload();
                } else {
                    alert('삭제 요청이 실패하였거나 오류가 발생하였습니다.');
                }
            }
        };

        xhr.send('postIds=' + encodeURIComponent(selectedIds.join(',')));
    } else {
        alert('삭제가 취소되었습니다.');
    }
}



function goBack() {
    window.location.href = 'admin'; // adminpage.jsp로 이동
}
function goBack2() {
	window.location.href = 'admin_contact'; 
}


