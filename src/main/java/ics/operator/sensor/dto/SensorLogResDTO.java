package ics.operator.sensor.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 센서 - 센서 상세 정보 - 로그조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class SensorLogResDTO {
	
	private String	sensorLogValue;
	private Integer	sensorIdx;
	private String	sensorLogCreateDate;
	private String	sensorLocationDescription;
	private String	sensorTypeName;
	private String	sensorTypeCode;

}
