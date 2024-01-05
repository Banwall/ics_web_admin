package ics.manager.sensor.dto;

import lombok.Data;

@Data
public class SensorSearchChartLogReqDTO {

	private Integer sensorIdx;
	private String	startDate;
	private String	endDate;
	
}
