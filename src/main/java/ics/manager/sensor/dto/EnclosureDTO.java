package ics.manager.sensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnclosureDTO {

	private Integer enclosureIdx;		//	함체 인덱스
	private String  enclosureName;		//	함체 명
	private Integer enclosureSeq;		//	함체 정렬 조건
	private String 	enclosureLocation;	//	함체 설치 위치
	private String	enclosureUseYn;		//	함체 사용 여부
	private String	enclosureRegistDate;	//	함체 등록일
	private String	enclosureType;		//	E
	
}
