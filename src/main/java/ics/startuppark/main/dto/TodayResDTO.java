package ics.startuppark.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * today
 * 응답 DTO
 *
 */
@Getter
@Setter
@Builder
@ToString
public class TodayResDTO {
	
	private Integer sensorCount;	//	전체 센서 수
	private Integer companyCount;	//	전체 회사 수 (ROLE_M, ROLE_O)
	private Integer serviceCount;	//	전체 서비스 수
	private String	today;			//	현재 일자(오늘)
	
}
