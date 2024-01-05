window.onload = function() {
	//modal
	const smartModal = document.getElementById('smartModal')
	const fireModal = document.getElementById('fireModal')
	const apModal = document.getElementById('apModal')
	function modal(index) {
		const closeBtn = index.querySelector('.close')
		closeBtn.addEventListener('click', function() {
			index.classList.remove('active')
		})
	}
	function makeModalBtn(index, btn) {
		btn.addEventListener('click', function(e) {
			//index.classList.add('active');
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
				url: "/operator/main/sensorInfoList",
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

	//select 함수
	const sensingSelect = document.querySelector('.sensing .select')
	const smartInfoSelct = document.querySelector('.smartInfo .select')

	select(smartInfoSelct)
	select(sensingSelect)

	function select(index) {
		const selectInput = index.querySelector('input')
		const options = index.querySelectorAll('ul > li')

		index.addEventListener('click', function() {
			index.classList.toggle('active');
		})
		options.forEach((item) => {
			item.addEventListener('click', function(e) {
				let value = e.target.innerHTML
				//selectInput.setAttribute('value', value)
				$(selectInput).val(value)
				if(selectInput.getAttribute("id") == "sensingInput"){
					//	실시간 센싱 현황
					var _enclosureIdx = $(this).attr('data-enclosure-idx');
					$(selectInput).attr('data-enclosure-idx', _enclosureIdx);
					_getSensorRealTime();
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
	}

	function modalClear(_dataType){
		//	온도 등의 센서
		if(_dataType == "1" || _dataType == 1){
			$("#smartModalUl").children().remove();
			$("#smartModalData").children().remove();
		}else if(_dataType =="2" || _dataType == 2){
			$("#fireModalData").children().remove();
		} else {
			$("#apModalData").children().remove();
		}
	}	

	//iot함체  위치 생성
	makeMap()
	function makeMap() {
		const mapSelecter = document.querySelector('.maps .select')

		//select 컨트롤러
		mapSelect()
		function mapSelect() {
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
		}

		//실제 데이터 뿌리기
		function writeIotBox(floor) {
			const ImgContainer = document.querySelector('.map-img');
			const IotBox = [
				{
					floor: 1,
					url: '/resources/publish/images/map1.svg',
					data: [
						{
							left: '30%',
							top: '25%',
							sensorId : '00002ca7804606d1'
						}, {
							left: '46%',
							top: '26%',
							sensorId : '00002ca7804606d2'
						}, {
							left: '54%',
							top: '77%',
							sensorId : '00002ca7804606d3'
						}, {
							left: '36%',
							top: '66%',
							sensorId : '00002ca7804606c2'
						}, {
							left: '54%',
							top: '51%',
							sensorId : '00002ca7804606d5'
						}
					]
				}, {
					floor: 2,
					url: '/resources/publish/images/map2.svg',
					data: [
						{
							left: '42%',
							top: '31%',
							sensorId : '00002ca7804606d6'
						}, {
							left: '70%',
							top: '30%',
							sensorId : '00002ca7804606d7'
						}, {
							left: '70%',
							top: '62%',
							sensorId : '00002ca7804606d8'
						}, {
							left: '43%',
							top: '62%',
							sensorId : '00002ca7804606d9'
						}
					]
				}
			]
			const fireSensor = [
				{
					floor: 1,
					data: [
						{
							left: '36%',
							top: '71%',
							sensorId : '140c5bffff52017b'
						}, {
							left: '55%',
							top: '56%',
							sensorId : '140c5bffff5201ed'
						}
					]
				}, {
					floor: 2,
					data: [
						{
							left: '43.8%',
							top: '62%',
							sensorId : '140c5bffff5201f1'
						}, {
							left: '42%',
							top: '33%',
							sensorId : '140c5bffff5201f2'
						}
					]
				}
			]
			const apSensor = [
				{
					floor: 1,
					data: [
						{
							left: '31.5%',
							top: '25%',
							sensorId : 'apSensor1'
						}, {
							left: '45.5%',
							top: '26%',
							sensorId : 'apSensor2'
						}, {
							left: '54.7%',
							top: '75%',
							sensorId : 'apSensor3'
						}, {
							left: '38%',
							top: '63%',
							sensorId : 'apSensor4'
						}, {
							left: '53%',
							top: '52.5%',
							sensorId : 'apSensor5'
						}
					]
				}, {
					floor: 2,
					url: '/resources/publish/images/map2.svg',
					data: [
						{
							left: '42.8%',
							top: '39%',
							sensorId : 'apSensor6'
						}, {
							left: '70.8%',
							top: '39%',
							sensorId : 'apSensor7'
						}, {
							left: '43.8%',
							top: '56%',
							sensorId : 'apSensor8'
						}, {
							left: '70.8%',
							top: '56%',
							sensorId : 'apSensor9'
						}
					]
				}
			]

			ImgContainer.innerHTML = '';
			const map = document.createElement('img');
			map.setAttribute('src', IotBox[floor].url)
			ImgContainer.appendChild(map)
			IotBox[floor].data.forEach((item, index) => {
				ImgContainer.innerHTML += makeSensorIcon(`sensor${index}`, item)
			})
			fireSensor[floor].data.forEach((item, index) => {
				ImgContainer.innerHTML += makeFireIcon(`fire${index}`, item)
			})
			apSensor[floor].data.forEach((item, index) => {
				ImgContainer.innerHTML += makeApIcon(`ap${index}`, item)
			})


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
		function makeSensorIcon(index, position) {
			return `<div class="sensor-icon ${index}" data-sensor-id='${position.sensorId}' data-type='1' style="left:${position.left}; top:${position.top}">
                                <span>
                                </span>
                            </div>`
		}

		function makeFireIcon(index, position) {
			return `<div class="fire-icon ${index}" data-sensor-id='${position.sensorId}' data-type='2' style="left:${position.left}; top:${position.top}">
                                <span>
                                </span>
                            </div>`
		}

		function makeApIcon(index, position) {
			return `<div class="ap-icon ${index}" data-sensor-id='${position.sensorId}' data-type='3' style="left:${position.left}; top:${position.top}">
                                <span>
                                </span>
                            </div>`
		}

	}

	////////////////////////////
	
	//	센서, 회원사, 서비스 수 조회
	function _getToday(){
		var _url = "/operator/main/today"
		var _data = null;
		_doAjaxGet(_url, _data, _setToday);
	}
	
	function _setToday(res){
		var today = res.data.today;					//	오늘일자
		var sensorCount = res.data.sensorCount;		//	센서 숫자
		var alarmLogCount = res.data.alarmLogCount;	//	회원사 숫자
		var alarmCount = res.data.alarmCount;	//	서비스 숫자
		
		$(".mainToday").text(today);
		$(".mainSensorCount").text(sensorCount);
		$(".mainAlarmLogCount").text(alarmLogCount);
		$(".mainAlarmCount").text(alarmCount);
		
		setTimeout(_getToday, 20000);
	}	
	
	//	센서정보 수신상태 조회
	function _getStatusCnt(){
		var _url = "/operator/main/statusCnt"
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


	//1시간 정상센서 수신율 그래프
	var _hourChart = null;
	function _makeHourChart(sensorRate) {
		const color = ['#64A70B', '#eee', '#002664']
		if(_hourChart != null){
			_hourChart.destroy();
		}
				
		const sensing = document.getElementById('hourData');
		const ctx = document
			.getElementById("hourData")
			.getContext("2d");
		const nowData = sensorRate;
		var gradient = ctx.createLinearGradient(0, 0, 0, 100);
		gradient.addColorStop(0, 'rgba(254,55,94,1)');
		gradient.addColorStop(1, 'rgba(255,200,156,1)');

		const span = document.getElementById('hourPercent')
		span.innerHTML = `${nowData}%`

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
		if(_dayChart != null){
			_dayChart.destroy();
		}		
		const color = ['#64A70B', '#eee', '#002664']
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
		var _url = "/operator/main/sensorWarnLog";
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
				var _li = `<li>${_cDate} - ${_locationDescription} - ${_v}</li>`;
				$(_target).append(_li);		
			}
		}
		setTimeout(_getSensorWarnLog, 20000);
	}	
	
	//	실시간 센싱현황
	function _getSensorRealTime(){
		
		//	셀렉트 선택 추가
		var _url = "/operator/main/sensorRealTime";
		var _data = {
			'enclosureIdx' : $("#sensingInput").attr('data-enclosure-idx')
		}
		//console.log(_data)
		_doAjaxGet(_url, _data, _setSensorRealTime);
	}	

	var _sensorRealTimeInterval = null;
	var _sensorRealTimeChart = null;
	function _setSensorRealTime(res){
		//console.log(res)
		
		if(_sensorRealTimeChart != null){
			_sensorRealTimeChart.destroy();
		}
		
		var _tempList = res.data.tempList;
		var _dustList = res.data.dustList;
		var _humidityList = res.data.humidityList;
		
		const color = ['#64A70B', '#009CDE', '#002664']
		const sensing = document.getElementById('sensingChart');
		const tempData = [];
		const humiData = [];
		const dustData = [];
		
		for(var i=0; i<_tempList.length; i++){
			tempData.push(_tempList[i].sensorValue);
		}

		
		for(var i=0; i<_dustList.length; i++){
			dustData.push(_dustList[i].sensorValue);
		}
		
		for(var i=0; i<_humidityList.length; i++){
			humiData.push(_humidityList[i].sensorValue);
		}
		
		function makeLabels() {
			let arr = []
			for (i = 1; i <= 30; i++) {
				arr.push(i)
			}
			return arr
		}

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
		
		if(_sensorRealTimeInterval != null){
			 clearInterval(_sensorRealTimeInterval); 
		}
		_sensorRealTimeInterval = setInterval(_getSensorRealTime, 20000);
	}	
	
	//	함체의 센서 데이터 조회
	function _getSensorData(){
		var _url = "/operator/main/sensorData";
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
	
	//	로그 1일
	function _getSensorDayLog(){
		var _url = "/operator/main/sensorTodayLog";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorDayLog);		
	}
	
	function _setSensorDayLog(res){
		
		var _target = $("#sensorLog");
		$(_target).children().remove();
		var _list = res.data.sensorLog;
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
					_li = `<li class='error'>${_cDate} - ${_locationDescription} - ${_v} (경고)</li>`;
				}else{
					_li = `<li class=>${_cDate} - ${_locationDescription} - ${_v}</li>`;
				}
				
				$(_target).append(_li);		
			}
		}
		
		setTimeout(_getSensorDayLog, 20000);		
	}
	
	/*
	//	센싱 실시간 로그데이터
	function _getSensorLogLimit(){
		var _url = "/operator/main/sensorRealLog";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorLogLimit);
	}	
	*/
	
	/*
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
				var _cDate = _list[i].sensorLogCreateDate;
				var _locationDescription = _list[i].sensorLocationDescription;
				var _v = koreasoft.modules.string.getValueType(_list[i].sensorTypeCode, _list[i].sensorLogValue);//_list[i].sensorLogValue;
				var _thresHold = _list[i].sensorThresHold;
				var _type = Number(_list[i].sensorLogType);
				//1 경고(임계치 초과), 2 일반
				//var _li = `<li class='${_type==1? 'error' : ''}'>${_cDate} | ${_locationDescription} | 현재값 : ${_v} | 임계치 : ${_thresHold}</li>`;
				
				var _li = null;
				if(_type == 1){
					_li = `<li class='error'>${_cDate} - ${_locationDescription} - ${_v} (경고)</li>`;
				}else{
					_li = `<li class=>${_cDate} - ${_locationDescription} - ${_v}</li>`;
				}
				
				$(_target).append(_li);		
			}
		}
		
		setTimeout(_getSensorLogLimit, 20000);
	}
	*/
	
	//	분전반 상태 정보
	function _getFireLog(){
		var _url = "/operator/main/fireLog";
		var _data = null;
		_doAjaxGet(_url, _data, _setFireLog);		
	}
	
	function _setFireLog(res){
		var _list = res.data.sensorLog
		var _target = $("#fireLog")
		$(_target).find("li").remove();
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
					_li = `<li class='error'>${_cDate} - ${_locationDescription} - ${_v} (경고)</li>`;
				}else{
					_li = `<li class=>${_cDate} - ${_locationDescription} - ${_v}</li>`;
				}
				
				$(_target).append(_li);		
			}
		}
		
		setTimeout(_getFireLog, 20000);
	}
	
	//	화장실 데이터 가져오기
  	function _getToiletSensor(){
		var _url = "/operator/main/sensorToilet";
		var _data = null;
		_doAjaxGet(_url, _data, _setSensorToilet);	
	}
	
	function _setSensorToilet(res){
		_initToilet()
		var _list = res.data.list;
		for(var i=0; i<_list.length; i++){
			var _toiletList = _list[i];
			for(var j=0; j<_toiletList.length; j++){
				var _obj = _toiletList[j]
				useToilet(_obj.toiletIdx, _obj.toiletSort, _obj.toiletStatus)	
			}
		}
		empty();
		
		setTimeout(_getToiletSensor, 20000);		
	}
	//화장실
	let total_toilet = 0;
	let use_toilet = 0;
	let alert_toilet = 0;
	const toiletImg = document.getElementById('toiletImg')

	//	화장실 데이터 초기화
	function _initToilet(){
		total_toilet = 0;
		use_toilet = 0;
		alert_toilet = 0;
		toiletImg.innerHTML = '';
		empty()	
	};
	
	// 데이터 받은 후 함수 반복 실행 
	// 재실행시 초기화 필요
	// toiletImg.innerHTML = null

	function useToilet(group, list, use) {
		const toiletIcon = makeToiletIcon(group, list, use)
		toiletImg.innerHTML += toiletIcon
		total_toilet++;
		if (use == "Y") {
			use_toilet++;
		} else if (use == "E") {
			alert_toilet++;
		}
	}
	
	//ex))
	//useToilet("1","2","N")
	empty()
	// 사용 중인 갯수 쓰기
	function empty() {
		const _empty = document.querySelector('.toilet-empty')
		const _use = document.querySelector('.toilet-use')
		const _alert = document.querySelector('.toilet-alert')

		_empty.innerHTML = `${(total_toilet - use_toilet - alert_toilet)}`
		_use.innerHTML = `${use_toilet}`
		_alert.innerHTML = `${alert_toilet}`
	}

	//사용 아이콘 생성
	function makeToiletIcon(group, list, use) {
		return `<div class="toilet-icon t${group}-${list} use-${use}">
                            </div>`
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
		
	//	함체의 센서 데이터 조회
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
	
	//	센싱 실시간 로그데이터
	//_getSensorLogLimit();
	
	//	분전반 상태 정보 - 현재일자 - 최근 30개
	_getFireLog();
	
	//	로그 1일 : 현재일자 - 최근 500개
	_getSensorDayLog();
	
	//	비디오
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
  	
  	//	화장실 데이터 가져오기
  	_getToiletSensor()
  	
  	/*
  	//	센서 알람
	function _getSensorLogAlarm(){
		var _obj = {
			"companyIdx" : $("input[name=companyIdx]").val()
		}
		_doAjaxGet("/api/v1/sensor/log/alarm", _obj, _setSensorLogAlarm, null)
	}
	
	function _setSensorLogAlarm(res){
		var _list = res.data.list
		for(var i=0; i<_list.length; i++){
			
			pushNotify(_list[i].alarmMessage, 2)
			
		}
		
		setTimeout(_getSensorLogAlarm, 20000)
	}
	
	_getSensorLogAlarm();
	
	function pushNotify(msg, code) {	
		var myNotify = new Notify({
			status: code == 1 ? 'success' : 'error',
			title: msg,
			//text: 'notify text',
			effect: 'slide',
			autoclose: true,
    		autotimeout: 3000,
			type: 3,
			position : 'bottom right'
		})
	}
	*/
}