package ics.manager.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ics.core.code.CompanyStatusCode;
import ics.core.code.RoleCode;
import ics.manager.user.dto.CompanyResDTO;
import ics.manager.user.dto.CompanyApprovalListResDTO;
import ics.manager.user.dto.MemberSearchReqDTO;
import ics.manager.user.dto.MemberSearchResDTO;

@Service
public class ManagerUserService {
	
	@Autowired
	private ManagerUserMapper managerUserMapper;

	/**
	 * 회원사 승인/미승인 조회
	 * 0 구분없이 전체 조회, 1 전체보기(운영사, 회원사), 2 운영사(실증수요기관), 3회원사(실증기업)
	 * @param roleSearchType
	 * @return
	 * @throws Exception
	 */
	public List<CompanyApprovalListResDTO> getCompanyApprovalList(String roleSearchType) throws Exception {
		List<CompanyApprovalListResDTO> list = managerUserMapper.getCompanyApprovalList(roleSearchType);
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
		return managerUserMapper.getCompanyInfo(companyIdx);
	}

	/**
	 * 회원사의 회원 수 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberUseCount(Integer companyIdx) throws Exception {
		return managerUserMapper.getMemberUseCount(companyIdx);
	}

	/**
	 * 회원사의 회원 조회
	 * @param memberSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberSearchResDTO> getMemberList(MemberSearchReqDTO memberSearchReqDTO) throws Exception {
		return managerUserMapper.getMemberList(memberSearchReqDTO);
	}
	
}
