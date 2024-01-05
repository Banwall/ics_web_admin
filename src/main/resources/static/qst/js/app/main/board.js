window.onload = function() {
    $(".startupPark").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheon").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheonAirport").css("background-color", "rgba(255,255,255, 0.15)");
    $(".incheonMaintenance").css("background-color", "rgba(255,192,0, 1)");
    $(".header-nav").hide();

    $("#boardWriteBtnGet").on("click", function() {
        location.href = "/qst/board/writeBoard"
    })

    $("#boardWriteBtnPost").on("click", function() {
        writeBoardEvt();
    })

    $("#reportBoardTable tbody tr td:nth-child(2)").on("click", function() {
        let boardIdx = $(this).find("#idx").val();

        location.href = "/qst/board/writeBoard?idx=" + boardIdx;
    })
}

function valid(obj, msg) {
    let checkInputValue = $.trim(obj.val());

    if( checkInputValue == null || checkInputValue === '') {
        alert(msg);
        return false;
    }
    return true;
}

function writeBoardEvt() {

    let btnText = $("#boardWriteBtnPost").text();
    let boardIdx = $("#hiddenBoardIdx").val();

    if(boardIdx === 0) {
        if( !valid($("#writer"), "작성자를 입력해주세요.") ||
            !valid($("#title"), "제목을 입력해주세요.")) {
            return false;
        }
    }

    if(btnText === "등록" || btnText === "저장") {
        let data = {
            'idx' : $("#detailIdx").val(),
            'title' : $("#title").val(),
            'comment' : $("#comment").val(),
            'writer' : $("#writer").val(),
            'flag' : $("#flag").val()
        }

        if(boardIdx !== 0) {
            if( !valid($("#writer"), "작성자를 입력해주세요.") ||
                !valid($("#title"), "제목을 입력해주세요.")) {
                return false;
            }
        }

        if( confirm(btnText + "하시겠습니까?") ) {
            $.ajax({
                url: "/qst/board/writeBoard",
                type:"POST",
                data: JSON.stringify(data),
                dataType: 'json',
                contentType : "application/json; charset=utf-8",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(_csrfHeader, _csrfToken);
                    xhr.setRequestHeader("AJAX", "true");
                },
            })
            .done(function(fragment) {
                if(fragment.result === "success") {
                    alert(btnText + "을 완료했습니다.");
                    location.href = "/qst/board/reportBoardList";
                } else {
                    alert(btnText + "을 실패했습니다.");
                }
            })
            .fail(function(response) {console.log(response);});
        }
    } else {
        let _detailWriter = $("#detailWriter").text();
        let _detailTitle = $("#detailTitle").text();

        $("#detailWriter").html("<input id='writer' type='text' value='" + _detailWriter + "'>");
        $("#detailTitle").html("<input id='title' type='text' value='" + _detailTitle + "'>");
        $("#detailComment").removeAttr("readonly");
        $("#detailComment").attr("id", "comment");
        $("#flag").val("modify");
        $("#h2Title").text("게시글 수정");
        $("#boardWriteBtnPost").text("저장");
    }
}