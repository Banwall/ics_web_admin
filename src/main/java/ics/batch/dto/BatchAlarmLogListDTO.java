package ics.batch.dto;

import lombok.Data;

@Data
public class BatchAlarmLogListDTO {

	private Integer alarmIdx;
	private Integer sensorIdx;
	private String	alarmMessage;
	private String	sensorLocationDescription;
	private String	sensorThreshold;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	private String	stationName;
	private String	alarmCreateDate;
	
}
