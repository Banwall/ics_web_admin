var koreasoft = koreasoft || {}; 
koreasoft.namespace = function(ns_string) { 
	var parts = ns_string.split('.'), parent = koreasoft, i; 
	if (parts[0] === "koreasoft") { 
		parts = parts.slice(1); 
	} 
	for (i = 0; i < parts.length; i += 1) { 
		if (typeof parent[parts[i]] === "undefined") { 
			parent[parts[i]] = {}; 
		} 
		parent = parent[parts[i]]; 
	} 
	return parent; 
} 

koreasoft.namespace('koreasoft.modules.string'); 

koreasoft.modules.string = function() { 
	
	//	센서 타입
	var sensorType = {
		MICRO_DUST : "미세먼지",
		INNER_TEMPERATURE : "내부 온도",
		INNER_HUMIDITY : "내부 습도",
		OUTER_TEMPERATURE : "외부 온도",
		OUTER_HUMIDITY : "외부 습도"
	}
	
	//	승인 요청
	var approvalType = {
		Y : "승인",
		N : "미승인",
		R : "승인 요청",
		W : "등록"
	}
	
	//	value의 값
	var valueType = {
		MICRO_DUST : "㎍/m",
		INNER_TEMPERATURE : "℃",
		INNER_HUMIDITY : "%",
		OUTER_TEMPERATURE : "℃",
		OUTER_HUMIDITY : "%",
		CO2 : "ppm",
		NOISE : "dB",
		CDS : "Lux",
		EARTHQUAKE_SI : "mm/sec",
		FIRE : "℃",
		LEFT_DOOR : "",
		RIGHT_DOOR : "",
		r_state : "",
		r_ecode : "",
		r_bms : "",
	}
	
	//	value 값 없으면 "";
	var getValueType =function(type, value){
		var str = null;
		if(valueType.hasOwnProperty(type)){
			//	타입 존재
			if(type == 'LEFT_DOOR' || type == 'RIGHT_DOOR'){
				str = Number(value) == 0? '닫힘' : '열림';
			}else{
				if(value == '' || value== null || value == 'null'){
					str = value;
				}else if(type != 'r_ecode') {
					str = `${value} ${valueType[type]}`;
				}
			}

			if(type == 'r_state') {
				str = Number(value) == 0 ? '대기' : Number(value) == 1 ? 'Task 진행' : Number(value) == 2 ? '도킹 중' : Number(value) == 3 ? '일시정지' : Number(value) == 4 ? 'Task 취소' :
					Number(value) == 5 ? '멈춤' : Number(value) == 6 ? '배터리 10% 미만' : Number(value) == 7 ? '충전중' : Number(value) == 8 ? '도킹 아웃중' : Number(value) == 9 ? '대기' : ""
			} else if(type == 'r_ecode') {
				let testArr = value.replace("[", "").replace("]", "").split(",");
				let subArr = [];

				for(let i=0; i<testArr.length; i++) {
					subArr.push(testArr[i].substring(0,2));
				}

				if(value == '' || value == null) {
					str = '정상';
				}

				if(subArr.includes("11")) {
					if(str === null) {
						str = "소프트웨어 오류"
					} else (
						str += ", 소프트웨어 오류"
					)
				}

				if(subArr.includes("12")) {
					if(str === null) {
						str = "하드웨어 오류"
					} else (
						str += ", 하드웨어 오류"
					)
				}
			} else if(type == 'r_bms') {
				str = Number(value) == 0 ? '-' : value + " %"
			}
			else{
				if(value == '' || value== null || value == 'null'){
					str = value;
				}else{
					str = `${value} ${valueType[type]}`;
				}
			}
			
			return str; 	
		}else{
			str = `${value}`;
			return str;
		}
		
	}

	//	날짜 포멧
	var dateFormat = function(text){
		var str = text;
		if(str == "null" || str == "" || str == null){
			str = "";
		}else{
			var dateArr = str.split("T");
			str = dateArr[0] + " (" + dateArr[1].substring(0,5) + ")";
			
		}
		
		return str;
	}
	
	
	
	/**
	2021-12-17
	 */
	var getToday = function(){
		var date = new Date();
		var _year = date.getFullYear();
		var _month = (date.getMonth()+1).toString().length == 1? "0"+ (date.getMonth()+1).toString() : (date.getMonth()+1).toString();
		var _day = date.getDate().toString().length == 1 ? "0"+ date.getDate().toString() : date.getDate().toString();
		var _today = _year.toString()+"-"+_month+"-"+_day;
		
		return _today;
	} 
	 
	//	2021-12-17 (15:33)
	var getDate = function(){
		var date = new Date();
		var _year = date.getFullYear();
		var _month = (date.getMonth()+1).toString().length == 1? "0"+ (date.getMonth()+1).toString() : (date.getMonth()+1).toString();
		var _day = date.getDate().toString().length == 1 ? "0"+ date.getDate().toString() : date.getDate().toString();
		var _today = _year.toString()+"-"+_month+"-"+_day;
		
		var _hours = date.getHours().toString();
		_hours = _hours.length == 1 ? "0"+_hours : _hours;
		
		var _minutes = date.getMinutes().toString();
		_minutes = _minutes.length == 1? "0"+_minutes : _minutes;
	
		let str = `${_today} (${_hours}:${_minutes})`;
		
		return str;
	}
	
	
	var getRoleCode = function(str){
		var roleCode = null
		if(str == "0" || str==0){
			roleCode = "ROLE_A";	//	관리 기관
		}else if(str == "1" || str == 1){
			roleCode = "ROLE_O";	//	실증 수요기관
		}else if(str == "2" || str == 2){
			roleCode = "ROLE_M";	//	실증 기업
		}
		
		return roleCode;
	}
	
	var getRoleName = function(str){
		var roleName = null;
		if(str == "ROLE_O"){
			roleName = "실증수요기관";
		}else if(str == "ROLE_A"){
			roleName = "관리기관";
		}else if(str == "ROLE_M"){
			roleName = "실증기업";
		}
		
		return roleName;
	}
	
	var dateFormatHi = function(str){
		var temp = null;
		if(str == "" || str == null || str == "null"){
			temp = ""
		}else{
			var _arr = str.split(" ");
			var _date = _arr[0];
			var _time = _arr[1];
			temp = _date +" "+ "(" + _time.substring(0,5) + ")";	
		}
		return temp;
	}
	
	
	//	2021-12-17 13:11:11 ->> 13:11:11
	var dateFormatHis = function(str){
		var temp = null;
		var _arr = str.split(" ");
		//var _date = _arr[0];
		var _time = _arr[1];
		return _time;	
	}
	
	var getApprovalType = function(str){
		return approvalType[str];
	}
	
	/////////////////////////////////////////// 

	// 특권 메서드가 들어있는 객체를 반환 
	return { 
		/*sensorFormat : sensorFormat,*/
		getDate : getDate,
		getToday : getToday,
		getValueType :getValueType,
		dateFormat : dateFormat,
		getRoleName : getRoleName,
		dateFormatHi : dateFormatHi,
		getApprovalType : getApprovalType,
		dateFormatHis : dateFormatHis
	}; 
}();
