package ics.member.user.dto;

import lombok.Data;

/**
 * 회사 정보 가져오기
 * @author Koreasoft
 *
 */
@Data
public class MemberCompanyDTO {

	private Integer companyIdx;
	private String	companyName;
	private String	companyEmail;
	private String	companyPhone;
	private String	companyAddress;
	private String	companyUrl;
	private String	companyRole;
	private String	companyCreateDate;
	private String	companyPresidentName;
	private String	companyRegistNumber;
	

	private String	companyRoleText;//	포멧-> enum
	private String	companyCreateDateDt;
	private String  stationName;
	
}
