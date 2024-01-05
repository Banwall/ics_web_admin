package ics.operator.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.core.util.StringUtil;
import ics.operator.mypage.dto.CompanyModReqDTO;
import ics.operator.mypage.dto.MyCompanyInfoResDTO;
import ics.operator.mypage.dto.MyMeberListResDTO;

@Service
public class OperatorMypageService {
	
	@Autowired
	private OperatorMypageMapper operatorMypageMapper;

	/**
	 * 마이페이지 회사 정보 가져오기
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public MyCompanyInfoResDTO getMyCompanyInfo(Integer companyIdx) throws Exception{
		return operatorMypageMapper.getMyCompanyInfo(companyIdx);
	}

	/**
	 * 마이페이지 - 회사의 회원 정보 목록
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	public List<MyMeberListResDTO> getMyMemberList(Integer companyIdx) throws Exception {
		List<MyMeberListResDTO> list = operatorMypageMapper.getMyMemberList(companyIdx);
		for(MyMeberListResDTO dto : list) {
			dto.setMemberCreateDateDt(StringUtil.dateFormat(dto.getMemberCreateDate()));
		}
		return list;
	}

	/**
	 * 마이페이지 - 실증수용기관 정보 수정
	 * @param companyModReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void companyModProc(CompanyModReqDTO companyModReqDTO) throws Exception {
		operatorMypageMapper.companyModProc(companyModReqDTO);
	}

}
