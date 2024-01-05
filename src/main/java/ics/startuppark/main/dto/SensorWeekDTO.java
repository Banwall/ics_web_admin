package ics.startuppark.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SensorWeekDTO {

	private String startDay;
	private String endDay;
	private Integer sensorTypeIdx;
	
}
