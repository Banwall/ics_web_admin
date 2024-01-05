package ics.api.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ics.api.company.dto.CompanyApprovalReqDTO;
import ics.api.company.dto.CompanyDelProcReqDTO;
import ics.api.company.dto.CompanyJoinProcReqDTO;
import ics.api.company.dto.CompanyModProcReqDTO;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyMapper companyMapper;

	/**
	 * 회원사 회원가입
	 * @param companyJoinProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void joinProc(CompanyJoinProcReqDTO companyJoinProcReqDTO) throws Exception {
		companyMapper.companyJoinProc(companyJoinProcReqDTO);
		// company_station 테이블에 정보를 집어넣어야함.
		String[] selectManagerArr = companyJoinProcReqDTO.getSelectManager().split(",");
		for (String s : selectManagerArr) {
			companyJoinProcReqDTO.setSelectManager(s);
			companyMapper.companyStationJoinProc(companyJoinProcReqDTO);
		}
		companyMapper.memberJoinProc(companyJoinProcReqDTO);
	}

	/**
	 * 회원사 승인/미승인
	 * @param companyApprovalReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void approvalProc(CompanyApprovalReqDTO companyApprovalReqDTO) throws Exception {
		companyMapper.approvalProc(companyApprovalReqDTO);
	}

	/**
	 * 회원사 삭제
	 * @param companyDelProcReqDTO
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void delProc(CompanyDelProcReqDTO companyDelProcReqDTO) throws Exception {
		companyMapper.delProc(companyDelProcReqDTO);
	}

	/**
	 * 회원사 수정
	 * @param companyModProcReqDTO
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	public void modProc(CompanyModProcReqDTO companyModProcReqDTO) throws Exception {
		companyMapper.modProc(companyModProcReqDTO);
	}

}
