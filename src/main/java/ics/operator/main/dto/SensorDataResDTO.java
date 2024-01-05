package ics.operator.main.dto;

import lombok.Data;

@Data
public class SensorDataResDTO {

	private Integer sensorIdx;
	private String  sensorTypeCode;
	private String	sensorTypeName;
	private String	sensorValue;
	
}
