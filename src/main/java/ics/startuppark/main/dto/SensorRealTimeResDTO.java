package ics.startuppark.main.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 실시간 센싱현황 - 응답
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class SensorRealTimeResDTO {

	private Integer sensorIdx;
	private String	sensorValue;
	private String	createDate;
}
