window.onload = function() {
	var _play = null;

	var api = {
		"cctvList" : "/operator/cctv/list"
	}

	const cctvSelct = document.querySelector('#cctvSelect');
	select(cctvSelct);

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
				if(selectInput.getAttribute("id")=="cctvInput"){
					if(_play != null){
						_play.autoplay = false;
					}
					
					var _stationIdx =  $(this).attr("data-station-idx")
					$(selectInput).attr('data-station-idx', _stationIdx);
					var _obj = {
						"stationIdx" : _stationIdx
					}
					_doAjaxGet(api.cctvList, _obj, _setCctvList);
				}
			})
		})
	}
	
	function _setCctvList(res){	
		var _list = res.data.cctvList;
		var _target = $("#cctvListUl");
		$(_target).children().remove();
		if(_list.length == 0){
			var _li = `
				<li class="active cctvListLi" data-cctv-idx='0' data-cctv-ws=''> 조회된 데이터가 없습니다</li>
			`;
			$(_target).append(_li);
		}else{
			for(var i=0; i<_list.length; i++){
				var _cctvIdx = _list[i].cctvIdx;
				var _cctvWs = _list[i].cctvWs;
				var _cctvLocation = _list[i].cctvLocation;
				var _li = `<li class="cctvListLi" data-cctv-idx='${_cctvIdx}' data-cctv-ws='${_cctvWs}'>${_cctvLocation}</li>`;
				
				$(_target).append(_li);
			}
		}
		_makeCctvLiEvt();
	}
	
	
	function _makeCctvLiEvt(){
		var _cctvListLi = $(".cctvListLi");
		$(_cctvListLi).on("click", function(){
			$(_cctvListLi).removeClass("active");
			$(this).addClass("active");	
			var _ws = $(this).attr('data-cctv-ws');
			_showCctv(_ws);
		})
		_cctvListLi[0].click();
	}

	
	function _showCctv(wsUrl){
		console.log(_play)
		
		if(_play != null){
			_play.destroy();
			$("#cctvArea").children().remove()
			var _can = `<canvas class="jsmpeg" data-url=''></canvas>`;
			$("#cctvArea").append(_can)

		}
		var _canvas = document.querySelector('canvas');
		
		_play = new JSMpeg.Player(wsUrl, {
			canvas: _canvas
		});
		
		
		console.log(wsUrl)
		
		_canvas.style.height = '750px';
		_canvas.style.width = '1330px';

		_play.play();
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
				console.log(res);
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
	
	//	최초 실행
	_makeCctvLiEvt()
	
}

