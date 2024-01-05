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

koreasoft.namespace('koreasoft.modules.regex'); 

koreasoft.modules.regex = function() { 
	//	document 속성 id 가 존재여부를 확인하는 함수	: 존재하면 true 
	var _targetIdCheck = function(target){ 
		if(document.getElementById(target) == undefined){ 
			console.log("target id is undefined : " + target); 
			return true; 
		}else{ 
			return false; 
		} 
	} 
	
	//	document 속성 id 의 값을 가져오는 함수 
	var _targetIdValue = function(target){ 
		return document.getElementById(target).value; 
	} 
	
	///////////	public 
	//	데이터가 있는지 없는지 확인하는 함수		null : true 	not : false 
	var _isNull = function(str) { 
		if(str == undefined || str ==''){ 
			return true 
		}else{ 
			if(str.trim() == ''){ 
				return true; 
			} 
			return false; 
		} 
	};// 
	
	//	document 속성 id를 확인 한 후 값이 널인지 체크하는 함수 
	//	null 일경우 true	아니면 false 
	var _isNullById = function(target){ 
		if(!_targetIdCheck(target)){ 
			return _isNull(_targetIdValue(target)); 
		} 
	} 
	
	//	정규식 사용자 id체크하는 함수 
	//	정규식 규칙에 맞으면 true	아니면 false 
	/*var _isId = function(str){
		//id : 아이디는 영문자로 시작하는 6~20자 영문자 또는 숫자이어야 합니다.
		var _expUserId	=   /^[a-z]+[a-z0-9]{5,19}$/g;
		return _expUserId.test(str); 
	}*/
	/*
	var _isId = function(str){
		//id : 아이디는 대소문자 구분없이 6~20자 영문자 이어야 합니다
		var _expUserId	=   /^[a-zA-Z]{6,20}$/i;
		return _expUserId.test(str); 
	}
	*/
	
	//	대소문자 구분없이 영어만
	var _isId = function(str){
		var _exp = /[a-z]/i;
		return _exp.test(str);
	}
	
	var _isEmail = function(str){
		var _expEmail 	=	/^[a-z0-9_+.-]+@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/
		return _expEmail.test(str);
	}	
	
	/*var _isPassword = function(str){
		//	8 ~ 16자 영문, 숫자, 특수문자를 최소 한가지씩 조합 : "비밀번호는 영문자로 시작하며 영문자, 숫자, 특수문자를 최소 한개씩 조합하여 8 ~ 16자입니다"
		var _regExp = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
		return _regExp.test(str); // 형식에 맞는 경우 true 리턴
	}*/
	
	var _isPassword = function(str){
		//	영문자, 숫자, 특수문자 8자리 이상 
		var _regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
		return _regExp.test(str); // 형식에 맞는 경우 true 리턴
	}
	
	var _isPhone = function(str){
		//	휴대폰 정규식
		var _regExp = /^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;
		return _regExp.test(str); // 형식에 맞는 경우 true 리턴 
	}

	var _isTel = function(str){
		var _regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
		return _regExp.test(str); // 형식에 맞는 경우 true 리턴
	}

	//	이미지 확장자 검색 
	var _isImgExt = function(str){ 
		//var _imgArr = ['JPG', 'JPEG', 'BMP', 'PNG', 'GIF', 'ICO'] 
		var _imgArr = ['JPG', 'JPEG', 'PNG']
		var _str = str.slice(str.lastIndexOf('.')+1); 
		var _result = false; 
		if(_imgArr.includes(_str.toLocaleUpperCase())){ 
			_result = true; 
		} 
		return _result; 
	} 
	
	//	파일 용량 체크
	var _imgSize = 1024 * 1024; //	1mb
	var _imgSizeCheck = function(fileSize){
		var _result = true;
		if(_imgSize<fileSize){
			_result = false;
		}
		return _result;
	}
	
	//	한글만 가능
	var _isKorean = function(str){
		var _regExp =  /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
		return _regExp.test(str); // 형식에 맞는 경우 true 리턴
	}
	
	/////////////////////////////////////////// 

	// 특권 메서드가 들어있는 객체를 반환 
	return { 
		isNull : _isNull, 
		isNullById:_isNullById,
		isId : _isId,
		isImgExt : _isImgExt,
		imgSizeCheck : _imgSizeCheck,
		isEmail: _isEmail,
		isPassword : _isPassword,
		isPhone : _isPhone,
		isTel : _isTel,
		isKorean:_isKorean,
	}; 
}();

/*
6~20 영문 대소문자 , 최소 1개의 숫자 혹은 특수 문자를 포함해야 함

/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/

*/
