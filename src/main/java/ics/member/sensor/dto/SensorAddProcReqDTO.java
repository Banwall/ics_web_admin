package ics.member.sensor.dto;



import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 센서 등록
 * @author Koreasoft
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorAddProcReqDTO {

	@NotBlank(message="SENSOR_ID_BLANK")
	private String	sensorId;
	private Integer	sensorTypeIdx;
	private Integer	locationIdx;
	
	@NotBlank(message="SENSOR_LOCATION_DESCRIPTION_BLANK")
	private String	sensorLocationDescription;
	
	
	@NotNull(message="SENSOR_THRESHOLD_NULL")
	private Integer sensorThreshold;
	
	//	컨트롤러에서 추가
	private Integer	companyIdx;
}
