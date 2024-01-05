package ics.operator.sensor.dto;

import lombok.Data;

@Data
public class SensorLogSearchReqDTO {

	private Integer sensorIdx;

	private Integer nowPage;
	private Integer listCount;
	private Integer pageGroup;

	private String startDate; // 일자 조회 조건
	private String endDate;

	private Integer startNum;
	private Integer endNum;
}
