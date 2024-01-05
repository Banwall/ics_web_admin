package ics.api.member.dto;

import lombok.Data;

/**
 * 회원정보 조회
 *
 */
@Data
public class MemberUserInfoResDTO {
	
	private Integer	memberIdx;
	private String	memberId;
	private String	memberDeptName;
	private String	memberName;
	private String	memberRank;
	private String	memberCreateDate;
	private String	memberEmail;
	private String	memberTel;	
	
	//	memberCreateDate date format >>> 2022-01-11 10:05:34 ====> 2021-10-19 (20:21) 형식으로 변경
	private String	memberCreateDateDt;

}
