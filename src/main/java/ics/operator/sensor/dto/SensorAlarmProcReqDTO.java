package ics.operator.sensor.dto;

import lombok.Data;

/**
 * 센서 알람 설정
 *
 */
@Data
public class SensorAlarmProcReqDTO {
	
	private Integer sensorIdx;
	private String	sensorThreshold;
	private String	sensorAlarmYn;
	
	private Integer modifier;

}
