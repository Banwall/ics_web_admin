window.onload = function() {
    $(".startupPark").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheon").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheonAirport").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheonMaintenance").css("background-color", "rgba(255,192,0, 1)");
    $(".header-nav").hide();

    grpNmClickEvt();
    $("#grpNmListUl").find("li:first-child").click();

    installationSelectEvt();

    $("#saveDeviceList").on("click", function() {
        saveDeviceListEvt();
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
}

function valid(obj, msg) {

    let checkInputValue = $.trim(obj.val());

    if( checkInputValue == null || checkInputValue === '' ) {
        alert(msg);
        return false;
    }
    return true;
}

function installationSelectEvt() {
    $("#installationSelect").on("change", function() {
        $.ajax({
            type:"GET",
            url: "/qst/device/getGrpNmListByInstallation?installation=" + $("#installationSelect option:selected").val(),
            contentType : "application/json; charset=utf-8",
        })
        .done(function(res) {
            let _grpNmList = res.data;
            let _target = $("#grpNmListUl");
            $(_target).find(".grpNmLi").remove();

            for(let i = 0; i < _grpNmList.length; i++) {
                let _grpNmListGrpCd = _grpNmList[i].grpCd;
                let _grpNmListGrpNm = _grpNmList[i].grpNm;

                let _li = `
                <li class="grpNmLi" class="${i === 0 ? 'active' : ''}" data-grp-cd="${_grpNmListGrpCd}">
                    <div style="">${_grpNmListGrpNm}</div>
                </li> `;

                $(_target).append(_li);
            }
            grpNmClickEvt();
            $("#grpNmListUl").find("li:first-child").click();
        })
        .fail(function(response) {console.log(response);});
    })
}

function grpNmClickEvt() {
    $("#grpNmListUl").find("li:first-child").click();
    let _li = $("#grpNmListUl").find("li.grpNmLi");

    $(_li).on("click", function(){
        $("#grpNmListUl").find("li").removeClass("active");
        $(this).addClass("active");

        getDeviceListByGrpCdAndInstallation( $(this).data("grpCd") );
    })
}

function getDeviceListByGrpCdAndInstallation(grpCd) {
    $.ajax({
        type:"GET",
        url: "/qst/device/getDeviceListByGrpCdAndInstallation?grpCd=" + grpCd + "&installation=" + $("#installationSelect option:selected").val(),
        contentType : "application/json; charset=utf-8",
    })
    .done(function(res) {
        let _deviceList = res.data;
        let _target = $("#deviceListTable");
        $(_target).find("tr.deviceList").remove();

        for(let i = 0; i < _deviceList.length; i++) {
            let _deviceListCode = _deviceList[i].code;
            let _deviceListCodeNm = _deviceList[i].codeNm;
            let _deviceListInstallation = _deviceList[i].installation;
            let _deviceListIp = _deviceList[i].ip;
            let _deviceListDeviceSpot = _deviceList[i].deviceSpot;
            let _deviceListValue = _deviceList[i].value;
            let _deviceListUseYn = _deviceList[i].useYn;
            let _deviceListUseYnReason = _deviceList[i].useYnReason;

            let _tbody = `
                    <tr class='deviceList'>
                        <td>
                            <input id="listCodeNm" value="${_deviceListCodeNm}">
                            <input type="hidden" id="listCode" value=${_deviceListCode} />
                        </td>
                        <td>
                            <input id="listInstallation" value="${_deviceListInstallation}">
                        </td>
                        <td>
                            <input id="listIp" value="${_deviceListIp}">
                        </td>
                        <td>
                            <input id="listDeviceSpot" value="${_deviceListDeviceSpot}">
                        </td>
                        <td>
                            <span>${_deviceListValue === 'Y' ? '정상' : '비정상'}</span>
                        </td>
                        <td>
                            <button id="listUseYnBtn" class="${_deviceListUseYn === 'Y' ? 'yBtn' : 'rBtn'}" onclick="listUseYnBtnClickEvt(this);">${_deviceListUseYn === 'Y' ? '사용' : '미사용'}</button>
                            <span></span>
                            <input type="hidden" id="listUseYn" value=${_deviceListUseYn}>
                        </td>
                        <td>
                            <input type="text" id="listUseYnReason" value="${_deviceListUseYnReason}" style="width: 370px;" ${_deviceListUseYn === 'Y' ? 'readonly' : ''}>
                        </td>
                    </tr>
            `;

            $(_target).append(_tbody);
        }
    })
    .fail(function(response) {console.log(response);});
}

function listUseYnBtnClickEvt(el) {
    if( $(el).hasClass("yBtn") === true) {
        // 미사용으로 변경
        $(el).removeClass("yBtn");
        $(el).addClass("rBtn");
        $(el).text("미사용");
        $(el).parent().find("#listUseYn").val("N");
        $(el).parent().parent().find("#listUseYnReason").removeAttr("readonly");
    } else if( $(el).hasClass("rBtn") === true ) {
        // 사용으로 변경
        $(el).removeClass("rBtn");
        $(el).addClass("yBtn");
        $(el).text("사용");
        $(el).parent().find("#listUseYn").val("Y");
        $(el).parent().parent().find("#listUseYnReason").val("");
        $(el).parent().parent().find("#listUseYnReason").attr("readonly", "readonly");
    }
}

function openDeviceAddModal() {
    $("#deviceAddModal").addClass("active");
    addDeviceInit();
}

function closeDeviceAddModal() {
    addDeviceClear();
    $("#deviceAddModal").removeClass("active");
}

function addDeviceClear(){
    $("#addDeviceGrpNm option:eq(0)").prop("selected", true);
    $("#addDeviceSelfGrpNm").val("");
    $("#addCodeNm").val("");
    $("#addIp").val("");
    $("#addInstallation").val("");
    $("#addDeviceSpot").val("");
}

function addDeviceInit() {
    $("#addDeviceGrpNm").empty();
    $(".selfInputDiv").hide();

    $.ajax({
        type:"GET",
        url: "/qst/getDeviceGrpNmList",
        contentType : "application/json; charset=utf-8",
    })
        .done(function(fragment) {
            let grpNmDataList = fragment.data;
            let grpNmOptionHtml = "";
            grpNmOptionHtml += '<option value="">--선택--</option>'

            for(let i = 0; i < grpNmDataList.length; i++) {
                grpNmOptionHtml += '<option value="' + grpNmDataList[i].grpCd + '">' + grpNmDataList[i].grpNm + '</option>'
            }
            grpNmOptionHtml += '<option value="selfInputValue">직접입력</option>'
            $("#addDeviceGrpNm").append(grpNmOptionHtml);
        })
        .fail(function(response) {console.log(response);});
}

function addDevice() {
    let optionGrpNmList = [];

    $("#addDeviceGrpNm option").each(function() {
        optionGrpNmList.push($(this).text());
    })

    if( !valid($("#addDeviceGrpNm"), "그룹명을 선택해주세요.") ||
        !valid($("#addCodeNm"), "코드명을 입력해주세요.") ||
        !valid($("#addIp"), "아이피를 입력해주세요.") ||
        !valid($("#addInstallation"), "설치연도를 입력해주세요.") ||
        !valid($("#addDeviceSpot"), "설치위치를 입력해주세요.") ) {
        return false;
    }

    if($("#addDeviceGrpNm").val() === "selfInputValue" && $("#addDeviceSelfGrpNm").val() == "") {
        alert("그룹명을 입력해주세요.");
        return false;
    }

    if(optionGrpNmList.includes($("#addDeviceSelfGrpNm").val())) {
        alert("이미 등록되어있는 그룹명입니다.");
        return false;
    }

    let addData = {
        'grpCd': $("#addDeviceGrpNm").val() === "selfInputValue" ? '' : $("#addDeviceGrpNm").val(),
        'grpNm': $("#addDeviceGrpNm option:selected").text(),
        'selfGrpNm': $("#addDeviceSelfGrpNm").val(),
        'codeNm': $("#addCodeNm").val(),
        'ip': $("#addIp").val(),
        'installation': $("#addInstallation").val(),
        'deviceSpot': $("#addDeviceSpot").val(),
    }

    $.ajax({
        type:"POST",
        url: "/qst/addDevice",
        data: JSON.stringify(addData),
        dataType: 'json',
        contentType : "application/json; charset=utf-8",
        beforeSend: function(xhr) {
            xhr.setRequestHeader(_csrfHeader, _csrfToken);
            xhr.setRequestHeader("AJAX", "true");
        },
    })
    .done(function(fragment) {
        if(fragment.result === "200") {
            alert("디바이스를 등록했습니다.");
            location.reload();
        } else {
            alert("디바이스등록에 실패했습니다.");
        }
    })
    .fail(function(response) {console.log(response);});
}

function getDeviceList() {
    let deviceList = [];

    $(".deviceList").each(function(index) {
        deviceList.push({
            'code' : $(this).find("#listCode").val(),
            'codeNm' : $(this).find("#listCodeNm").val(),
            'installation' : $(this).find("#listInstallation").val(),
            'ip' : $(this).find("#listIp").val(),
            'deviceSpot' : $(this).find("#listDeviceSpot").val(),
            'useYn' : $(this).find("#listUseYn").val(),
            'useYnReason' : $(this).find("#listUseYnReason").val()
        })
    })

    return deviceList;
}

function saveDeviceListEvt() {
    let data = {
        'deviceList' : getDeviceList()
    }

    $.ajax({
        type:"POST",
        url: "/qst/device/saveDeviceList",
        data: JSON.stringify(data),
        dataType: 'json',
        contentType : "application/json; charset=utf-8",
        beforeSend: function(xhr) {
            xhr.setRequestHeader(_csrfHeader, _csrfToken);
            xhr.setRequestHeader("AJAX", "true");
        },
    })
    .done(function(fragment) {
        if(fragment.result === "200") {
            alert("저장을 완료했습니다.");
            location.reload();
        } else {
            alert("저장에 실패했습니다.");
        }
    })
    .fail(function(response) {console.log(response);});
}