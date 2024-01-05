package ics.member.mypage.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 마이페이지 - 내정보 변경
 *
 */
@Data
public class MyInfoModProcReqDTO {

	@NotBlank(message="MEMBER_NAME_BLANK")
	private String	memberName;
	private String	memberTel;
	
	@Email(message="MEMBER_EMAIL_REG")
	private String	memberEmail;
	private String	memberRank;
	private String	memberDeptName;
	
	//	컨트롤러에서 추가
	private Integer memberIdx;
	
	
}
