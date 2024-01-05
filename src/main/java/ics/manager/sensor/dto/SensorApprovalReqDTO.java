package ics.manager.sensor.dto;

import lombok.Data;

/**
 * 센서 승인 / 미승인
 * @author Koreasoft
 *
 */
@Data
public class SensorApprovalReqDTO {

	private String	sensorApprovalStatus;
	private Integer	sensorIdx;
	
	private Integer modifier;
	
}
