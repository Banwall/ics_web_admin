package ics.member.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원사관리 - 실증기업 정보 수정(회원사의 회원 정보 수정)
 * @author Koreasoft
 *
 */
@Getter
@Setter
@ToString
public class MemberCompanyModReqDTO {
	
	//	파라미터
	@NotBlank(message="COMPANY_NAME_BLANK")
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
