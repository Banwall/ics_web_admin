package ics.operator.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 실시간 센싱현황 - 외부온도, 외부습도, 미세먼지 
 * @author Koreasoft
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SensorRealTimeReqDTO {

	Integer enclosureIdx;
	Integer sensorTypeIdx;
	Integer companyIdx;
}
