let code = 0;
let grpCd = '';
let flag = '';

function init() {
	//setInterval(fiveMinReload , 5 * 60000);
	sortOrderByValue();
	serverUsage();
	$("#installationSelect").on("change", function() {
		let installation = $(this).val();

		getDeviceAndServer(installation);
	})

	$("#addDeviceGrpNm").on("change", function() {
		let selectGrpNm = $(this).val();

		if(selectGrpNm === "selfInputValue") {
			$(".selfInputDiv").show();
			$("#addDeviceSelfGrpNm").val("");
		} else {
			$(".selfInputDiv").hide();
		}
	})

	$(".getServer").on("click", function() {
		getDevice("server");
	})

	$(".startupPark").css("background-color", "rgba(255,255,255, 0.15)");
	$(".incheon").css("background-color", "rgba(255,255,255, 0.15)");
	$(".incheonAirport").css("background-color", "rgba(255,255,255, 0.15)");
	$(".incheonMaintenance").css("background-color", "rgba(255,192,0, 1)");
	$(".header-nav").hide();
}

function getDevice(type) {
	
	if(type == "sensor") {
		flag = "sensor";
	} else if(type == "server") {
		flag = "server"
	}
	setGrid();
	
	$.ajax({
		url: "/qst/device/getDevice?grpCd=" + grpCd + '&code=' + code + '&flag=' + flag,
		dataType: 'json',
		contentType: 'application/json',
		type:"GET",
	})
	.done(function(fragment) {
		renderGrid(fragment);
		
		code = '';
		grpCd = '';
		flag = '';
	})
	.fail(function(response) {defaultError(response);});
}

/*function fiveMinReload() {
	$.ajax({
		type:"GET",
		url: "/qst/fiveMinReload?value=" + $("#installationSelect option:selected").val(),
		contentType : "application/json; charset=utf-8",
	})
	.done(function(fragment) {
		$("#cardDiv").replaceWith(fragment);
		init();
	})
	.fail(function(response) {console.log(response);});
}*/

function serverUsage() {
	let divCnt = $("#server .serverDiv").length

	for(let i = 0; i < divCnt; i++) {
		let cpu = $("#cpuSpan" + i).data("cpu");
		let ram = $("#ramSpan" + i).data("ram");
		let hdd = $("#hddSpan" + i).data("hdd");
		
		if(cpu >= 50 && cpu <= 75) {
			$("#cpuBar" + i).css("background-color", "rgb(242,192,55)");
		} else if(cpu >= 76) {
			$("#cpuBar" + i).css("background-color", "rgb(255,0,14)");
		} else {
			$("#cpuBar" + i).css("background-color", "rgb(126, 166, 231)");
		}
		
		if(ram >= 50 && ram <= 75) {
			$("#ramBar" + i).css("background-color", "rgb(242,192,55)");
		} else if(ram >= 76) {
			$("#ramBar" + i).css("background-color", "rgb(255,0,14)");
		} else {
			$("#ramBar" + i).css("background-color", "rgb(126, 166, 231)");
		}
		
		if(hdd >= 50 && hdd <= 75) {
			$("#hddBar" + i).css("background-color", "rgb(242,192,55)");
		} else if(hdd >= 76) {
			$("#hddBar" + i).css("background-color", "rgb(255,0,14)");
		} else {
			$("#hddBar" + i).css("background-color", "rgb(126, 166, 231)");
		}
	}
}

function getTypeServer(el) {
	code = $(el).data("code");
	$(".getServer").click();
}

function getDeviceAndServer(val) {
	$.ajax({
		url: "/qst/getDeviceAndServer?value=" + val,
		contentType : "application/json; charset=utf-8",
		type:"GET",
	})
	.done(function(fragment) {
		$("#cardDiv").replaceWith(fragment);
		init();
		// 차트 함수 getChartData();
	})
	.fail(function(response) {console.log(response);});
}

function sortOrderByValue() {
	$(".deviceListDiv").each(function() {
		let value = $(this).find("#valueSpan").val();
		let _this = $(this);

		if(value === "N") {
			$(this).parent().prepend(_this);
		}
	})
}