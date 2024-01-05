package ics.startuppark.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 센서 정보 수신 상태
 * @author Koreasoft
 *
 */
@Getter
@Setter
@Builder
public class StatusCntResDTO {

	private Integer sensorTotalCount;	//	총 센서 갯수
	private Integer sensorStatusCount;	//	최근 한 시간동안 데이터를 수신한 센서 수
	private Integer userCount;
	
}
