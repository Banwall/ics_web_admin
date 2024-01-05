package ics.api.company.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 회원사 회원 가입
 *
 */
@Data
public class CompanyJoinProcReqDTO {

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
	@Min(value=1, message="COMPANY_ROLE_BLANK")
	private int	companyRoleIdx;	//	1 :  실증수요기관 ROLE_O , 2: ROLE_M 실증기업
	@NotBlank(message="MEMBER_ID_BLANK")
	private String	memberId;
	@NotBlank(message="MEMBER_PWD_BLANK")
	private String	memberPwd;
	
	//	컨트롤러에서 추가
	private String registType;
	private Integer registrant;
	private Integer companyIdx;
	private String companyRole;
	private String encMemberPwd;
	private String companyApprovalStatus;
	private String selectManager;
	
	
}
