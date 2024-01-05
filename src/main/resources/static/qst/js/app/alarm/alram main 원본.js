let result = true;

$(function() {
	$( document ).ajaxStart(function() {
	    //마우스 커서를 로딩 중 커서로 변경
	    $('html').css("cursor", "wait"); 
	});
	//AJAX 통신 종료
	$( document ).ajaxStop(function() {
	    //마우스 커서를 원래대로 돌린다
	    $('html').css("cursor", "auto"); 
	});
})

var validation = function(ele, msg) {
	
	checkInputValue = $.trim(ele.val());
	
	if (checkInputValue == null || checkInputValue == '') {
		alert(msg);
		return false;
	}
	
	return true;
};

var search = function() {
	setGrid();
	result = true;
	
	if (!validation($('#startDate'), "기간을 선택주세요.") || !validation($('#endDate'), "기간을 선택해주세요.")) {
		result = false;
		return;
	}

	let alarmData = {
		'code' : $('#codeNmSelect').val(),
		'startDate' : $('#startDate').val(),
		'endDate' : $('#endDate').val()
	}
	
	$.ajax({
		url: "/alarm/alarmLogList",
		data: JSON.stringify(alarmData),
		dataType: 'json',
		contentType: 'application/json',
		type:"POST",
	})
	.done(function(fragment) {
		renderGrid(fragment);
	})
	.fail(function(response) {defaultError(response);});
}

var updateAlarmFlag = function(el) {
	let alarmIdx = el;
	
	if(!confirm("알람을 확인하시겠습니까?")) {
		return false;
	}
	
	$.ajax({
		url: "/alarm/updateAlarmFlag",
		data: alarmIdx,
		contentType: 'application/json',
		type:"POST",
	})
	.done(function(fragment) {
		alert(fragment);
		
		search();
	})
	.fail(function(response) {defaultError(response);});
}

var excelDown = function() {
	
	search();
	
	if(!result) {
		return false;
	}
	
	if(!confirm("엑셀파일로 다운로드 하시겠습니까?")) {
		return false;
	}
	
	if($("#codeNmSelect").val() != "") {
		$("#hideCode").val( $("#codeNmSelect").val() );
	}
	
	$("#hideStartDate").val( $("#startDate").val() );
	$("#hideEndDate").val( $("#endDate").val() );
	
	$('#excelForm').attr('action', '/excel/excelDownXlsx').attr("method", "GET").submit();
}