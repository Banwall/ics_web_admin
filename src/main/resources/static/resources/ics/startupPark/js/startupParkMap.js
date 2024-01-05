let mapContainer = document.getElementById('map'),
		mapOption = {
			center: new kakao.maps.LatLng(37.387360, 126.639999),
			level: 2
		};

let map = new kakao.maps.Map(mapContainer, mapOption);

let positions = [
	{ latlng: new kakao.maps.LatLng(37.388052, 126.640112), title: "IoTlab-cam-01-s", test: "IoTlab-cam-02-s, IoTlab-cam-03-s, IoTlab-cam-04-s" },
	{ latlng: new kakao.maps.LatLng(37.388108, 126.639400), title: "IoTlab-cam-01-s", test: "IoTlab-cam-02-s, IoTlab-cam-03-s, IoTlab-cam-04-s" },
	{ latlng: new kakao.maps.LatLng(37.387343, 126.639624), title: "IoTlab-cam-01-s", test: "IoTlab-cam-02-s, IoTlab-cam-03-s, IoTlab-cam-04-s" },
	{ latlng: new kakao.maps.LatLng(37.387809, 126.638961), title: "IoTlab-cam-01-s", test: "IoTlab-cam-02-s, IoTlab-cam-03-s, IoTlab-cam-04-s" },
]
let rotateList = ['IoTlab-cam-01-s', 'IoTlab-cam-05-s', 'IoTlab-cam-09-s', 'IoTlab-cam-13-s']
let fixList = ['IoTlab-cam-02-s, IoTlab-cam-03-s, IoTlab-cam-04-s', 'IoTlab-cam-06-s, IoTlab-cam-07-s, IoTlab-cam-08-s',
				'IoTlab-cam-10-s, IoTlab-cam-11-s, IoTlab-cam-12-s', 'IoTlab-cam-14-s, IoTlab-cam-15-s, IoTlab-cam-16-s']
for (let i = 0; i < positions.length; i++) {
	let j = 1;
	j += i;

	let marker = new kakao.maps.Marker({
		map: map,
		position: positions[i].latlng
	});

	let content = '<div class ="label"><span style="position: relative; bottom: 55px; font-weight: bold;">' + "Pole" + j + '</span></div>'
	marker.id = "Pole" + j;
	marker.rotate = rotateList[i];
	marker.fix = fixList[i];

	let customOverlay = new kakao.maps.CustomOverlay({
		position: positions[i].latlng,
		content: content
	});

	customOverlay.setMap(map);

	kakao.maps.event.addListener(marker, 'click', function() {
		let id = this.id;
		let rotate = this.rotate;
		let fix = this.fix;
		openModal(id, rotate, fix);
	})
}

function openModal(id, rotate, fix) {
	$(".rotate").text(" : " + rotate);
	$(".fix").text(" : " + fix);
	$(".modal-head h2").text(id + "번 정보");
	$("#smartModal").addClass("active");
}