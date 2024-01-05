package ics.api.sensor.dto;

import lombok.Data;

/**
 * 센서 로그 CSV 파일 다운
 *
 */
@Data
public class SensorLogCsvReqDTO {
	
	private Integer	sensorIdx;
	private String	startDate;
	private String	endDate;
	private String	fileName;

}
