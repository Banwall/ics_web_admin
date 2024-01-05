package ics.manager.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CompanyResDTO {

	private Integer companyIdx;
	private String	companyCreateDate;
	private String	companyName;
	private String	companyEmail;
	private String 	companyPhone;
	private String	companyAddress;
	private String	companyUrl;
	private String	companyRole;
	private String	companyRegistNumber;
	private String	companyPresidentName;
	private String	companyApprovalDate;
	private String	companyApprovalStatus;
	
	private Integer	sensorCount;
	
}
