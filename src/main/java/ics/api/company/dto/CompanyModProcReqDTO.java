package ics.api.company.dto;

import lombok.Data;

/**
 * 실증수요기관 / 실증기업 정보 수정
 * @author Koreasoft
 *
 */
@Data
public class CompanyModProcReqDTO {
	
	private Integer companyIdx;
	private String	companyName;
	private String	companyPresidentName;
	private String	companyEmail;
	private String	companyRegistNumber;
	private String	companyPhone;
	private String	companyAddress;
	private String	companyUrl;
	private String	companyRole; 	
	
	//	컨트롤러에서 생성
	private Integer modifier;
}
