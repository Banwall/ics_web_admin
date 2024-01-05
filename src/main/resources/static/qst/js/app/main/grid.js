var gridColumns = [{name: 'NO', formatter: (cell) => {return gridjs.h('span', {id: 'idx'}, `${cell}`);}, width: 100, sort: false},
					{name: '코드', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
					{name: '코드명', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 150},
					{name: '상태', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
					{name: '생성날짜', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 250},
					{name: '설치위치', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 200}];
var gridData =[]

const grid = new gridjs.Grid({
	columns: gridColumns,
	sort: true,
	autoWidth: true,
	search: true,
	pagination: true,
	pagination: {
		limit: 6
	},
	data: [],
	className: {
		td: 'px-2 py-1'
	},
	style: {
		th: {
			'background-color': 'rgb(238,238,238)',
			'border': '1px solid rgb(189, 228, 255)',
			'color': 'rgb(0,38,100)',
			'text-align': 'center',
			'overflow': 'hidden',
			'text-overflow': 'ellipsis',
			'padding': '6px 4px',
			'white-space': 'nowrap',
		},
		td : {
			'text-align' : 'center',
			'border': '1px solid rgba(127, 127, 127, 0.2)',
			'overflow': 'hidden',
			'text-overflow': 'ellipsis',
			'white-space': 'nowrap',
		},
	},
});

var setGrid = function() {
	gridColumns = [{name: 'NO', formatter: (cell) => {return gridjs.h('span', {id: 'idx'}, `${cell}`);}, width: 100, sort: false},
				   {name: '코드', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
				   {name: '코드명', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 150},
				   {name: '상태', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
				   {name: '생성날짜', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 200},
				   {name: '설치위치', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 250}];
				   
	gridData = [];
}

var renderGrid = function(fragment) {
	
	let value;
	
	for (var i = 0; i < fragment.length; i++) {
		value = null;
		if (gridData[i] == null) {
			if(fragment[i].value == "Y") {
				value = '정상'
			} else if(fragment[i].value == "N") {
				value = '비정상'
			}
			
			gridData.push([fragment[i].idx, fragment[i].code , fragment[i].codeNm, value, fragment[i].mdt, fragment[i].deviceSpot]);
		} else {
			gridData[i].push([fragment[i].idx, fragment[i].code , fragment[i].codeNm, value, fragment[i].mdt, fragment[i].deviceSpot]);
		}
	}
	
	grid.updateConfig({data: gridData, columns: gridColumns}).forceRender();
}

$(function() {
	grid.render(document.getElementById('grid'));
});