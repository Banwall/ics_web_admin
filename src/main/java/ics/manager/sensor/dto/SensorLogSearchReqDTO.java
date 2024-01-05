package ics.manager.sensor.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 데이터 검색 : 로그 전체 조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
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
