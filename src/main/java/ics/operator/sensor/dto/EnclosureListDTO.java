package ics.operator.sensor.dto;

import lombok.Data;

/**
 * 
 * 함체 조회 - 전체 조회 | 미세먼지,온도,습도 조회 등
 * @author Koreasoft
 *
 */
@Data
public class EnclosureListDTO {

	private Integer enclosureIdx;		//	함체 인덱스
	private String	enclosureName;		//	함체 명
	private String	enclosureLocation;	//	함체 설치 위치
	
}
