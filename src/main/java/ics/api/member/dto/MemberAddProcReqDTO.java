package ics.api.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 회원 신규등록
 * @author Koreasoft
 *
 */
@Data
public class MemberAddProcReqDTO {

	
	@NotBlank(message="MEMBER_ID_BLANK")
	private String	memberId;
	private String	memberDeptName;
	
	@NotBlank(message="MEMBER_NAME_BLANK")
	private String	memberName;
	private String	memberRank;
	
	@Email(message="MEMBER_EMAIL_REG")
	private String	memberEmail;
	private String	memberTel;
	
	@NotBlank(message="MEMBER_PWD_BLANK")
	private String	memberPwd;
	private Integer	companyIdx;
	
	//	컨트롤러에서 등록
	private String	encMemberPwd;
	
}
