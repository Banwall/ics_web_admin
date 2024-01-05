package ics.api.member.dto;


import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 회원사관리 - 회원 비밀번호 수정
 * @author Koreasoft
 *
 */
@Data
public class MemberPwdModProcDTO {
	private Integer memberIdx;
	
	@NotBlank(message="MEMBER_PWD_BLANK")
	private String	memberPwd;
	
	//	컨트롤러에서 등록
	private Integer modifier;
	private String	encMemberPwd;
}
