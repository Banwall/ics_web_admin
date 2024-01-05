package ics.operator.main.dto;

import lombok.Data;

/**
 * 알람발생 로그(1일)
 * @author Koreasoft
 *
 */
@Data
public class FirelogResDTO {
	
	private String sensorLogCreateDate;	//	로그 생성일
	private String sensorLogValue;		//	로그 값
	private String sensorThresHold;		//	센서의 임계치 값
	private String sensorLocationDescription;	//	센서 설치 위치 설명
	private Integer sensorIdx;			//	센서 인덱스
	private String	sensorTypeCode;

}
