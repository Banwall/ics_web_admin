package ics.startuppark.main.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDataResDTO {

	private Integer sensorIdx;
	private String  sensorLocation;
	private String  sensorTypeCode;
	private String	sensorTypeName;
	private String	sensorValue;

}
