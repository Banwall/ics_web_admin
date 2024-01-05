package ics.startuppark.sensor.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensorLogResDTO {

	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	
}
