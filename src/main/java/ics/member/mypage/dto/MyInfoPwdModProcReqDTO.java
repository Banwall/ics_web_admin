package ics.member.mypage.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 마이페이지 - 비밀번호 변경
 * @author Koreasoft
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyInfoPwdModProcReqDTO {

	
	@NotBlank(message="MEMBER_PWD_BLANK")
	private String memberPwd;
	
	
	//	컨트롤러에서 등록
	private String encMemberPwd;
	private Integer memberIdx;
	private Integer modifier;
}
