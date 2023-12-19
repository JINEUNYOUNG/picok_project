//하트 누를때 title on <-> title off 하면서 Ajax을 이용해 like+1 or like-1
function like(element) {
	var boardIdx = element.getAttribute("data-board-idx");

	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState === XMLHttpRequest.DONE) {
			if (xhr.status === 200) {
				if (xhr.responseText === "success_add") {
					console.log("OK");
					const isHeart = element.getAttribute("title") === "on";
					if (isHeart) {
						element.setAttribute('src', 'assets/img/like_off.png');
						element.setAttribute('title', 'off');
					} else {
						element.setAttribute('src', 'assets/img/like_on.png');
						element.setAttribute('title', 'on');
					}
				} else {
					console.log("취소한 건임")
				}
			} else {
				console.error("Ajax 요청 실패");
			}

		}
	};

	xhr.open('POST', 'listBoardLike', true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send('board_idx=' + boardIdx);

	
}
