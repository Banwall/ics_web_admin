package ics.member.sensor.dto;

import lombok.Data;

/**
 * 센서 삭제
 * @author Koreasoft
 *
 */
@Data
public class SensorDelProcReqDTO {

	private Integer sensorIdx;
	
	private Integer memberIdx;
}
