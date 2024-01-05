package ics.manager.main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDataResDTO {

	private Integer sensorIdx;
	private String  sensorTypeCode;
	private String	sensorTypeName;
	private String	sensorValue;
	
}
