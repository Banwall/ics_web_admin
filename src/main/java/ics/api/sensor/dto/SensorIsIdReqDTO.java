package ics.api.sensor.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorIsIdReqDTO {

	@NotBlank(message="SENSOR_ID_BLANK")
	private String 	sensorId;
	private Integer	sensorTypeIdx;
	
}
