package ics.operator.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorDayLogReqDTO {

	private Integer companyIdx;
	private String	startDay;
	private String	endDay;
}
