package ics.api.sensor.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class SensorTypeAddReqDTO {
	
	@NotBlank(message="SENSOR_TYPE_CODE_BLANK")
	private String	sensorTypeCode;
	
	@NotBlank(message="SENSOR_TYPE_NAME_BLANK")
	private String	sensorTypeName;
	
	// 컨트롤러에서 추가
	private Integer registrant;
}
