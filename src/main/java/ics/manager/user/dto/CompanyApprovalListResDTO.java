package ics.manager.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyApprovalListResDTO {
	
	private Integer companyIdx;
	private String	companyName;
	private String	companyApprovalStatus;
	private String	companyApprovalText;
	private String	companyApprovalDate;
	private String	companyRole;
	
	private String	companyApprovalDateText;
	private String	companyRoleText;
}
