window.onload = function() {

	var api = {
		"myCompanyInfo" : "/operator/mypage/companyInfo",	//	회사 정보 조회
		"myCompanySave" : "/operator/mypage/companyModProc",	//	회사 정보 수정
		"myMemberList" : "/operator/mypage/memberList",		//	회원 정보 목록 조회
		"memberIsId" : "/api/v1/member/isId",				//	아이디 중복 체크
		"memberAddProc" : "/api/v1/member/addProc",			//	회원 등록
		"memberDelProc" : "/api/v1/member/delProc",			//	회원 삭제
		"memberInfo" : "/api/v1/member/info",				//	회원 정보 조회
		"memberModProc" : "/api/v1/member/modProc",			//	회원 수정
		"memberPwdModProc" : "/api/v1/member/pwdModProc",	//	회원 비밀번호 수정
	}

	function modal(index) {
		const closeBtn = index.querySelector('.close')
		closeBtn.addEventListener('click', function() {
			index.classList.remove('active')
		})
	}
	function makeModalBtn(index, btn) {
		btn.addEventListener('click', function() {
			index.classList.add('active');
		})
	}
	
	function _getMyMemberList(){
		_doAjaxGet(api.myMemberList, null, _setMyMemberList);	
	}	
	
	//	마이페이지 - 회사의 회원 정보 목록 컬백
	function _setMyMemberList(res){
		var _list = res.data.myMemberList;
		var _target = $("#myMemberListUl");
		$(_target).find("li").remove();
		
		if(_list.length == 0){
			var _li = `
				<li class="myMemberListLi" data-member-idx='0'>
					조회된 데이터가 없습니다
				</li>
			`;
			$(_target).append(_li);
			
		}else{
			for(var i=0; i<_list.length; i++){
				var _memberIdx = _list[i].memberIdx;
				var _memberId = _list[i].memberId;
				var _memberDeptName = _list[i].memberDeptName;
				var _memberName = _list[i].memberName;
				var _memberRank = _list[i].memberRank;
				var _memberTel = _list[i].memberTel;
				var _memberCreateDateDt = _list[i].memberCreateDateDt;
				var _memberEmail = _list[i].memberEmail;
				
				var _li = `
					<li class="myMemberListLi" data-member-idx='${_memberIdx}' data-member-id='${_memberId}'>
                        <p>· 회원 아이디: ${_memberId}</p>
                        <p>· 부서: ${_memberDeptName}</p>
                        <p>· 회원 이름: ${_memberName}</p>
                        <p>· 직책: ${_memberRank}</p>
                        <p>· 연락처: ${_memberTel}</p>
                        <p>· 등록 일자: ${_memberCreateDateDt}</p>
                        <p>· 이메일 주소: ${_memberEmail}</p>
                        <span class="close" ></span>
                    </li>
				`;
				
				$(_target).append(_li);
			}
			
			var _li = $(".myMemberListLi");
			$(_li).on("click", function(e) {
				e.preventDefault()
				$(".myMemberListLi").removeClass("active");
				$(this).addClass("active");

				if (e.target.className != 'close') {
					$("#modifyModalBtn").click();
				}

			});
		}

		//	삭제 이벤트
		var _delBtn = $("#myMemberListUl").find(".close");
		$(_delBtn).on("click", function(e){
			
			if(e.target.className == 'close'){
				var _memberId = $(this).parent('.myMemberListLi').attr('data-member-id');
				var _memberIdx =  $(this).parent('.myMemberListLi').attr('data-member-idx')
				memberDelObj.memberId = _memberId;
				memberDelObj.memberIdx = _memberIdx;
				memberDelObj.companyIdx = $("#memberCompanyIdx").val();   
				
				$("#memberDelSpan").html("")
				var _text = `<span>${_memberId}</span>을(를)<br>
						<b>삭제</b>하시겠습니까?`;	//${_memberId}님을 삭제 하시겠습니까?; 
				$("#memberDelSpan").append(_text);
				
				$("#deleteModal").addClass("active");
			}
		});
	}

	//	신규등록 모달 - 팝업
	$("#addMemberModalBtn").on("click", function(){
		$("#addMemberModal").addClass("active");
	})
	
	//	신규등록 인풋 - 초기화
	function _addMemberInit(){
		$("#addMemberIdError").hide();
		$("#addMemberId").val("");
		$("#addMemberCheck").val("");
		$("#addMemberPwd").val("");
		$("#addMemberName").val("");
		$("#addMemberTel").val("");
		$("#addMemberEmail").val("");
		$("#addMemberDeptName").val("");
		$("#addMemberRank").val("");
	}
	
	//	신규등록 모달 - 창 닫기
	$("#addMemberCancelBtn").on("click", function(){
		_addMemberInit();
		$("#addMemberModal").removeClass("active");
	})
	
	$("#addMemberModal").find(".close").on("click",function(){
		_addMemberInit();
		$("#addMemberModal").removeClass("active");
	});
	
	//	신규등록 - 저장 버튼
	$("#addMemberProcBtn").on("click", function(){
		//if(confirm("신규 회원을 등록하시겠습니까?")){
			var _obj = {
					memberId : $("#addMemberId").val(),
					memberPwd : $("#addMemberPwd").val(),
					memberName : $("#addMemberName").val(),
					memberTel : $("#addMemberTel").val(),
					memberEmail : $("#addMemberEmail").val(),
					memberDeptName : $("#addMemberDeptName").val(),
					memberRank : $("#addMemberRank").val(),
					companyIdx : $("#memberCompanyIdx").val()
				}
				
				//	회원아이디, 비밀번호, 회원이름
				if(!koreasoft.modules.regex.isId(_obj.memberId)){
					pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
					return;
				}
				
				if($("#addMemberCheck").val() == ""){
					pushNotify("회원 아이디 중복 체크를 해주시기 바랍니다",2);
					return;
				}
				
				if(_obj.memberPwd == ""){
					pushNotify("비밀번호를 입력해주시기 바랍니다",2);
					return;
				}
				
				if(_obj.memberName == ""){
					pushNotify("회원 이름을 작성해주시기 바랍니다",2);
					return;				
				}
				
				if(_obj.memberTel != ""){
					if(!koreasoft.modules.regex.isPhone(_obj.memberTel)){
						pushNotify("모바일 번호에 맞게 작성해주시기 바랍니다",2);
						return;
					}
					
				}
				_obj.memberTel = _obj.memberTel.replace(/-/gi,'');
				_doAjaxPost(api.memberAddProc, _obj, _memberAddProcCallback);
		//}
	});
	
	function _memberAddProcCallback(res){
		_getMyMemberList();
		_addMemberInit();
		$("#addMemberModal").removeClass("active");
		
		pushNotify("저장에 성공하였습니다",1);
	}
	
	//	신규등록 모달 - 회원 아이디 중복 체크
	$("#memberIdCheckBtn").on("click", function(){
		var _obj = {
				memberId : $("#addMemberId").val(),
			}
		
		if(!koreasoft.modules.regex.isId(_obj.memberId)){
			pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
			return;
		}
		//console.log(_obj)
		_doAjaxPost(api.memberIsId, _obj, _isMemberIdCallback);
	})	
	
	$("#addMemberId").on("keyup",function(e){
		//console.log($("#addMemberCheck").val())
		if($("#addMemberCheck").val() != ""){
			$("#addMemberCheck").val("");
		}
	})	
	
	function _isMemberIdCallback(res){
		var _result = res.data.isId;
		var _text = null;
		var _target = $("#addMemberIdError");
		$(_target).text("");
		if(_result == true || _result == 'true'){
			_text = '사용 할 수 있는 아이디입니다';
			$("#addMemberCheck").val("1");
			
		}else{
			_text = '이미 사용중인 아이디입니다';
		}
		$(_target).text(_text);
		$(_target).show();
		setTimeout(function(){
			$(_target).hide();	
		},10000)
	}
		
	var memberDelObj = {
		memberIdx : 0,
		memberId : null,
		companyIdx : 0
	}
	
	function _memberDelObjInit(){
		$("#memberDelSpan").html("")
		memberDelObj.memberIdx = 0;
		memberDelObj.memberId = null;
		memberDelObj.companyIdx = 0;
	}	
	
		//	회원정보 삭제 저장 버튼
	$("#memberDelProcBtn").on("click", function(){
		//	memberDelObj 
		if(memberDelObj.memberId == null || memberDelObj.memberIdx == 0){
			pushNotify("선택된 회원이 없습니다",2);
			return;
		}
		
		_doAjaxPost(api.memberDelProc, memberDelObj, _memberDelProcCallback);
	});
	
	//	회원정보 삭제 모달 취소 버튼
	$("#memberDelProcCancelBtn").on("click", function(){
		_memberDelObjInit();
		$("#deleteModal").removeClass("active");
		
	})
	
	//	회원정보 삭제 모달 close 버튼
	$("#deleteModal").find(".close").on("click", function(){
		$("#memberDelProcCancelBtn").click();
	});
	
	//	회원정보 삭제 컬백
	function _memberDelProcCallback(res){
		$("#memberDelProcCancelBtn").click();
		_memberDelObjInit();
		_getMyMemberList();
		pushNotify("삭제에 성공하였습니다",1);
	}	
	
	//	회원정보 수정 모달 - 버튼
	$("#modifyModalBtn").on("click", function(){
		var _active = $("#myMemberListUl").find(".active")
		if(_active.length == 0){
			pushNotify("회원을 선택해주시기 바랍니다",2);
			return;
		}else{
			var _obj = {
				"memberIdx": $(_active).attr('data-member-idx')
			}
			
			_doAjaxGet(api.memberInfo, _obj, _setModifyModal);
		}
	})
	
	//	회원정보 수정 모달 - 컬백
	function _setModifyModal(res){
		var _data = res.data.info;
		
		$("#memberModIdx").val(_data.memberIdx);
		$("#memberModId").val(_data.memberId);
		$("#memberModName").val(_data.memberName);
		$("#memberModTel").val(_data.memberTel);
		$("#memberModEmail").val(_data.memberEmail);
		$("#memberModDeptName").val(_data.memberDeptName);
		$("#memberModRank").val(_data.memberRank);
		
		$("#modifyModal").addClass("active");
	}
	
	//	회원정보 수정 모달 - 취소 버튼
	$("#modifyModal").find(".close").on("click", function(){
		_memberInfoModifyModalClear();
	});
	
	//	회원정보 수정 모달 - 취소 버튼
	$("#memberModCancelBtn").on("click", function(){
		_memberInfoModifyModalClear();
	})
	
	//	회원정보 수정 인풋 - 초기화
	function _memberInfoModifyModalClear(){
		$("#modifyModal").removeClass("active")
		$("#memberModId").val("");
		$("#memberModName").val("");
		$("#memberModTel").val("");
		$("#memberModEmail").val("");
		$("#memberModDeptName").val("");
		$("#memberModRank").val("");
		$("#memberModIdx").val("");
	}	
	
		//	회원정보 수정 저장 - 버튼
	$("#memberModProcBtn").on("click", function(){
		//if(confirm("저장하시겠습니까?")){
			var _obj = {
				"memberName" : $("#memberModName").val(),
				"memberTel" : $("#memberModTel").val(),
				"memberEmail" : $("#memberModEmail").val(),
				"memberRank" : $("#memberModRank").val(),
				"memberDeptName" : $("#memberModDeptName").val(),
				"memberIdx" : $("#memberModIdx").val()
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
			
			_doAjaxPost(api.memberModProc, _obj, _memberModProcCallback)
		//}
	});
	
	function _memberModProcCallback(res){
		_memberInfoModifyModalClear();
		_getMyMemberList();
		pushNotify("수정에 성공하였습니다",1);
		if(res.data.flag== 1){
			$("#myName").text(res.data.memberName)
		}
	}	
	
	//	비밀번호 변경 - 모달 팝업
	$("#pwdModalBtn").on("click", function(){
		var _active = $("#myMemberListUl").find(".active")
		if(_active.length == 0){
			pushNotify("회원을 선택해주시기 바랍니다",2);
			return;
		}else{
			var _memberIdx = $(_active).attr('data-member-idx');
			$("#modPwdMemberIdx").val(_memberIdx);			
		}
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
		$("#modPwdMemberIdx").val("")
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
	}
	
	$("#modPwd1").on("keyup", function(){
		_pwdCheck()
	})		
	
	$("#modPwd2").on("keyup", function(){
		_pwdCheck()
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
				memberIdx : $("#modPwdMemberIdx").val(),
				memberPwd : $("#modPwd1").val()
			}
			
			_doAjaxPost(api.memberPwdModProc, _obj, _memberPwdModProcCallback)
		//}
	})
	
	function _memberPwdModProcCallback(res){
		_pwdModalInputClear();
		pushNotify("비밀번호 변경에 성공하였습니다",1)
	}	
	
	//	실증수요기관 정보 변경 버튼
	$("#companyModBtn").on("click", function(){
		_companyInfoInput(false)
	});
	
	function _companyInfoInput(bool){
		$("#companyName").prop("readonly", bool);
		$("#companyPresidentName").prop("readonly", bool);
		$("#companyEmail").prop("readonly", bool);
		$("#companyRegistNumber").prop("readonly", bool);
		$("#companyPhone").prop("readonly", bool);
		$("#companyAddress").prop("readonly", bool);
		$("#companyUrl").prop("readonly", bool);
		
		if(bool){	// bool == true
			$("#companyModBtn").show();
			$("#companySaveBtn").hide();
			$("#companyCancelBtn").hide();
		}else{		//	bool == false
			$("#companyModBtn").hide();
			$("#companySaveBtn").show();
			$("#companyCancelBtn").show();
		}
	}
	
	function _getCompanyInfo(){
		_doAjaxGet(api.myCompanyInfo, null, _setMyCompanyInfo);
	}
		
	//	실증수요기관 정보 변경 취소 버튼
	$("#companyCancelBtn").on("click", function(){
		_ccModalType = 0;	//	공통 모달 초기화
		_companyInfoInput(true);
		_getCompanyInfo();
	});
	
	//	실증수요기관 정보 변경 저장
	$("#companySaveBtn").on("click", function(){
		_popCcModal(1)
	});
	
	function _companySaveCallback(res){
		_ccModalType = 0;					//	공통 모달 초기화
		$("#ccModProcCancelBtn").click();	//	active 제거
		_companyInfoInput(true)
		_getCompanyInfo();					//	회사 정보 불러오기
		pushNotify("저장에 성공하였습니다",1);
		
	}
	
	function _setMyCompanyInfo(res){
		
		var _obj = res.data.myCompanyInfo;
		$("#companyName").val(_obj.companyName);
		$("#companyPresidentName").val(_obj.companyPresidentName);
		$("#companyPhone").val(_obj.companyPhone);
		$("#companyEmail").val(_obj.companyEmail);
		$("#companyRegistNumber").val(_obj.companyRegistNumber);
		$("#companyAddress").val(_obj.companyAddress);
		$("#companyUrl").val(_obj.companyUrl);
		$("#myCompanyName").text(_obj.companyName)
	}	
	
	//	공통 모달
	var _ccModalType = 0;
	
	function _popCcModal(type){
		_ccModalType = type;
		var _header = null;
		var _text = null;
		if(type == 1){
			_header = "기관 정보 변경";
			_text = "기관 정보을(를) 변경하시겠습니까?";
		}
		$("#ccModalTitle").text(_header);
		$("#ccModalText").text(_text);
		$("#ccModal").addClass('active')
	}
	
	//	공통 모달  - 취소
	$("#ccModProcCancelBtn").on("click", function(){
		$("#ccModal").removeClass('active')
		
	})
	
	//	공통 모달  - 취소
	$("#ccModal").find(".close").on("click", function(){
		$("#ccModal").removeClass('active')
		
	})
		
	
	//	공통 모달 - 확인
	$("#ccModProcBtn").on("click", function(){
		
		if(_ccModalType == 1){
			
			var _obj = {
				"companyName" : $("#companyName").val(),
				"companyPresidentName" : $("#companyPresidentName").val(),
				"companyEmail" : $("#companyEmail").val(),
				"companyRegistNumber" : $("#companyRegistNumber").val(),
				"companyPhone" : $("#companyPhone").val(), 
				"companyAddress" : $("#companyAddress").val(),
				"companyUrl" : $("#companyUrl").val()
			}
			
			//	필수입력 : 실증기관 이름, 이메일주소
			if(_obj.companyName == ""){
				$("#ccModal").removeClass('active')
				pushNotify("실증기관 이름을 입력해주시기 바랍니다", 2);
				return;
			}
			
			if(!koreasoft.modules.regex.isEmail(_obj.companyEmail)){
				$("#ccModal").removeClass('active')
				pushNotify("이메일 주소 형식에 작성해주시기 바랍니다", 2);
				return;
			}
						
			_doAjaxPost(api.myCompanySave, _obj, _companySaveCallback)
		}
	})		
		
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
				alert("에러가 발생했습니다");
				console.log(e)
				if (e.status == 400) {
					//alert(e.responseJSON.message);
				} else {
					//alert("데이터 전송 중 에러가 발생했습니다");
				}
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
	
	function pushNotify(msg, code) {	
		var myNotify = new Notify({
			status: code == 1 ? 'success' : code == 2? 'error': 'warning',
			title: msg,
			//text: 'notify text',
			effect: 'slide',
			autoclose: true,
    		autotimeout: 3000,
			type: 3,
			position : 'center'
		})
	}	
	
	//	마이페이지 - 회사의 회원 정보 목록 호출
	_getMyMemberList()
	
		
}
