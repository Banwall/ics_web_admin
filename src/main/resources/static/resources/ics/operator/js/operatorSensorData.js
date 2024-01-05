window.onload = function() {
	
	//	데이트피커
	flatpickr(document.getElementById('startDate'), {
		maxDate : "today"
		, locale: "ko"
		, dateFormat: "Y-m-d"
	});
	flatpickr(document.getElementById('endDate'), {
		maxDate : "today"
		, locale: "ko"
		, dateFormat: "Y-m-d"
	});
	
	var api = {
		"sensorSelect" : "/operator/sensor/select",
		"sensorLogList" : "/operator/sensor/logList",
		"sensorLogChart" : "/operator/sensor/searchChartLog"		
	}
	
	var stationObj = {
		"stationIdx" : 0
	};
	
	var sensorLogObj = {
		"nowPage" : 1,
		"listCount" : 10,
		"pageGroup" : 5,
		"sensorIdx" : 0,
		"startDate" : null,
		"endDate" : null,
		"page" : null
	};
	
	function _sensorLogObjInit(){
		sensorLogObj.nowPage = 1;
		sensorLogObj.sensorIdx = 0;
		sensorLogObj.startDate = null;
		sensorLogObj.endDate = null;
		sensorLogObj.page = null;
	}
	
	var sensorChartLog = {
		"sensorIdx" : 0,
		"startDate" : null,
		"endDate" : null,
	};
	
	var sensorChartLogArr = [];
	
	function _sensoChartLogInit(){
		sensorChartLogArr = [];
		sensorChartLog.sensorIdx = 0;
		sensorChartLog.startDate = null;
		sensorChartLog.endDate = null;
	}
	
	
	//select 함수
	const memberSelect = document.querySelector('#memberList')
	const sensorSelect = document.querySelector('#sensorList')
	select(memberSelect)
	select(sensorSelect)

	function select(index) {
		const selectInput = index.querySelector('input')
		const options = index.querySelectorAll('ul > li')

		index.addEventListener('click', function() {
			index.classList.toggle('active');
		})
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML
				selectInput.setAttribute('value', value)
				if(selectInput.getAttribute("id") == "stationInput"){
					//	설치 역 선택 인경우
					stationObj.stationIdx = $(this).attr("data-station-idx");
					$(selectInput).attr("data-station-idx", stationObj.stationIdx);
					$("#sensorList").removeClass("active")
					_doAjaxGet(api.sensorSelect, stationObj, _setSensorSelect);
				}else if(selectInput.getAttribute("id")=="sensorInput"){
					var _sensorIdx = $(this).attr('data-sensor-idx')
					$(selectInput).attr("data-sensor-idx", _sensorIdx);
				}
			})
		})
	}
	
	function _setSensorSelect(res){
		//console.log(res)	
		var _list = res.data.sensorSelectList;
		var _target = $("#sensorSelect");
		$(_target).find("li").remove();
		if(_list.length == 0){
			//	input 초기화
			$("#sensorInput").attr("data-sensor-idx", 0);
			$("#sensorInput").val("선택");
		}else{
			for(var i=0; i<_list.length; i++){
				var _sensorIdx = _list[i].sensorIdx;
				var _sensorLocationDescription = _list[i].sensorLocationDescription;
				_li = `
					<li data-sensor-idx='${_sensorIdx}'>${_sensorLocationDescription}</li>
				`;
				$(_target).append(_li);
			}
		}
		
		var _targetLi = $(_target).find("li");
		$(_targetLi).on("click", function(e){
			e.preventDefault();
			//debugger;
			var _sensorIdx = $(this).attr('data-sensor-idx')
			var _text = $(this).text();
			$("#sensorInput").attr("data-sensor-idx", _sensorIdx);
			$("#sensorInput").val(_text)
		});		
		
		if(_targetLi.length !=0){
			var _liFirst = _targetLi[0];
			var _sensorIdx = $(_liFirst).attr('data-sensor-idx')
			var _text = $(_liFirst).text();
			$("#sensorInput").attr("data-sensor-idx", _sensorIdx);
			$("#sensorInput").val(_text)
		}
	}

	//	검색 버튼
	$("#searchBtn").on("click", function(){
		_sensorLogObjInit();
		_sensoChartLogInit();
		var _sensorIdx = $("#sensorInput").attr("data-sensor-idx"); 
		sensorLogObj.sensorIdx = _sensorIdx;
		sensorLogObj.startDate = $("#startDate").val();
		sensorLogObj.endDate = $("#endDate").val();
		
		sensorChartLog.sensorIdx = _sensorIdx;
		sensorChartLog.startDate = $("#startDate").val();
		sensorChartLog.endDate = $("#endDate").val();
		_getSensorLogList();
		_getSensorLogChart();
	});
	
	function _getSensorLogList(){
		_doAjaxGet(api.sensorLogList, sensorLogObj, _setSensorLogList)
	}
	
	function _setSensorLogList(res){
		//console.log(res);
		var _page = res.data.page;
		var _list = res.data.sensorLogList;
		
		var _target = $("#logTable");
		$(_target).find("tr.sensorLog").remove();
		
		if(_list.length == 0){
			var _tr = `
				<tr class='sensorLog noData'>
					<td colspan=7 style="text-align:center"> 조회된 데이터가 없습니다</td>
				</tr>
			`;
			$(_target).append(_tr);
		}else{
			for(var i=0; i<_list.length; i++){
				var _companyName = _list[i].companyName;
				var _sensorId = _list[i].sensorId;
				var _sensorTypeName = _list[i].sensorTypeName;
				var _sensorTypeCode = _list[i].sensorTypeCode;
				var _sensorLocationDescription = _list[i].sensorLocationDescription;
				var _sensorLogCreateDate = _list[i].sensorLogCreateDate;
				var _sensorLogValue = koreasoft.modules.string.getValueType(_sensorTypeCode ,_list[i].sensorLogValue);
				var _sensorApprovalDate = _list[i].sensorApprovalDate;
				var _tr = `
					<tr class='sensorLog'>
						<td>${_companyName}</td>
						<td>${_sensorId}</td>
						<td>${_sensorTypeName}</td>
						<td>${_sensorLocationDescription}</td>
						<td>${_sensorLogCreateDate}</td>
						<td>${_sensorTypeName}: ${_sensorLogValue}</td>
						<td>${_sensorApprovalDate}</td>					
					</tr>
				`;
				$(_target).append(_tr);
			}
		}
		
		_makePageEvt(_page);	
	}	
	
	//	페이징 이벤트
	function _makePageEvt(page){
		//	startPage : 1 , endPage 4 인경우 1,2,3,4
		var startPage = page.startPage;
		var endPage = page.endPage;
		var nowPage = page.nowPage;
		
		sensorLogObj.page = page;
		
		var _target = $("#sensorLogPageUl")
		var _liClassName = 'sensorLogPageLi';
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
		
		//	센서 상세 정보 페이징
		$(".sensorLogPageLi").on("click", function() {
			if ($(this).hasClass("prev")) {
				//	이전 누른 경우
				if (Number(sensorLogObj.page.startPage) != 1) {
					sensorLogObj.nowPage = Number(sensorLogObj.page.startPage) - 1;
					_getSensorLogList();
				}
			}else if ($(this).hasClass("next")) {
				//	다음 누른 경우
				if (Number(sensorLogObj.page.endPage) != Number(sensorLogObj.page.totalPage)) {
					sensorLogObj.nowPage = Number(sensorLogObj.page.endPage) + 1;
					_getSensorLogList();
				}
			} else {
				sensorLogObj.nowPage = $(this).attr("data-page");
				_getSensorLogList();
			}
		})
	};	
	
	//	센서 차트 로그 조회
	function _getSensorLogChart(){
		_doAjaxGet(api.sensorLogChart, sensorChartLog, _setSensorLogChart)
	}
	
	//	센서 차트 로그 조회 컬백
	function _setSensorLogChart(res){
		var _list =  res.data.sensorLogChart
		//console.log(_list)
		for(var i=0; i<_list.length; i++){
			sensorChartLogArr.push(Number(_list[i].sensorLogValue));
		}
		$(".chartLiEvt")[0].click();		
	}
	function makeLabels() {
		let arr = []
		for (i = 1; i <= 30; i++) {
			arr.push(i)
		}
		return arr
	}
	
	var _myChart = null;		
	function _makeChart(type){
		
		if(_myChart != null){
			_myChart.destroy();
		}
		
		const color = ['#64A70B', '#009CDE', '#002664']
		const colorAlpha = ['rgba(100,167,11,0.2)', 'rgba(0,156,222,0.2)', 'rgba(0,38,100,0.2)']
		const chartId = document.getElementById('dataChart');
		let sensorData = sensorChartLogArr;
		
		if(type == 0){
			//바 차트
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
		}else if(type == 1){
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
	
		function _csvFileClear(){
		$("#csvFileName").val("");
	}
	
	//	저장 취소 버튼
	$("#csvProcCancelBtn").on("click", function(){
		_csvFileClear()
		$("#csvModal").removeClass("active");
	})
	//	저장 취소 버튼
	$("#csvModal").find(".close").on("click", function(){
		$("#csvProcCancelBtn").click();
	})
	//	저장 버튼
	$("#csvDown").on("click", function(e){
		var _target = $("#logTable").find(".noData")
		if(_target.length > 0 ){
			pushNotify("조회된 데이터가 없습니다", 2)
			return;
		}
		$("#csvModal").addClass("active");
	})
	//	저장 모달 - 파일 저장버튼
	$("#csvProcBtn").on("click", function(){
		var _obj = {
			"sensorIdx" : sensorLogObj.sensorIdx,
			"startDate" : sensorLogObj.startDate,
			"endDate" : sensorLogObj.endDate,
			"fileName" : $("#csvFileName").val()
		}
		
		if(_obj.sensorIdx==0){
			pushNotify("센서를 선택해주시기 바랍니다", 2)
			return;
		}else if(_obj.startDate == null || _obj.endDate == null){
			pushNotify("조회날짜를 선택해주시기 바랍니다", 2)
			return;
		}else if(_obj.startDate == "" || _obj.endDate == ""){
			pushNotify("조회날짜를 선택해주시기 바랍니다", 2)
			return;
		}else if(_obj.fileName == ''){
			pushNotify("파일명을 작성해주시기 바랍니다", 2)
			return;
		}
		
		var _url = `/api/v1/sensor/downloadCSV?sensorIdx=${_obj.sensorIdx}&startDate=${_obj.startDate}&endDate=${_obj.endDate}&fileName=${_obj.fileName}`;
		
		location.href = _url;
		$("#csvProcCancelBtn").click();
	})
	
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
	
	/////	최초 실행
	$("#searchBtn").click();
}
