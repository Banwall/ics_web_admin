package ics.qst.vo;

import lombok.Data;

@Data
public class Station {

	/* 테이블 컬럼 */
	private int idx;
	private String routeNumber;
	private String stationNumber;
	private String stationName;
	
	/* 추가 */
}