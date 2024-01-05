package ics.member.mypage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.core.util.StringUtil;
import ics.member.mypage.dto.MyInfoModProcReqDTO;
import ics.member.mypage.dto.MyInfoPwdModProcReqDTO;
import ics.member.mypage.dto.MyInfoResDTO;

@Service
public class MemberMypageService {

	@Autowired
	private MemberMypageMapper memberMypageMapper;
	
	/**
	 * 마이페이지 - 내정보
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MyInfoResDTO getMyInfo(Integer memberIdx) throws Exception {
		MyInfoResDTO dto = memberMypageMapper.getMyInfo(memberIdx);
		dto.setMemberCreateDateDt(StringUtil.dateFormat(dto.getMemberCreateDate()));
		return dto;
	}
	
	/**
	 * 마이페이지 - 내정보 변경 저장
	 * @param myInfoModProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void myInfoModProc(MyInfoModProcReqDTO myInfoModProcReqDTO) throws Exception {
		memberMypageMapper.myInfoModProc(myInfoModProcReqDTO);
	}

	/**
	 * 마이페이지 - 내 비밀번호 변경 수정
	 * @param myInfoPwdModProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void myInfoPwdModProc(MyInfoPwdModProcReqDTO myInfoPwdModProcReqDTO) throws Exception {
		memberMypageMapper.myInfoPwdModProc(myInfoPwdModProcReqDTO);
	}

	
	
}
