package ics.manager.sensor.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 센서 함체 조회
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class EnclosureListDTO {

	private Integer enclosureIdx;		//	함체 인덱스
	private String	enclosureName;		//	함체 명
	private String	enclosureLocation;	//	함체 설치 위치
	
	
}
