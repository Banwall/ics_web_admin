package ics.operator.sensor.dto;

import lombok.Data;

@Data
public class SensorListReqDTO {

	//	파라미터
	private Integer nowPage;
	private Integer listCount;
	private Integer pageGroup;
	
	private Integer companyIdx;
	
	//	계산 후 검색 데이터
	private Integer	startNum;
	private Integer endNum;
	
}
