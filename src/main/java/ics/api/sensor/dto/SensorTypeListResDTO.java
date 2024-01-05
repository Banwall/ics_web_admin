package ics.api.sensor.dto;

import lombok.Data;

/**
 * 센서 타입 목록
 * @author Koreasoft
 *
 */
@Data
public class SensorTypeListResDTO {

	private Integer	sensorTypeIdx;
	private String	sensorTypeCode;
	private String	sensorTypeName;
	
	
}
