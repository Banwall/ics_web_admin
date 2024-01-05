package ics.api.company;

import org.apache.ibatis.annotations.Mapper;

import ics.api.company.dto.CompanyApprovalReqDTO;
import ics.api.company.dto.CompanyDelProcReqDTO;
import ics.api.company.dto.CompanyJoinProcReqDTO;
import ics.api.company.dto.CompanyModProcReqDTO;

@Mapper
public interface CompanyMapper {
	
	public Integer companyJoinProc(CompanyJoinProcReqDTO companyJoinProcReqDTO) throws Exception;

	public void companyStationJoinProc(CompanyJoinProcReqDTO companyJoinProcReqDTO) throws Exception;

	public void memberJoinProc(CompanyJoinProcReqDTO companyJoinProcReqDTO) throws Exception;

	/**
	 * 회원사 승인/미승인
	 * @param companyApprovalReqDTO
	 * @throws Exception
	 */
	public void approvalProc(CompanyApprovalReqDTO companyApprovalReqDTO) throws Exception;

	/**
	 * 회원사 삭제
	 * @param companyDelProcReqDTO
	 * @throws Exception
	 */
	public void delProc(CompanyDelProcReqDTO companyDelProcReqDTO) throws Exception;

	/**
	 * 회원사 수정
	 * @param companyModProcReqDTO
	 * @throws Exception
	 */
	public void modProc(CompanyModProcReqDTO companyModProcReqDTO) throws Exception; 

}
