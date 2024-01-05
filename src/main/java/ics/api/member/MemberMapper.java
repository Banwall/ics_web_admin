package ics.api.member;

import org.apache.ibatis.annotations.Mapper;

import ics.api.member.dto.MemberAddProcReqDTO;
import ics.api.member.dto.MemberDelProcReqDTO;
import ics.api.member.dto.MemberIsIdReqDTO;
import ics.api.member.dto.MemberModProcReqDTO;
import ics.api.member.dto.MemberPwdModProcDTO;
import ics.api.member.dto.MemberUserInfoResDTO;

@Mapper
public interface MemberMapper {

	/**
	 * 아이디 중복 체크
	 * @param memberIsIdReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer memberIsId(MemberIsIdReqDTO memberIsIdReqDTO) throws Exception;

	/**
	 * 회원 신규 등록
	 * @param memberAddProcReqDTO
	 * @throws Exception
	 */
	public void memberAddProc(MemberAddProcReqDTO memberAddProcReqDTO) throws Exception;

	/**
	 * 회원 정보 삭제
	 * @param memberDelProcReqDTO
	 * @throws Exception
	 */
	public void memberDelProc(MemberDelProcReqDTO memberDelProcReqDTO) throws Exception;

	/**
	 * 회원 정보 조회
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MemberUserInfoResDTO getMemberInfo(Integer memberIdx) throws Exception;

	/**
	 * 회원 정보 수정
	 * @param memberModProcReqDTO
	 * @throws Exception
	 */
	public void memberModProc(MemberModProcReqDTO memberModProcReqDTO) throws Exception;

	/**
	 * 회원 비밀번호 수정
	 * @param memberPwdModProcDTO
	 * @throws Exception
	 */
	public void memberPwdModProc(MemberPwdModProcDTO memberPwdModProcDTO) throws Exception;

}
