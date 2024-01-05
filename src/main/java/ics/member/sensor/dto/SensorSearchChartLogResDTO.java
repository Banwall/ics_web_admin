package ics.member.sensor.dto;

import lombok.Data;

/**
 * 
 * 데이터 검색
 */
@Data
public class SensorSearchChartLogResDTO {

	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	
}
