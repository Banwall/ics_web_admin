package ics.startuppark.main.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 함체의 센서별 현재 데이터
 * @author Koreasoft
 *
 */
@Getter
@Setter
public class SensorCurrentValueResDTO {
	
	private String	enclosureName;
	private String	enclosureLocation;
	private String	sensorValue;
	
	
}
