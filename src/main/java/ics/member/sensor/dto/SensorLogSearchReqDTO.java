package ics.member.sensor.dto;

import lombok.Data;

/**
 * 데이터 검색 : 로그 전체 조회
 * @author Koreasoft
 *
 */
@Data
public class SensorLogSearchReqDTO {

	private Integer	sensorIdx;	 
	
	private Integer	nowPage;
	private Integer	listCount;
	private Integer	pageGroup;
	
	private String	startDate;	//	일자 조회 조건
	private String	endDate;
	
	private Integer	startNum;
	private Integer	endNum;
	
	
}
