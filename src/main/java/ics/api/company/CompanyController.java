package ics.api.company;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.api.company.dto.CompanyApprovalReqDTO;
import ics.api.company.dto.CompanyDelProcReqDTO;
import ics.api.company.dto.CompanyJoinProcReqDTO;
import ics.api.company.dto.CompanyModProcReqDTO;
import ics.api.member.MemberService;
import ics.api.member.dto.MemberIsIdReqDTO;
import ics.core.error.ErrorCode;
import ics.core.error.exception.CustomValidException;
import ics.core.util.SessionUtil;
import ics.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/company")
@Slf4j
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private MemberService memberService;

	/**
	 * 회원가입
	 * @param companyJoinProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/joinProc")
	@ResponseBody
	public ResponseEntity joinProc(@RequestBody @Valid CompanyJoinProcReqDTO companyJoinProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		MemberIsIdReqDTO memberIsIdReqDTO = MemberIsIdReqDTO.builder()
				.memberId(companyJoinProcReqDTO.getMemberId())
				.build();
		
		Integer cnt = memberService.memberIsId(memberIsIdReqDTO);
		
		if(cnt > 0) {
			throw new CustomValidException(companyJoinProcReqDTO.getMemberId() + " 이미 사용중", ErrorCode.MEMBER_ID_DUPLICATE);
		}
		
		boolean isSession = SessionUtil.hasSession();
		if(isSession) {
			//	세션 있는 경우 >>
			companyJoinProcReqDTO.setRegistType("A");
			companyJoinProcReqDTO.setRegistrant(SessionUtil.getIdx());
			companyJoinProcReqDTO.setCompanyApprovalStatus("Y");
		}else {
			//	세션 없는 경우 >>
			//	회원사가 회원 가입
			companyJoinProcReqDTO.setRegistType("L");
			companyJoinProcReqDTO.setCompanyApprovalStatus("R");
		}
		
		int roleIdx = companyJoinProcReqDTO.getCompanyRoleIdx();
		String roleCode = roleIdx == 1? "ROLE_O" : "ROLE_M";
		companyJoinProcReqDTO.setCompanyRole(roleCode);
		companyJoinProcReqDTO.setEncMemberPwd(StringUtil.encrypt(companyJoinProcReqDTO.getMemberPwd()));

		log.info("회원사 회원 가입");
		log.info(companyJoinProcReqDTO.toString());
		
		companyService.joinProc(companyJoinProcReqDTO);
		
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 승인/미승인
	 * @param companyApprovalReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/approval")
	@ResponseBody
	public ResponseEntity approvalProc(@RequestBody CompanyApprovalReqDTO companyApprovalReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		companyApprovalReqDTO.setModifier(SessionUtil.getIdx());
		
		log.info("회원사 승인 / 미승인");
		log.info(companyApprovalReqDTO.toString());
		
		companyService.approvalProc(companyApprovalReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 삭제
	 * @param companyDelProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/delProc")
	@ResponseBody
	public ResponseEntity delProc(@RequestBody CompanyDelProcReqDTO companyDelProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		companyDelProcReqDTO.setModifier(SessionUtil.getIdx());
		
		log.info("회원사 삭제");
		log.info(companyDelProcReqDTO.toString());
		
		companyService.delProc(companyDelProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
	
	/**
	 * 회원사 수정
	 * @param CompanyModProcReqDTO
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value="/modProc")
	@ResponseBody
	public ResponseEntity modProc(@RequestBody CompanyModProcReqDTO companyModProcReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		
		companyModProcReqDTO.setModifier(SessionUtil.getIdx());
		
		log.info("회원사 수정");
		log.info(companyModProcReqDTO.toString());
		
		companyService.modProc(companyModProcReqDTO);
		
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		
		return new ResponseEntity(resultMap, HttpStatus.OK);
	}
}
