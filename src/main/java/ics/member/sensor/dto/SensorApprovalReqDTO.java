package ics.member.sensor.dto;

import lombok.Data;

/**
 * 센서 승인 요청
 * @author Koreasoft
 *
 */
@Data
public class SensorApprovalReqDTO {

	private Integer sensorIdx;
	
	//	컨트롤러에서 추가
	private Integer modifier;
}
