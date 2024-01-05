package ics.member.sensor.dto;

import lombok.Data;

@Data
public class SensorLogResDTO {

	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	
}
