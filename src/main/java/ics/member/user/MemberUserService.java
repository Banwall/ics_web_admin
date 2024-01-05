package ics.member.user;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.core.code.RoleCode;
import ics.core.util.StringUtil;
import ics.member.user.dto.MemberCompanyDTO;
import ics.member.user.dto.MemberCompanyModReqDTO;
import ics.member.user.dto.MemberListReqDTO;
import ics.member.user.dto.MemberListResDTO;
import ics.member.user.dto.StationListResDTO;

@Service
@Slf4j
public class MemberUserService {

	@Autowired
	private MemberUserMapper memberUserMapper;
	
	/**
	 * 회원사 정보
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public MemberCompanyDTO getCompanyInfo(Integer companyIdx) throws Exception {
		List<MemberCompanyDTO> listDto =  memberUserMapper.getCompanyInfos(companyIdx);
		MemberCompanyDTO dto =  memberUserMapper.getCompanyInfo(companyIdx);

		String stationName = "";

		for (MemberCompanyDTO memberCompanyDTO : listDto) {
			if (stationName.equals("")) {
				stationName += memberCompanyDTO.getStationName();
			} else {
				stationName += "," + memberCompanyDTO.getStationName();
			}
		}

		log.info("디버깅 :: " + stationName);
		dto.setStationName(stationName);

		RoleCode code = RoleCode.valueOf(dto.getCompanyRole());
		dto.setCompanyRoleText(code.getText());
		dto.setCompanyCreateDateDt(StringUtil.dateFormat(dto.getCompanyCreateDate()));
		return dto;
	}

	/**
	 * 회원사의 회원의 총 수 구하기
	 * @param memberListReqDTO
	 * @return
	 * @throws Exception
	 */
	public Integer getMemberTotalCount(MemberListReqDTO memberListReqDTO) throws Exception{
		return memberUserMapper.getMemberTotalCount(memberListReqDTO);
	}

	/**
	 * 회원사의 회원 목록 구하기
	 * @param memberListReqDTO
	 * @return
	 * @throws Exception
	 */
	public List<MemberListResDTO> getMemberList(MemberListReqDTO memberListReqDTO) throws Exception {
		List<MemberListResDTO> list = memberUserMapper.getMemberList(memberListReqDTO);
		for(MemberListResDTO dto : list) {
			dto.setMemberCreateDateDt(dto.getMemberCreateDate());
		}
		return list;
	}

	/**
	 * 회원사의 정보 수정
	 * @param memberCompanyModReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void companyModProc(MemberCompanyModReqDTO memberCompanyModReqDTO) throws Exception {
		memberUserMapper.companyModProc(memberCompanyModReqDTO);
	}

	/**
	 * 설치 위치
	 * @return
	 * @throws Exception
	 */
	public List<StationListResDTO> getStationList() throws Exception {
		return memberUserMapper.getStationList();
	}

}
