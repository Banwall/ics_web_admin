package ics.operator.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대시보드 - 센서정보 수신 상태
 * @author Koreasoft
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusCntResDTO {

	private Integer sensorTotalCount;	//	총 센서 갯수
	private Integer sensorStatusCount;	//	최근 한 시간동안 데이터를 수신한 센서 수
	private Integer userCount;
}
