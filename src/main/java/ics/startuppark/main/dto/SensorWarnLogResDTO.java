package ics.startuppark.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 센서 경고 현황
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class SensorWarnLogResDTO {

	private String sensorLogCreateDate;	//	로그 생성일
	private String sensorLogValue;		//	로그 값
	private String sensorThresHold;		//	센서의 임계치 값
	private String sensorLocationDescription;	//	센서 설치 위치 설명
	private Integer sensorIdx;			//	센서 인덱스
	private String	sensorTypeCode;
	
}
