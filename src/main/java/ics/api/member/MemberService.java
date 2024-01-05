package ics.api.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.api.member.dto.MemberAddProcReqDTO;
import ics.api.member.dto.MemberDelProcReqDTO;
import ics.api.member.dto.MemberIsIdReqDTO;
import ics.api.member.dto.MemberModProcReqDTO;
import ics.api.member.dto.MemberPwdModProcDTO;
import ics.api.member.dto.MemberUserInfoResDTO;
import ics.core.util.StringUtil;

@Service
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;

	/**
	 * 아이디 중복 체크
	 * @param memberIsIdReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer memberIsId(MemberIsIdReqDTO memberIsIdReqDTO) throws Exception {
		return memberMapper.memberIsId(memberIsIdReqDTO);
	}

	/**
	 * 회원 신규 등록
	 * @param memberAddProcReqDTO
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void memberAddProc(MemberAddProcReqDTO memberAddProcReqDTO) throws Exception {
		 memberMapper.memberAddProc(memberAddProcReqDTO);
	}

	/**
	 * 회원 정보 삭제
	 * @param memberDelProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void memberDelProc(MemberDelProcReqDTO memberDelProcReqDTO) throws Exception {
		memberMapper.memberDelProc(memberDelProcReqDTO);
	}

	/**
	 * 회원 정보 조회
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MemberUserInfoResDTO getMemberInfo(Integer memberIdx) throws Exception {
		MemberUserInfoResDTO dto = memberMapper.getMemberInfo(memberIdx);
		dto.setMemberCreateDateDt(StringUtil.dateFormat(dto.getMemberCreateDate()));
		return dto;
	}

	/**
	 * 회원 정보 수정
	 * @param memberModProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void memberModProc(MemberModProcReqDTO memberModProcReqDTO) throws Exception {
		memberMapper.memberModProc(memberModProcReqDTO);
	}

	/**
	 * 회원 비밀번호 수정
	 * @param memberPwdModProcDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void memberPwdModProc(MemberPwdModProcDTO memberPwdModProcDTO) throws Exception {
		memberMapper.memberPwdModProc(memberPwdModProcDTO);
	}

}
