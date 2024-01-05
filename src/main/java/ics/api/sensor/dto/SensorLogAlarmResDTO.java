package ics.api.sensor.dto;

import lombok.Data;

@Data
public class SensorLogAlarmResDTO {

	private Integer alarmIdx;
	private String	alarmMessage;
	private String	sensorIdx;
	
	//	컨트롤러에서 넣기
	private Integer memberIdx;
	
}
