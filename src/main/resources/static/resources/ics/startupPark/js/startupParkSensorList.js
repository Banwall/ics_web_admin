window.onload = function() {
	//	차트 이벤트
	_makeChartEvt();
	function _makeChartEvt(){
		$(".chartLiEvt").on("click", function(){
			$(".chartLiEvt").removeClass("active");
			$(this).addClass("active");
			let _idx = $(this).attr("data-chart-idx")
			_makeChart(_idx);
		})
	}
	
	let api = {
		"userCompanyApprovalList" : "/manager/companyApprovalList",
		"sensorList" : "/manager/sensor/listOfCompany",
		"sensorLog" : "/manager/sensor/logOfSensorIdx",
		"sensorLogChart" : "/manager/sensor/logChart",
		"sensorApproval" : "/manager/sensor/approval",
		"sensorTypeList" : "/api/v1/sensor/typeList",
		"sensorTypeAdd" : "/api/v1/sensor/typeAdd"
	}
	
	let sensorObj = {
		"nowPage" : 1,
		"listCount" : 4,
		"pageGroup" : 4,
		"companyIdx" : 0,
		"page" : null
	};
	
	let sensorLogObj = {
		"nowPage" : 1,
		"listCount" : 5,
		"pageGroup" : 4,
		"sensorIdx" : 0,
		"page" : null
	};
	
	let sensorChartLog = {
		"sensorIdx" : 0
	};
	
	let sensorChartLogArr = [];
	
	//	select evt
	const memberSelect = document.querySelector('#memberList');

	select(memberSelect)
	function select(index) {
		const selectInput = index.querySelector('input')
		const options = index.querySelectorAll('ul > li')

		index.addEventListener('click', function() {
			index.classList.toggle('active');
		});
		
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML
				selectInput.setAttribute('value', value);
				let roleSearchType =$(this).attr('data-role-search-type'); 
				$(selectInput).attr('data-role-search-type', roleSearchType);
				let _url = api.userCompanyApprovalList;
				let _data = {
					"roleSearchType":roleSearchType
				}
				_doAjaxGet(_url, _data, _setCompanyApprovalList);
			});
		});
	}
	
	//	회원사 조회 후 컬백
	function _setCompanyApprovalList(res){
		let _list = res.data.companyApprovalList;
		let _target = $("#memberListLi");
		$(_target).find("li").remove();
		for(let i=0; i<_list.length; i++){
			let _company = _list[i];
			let _activeClass = i==0? 'active' : '';	
			let _approvalClass = _company.companyApprovalStatus == 'Y' ? 'agree' : 'dis-agree';
			let _li = `
				<li class="${_activeClass}" data-company-idx="${_company.companyIdx}">
					<div class="memberName"> ${_company.companyName} </div>
					<div class="${_approvalClass}"> ${_company.companyApprovalText} ${_company.companyApprovalDateText}</div>
				</li>
			`;
			$(_target).append(_li);
			if(i==0){
				_init(_company.companyIdx)
			}
		}
		_makeCompanyLiEvt();
		_getSensorList();
	}
	
	//	전체 초기화
	function _init(companyIdx){
		sensorObj.companyIdx = companyIdx;
		sensorObj.nowPage = 1;
		sensorObj.page = null;
		sensorLogObj.nowPage = 1;
		sensorLogObj.sensorIdx = 0;
		sensorLogObj.page = null;
		sensorChartLogArr = [];
		sensorChartLog.sensorIdx = 0;
		
	}
	
	//	센서 로그 초기화
	function _initSensorLog(sensorLogIdx){
		sensorLogObj.nowPage = 1;
		sensorLogObj.sensorIdx = sensorLogIdx;
		sensorLogObj.page = null;
			
	}
	
	//	차트 로그 초기화
	function _initSensorChart(sensorIdx){
		sensorChartLogArr = [];
		sensorChartLog.sensorIdx = sensorIdx;
	}
	
	//	회원사 클릭 이벤트
	function _makeCompanyLiEvt(){
		let _li = $("#memberListLi").find("li");
		$(_li).on("click", function(){
			$("#memberListLi").find("li").removeClass("active");
			$(this).addClass("active");
			_init($(this).attr("data-company-idx"));
			
			_getSensorList();
		})
	}
	
	//	센서 목록 조회
	function _getSensorList(){
		_doAjaxGet(api.sensorList, sensorObj, _setSensorList);
	}
	
	let approvalObj = {
		"sensorIdx" : 0,
		"sensorApprovalStatus" : null,
	}
	
	function _approvalObjInit(){
		approvalObj.sensorIdx = 0;
		approvalObj.sensorApprovalStatus = null;
	}
	
	//	센서 목록 조회 컬백 함수
	function _setSensorList(res){
		let _sensorList = res.data.sensorList;
		let _sensorPageInfo = res.data.page;
		let _sensorUl = $("#sensorListUl");
		//console.log(res)
		$(_sensorUl).find("li").remove();
		
		if(_sensorList.length==0){
			//	조회된 데이터가 없는 경우	
			let _li = `
					<li class="noData" data-sensor-idx='0'>
						조회된 데이터가 없습니다
					</li>	
			`;
			
			$(_sensorUl).append(_li);
		}
		//	센서 인덱스, 센서아이디, 센서 종류, 센서 위치, 승인일자
		for(let i=0; i<_sensorList.length; i++){
			
			let _sensorId = _sensorList[i].sensorId;
			let _sensorIdx = _sensorList[i].sensorIdx;
			let _sensorLocationDescription = _sensorList[i].sensorLocationDescription;
			let _sensorTypeName = _sensorList[i].sensorTypeName;
			let _sensorApprovalDate = koreasoft.modules.string.dateFormatHi(_sensorList[i].sensorApprovalDate);
			let _sensorApprovalText = koreasoft.modules.string.getApprovalType(_sensorList[i].sensorApprovalStatus);
			let _agreeClassLi = _sensorList[i].sensorApprovalStatus == 'Y'? 'agree' : '';
			let _sensorSearchLogBtn =  'sensorSearchLogBtn' ;		// 센서 로그 조회 벝튼
			let _sensorApprovalBtn = 'sensorApprovalBtn'; 
			
			let _li = `
					<li class="${_agreeClassLi}" data-sensor-idx='${_sensorIdx}' data-sensor-approval-status='${_sensorList[i].sensorApprovalStatus}'>
						<div class="sensor-index">
							<div class="sensor-name">
								<span></span> ${_sensorLocationDescription}
							</div>
							<div class="agree-btn ${_sensorApprovalBtn}">${_sensorApprovalText}</div>
						</div>
						<div class="sensor-desc ${_sensorSearchLogBtn}" data-sensor-idx='${_sensorIdx}'>
							<p>· 센서 아이디: ${_sensorId}</p>
							<p>· 센서 종류: ${_sensorTypeName}</p>
							<p>· 설치 위치: ${_sensorLocationDescription}</p>
							<p>· 승인 일자: ${_sensorApprovalDate}</p>
						</div>
					</li>	
			`;
			
			$(_sensorUl).append(_li);
		}
		
		//	로그 조회
		let _agreeLi = $("#sensorListUl").find("li");
		if(_agreeLi.length !=0){
			let _agreeLiTarget = _agreeLi[0];
			$(_agreeLiTarget).addClass('active');
			let _agreeLiSensorIdx = $(_agreeLiTarget).attr("data-sensor-idx");
			_initSensorLog(_agreeLiSensorIdx);
			_initSensorChart(_agreeLiSensorIdx)
			//	센서 로그 조회
			_getSensorLogOfSensorIdx();
			_getSensorLogChart()
		}
		
		//	센서 승인 / 미승인
		let _sensorBtn = $("#sensorListUl").find(".sensorApprovalBtn")
		if(_sensorBtn.length != 0){
			$(_sensorBtn).on("click", function(e){
				e.preventDefault()
				if(e.target.className.indexOf("sensorApprovalBtn") > 0){
					$("#sensorListUl").find("li").removeClass('active');
					$(this).parents("li").addClass('active');
					_approvalObjInit();
					let _dataSensorIdx = $(this).parents("li").attr("data-sensor-idx");
					let _dataSensorStatus = $(this).parents("li").attr("data-sensor-approval-status");
					approvalObj.sensorIdx = _dataSensorIdx;
					//	현재 센서가 Y이면 N으로 미승인 처리
					//	현재 센서가 N이면 Y으로 승인 처리
					approvalObj.sensorApprovalStatus = _dataSensorStatus == 'Y' ? 'N' : 'Y';
					console.log(approvalObj)
					_doPop()
				}
				
			})
		}
		
		//로그 조회 클릭 이벤트
		_makeSensorSearchLogBtn();
		
		//	페이징 이벤트
		_makePageEvt(1, _sensorPageInfo);
	}
	
	
	
	$("#sensorProcCancelBtn").on("click", function(){
		$("#sensorModal").removeClass('active');
		$("#sensorModalTitle").text('');
		$("#sensorModalText").html('');
	})
	
	$("#sensorModal").find(".close").on("click", function(){
		$("#sensorProcCancelBtn").click();	
	})
	
	function _doPop(){
		//console.log(approvalObj);
		let _header = approvalObj.sensorApprovalStatus == 'Y'? '센서 승인' : '센서 미승인';
		let _text =  approvalObj.sensorApprovalStatus == 'Y' ? '<span>센서를</span> <br> <b>승인</b> 처리 하시겠습니까?' : "<span>센서를</span> <br> <b>미승인<b> 처리 하시겠습니까?";
		$("#sensorModalTitle").text(_header);
		$("#sensorModalText").html(_text);
		
		$("#sensorModal").addClass('active')
		
	}
	
	$("#sensorProcBtn").on("click", function(){
		//console.log(approvalObj)
		_doAjaxPost(api.sensorApproval, approvalObj, _approvalCallback);
	})
	
	function _approvalCallback(res){
		let _text = approvalObj.sensorApprovalStatus == 'Y' ? "센서를 승인하였습니다" : "센서를 미승인하였습니다";
		_approvalObjInit();
		_getSensorList();
		pushNotify(_text, 1);
		$("#sensorProcCancelBtn").click();
	}
	
	//	페이징 이벤트
	function _makePageEvt(type, page){
		//	startPage : 1 , endPage 4 인경우 1,2,3,4
		let startPage = page.startPage;
		let endPage = page.endPage;
		let nowPage = page.nowPage;
		
		type == 1? sensorObj.page = page : sensorLogObj.page = page; 
		
		let _target = type == 1 ? $("#sensorPageUl") : $("#sensorLogPageUl");
		let _liClassName = type == 1? 'sensorPageLi' : 'sensorLogPageLi';
		$(_target).find("li").remove();
		
		let _prevLi = `
			<li class='arrow prev ${_liClassName}'></li>
		`;
		
		$(_target).append(_prevLi);
		
		if(startPage == 1 && endPage == 0){	//	데이터가 없는 경우
			let _li = `
						<li class='active ${_liClassName}'  data-page='${1}'>
							${1}
						</li>
					`;
			$(_target).append(_li);
		}else{
			for(let i = startPage; i <= endPage; i++) {
				let _active = Number(i) == Number(nowPage) ? 'active' : '';
				let _li = `
						<li class='${_active} ${_liClassName}'  data-page='${i}'>
							${i}
						</li>
					`;
				$(_target).append(_li);
			}	
		}
				
		let _nextLi = `
			<li class='arrow next ${_liClassName}'></li>
		`;
		
		$(_target).append(_nextLi);
		
		if(type == 1){
			//	센서 목록 페이징
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
			});
		}else if(type == 2){
			//	센서 상세 정보 페이징
			$(".sensorLogPageLi").on("click", function(){
				if($(this).hasClass("prev")){
					//	이전 누른 경우
					if(Number(sensorLogObj.page.startPage) != 1){
						sensorLogObj.nowPage = Number(sensorLogObj.page.startPage) - 1;
						_getSensorLogOfSensorIdx();
					}
				}else if($(this).hasClass("next")){
					//	다음 누른 경우
					if(Number(sensorLogObj.page.endPage) != Number(sensorLogObj.page.totalPage)){
						sensorLogObj.nowPage = Number(sensorLogObj.page.endPage) + 1;
						_getSensorLogOfSensorIdx();
					}
				}else{
					sensorLogObj.nowPage = $(this).attr("data-page");
					_getSensorLogOfSensorIdx();
				}
				
			})
		}
	}

	// 승인된 센서 : 로그 조회 클릭 이벤트
	function _makeSensorSearchLogBtn(){
		let _li = $(".sensorSearchLogBtn");	//	sensorSearchLogBtn
		$(_li).on("click", function(e){
			e.preventDefault()
			$("#sensorListUl").find("li").removeClass("active");
			$(this).parent("li").addClass("active");
			
			let _sensorIdx = $(this).attr("data-sensor-idx");
			//	센서 로그 페이지 초기화
			_initSensorLog(_sensorIdx)
			_initSensorChart(_sensorIdx)
			//	센서 로그 조회
			_getSensorLogOfSensorIdx();
			_getSensorLogChart();
		});
	}
	
	//	센서 로그 조회
	function _getSensorLogOfSensorIdx(){
		_doAjaxGet(api.sensorLog, sensorLogObj, _setSensorLogOfSensorIdx);
		
	}
	
	//	센서 로그 조회 컬백
	function _setSensorLogOfSensorIdx(res){
		//console.log(res)
		let _page = res.data.page;
		let _logList = res.data.sensorLogList;
				
		let _target = $("#sensorLogUl");
		$(_target).find("li").remove();
		
		if(_logList.length == 0){
			let _li = `
				<li> 조회된 데이터가 없습니다 </li>
			`;
			
			$(_target).append(_li);
		}else{
			for(let i=0; i<_logList.length; i++){
				let _sensorTypeName = _logList[i].sensorTypeName;
				let _sensorLogValue = _logList[i].sensorLogValue;
				let _code = koreasoft.modules.string.getValueType(_logList[i].sensorTypeCode, _sensorLogValue);
				let _sensorLocationDescription = _logList[i].sensorLocationDescription;
				let _sensorLogCreateDate = koreasoft.modules.string.dateFormatHi(_logList[i].sensorLogCreateDate);
				
				let _li = `
					<li>
						<div>${_sensorTypeName} : ${_code} </div>
						<div>${_sensorLocationDescription} ㅣ ${_sensorLogCreateDate}</div>
					</li>
				`;		
				
				$(_target).append(_li)	
			}
		};
		
		_makePageEvt(2, _page);	
	}
	
	//	센서 차트 로그 조회
	function _getSensorLogChart(){
		_doAjaxGet(api.sensorLogChart, sensorChartLog, _setSensorLogChart)
	}
	
	//	센서 차트 로그 조회 컬백
	function _setSensorLogChart(res){
		let _list =  res.data.sensorLogChart
		for(let i=0; i<_list.length; i++){
			sensorChartLogArr.push(Number(_list[i].sensorLogValue));
		}
		$(".chartLiEvt")[0].click();
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

	let _myChart = null;		
	//	라벨 생성	
	function makeLabels() {
		let arr = []
		for (i = 1; i <= 30; i++) {
			arr.push(i)
		}
		return arr
	}

	function _makeChart(type){
		
		if(_myChart != null){
			_myChart.destroy();
		}
		
		let sensorData = sensorChartLogArr;
		const chartId = document.getElementById('listChart');
		//온도인경우 컬러1, 습도 = 컬러2, 그 외 컬러3
		const color = ['#64A70B', '#009CDE', '#002664']
		const colorAlpha = ['rgba(100,167,11,0.2)', 'rgba(0,156,222,0.2)', 'rgba(0,38,100,0.2)']
				
		if(type == 0){
			
			//라인 차트
			_myChart = new Chart(chartId, {
				type: 'line',
				data: {
					labels: makeLabels(),
					datasets: [
						{
							data: sensorData,
							backgroundColor: '#fff',
							borderColor: color[0],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						},
					]
				},
				options: {
					scales: {
						x: {
							display: false
						},
						y: {
							suggestedMin: 0,
							suggestedMax: 50
						}
					},
					plugins: {
						tooltip: {
							enabled: false
						},
						legend: {
							display: false,
						}
					}
				}
			})
		}else if(type == 1){
			//	바 차트
			_myChart = new Chart(chartId, {
				type: 'bar',
				data: {
					labels: makeLabels(),
					datasets: [
						{
							data: sensorData,
							backgroundColor: colorAlpha[0],
							borderColor: color[0],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						},
					]
				},
				options: {
					scales: {
						x: {
							display: false
						},
						y: {
							suggestedMin: 0,
							suggestedMax: 50
						}
					},
					plugins: {
						tooltip: {
							enabled: false
						},
						legend: {
							display: false,
						}
					}
				}
			})
		}else if(type == 2){
			//배경이 찬 라인차트
			_myChart = new Chart(chartId, {
				type: 'line',
				data: {
					labels: makeLabels(),
					datasets: [
						{
							data: sensorData,
							backgroundColor: colorAlpha[0],
							borderColor: color[0],
							borderWidth: 2,
							pointRadius: 2,
							tension: 0.1,
							fill: 'start',
						},
					]
				},
				options: {
					scales: {
						x: {
							display: false
						},
						y: {
							suggestedMin: 0,
							suggestedMax: 50
						}
					},
					plugins: {
						tooltip: {
							enabled: false
						},
						legend: {
							display: false,
						}
					}
				}
			})			
		}
		
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
	
	$("#sensorTypeAddBtn").on("click", function(){
		_getSensorTypeList()
	})
	
	$("#sensorAddModal").find(".close").on("click", function(){
		$("#sensorAddModal").removeClass("active");
		_sensorTypeAddInputClear();
	})
	
	$("#sensorTypeCodeAddCancelBtn").on("click", function(){
		$("#sensorAddModal").removeClass("active");
		_sensorTypeAddInputClear();
	})
	
	$("#sensorTypeCodeAddBtn").on("click", function(){
		let _code = $("#addSensorTypeCode").val()
		let _name = $("#addSensorTypeName").val()
		
		if(koreasoft.modules.regex.isKorean(_code)){
			pushNotify("센서타입코드는 영문,숫자,특수문자만 가능합니다 ", 2)
			return;
		}
		
		if(_name == ""){
			pushNotify("센서타입명을 작성해주시기 바랍니다 ", 2)
			return;
		}
		
		let _tag = 
		`
			<span> ${_code}(${_name}) 을</span>
			<br>
			<b>등록</b> 하시겠습니까?
		`
		$("#sensorTypeConfrimText").append(_tag);
		
		$("#sensorTypeConfirmModal").addClass("active")
	});
	
	$("#sensorTypeConfirmCancelBtn").on("click", function(){
		$("#sensorTypeConfrimText").html('')
		$("#sensorTypeConfirmModal").removeClass('active')
	})
	
	$("#sensorTypeConfirmModal").find(".close").on("click", function(){
		$("#sensorTypeConfrimText").html('')
		$("#sensorTypeConfirmModal").removeClass('active')
	})
	
	function _sensorTypeAddInputClear(){
		$("#addSensorTypeCode").val("")
		$("#addSensorTypeName").val("")
	}
	
	
	$("#sensorTypeConfrimSaveBtn").on("click", function(){
		let _obj = {
			'sensorTypeCode': $("#addSensorTypeCode").val(),
			'sensorTypeName' : $("#addSensorTypeName").val()
		};
			
		_doAjaxPost(api.sensorTypeAdd, _obj, _sensorTypeAddCalback);
	})
	
	function _sensorTypeAddCalback(res){
		pushNotify("센서타입을 등록하였습니다", 1)
		//	등록 인풋 초기화
		_sensorTypeAddInputClear();
		//	확인 모달 종료
		$("#sensorTypeConfirmCancelBtn").click();
		//	센서 타입 목록 가져오기
		_getSensorTypeList()
	}
	
	//	센서타입 목록 가져오기
	function _getSensorTypeList(){
		_doAjaxGet(api.sensorTypeList, null, _setSensorTypeList)
	}
	
	
	function _setSensorTypeList(res){
		let _list = res.data.sensorTypeList;
		let _target = $("#sensorTypeBody");
		$(_target).find('tr').remove();
		if(_list.length == 0){
			let _tr = `
				<tr class=noData> 
					<td colspan=2> 조회된 데이터가 없습니다 </td>
				</tr>
			`
		}else{
			
			for(let i=0; i<_list.length; i++){
				let _sensorTypeIdx = _list[i].sensorTypeIdx;
				let _sensorTypeName = _list[i].sensorTypeName;
				let _sensorTypeCode = _list[i].sensorTypeCode;
				let _idx = (i+1)
				let _tr = `
					<tr data-sensor-type-idx=${_sensorTypeIdx}>
						<td>${_idx}</td>
						<td>${_sensorTypeCode}</td>
						<td>${_sensorTypeName}</td>
					</tr>
				`
				$(_target).append(_tr);
			}
		}
		$("#sensorAddModal").addClass("active");
	}
		
	//	페이지 로딩 완료 후
	//	회원사 목록 li evt 시작
	_makeCompanyLiEvt();
	//	데이터 목록 조회 시작
	let _li = $("#memberListLi").children("li");
	if(_li.length != 0){
		_li[0].click();
	}
	
}	