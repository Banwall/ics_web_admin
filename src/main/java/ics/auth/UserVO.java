package ics.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String 	id;			//	아이디
	private String 	password;	//	비밀번호
	private String 	email;		//	이메일
	private String 	tel;		//	전화번호
	private String 	name;		//	사용자 이름
	private String	auth_id;	//	규칙
	private Integer idx;		//	사용자 인덱스
	
	
	private Integer	companyIdx;	//	회사 인덱스
	private String	companyName;//	회사 명
	
	
	private boolean alarm;
	private String	fcmToken;
	private String	uuid;
	
	private Integer status;		//	스프링 시큐리티 인증 상태
	
}
