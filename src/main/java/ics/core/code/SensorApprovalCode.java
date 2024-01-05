package ics.core.code;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SensorApprovalCode {

	Y("Y", "승인"),
	N("N", "미승인"),
	R("R", "요청"),
	W("W", "등록");
	
	private String code;
	private String text;
	
	SensorApprovalCode(String code, String text){
		this.code = code;
		this.text = text;
	}
}
