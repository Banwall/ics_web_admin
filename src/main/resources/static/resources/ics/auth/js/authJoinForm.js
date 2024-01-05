$(function(){
	
	const inputIcon = document.querySelectorAll('.input-icon');
	inputIcon.forEach(function(item) {
		item.addEventListener('click', function(e) {
			e.target.classList.toggle('on');
			if($(this).hasClass("on")){
				$("#password").attr("type", "text");
			}else{
				$("#password").attr("type", "password");
			}
		})
	});
	
	// 회원 가입 신청
	$("#joinProcBtn").on("click", function(){
		var _obj = {};
		_obj.username = $("#username").val();
		_obj.password = $("#password").val();
		_obj.name = $("#name").val();
		_obj.tel = $("#tel").val().replaceAll("-","");
		_obj.email = $("#email").val();
		_obj.companyIdx = $("#companySelect").val();
		if(_obj.companyIdx == "0"){
			var _text = "회원사를 선택해주세요"
			_modalOn(_text);
			return;
		}
		if( !koreasoft.modules.regex.isId(_obj.username)){
			//var _text = "아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.";
			var _text = "아이디는 대소문자 구분없이 6~20자 영문자 이어야 합니다";
			_modalOn(_text);
			return;
		}	
		if( !koreasoft.modules.regex.isPassword(_obj.password)){
			//var _text = "비밀번호는 영문자로 시작하며 영문자, 숫자, 특수문자를 최소 한개씩 조합하여 8 ~ 16자입니다";
			var _text = "비밀번호는 8자리 이상이며 영문자, 숫자, 특수문자 조합이어야 합니다";
			_modalOn(_text);
			return;
		}
		if( koreasoft.modules.regex.isNull(_obj.name)){
			var _text = "이름을 작성해주세요"; 
			_modalOn(_text);
			return;
		}
		if( !koreasoft.modules.regex.isPhone(_obj.tel)){
			var _text = "휴대폰 형식에 맞게 작성해주세요";
			_modalOn(_text);
			return;
		}
		if( !koreasoft.modules.regex.isEmail(_obj.email)){
			var _text = "이메일 형식에 맞게 작성해주세요";
			_modalOn(_text);
			return;
		}
		_doAjax("/auth/join/saveProc", _obj, _procCallback)
	});	
	
	function _modalOn(text){
		$("#errorText").text(text)
		$("#join-modal").addClass("on");
	}
	
	$(".modal-close").on("click", function(){
		$("#errorText").text("");
		$("#join-modal").removeClass("on");
	})

	//	Ajax
	function _doAjax(url, data, callBack){
		$.ajax({
			type: "POST",
			url: url,
			data: data == null? null : JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				/*xhr.setRequestHeader(_csrfHeader, _csrfToken);*/
				$("#loading").show();
			},
			success: function (res) {
				callBack(res);
			},
			error: function (e) {
				//console.log(e)
				alert("데이터 전송 중 에러가 발생했습니다");
			},
			complete : function(e){
				$("#loading").hide();
			}
		});
	}

	//	회원 가입 저장 콜백 함수	
	function _procCallback(res){
		if(res.result == 'false'|| res.result ==false){
			alert(res.msg);
			return;
		}else{
			alert("가입 신청을 완료했습니다");
			location.href="/";
		}
	}
	
});