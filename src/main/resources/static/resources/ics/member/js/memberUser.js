window.onload = function() {
	
	var api = {
		"myCompanyInfo" : "/member/user/companyInfo",		//	회사 정보 조회
		"myCompanySave" : "/member/user/companyModProc",	//	회사 정보 수정
		"myMemberList" : "/member/user/memberList",			//	회원 정보 목록 조회
		"myMemberDelProc" : "/api/v1/member/delProc",		//	회원 삭제
		"myMemberModProc" : "/api/v1/member/modProc",		//	회원 수정
		"myMemberAddProc" : "/api/v1/member/addProc",		//	회원 등록
		"myMemberPwdModProc" : "/api/v1/member/pwdModProc",	//	회원 비밀번호 수정
		"sensorList" : "/member/sensor/listOfCompany",		//	회사의 센서 목록 조회
		"memberIsId" : "/api/v1/member/isId",				//	아이디 중복 체크
		"memberInfo" : "/api/v1/member/info",				//	회원 정보 조회
		"sensorAddProc" : "/member/sensor/addProc",			//	센서 등록
		"sensorInfo" : "/member/sensor/info",				//	센서 정보 조회
		"sensorModProc" : "/member/sensor/modProc",			//	센서 수정
		"sensorDelProc" : "/member/sensor/delProc",			//	센서 삭제
		"sensorApproval" : "/member/sensor/approval",		//	센서 승인 요청
		"sensorIsId" : "/api/v1/sensor/isId"				//	센서 중복 체크
	}
		
	const addSensorModal = document.getElementById('sensorAddModal')
	const sensorDeleteModal = document.getElementById('sensorDeleteModal')
	
	makeModalBtn(addSensorModal, sensorAddModalBtn)

	modal(sensorAddModal)
	modal(sensorDeleteModal)

	function modal(index) {
		const closeBtn = index.querySelector('.close')
		closeBtn.addEventListener('click', function(e) {
			index.classList.remove('active')
			var _id = $(index).attr("id");
			if(_id == 'sensorAddModal'){
				_sensorAddInit();
			}
			
		})
	}
	function makeModalBtn(index, btn) {
		btn.addEventListener('click', function(e) {
			
			index.classList.add('active');
		})
	}

	const sensorAddSelect = document.querySelector('#sensorSelect')
	const locationSelect = document.querySelector('#locationSelect')
	const sensorModifySelect = document.querySelector('#sensorModifySelect')
	const stationModifySelect = document.querySelector('#stationModifySelect')

	select(locationSelect)
	select(sensorAddSelect)
	select(sensorModifySelect)
	select(stationModifySelect);

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
				//selectInput.setAttribute('value', value)
				let id = selectInput.getAttribute("id");
				if( id == "sensorAddInput"){
					$(selectInput).attr('data-sensor-type-idx', $(this).attr('data-sensor-type-idx'));
					$("#sensorAddCheck").val("");
				}else if(id == "stationAddInput"){
					$(selectInput).attr('data-station-idx', $(this).attr('data-station-idx'))
				}else if(id=="sensorModifyInput"){
					$(selectInput).attr('data-sensor-type-idx', $(this).attr('data-sensor-type-idx'))
					$("#sensorModCheck").val("");
				}else if(id=="stationModifyInput"){
					$(selectInput).attr('data-station-idx', $(this).attr('data-station-idx'))
				}
			})
		})
	}
	
	//	실증기업 정보 변경 버튼
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
		
	//	실증기업 정보 변경 취소 버튼
	$("#companyCancelBtn").on("click", function(){
		//if(confirm("취소하시겠습니까?")){
			_companyInfoInput(true);
			_doAjaxGet(api.myCompanyInfo, null, _setMyCompanyInfo);
		//}
	});
	
	//	실증기업 정보 변경 저장
	$("#companySaveBtn").on("click", function(){
		
		//if(confirm("저장하시겠습니까?")){
		
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
				pushNotify("실증기관 이름을 입력해주시기 바랍니다", 2);
				return;
			}
			
			if(!koreasoft.modules.regex.isEmail(_obj.companyEmail)){
				pushNotify("이메일 주소 형식에 작성해주시기 바랍니다", 2);
				return;
			}
					
			_doAjaxPost(api.myCompanySave, _obj, _companySaveCallback)
		//}
	});
	
	function _companySaveCallback(res){
		_companyInfoInput(true)
		_doAjaxGet(api.myCompanyInfo, null, _setMyCompanyInfo);
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
		
		//	헤더에 회사이름 넣기
		$("#myCompanyName").text(_obj.companyName)
	}
	
	var memberObj = {
		"nowPage" : 1,
		"listCount" : 4,
		"pageGroup" : 4,
		"page" : null
	};
	
	function _memberObjInit(){
		memberObj.nowPage = 1;
		memberObj.page = null;
	}
	
	var sensorObj = {
		"nowPage" : 1,
		"listCount" : 4,
		"pageGroup" : 4,
		"page" : null
	};
	
	function _sensorObjInit(){
		sensorObj.nowPage = 1;
		sensorObj.page = null;
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
	
	function _setMemberList(res){
		//console.log(res)
		var _list = res.data.memberList;
		var _memberPageInfo = res.data.page;
		
		var _target = $("#memberInfoListUl");
		//console.log(res)
		$(_target).find("li").remove();
		
		if(_list.length==0){
			//	조회된 데이터가 없는 경우
			var _li = `<li class='memberInfoListLi' data-member-idx='0'>조회된 데이터가 없습니다</li>`	
			$(_target).append(_li);
		}else{
			for(var i=0; i < _list.length; i++){
				var _memberId = _list[i].memberId;
				var _memberIdx = _list[i].memberIdx;
				var _memberDeptName = _list[i].memberDeptName;
				var _memberName = _list[i].memberName;
				var _memberRank = _list[i].memberRank;
				var _memberEmail = _list[i].memberEmail;
				var _memberTel = _list[i].memberTel;
				var _memberCreateDate = koreasoft.modules.string.dateFormatHi(_list[i].memberCreateDate);
				
				var _li = `
					<li class="memberInfoListLi" data-member-idx='${_memberIdx}' data-member-id='${_memberId}'>
							<p>· 회원 아이디: ${_memberId}</p>
							<p>· 부서: ${_memberDeptName}</p>
							<p>· 회원 이름: ${_memberName}</p>
							<p>· 직책: ${_memberRank}</p>
							<p>· 연락처: ${_memberTel}</p>
							<p>· 등록 일자: ${_memberCreateDate}</p>
							<p>· 이메일 주소: ${_memberEmail}</p>
							<span class="close"></span>
					</li>
				`;
				$(_target).append(_li);
			}
			
			var _li = $(".memberInfoListLi");
			$(_li).on("click", function(e) {
				e.preventDefault()  
				$(".memberInfoListLi").removeClass("active");
				$(this).addClass("active");
				
				if(e.target.className != 'close'){
					$("#modifyModalBtn").click();
				}
				
			});		
		}
		
		//	삭제 이벤트
		var _delBtn = $("#memberInfoListUl").find(".close");
		$(_delBtn).on("click", function(e){
			
			if(e.target.className == 'close'){
				var _memberId = $(this).parent('.memberInfoListLi').attr('data-member-id');
				var _memberIdx =  $(this).parent('.memberInfoListLi').attr('data-member-idx')
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
		
		//	페이징 이벤트
		_makePageEvt(1, _memberPageInfo);			
	}
	
	//	회원정보 삭제 저장 버튼
	$("#memberDelProcBtn").on("click", function(){
		//	memberDelObj 
		if(memberDelObj.memberId == null || memberDelObj.memberIdx == 0){
			pushNotify("선택된 회원이 없습니다",2);
			return;
		}
		
		_doAjaxPost(api.myMemberDelProc, memberDelObj, _memberDelProcCallback);
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
		_memberObjInit();
		_getMemberList();
		pushNotify("삭제에 성공하였습니다",1);
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
			
			if (_obj.memberTel != "") {
				if (!koreasoft.modules.regex.isPhone(_obj.memberTel)) {
					pushNotify("모바일 번호에 맞게 작성해주시기 바랍니다", 2);
					return;
				}
			}
			
			_obj.memberTel = _obj.memberTel.replace(/-/gi, '');
			
			_doAjaxPost(api.myMemberAddProc, _obj, _memberAddProcCallback);
		//}
	});
	
	function _memberAddProcCallback(res){
		_getMemberList();
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
	
	//	회원정보 수정 모달 - 버튼
	$("#modifyModalBtn").on("click", function(){
		var _active = $("#memberInfoListUl").find(".active")
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
			
			_doAjaxPost(api.myMemberModProc, _obj, _memberModProcCallback)
		//}
	});
	
	function _memberModProcCallback(res){
		_memberInfoModifyModalClear();
		_getMemberList();
		pushNotify("수정에 성공하였습니다",1);
		
		if(res.data.flag== 1){
			$("#myName").text(res.data.memberName)
		}
		
	}
	
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
		
	$("#addMemberId").on("keyup",function(e){
		//console.log($("#addMemberCheck").val())
		if($("#addMemberCheck").val() != ""){
			$("#addMemberCheck").val("");
		}
	})
	
	//	비밀번호 변경 - 모달 팝업
	$("#pwdModalBtn").on("click", function(){
		var _active = $("#memberInfoListUl").find(".active")
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
				memberIdx : $("#modPwdMemberIdx").val(),
				memberPwd : $("#modPwd1").val()
			}
			
			_doAjaxPost(api.myMemberPwdModProc, _obj, _memberPwdModProcCallback)
		//}
	})
	
	function _memberPwdModProcCallback(res){
		_pwdModalInputClear();
		pushNotify("비밀번호 변경에 성공하였습니다",1)
	}
		
	//	센서 목록 조회 컬백 함수
	function _setSensorList(res){
		var _sensorList = res.data.sensorList;
		var _sensorPageInfo = res.data.page;
		var _sensorUl = $("#sensorListUl");
		//console.log(res)
		$(_sensorUl).find("li").remove();
		
		if(_sensorList.length==0){
			//	조회된 데이터가 없는 경우
			var _li = `<li class='noData' data-sensor-idx='0'>조회된 데이터가 없습니다</li>`	
			$(_sensorUl).append(_li);
		}
		//	센서 인덱스, 센서아이디, 센서 종류, 센서 위치, 승인일자
		for(var i=0; i<_sensorList.length; i++){
			
			var _sensorId = _sensorList[i].sensorId;
			var _sensorIdx = _sensorList[i].sensorIdx;
			var _sensorLocationDescription = _sensorList[i].sensorLocationDescription;
			var _sensorTypeName = _sensorList[i].sensorTypeName;
			var _sensorApprovalDate = koreasoft.modules.string.dateFormatHi(_sensorList[i].sensorApprovalDate);
			var _agreeClassLi = _sensorList[i].sensorApprovalStatus == 'Y'? 'agree' : '';
			
			var _sensorReceiveDate = koreasoft.modules.string.dateFormatHi(_sensorList[i].sensorReceiveDate);
			var _sensorRequestApprovalDate = koreasoft.modules.string.dateFormatHi(_sensorList[i].sensorRequestApprovalDate);
			var _sensorValue = koreasoft.modules.string.getValueType(_sensorList[i].sensorTypeCode, _sensorList[i].sensorValue)
			
			var _li = `
					<li class="${_agreeClassLi}" data-sensor-idx='${_sensorIdx}' data-sensor-id='${_sensorId}'>
						<div class="sensor-index">
							<div class="sensor-name">
								<span></span> ${_sensorLocationDescription}
							</div>
						</div>
						<div class="sensor-desc">
							<p>· 센서 아이디: ${_sensorId}</p>
							<p>· 마지막 수신 시간: ${_sensorReceiveDate}</p>
							<p>· 센서 종류: ${_sensorTypeName}</p>
							<p>· 승인 요청 일자: ${_sensorRequestApprovalDate}</p>
							<p>· 센서 값: ${_sensorValue}</p>
							<p>· 설치 위치: ${_sensorLocationDescription}</p>
							<p>· 승인 일자: ${_sensorApprovalDate}</p>
							<span class="close"></span>
						</div>
					</li>	
			`;
			
			$(_sensorUl).append(_li);
		}
		
		var _sensorLi =  $(_sensorUl).find("li")
		$(_sensorLi).on("click", function(e){
			$("#sensorListUl").find('li').removeClass("active")
			$(this).addClass("active");
			
			if(e.target.className != 'close'){	
				$("#sensorModifyModalBtn").click();
			}
		})
		
		$("#sensorListUl").find(".close").on('click', function(e){
			e.preventDefault()  
			if(e.target.className == 'close'){
				var _idx = $(this).parents("li").attr('data-sensor-idx');
				var _id = $(this).parents("li").attr('data-sensor-id');
				
				var _text = `<span>센서 ${_id}</span>을(를)<br>
				<b>삭제</b>하시겠습니까?`;
				
				$("#delProcText").append(_text);
				$("#sensorDeleteInput").val(_idx);
				$("#sensorDeleteModal").addClass("active");
			}
		})
		
		//	페이징 이벤트
		_makePageEvt(2, _sensorPageInfo);
	}
	
	//	센서 삭제 모달 취소
	$("#sensorDelProcCancelBtn").on("click", function(){
		_sensorDelProcInit();
	});
	
	//	센서 삭제 모달 취소
	$("#sensorDeleteModal").find(".close").on("click", function(){
		_sensorDelProcInit();
	});
		
	function _sensorDelProcInit(){
		$("#sensorDeleteModal").removeClass("active");
		$("#delProcText").html("");
		$("#sensorDeleteInput").val("")
	}
	
	$("#sensorDelProBtn").on("click", function(){
		var _obj = {
			"sensorIdx" : $("#sensorDeleteInput").val()
		}
		//console.log(_obj)
		_doAjaxPost(api.sensorDelProc, _obj, _sensorDelProcCallback)
	});
	
	function _sensorDelProcCallback(res){
		_sensorObjInit();	// 센서 페이징 초기화
		_getSensorList();
		_sensorDelProcInit();
		pushNotify("센서를 삭제하였습니다",1);
		
	}
	
	//	페이징 이벤트
	//	1 : 사용자 목록, 2 : 센서 목록
	function _makePageEvt(type, page){
		//	startPage : 1 , endPage 4 인경우 1,2,3,4
		var startPage = page.startPage;
		var endPage = page.endPage;
		var nowPage = page.nowPage;
		
		type == 1? memberObj.page = page : sensorObj.page = page; 
		
		var _target = type == 1 ? $("#memberPageUl") : $("#sensorPageUl");
		var _liClassName = type == 1? 'memberPageLi' : 'sensorPageLi';
		$(_target).find("li").remove();
		
		var _prevLi = `
			<li class='arrow prev ${_liClassName}'></li>
		`;
		
		$(_target).append(_prevLi);
		
		if(startPage == 1 && endPage == 0){	//	데이터가 없는 경우
			var _li = `
						<li class='active ${_liClassName}'  data-page='${1}'>
							${1}
						</li>
					`;
			$(_target).append(_li);
		}else{
			for(var i = startPage; i <= endPage; i++) {
				var _active = Number(i) == Number(nowPage) ? 'active' : '';
				var _li = `
						<li class='${_active} ${_liClassName}'  data-page='${i}'>
							${i}
						</li>
					`;
				$(_target).append(_li);
			}	
		}
				
		var _nextLi = `
			<li class='arrow next ${_liClassName}'></li>
		`;
		
		$(_target).append(_nextLi);
		
		if(type == 1){
			//	센서 목록 페이징
			$(".memberPageLi").on("click", function(){
				if($(this).hasClass("prev")){
					//	이전 누른 경우
					if(Number(memberObj.page.startPage) != 1){
						memberObj.nowPage = Number(memberObj.page.startPage) - 1;
						_getMemberList();
					}
				}else if($(this).hasClass("next")){
					//	다음 누른 경우
					if(Number(memberObj.page.endPage) != Number(memberObj.page.totalPage)){
						memberObj.nowPage = Number(memberObj.page.endPage) + 1;
						_getMemberList();
					}
				}else{
					memberObj.nowPage = $(this).attr("data-page");
					_getMemberList();
				}
			});
		}else if(type == 2){
			//	센서 상세 정보 페이징
			$(".sensorPageLi").on("click", function(){
				if($(this).hasClass("prev")){
					//	이전 누른 경우
					if(Number(sensorObj.page.startPage) != 1){
						sensorObj.nowPage = Number(sensorObj.page.startPage) - 1;
						_getSensorList();
					}
				}else if($(this).hasClass("next")){
					//	다음 누른 경우
					if(Number(sensorObj.page.endPage) != Number(sensorObj.page.totalPage)){
						sensorObj.nowPage = Number(sensorObj.page.endPage) + 1;
						_getSensorList();
					}
				}else{
					sensorObj.nowPage = $(this).attr("data-page");
					_getSensorList();
				}
				
			})
		}
	}
	
	//	센서 등록 모달 - 초기화
	function _sensorAddInit(){
		$("#sensorSelect").removeClass("active");
		$("#locationSelect").removeClass("active");
		$("#sensorAddModal").removeClass("active");
		$("#sensorAddId").val("");
		$("#sensorAddLocationDescription").val("");	
		
		var _li = $("#sensorAddUl").find("li");
		var _sensorTypeText = $(_li[0]).html();
		var _sensorTypeIdx = $(_li[0]).attr('data-sensor-type-idx');
		$("#sensorAddInput").val(_sensorTypeText);
		$("#sensorAddInput").attr("data-sensor-type-idx", _sensorTypeIdx);
		
		var _location = $("#stationAddUl").find("li");
		var _locationText = $(_location[0]).html();
		var _locationIdx = $(_location[0]).attr('data-station-idx');
		$("#stationAddInput").val(_locationText);
		$("#stationAddInput").attr("data-station-idx", _locationIdx);	
		$("#sensorAddThreshold").val("")
	}
	
	//	센서 등록 모달 - 취소 버튼
	$("#sensorAddCancelBtn").on("click", function(){
		_sensorAddInit();
		
	})
	
	//	센서 등록 모달 - 저장 버튼
	$("#sensorAddBtn").on("click", function(){
		
		var _check = $("#sensorAddCheck").val();
		if(_check == ""){
			pushNotify("센서 아이디 중복체크를 해주시기 바랍니다",2);
			return;
		}
		
		//if(confirm("센서를 등록하시겠습니까?")){
			var _obj = {
				"sensorId" : $("#sensorAddId").val(),
				"sensorTypeIdx" : $("#sensorAddInput").attr("data-sensor-type-idx"),
				"locationIdx" : $("#stationAddInput").attr("data-station-idx"),
				"sensorLocationDescription" : $("#sensorAddLocationDescription").val(),
				"sensorThreshold" : $("#sensorAddThreshold").val()
			}
			
			if(_obj.sensorId == ""){
				pushNotify("센서 아이디를 작성해주시기 바랍니다",2);
				return
			}
			
			if(_obj.sensorLocationDescription ==""){
				pushNotify("센서위치를 작성해주시기 바랍니다",2)
				return;
			}
			
			
			if(_obj.sensorThreshold == ""){
				pushNotify("센서 임계치값을 작성해주시기 바랍니다",2)
				return;
			}
			
			_doAjaxPost(api.sensorAddProc, _obj, _sensorAddProcCallback)
		//}
	});
	
	function _sensorAddProcCallback(res){
		_sensorAddInit();
		_getSensorList();
		pushNotify("센서를 등록하였습니다",1);
	}
	
	$("#sensorModifyModal").find(".close").on("click", function(){
		_sensorModInit();
	})
	
	//	센서 수정 - 취소 버튼
	$("#sensorModProcCancelBtn").on("click", function(){
		_sensorModInit();
	})
	
	//	센서 수정 - 모달 버튼
	$("#sensorModifyModalBtn").on("click", function(e){
		e.preventDefault()  
		if(e.target.className != 'close'){
			var _li = $("#sensorListUl").find("li.active")
			if(_li.length == 0){
				pushNotify("센서를 선택해주시기 바랍니다",2);
				return;
			}
			var _obj = {
				"sensorIdx" : $(_li).attr('data-sensor-idx') 
			}
			_doAjaxGet(api.sensorInfo, _obj, _setSensorModify)
		}
	})
	
	
	
	//	센서 수정 팝업- 컬백
	function _setSensorModify(res){
		var _obj = res.data.sensorInfo
		//console.log(_obj)
		$("#sensorModId").val(_obj.sensorId);
		$("#sensorModLocationDescription").val(_obj.sensorLocationDescription);	
		$("#sensorModValue").val(koreasoft.modules.string.getValueType(_obj.sensorTypeCode, _obj.sensorValue));
		$("#sensorModReceiveDate").val(koreasoft.modules.string.dateFormatHi(_obj.sensorReceiveDate));
		$("#sensorModRequestApprovalDate").val(koreasoft.modules.string.dateFormatHi(_obj.sensorRequestApprovalDate));
		$("#sensorModApprovalDate").val(koreasoft.modules.string.dateFormatHi(_obj.sensorApprovalDate));
		$("#sensorModApprovalStatus").val(koreasoft.modules.string.getApprovalType(_obj.sensorApprovalStatus));
		$("#sensorModIdx").val(_obj.sensorIdx);
		
		$("#stationModifyInput").attr('data-station-idx', _obj.sensorStationIdx);
		$("#stationModifyInput").val(_obj.sensorStationName);
		$("#sensorModifyInput").val(_obj.sensorTypeName);
		$("#sensorModifyInput").attr('data-sensor-type-idx', _obj.sensorTypeIdx);
		$("#sensorModThreshold").val(_obj.sensorThreshold);
		
		//	중복 확인
		$("#sensorModCheck").val("1")
		$("#sensorModCheck").attr('data-old-sensor-id', _obj.sensorId);
		$("#sensorModCheck").attr('data-old-sensor-type-idx', _obj.sensorTypeIdx);
		
		var _sensorApprovalStatus = _obj.sensorApprovalStatus;
		if(_sensorApprovalStatus == 'W' || _sensorApprovalStatus == 'N'){
			$("#sensorApprovalBtn").show();
		}else{
			$("#sensorApprovalBtn").hide();
		}
		$("#sensorApprovalStatus").val(_sensorApprovalStatus)
		
		$("#sensorModifyModal").addClass("active");
		
	}
	
	//	센서승인 모달 취소
	$("#sensorAgreeProcCancelBtn").on("click", function(){
		$("#agreeModal").removeClass("active");
	});
	
	$("#agreeModal").find(".close").on("click", function(){
		$("#agreeModal").removeClass("active");
	});
	
	//	센서승인 요청 모달 팝업
	$("#sensorApprovalBtn").on("click", function(){
		$("#agreeModal").addClass("active");
	});
	
	//	센서 승인 요청 - 신청
	$("#sensorAgreeProcBtn").on("click", function(){
		var _status = $("#sensorApprovalStatus").val();
		if(_status == "W" || _status == "N"){
			var _obj = {
				"sensorIdx" : $("#sensorModIdx").val()
			}
			//_setSensorApprovalCallback()
			_doAjaxPost(api.sensorApproval, _obj, _setSensorApprovalCallback)
		}else{
			pushNotify("승인 요청을 할 수 없습니다", 2);
			return;
		}	
	})
	
	function _setSensorApprovalCallback(res){
		$("#agreeModal").removeClass("active");	//	모달 종료
		_sensorModInit();						//	수정 데이터 초기화
		_getSensorList();						//	센서 조회
		pushNotify("승인 요청을 하였습니다", 1)
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
		
	//	센서 수정 - 모달 초기화
	function _sensorModInit(){
		
		//	센서 수정 관련 데이터
		$("#sensorModifyModal").removeClass("active");
		$("#sensorModId").val("");
		$("#sensorModLocationDescription").val("");	
		$("#sensorModValue").val("");
		$("#sensorModReceiveDate").val("");
		$("#sensorModRequestApprovalDate").val("");
		$("#sensorModApprovalDate").val("");
		$("#sensorModApprovalStatus").val("");
		$("#sensorModIdx").val("");
		$("#sensorApprovalStatus").val("")
		$("#sensorModThreshold").val("")
		
		var _li = $("#sensorModUl").find("li");
		var _sensorTypeText = $(_li[0]).html();
		var _sensorTypeIdx = $(_li[0]).attr('data-sensor-type-idx');
		$("#sensorModifyInput").val(_sensorTypeText);
		$("#sensorModifyInput").attr("data-sensor-type-idx", _sensorTypeIdx);
		
		var _location = $("#stationModUl").find("li");
		var _locationText = $(_location[0]).html();
		var _locationIdx = $(_location[0]).attr('data-station-idx');
		$("#stationModifyInput").val(_locationText);
		$("#stationModifyInput").attr("data-station-idx", _locationIdx);
			
		$("#sensorModifySelect").removeClass("active");
		$("#stationModifySelect").removeClass("active");
		
		$("#sensorModCheck").val("");	//	중복 확인		
		$("#sensorModCheck").attr('data-old-sensor-id', '');
		$("#sensorModCheck").attr('data-old-sensor-type-idx', '');
	}
	
	//	센서 수정 - 모달 저장 이벤트
	$("#sensorModProcBtn").on("click", function(){
		var _check = false;
	
		var _oldSensorId = $("#sensorModCheck").attr('data-old-sensor-id')
		var _oldSensorTypeIdx = $("#sensorModCheck").attr("data-old-sensor-type-idx");
		
		var _sensorModId = $("#sensorModId").val(); 
		var _sensorModTypeIdx = $("#sensorModifyInput").attr("data-sensor-type-idx");
		
		if( (_oldSensorId == _sensorModId) && (_oldSensorTypeIdx == _sensorModTypeIdx) ){
			_check = true;
		}else{
			_check = false;
		}
		
		if(! _check){
			var _modCheck = $("#sensorModCheck").val();
			if( _modCheck == ""){
				pushNotify("센서 아이디 중복체크를 해주시기 바랍니다",2);
				return;
			}	
		}
		
		//if(confirm("수정 하시겠습니까?")){
			var _obj = {
				"sensorId" : _sensorModId, 
				"sensorIdx" : $("#sensorModIdx").val(),
				"locationIdx" : $("#stationModifyInput").attr("data-station-idx"),
				"sensorLocationDescription" : $("#sensorModLocationDescription").val(),
				"sensorTypeIdx" : _sensorModTypeIdx,
				"sensorThreshold" : $("#sensorModThreshold").val()
			}
			
			if(_obj.sensorId == ""){
				pushNotify("센서 아이디를 작성해주시기 바랍니다",2);
				return;
			}
			
			if(_obj.sensorLocationDescription == ""){
				pushNotify("센서위치를 작성해주시기 바랍니다", 2);
				return;
			}
			
			if(_obj.sensorThreshold == ""){
				pushNotify("센서 임계치값을 작성해주시기 바랍니다", 2);
				return;
			}
			
			_doAjaxPost(api.sensorModProc, _obj, _sensorModProcCallback)
			
		//}
	});
	
	function _sensorModProcCallback(res){
		//	조회
		_getSensorList();
		//	초기화
		_sensorModInit();
		pushNotify("수정에 성공하였습니다",1);
	}
	
	//	센서 중복 체크
	$("#sensorAddCheckBtn").on("click", function(){
		var _obj = {
			sensorId : $("#sensorAddId").val(),
			sensorTypeIdx :$("#sensorAddInput").attr('data-sensor-type-idx') 	
		}
		_doAjaxPost(api.sensorIsId, _obj, _setSensorAddCheckCallback)
	})
	
	//	센서 중복 체크 컬백
	function _setSensorAddCheckCallback(res){
		//console.log(res)
		var _result = res.data.isSensor;
		if(_result == true || _result == 'true'){
			$("#sensorAddCheck").val(1);
			pushNotify("사용할 수 있는 센서 아이디 및 타입입니다",1);
		}else{
			$("#sensorAddCheck").val("");
			pushNotify("사용할 수 없는 센서 아이디 및 타입입니다",2);	
		}
	}
	
	$("#sensorAddId").on("keyup",function(e){
		$("#sensorAddCheck").val("");
	})
	
	//	센서 수정 - 중복 체크
	$("#sensorModCheckBtn").on("click", function(){
		var _oldSensorId = $("#sensorModCheck").attr('data-old-sensor-id')
		var _oldSensorTypeIdx = $("#sensorModCheck").attr("data-old-sensor-type-idx");
		
		var _sensorModId = $("#sensorModId").val(); 
		var _sensorModTypeIdx = $("#sensorModifyInput").attr("data-sensor-type-idx");
		
		if(_oldSensorId == _sensorModId && _oldSensorTypeIdx == _sensorModTypeIdx){
			var _res = {};
			_res.data = {};
			_res.data.isSensor = true;
			_sensorModIsIdCallback(_res);
			
		}else{
			var _obj = {
				sensorId : _sensorModId,
				sensorTypeIdx :_sensorModTypeIdx,
			}
			_doAjaxPost(api.sensorIsId, _obj, _sensorModIsIdCallback)
		}
	})
	
	function _sensorModIsIdCallback(res){
		var _result = res.data.isSensor;
		if(_result == true || _result == 'true'){
			$("#sensorModCheck").val(1);
			pushNotify("사용할 수 있는 센서 아이디 및 타입입니다",1);
		}else{
			$("#sensorModCheck").val("");
			pushNotify("사용할 수 없는 센서 아이디 및 타입입니다",2);	
		}
	}
	
	$("#sensorModId").on("keyup",function(e){
		$("#sensorModCheck").val("");
	})
	
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
				console.log(e)
				alert("에러가 발생했습니다");
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

	//	사용자 목록 조회
	function _getMemberList(){
		_doAjaxGet(api.myMemberList, memberObj, _setMemberList);	
	}
	
	//	센서 목록 조회
	function _getSensorList(){
		_doAjaxGet(api.sensorList, sensorObj, _setSensorList);
	}
			
	///////////////	최초 실행
	//	사용자 목록 조회
	_getMemberList();
	//	센서 목록 조회
	_getSensorList();
	
	
}