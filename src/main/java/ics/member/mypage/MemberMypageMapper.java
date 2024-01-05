package ics.member.mypage;

import org.apache.ibatis.annotations.Mapper;

import ics.member.mypage.dto.MyInfoModProcReqDTO;
import ics.member.mypage.dto.MyInfoPwdModProcReqDTO;
import ics.member.mypage.dto.MyInfoResDTO;

@Mapper
public interface MemberMypageMapper {

	/**
	 * 마이페이지 - 내정보
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MyInfoResDTO getMyInfo(Integer memberIdx) throws Exception;

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
