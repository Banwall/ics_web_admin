package ics.startuppark.mypage.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * 마이페이지 - 회사 정보 수정
 *
 */
@Data
public class ManagerCompanyModReqDTO {
	//	파라미터
	//@NotBlank(message="COMPANY_NAME_BLANK")
	private String	companyName;
	private String	companyPresidentName;
	@Email(message="COMPANY_EMAIL_REG")
	@NotBlank(message="COMPANY_EMAIL_BLANK")
	private String	companyEmail;
	private String	companyRegistNumber;
	private String	companyPhone;
	private String	companyAddress;
	private String	companyUrl;
	
	//	컨트롤러에서 추가
	private Integer	companyIdx;
	private	Integer	modifier;
}
