window.onload = function() {
	
	$("#signupManualDownBtn").on("click", function(){
		location.href = "/common/down/signup_manual.pdf";
	})
	
	
	//회원가입 눌렀을 시 animaiton
	const joinBtn = document.getElementById('join')
	const logoBtn = document.querySelector('h1')
	const slideContainer = document.querySelector('.slide-container')
	const joinWrap = document.querySelector('.join')
	let back = ''

	joinBtn.addEventListener('click', goJoin)

	// 회원가입 창으로 가기
	function goJoin() {
		slideContainer.style.left = '-100%'
		logoBtn.classList.remove('uneffect')
		setTimeout(function() {
			logoBtn.classList.add('effect')
		})
		back = document.querySelector('h1')
		joinWrap.classList.add('active')
		back.addEventListener('click', goBack)
		_memberInit()
	}

	//회원가입창에서 로그인창으로 돌아오기
	function goBack() {
		_memberInit()
		slideContainer.style.left = '0%'
		logoBtn.classList.remove('effect')
		setTimeout(function() {
			logoBtn.classList.add('uneffect')
			back.removeEventListener('click', goBack)
		})
		joinWrap.classList.remove('active')
	}

	//아이디, 로그인 버튼조작
	const idBtn = document.querySelector('.id > span')
	const pwdBtn = document.querySelector('.password > span')
	const pwdInput = document.querySelector('.password > input')

	idBtn.addEventListener('click', function(e) {
		e.target.classList.toggle('active')
	})
	pwdBtn.addEventListener('click', function(e) {
		e.target.classList.toggle('active')
		if (pwdBtn.classList.contains('active')) {
			pwdInput.setAttribute('type', 'text');
		} else {
			pwdInput.setAttribute('type', 'password');
		}
	})

	//select 선택 value에 추가
	let t = document.querySelector('.select')

	select(t);
	
	function select(index) {
		const selectInput = index.querySelector('input')
		const options = index.querySelectorAll('ul > li')

		index.addEventListener('click', function() {
			index.classList.toggle('active');
		})
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML
				
				$(selectInput).val(value)
				if(selectInput.getAttribute("id") == "roleInput"){
				
					var _role = $(this).attr('data-role');
					$(selectInput).attr('data-role', _role);
					
				}
			})
		})
	}
	
	//	쿠키	
	var _cookieData = koreasoft.modules.cookie.getCookie("icsWebAdminCookie");
	if(_cookieData != null){
		$("#saveId").addClass("active");
		$("input[name=username]").val(Decrypt(_cookieData, "icsWebAdminCookie"))
	}
	
	//	로그인
	$("#loginBtn").on("click", function(){
		if($("#saveId").hasClass("active")){
			var _username = $("input[name=username]").val();
			koreasoft.modules.cookie.setCookie("icsWebAdminCookie", Encrypt(_username, "icsWebAdminCookie"), 14);
		}else{
			if(_cookieData != null){
				koreasoft.modules.cookie.deleteCookie("icsWebAdminCookie");
			}		
		}		
		$("#loginForm").submit();
	});

	$("input[name=username]").on("keypress", function(e){
		if (e.keyCode == 13) {
			$("input[name=password]").focus();
		}
	});
	
	$("input[name=password]").on("keypress", function(e){
		if (e.keyCode == 13) {
			$("#loginBtn").click();	
		}
	});
	
	var _errorMsg = $("#errorMsg").text();
	if(_errorMsg != ""){
		//console.log(_errorMsg)
		pushNotify(_errorMsg, 2)
	}
	
	function pushNotify(msg, code) {
		
		var myNotify = new Notify({
			status: code == 1 ? 'success' : 'error',
			title: msg,
			//text: 'notify text',
			effect: 'slide',
			autoclose: true,
    		autotimeout: 3000,
			type: 3,
			position : 'center'
		})
	}
	
	function _memberInit(){
		//console.log(1)
		$("#roleInput").val("")
		$("#roleInput").attr('data-role', "")
		$("#companyName").val("");
		$("#memberId").val("");
		$("#memberIdChk").val("");
		$("#memberPwd1").val("");
		$("#memberPwd2").val("");
		$("#pwdCheck").text("");
		$("#pwdCheck").hide();
		$("#companyPresidentName").val("");
		$("#companyEmail").val("");
		$("#companyRegistNumber").val("");
		$("#companyPhone").val("");
		$("#companyAddress").val("");
		$("#companyUrl").val("");
		$("#agreeChk").prop("checked", false);
		$("#isIdError").hide();
	}
	
	var api = {
		"memberIsId" : "/api/v1/member/isId",				//	아이디 중복 체크
		"joinProc" : "/api/v1/company/joinProc"
	}
	
	//	가입하기 버튼을 누를경우
	$("#companyJoinBtn").on("click", function(){
		$("#memberJoinConfirmModal").addClass('active')
	})
	
	$("#memberJoinConfirmModal").find(".close").on("click", function(){
		$("#memberJoinConfirmModal").removeClass('active');
	})
	
	$("#memberJoinProcCancelBtn").on("click", function(){
		$("#memberJoinConfirmModal").removeClass('active');
	})
	
	$("#memberPwd1").on("keyup", function(){
		_pwdCheck()
	})		
	
	$("#memberPwd2").on("keyup", function(){
		_pwdCheck()
	});
	
	function _pwdCheck(){
		var _p1 = $("#memberPwd1").val();
		var _p2 = $("#memberPwd2").val();
		
		var _text = null;
		
		if(_p1 != _p2){
			_text = "비밀번호가 일치하지 않습니다.";
		}else{
			
			if(_p1 == "" && _p2 == ""){
				_text = "비밀번호를 작성해주시기 바랍니다";
			}else{
				_text = "비밀번호가 일치합니다";	
			}
			
		}
		
		$("#pwdCheck").show();
		$("#pwdCheck").text(_text);
		
	}	
	
	$("#memberId").on("keyup",function(e){
		//console.log($("#addMemberCheck").val())
		if($("#memberIdChk").val() != ""){
			$("#memberIdChk").val("");
		}
	})	
	
	//	신규등록 모달 - 회원 아이디 중복 체크
	$("#isIdBtn").on("click", function(){
		var _obj = {
				memberId : $("#memberId").val(),
			}
		
		if(!koreasoft.modules.regex.isId(_obj.memberId)){
			pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
			return;
		}
		
		_doAjaxPost(api.memberIsId, _obj, _isMemberIdCallback);
	})	
	
	function _isMemberIdCallback(res){
		var _result = res.data.isId;
		var _text = null;
		var _target = $("#isIdError");
		$(_target).text("");
		if(_result == true || _result == 'true'){
			_text = '사용 할 수 있는 아이디입니다';
			$("#memberIdChk").val("1");
			
		}else{
			_text = '이미 사용중인 아이디입니다';
		}
		$(_target).text(_text);
		$(_target).show();
		
	}
	
	$("#memberJoinProcBtn").on("click", function(){
			
		if($("#agreeChk").prop('checked') == false){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("동의하기를 체크해주시기 바랍니다",2);
			return;
		}
		
		var _p1 = $("#memberPwd1").val();
		var _p2 = $("#memberPwd2").val();
		
		var _text = null;
		
		if(_p1 != _p2){
			$("#memberJoinConfirmModal").removeClass('active');
			_text = "비밀번호가 일치하지 않습니다.";
			$("#pwdCheck").show();
			$("#pwdCheck").text(_text);
			return;
		}else{
			$("#memberJoinConfirmModal").removeClass('active');
			if(_p1 == "" && _p2 == ""){
				_text = "비밀번호를 작성해주시기 바랍니다";
				$("#pwdCheck").show();
				$("#pwdCheck").text(_text);
				return;
			}else{
				_text = "비밀번호가 일치합니다";
				$("#pwdCheck").show();
				$("#pwdCheck").text(_text);
			}
		}

		let selectManager = "";

		$(".stationChk:checked").each(function() {
			if(selectManager === "") {
				selectManager += $(this).val();
			} else {
				selectManager += "," + $(this).val();
			}
		})

		var _obj = {
			"companyName" : $("#companyName").val(),
			"companyPresidentName" : $("#companyPresidentName").val(),
			"companyEmail" : $("#companyEmail").val(),
			"companyRegistNumber" : $("#companyRegistNumber").val(),
			"companyPhone" : $("#companyPhone").val(), 
			"companyAddress" : $("#companyAddress").val(),
			"companyUrl" : $("#companyUrl").val(),
			"memberId" : $("#memberId").val(),
			"memberPwd": $("#memberPwd1").val(),
			"companyRoleIdx" : $("#roleInput").attr("data-role"),
			"selectManager" : selectManager
		}
		
		if(_obj.companyRole == ""){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("회원사 종류를 선택해주시기 바랍니다",2);
			return;
		}
		
		if(!koreasoft.modules.regex.isId(_obj.memberId)){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
			return;
		}
		
		if($("#memberIdChk").val() == ""){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("회원 아이디 중복 체크를 해주시기 바랍니다",2);
			return;
		}
		
		if(_obj.companyName == ""){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("회원사 이름을 작성해주시기 바랍니다",2);
			return;
		}		
		
		if(!koreasoft.modules.regex.isEmail(_obj.companyEmail)){
			$("#memberJoinConfirmModal").removeClass('active');
			pushNotify("이메일 주소 형식에 작성해주시기 바랍니다", 2);
			return;
		}

		if( $(".stationChk:checked").length === 0 ) {
			pushNotify("한 개 이상의 운영사를 선택해주세요.", 2);
			return false;
		}

		_doAjaxPost(api.joinProc, _obj, _setJoinProcCallback)
		
	})
	
	function _setJoinProcCallback(res){
		pushNotify("가입에 성공하였습니다", 1)
		$("#memberJoinConfirmModal").removeClass('active')
		goBack()
		
	}
	
	function _doAjaxPost(url, data, callBack){
		//console.log(data)
		$.ajax({
			type: "POST",
			url: url,
			dataType: "json",
			data: data == null ? null : JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			beforeSend: function(xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				xhr.setRequestHeader("AJAX", "true");
				$("#loading").show()

			},
			success: function(res) {
				callBack(res);
			},
			error: function(e) {
				console.log(e)
				if (e.hasOwnProperty('responseJSON')) {
						var _msg = e.responseJSON.message;
						pushNotify(_msg, 2)
				} else {
					alert("데이터 전송 중 에러가 발생했습니다");
				}	
			},
			complete: function(e) {
				$("#loading").hide()
			}
		});
	}	
	
	
		
}

	
	