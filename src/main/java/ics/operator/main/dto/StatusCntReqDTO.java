package ics.operator.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대시보드 - 센서정보 수신 상태 조회
 * @author Koreasoft
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusCntReqDTO {

	private String startTime;
	private String endTime;
	private Integer companyIdx;
}
