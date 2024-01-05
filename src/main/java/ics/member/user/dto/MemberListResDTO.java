package ics.member.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MemberListResDTO {

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
