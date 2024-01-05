package ics.startuppark.mypage;

import ics.startuppark.mypage.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StartupParkMypageMapper {

	/**
	 * 마이페이지 - 회사 정보 가져오기
	 * @param companyIdx
	 * @return
	 */
	public MyCompanyInfoResDTO getMyCompanyInfo(Integer companyIdx);

	/**
	 * 마이페이지 - 내정보 가져오기
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MyInfoResDTO getMyInfo(Integer memberIdx) throws Exception;

	/**
	 * 마이페이지 - 회사의 회원 정보 목록
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<MyMeberListResDTO> getMyMemberList(Integer companyIdx) throws Exception;

	/**
	 *  마이페이지 - 회사 정보 수정
	 * @param managerCompanyModReqDTO
	 * @throws Exception
	 */
	public void companyModProc(ManagerCompanyModReqDTO managerCompanyModReqDTO) throws Exception;

	/**
	 * 마이페이지 - 내정보 변경 저장
	 * @param myInfoModProcReqDTO
	 * @throws Exception
	 */
	public void myInfoModProc(MyInfoModProcReqDTO myInfoModProcReqDTO) throws Exception;

	/**
	 * 마이페이지 - 내 비밀번호 변경 수정
	 * @param myInfoPwdModProcReqDTO
	 * @throws Exception
	 */
	public void myInfoPwdModProc(MyInfoPwdModProcReqDTO myInfoPwdModProcReqDTO) throws Exception;

	
}
