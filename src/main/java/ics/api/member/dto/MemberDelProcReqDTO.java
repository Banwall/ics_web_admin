package ics.api.member.dto;


import lombok.Data;

/**
 * 회원사관리 - 회원정보 삭제
 * @author Koreasoft
 *
 */
@Data
public class MemberDelProcReqDTO {

	private Integer memberIdx;
	private String	memberId;
	private Integer	companyIdx;
	
	//	컨트롤러에서 추가
	private	Integer	modifier;
	
}
