package ics.manager.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.manager.user.dto.CompanyResDTO;
import ics.manager.user.dto.CompanyApprovalListResDTO;
import ics.manager.user.dto.MemberSearchReqDTO;
import ics.manager.user.dto.MemberSearchResDTO;

@Mapper
public interface ManagerUserMapper {

	/**
	 * 회원사 승인/미승인 조회
	 * @param roleSearchType
	 * @return
	 * @throws Exception
	 */
	public List<CompanyApprovalListResDTO> getCompanyApprovalList(String roleSearchType) throws Exception;

	/**
	 * 회원사 회사 정보 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public CompanyResDTO getCompanyInfo(Integer companyIdx) throws Exception;

	/**
	 * 회원사의 회원 수 조회
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberUseCount(Integer companyIdx) throws Exception;

	/**
	 * 회원사의 회원 조회
	 * @param memberSearchReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberSearchResDTO> getMemberList(MemberSearchReqDTO memberSearchReqDTO) throws Exception;

	

	
}
