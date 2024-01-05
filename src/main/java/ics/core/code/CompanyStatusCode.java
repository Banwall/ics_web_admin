package ics.core.code;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CompanyStatusCode {

	Y("Y", "승인"),
	N("N", "미승인"),
	R("R", "승인 요청"),
	W("W", "등록");
	
	private String code;
	private String text;
	
	CompanyStatusCode(String code, String text){
		this.code = code;
		this.text = text;
	}
}
