package ics.manager.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ics.manager.user.dto.CompanyResDTO;
import ics.manager.user.dto.CompanyApprovalListResDTO;
import ics.manager.user.dto.MemberSearchReqDTO;
import ics.manager.user.dto.MemberSearchResDTO;

@RestController
@RequestMapping("/manager")
public class ManagerUserRestContoller {
	
	@Autowired
	private ManagerUserService managerUserService;
	
	/**
	 *	회원사 승인/미승인 조회
	 * @param roleSearchType
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/companyApprovalList")
	@ResponseBody
	public HashMap companyApprovalList(@RequestParam String roleSearchType) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<CompanyApprovalListResDTO> companyApprovalList = managerUserService.getCompanyApprovalList(roleSearchType);
		dataMap.put("companyApprovalList", companyApprovalList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}
	
	/**
	 * 회원사의 회원 정보
	 * @param companyIdx
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value="/companyInfo")
	@ResponseBody
	public HashMap companyInfo(@RequestParam Integer companyIdx) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		CompanyResDTO companyInfo = managerUserService.getCompanyInfo(companyIdx);
		dataMap.put("companyInfo", companyInfo);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
	
	@GetMapping(value="/memberList")
	@ResponseBody
	public HashMap userMemberList(@ModelAttribute MemberSearchReqDTO memberSearchReqDTO) throws Exception{
		
		boolean result = true;
		HashMap resultMap = new HashMap();
		HashMap dataMap = new HashMap();
		List<MemberSearchResDTO> memberList = managerUserService.getMemberList(memberSearchReqDTO);
		dataMap.put("memberList", memberList);
		resultMap.put("result", result);
		resultMap.put("data", dataMap);
		return resultMap;
	}	
}
