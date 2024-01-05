const centerText = {
	id: 'centerText',
	afterDatasetsDraw(chart, args, options) {
		const { 
			ctx,
			chartArea: {
				left,
				right,
				top,
				bottom,
				width,
				height
			} 
		} = chart;
		
		ctx.save();

		ctx.font = 'bolder 24px Arial';
		ctx.fillStyle = 'rgba(0, 0, 0, 1)';
		ctx.textAlign = 'center';

		ctx.fillText(Math.round(percentList[chart.id]) + "%", width / 2, height / 2 + 10)
	}
}

const options = {
	plugins: {
		legend: {
			position: 'right',
			labels: { color: 'rgb(0,0,0)' }
		},
	},
	responsive: false,
	backgroundColor: ['rgb(76,127,209)', 'rgb(191,191,191)'],
	cutout: '50%',
}

function drawChart(chartCtx, sucCnt, failCnt, legendList) {
	var chart = new Chart(chartCtx, {
		type: 'doughnut',
		data: {
			labels: [legendList[0] , legendList[1]],
			datasets: [
				{
					label: [legendList[0] , legendList[1]],
					data: [sucCnt, failCnt],
					borderRadius: 50
				}
			],
		},
		options: options,
		plugins:[centerText],
	});
}