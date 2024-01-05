package ics.startuppark.user;

import ics.core.code.CompanyStatusCode;
import ics.core.code.RoleCode;
import ics.startuppark.user.dto.CompanyApprovalListResDTO;
import ics.startuppark.user.dto.CompanyResDTO;
import ics.startuppark.user.dto.MemberSearchReqDTO;
import ics.startuppark.user.dto.MemberSearchResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartupParkUserService {
	
	@Autowired
	private StartupParkUserMapper startupParkUserMapper;

	/**
	 * 회원사 승인/미승인 조회
	 * 0 구분없이 전체 조회, 1 전체보기(운영사, 회원사), 2 운영사(실증수요기관), 3회원사(실증기업)
	 * @param roleSearchType
	 * @return
	 * @throws Exception
	 */
	public List<CompanyApprovalListResDTO> getCompanyApprovalList(String roleSearchType) throws Exception {
		List<CompanyApprovalListResDTO> list = startupParkUserMapper.getCompanyApprovalList(roleSearchType);
		for(int i=0; i<list.size(); i++) {
			CompanyApprovalListResDTO dto = list.get(i);
			String companyApprovalDate = dto.getCompanyApprovalDate();
			RoleCode role = RoleCode.valueOf(dto.getCompanyRole());
			dto.setCompanyRoleText(role.getText());
			dto.setCompanyApprovalDateText(companyApprovalDate);
			CompanyStatusCode code = CompanyStatusCode.valueOf(dto.getCompanyApprovalStatus());
			dto.setCompanyApprovalText(code.getText());
		}
		return list;
	}

	/**
	 * 회원사 회사 정보 조회
	 * @param companyIdx
	 * @return
	 */
	public CompanyResDTO getCompanyInfo(Integer companyIdx) throws Exception{
		return startupParkUserMapper.getCompanyInfo(companyIdx);
	}

	/**
	 * 회원사의 회원 수 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberUseCount(Integer companyIdx) throws Exception {
		return startupParkUserMapper.getMemberUseCount(companyIdx);
	}

	/**
	 * 회원사의 회원 조회
	 * @param memberSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberSearchResDTO> getMemberList(MemberSearchReqDTO memberSearchReqDTO) throws Exception {
		return startupParkUserMapper.getMemberList(memberSearchReqDTO);
	}
	
}
