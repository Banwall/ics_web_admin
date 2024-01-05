package ics.api.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 
 * 회원정보 수정
 */
@Data
public class MemberModProcReqDTO {

	@NotBlank(message="MEMBER_NAME_BLANK")
	private String	memberName;
	private String	memberTel;
	@Email(message="MEMBER_EMAIL_REG")
	private String	memberEmail;
	private String	memberRank;
	private String	memberDeptName;
	private Integer memberIdx;
	
	//	컨트롤러에서 추가
	private Integer modifier;
	
}
