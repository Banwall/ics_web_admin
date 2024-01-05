package ics.member.user.dto;

import lombok.Data;

/**
 * 사용자 목록
 * @author Koreasoft
 *
 */
@Data
public class MemberListReqDTO {

	private Integer companyIdx;
	
	private Integer	nowPage;
	private Integer	listCount;
	private Integer	pageGroup;
		
	private Integer	startNum;
	private Integer	endNum;
}
