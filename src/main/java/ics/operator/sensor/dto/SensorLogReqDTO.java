package ics.operator.sensor.dto;

import lombok.Data;

/**
 * 센서 - 센서 상세 정보 - 로그조회
 * @author Koreasoft
 *
 */
@Data
public class SensorLogReqDTO {

	//	파라미터
	private Integer nowPage;
	private Integer listCount;
	private Integer pageGroup;
	private Integer sensorIdx;
	
	//	조회할 데이터
	private String	startDate;
	private String	endDate;
	
	//	계산 후 검색 데이터
	private Integer	startNum;
	private Integer endNum;
	
}
