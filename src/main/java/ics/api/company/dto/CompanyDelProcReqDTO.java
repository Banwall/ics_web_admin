package ics.api.company.dto;

import lombok.Data;

/**
 * 회원사 삭제
 * @author Koreasoft
 *
 */
@Data
public class CompanyDelProcReqDTO {

	private Integer companyIdx;
	
	//	컨트롤러에서 생성
	private Integer modifier;
}
