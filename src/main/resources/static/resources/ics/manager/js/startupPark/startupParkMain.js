window.onload = function(){
	
	 //select 함수
	const sensingSelect = document.querySelector('.sensing .select');	//	실시간 센싱 현황
	const chartSelect = document.querySelector('.charts .select');		//	
	const smartInfoSelct = document.querySelector('.smartInfo .select');//	스마트 함체 선택

	select(smartInfoSelct);
	select(chartSelect);
	select(sensingSelect);

	function select(index) {
		const selectInput = index.querySelector('input');
		const options = index.querySelectorAll('ul > li');
		
		index.addEventListener('click', function(e) {
			index.classList.toggle('active');
		})
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML;
				//selectInput.setAttribute('value', value);
				$(selectInput).val(value)
				
				if(selectInput.getAttribute("id") == "sensingInput"){
					//	실시간 센싱 현황
					var _enclosureIdx = $(this).attr('data-enclosure-idx');
					$(selectInput).attr('data-enclosure-idx', _enclosureIdx);
					_getSensorRealTime();
				}else if(selectInput.getAttribute("id") == "chartInput"){
					//	센서별 현재 데이터
					var _sensorTypeIdx = $(this).attr('data-sensor-type-idx');
					$(selectInput).attr('data-sensor-type-idx', _sensorTypeIdx);
					_getSensorCurrentValue();
					
				}else if(selectInput.getAttribute("id") == "smartInfoInput"){
					//	스마트 함체 정보
					//	smartInfoInput
					var _enclosureIdx = $(this).attr('data-enclosure-idx');
					$(selectInput).attr('data-enclosure-idx', _enclosureIdx);
					$("#smartInfoTitle").text(value); //	smartInfoTitle
					_getSensorData();
				}
				
			})
		})
	};
	
	//modal
	const smartModal = document.getElementById('smartModal')
	const fireModal = document.getElementById('fireModal')
	const apModal = document.getElementById('apModal')
	function modal(index){
	    const closeBtn = index.querySelector('.close')
	    closeBtn.addEventListener('click', function(){
	        index.classList.remove('active')
	    })
	}
	
	function makeModalBtn(index, btn){
		
	    btn.addEventListener('click', function (e) {
			//debugger;
			var _sensorId = $(e.currentTarget).attr('data-sensor-id');
			var _dataType = $(e.currentTarget).attr('data-type');
			
			modalClear(_dataType);
			var _obj = {
				'sensorId' : _sensorId,
				'dataType' : _dataType
			}
			
			$.ajax({
				type: "GET",
				url: "/manager/main/sensorInfoList",
				dataType: "json",
				data: _obj,
				contentType: "application/json; charset=utf-8",
				beforeSend: function(xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(res) {
					
					var _list = res.data.sensorInfoList;
					var _type = res.data.dataType;
					
					if(_type == "1" || _type == 1){
						for(var i=0; i<_list.length; i++){
							//debugger;
							var companyName = _list[i].companyName;
							var companyRole = koreasoft.modules.string.getRoleName(_list[i].companyRole); 
							var requestApprovalDate =_list[i].requestApprovalDate;
							var sensorApprovalDate = _list[i].sensorApprovalDate;
							var sensorLocationDescription = _list[i].sensorLocationDescription;
							var sensorTypeName = _list[i].sensorTypeName;
							var sensorId = _list[i].sensorId;
						
							var _li = `
								<li 
									data-company-name='${companyName}'
									data-company-role='${companyRole}'
									data-request-approval-date='${requestApprovalDate}'
									data-sensor-approval-date='${sensorApprovalDate}'
									data-sensor-location-description='${sensorLocationDescription}'
									data-sensor-type-name='${sensorTypeName}'
									data-sensor-id='${sensorId}'	
								>
									${sensorTypeName}
								</li>
							`;
							$("#smartModalUl").append(_li);
						}
						
						var _liEvt = $("#smartModalUl").find("li")
						$(_liEvt).on("click", function(e){
							$("#smartModalUl").find("li").removeClass("active");
							$(this).addClass("active");
							
							$("#smartModalData").children().remove();
							
							var companyName = $(this).attr('data-company-name');
							var companyRole = $(this).attr('data-company-role'); 
							var requestApprovalDate =  koreasoft.modules.string.dateFormatHi($(this).attr('data-request-approval-date'));
							var sensorApprovalDate =  koreasoft.modules.string.dateFormatHi($(this).attr('data-sensor-approval-date'));
							var sensorLocationDescription = $(this).attr('data-sensor-location-description');
							var sensorTypeName = $(this).attr('data-sensor-type-name');
							var sensorId = $(this).attr('data-sensor-id');
							var _table = `
							<table>
								<tr>
									<th>승인 일자</th>
									<td>${sensorApprovalDate}</td>
								</tr>
								<tr>
									<th>센서 아이디</th>
									<td>${sensorId}</td>
								</tr>
								<tr>
									<th>승인 종류</th>
									<td>${sensorTypeName}</td>
								</tr>
								<tr>
									<th>${companyRole}</th>
									<td>${companyName}</td>
								</tr>
								<tr>
									<th>설치 위치</th>
									<td>${sensorLocationDescription}</td>
								</tr>
								<tr>
									<th>승인 요청 시간</th>
									<td>${requestApprovalDate}</td>
								</tr>
								<tr class="green">
									<th>승인 시간</th>
									<td>${sensorApprovalDate}</td>
								</tr>
							</table>
							`;
							
							$("#smartModalData").append(_table);
						});
						
						$(_liEvt)[0].click();
						
					}else if(_type == '2' || _type == 2){
						//	화재 감지기는 1개 밖에 없으므로 index 0을 사용
						var companyName = _list[0].companyName;
						var companyRole = koreasoft.modules.string.getRoleName(_list[0].companyRole); 
						var requestApprovalDate = koreasoft.modules.string.dateFormatHi(_list[0].requestApprovalDate);
						var sensorApprovalDate = koreasoft.modules.string.dateFormatHi(_list[0].sensorApprovalDate);
						var sensorLocationDescription = _list[0].sensorLocationDescription;
						var sensorTypeName = _list[0].sensorTypeName;
						var sensorId = _list[0].sensorId;
						
						var _table = `
							<table>
								<tr>
									<th>승인 일자</th>
									<td>${sensorApprovalDate}</td>
								</tr>
								<tr>
									<th>센서 아이디</th>
									<td>${sensorId}</td>
								</tr>
								<tr>
									<th>승인 종류</th>
									<td>${sensorTypeName}</td>
								</tr>
								<tr>
									<th>${companyRole}</th>
									<td>${companyName}</td>
								</tr>
								<tr>
									<th>설치 위치</th>
									<td>${sensorLocationDescription}</td>
								</tr>
								<tr>
									<th>승인 요청 시간</th>
									<td>${requestApprovalDate}</td>
								</tr>
								<tr class="green">
									<th>승인 시간</th>
									<td>${sensorApprovalDate}</td>
								</tr>
							</table>
						`;
						$("#fireModalData").append(_table);	
					} else {
						var _table = `
							<table>
								<tr>
									<th>승인 일자</th>
									<td>2022-10-07</td>
								</tr>
								<tr>
									<th>센서 아이디</th>
									<td>apSensor</td>
								</tr>
								<tr>
									<th>승인 종류</th>
									<td>무선 AP</td>
								</tr>
								<tr>
									<th>설치 위치</th>
									<td>각 함체번호 앞</td>
								</tr>
								<tr>
									<th>승인 요청 시간</th>
									<td>2022-10-07</td>
								</tr>
								<tr class="green">
									<th>승인 시간</th>
									<td>2022-10-07</td>
								</tr>
							</table>
						`;
						$("#apModalData").append(_table);
					}
	        		index.classList.add('active');
				},
				error: function(e) {
					if (e.status == 400) {
						//alert(e.responseJSON.message);
					} else {
						alert("데이터 전송 중 에러가 발생했습니다");
					}
				},
				complete: function(e) {
				}
			});//	ajax end
	    })
	}
	
	function modalClear(_dataType){
		//	온도 등의 센서
		if(_dataType == "1" || _dataType == 1){
			$("#smartModalUl").children().remove();
			$("#smartModalData").children().remove();
		}else if(_dataType =="2" || _dataType == 2){
			$("#fireModalData").children().remove();
		}
	}
	
	//iot함체  위치 생성
	makeMap()
	function makeMap() {
		//const mapSelecter = document.querySelector('.maps .select')
		writeIotBox();
		//select 컨트롤러
		//mapSelect()
		/*function mapSelect() {
			const selectInput = mapSelecter.querySelector('input')
			const options = mapSelecter.querySelectorAll('ul > li')
			writeIotBox(0)

			mapSelecter.addEventListener('click', function() {
				mapSelecter
					.classList
					.toggle('active');
			})
			options.forEach((item) => {
				item.addEventListener('click', function(e) {
					let value = e.target.innerHTML
					selectInput.setAttribute('value', value)
					writeIotBox(e.target.dataset.floor)
				})
			})
		}*/

		//실제 데이터 뿌리기 + 맵 백그라운드
		function writeIotBox(floor) {
			const ImgContainer = document.querySelector('.map-img');
			ImgContainer.innerHTML = ''; // 초기화 목적

			const map = document.createElement('img');
			map.setAttribute('src', '/resources/publish/images/startupParkBack.png');
			ImgContainer.appendChild(map);

			// img 태그를 새로 생성 -> 이미지를 경로로 잡고 .map-ing 밑에 추가한 후 초기화 (이미지 경로가 백그라운드 이미지)

			//모달 생성하기
			const sensorIcon = ImgContainer.querySelectorAll('.sensor-icon');
			sensorIcon.forEach((item) => {
				makeModalBtn(smartModal, item);
			})
			modal(smartModal)
			const fireIcon = ImgContainer.querySelectorAll('.fire-icon');
			fireIcon.forEach((item) => {
				makeModalBtn(fireModal, item);
			})
			modal(fireModal)
			const apIcon = ImgContainer.querySelectorAll('.ap-icon');
			apIcon.forEach((item) => {
				makeModalBtn(apModal, item);
			})
			modal(apModal)
		}
		//sensorIcon 만들기
	}
	
        
	////////////////////////////
	
	//	센서, 회원사, 서비스 수 조회
	function _getToday(){
		var _url = "/manager/main/today"
		var _data = null;
		_doAjaxGet(_url, _data, _setToday);
	}
	
	function _setToday(res){
		var today = res.data.today;					//	오늘일자
		var sensorCount = res.data.sensorCount;		//	센서 숫자
		var companyCount = res.data.companyCount;	//	회원사 숫자
		var serviceCount = res.data.serviceCount;	//	서비스 숫자
		
		$(".mainToday").text(today);
		$(".mainSensorCnt").text(sensorCount);
		$(".mainUserCnt").text(companyCount);
		$(".mainServiceCnt").text(serviceCount);
		
		setTimeout(_getToday, 20000);
	}
	
	//	센서정보 수신상태 조회
	function _getStatusCnt(){
		var _url = "/manager/main/statusCnt"
		var _data = null;
		_doAjaxGet(_url, _data, _setStatusCnt);
	}
	
	function _setStatusCnt(res){
		var _statusCnt = res.data.sensorStatusCount;	//	정상 수신 센서
		var _totalCnt = res.data.sensorTotalCount;		//	전체 센서
		var _userCnt = res.data.userCount;
		
		var _sensorRate = Math.floor(Number(_statusCnt) / Number(_totalCnt) * 100);
		_makeHourChart(_sensorRate);
		
		//var _dayRate = 86;
		_makeDayChart(_userCnt);
		setTimeout(_getStatusCnt, 20000);
	}
	
	
	//	1시간 정상센서 수신율
	var _hourChart = null;
	function _makeHourChart(sensorRate) {
		//const color = ['#64A70B', '#eee', '#002664'];
		if(_hourChart != null){
			_hourChart.destroy();
		}
		
		var sensing = document.getElementById('hourData');
		
		const ctx = document
			.getElementById("hourData")
			.getContext("2d");
		const nowData = sensorRate;
		var gradient = ctx.createLinearGradient(0, 0, 0, 100);
		gradient.addColorStop(0, 'rgba(254,55,94,1)');
		gradient.addColorStop(1, 'rgba(255,200,156,1)');

		const span = document.getElementById('hourPercent')
		span.innerHTML = `${nowData}%`;

		_hourChart = new Chart(sensing, {
			type: 'doughnut',
			data: {
				labels: [
					'1', '2'
				],
				datasets: [
					{
						data: [
							nowData, 100 - nowData
						],
						backgroundColor: [
							gradient, '#eee'
						],
						borderRadius: 50
					}
				]
			},
			options: {
				cutout: '60%',
				layout: 10,

				scales: {
					x: {
						display: false
					}
				},
				plugins: {
					tooltip: {
						enabled: false
					},
					title: {
						display: true,
						text: '1시간 정상센서 수신율 ',
						position: 'bottom',
						font: {
							size: 12,
							color: '#7F7F7F'
						}
					},
					legend: {
						display: false
					}
				}
			}
		})
	}
	
	//	1일 서비스 앱 접속 현황
	var _dayChart = null;
	function _makeDayChart(dayRate) {
		//const color = ['#64A70B', '#eee', '#002664']		
		if(_dayChart != null){
			_dayChart.destroy();
		}

		const sensing = document.getElementById('dayData');
		const ctx = document
			.getElementById("dayData")
			.getContext("2d");
		const nowData = dayRate;
		var gradient = ctx.createLinearGradient(0, 0, 0, 100);
		gradient.addColorStop(0, 'rgba(97,110,255,1)');
		gradient.addColorStop(1, 'rgba(12,223,245,1)');

		const span = document.getElementById('dayPercent')
		span.innerHTML = `${nowData}명`

		_dayChart = new Chart(sensing, {
			type: 'doughnut',
			data: {
				labels: [
					'1', '2'
				],
				datasets: [
					{
						data: [
							nowData
						],
						backgroundColor: [
							gradient, '#eee'
						],
						borderRadius: 50
					}
				]
			},
			options: {
				cutout: '60%',
				layout: 10,

				scales: {
					x: {
						display: false
					}
				},
				plugins: {
					tooltip: {
						enabled: false
					},
					title: {
						display: true,
						text: '1일 서비스 앱 접속 현황 ',
						position: 'bottom',
						font: {
							size: 12,
							color: '#7F7F7F'
						}
					},
					legend: {
						display: false
					}
				}
			}
		})
	}	
	
	//	센서 경고현황 로그 조회
	function _getSensorWarnLog(){
		var _url = "/manager/main/sensorWarnLog";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorWarnLog);		
	}
	
	//	센서 경고현황 컬백 로그 
	function _setSensorWarnLog(res){
		var _target = $("#sensorWarnLog");
		$(_target).children().remove();
		var _list = res.data.warnLogList;
		if(_list.length == 0){
			var _li = `<li>조회된 데이터가 없습니다</li>`;
			$(_target).append(_li);
		}else{
			for(var i=0; i<_list.length; i++){
				var _cDate = koreasoft.modules.string.dateFormatHis(_list[i].sensorLogCreateDate);
				var _locationDescription = _list[i].sensorLocationDescription;
				var _v = koreasoft.modules.string.getValueType(_list[i].sensorTypeCode, _list[i].sensorLogValue);
				var _thresHold = _list[i].sensorThresHold;
				
				//var _li = `<li>${_cDate} | ${_locationDescription} | 현재값 : ${_v} | 임계치 : ${_thresHold}</li>`;
				var _li = `<li>${_cDate} - ${_locationDescription} : ${_v}</li>`;
				$(_target).append(_li);		
			}
		}
		setTimeout(_getSensorWarnLog, 20000);
	}
	
	//	실시간 센싱현황
	function _getSensorRealTime(){
		
		//	셀렉트 선택 추가
		var _url = "/manager/main/sensorRealTime";
		var _data = {
			'enclosureIdx' : $("#sensingInput").attr('data-enclosure-idx')
		}
		_doAjaxGet(_url, _data, _setSensorRealTime);
	}
	
	var _sensorRealTimeInterval = null;
	var _sensorRealTimeChart = null;
	var _sensorRealTimeBmsChart = null;
	function _setSensorRealTime(res){

		if(_sensorRealTimeChart != null){
			_sensorRealTimeChart.destroy();
		}

		if(_sensorRealTimeBmsChart != null){
			_sensorRealTimeBmsChart.destroy();
		}
		
		var _tempList = res.data.tempList;
		var _dustList = res.data.dustList;
		var _humidityList = res.data.humidityList;
		var _bmsList = res.data.bmsList;
		
		const color = ['#64A70B', '#009CDE', '#002664']
		const sensing = document.getElementById('sensingChart');
		const tempData = [];
		const humiData = [];
		const dustData = [];
		const bmsData = [];
		
		for(var i=0; i<_tempList.length; i++){
			tempData.push(_tempList[i].sensorValue);
		}

		for(var i=0; i<_dustList.length; i++){
			dustData.push(_dustList[i].sensorValue);
		}
		
		for(var i=0; i<_humidityList.length; i++){
			humiData.push(_humidityList[i].sensorValue);
		}

		for(var i=0; i<_bmsList.length; i++){
			bmsData.push(_bmsList[i].sensorValue);
		}

		function makeLabels() {
			let arr = []
			for (i = 1; i <= 30; i++) {
				arr.push(i)
			}
			return arr
		}

		if(dustData.length === 0 && humiData.length === 0 && tempData.length === 0) {
			_sensorRealTimeBmsChart = new Chart(sensing, {
				type: 'line',
				data: {
					labels: makeLabels(),
					datasets: [
						{
							label: '배터리 %',
							data: bmsData,
							backgroundColor: '#fff',
							borderColor: color[0],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						}
					]
				},
				options: {
					scales: {
						x: {
							display: false
						},
						y: {
							suggestedMin: 0,
							suggestedMax: 100
						}
					},
					plugins: {
						tooltip: {
							enabled: false
						},
						legend: {
							align: 'end',
							labels: {
								font: {
									size: 10
								},
								boxWidth: 4,
								boxHeight: 4,
								usePointStyle: true,
								pointStyle: 'circle'
							}
						}
					}
				}
			})
		} else {
			_sensorRealTimeChart = new Chart(sensing, {
				type: 'line',
				data: {
					labels: makeLabels(),
					datasets: [
						{
							label: '온도 ℃',
							data: tempData,
							backgroundColor: '#fff',
							borderColor: color[0],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						}, {
							label: '습도 %',
							data: humiData,
							backgroundColor: '#fff',
							borderColor: color[1],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						}, {
							label: '미세먼지 ㎍/m',
							data: dustData,
							backgroundColor: '#fff',
							borderColor: color[2],
							borderWidth: 2,
							pointRadius: 2,
							borderDash: [
								2, 2
							],
							tension: 0.1
						}
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
							align: 'end',
							labels: {
								font: {
									size: 10
								},
								boxWidth: 4,
								boxHeight: 4,
								usePointStyle: true,
								pointStyle: 'circle'
							}
						}
					}
				}
			})
		}

		if(_sensorRealTimeInterval != null){
			 clearInterval(_sensorRealTimeInterval); 
		}
		_sensorRealTimeInterval = setInterval(_getSensorRealTime, 20000);
	}
	
	//	센서별 현재데이터
	function _getSensorCurrentValue(){
				
		//	셀렉트 선택 추가
		var _url = "/manager/main/sensorCurrentValue";
		var _data = {
			'sensorType' :  $("#chartInput").attr('data-sensor-type-idx')
		}
		_doAjaxGet(_url, _data, _setSensorCurrentValue);
	}
	
	var _sensorCurrentChart = null;
	var _sensorCurrentValueInterval = null;
	function _setSensorCurrentValue(res){
		
		if(_sensorCurrentChart!=null){
			_sensorCurrentChart.destroy();
		}
		
		var _currList = res.data.sensorCurrList;
		var _type = Number(res.data.sensorTypeIdx);
		
		const color = ['', '#64A70B', '', '#009CDE', '#002664'];
		const colorAlpha = ['','rgba(100,167,11,0.2)','', 'rgba(0,156,222,0.2)', 'rgba(0,38,100,0.2)'];
		const label = ['', '온도 ℃', '', '습도 %', '미세먼지 ㎍/m'];
		const sensing = document.getElementById('sensorNow');
		let nowData = [];
		let labels = [];
		
		for(var i=0; i<_currList.length; i++){
			var _enclosureName = _currList[i].enclosureName;
			var _sensorValue = _currList[i].sensorValue;
			
			nowData.push(_sensorValue);
			labels.push(_enclosureName);
		}	

		_sensorCurrentChart = new Chart(sensing, {

			type: 'bar',
			data: {
				labels: labels,
				datasets: [
					{
						label: label[_type-1],
						data: nowData,
						backgroundColor: colorAlpha[_type-1],
						borderColor: color[_type-1],
						borderWidth: 1,
						borderRadius: 4,
						barThickness: 12
					}
				]
			},
			options: {
				scales: {
					x: {
						display: true,
						ticks: {
							autoSkip: false,
							maxRotation: 0,
							minRotation: 0,
							font: {
								size: 10,
								weight: 'bold'
							}
						}
					},
					
				},
				plugins: {
					tooltip: {
						enabled: false
					},
					legend: {
						align: 'end',
						labels: {
							font: {
								size: 10
							},
							boxWidth: 4,
							boxHeight: 4,
							usePointStyle: true,
							pointStyle: 'circle'
						}
					}
				}
			}
		});

		if(_sensorCurrentValueInterval != null){
			clearInterval(_sensorCurrentValueInterval); 
		}
		_sensorCurrentValueInterval = setInterval(_getSensorCurrentValue, 20000);
		
	}
	
	//	센서별 데이터 수집율 - 센서 타입별 센서 로그 수(현재일자)
	function _getSensorTypeCnt(){
		
		var _url = "/manager/main/sensorTypeCnt"
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorTypeCnt);
				
	}
	
	//	센서별 데이터 수집율 - 컬백 - 센서 타입별 센서 로그 수(현재일자)	//	설치된 센서종류별 데이터 수신을을 표시함(1일)
	var _sensorTypeChart = null;
	function _setSensorTypeCnt(res){

		if(_sensorTypeChart != null){
			_sensorTypeChart.destroy();
		}
		
		const color = [
            '#20bcff',
            '#64A70B',
            '#8fd138',
            '#9bee28',
            '#FF671F',
            '#f38e5f',
            'rgba(255,103,31,0.2)',
            '#63a70b57',
            'rgba(100,167,11,0.2)',
            'rgba(0,38,100,0.2)',
            'rgba(0,156,222,0.2)',
            '#D9E8F3',
        ]
        
        var _list = res.data.typeLogCntList;

        const sensing = document.getElementById('dataCheck');
        let _sensorLogData = [];
        let _nowData = [];
        let _labels = [];
        let _totalCnt = 0;
        for(var i=0; i<_list.length; i++){
			var _sensorLogCnt = _list[i].sensorLogCnt;
			_totalCnt = Number(_sensorLogCnt) + _totalCnt;
			_sensorLogData.push(_sensorLogCnt);	//	데이터 : 
			_labels.push(_list[i].sensorTypeName);	//	라벨
		}
		
		for(var i=0; i<_sensorLogData.length; i++){
			var _v = Math.ceil(_sensorLogData[i]/_totalCnt * 100);
			_nowData.push(_v);
		}

        _sensorTypeChart = new Chart(sensing, {
            type: 'doughnut',
            data: {
                labels: _labels,
                datasets: [
                    {
                        data: _nowData,
                        backgroundColor: color,
                        borderWidth: 0
                    }
                ]
            },
            plugins: [ChartDataLabels],
            options: {
                cutout: '20%',
                layout: 10,
                hoverOffset: 24,
                scales: {
                    x: {
                        display: false
                    }
                },
                plugins: {
                    datalabels: {
                        formatter: function (value, context) {
                            var idx = context.dataIndex
                            function showContext(value, context) {
                                return idx
                            }
                            return `${context
                                .dataset
                                .data[showContext(value, context)]}%`
                        },
                        anchor: 'center',
                        display: 'auto',
                        font: {
                            size: 14,
                            weight: 'bold'
                        },
                        color: '#fff'
                    },
                    tooltip: {
                        enabled: false
                    },
                    legend: {
                        position: 'right',
                        align: 'start',
                        paddimg: 50,
                        labels: {
                            font: {
                                size: 10,
                                weight: 'bold'
                            },
                            boxWidth: 6,
                            boxHeight: 6,
                            usePointStyle: true,
                            pointStyle: 'circle'
                        }
                    }
                }
            }
		 })
			 
		setTimeout(_getSensorTypeCnt, 20000);
	}
	
	//	요일별 평균 데이터
	function _getSensorWeek(){
		var _url = "/manager/main/sensorWeek";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorWeek);
	}
	
	var _sensorWeek = null;
	function _setSensorWeek(res){
		
		if(_sensorWeek != null){
			_sensorWeek.destroy();
		}

		var _dayList = res.data.day.dayList;
		var _tempList = res.data.tempList;
		var _humidityList = res.data.humidityList;
		var _dustList = res.data.dustList;	//	미세먼지
		
		let _tempData = [];
		let _humiData = [];
		let _dustData = [];
		
		for(var i=0; i<_tempList.length; i++){
			var _v = Number(_tempList[i].sensorLogValue);
			var _cnt = Number(_tempList[i].sensorLogCnt);
			_tempData.push(Math.ceil(_v/_cnt));
		}
		
		for(var i=0; i<_humidityList.length; i++){
			var _v = Number(_humidityList[i].sensorLogValue);
			var _cnt = Number(_humidityList[i].sensorLogCnt);
			_humiData.push(Math.ceil(_v/_cnt));
		}
		
		for(var i=0; i<_dustList.length; i++){
			var _v = Number(_dustList[i].sensorLogValue);
			var _cnt = Number(_dustList[i].sensorLogCnt);
			_dustData.push(Math.ceil(_v/_cnt));
		}
		
		const color = ['#64A70B', '#009CDE', '#002664']
		const sensing = document.getElementById('weekData');
		
		_sensorWeek = new Chart(sensing, {
			type: 'bar',
			data: {
				labels: [
					'월요일',
					'화요일',
					'수요일',
					'목요일',
					'금요일',
					'토요일',
					'일요일'
				],
				datasets: [
					{
						label: '온도 ℃',
						data: _tempData,
						backgroundColor: color[0],
						borderColor: '#fff',
						borderWidth: 2,
						borderRadius: 4,
						barThickness: 12
					}, {
						label: '습도 %',
						data: _humiData,
						backgroundColor: color[1],
						borderColor: '#fff',
						borderWidth: 2,
						borderRadius: 4,
						barThickness: 12
					}, {
						label: '미세먼지 ㎍/m',
						data: _dustData,
						backgroundColor: color[2],
						borderColor: '#fff',
						borderWidth: 2,
						borderRadius: 4,
						barThickness: 12
					}
				]
			},
			options: {
				responsive: true,
				scales: {
					x: {
						display: true,
						ticks: {
							autoSkip: false,
							maxRotation: 0,
							minRotation: 0,
							font: {
								size: 10,
								weight: 'bold'
							}
						}
					}
				},
				plugins: {
					tooltip: {
						enabled: false
					},
					legend: {
						align: 'end',
						labels: {
							font: {
								size: 12
							},
							boxWidth: 10,
							boxHeight: 10,
							usePointStyle: true,
							pointStyle: 'rect'
						}
					}
				}
			}
		});		
		setTimeout(_getSensorWeek, 20000);
	}
	
	//	센싱 실시간 로그데이터
	function _getSensorLogLimit(){
		var _url = "/manager/main/sensorRealLog";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorLogLimit);
	}
	
	//	센싱 실시간 로그데이터 컬백
	function _setSensorLogLimit(res){
		var _target = $("#sensorLog");
		$(_target).children().remove();
		var _list = res.data.realLogList;
		if(_list.length == 0){
			var _li = `<li> 조회된 데이터가 없습니다 </li>`;
			$(_target).append(_li);
		}else{
			for(var i=0; i<_list.length; i++){
				//debugger;
				var _cDate = koreasoft.modules.string.dateFormatHis(_list[i].sensorLogCreateDate);
				var _locationDescription = _list[i].sensorLocationDescription;
				var _v = koreasoft.modules.string.getValueType(_list[i].sensorTypeCode, _list[i].sensorLogValue);//_list[i].sensorLogValue;
				var _thresHold = _list[i].sensorThresHold;
				var _type = Number(_list[i].sensorLogType);
				//1 경고(임계치 초과), 2 일반
				//var _li = `<li class='${_type==1? 'error' : ''}'>${_cDate} | ${_locationDescription} | 현재값 : ${_v} | 임계치 : ${_thresHold}</li>`;
				
				var _li = null;
				if(_type == 1){
					_li = `<li class='error'>${_cDate} - ${_locationDescription} : ${_v} (경고)</li>`;
				}else{
					_li = `<li class=>${_cDate} - ${_locationDescription} : ${_v}</li>`;
				}
				
				$(_target).append(_li);		
			}
		}
		
		setTimeout(_getSensorLogLimit, 20000);
	}
	
	//	함체의 센서 데이터 조회
	function _getSensorData(){
		var _url = "/manager/main/sensorData";
		var _data = {
			'enclosureIdx' : $("#smartInfoInput").attr('data-enclosure-idx')
		}
		_doAjaxGet(_url, _data, _setSensorData);
	}
	
	var _sensorDataInterval = null;
	function _setSensorData(res){
		
		$("#smartInfoSensorList").find("li").remove();
		var _list = res.data.sensorDataList;
		for(var i=0; i<_list.length; i++){
			var _type = _list[i].sensorTypeCode;
			var _typeName = _list[i].sensorTypeName;
			var _sensorValue = _list[i].sensorValue;
			var _unit =  koreasoft.modules.string.getValueType(_type, _sensorValue);
			
			var _img = null;
			var _class = null;
			if(_type.toLocaleLowerCase().indexOf('temp')> 0){
				_img = '<img src="/resources/publish/images/temp.png" alt="">';
				_class = 'temp';
			}else if(_type.toLocaleLowerCase().indexOf('humi')>0){
				_img = '<img src="/resources/publish/images/humi.png" alt="">';
				_class = 'humi';
			}else if(_type.toLocaleLowerCase().indexOf('dust')>0){
				_img = '<img src="/resources/publish/images/dust.png" alt="">';
				_class = 'dust';
			}else{
				_class='';
				_img = '';
			}
			
			var _li = 
			`
				<li class='${_class}'>
					${_img}
					<p>${_typeName} : ${_unit}</p>
				</li>
			`;
			$("#smartInfoSensorList").append(_li);
		}
		
		if(_sensorDataInterval != null){
			clearInterval(_sensorDataInterval);
		}
		
		_sensorDataInterval = setInterval(_getSensorData, 20000)
		
	}
	
	
	function _doAjaxGet(url, data, callBack){		
		$.ajax({
			type: "GET",
			url: url,
			dataType : "json",
			data: data == null? null : data,
			contentType : "application/json; charset=utf-8", 
			beforeSend: function (xhr) {
				xhr.setRequestHeader(_csrfHeader, _csrfToken);
				xhr.setRequestHeader("AJAX", "true");
				
			},
			success: function (res) {
				callBack(res);
			},
			error: function (e) {

				if(e.status == 400){
					//alert(e.responseJSON.message);
				}else{
					//alert("데이터 전송 중 에러가 발생했습니다");
				}
				
			},
			complete : function(e){
				
			}
		});
	}
	
	//	실행
	//	today
	_getToday();
	
	//	센서정보 수신상태 - 1시간
	_getStatusCnt();
	
	//	센서 경고 현황	- 현재일자 - 최근 30개
	_getSensorWarnLog();
	
	//	센서별 데이터 수집율 : 현재일자 - 센서 타입별 센서 로그 수 
	_getSensorTypeCnt();
	
	//	센싱 실시간 로그데이터: 현재일자 - 최근 30개
	_getSensorLogLimit(); 
	
	//	센서별 현재데이터
	if($(".chartLi").length!=0){
		var _s = $(".chartLi")[0];//	0번 선택
		var _text = $(_s).html();	//	센서 타입명
		var _v = $(_s).attr("data-sensor-type-idx"); // 센터 타입 선택
		var _target =  $("#chartInput");
		$(_target).val(_text);
		$(_target).attr('data-sensor-type-idx', _v);
		_getSensorCurrentValue();
	}
	
		
	//	실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 - 현재일자 최근 30개
	if($(".sensingLi").length!=0){
		var _s = $(".sensingLi")[0];//	0번 선택
		var _text = $(_s).html();	//	함체명
		var _v = $(_s).attr("data-enclosure-idx"); // 함체 번호 선택
		var _target =  $("#sensingInput");
		$(_target).val(_text);
		$(_target).attr('data-enclosure-idx', _v);
		_getSensorRealTime();	
	}		
	
	
	//	함체의 센서 데이터 조회 - 최근 받은값
	if($(".smartInfoLi").length!=0){
		var _s = $(".smartInfoLi")[0];//	0번 선택
		var _text = $(_s).html();	//	함체명
		var _v = $(_s).attr("data-enclosure-idx"); // 함체 번호 선택
		var _target =  $("#smartInfoInput");
		$(_target).val(_text);
		$("#smartInfoTitle").text(_text); //	smartInfoTitle
		$(_target).attr('data-enclosure-idx', _v);
		_getSensorData();
	}
	
	//	요일별 평균 데이터 조회	- 주 단위(월~일)
	_getSensorWeek();
	
	//	비디오
	/*
	var canvas = document.getElementsByClassName('jsmpeg');
	var client = [];
	client.push('ws://192.168.230.105:20005');
	client.push('ws://192.168.230.105:20007');
	
	var player = [];
	for (var i = 0; i < canvas.length; i++) {
		var p = new JSMpeg.Player(client[i], {
			canvas: canvas[i]
		});
		
		player.push(p);		
	}
	
	canvas[0].style.width  = '275px';
  	canvas[0].style.height = '145px';
  	
  	canvas[1].style.width  = '275px';
  	canvas[1].style.height = '145px';
	*/
};
	