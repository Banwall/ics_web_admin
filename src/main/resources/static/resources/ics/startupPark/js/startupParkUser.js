window.onload = function() {
	
	let api = {
		"userCompanyApprovalList" : "/startupPark/companyApprovalList",
		"companyInfo" : "/startupPark/companyInfo",
		"memberList" : "/startupPark/memberList",
		"joinProc" : "/api/v1/company/joinProc",		//	회원 가입
		"memberIsId" : "/api/v1/member/isId",			//	아이디 중복 체크
		"companyApproval" : "/api/v1/company/approval",	//	회사 승인/미슨인
		"companyDelProc" : "/api/v1/company/delProc",	//	회원사 삭제
		"companyModProc" :  "/api/v1/company/modProc",	//	회원사 정보 수정
		"memberDelProc" : "/api/v1/member/delProc",		//	회원 삭제
		"memberModProc" : "/api/v1/member/modProc",		//	회원 수정
		"memberAddProc" : "/api/v1/member/addProc",		//	회원 등록
		"memberPwdModProc" : "/api/v1/member/pwdModProc",//	회원 비밀번호 수정
		"memberInfo" : "/api/v1/member/info",				//	회원 정보 조회
	}
	
	let company = {
		"companyIdx" : 0,
		"nowPage" : 1,
		"listCount" : 10,
		"companyName" : null,
		"pageGroup" : 5,
		"page" : null,
		
	}
	
	function _companyInit(){
		company.companyIdx = 0;
		company.nowPage = 1;
		company.page = null;
		company.companyName = null;
	}
	
	let companyRole = {
		"ROLE_O" : {type :2 , text : '실증수요기관'},
		"ROLE_M" : {type :3 , text : '실증기업'}
	}
	
	//select 함수
	const memberSelect = document.querySelector('#memberList')
	const newMemberSelect = document.querySelector('#newMemberList')
	const memberModSelect = document.querySelector("#memberModSelect");
			 

	select(memberModSelect)
	select(newMemberSelect)
	select(memberSelect)
	
	function select(index) {
		const selectInput = index.querySelector('input')
		const options = index.querySelectorAll('ul > li')

		index.addEventListener('click', function() {
			//console.log(index)
			if($(index).attr("id") == 'memberModSelect'){
				if(_evt == 0){
					//console.log(_evt)
					return;
				}
			}
			index.classList.toggle('active');
		})
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML
				//selectInput.value('value', value)
				$(selectInput).val(value);
				
				if(selectInput.getAttribute("id")=="roleInput"){
					let roleSearchType =$(this).attr('data-role-search-type'); 
					$(selectInput).attr('data-role-search-type', roleSearchType);
					let _url = api.userCompanyApprovalList;
					let _data = {
						"roleSearchType":roleSearchType
					}
					_doAjaxGet(_url, _data, _setCompanyApprovalList);
				}else if(selectInput.getAttribute("id")=="newMemberRoleInput"){
					let _role = $(this).attr('data-role')
					$(selectInput).attr('data-role', _role);
				}else if(selectInput.getAttribute("id")=="memberInfoInput"){
					let roleSearchType =$(this).attr('data-role-search-type'); 
					$(selectInput).attr('data-role-search-type', roleSearchType);
				}
			})
		})
	}
	
	//	회원사 조회 후 컬백
	function _setCompanyApprovalList(res){
		let _list = res.data.companyApprovalList;
		let _target = $("#memberListLi");
		$(_target).find("li").remove();
		
		_companyInit();

		for(let i=0; i<_list.length; i++){
			let _company = _list[i];
			//console.log(_company)
			let _activeClass = i==0? 'active' : '';	
			let _approvalClass = _company.companyApprovalStatus == 'Y' ? 'agree' : 'dis-agree';
			let _li = `
				<li class="${_activeClass}" data-company-idx="${_company.companyIdx}" data-company-role='${_company.companyRole}' data-company-status='${_company.companyApprovalStatus}' data-company-name='${_company.companyName}'>
					<div class="memberName"> ${_company.companyName} </div>
					<div class="${_approvalClass}"> ${_company.companyApprovalText} ${_company.companyApprovalDateText}</div>
				</li>
			`;
			$(_target).append(_li);
			if(i==0){
				company.companyIdx = _company.companyIdx;
				company.companyName = _company.companyName;
			}
		}
		_makeCompanyLiEvt();

		let _li = $("#memberListLi").find("li");
		_li[0].click();
	}
	
	//	회원사 클릭 이벤트
	function _makeCompanyLiEvt(){
		let _li = $("#memberListLi").find("li");
		$(_li).on("click", function(){
			_companyInit();
			$("#memberListLi").find("li").removeClass("active");
			$(this).addClass("active");
			//console.log($(this).attr("data-company-idx"))
			company.companyIdx = $(this).attr("data-company-idx");
			company.companyName = $(this).attr("data-company-name")
			_getCompanyInfo();
			_getMemberList();	
			
			let _stat = $(this).attr('data-company-status')
			if(_stat == 'Y'){
				$("#companyAgreeBtn").hide();
				$("#companyDisAgreelBtn").show();
			}else{
				$("#companyAgreeBtn").show();
				$("#companyDisAgreelBtn").hide();
			}
			
			_companyInfoMod(true)
		})
	}
	
	//	회원사의 회원 정보
	function _getCompanyInfo(){
		
		_doAjaxGet(api.companyInfo, {
			"companyIdx" : company.companyIdx
		}, _setCompanyInfo);
	}
	
	function _companyInfoClear(){
		$("#memberInfoInput").text("전체보기");
		$("#memberInfoInput").attr("data-role-search-type", "1");		
		$("#infoCompanyName").val("-");
		$("#infoCompanyPresidentName").val("-")
		$("#infoCompanyEmail").val("-")
		$("#infoCompanyRegistNumber").val("-")
		$("#infoCompanyPhone").val("-")
		$("#infoCompanyAddress").val("-")
		$("#infoCompanyUrl").val("-")
		$("#infoCompanyCreateDate").val("-")
		$("#infoCompanyApprovalDate").val("-")
		$("#infoSensorCount").val("-")
	}
	
	function _setCompanyInfo(res){
		_companyInfoClear();
		let _obj = res.data.companyInfo;
		
		$("#infoCompanyName").val(_obj.companyName);
		$("#infoCompanyPresidentName").val(_obj.companyPresidentName);
		$("#infoCompanyEmail").val(_obj.companyEmail);
		$("#infoCompanyRegistNumber").val(_obj.companyRegistNumber);
		$("#infoCompanyPhone").val(_obj.companyPhone);
		$("#infoCompanyAddress").val(_obj.companyAddress);
		$("#infoCompanyUrl").val(_obj.companyUrl);			
		$("#infoCompanyCreateDate").val(koreasoft.modules.string.dateFormatHi(_obj.companyCreateDate));
		$("#infoCompanyApprovalDate").val(koreasoft.modules.string.dateFormatHi(_obj.companyApprovalDate));	
		$("#infoSensorCount").val(_obj.sensorCount);
		
		let _data = companyRole[_obj.companyRole];
		$("#memberInfoInput").val(_data.text);
		$("#memberInfoInput").attr("data-role-search-type", _data.type);
		
	}
	
	function _getMemberList(){
		_doAjaxGet(api.memberList, company, _setMemberList);
	}
	
	function _setMemberList(res){
		let _target = $("#memberInfoListUl");
		$(_target).find("li").remove();
		
		let _list = res.data.memberList;
				
		if(_list.length == 0){
			let _li = `
					<li class='memberInfoListLi noData'>
						조회된 데이터가 없습니다	
					</li>`;
			$(_target).append(_li);					
			
		}else{
			for(let i=0; i < _list.length; i++){
				let _memberId = _list[i].memberId;
				let _memberIdx = _list[i].memberIdx;
				let _memberDeptName = _list[i].memberDeptName;
				let _memberName = _list[i].memberName;
				let _memberRank = _list[i].memberRank;
				let _memberEmail = _list[i].memberEmail;
				let _memberTel = _list[i].memberTel;
				let _memberCreateDate = koreasoft.modules.string.dateFormatHi(_list[i].memberCreateDate);
				
				let _li = `
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
			
			let _li = $(".memberInfoListLi");
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
		let _delBtn = $("#memberInfoListUl").find(".close");
		$(_delBtn).on("click", function(e){
			
			if(e.target.className == 'close'){
				let _memberId = $(this).parent('.memberInfoListLi').attr('data-member-id');
				let _memberIdx =  $(this).parent('.memberInfoListLi').attr('data-member-idx')
				memberDelObj.memberId = _memberId;
				memberDelObj.memberIdx = _memberIdx;
				memberDelObj.companyIdx = company.companyIdx;
								
				$("#memberDelSpan").html("")
				let _text = `<span>${_memberId}</span>을(를)<br>
						<b>삭제</b>하시겠습니까?`;	//${_memberId}님을 삭제 하시겠습니까?; 
				$("#memberDelSpan").append(_text);
				
				$("#deleteModal").addClass("active");
			}
		});
		
		/*
		let _li = $(".memberInfoListLi");
		$(_li).on("click", function(){
			$(".memberInfoListLi").removeClass("active");
			$(this).addClass("active");
		});
		*/
	}
	
	let memberDelObj = {
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
		_getMemberList();
		pushNotify("삭제에 성공하였습니다",1);
	}
	
	//	회원정보 수정 모달 - 버튼
	$("#modifyModalBtn").on("click", function(){
		let _active = $("#memberInfoListUl").find(".active")
		if(_active.length == 0){
			pushNotify("회원을 선택해주시기 바랍니다",2);
			return;
		}else{
			let _obj = {
				"memberIdx": $(_active).attr('data-member-idx')
			}
			_doAjaxGet(api.memberInfo, _obj, _setModifyModal);
		}
	})


	//	회원정보 수정 모달 - 컬백
	function _setModifyModal(res){
		let _data = res.data.info;
		
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
			let _obj = {
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
			
			_doAjaxPost(api.memberModProc, _obj, _memberModProcCallback)
		//}
	});
	
	function _memberModProcCallback(res){
		_memberInfoModifyModalClear();
		_getMemberList();
		pushNotify("수정에 성공하였습니다",1);
		
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
				$("#loading").show();				

			},
			success: function(res) {
				callBack(res);
			},
			error: function(e) {
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
						let _msg = e.responseJSON.message;
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
		let myNotify = new Notify({
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
			
	//	신규등록 - 버튼 누른 경우
	$("#addCompanyModalBtn").on("click", function(){
		//	관리사가 직접 등록한 회원사/운영사는 관리사가 따로 승인이 필요없다.
		_addCompanyInputInit();
		$("#addCompanyModal").addClass("active")
	})
	
	function _addCompanyInputInit(){
				
		$("#addCompanyMemberId").val("");
		$("#addCompanyMemberError").hide();
		$("#addCompanyMemberIdCheck").val("")
		$("#newMemberRoleInput").val("전체보기")
		$("#newMemberRoleInput").attr('data-role', '')
		$("#newMemberList").removeClass('active')
		$("#addCompanyName").val("");
		$("#addCompanyPwd1").val("");
		$("#addCompanyPwd2").val("");
		$("#addCompanyPwdCheck").text("");
		$("#addCompanyPresidentName").val("");
		$("#addCompanyEmail").val("");
		$("#addCompanyRegistNumber").val("");
		$("#addCompanyPhone").val("");
		$("#addCompanyAddress").val("");
		$("#addCompanyUrl").val("");
	}
	
	//	신규등록 - 취소
	$("#addCompanyCancelBtn").on("click", function(){
		_addCompanyInputInit();
		$("#addCompanyModal").removeClass("active")
	})
	
	//	신규등록 - 취소
	$("#addCompanyModal").find('.close').on("click", function(){
		$("#addCompanyCancelBtn").click();
	})
	
	//	신규등록 - 중복 버튼을 누른경우
	$("#addCompanyMemberIsId").on("click", function(){
		let _obj = {
				memberId : $("#addCompanyMemberId").val(),
			}
		
		if(!koreasoft.modules.regex.isId(_obj.memberId)){
			pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
			return;
		}
		_doAjaxPost(api.memberIsId, _obj, _addCompanyMemberIsIdCallback);
	})
	
	$("#addCompanyPwd1").on("keyup", function(){
		_pwdCheck(1)
	})		
	
	$("#addCompanyPwd2").on("keyup", function(){
		_pwdCheck(1)
	});
	
	function _pwdCheck(type){
		let _p1 = type == 1 ? $("#addCompanyPwd1").val() : $("#modPwd1").val();
		let _p2 = type == 1 ? $("#addCompanyPwd2").val() : $("#modPwd2").val();
		let _target = type == 1 ? $("#addCompanyPwdCheck") : $("#modPwdError");
		let _text = null;
		//console.log(type)
		if(_p1 != _p2){
			_text = "비밀번호가 일치하지 않습니다.";
		}else{
			
			if(_p1 == "" && _p2 == ""){
				_text = "비밀번호를 작성해주시기 바랍니다";
			}else{
				_text = "비밀번호가 일치합니다";	
			}
			
		}
		
		$(_target).show();
		$(_target).text(_text);
		
	}	
	
	$("#addCompanyMemberId").on("keyup",function(e){
		//console.log($("#addMemberCheck").val())
		if($("#addCompanyMemberIdCheck").val() != ""){
			$("#addCompanyMemberIdCheck").val("");
		}
	})	
	
	function _addCompanyMemberIsIdCallback(res){
		let _result = res.data.isId;
		let _text = null;
		let _target = $("#addCompanyMemberError");
		$(_target).text("");
		if(_result == true || _result == 'true'){
			_text = '사용 할 수 있는 아이디입니다';
			$("#addCompanyMemberIdCheck").val("1");
			
		}else{
			_text = '이미 사용중인 아이디입니다';
		}
		$(_target).text(_text);
		$(_target).show();
		setTimeout(function(){
			$(_target).hide();	
		},10000)
	}
	
	
	$("#addCompanyProcBtn").on("click", function(){
		_popModal(1)
	});

	let _modalType = 0;
	
	let _popCompanyName = null;
	function _popModal(type, v){
		_modalType = type;
		
		let _headerTarget = $("#agreeHeader");
		let _textTarget = $("#agreeText");
		let _title = null;
		let _text = null;
		
		if(_modalType == 1){
			//	회원 등록
			_title = '실증수요기관 / 실증기업 신규 등록';
			_text = '신규 등록하시겠습니까?';
		}else if(_modalType == 2){
			//	수요기관 / 기업 승인
			_popCompanyName = company.companyName;
			_title = `실증수요기관 / 실증기업 승인`
			_text = `${_popCompanyName}을 승인하시겠습니까?`;
		}else if(_modalType == 3){
			//	수요기관 / 기업 미승인
			_popCompanyName = company.companyName;
			_title = `실증수요기관 / 실증기업 미승인`
			_text = `${_popCompanyName}을 미승인하시겠습니까?`;			
		}else if(_modalType == 4){
			_popCompanyName = company.companyName;
			_title = `실증수요기관 / 실증기업 삭제`
			_text = `${_popCompanyName}을 삭제하시겠습니까?`;
		}else if(_modalType == 5){
			_popCompanyName = company.companyName;
			_title = `실증수요기관 / 실증기업 수정`
			_text = `${_popCompanyName}을 수정하시겠습니까?`;
		}else if( _modalType == 6){
			//	회원사의 회원 신규등록
			_popCompanyName = company.companyName;
			_title = `회원 신규등록`
			_text = `${_popCompanyName}의 회원을 신규 등록 하시겠습니까?`;
		}
		
		$(_headerTarget).text(_title);
		$(_textTarget).text(_text)
		$("#agreeModal").addClass("active");
	}


	//	확인 모달 - 확인 버튼
	$("#agreeModalBtn").on("click", function(){
		if(_modalType == 1){
			let _obj = {
				"companyName" : $("#addCompanyName").val(),
				"companyPresidentName" : $("#addCompanyPresidentName").val(),
				"companyEmail" : $("#addCompanyEmail").val(),
				"companyRegistNumber" : $("#addCompanyRegistNumber").val(),
				"companyPhone" : $("#addCompanyPhone").val(), 
				"companyAddress" : $("#addCompanyAddress").val(),
				"companyUrl" : $("#addCompanyUrl").val(),
				"memberId" : $("#addCompanyMemberId").val(),
				"memberPwd": $("#addCompanyPwd1").val(),
				"companyRoleIdx" : $("#newMemberRoleInput").attr("data-role")
			}
			
			if(_obj.companyRole == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원사 종류를 선택해주시기 바랍니다",2);
				return;
			}
			
			if(!koreasoft.modules.regex.isId(_obj.memberId)){
				$("#agreeCancelBtn").click();
				pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
				return;
			}
			
			if($("#addCompanyMemberIdCheck").val() == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원 아이디 중복 체크를 해주시기 바랍니다",2);
				return;
			}
			
			if(_obj.companyName == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원사 이름을 작성해주시기 바랍니다",2);
				return;
			}		
			
			if(!koreasoft.modules.regex.isEmail(_obj.companyEmail)){
				$("#agreeCancelBtn").click();
				pushNotify("이메일 주소 형식에 작성해주시기 바랍니다", 2);
				return;
			}
			
			//	회원 등록
			let _p1 = $("#addCompanyPwd1").val();
			let _p2 = $("#addCompanyPwd2").val();
			
			let _text = null;
			
			if(_p1 != _p2){
				$("#agreeCancelBtn").click();
				_text = "비밀번호가 일치하지 않습니다.";
				$("#addCompanyPwdCheck").show();
				$("#addCompanyPwdCheck").text(_text);
				return;
			}else{
				$("#agreeCancelBtn").click();
				if(_p1 == "" && _p2 == ""){
					_text = "비밀번호를 작성해주시기 바랍니다";
					$("#addCompanyPwdCheck").show();
					$("#addCompanyPwdCheck").text(_text);
					return;
				}else{
					_text = "비밀번호가 일치합니다";
					$("#addCompanyPwdCheck").show();
					$("#addCompanyPwdCheck").text(_text);
				}
			}
			
			_doAjaxPost(api.joinProc, _obj, _setJoinProcCallback)
		}else if(_modalType == 2){
			//	승인
			let _li = $("#memberListLi").find("li.active");
			let _obj = {
				"companyIdx" : $(_li).attr('data-company-idx'),
				"companyApprovalStatus" : 'Y',
			}
			
			_doAjaxPost(api.companyApproval, _obj, _setCompanyAccept)
			//console.log(_obj)
		}else if(_modalType == 3){
			//	미 승인
			let _li = $("#memberListLi").find("li.active");
			let _obj = {
				"companyIdx" : $(_li).attr('data-company-idx'),
				"companyApprovalStatus" : 'N',
			}
			
			_doAjaxPost(api.companyApproval, _obj, _setCompanyReject)
		}else if(_modalType == 4){
			//	회원사 삭제
			let _li = $("#memberListLi").find("li.active");
			let _obj = {
				"companyIdx" : $(_li).attr('data-company-idx'),
			}
			_doAjaxPost(api.companyDelProc, _obj, _setCompanyDelProcCallback)
		}else if(_modalType == 5){
			let _roleType = $("#memberInfoInput").attr('data-role-search-type');
			
			let _role = Number(_roleType) == 2 ? 'ROLE_O' : 'ROLE_M';
			
			//	회원사 수정
			let _obj = {
				"companyIdx" : company.companyIdx,
				"companyName" : $("#infoCompanyName").val(),
				"companyPresidentName" : $("#infoCompanyPresidentName").val(),
				"companyEmail" : $("#infoCompanyEmail").val(),
				"companyRegistNumber" : $("#infoCompanyRegistNumber").val(),
				"companyPhone": $("#infoCompanyPhone").val(),
				"companyAddress" : $("#infoCompanyAddress").val(),
				"companyUrl" : $("#infoCompanyUrl").val(),
				"companyRole" : _role 	
			}
			
			if(_obj.companyName == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원사 이름을 작성해주시기 바랍니다",2);
				return;
			}		
			
			if(!koreasoft.modules.regex.isEmail(_obj.companyEmail)){
				$("#agreeCancelBtn").click();
				pushNotify("이메일 주소 형식에 작성해주시기 바랍니다", 2);
				return;
			}
			
			_doAjaxPost(api.companyModProc, _obj, _setCompanyModProcCallback);
		}else if(_modalType == 6){
			//	회원 신규등록
			let _obj = {
				memberId : $("#addMemberId").val(),
				memberPwd : $("#addMemberPwd").val(),
				memberName : $("#addMemberName").val(),
				memberTel : $("#addMemberTel").val(),
				memberEmail : $("#addMemberEmail").val(),
				memberDeptName : $("#addMemberDeptName").val(),
				memberRank : $("#addMemberRank").val(),
				companyIdx : company.companyIdx,
			}
			
			//	회원아이디, 비밀번호, 회원이름
			if(!koreasoft.modules.regex.isId(_obj.memberId)){
				$("#agreeCancelBtn").click();
				pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
				return;
			}
			
			if($("#addMemberCheck").val() == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원 아이디 중복 체크를 해주시기 바랍니다",2);
				return;
			}
			
			if(_obj.memberPwd == ""){
				$("#agreeCancelBtn").click();
				pushNotify("비밀번호를 입력해주시기 바랍니다",2);
				return;
			}
			
			if(_obj.memberName == ""){
				$("#agreeCancelBtn").click();
				pushNotify("회원 이름을 작성해주시기 바랍니다",2);
				return;				
			}
			_doAjaxPost(api.memberAddProc, _obj, _memberAddProcCallback);
			
		}
	})
	
	//	회원 등록 컬백
	function _memberAddProcCallback(){
		pushNotify(_popCompanyName +"의 회원을 등록하였습니다 ", 1);
		$("#agreeCancelBtn").click();
		$("#addMemberCancelBtn").click();
		_getMemberList();
		
	}
	
	//	회원사 수정 컬백
	function _setCompanyModProcCallback(res){
		pushNotify(_popCompanyName +" 수정 하였습니다", 1);
		_companyInfoMod(true)
		$("#agreeCancelBtn").click();
		_getCompanyApprovalList();
	}
	
	//	회원사 삭제 컬백
	function _setCompanyDelProcCallback(res){
		pushNotify(_popCompanyName +" 삭제 하였습니다", 1)
		$("#agreeCancelBtn").click();
		_getCompanyApprovalList();
	}
	
	//	회원사 승인 컬백
	function _setCompanyAccept(){
		pushNotify(_popCompanyName +" 승인 하였습니다", 1)
		$("#agreeCancelBtn").click();
		_getCompanyApprovalList();
		
	}
	
	//	회원사 미승인 컬백
	function _setCompanyReject(){
		pushNotify(_popCompanyName +" 미승인 하였습니다", 1)
		$("#agreeCancelBtn").click();
		_getCompanyApprovalList();
	}
	
	function _getCompanyApprovalList(){
		_doAjaxGet(api.userCompanyApprovalList, {
			"roleSearchType" : $("#roleInput").attr('data-role-search-type')
		}, _setCompanyApprovalList);	
	}
	
	//	회원사 등록 컬백
	function _setJoinProcCallback(res){
		pushNotify("저장에 성공하였습니다", 1)
		$("#agreeCancelBtn").click();
		$("#addCompanyCancelBtn").click();
		_getCompanyApprovalList();
	}
	
	//	확인 모달 - 취소 버튼
	$("#agreeCancelBtn").on("click", function(){
		_modalType = 0;
		_popCompanyName = null;
		$("#agreeModal").removeClass('active');
	})
	
	//	확인 모달 - 취소 버튼
	$("#agreeModal").find('.close').on("click", function(){
		$("#agreeCancelBtn").click();
	})
	
	//	회원사 승인 버튼
	$("#companyAgreeBtn").on("click", function(){
		let _li = $("#memberListLi").find("li.active");
		if(_li.length == 0){
			pushNotify("실증수요기관 / 실증기업을 선택해주시기 바랍니다", 2);
			return;
		}else{
			//	회원사 승인
			_popModal(2)
		}
	});
	
	//	회원사 미승인 버튼
	$("#companyDisAgreelBtn").on("click", function(){
		let _li = $("#memberListLi").find("li.active");
		if(_li.length == 0){
			pushNotify("실증수요기관 / 실증기업을 선택해주시기 바랍니다", 2);
			return;
		}else{
			//	회원사 미승인
			_popModal(3)
		}
	})
	
	//	회원사 삭제 버튼
	$("#companyDelBtn").on("click", function(){
		let _li = $("#memberListLi").find("li.active");
		if(_li.length == 0){
			pushNotify("실증수요기관 / 실증기업을 선택해주시기 바랍니다", 2);
			return;
		}else{
			//	회원사 삭제
			_popModal(4)
		}
	})
	
	//	회원사 수정
	$("#companyModBtn").on("click", function(){
		_companyInfoMod(false)
	});
	
	$("#companyCancelBtn").on("click", function(){
		_companyInfoMod(true)
		_getCompanyInfo();
	})	
	
	let _evt = 0;
	function _companyInfoMod(bool){
		
		if(bool== false){
			_evt = 1;
			$("#companyModBtn").hide();
			$("#companySaveProcBtn").show();
			$("#memberModSelect").removeClass('disabled')
			$("#companyCancelBtn").show();	
		}else{
			_evt = 0;
			$("#companyModBtn").show();
			$("#memberModSelect").addClass('disabled')
			$("#companyCancelBtn").hide();
			$("#companySaveProcBtn").hide();
		}
		
		$("#memberInfoInput").attr("readonly", bool)
		$("#infoCompanyName").attr("readonly", bool)
		$("#infoCompanyPresidentName").attr("readonly", bool)
		$("#infoCompanyEmail").attr("readonly", bool)
		$("#infoCompanyRegistNumber").attr("readonly", bool)
		$("#infoCompanyPhone").attr("readonly", bool)
		$("#infoCompanyAddress").attr("readonly", bool)
		$("#infoCompanyUrl").attr("readonly", bool)
	}
	
	//	수정 저장
	$("#companySaveProcBtn").on("click", function(){
		_popModal(5);
	});
	
	//	회원 정보 - 신규등록 인풋 - 초기화
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
	
	//	회원 정보 등록 버튼
	$("#addMemberModalBtn").on("click", function(){
		let _li = $("#memberListLi").find("li.active");
		if(_li.length == 0){
			pushNotify("실증수요기관 / 실증기업을 선택해주시기 바랍니다", 2);
			return;
		}
		$("#addMemberModal").addClass('active');
	});
	
	//	신규등록 모달 - 창 닫기
	$("#addMemberCancelBtn").on("click", function(){
		_addMemberInit();
		$("#addMemberModal").removeClass("active");
	})
	
	//	신규등록 모달 - 창 닫기
	$("#addMemberModal").find(".close").on("click",function(){
		_addMemberInit();
		$("#addMemberModal").removeClass("active");
	});
	
	//	신규등록 모달 - 저장
	$("#addMemberProcBtn").on("click", function(){
		_popModal(6)
	});
	
	//	신규등록 모달 - 회원 아이디 중복 체크
	$("#memberIdCheckBtn").on("click", function(){
		let _obj = {
				memberId : $("#addMemberId").val(),
			}
		
		if(!koreasoft.modules.regex.isId(_obj.memberId)){
			pushNotify("회원 아이디는 대소문자 구분없이 영문, 숫자입니다",2);
			return;
		}
		//console.log(_obj)
		_doAjaxPost(api.memberIsId, _obj, _isMemberIdCallback);
	})
	
	function _isMemberIdCallback(res){
		let _result = res.data.isId;
		let _text = null;
		let _target = $("#addMemberIdError");
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
	
	//	비밀번호 변경 - 모달 팝업
	$("#pwdModalBtn").on("click", function(){
		let _active = $("#memberInfoListUl").find(".active")
		if(_active.length == 0){
			pushNotify("회원을 선택해주시기 바랍니다",2);
			return;
		}else{
			let _memberIdx = $(_active).attr('data-member-idx');
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
	
	$("#modPwd1").on("keyup", function(){
		_pwdCheck(2);
	});
	
	$("#modPwd2").on("keyup", function(){
		_pwdCheck(2);
	});
	
	function _pwdModalInputClear(){
		$("#pwdModal").removeClass("active");
		$("#modPwd1").val("");
		$("#modPwd2").val("");
		$("#modPwdMemberIdx").val("")
		$("#modPwdError").text("");
		
	}
	
	//	비밀번호 저장
	$("#modPwdProcBtn").on("click", function(){
		//if(confirm("저장하시겠습니까")){
			let _p1 = $("#modPwd1").val();
			let _p2 = $("#modPwd2").val();
			
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
			
			let _obj = {
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
	
	//	최초 실행
	_makeCompanyLiEvt();
	let _li = $("#memberListLi").find("li");
	if(_li.length != 0){
		_li[0].click();
	}
	
	
	
}

