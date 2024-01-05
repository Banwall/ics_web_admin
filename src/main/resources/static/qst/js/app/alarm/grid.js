var gridColumns = [{name: 'NO', formatter: (cell) => {return gridjs.h('span', {id: 'idx'}, `${cell}`);}, width: 100, sort: false},
					{name: '코드', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
					{name: '그룹코드명', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 150},
					{name: '상태', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 250},
					{name: '생성날짜', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 300}];
var gridData =[]

const grid = new gridjs.Grid({
	columns: gridColumns,
	sort: true,
	autoWidth: true,
	search: true,
	pagination: true,
	pagination: {
		limit: 10
	},
	data: [],
	className: {
		td: 'px-2 py-1'
	},
	style: {
		th: {
			'background-color': 'rgb(0, 152, 255)',
			'border': '1px solid rgb(189, 228, 255)',
			'color': '#fff',
			'text-align': 'center',
			'overflow': 'hidden',
			'text-overflow': 'ellipsis',
			'padding': '6px 4px',
			'white-space': 'nowrap',
		},
		td : {
			'text-align' : 'center',
		},
	},
});

$(function() {
	grid.render(document.getElementById('grid'));
});

/*var downloadExcel = function() {
	
	let table = "<table><thead><tr>";
	
	for (var i = 0; i < gridColumns.length; i++) {
		table = table + '<th>' + gridColumns[i]['name'] + '</th>';
	}
	
	table = table + "</tr></thead><tbody>";
	
	for (var i = 0; i < gridData.length; i++) {
		table = table + '<tr>';
		for (var j = 0; j < gridData[i].length; j++) {
			table = table + '<td>' + (gridData[i][j] == null ? '' : gridData[i][j]) + "</td>";
		}
		table = table + '</tr>';
	}
	
	table = table + "</tbody></table>";
	
	var data_type = 'data:application/vnd.ms-excel;charset=utf-8';
	//var table_html = encodeURIComponent($('.gridjs-wrapper').html());
	var table_html = encodeURIComponent(table);
	
	var a = document.createElement('a');
	a.href = data_type + ',%EF%BB%BF' + table_html;
	a.download = 'excel'+'.xls';
	a.click();
};*/

/*var downloadExcel = function() {
	
	let table = '';
	
	for (var i = 0; i < gridColumns.length; i++) {
		if (gridColumns.length - 1 == i) {
			table = table + gridColumns[i]['name'] + '\n';
		} else {
			table = table + gridColumns[i]['name'] + ',';
		}
	}
	
	for (var i = 0; i < gridData.length; i++) {
		for (var j = 0; j < gridData[i].length; j++) {
			if (gridData[i].length - 1 == j) {
				table = table + (gridData[i][j] == null ? '' : gridData[i][j]) + "\n";
			} else {
				table = table + (gridData[i][j] == null ? '' : gridData[i][j]) + ",";
			}
		}
	}
	
	var data_type = 'data:application/vnd.ms-excel;charset=utf-8';
	//var table_html = encodeURIComponent($('.gridjs-wrapper').html());
	var table_html = encodeURIComponent(table);
	
	var a = document.createElement('a');
	a.href = data_type + ',%EF%BB%BF' + table_html;
	a.download = 'excel'+'.csv';
	a.click();
};*/

var setGrid = function() {
	gridColumns = [{name: 'NO', formatter: (cell) => {return gridjs.h('span', {id: 'idx'}, `${cell}`);}, width: 100, sort: false},
				   {name: '코드', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 100},
				   {name: '코드명', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 150},
				   {name: '상태', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 250},
				   {name: '생성날짜', formatter: (cell) => {return gridjs.h('div', {title: `${cell}`}, `${cell}`);}, width: 300}];
				   
	gridData = [];
}

var renderGrid = function(fragment) {
	
	let alarmFlag;
	
	for (var i = 0; i < fragment.length; i++) {
		alramFlag = null;
		if (gridData[i] == null) {
			if(fragment[i].alarmFlag == 0) {
				alarmFlag = '확인'
			}
			gridData.push([fragment[i].alarmIdx, fragment[i].code , fragment[i].codeNm, fragment[i].status, fragment[i].cdt, alarmFlag]);
		} else {
			gridData[i].push([fragment[i].alarmIdx, fragment[i].code , fragment[i].codeNm, fragment[i].status, fragment[i].cdt, alarmFlag]);
		}
	}
	
	grid.updateConfig({data: gridData, columns: gridColumns}).forceRender();
}