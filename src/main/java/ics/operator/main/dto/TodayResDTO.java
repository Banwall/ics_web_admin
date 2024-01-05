package ics.operator.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 대시보드 - TODAY
 * @author Koreasoft
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodayResDTO {

	/**
	 현재일자가표시되며, 현재기준 등록된 센서의 총 수량, 발생한 알람갯수(일) 등록된 알람설정수량
	 */
	
	private Integer sensorCount;	//	전체 센서 수
	private Integer alarmLogCount;	//	발생한 알람갯수 (일)
	private Integer alarmCount;		//	알람 설정 수량
	private String	today;			//	현재 일자(오늘)
	
}
