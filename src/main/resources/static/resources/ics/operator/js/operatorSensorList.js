window.onload = function() {
	
	//	차트 이벤트
	_makeChartEvt();
	function _makeChartEvt(){
		$(".chartLiEvt").on("click", function(){
			$(".chartLiEvt").removeClass("active");
			$(this).addClass("active");
			var _idx = $(this).attr("data-chart-idx")
			_makeChart(_idx);
		})
	}
	
	var api = {
		"sensorList" : "/operator/sensor/listOfCompany",
		"sensorLog" : "/operator/sensor/logOfSensorIdx",
		"sensorLogChart" : "/operator/sensor/logChart",
		"sensorAlarmInfo" : "/operator/sensor/alarmInfo",	//	센서의 알람정보 가져오기
		"sensorAlarmProc" : "/operator/sensor/alarmProc"	//	센서의 알람설정하기
	}
	
	var sensorObj = {
		"nowPage" : 1,
		"listCount" : 4,
		"pageGroup" : 4,
		"page" : null
	};
	
	var sensorLogObj = {
		"nowPage" : 1,
		"listCount" : 9,
		"pageGroup" : 4,
		"sensorIdx" : 0,
		"page" : null
	};

	var sensorChartLog = {
		"sensorIdx" : 0
	};
	
	var sensorChartLogArr = [];	
	
	//	차트 로그 초기화
	function _initSensorChart(sensorIdx){
		sensorChartLogArr = [];
		sensorChartLog.sensorIdx = sensorIdx;
	}
		
	//	센서 로그 초기화
	function _initSensorLog(sensorLogIdx){
		sensorLogObj.nowPage = 1;
		sensorLogObj.sensorIdx = sensorLogIdx;
		sensorLogObj.page = null;
	}
	
	//	센서 목록 조회
	function _getSensorList(){
		_doAjaxGet(api.sensorList, sensorObj, _setSensorList);
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
			var _li = `<li class='noData'>조회된 데이터가 없습니다</li>`	
			$(_sensorUl).append(_li);
		}
		//	센서 인덱스, 센서아이디, 센서 종류, 센서 위치, 승인일자
		for(var i=0; i<_sensorList.length; i++){
			
			var _sensorId = _sensorList[i].sensorId;
			var _sensorIdx = _sensorList[i].sensorIdx;
			var _sensorLocationDescription = _sensorList[i].sensorLocationDescription;
			var _sensorTypeName = _sensorList[i].sensorTypeName;
			var _sensorApprovalDate = koreasoft.modules.string.dateFormatHi(_sensorList[i].sensorApprovalDate);
			var _agreeClassLi = _sensorList[i].sensorApprovalStatus == 'Y'? 'agree' : '';				//
			
			var _li = `
					<li class="${_agreeClassLi}" data-sensor-idx='${_sensorIdx}'>
						<div class="sensor-index">
							<div class="sensor-name">
								<span></span> ${_sensorLocationDescription}
							</div>
							<div class="agree-btn alarmBtn">알람</div>
						</div>
						<div class="sensor-desc" data-sensor-idx='${_sensorIdx}'>
							<p>· 센서 아이디: ${_sensorId}</p>
							<p>· 센서 종류: ${_sensorTypeName}</p>
							<p>· 설치 위치: ${_sensorLocationDescription}</p>
							<p>· 승인 일자: ${_sensorApprovalDate}</p>
						</div>
					</li>	
			`;
			
			$(_sensorUl).append(_li);
		}
		
		//로그 조회
		var _agreeLi = $("#sensorListUl").find("li");
		if(_agreeLi.length !=0){
			var _agreeLiTarget = _agreeLi[0];
			$(_agreeLiTarget).addClass('active');
			var _agreeLiSensorIdx = $(_agreeLiTarget).attr("data-sensor-idx");
			_initSensorLog(_agreeLiSensorIdx);
			_initSensorChart(_agreeLiSensorIdx)
			//	센서 로그 조회
			_getSensorLogOfSensorIdx();
			_getSensorLogChart()
		}
		
		_makeAlarm();
		// 로그 조회 클릭 이벤트
		_makeSensorSearchLogBtn();
		
		//	알람버튼
		_makeAlarm();
		
		//	페이징 이벤트
		_makePageEvt(1, _sensorPageInfo);
	}
	
		//	페이징 이벤트
	function _makePageEvt(type, page){
		//	startPage : 1 , endPage 4 인경우 1,2,3,4
		var startPage = page.startPage;
		var endPage = page.endPage;
		var nowPage = page.nowPage;
		
		type == 1? sensorObj.page = page : sensorLogObj.page = page; 
		
		var _target = type == 1 ? $("#sensorPageUl") : $("#sensorLogPageUl");
		var _liClassName = type == 1? 'sensorPageLi' : 'sensorLogPageLi';
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
	
	// 로그 조회 클릭 이벤트
	function _makeSensorSearchLogBtn(){
		var _li = $("#sensorListUl").find("li");	
		$(_li).on("click", function(e){
			e.preventDefault();
			$("#sensorListUl").find("li").removeClass("active");
			$(this).addClass("active");
			
			if(e.target.className.indexOf('alarmBtn')<0){
				var _sensorIdx = $(this).attr("data-sensor-idx");
				//	센서 로그 페이지 초기화
				_initSensorLog(_sensorIdx)
				_initSensorChart(_sensorIdx)
				//	센서 로그 조회
				_getSensorLogOfSensorIdx();
				_getSensorLogChart();	
			}
		});
	}
	
	//	센서 로그 조회
	function _getSensorLogOfSensorIdx(){
		_doAjaxGet(api.sensorLog, sensorLogObj, _setSensorLogOfSensorIdx);	
	}

	//	센서 로그 조회 컬백
	function _setSensorLogOfSensorIdx(res){
		//console.log(res)
		var _page = res.data.page;
		var _logList = res.data.sensorLogList;
				
		var _target = $("#sensorLogUl");
		$(_target).find("li").remove();
		
		if(_logList.length == 0){
			var _li = `
				<li class='noData'> 조회된 데이터가 없습니다 </li>
			`;
			
			$(_target).append(_li);
		}else{
			for(var i=0; i<_logList.length; i++){
				var _sensorTypeName = _logList[i].sensorTypeName;
				var _sensorLogValue = _logList[i].sensorLogValue;
				var _code = koreasoft.modules.string.getValueType(_logList[i].sensorTypeCode, _sensorLogValue);
				var _sensorLocationDescription = _logList[i].sensorLocationDescription;
				var _sensorLogCreateDate = koreasoft.modules.string.dateFormatHi(_logList[i].sensorLogCreateDate);
				
				var _li = `
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
		var _list =  res.data.sensorLogChart
		for(var i=0; i<_list.length; i++){
			sensorChartLogArr.push(Number(_list[i].sensorLogValue));
		}
		$(".chartLiEvt")[0].click();
	}
	
	var _myChart = null;		
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
	
	//	알람 취소
	$("#alarmCancelBtn").on("click", function(){
		_alarmInputClear();
		$("#alarmModal").removeClass('active');
	})
	//	알람 취소
	$("#alarmModal").find(".close").on("click", function(){
		$("#alarmCancelBtn").click();
	});
	
	//	알람설정 인풋 초기화
	function _alarmInputClear(){
		$("#alarmSensorId").val("");
		$("#alarmSensorType").val("");
		$("#alarmsenSensorLocationDescription").val("");
		$("#alarmSensorValue").val("");
		$("#alarmSensorReceiveDate").val("");
		$("#alarmSensorThreshold").val("");
		$("#alarmSensorIdx").val("");
		$("#alarmCheckBox").prop("checked", false)
	}
	
	//	알람설정
	function _makeAlarm(){
		var _btn = $("#sensorListUl").find("li").find(".alarmBtn"); 
		$(_btn).on("click", function(e){
			if(e.target.className.indexOf("alarmBtn")>0){
				_doAjaxGet(api.sensorAlarmInfo, {"sensorIdx" : $(this).parents("li").attr("data-sensor-idx") }
				, _setAlarmInput)
			}
		})
	};	
	
	function _setAlarmInput(res){
		var _obj =  res.data.alarmInfo;
		$("#alarmSensorId").val(_obj.sensorId);
		$("#alarmSensorType").val(_obj.sensorTypeName);
		$("#alarmsenSensorLocationDescription").val(_obj.sensorLocationDescription);
		$("#alarmSensorValue").val(koreasoft.modules.string.getValueType(_obj.sensorTypeCode, _obj.sensorValue));
		$("#alarmSensorReceiveDate").val(koreasoft.modules.string.dateFormatHi(_obj.sensorReceiveDate));
		$("#alarmSensorThreshold").val(_obj.sensorThreshold);
		$("#alarmSensorIdx").val(_obj.sensorIdx);
		$("#alarmModal").addClass('active');
		var _yn = _obj.alarmYn;
		var _bool = _yn == 'Y'? true : false;
		$("#alarmCheckBox").prop("checked", _bool)
	}
	
	$("#alarmSaveProcBtn").on("click", function(){
		var _obj = {
			"sensorIdx" : $("#alarmSensorIdx").val(),
			"sensorThreshold" : $("#alarmSensorThreshold").val(),
			"sensorAlarmYn" : $("#alarmCheckBox").prop("checked") == true ? 'Y' : 'N'
		}
		
		if(_obj.sensorThreshold == ""){
			pushNotify("임계치를 설정해주시기 바랍니다", 2)
			return;
		}
		
		if(isNaN(_obj.sensorThreshold)){
			pushNotify("임계치는 숫자만 입력해주시기 바랍니다", 2)
			return;
		}
		
		_doAjaxPost(api.sensorAlarmProc, _obj, _setAlarmProcCallback)
	})
	
	function _setAlarmProcCallback(res){
		pushNotify("알람 설정을 저장하였습니다", 1);
		$("#alarmCancelBtn").click();
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
				alert("에러가 발생했습니다");
				console.log(e)
				if (e.status == 400) {
					//alert(e.responseJSON.message);
				} else {
					
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
	
	
	//	최초 실행
	_getSensorList();
}
