package ics.operator.sensor.dto;

import lombok.Data;
/**
 * 데이터 검색 - 센서 선택 리스트
 * @author Koreasoft
 *
 */
@Data
public class SensorSelectResDTO {
	
	private Integer	sensorIdx;
	private String	sensorLocationDescription;

}
