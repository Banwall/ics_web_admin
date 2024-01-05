package ics.member.user;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.member.user.dto.MemberCompanyDTO;
import ics.member.user.dto.MemberCompanyModReqDTO;
import ics.member.user.dto.MemberListReqDTO;
import ics.member.user.dto.MemberListResDTO;
import ics.member.user.dto.StationListResDTO;

@Mapper
public interface MemberUserMapper {

	/**
	 * 회원사 정보
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public MemberCompanyDTO getCompanyInfo(Integer companyIdx) throws Exception;

	/**
	 * 회원사 정보
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<MemberCompanyDTO> getCompanyInfos(Integer companyIdx) throws Exception;

	/**
	 * 회원사의 회원의 총 수 구하기
	 * @param memberListReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberTotalCount(MemberListReqDTO memberListReqDTO) throws Exception;

	/**
	 * 회원사의 회원 목록 구하기
	 * @param memberListReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberListResDTO> getMemberList(MemberListReqDTO memberListReqDTO) throws Exception;

	/**
	 * 회원사의 정보 수정
	 * @param memberCompanyModReqDTO
	 * @throws Exception
	 */
	public void companyModProc(MemberCompanyModReqDTO memberCompanyModReqDTO) throws Exception;


	/**
	 * 설치 위치
	 * @return
	 * @throws Exception
	 */
	public List<StationListResDTO> getStationList() throws Exception;

	

}
