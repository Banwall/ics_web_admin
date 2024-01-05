package ics.startuppark.user;

import ics.startuppark.user.dto.CompanyApprovalListResDTO;
import ics.startuppark.user.dto.CompanyResDTO;
import ics.startuppark.user.dto.MemberSearchReqDTO;
import ics.startuppark.user.dto.MemberSearchResDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StartupParkUserMapper {

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
