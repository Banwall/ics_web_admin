package ics.api.company.dto;

import lombok.Data;

/**
 * 회원사 승인/미승인
 * @author Koreasoft
 *
 */
@Data
public class CompanyApprovalReqDTO {
	
	private	Integer	companyIdx;
	private String	companyApprovalStatus;
	
	private Integer modifier;
	

}
