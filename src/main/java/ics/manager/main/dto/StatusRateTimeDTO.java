package ics.manager.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 대시보드 - 센서정보 수신 상태 조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
@Builder
public class StatusRateTimeDTO {

	private String startTime;
	private String endTime;
	
}
