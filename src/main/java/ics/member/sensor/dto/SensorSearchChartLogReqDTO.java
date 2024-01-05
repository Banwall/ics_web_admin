package ics.member.sensor.dto;

import lombok.Data;

/**
 * 데이터 검색
 * @author Koreasoft
 *
 */
@Data
public class SensorSearchChartLogReqDTO {

	private Integer sensorIdx;
	private String	startDate;
	private String	endDate;

}
