window.onload = function(){
	
	 //select 함수
	const sensingSelect = document.querySelector('.sensing .select');	//	실시간 센싱 현황
	const chartSelect = document.querySelector('.charts .select');		//	
	//const smartInfoSelct = document.querySelector('.smartInfo .select');//	스마트 함체 선택

	_getSensorData();

	//select(smartInfoSelct);
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
					let _sensorIdx = $(this).attr('data-sensor-idx');
					$(selectInput).attr('data-sensor-idx', _sensorIdx);
					_getSensorRealTime();
				}else if(selectInput.getAttribute("id") == "chartInput"){
					//	센서별 현재 데이터
					let _sensorTypeIdx = $(this).attr('data-sensor-type-idx');
					$(selectInput).attr('data-sensor-type-idx', _sensorTypeIdx);
					_getSensorCurrentValue();
					
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
			/*		
			//debugger;
			let _sensorId = $(e.currentTarget).attr('data-sensor-id');
			let _dataType = $(e.currentTarget).attr('data-type');
			
			modalClear(_dataType);
			let _obj = {
				'sensorId' : _sensorId,
				'dataType' : _dataType
			}
			
			$.ajax({
				type: "GET",
				url: "/startupPark/main/sensorInfoList",
				dataType: "json",
				data: _obj,
				contentType: "application/json; charset=utf-8",
				beforeSend: function(xhr) {
					xhr.setRequestHeader(_csrfHeader, _csrfToken);
					xhr.setRequestHeader("AJAX", "true");
				},
				success: function(res) {
					
					let _list = res.data.sensorInfoList;
					let _type = res.data.dataType;
					
					if(_type == "1" || _type == 1){
						for(let i=0; i<_list.length; i++){
							//debugger;
							let companyName = _list[i].companyName;
							let companyRole = koreasoft.modules.string.getRoleName(_list[i].companyRole); 
							let requestApprovalDate =_list[i].requestApprovalDate;
							let sensorApprovalDate = _list[i].sensorApprovalDate;
							let sensorLocationDescription = _list[i].sensorLocationDescription;
							let sensorTypeName = _list[i].sensorTypeName;
							let sensorId = _list[i].sensorId;
						
							let _li = `
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
						
						let _liEvt = $("#smartModalUl").find("li")
						$(_liEvt).on("click", function(e){
							$("#smartModalUl").find("li").removeClass("active");
							$(this).addClass("active");
							
							$("#smartModalData").children().remove();
							
							let companyName = $(this).attr('data-company-name');
							let companyRole = $(this).attr('data-company-role'); 
							let requestApprovalDate =  koreasoft.modules.string.dateFormatHi($(this).attr('data-request-approval-date'));
							let sensorApprovalDate =  koreasoft.modules.string.dateFormatHi($(this).attr('data-sensor-approval-date'));
							let sensorLocationDescription = $(this).attr('data-sensor-location-description');
							let sensorTypeName = $(this).attr('data-sensor-type-name');
							let sensorId = $(this).attr('data-sensor-id');
							let _table = `
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
						let companyName = _list[0].companyName;
						let companyRole = koreasoft.modules.string.getRoleName(_list[0].companyRole); 
						let requestApprovalDate = koreasoft.modules.string.dateFormatHi(_list[0].requestApprovalDate);
						let sensorApprovalDate = koreasoft.modules.string.dateFormatHi(_list[0].sensorApprovalDate);
						let sensorLocationDescription = _list[0].sensorLocationDescription;
						let sensorTypeName = _list[0].sensorTypeName;
						let sensorId = _list[0].sensorId;
						
						let _table = `
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
						let _table = `
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
			*/
			
			let checkId = $(e.currentTarget).attr('id');
			if(checkId == "fire-btn1"){
				$("#batchPole").attr("src", "/resources/publish/images/startupPark_batchpole1.png");
				$(".pole0").show();
				$(".pole1").show();
				$(".pole2").hide();
				$(".pole3").hide();
				$(".pole4").hide();	
				$(".pole5").hide();
				$(".pole6").hide();
				$(".pole7").hide();
				
				$("#fire-btn5").css({"left":"68%", "top":"11%"});
				$("#fire-btn6").css({"left":"70%", "top":"10%"});
				$("#fire-btn7").css({"left":"71%", "top":"13%"});
				$("#fire-btn8").css({"left":"69.5%", "top":"20%"});
				$("#fire-btn9").css({"left":"87%", "top":"34%"});
				$("#fire-btn10").css({"left":"86%", "top":"42%"});
				$("#fire-btn11").css({"left":"90%", "top":"42%"});
				$("#fire-btn12").css({"left":"90%", "top":"46%"});					
			}
			else if(checkId == "fire-btn2"){
				$("#batchPole").attr("src", "/resources/publish/images/startupPark_batchpole2.png");
				$(".pole0").hide();
				$(".pole1").hide();
				$(".pole2").show();
				$(".pole3").show();
				$(".pole4").hide();	
				$(".pole5").hide();
				$(".pole6").hide();
				$(".pole7").hide();
				
				$("#fire-btn5").css({"left":"70%", "top":"7%"});
				$("#fire-btn6").css({"left":"72%", "top":"6%"});
				$("#fire-btn7").css({"left":"75%", "top":"9%"});
				$("#fire-btn8").css({"left":"71.5%", "top":"16%"});
				$("#fire-btn9").css({"left":"88%", "top":"37%"});
				$("#fire-btn10").css({"left":"87%", "top":"45%"});
				$("#fire-btn11").css({"left":"90%", "top":"46%"});
				$("#fire-btn12").css({"left":"90%", "top":"50%"});	
			} 
			else if(checkId == "fire-btn3"){
				$("#batchPole").attr("src", "/resources/publish/images/startupPark_batchpole3.png");
				$(".pole0").hide();
				$(".pole1").hide();
				$(".pole2").hide();
				$(".pole3").hide();
				$(".pole4").show();	
				$(".pole5").show();
				$(".pole6").hide();
				$(".pole7").hide();	
				
				$("#fire-btn5").css({"left":"69%", "top":"12%"});
				$("#fire-btn6").css({"left":"70%", "top":"10%"});
				$("#fire-btn7").css({"left":"72%", "top":"13%"});
				$("#fire-btn8").css({"left":"70.5%", "top":"19%"});
				$("#fire-btn9").css({"left":"87%", "top":"34%"});
				$("#fire-btn10").css({"left":"86%", "top":"42%"});
				$("#fire-btn11").css({"left":"90%", "top":"42%"});
				$("#fire-btn12").css({"left":"90%", "top":"46%"});
			} 
			else if(checkId == "fire-btn4"){
				$("#batchPole").attr("src", "/resources/publish/images/startupPark_batchpole4.png");
				$(".pole0").hide();
				$(".pole1").hide();
				$(".pole2").hide();
				$(".pole3").hide();
				$(".pole4").hide();	
				$(".pole5").hide();
				$(".pole6").show();
				$(".pole7").show();	
				
				$("#fire-btn5").css({"left":"73%", "top":"17%"});
				$("#fire-btn6").css({"left":"75%", "top":"16%"});
				$("#fire-btn7").css({"left":"76%", "top":"19%"});
				$("#fire-btn8").css({"left":"74.5%", "top":"25%"});
				$("#fire-btn9").css({"left":"91%", "top":"35%"});
				$("#fire-btn10").css({"left":"90%", "top":"42%"});
				$("#fire-btn11").css({"left":"94%", "top":"42%"});
				$("#fire-btn12").css({"left":"94%", "top":"46%"});
			}
			else if(checkId == "fire-btn5"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1); //_dataType
				let _li = `
					<li>
						고정형 카메라 1
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XNO-6080R</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 외부 상단바 상단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>스타트업파크 일대 영상감시용</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/stop_camera.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn6"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						고정형 카메라 2
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XNO-6080R</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 외부 상단바 상단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>스타트업파크 일대 영상감시용</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/stop_camera.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn7"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						고정형 카메라 3
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XNO-6080R</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 외부 상단바 상단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>스타트업파크 일대 영상감시용</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/stop_camera.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn8"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						회전형 카메라
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XNP-6371RHG</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 외부 상단바 하단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>스타트업파크 일대 영상감시용</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/rotate_camera.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn9"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						PUBLIC WiFi WIPS
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XIRRUS XH2-240</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 지지대 중상단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>폴대에 설치된 WiFi WIPS</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/wifi_ap.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn10"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						PUBLIC WiFi AP
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>XIRRUS XH2-240</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 지지대 중단</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>공공 WiFi 서비스</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/wifi_ap.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn11"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						CCTV용 PoE 스위치
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>OS6465-P6</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 지지대 중단 몸체 내부</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>CCTV 네트워크 연결 및 전원공급</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/poe_switch.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn12"){
				let _dataType = $(e.currentTarget).attr('data-type');	
				modalClear(1);
				let _li = `
					<li>
						WiFi용 PoE 스위치
					</li>
				`;
				$("#smartModalUl").append(_li);
				let _table = `
					<table>
						<tr>
							<th>모델명</th>
							<td>OS6465-P6</td>
						</tr>
						<tr>
							<th>설치 위치</th>
							<td>POLE 지지대 중단 몸체 내부</td>
						</tr>
						<tr>
							<th>설치 목적</th>
							<td>WiFi 네트워크 연결 및 전원공급</td>
						</tr>
						<tr>
							<th>제품 이미지</th>
							<td><img src="/resources/publish/images/poe_switch.png" style="width:90%; height:90%;"></td>
						</tr>
					</table>
				`;
				$("#smartModalData").append(_table);
				$("#smartModalData").width(700);
				index.classList.add('active');	
			}
			else if(checkId == "fire-btn13"){
				$('#fire-btn5').trigger('click');		
			}
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
			const ImgContainer = document.querySelector('.startup-map-img');
			ImgContainer.innerHTML = ''; // 초기화 목적

			const map = document.createElement('img');
			map.setAttribute('src', '/resources/publish/images/startupPark_batchpole1.png');
			map.setAttribute('id', 'batchPole');
			ImgContainer.appendChild(map);
			ImgContainer.innerHTML += makeFireIcon(1, 38.5, 51);
			ImgContainer.innerHTML += makeFireIcon(2, 31.8, 24.5);
			ImgContainer.innerHTML += makeFireIcon(3, 21, 60.5);
			ImgContainer.innerHTML += makeFireIcon(4, 14.5, 45);
			ImgContainer.innerHTML += makeFireIcon(5, 68, 11);
			ImgContainer.innerHTML += makeFireIcon(6, 70, 10);
			ImgContainer.innerHTML += makeFireIcon(7, 71, 13);
			ImgContainer.innerHTML += makeFireIcon(8, 69.5, 20);
			//ImgContainer.innerHTML += makeFireIcon(9, 87, 34); WIPS는 안띄운다고 함
			ImgContainer.innerHTML += makeFireIcon(10, 86, 42);
			ImgContainer.innerHTML += makeFireIcon(11, 90, 42);
			ImgContainer.innerHTML += makeFireIcon(12, 90, 46);
			
			ImgContainer.innerHTML += makeFireTextIcon(13, 68, 2, 8, 5);

			// img 태그를 새로 생성 -> 이미지를 경로로 잡고 .map-ing 밑에 추가한 후 초기화 (이미지 경로가 백그라운드 이미지)

			//모달 생성하기
			const sensorIcon = ImgContainer.querySelectorAll('.sensor-icon');
			sensorIcon.forEach((item) => {
				makeModalBtn(smartModal, item);
			})
			modal(smartModal)
			const fireIcon = ImgContainer.querySelectorAll('.fire-icon');
			fireIcon.forEach((item) => {
				makeModalBtn(smartModal, item); //원래는 fireModal
			})
			modal(smartModal) //modal(fireModal)
			const apIcon = ImgContainer.querySelectorAll('.ap-icon');
			apIcon.forEach((item) => {
				makeModalBtn(apModal, item);
			})
			modal(apModal)
		}
		
		//sensorIcon 만들기
		function makeFireIcon(id, left, top) {
			return `<div class="fire-icon 0" data-sensor-id='${id}' data-type='2' id="fire-btn${id}" style="left:${left}%; top:${top}%;">
                        <span>
                        </span>
                    </div>`	
		}
		
		//sensorIcon 만들기
		function makeFireTextIcon(id, left, top, width, height) {
			return `<div class="fire-icon 0" data-sensor-id='${id}' data-type='2' id="fire-btn${id}" style="opacity:0; left:${left}%; top:${top}%; width:${width}%; height:${height}%;">
                        <span>
                        </span>
                    </div>`	
		}
		
		//sensorIcon 만들기
		function makeSensorIcon(id, left, top) {
			return `<div class="sensor-icon 0" data-sensor-id='${id}' data-type='2' id="fire-btn${id}" style="left:${left}%; top:${top}%;">
                        <span>
                        </span>
                    </div>`
		}
	}
	
        
	////////////////////////////
	
	//	센서, 회원사, 서비스 수 조회
	function _getToday(){
		let _url = "/startupPark/main/today"
		let _data = null;
		_doAjaxGet(_url, _data, _setToday);
	}
	
	function _setToday(res){
		let today = res.data.today;					//	오늘일자
		let sensorCount = res.data.sensorCount;		//	센서 숫자
		let companyCount = res.data.companyCount;	//	회원사 숫자
		let serviceCount = res.data.serviceCount;	//	서비스 숫자
		
		$(".mainToday").text(today);
		$(".mainSensorCnt").text(sensorCount);
		$(".mainUserCnt").text(companyCount);
		$(".mainServiceCnt").text(serviceCount);
		
		setTimeout(_getToday, 20000);
	}
	
	//	센서정보 수신상태 조회
	function _getStatusCnt(){
		let _url = "/startupPark/main/statusCnt"
		let _data = null;
		_doAjaxGet(_url, _data, _setStatusCnt);
	}
	
	function _setStatusCnt(res){
		let _statusCnt = res.data.sensorStatusCount;	//	정상 수신 센서
		let _totalCnt = res.data.sensorTotalCount;		//	전체 센서
		let _userCnt = res.data.userCount;
		
		let _sensorRate = Math.floor(Number(_statusCnt) / Number(_totalCnt) * 100);
		_makeHourChart(_sensorRate);
		
		//let _dayRate = 86;
		_makeDayChart(_userCnt);
		setTimeout(_getStatusCnt, 20000);
	}
	
	
	//	1시간 정상센서 수신율
	let _hourChart = null;
	function _makeHourChart(sensorRate) {
		//const color = ['#64A70B', '#eee', '#002664'];
		if(_hourChart != null){
			_hourChart.destroy();
		}
		
		let sensing = document.getElementById('hourData');
		
		const ctx = document
			.getElementById("hourData")
			.getContext("2d");
		const nowData = sensorRate;
		let gradient = ctx.createLinearGradient(0, 0, 0, 100);
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
	let _dayChart = null;
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
		let gradient = ctx.createLinearGradient(0, 0, 0, 100);
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
		let _url = "/startupPark/main/sensorWarnLog";
		let _data = null;
		_doAjaxGet(_url, _data, _setSensorWarnLog);		
	}
	
	//	센서 경고현황 컬백 로그 
	function _setSensorWarnLog(res){
		let _target = $("#sensorWarnLog");
		$(_target).children().remove();
		let _list = res.data.warnLogList;
		if(_list.length == 0){
			let _li = `<li>조회된 데이터가 없습니다</li>`;
			$(_target).append(_li);
		}else{
			for(let i=0; i<_list.length; i++){
				let _cDate = koreasoft.modules.string.dateFormatHis(_list[i].sensorLogCreateDate);
				let _locationDescription = _list[i].sensorLocationDescription;
				let _v = koreasoft.modules.string.getValueType(_list[i].sensorTypeCode, _list[i].sensorLogValue);
				let _thresHold = _list[i].sensorThresHold;
				
				//let _li = `<li>${_cDate} | ${_locationDescription} | 현재값 : ${_v} | 임계치 : ${_thresHold}</li>`;
				let _li = `<li>${_cDate} - ${_locationDescription} : ${_v}</li>`;
				$(_target).append(_li);		
			}
		}
		setTimeout(_getSensorWarnLog, 20000);
	}
	
	//	실시간 센싱 현황
	function _getSensorRealTime(){
		
		//	셀렉트 선택 추가
		let _url = "/startupPark/main/sensorRealTime";
		let _data = {
			'sensorIdx' : $("#sensingInput").attr('data-sensor-idx')
		}
		_doAjaxGet(_url, _data, _setSensorRealTime);
	}

	// enclosureIdx 에서 sensorIdx로 바꿔서 넘긴 후 해당 센서의 로그 값 가져오기

	let _sensorRealTimeInterval = null;
	let _sensorRealTimeChart = null;
	let _sensorRealTimeBmsChart = null;
	function _setSensorRealTime(res){

		if(_sensorRealTimeChart != null){
			_sensorRealTimeChart.destroy();
		}

		if(_sensorRealTimeBmsChart != null){
			_sensorRealTimeBmsChart.destroy();
		}
		
		let _tempList = res.data.tempList;
		let _humidityList = res.data.humidityList;

		const color = ['#64A70B', '#009CDE']
		const sensing = document.getElementById('sensingChart');
		const tempData = [];
		const humiData = [];

		for(let i=0; i<_tempList.length; i++){
			tempData.push(_tempList[i].sensorValue);
		}

		for(let i=0; i<_humidityList.length; i++){
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
	
	//	센서별 현재데이터
	function _getSensorCurrentValue(){
				
		//	셀렉트 선택 추가
		let _url = "/startupPark/main/sensorCurrentValue";
		let _data = {
			'sensorType' :  $("#chartInput").attr('data-sensor-type-idx')
		}
		_doAjaxGet(_url, _data, _setSensorCurrentValue);
		$("#batchPole").attr("src", "/resources/publish/images/startupPark_batchpole1.png");
	}
	
	let _sensorCurrentChart = null;
	let _sensorCurrentValueInterval = null;
	function _setSensorCurrentValue(res){
		
		if(_sensorCurrentChart!=null){
			_sensorCurrentChart.destroy();
		}
		
		let _currList = res.data.sensorCurrList;
		let _type = Number(res.data.sensorTypeIdx);
		
		const color = ['#64A70B', '', '#009CDE', '#002664'];
		const colorAlpha = ['rgba(100,167,11,0.2)','', 'rgba(0,156,222,0.2)', 'rgba(0,38,100,0.2)'];
		const label = ['온도 ℃', '', '습도 %', '미세먼지 ㎍/m'];
		const sensing = document.getElementById('sensorNow');
		let nowData = [];
		let labels = [];
		
		for(let i=0; i<_currList.length; i++){
			let _enclosureName = _currList[i].enclosureName;
			let _sensorValue = _currList[i].sensorValue;
			
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
		
		let _url = "/startupPark/main/sensorTypeCnt"
		let _data = null;
		_doAjaxGet(_url, _data, _setSensorTypeCnt);
	}
	
	//	센서별 데이터 수집율 - 컬백 - 센서 타입별 센서 로그 수(현재일자)	//	설치된 센서종류별 데이터 수신을을 표시함(1일)
	let _sensorTypeChart = null;
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
        
        let _list = res.data.typeLogCntList;

        const sensing = document.getElementById('dataCheck');
        let _sensorLogData = [];
        let _nowData = [];
        let _labels = [];
        let _totalCnt = 0;
		
        for(let i=0; i<_list.length; i++){
			let _sensorLogCnt = _list[i].sensorLogCnt;
			_totalCnt = Number(_sensorLogCnt) + _totalCnt;
			_sensorLogData.push(_sensorLogCnt);	//	데이터 : 
			_labels.push(_list[i].sensorTypeName);	//	라벨
		}
		
		for(let i=0; i<_sensorLogData.length; i++){
			let _v = Math.ceil(_sensorLogData[i]/_totalCnt * 100);
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
                            let idx = context.dataIndex
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
		let _url = "/startupPark/main/sensorWeek";
		let _data = null;
		_doAjaxGet(_url, _data, _setSensorWeek);
	}
	
	let _sensorWeek = null;
	function _setSensorWeek(res){
		
		if(_sensorWeek != null){
			_sensorWeek.destroy();
		}

		let _dayList = res.data.day.dayList;
		let _tempList = res.data.tempList;
		let _humidityList = res.data.humidityList;
		
		let _tempData = [];
		let _humiData = [];
		
		for(let i=0; i<_tempList.length; i++){
			let _v = Number(_tempList[i].sensorLogValue);
			let _cnt = Number(_tempList[i].sensorLogCnt);
			_tempData.push(Math.ceil(_v/_cnt));
		}
		
		for(let i=0; i<_humidityList.length; i++){
			let _v = Number(_humidityList[i].sensorLogValue);
			let _cnt = Number(_humidityList[i].sensorLogCnt);
			_humiData.push(Math.ceil(_v/_cnt));
		}
		
		const color = ['#64A70B', '#009CDE']
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
		let _url = "/startupPark/main/sensorRealLog";
		let _data = null;
		_doAjaxGet(_url, _data, _setSensorLogLimit);
	}
	
	//	센싱 실시간 로그데이터 컬백
	function _setSensorLogLimit(res){
		let _target = $("#sensorLog");
		$(_target).children().remove();
		let _list = res.data.realLogList;
		if(_list.length == 0){
			let _li = `<li> 조회된 데이터가 없습니다 </li>`;
			$(_target).append(_li);
		}else{
			for(let i=0; i<_list.length; i++){
				//debugger;
				let _cDate = koreasoft.modules.string.dateFormatHis(_list[i].sensorLogCreateDate);
				let _locationDescription = _list[i].sensorLocationDescription;
				let _v = koreasoft.modules.string.getValueType(_list[i].sensorTypeCode, _list[i].sensorLogValue);//_list[i].sensorLogValue;
				let _thresHold = _list[i].sensorThresHold;
				let _type = Number(_list[i].sensorLogType);
				//1 경고(임계치 초과), 2 일반
				//let _li = `<li class='${_type==1? 'error' : ''}'>${_cDate} | ${_locationDescription} | 현재값 : ${_v} | 임계치 : ${_thresHold}</li>`;
				
				let _li = null;
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
		let _url = "/startupPark/main/sensorData";
		_doAjaxGet(_url, '', _setSensorData);
	}

	let _sensorDataInterval = null;
	function _setSensorData(res){
		$("#smartInfoDiv").find("h4").remove();
		$("#smartInfoDiv").find("ul").remove();
		let _list = res.data.sensorDataList;

		for(let i = 0; i < _list.length; i++) {
			let _type = _list[i].sensorTypeCode;
			let _typeName = _list[i].sensorTypeName;
			let _sensorValue = _list[i].sensorValue;
			let _smartInfoTitle = _list[i].sensorLocation;
			let _unit =  koreasoft.modules.string.getValueType(_type, _sensorValue);

			let _img = null;
			let _class = null;
			if(_type.toLocaleLowerCase().indexOf('temp')> 0) {
				_img = '<img src="/resources/publish/images/temp.png" alt="">';
				_class = 'temp';
			} else if(_type.toLocaleLowerCase().indexOf('humi')>0) {
				_img = '<img src="/resources/publish/images/humi.png" alt="">';
				_class = 'humi';
			} else {
				_class='';
				_img = '';
			}

			let _h4 =
			`
				<h4 class="pole${i}">${_smartInfoTitle}</h4>
			`;
			if(i % 2 == 0) {
				$("#smartInfoDiv").append(_h4);
			}

			let _ul =
			`
				<ul class="pole${i}">
					<li class='${_class} pole${i}'>
						${_img}
						<p class="pole${i}">${_typeName} : ${_unit}</p>
					</li>
				</ul>
			`;
			
			if(i % 2 == 1) {
				_ul = _ul + 
				`
					<ul class="pole${i}">
						<li class='pole${i}'>
							<p>CCTV용 PoE 스위치</p>
						</li>
						<li class='pole${i}'>
							<p>WiFi용 PoE 스위치</p>
						</li>
						<li class='pole${i}'>
							<p>고정형 카메라 1</p>
						</li>
						<li class='pole${i}'>
							<p>고정형 카메라 2</p>
						</li>
						<li class='pole${i}'>
							<p>고정형 카메라 3</p>
						</li>
						<li class='pole${i}'>
							<p>회전형 카메라</p>
						</li>
						<li class='pole${i}'>
							<p>PUBLIC WiFi AP</p>
						</li>
					</ul>
				`
			}
			
			$("#smartInfoDiv").append(_ul);
		}
		
		if(_sensorDataInterval != null){
			clearInterval(_sensorDataInterval);
		}
		
		_getAliveData();
		
		_sensorDataInterval = setInterval(_getSensorData, 20000);
		
		$(".pole0").show();
		$(".pole1").show();
		$(".pole2").hide();
		$(".pole3").hide();
		$(".pole4").hide();	
		$(".pole5").hide();
		$(".pole6").hide();
		$(".pole7").hide();	
		
		$("#fire-btn5").css({"left":"68%", "top":"11%"});
		$("#fire-btn6").css({"left":"70%", "top":"10%"});
		$("#fire-btn7").css({"left":"71%", "top":"13%"});
		$("#fire-btn8").css({"left":"69.5%", "top":"20%"});
		$("#fire-btn9").css({"left":"87%", "top":"34%"});
		$("#fire-btn10").css({"left":"86%", "top":"42%"});
		$("#fire-btn11").css({"left":"90%", "top":"42%"});
		$("#fire-btn12").css({"left":"90%", "top":"46%"});
	}
	
	//	함체의 센서 데이터 조회
	function _getAliveData(){
		let _url = "/startupPark/main/aliveData";
		_doAjaxGet(_url, '', _setAliveData);
	}

	let _aliveDataInterval = null;
	function _setAliveData(res){
		let _list = res.data.aliveDataList;
		let _span;
		
		for(let i = 0; i < _list.length; i++) {
			let _codeNm = _list[i].codeNm;
			let _value = _list[i].value;
			
			if(_value == 'Y') {
				_span = 
				`
				<span class="greenCircle"></span>&nbsp;&nbsp;
				`;	
			}
			else {
				_span = 
				`
				<span class="redCircle"></span>&nbsp;&nbsp;
				`;		
			}
						
			if(i == 0) {				
				$('.pole1').eq(4).prepend(_span);	
			}
			else if(i == 1) {
				$('.pole1').eq(5).prepend(_span);
			}
			else if(i == 2) {
				$('.pole1').eq(6).prepend(_span);	
			}
			else if(i == 3) {
				$('.pole1').eq(7).prepend(_span);
			}
			else if(i == 4) {
				$('.pole1').eq(8).prepend(_span);
			}
			else if(i == 5) {
				$('.pole1').eq(9).prepend(_span);
			}
			else if(i == 6) {
				$('.pole1').eq(10).prepend(_span);
			}
			else if(i == 7) {
				$('.pole3').eq(4).prepend(_span);
			}
			else if(i == 8) {
				$('.pole3').eq(5).prepend(_span);
			}
			else if(i == 9) {
				$('.pole3').eq(6).prepend(_span);
			}
			else if(i == 10) {
				$('.pole3').eq(7).prepend(_span);
			}
			else if(i == 11) {
				$('.pole3').eq(8).prepend(_span);
			}
			else if(i == 12) {
				$('.pole3').eq(9).prepend(_span);
			}
			else if(i == 13) {
				$('.pole3').eq(10).prepend(_span);
			}
			else if(i == 14) {
				$('.pole5').eq(4).prepend(_span);
			}
			else if(i == 15) {
				$('.pole5').eq(5).prepend(_span);
			}
			else if(i == 16) {
				$('.pole5').eq(6).prepend(_span);
			}
			else if(i == 17) {
				$('.pole5').eq(7).prepend(_span);
			}
			else if(i == 18) {
				$('.pole5').eq(8).prepend(_span);
			}
			else if(i == 19) {
				$('.pole5').eq(9).prepend(_span);
			}
			else if(i == 20) {
				$('.pole5').eq(10).prepend(_span);
			}
			else if(i == 21) {
				$('.pole7').eq(4).prepend(_span);
			}
			else if(i == 22) {
				$('.pole7').eq(5).prepend(_span);
			}
			else if(i == 23) {
				$('.pole7').eq(6).prepend(_span);
			}
			else if(i == 24) {
				$('.pole7').eq(7).prepend(_span);
			}
			else if(i == 25) {
				$('.pole7').eq(8).prepend(_span);
			}
			else if(i == 26) {
				$('.pole7').eq(9).prepend(_span);
			}
			else if(i == 27) {
				$('.pole7').eq(10).prepend(_span);
			}		
		}
		
		/*if(_aliveDataInterval != null){
			clearInterval(_aliveDataInterval);
		}
		
		_aliveDataInterval = setInterval(_getAliveData, 20000);*/
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
		let _s = $(".chartLi")[0];//	0번 선택
		let _text = $(_s).html();	//	센서 타입명
		let _v = $(_s).attr("data-sensor-type-idx"); // 센터 타입 선택
		let _target =  $("#chartInput");
		$(_target).val(_text);
		$(_target).attr('data-sensor-type-idx', _v);
		_getSensorCurrentValue();
	}
	
		
	//	실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 - 현재일자 최근 30개
	if($(".sensingLi").length!=0){
		let _s = $(".sensingLi")[0];//	0번 선택
		let _text = $(_s).html();	//	함체명
		let _v = $(_s).attr("data-sensor-idx"); // 함체 번호 선택
		let _target =  $("#sensingInput");
		$(_target).val(_text);
		$(_target).attr('data-sensor-idx', _v);
		_getSensorRealTime();	
	}		
	
	
	//	함체의 센서 데이터 조회 - 최근 받은값
	if($(".smartInfoLi").length!=0){
		let _s = $(".smartInfoLi")[0];//	0번 선택
		let _text = $(_s).html();	//	함체명
		let _v = $(_s).attr("data-enclosure-idx"); // 함체 번호 선택
		let _target =  $("#smartInfoInput");
		$(_target).val(_text);
		$("#smartInfoTitle").text(_text); //	smartInfoTitle
		$(_target).attr('data-enclosure-idx', _v);
		_getSensorData();
	}
	
	//	요일별 평균 데이터 조회	- 주 단위(월~일)
	_getSensorWeek();
	
	//	비디오
	/*
	let canvas = document.getElementsByClassName('jsmpeg');
	let client = [];
	client.push('ws://192.168.230.105:20005');
	client.push('ws://192.168.230.105:20007');
	
	let player = [];
	for (let i = 0; i < canvas.length; i++) {
		let p = new JSMpeg.Player(client[i], {
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
	