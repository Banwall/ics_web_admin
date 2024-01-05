package ics.operator.sensor.dto;

import lombok.Data;
/**
 * 데이터 검색 - 차트 로그
 * @author Koreasoft
 *
 */
@Data
public class SensorSearchChartLogResDTO {

	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;
	
}
