package ics.member.sensor.dto;

import lombok.Data;

/**
 * 
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
