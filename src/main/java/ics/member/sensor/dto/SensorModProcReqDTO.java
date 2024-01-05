package ics.member.sensor.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 센서 수정
 * @author Koreasoft
 *
 */
@Data
public class SensorModProcReqDTO {

	@NotBlank(message="SENSOR_ID_BLANK")
	private String	sensorId;
	private Integer	sensorTypeIdx;
	private Integer	locationIdx;
	@NotBlank(message="SENSOR_LOCATION_DESCRIPTION_BLANK")
	private String	sensorLocationDescription;
	private Integer	sensorIdx;
	
	@NotNull(message="SENSOR_THRESHOLD_NULL")
	private Integer sensorThreshold;
	
	//	컨트롤러에서 추가
	private Integer	companyIdx;
	
}
