package ics.operator.mypage;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ics.operator.mypage.dto.CompanyModReqDTO;
import ics.operator.mypage.dto.MyCompanyInfoResDTO;
import ics.operator.mypage.dto.MyMeberListResDTO;

@Mapper
public interface OperatorMypageMapper {

	/**
	 * 마이페이지 회사 정보 가져오기
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	MyCompanyInfoResDTO getMyCompanyInfo(Integer companyIdx) throws Exception;

	/**
	 * 마이페이지 - 회사의 회원 정보 목록
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	List<MyMeberListResDTO> getMyMemberList(Integer companyIdx) throws Exception;

	/**
	 * 마이페이지 - 실증수용기관 정보 수정
	 * @param companyModReqDTO
	 * @throws Exception
	 */
	public void companyModProc(CompanyModReqDTO companyModReqDTO) throws Exception;

}
