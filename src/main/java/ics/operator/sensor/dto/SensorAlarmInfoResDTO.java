package ics.operator.sensor.dto;

import lombok.Data;

/**
 * 
 *
 */
@Data
public class SensorAlarmInfoResDTO {

	private Integer sensorIdx;
	private String	sensorId;
	private String	sensorLocationDescription;
	private String	sensorReceiveDate;
	private String	sensorValue;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	private String	sensorThreshold;
	private String	alarmYn;
	
}
