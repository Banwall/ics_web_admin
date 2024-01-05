package ics.manager.main.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 센서별 데이터 수집율 : 센서 타입별 센서 로그 수 - 현재일자
 * @author Koreasoft
 *
 */
@Getter
@Setter
public class SensorTypeCntResDTO {

	private String sensorTypeCode;	//	센서 타입 코드(유형)
	private String sensorTypeName;	//	센서 타입 명
	private Integer sensorLogCnt;	//	센서 타입별 로그 수
	
}
