package ics.startuppark.mypage.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 마이페이지 - 내정보 수정
 *
 */
@Data
public class MyInfoModProcReqDTO {

	@NotBlank(message="MEMBER_NAME_BLANK")
	private String	memberName;
	private String	memberTel;
	private String	memberEmail;
	private String	memberRank;
	private String	memberDeptName;
	
	//	컨트롤러에서 추가
	private Integer memberIdx;

	
}
