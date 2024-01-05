package ics.core.code;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum RoleCode {
	
	ROLE_A("ROLE_A", "관리기관"),		
	ROLE_O("ROLE_O", "실증수요기관"),	
	ROLE_M("ROLE_M", "실증기업");		

	private String code;
	private String text;

	RoleCode(String code, String text){
		this.code = code;
		this.text = text;
	};
	
	
	
	//	RoleCode[] r = RoleCode.values();			//	배열로 반환
	//	System.out.println(r[0].getCodeByType("0"));//	0번째의 code 반환
	//	System.out.println(RoleCode.valueOf("A").getText());

}
