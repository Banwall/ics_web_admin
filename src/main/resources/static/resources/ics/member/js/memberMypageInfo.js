window.onload = function() {
	
	//_doAjaxGet(api.myInfo, null, null);		

	var api = {
		"myInfo" : "/member/mypage/myInfo",
		"myInfoModProc" : "/member/mypage/myInfoModProc",
		"myInfoPwdModProc" : "/member/mypage/myInfoPwdModProc"
	}
	
	//	내정보 변경
	$("#myInfoModBtn").on("click", function(){
		_myInfoInput(false);
	});

	//	내정보 변경 저장
	$("#myInfoSaveBtn").on("click", function(){
		//if(confirm("저장하시겠습니까?")){
			var _obj = {
				"memberName" : $("#myInfoMemberName").val(),
				"memberTel" : $("#myInfoMemberTel").val(),
				"memberEmail" : $("#myInfoMemberEmail").val(),
				"memberRank" : $("#myInfoMemberRank").val(),
				"memberDeptName" : $("#myInfoMemberDeptName").val()
			}
			//	회원이름
			if(_obj.memberName == ""){
				pushNotify("회원 이름을 작성해주시기 바랍니다",2);
				return;
			}
			if (_obj.memberTel != "") {
				if (!koreasoft.modules.regex.isPhone(_obj.memberTel)) {
					pushNotify("모바일 번호에 맞게 작성해주시기 바랍니다", 2);
					return;
				}

			}
			_obj.memberTel = _obj.memberTel.replace(/-/gi, '');
			_doAjaxPost(api.myInfoModProc, _obj, _setMyInfoModProcCallback)
		//}		
	});
	
	//	내정보 변경 저장 컬백
	function _setMyInfoModProcCallback(res){
		//console.log(res)
		_myInfoInput(true)
		_doAjaxGet(api.myInfo, null, _setMyInfo);
		pushNotify("저장에 성공하였습니다",1);
			
	}
	
	
	//	내정보 변경 취소
	$("#myInfocancel").on("click", function(){
		//if(confirm("취소하시겠습니까?")){
			_myInfoInput(true);
			_doAjaxGet(api.myInfo, null, _setMyInfo);				
		//}		
	})
	
	//	내정보 변경 버튼을 누를경우
	function _myInfoInput(bool){
		$("#myInfoMemberName").prop("readonly", bool);
		$("#myInfoMemberTel").prop("readonly", bool);		
		$("#myInfoMemberEmail").prop("readonly", bool);
		$("#myInfoMemberDeptName").prop("readonly", bool);
		$("#myInfoMemberRank").prop("readonly", bool);					
		
		if(bool){	// bool == true
			$("#myInfoModBtn").show();
			$("#myInfoSaveBtn").hide();
			$("#myInfocancel").hide();
		}else{		//	bool == false
			$("#myInfoModBtn").hide();
			$("#myInfoSaveBtn").show();
			$("#myInfocancel").show();
		}
	}
			
	function _setMyInfo(res){
		var _obj = res.data.myInfo;
		$("#myInfoMemberName").val(_obj.memberName);
		$("#myInfoMemberTel").val(_obj.memberTel);		
		$("#myInfoMemberEmail").val(_obj.memberEmail);
		$("#myInfoMemberDeptName").val(_obj.memberDeptName);
		$("#myInfoMemberRank").val(_obj.memberRank);	
		$("#myName").text(_obj.memberName);		
	}
	
	//	비밀번호 변경
	$("#myInfoPwdBtn").on("click", function(){
		$("#pwdModal").addClass("active");
	})	
	
	//	비밀번호 변경 - 취소 모달
	$("#modPwdCancelBtn").on("click", function(){
		_pwdModalInputClear();
	});
	
	//	비밀번호 변경 - close 모달
	$("#pwdModal").find(".close").on("click", function(){
		_pwdModalInputClear();
	});
	
	function _pwdModalInputClear(){
		$("#pwdModal").removeClass("active");
		$("#modPwd1").val("");
		$("#modPwd2").val("");
		//$("#modPwdMemberIdx").val("")
		$("#modPwdError").text("");	
	}
	
	function _pwdCheck(){
		var _p1 = $("#modPwd1").val();
		var _p2 = $("#modPwd2").val();
		
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
		
		$("#modPwdError").text(_text);
	};
	
	$("#modPwd1").on("keyup", function(){
		_pwdCheck();
	});
	
	$("#modPwd2").on("keyup", function(){
		_pwdCheck();
	});
	
	$("#modPwdProcBtn").on("click", function(){
		//if(confirm("저장하시겠습니까")){
			var _p1 = $("#modPwd1").val();
			var _p2 = $("#modPwd2").val();
			
			if(_p1 != _p2){
				_text = "비밀번호가 일치하지 않습니다.";
				$("#modPwdError").text(_text);
				return;
			}else{
				if(_p1 == "" && _p2 == ""){
					_text = "비밀번호를 작성해주시기 바랍니다";
					$("#modPwdError").text(_text);
					return;
				}
			}
			
			var _obj = {
				//memberIdx : $("#modPwdMemberIdx").val(),
				memberPwd : $("#modPwd1").val()
			}
			
			_doAjaxPost(api.myInfoPwdModProc, _obj, _memberPwdModProcCallback)
		//}
	})
	
	function _memberPwdModProcCallback(res){
		_pwdModalInputClear();
		pushNotify("비밀번호 변경에 성공하였습니다",1)
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
	
	function _doAjaxGet(url, data, callBack) {
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			data: data == null ? null : data,
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
				alert("데이터 전송 중 에러가 발생했습니다");
			},
			complete: function(e) {
				$("#loading").hide()
			}
		});
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
