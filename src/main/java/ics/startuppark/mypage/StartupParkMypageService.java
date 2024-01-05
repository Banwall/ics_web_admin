package ics.startuppark.mypage;

import ics.core.util.StringUtil;
import ics.startuppark.mypage.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StartupParkMypageService {
	
	@Autowired
	private StartupParkMypageMapper managerMypageMapper;

	/**
	 * 마이페이지 - 회사 정보 가져오기
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public MyCompanyInfoResDTO getMyCompanyInfo(Integer companyIdx) throws Exception {
		return managerMypageMapper.getMyCompanyInfo(companyIdx);
	}

	/**
	 * 마이페이지 - 내정보 가져오기
	 * @param memberIdx
	 * @return
	 * @throws Exception
	 */
	public MyInfoResDTO getMyInfo(Integer memberIdx) throws Exception{
		MyInfoResDTO dto = managerMypageMapper.getMyInfo(memberIdx);
		dto.setMemberCreateDateDt(StringUtil.dateFormat(dto.getMemberCreateDate()));;
		return dto;
	}

	/**
	 * 마이페이지 - 회사의 회원 정보 목록
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<MyMeberListResDTO> getMyMemberList(Integer companyIdx) throws Exception {
		List<MyMeberListResDTO> list = managerMypageMapper.getMyMemberList(companyIdx);
		for(int i=0; i<list.size(); i++) {
			MyMeberListResDTO dto = list.get(i);
			dto.setMemberCreateDateDt(StringUtil.dateFormat(dto.getMemberCreateDate()));
		}
		return list;
	}

	/**
	 *  마이페이지 - 회사 정보 수정
	 * @param managerCompanyModReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void companyModProc(ManagerCompanyModReqDTO managerCompanyModReqDTO) throws Exception {
		managerMypageMapper.companyModProc(managerCompanyModReqDTO);
	}

	/**
	 * 마이페이지 - 내정보 변경 저장
	 * @param myInfoModProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void myInfoModProc(MyInfoModProcReqDTO myInfoModProcReqDTO) throws Exception{
		managerMypageMapper.myInfoModProc(myInfoModProcReqDTO);
	}

	/**
	 * 마이페이지 - 내 비밀번호 변경 수정
	 * @param myInfoPwdModProcReqDTO
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void myInfoPwdModProc(MyInfoPwdModProcReqDTO myInfoPwdModProcReqDTO) throws Exception {
		managerMypageMapper.myInfoPwdModProc(myInfoPwdModProcReqDTO);
	}

}
