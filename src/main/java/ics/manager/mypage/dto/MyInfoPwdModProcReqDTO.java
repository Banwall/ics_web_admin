package ics.manager.mypage.dto;

import lombok.Data;

/**
 * 마이페이지 - 비밀번호변경
 *
 */
@Data
public class MyInfoPwdModProcReqDTO {

	private String memberPwd;
	
	
	//	컨트롤러에서 등록
	private String encMemberPwd;
	private Integer memberIdx;
	private Integer modifier;
	
}
