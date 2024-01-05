package ics.startuppark.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 실시간 센싱현황 - 조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
@Builder
public class SensorRealTimeDTO {

	Integer enclosureIdx;
	Integer sensorTypeIdx;

	/* 스타트업파크 고도화 추가 */

	Integer sensorIdx;
	
}
